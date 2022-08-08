package com.accenture.banco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.banco.entity.Extrato;
import com.accenture.banco.repository.ExtratoRepo;

@Service
public class ExtratoService {
	
	@Autowired
	private ExtratoRepo extratoRepo;
	
	public List<Extrato> listaTodosExtratos(){
		return (List<Extrato>) extratoRepo.findAll();
	}
	
	public Extrato salvar(Extrato extrato) {
		return extratoRepo.save(extrato);
	}
}
