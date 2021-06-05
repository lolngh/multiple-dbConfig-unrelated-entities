package com.ngh.multipledatasources.repository.publisher;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ngh.multipledatasources.model.publisher.Chcek;

@Repository
public interface ChecKRepo extends JpaRepository<Chcek, Integer> {

	@Query(value="SELECT C.* FROM CHCEK C JOIN PUBLISHER P ON C.USER_ID = P.USER_ID WHERE P.USERNAME = ?",nativeQuery=true)
	List<Chcek> getByPublisherKey(String userName);
}
