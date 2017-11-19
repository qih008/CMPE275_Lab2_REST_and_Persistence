package com.example.cmpe275lab2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<Player> createPlayer(@Valid @RequestBody Player player) {
		
		// check duplicate email for new player
		List<Player> playerList = playerRepository.findAll();
		for(int i = 0; i < playerList.size(); i++){
			Player temp = playerList.get(i);
			if(temp.getEmail().equals(player.getEmail()))
				return ResponseEntity.badRequest().build();
		}
		
		if(player.getSponsor() != null){
		    Sponsor sponsor = sponsorRepository.findOne(player.getSponsor().getId());
		    if(sponsor != null){
			    player.setSponsor(sponsor);
		    }
		    else
		    	return ResponseEntity.badRequest().build();
		}
		
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
