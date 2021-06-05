package com.ngh.multipledatasources.repository.publisher;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ngh.multipledatasources.model.publisher.Publisher;

@Repository
public interface PublisherRepo extends JpaRepository<Publisher, Integer> {

	List<Publisher> findByUserNameAndEmailIDAndPublishes(String userName,String emailID,int publishes);
	
	List<Publisher> findByUserNameOrderByUserIdAsc(String userName);
	
	@Query(value = "select u from Publisher u where u.id=?1")
	List<Publisher> findData(int userId);
	
	List<Publisher> findByEmailID(String emailID, Sort sort);
	
}
