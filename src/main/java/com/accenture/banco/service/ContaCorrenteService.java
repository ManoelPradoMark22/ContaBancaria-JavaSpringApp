package com.accenture.banco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.banco.entity.ContaCorrente;
import com.accenture.banco.repository.ContaCorrenteRepo;

@Service
public class ContaCorrenteService {
	
	@Autowired
	private ContaCorrenteRepo contaCorrenteRepo;
	
	public List<ContaCorrente> listaTodasContas(){
		return (List<ContaCorrente>) contaCorrenteRepo.findAll();
	}
	
	public ContaCorrente salvar(ContaCorrente contacorrente) {
		return contaCorrenteRepo.save(contacorrente);
	}
}
