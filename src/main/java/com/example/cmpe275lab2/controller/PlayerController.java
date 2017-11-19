package com.example.cmpe275lab2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cmpe275lab2.model.Address;
import com.example.cmpe275lab2.model.Player;
import com.example.cmpe275lab2.model.Sponsor;
import com.example.cmpe275lab2.repository.PlayerRepository;
import com.example.cmpe275lab2.repository.SponsorRepository;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PlayerController {
	
	@Autowired
    PlayerRepository playerRepository;
	
	@Autowired
	SponsorRepository sponsorRepository;

	// Get All Players
	@GetMapping("/players")
	public List<Player> getAllPlayers() {
	    return playerRepository.findAll();
	}
	
    // Create a new Player
	@PostMapping("/player")
	public ResponseEntity<Player> createPlayer( 
			@RequestParam(value="firstname", required=true) String firstname,
			@RequestParam(value="lastname", required=true) String lastname,
			@RequestParam(value="email", required=true) String email,
			@RequestParam(value="description", required=false) String description,
			@RequestParam(value="street", required=false) String street,
			@RequestParam(value="city", required=false) String city,
			@RequestParam(value="state", required=false) String state,
			@RequestParam(value="zip", required=false) String zip,
			@RequestParam(value="sponsor", required=false) Long sponsor_id) 
	{

		Player player = new Player();
		
		// check duplicate email for new player
		List<Player> playerList = playerRepository.findAll();
		for(int i = 0; i < playerList.size(); i++){
			Player temp = playerList.get(i);
			if(temp.getEmail().equals(email))
				return ResponseEntity.badRequest().build();
		}
		
		
		//System.out.print(player.getId());
		if(sponsor_id != null){
		    Sponsor sponsor = sponsorRepository.findOne(sponsor_id);
		    if(sponsor != null){
			    player.setSponsor(sponsor);
		    }
		    else
		        return ResponseEntity.badRequest().build();
		}
		
		Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		
		player.setFirstname(firstname);
		player.setLastname(lastname);
		player.setEmail(email);
		player.setAddress(address);
		player.setDescription(description);
		
		
	    Player newplayer = playerRepository.save(player);
	    return ResponseEntity.ok(newplayer);
	}

    // Get a Player
	@GetMapping("/player/{id}")
	public ResponseEntity<Player> getPlayerById(@PathVariable(value = "id") Long playerId) {
	    Player player = playerRepository.findOne(playerId);
	    if(player == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(player);
	}

    // Update a Player
	@PutMapping("/player/{id}")
	public ResponseEntity<Player> updatePlayer(@PathVariable(value = "id") Long playerId,                                     
	        @RequestParam(value="firstname", required=true) String firstname,
	        @RequestParam(value="lastname", required=true) String lastname,
	        @RequestParam(value="email", required=true) String email,
	        @RequestParam(value="description", required=false) String description,
	        @RequestParam(value="street", required=false) String street,
	        @RequestParam(value="city", required=false) String city,
	        @RequestParam(value="state", required=false) String state,
	        @RequestParam(value="zip", required=false) String zip,
	        @RequestParam(value="sponsor", required=false) Long sponsor_id) 
	{
	    Player player = playerRepository.findOne(playerId);
	    if(player == null) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    // check duplicate email for updated player
	 	List<Player> playerList = playerRepository.findAll();
	 	for(int i = 0; i < playerList.size(); i++){
	 		Player temp = playerList.get(i);
	 		if(temp.getEmail().equals(email) && !player.getEmail().equals(email))
	 			return ResponseEntity.badRequest().build();
	 	}
	    
	    if(sponsor_id != null){
	    	//System.out.println(sponsor_id);
	    	
	        Sponsor sponsor = sponsorRepository.findOne(sponsor_id);
		    if(sponsor != null){
			    player.setSponsor(sponsor);
			    //System.out.println("!!!!!!!!!!!!");
		    }
		    else
			    return ResponseEntity.badRequest().build();
	    }
	    else
	    	player.setSponsor(null);
	    
	    Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		
	    player.setFirstname(firstname);
		player.setLastname(lastname);
		player.setEmail(email);
		player.setAddress(address);
		player.setDescription(description);	
		
	    
	    Player updatedPlayer = playerRepository.save(player);
	    return ResponseEntity.ok(updatedPlayer);
	}

    // Delete a Player
	@DeleteMapping("/player/{id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable(value = "id") Long playerId) {
        Player player = playerRepository.findOne(playerId);
        if(player == null) {
            return ResponseEntity.notFound().build();
        }
        
        List<Player> opponentList1 = player.getOpponents();
        if(!opponentList1.isEmpty()){
        	for(int i = 0; i < opponentList1.size(); i++){
        		Player temp = playerRepository.findOne(opponentList1.get(i).getId());
        		List<Player> opponentList2 = temp.getOpponents();
        		opponentList2.remove(player);
        		temp.setOpponents(opponentList2);
        		playerRepository.save(temp);
        	}
        }
        
        player.setOpponents(new ArrayList<Player>());

        playerRepository.delete(player);
        return ResponseEntity.ok().body(player);
    }

}
