package com.accenture.banco.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.accenture.banco.entity.Cliente;

@Repository
public interface ClienteRepo extends CrudRepository<Cliente, Integer>{
	
	boolean existsByClienteCPF(String clienteCPF);
	
	Optional<Cliente> findByClienteCPF(String cpf);
}
