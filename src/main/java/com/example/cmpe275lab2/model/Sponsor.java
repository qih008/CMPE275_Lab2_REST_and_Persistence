package com.example.cmpe275lab2.model;

import org.hibernate.validator.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sponsor")
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value = {"players"}, allowGetters = true)
public class Sponsor implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)       // auto generate key ID
    private Long id;
	
	@NotBlank
    private String name;                                 // require element, can't be null or blank
	
    private String description;
    
    @Embedded
    private Address address = new Address();             // embedded from address class
    
    @OneToMany(mappedBy = "sponsor")
    @JsonIgnore
    private List<Player> players = new ArrayList<>();    // one sponsor can map to many player
    
    // constructors, setters, getters, etc.
    
    public void setId(Long id){
    	this.id = id;
    }
    
    public Long getId(){
    	return id;
    }
    
    public void setName(String name){
    	this.name = name;
    }
    
    public String getName(){
    	return name;
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
    
    public void setPlayers(List<Player> players){
    	this.players = players;
    }
    
    public List<Player> getPlayers(){
    	return players;
    }
}