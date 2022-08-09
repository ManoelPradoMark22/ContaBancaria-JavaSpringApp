package com.accenture.banco.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.banco.entity.Cliente;
import com.accenture.banco.entity.ContaCorrente;

@Repository
public interface ContaCorrenteRepo extends JpaRepository<ContaCorrente, Integer>{
	
	List<ContaCorrente> findAllByCliente(Cliente cliente);
	 //List<ContaCorrente> findByCliente(Cliente cliente);
}