package com.accenture.banco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.banco.entity.ContaSolidaria;
import com.accenture.banco.repository.ContaSolidariaRepo;

@Service
public class ContaSolidariaService {
	
	@Autowired
	private ContaSolidariaRepo contaSolidariaRepo;
	
	public List<ContaSolidaria> listarTodasContasSolidarias(){
		return (List<ContaSolidaria>) contaSolidariaRepo.findAll();
	}
	
	public ContaSolidaria salvar(ContaSolidaria contaSolidaria) {
		return contaSolidariaRepo.save(contaSolidaria);
	}
}
