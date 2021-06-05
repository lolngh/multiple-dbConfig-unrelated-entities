package com.ngh.multipledatasources.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

//Commented block is of one typr of implementation type with auto configuring properties for hibernate
@Configuration
@PropertySource({ "classpath:multiple-db.properties" }) //at one config file this is enough it will be auto configured in the context
@EnableJpaRepositories(basePackages = "com.ngh.multipledatasources.repository.publisher",entityManagerFactoryRef = "publisherEntityManagerFactory",transactionManagerRef = "publisherTransactionManager")
public class PublisherConfig {

	@Bean
	@ConfigurationProperties("ngh.datasource.user")
	public DataSourceProperties publisherDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties("ngh.datasource.user.configuration")
	public DataSource publisherDataSource() {
		return publisherDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean(name = "publisherEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean publisherEntityManagerFactory() {
//			(EntityManagerFactoryBuilder publisherEntityManager) {
//		return publisherEntityManager.dataSource(publisherDataSource())
//				.packages("com.ngh.multipledatasources.model.publisher").build();
		LocalContainerEntityManagerFactoryBean lb = new LocalContainerEntityManagerFactoryBean();
		lb.setDataSource(publisherDataSource());
		lb.setPackagesToScan("com.ngh.multipledatasources.model.publisher");
		HibernateJpaVendorAdapter hb = new HibernateJpaVendorAdapter();
		lb.setJpaVendorAdapter(hb);
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");// only this property is enough. To show-sql add tht property in hibernate
		properties.put("hibernate.enable_lazy_load_no_trans", "true"); //Each fetch of a lazy entity will open a temporary session and run inside a separate transaction. Or use @Transactional in component or service where the method is called
		lb.setJpaPropertyMap(properties);
		return lb;
	}

	@Bean
	public PlatformTransactionManager publisherTransactionManager(
			final @Qualifier("publisherEntityManagerFactory") LocalContainerEntityManagerFactoryBean publisherEntityManager) {
		return new JpaTransactionManager(publisherEntityManager.getObject());
	}
}
