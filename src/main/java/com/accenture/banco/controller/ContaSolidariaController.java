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

import com.accenture.banco.entity.ContaSolidaria;
import com.accenture.banco.service.ContaSolidariaService;

@RestController
@RequestMapping("/contasolidaria")
public class ContaSolidariaController {
	
	@Autowired
	private ContaSolidariaService contaSolidariaService;
	
	//Listar Contas Solidarias
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ContaSolidaria>> listarContasSolidarias(){
		List<ContaSolidaria> contasSolidarias = contaSolidariaService.listarTodasContasSolidarias();
		//se a requisicao for ok() 200 - entao retornamos alunos no body
		return ResponseEntity.ok().body(contasSolidarias);
	}
	
	//Cadastrar Conta Solidaria
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> inserirContaSolidaria(@RequestBody ContaSolidaria objContaSolidaria) {
		try {
			ContaSolidaria contaSolidaria = contaSolidariaService.salvar(objContaSolidaria);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(contaSolidaria.getIdContaSolidaria()).toUri();
			return ResponseEntity.created(uri).build();
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	/*
	@RequestMapping(method =  RequestMethod.POST)
    public Agencia Post(@Validated @RequestBody Agencia agencia)
    {
        return agenciaService.salvar(agencia);
    }
	*/
	
}