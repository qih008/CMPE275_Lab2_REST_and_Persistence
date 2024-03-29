package com.example.cmpe275lab2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cmpe275lab2.model.Address;
import com.example.cmpe275lab2.model.Player;
import com.example.cmpe275lab2.model.Sponsor;
import com.example.cmpe275lab2.repository.SponsorRepository;

import java.util.List;

import javax.validation.Valid;

@RestController
public class SponsorController {
	
	@Autowired
    SponsorRepository sponsorRepository;

	// Get All Sponsors
	@GetMapping("/sponsors")
	public List<Sponsor> getAllSponsors() {
	    return sponsorRepository.findAll();
	}
	
    // Create a new Sponsor
	@PostMapping("/sponsor")
	public Sponsor createSponsor(
			@RequestParam(value="name", required=true) String name,
			@RequestParam(value="description", required=false) String description,
			@RequestParam(value="street", required=false) String street,
			@RequestParam(value="city", required=false) String city,
			@RequestParam(value="state", required=false) String state,
			@RequestParam(value="zip", required=false) String zip) 
	{
		Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		
		Sponsor sponsor = new Sponsor();
		
		sponsor.setName(name);
		sponsor.setAddress(address);
		sponsor.setDescription(description);
		
	    return sponsorRepository.save(sponsor);
	}

    // Get a Sponsor
	@GetMapping("/sponsor/{id}")
	public ResponseEntity<Sponsor> getSponsorById(@PathVariable(value = "id") Long sponsorId) {
		Sponsor sponsor = sponsorRepository.findOne(sponsorId);
	    if(sponsor == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(sponsor);
	}

    // Update a Sponsor
	@PutMapping("/sponsor/{id}")
	public ResponseEntity<Sponsor> updateSponsor(@PathVariable(value = "id") Long sponsorId, 
			@RequestParam(value="name", required=true) String name,
			@RequestParam(value="description", required=false) String description,
			@RequestParam(value="street", required=false) String street,
			@RequestParam(value="city", required=false) String city,
			@RequestParam(value="state", required=false) String state,
			@RequestParam(value="zip", required=false) String zip)
    {
	    Sponsor sponsor = sponsorRepository.findOne(sponsorId);
	    if(sponsor == null) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		
	    sponsor.setName(name);
	    sponsor.setDescription(description);
	    sponsor.setAddress(address);

	    Sponsor updatedSponsor = sponsorRepository.save(sponsor);
	    return ResponseEntity.ok(updatedSponsor);
	}

    // Delete a Sponsor
	@DeleteMapping("/sponsor/{id}")
    public ResponseEntity<Sponsor> deleteSponsor(@PathVariable(value = "id") Long sponsorId) {
        Sponsor sponsor = sponsorRepository.findOne(sponsorId);
        if(sponsor == null) {
            return ResponseEntity.notFound().build();
        }
        
        List<Player> playerList = sponsor.getPlayers();
        //System.out.println(playerList);
        
        if(playerList == null || playerList.size() == 0){
            sponsorRepository.delete(sponsor);
            return ResponseEntity.ok().body(sponsor);
        }
        else
        	return ResponseEntity.badRequest().build();
    }

}
