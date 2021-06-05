package com.ngh.multipledatasources.repository.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngh.multipledatasources.model.blocks.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

	List<Book> findByNameAndPublishedbyAndPublishedon(String name,String publishedby, String publishedon);
}
