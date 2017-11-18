package com.example.cmpe275lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cmpe275lab2.model.Sponsor;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Long> {

}
