package com.accenture.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.banco.entity.ContaCorrente;

@Repository
public interface ContaCorrenteRepo extends JpaRepository<ContaCorrente, Integer>{

}