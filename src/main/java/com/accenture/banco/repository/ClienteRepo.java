package com.accenture.banco.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.accenture.banco.entity.Cliente;
import com.accenture.banco.entity.ContaCorrente;

@Repository
public interface ClienteRepo extends CrudRepository<Cliente, Integer>{
	
	boolean existsByClienteCPF(String clienteCPF);
	boolean existsByIdCliente(int idCliente);
	
	Optional<Cliente> findByClienteCPF(String cpf);
}
