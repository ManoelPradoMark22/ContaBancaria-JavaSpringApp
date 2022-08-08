package com.accenture.banco.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.accenture.banco.entity.ContaSolidaria;

@Repository
public interface ContaSolidariaRepo extends CrudRepository<ContaSolidaria, Integer>{
	
}
