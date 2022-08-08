package com.accenture.banco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.banco.entity.Agencia;
import com.accenture.banco.repository.AgenciaRepo;

@Service
public class AgenciaService {
	
	@Autowired
	private AgenciaRepo agenciaRepo;
	
	public List<Agencia> listaTodasAgencias(){
		return (List<Agencia>) agenciaRepo.findAll();
	}
	
	public Agencia salvar(Agencia agencia) {
		return agenciaRepo.save(agencia);
	}
	
}
