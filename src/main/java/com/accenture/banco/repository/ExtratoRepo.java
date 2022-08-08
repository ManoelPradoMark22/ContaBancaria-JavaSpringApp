package com.accenture.banco.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.accenture.banco.entity.Extrato;

@Repository
public interface ExtratoRepo extends CrudRepository<Extrato, Integer>{
	
}
