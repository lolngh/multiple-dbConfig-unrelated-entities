package com.ngh.multipledatasources.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.ngh.multipledatasources.repository.data",
                        entityManagerFactoryRef = "dataBlockEntityManagerFactory",
                        transactionManagerRef = "dataBlockTransactionManager" )
public class DataConfig {

	@Bean
	@Primary // primary datasource to differentiate between multiple datasources used
	@ConfigurationProperties("ngh.datasource.data-block")
	public DataSourceProperties dataBlockDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties("ngh.datasource.data-block.configuration")
	public DataSource dataBlockDataSource() {
		return dataBlockDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}
	
	@Primary
	@Bean(name="dataBlockEntityManagerFactory") //assigning name is optional
    public LocalContainerEntityManagerFactoryBean dataBlockEntityManagerFactory(EntityManagerFactoryBuilder entityManagerFactory) {
//	return entityManagerFactory.dataSource(dataBlockDataSource())
//				.packages("com.ngh.multipledatasources.model.blocks").build();
		LocalContainerEntityManagerFactoryBean lb = new LocalContainerEntityManagerFactoryBean();
		lb.setDataSource(dataBlockDataSource());
		lb.setPackagesToScan(new String[] {"com.ngh.multipledatasources.model.blocks"});
		HibernateJpaVendorAdapter hb = new HibernateJpaVendorAdapter();
		lb.setJpaVendorAdapter(hb);
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		properties.put("hibernate.hbm2ddl.auto", "update");
		lb.setJpaPropertyMap(properties);
		return lb;
	}
	
	@Primary
	@Bean //assigning name is optional
	public PlatformTransactionManager dataBlockTransactionManager(final @Qualifier("dataBlockEntityManagerFactory") LocalContainerEntityManagerFactoryBean dataBlockEntityManager) {
		return new JpaTransactionManager(dataBlockEntityManager.getObject());
	}

}
