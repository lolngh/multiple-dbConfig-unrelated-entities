package com.ngh.multipledatasources.model.blocks;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int movieId;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name="bookId")
	@JsonIgnore
	private Book book;

}
