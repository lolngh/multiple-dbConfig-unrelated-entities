package com.ngh.multipledatasources.model.blocks;

import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookId;
	
	private String name;
	
	private String publishedon;
	
	private String publishedby;
	
	@OneToMany(mappedBy="book",cascade = CascadeType.ALL)
	private List<Movie> movies;	
	 
}
