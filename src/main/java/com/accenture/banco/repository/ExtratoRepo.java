package com.accenture.banco.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.accenture.banco.entity.Cliente;
import com.accenture.banco.entity.ContaCorrente;
import com.accenture.banco.entity.Extrato;

@Repository
public interface ExtratoRepo extends CrudRepository<Extrato, Integer>{
	List<Extrato> findAllByContaCorrente(ContaCorrente contaCorrente);
}
