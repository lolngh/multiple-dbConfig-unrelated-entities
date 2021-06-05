package com.ngh.multipledatasources.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ngh.multipledatasources.model.blocks.Book;
import com.ngh.multipledatasources.model.blocks.Movie;
import com.ngh.multipledatasources.model.publisher.Chcek;
import com.ngh.multipledatasources.model.publisher.Publisher;
import com.ngh.multipledatasources.repository.data.BookRepo;
import com.ngh.multipledatasources.repository.data.MovieRepo;
import com.ngh.multipledatasources.repository.publisher.ChecKRepo;
import com.ngh.multipledatasources.repository.publisher.PublisherRepo;

@RestController
@RequestMapping("ngh")
public class DataController {

	@Autowired
	public MovieRepo movieRepo;
	
	@Autowired
	public BookRepo bookRepo;
	
	@Autowired
	public PublisherRepo publisherRepo;
	
	@Autowired
	public ChecKRepo repo;
	
	@PostMapping("/createUsers")
	public String createUser(@RequestBody Publisher publisher) {
		publisher.setPublishes(0);
		publisherRepo.save(publisher);
		return "User Created Successfully";
	}
	
	@PostMapping("/addBook/{publisherId}")
	public String addBook(@RequestBody Book book, @PathVariable int publisherId) {
		Publisher publisher = publisherRepo.findById(publisherId).orElseThrow(()-> new IllegalArgumentException("User is not found"));
		publisher.setPublishes(publisher.getPublishes()+1);
		System.out.println("in-1");
		publisherRepo.save(publisher);
		System.out.println("in-2");
		bookRepo.save(book);
		System.out.println("in-3");
		return "Data added";
	}
	
	@GetMapping("/publisher")
	public List<Publisher> getPublishers(@RequestParam String userName,@RequestParam String emailID,@RequestParam int publishes){
		return publisherRepo.findByUserNameAndEmailIDAndPublishes(userName, emailID, publishes);
	}
	
	@PostMapping("/check")
	public void addCheck(@RequestBody Chcek check) {
		repo.save(check);
	}
	
	@GetMapping("/sample/{id}")
	public List<Movie> getMovies(@PathVariable int id){
		return movieRepo.findByBook_bookId(id);
	}
	
	@GetMapping("/getpublisher/{name}")
	public List<Publisher> getpublisher(@PathVariable String name){
		return publisherRepo.findByUserNameOrderByUserIdAsc(name);
	}
	
	@GetMapping("/all/{id}")
	public Publisher getById(@PathVariable int id){
		//return publisherRepo.findAll();
		Publisher data = publisherRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("not there"));
		System.out.println(data.getChcek());
		return data;
	}
	
	@GetMapping("/sortBy")
	public List<Publisher> sortBy(){
		return publisherRepo.findAll(Sort.by("userName").descending().and(Sort.by("userId")));
	}
	
	@GetMapping("/getall")
	public List<Publisher> getAll(){
		return publisherRepo.findAll();
	}
	
	@GetMapping("/getdata/{email}")
	public List<Publisher> getDataByEmail(@PathVariable String email){
		return publisherRepo.findByEmailID(email, Sort.by("userName").descending().and(Sort.by("userId")));
	}
	
	@GetMapping("/getchcek/{name}") 
	public List<Chcek> getCheckByPublisher_Username(@PathVariable String name){
		return repo.getByPublisherKey(name);
	}
}
