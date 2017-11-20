package com.example.cmpe275lab2.model;

import org.hibernate.validator.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "player")
public class Player implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)   // auto generate key ID
    private Long id;
	
	@NotBlank
    private String firstname;         // require element, can't be null or blank
	
	@NotBlank
    private String lastname;          // require element
    
    @NotBlank
    private String email;             // require element, check unique in controller
    
    private String description;
    
    @Embedded
    private Address address = new Address();    // embedded from address class
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sponsor_Id")
    private Sponsor sponsor;                   // one player can only map to one sponsor
    
    @ManyToMany
    @JoinTable(
        name = "opponent",
        joinColumns = @JoinColumn(name = "player_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "opponent_id", referencedColumnName = "id"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"player_id", "opponent_id"})
    )
    @JsonIgnoreProperties("opponents")
    private List<Player> opponents = new ArrayList<>();      // self join to create a table that contain opponent relationship
    
    // constructors, setters, getters, etc.
    
    public void setId(Long id){
    	this.id = id;
    }
    
    public Long getId(){
    	return id;
    }
    
    public void setFirstname(String firstname){
    	this.firstname = firstname;
    }
    
    public String getFirstname(){
    	return firstname;
    }
    
    public void setLastname(String lastname){
    	this.lastname = lastname;
    }
    
    public String getLastname(){
    	return lastname;
    }
    
    public void setEmail(String email){
    	this.email = email;
    }
    
    public String getEmail(){
    	return email;
    }
    
    public void setDescription(String description){
    	this.description = description;
    }
    
    public String getDescription(){
    	return description;
    }
    
    public void setAddress(Address address){
    	this.address = address;
    }
    
    public Address getAddress(){
    	return address;
    }
    
    public void setSponsor(Sponsor sponsor){
    	this.sponsor = sponsor;
    }
    
    public Sponsor getSponsor(){
    	return sponsor;
    }
    
    public void setOpponents(List<Player> opponents){
    	this.opponents = opponents;
    }
    
    public List<Player> getOpponents(){
    	return opponents;
    }
}