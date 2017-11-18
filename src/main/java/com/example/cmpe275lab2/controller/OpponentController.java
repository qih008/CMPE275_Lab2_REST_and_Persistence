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
public class OpponentController {
	
	@Autowired
    PlayerRepository playerRepository;
	
	// Add an opponent
	@PutMapping("/opponents/{id1}/{id2}")
	public ResponseEntity<Player> addOpponentById(@PathVariable(value = "id1") Long playerId1,
			                                    @PathVariable(value = "id2") Long playerId2) {
		
		Player player1 = playerRepository.findOne(playerId1);
		Player player2 = playerRepository.findOne(playerId2);
		if(player1 == null || player2 == null) {
	        return ResponseEntity.notFound().build();
	    }
		
		List<Player> opponentList1 = player1.getOpponents();
		List<Player> opponentList2 = player2.getOpponents();
		if(opponentList1.contains(player2))
			return ResponseEntity.ok().build();
		else{
			opponentList1.add(player2);
			opponentList2.add(player1);
		}
		player1.setOpponents(opponentList1);
		player2.setOpponents(opponentList2);
		Player updatedPlayer1 = playerRepository.save(player1);
		Player updatedPlayer2 = playerRepository.save(player2);
		
		return ResponseEntity.ok().body(updatedPlayer1);
	}
	
	
	// Add an opponent
	@DeleteMapping("/opponents/{id1}/{id2}")
	public ResponseEntity<Player> deleteOpponentById(@PathVariable(value = "id1") Long playerId1,
			                                    @PathVariable(value = "id2") Long playerId2) {
			
		Player player1 = playerRepository.findOne(playerId1);
		Player player2 = playerRepository.findOne(playerId2);
		if(player1 == null || player2 == null) {
	        return ResponseEntity.notFound().build();
	    }
		
		List<Player> opponentList1 = player1.getOpponents();
		List<Player> opponentList2 = player2.getOpponents();
		if(opponentList1.contains(player2)){
			opponentList1.remove(player2);
			opponentList2.remove(player1);
		}
		else{
			return ResponseEntity.notFound().build();
		}
		
		player1.setOpponents(opponentList1);
		player2.setOpponents(opponentList2);
		Player updatedPlayer1 = playerRepository.save(player1);
		Player updatedPlayer2 = playerRepository.save(player2);
		
		return ResponseEntity.ok().body(updatedPlayer1);
	}	
}
