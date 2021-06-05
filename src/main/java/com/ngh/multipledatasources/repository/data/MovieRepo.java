package com.ngh.multipledatasources.repository.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngh.multipledatasources.model.blocks.Movie;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Integer> {

	List<Movie> findByBook_bookId(int bookId);
}
