package com.accenture.banco.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.accenture.banco.entity.Cliente;
import com.accenture.banco.entity.ContaCorrente;
import com.accenture.banco.entity.Extrato;
import com.accenture.banco.service.ClienteService;
import com.accenture.banco.service.ContaCorrenteService;
import com.accenture.banco.service.ExtratoService;

@RestController
@RequestMapping("/extrato")
public class ExtratoController {
	
	@Autowired
	private ExtratoService extratoService;
	
	//Listar Extratos
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Extrato>> listarExtratos(){
		List<Extrato> extratos = extratoService.listaTodosExtratos();
		//se a requisicao for ok() 200 - entao retornamos alunos no body
		return ResponseEntity.ok().body(extratos);
	}
	
	//Cadastrar Cliente
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> inserirExtrato(@RequestBody Extrato objextrato) {
		try {
			Extrato extrato = extratoService.salvar(objextrato);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(extrato.getIdExtrato()).toUri();
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
