package com.accenture.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.banco.entity.Agencia;

@Repository
public interface AgenciaRepo extends JpaRepository<Agencia, Integer>{

}