package com.example.cmpe275lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cmpe275lab2.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

}
