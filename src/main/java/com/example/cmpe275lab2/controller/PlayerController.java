package com.example.cmpe275lab2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cmpe275lab2.model.Player;
import com.example.cmpe275lab2.model.Sponsor;
import com.example.cmpe275lab2.repository.PlayerRepository;
import com.example.cmpe275lab2.repository.SponsorRepository;

import javax.validation.Valid;
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
	public Player createPlayer(@Valid @RequestBody Player player) {
		if(player.getSponsor() != null){
		    Sponsor sponsor = sponsorRepository.findOne(player.getSponsor().getId());
		    if(sponsor != null){
			    player.setSponsor(sponsor);
		    }
		}
	    return playerRepository.save(player);
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
	                                       @Valid @RequestBody Player playerDetails) {
	    Player player = playerRepository.findOne(playerId);
	    if(player == null) {
	        return ResponseEntity.notFound().build();
	    }
	    player.setFirstname(playerDetails.getFirstname());
	    player.setLastname(playerDetails.getLastname());
	    player.setEmail(playerDetails.getEmail());
	    player.setDescription(playerDetails.getDescription());
	    player.setAddress(playerDetails.getAddress());
	   
	    if(playerDetails.getSponsor() != null){
		    Sponsor sponsor = sponsorRepository.findOne(playerDetails.getSponsor().getId());
		    if(sponsor != null){
			    player.setSponsor(sponsor);
		    }
		}
	    else
	    	player.setSponsor(playerDetails.getSponsor());
	    
	    Player updatedPlayer = playerRepository.save(player);
	    return ResponseEntity.ok(updatedPlayer);
	}

    // Delete a Player
	@DeleteMapping("/player/{id}")
    public ResponseEntity<Player> deleteNote(@PathVariable(value = "id") Long playerId) {
        Player player = playerRepository.findOne(playerId);
        if(player == null) {
            return ResponseEntity.notFound().build();
        }

        playerRepository.delete(player);
        return ResponseEntity.ok().body(player);
    }

}
