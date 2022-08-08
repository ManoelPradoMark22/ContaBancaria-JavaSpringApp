package com.accenture.banco.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.accenture.banco.entity.Agencia;
import com.accenture.banco.service.AgenciaService;

@RestController
@RequestMapping("/agencia")
public class AgenciaController {
	
	@Autowired
	private AgenciaService agenciaService;
	
	//Listar Agencias
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Agencia>> listarAgencias(){
		List<Agencia> agencias = agenciaService.listaTodasAgencias();
		//se a requisicao for ok() 200 - entao retornamos alunos no body
		return ResponseEntity.ok().body(agencias);
	}	
	
	//Cadastrar agencia
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inseriragencia(@RequestBody Agencia objAgencia) {
		Agencia agencia = agenciaService.salvar(objAgencia);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(agencia.getIdAgencia()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	/*
	@RequestMapping(method =  RequestMethod.POST)
    public Agencia Post(@Validated @RequestBody Agencia agencia)
    {
        return agenciaService.salvar(agencia);
    }
	*/
	
}
