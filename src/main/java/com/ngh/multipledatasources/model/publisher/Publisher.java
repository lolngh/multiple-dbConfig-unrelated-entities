package com.ngh.multipledatasources.model.publisher;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Data;

@Entity
@Data
public class Publisher {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence") // creating a sequence ID_SEQ to
																					// generate id's as IDENTITY feature
																					// does because oracle doen't
    																				// support IDENTITY generationType
	@SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	@Column(name="User_ID")
	private int userId;

	private String userName;

	private String emailID;

	private int publishes;
	
	@OneToMany
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(
       name = "User_ID", 
       referencedColumnName = "User_ID", 
       insertable = false, 
       updatable = false, 
       foreignKey = @javax.persistence
         .ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private List<Chcek> chcek;

}
