package com.ngh.multipledatasources.model.publisher;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Chcek {

	@Id
	private int id;
	
	private String data;
	
  @Column(name="User_ID",precision=6,scale=0,nullable=false)	
   private int temp;
    
//    @ManyToOne
//    @JoinColumn(name="userId")
//    private Publisher publish;
}
