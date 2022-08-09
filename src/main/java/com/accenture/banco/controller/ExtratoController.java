package com.accenture.banco.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.accenture.banco.entity.Cliente;
import com.accenture.banco.entity.ContaCorrente;
import com.accenture.banco.entity.Extrato;
import com.accenture.banco.service.ContaCorrenteService;
import com.accenture.banco.service.ExtratoService;

@RestController
@RequestMapping("/extrato")
public class ExtratoController {
	
	@Autowired
	private ExtratoService extratoService;
	
	@Autowired
	private ContaCorrenteService contaCorrenteService;
	
	//Listar Extratos
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Extrato>> listarExtratos(){
		List<Extrato> extratos = extratoService.listaTodosExtratos();
		return ResponseEntity.ok().body(extratos);
	}
	
	//Pesquisar extratos pelo id da conta
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Optional> buscaPorCpf(@PathVariable int id) throws ObjectNotFoundException{
		try {
			ContaCorrente contaCorrente = contaCorrenteService.buscarContaPorId(id);
			Optional<List<Extrato>> extratos = extratoService.buscarExtratosPorConta(contaCorrente);
			return ResponseEntity.ok().body(Optional.ofNullable(extratos));
		}catch(ObjectNotFoundException e){
			return ResponseEntity.badRequest().body(Optional.ofNullable(e.getMessage()));
		}catch(Exception e){
			return ResponseEntity.badRequest().body(Optional.ofNullable(e.getMessage()));
		}
	}
	
	/*
	//Cadastrar extrato
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
	*/
	
	/*
	@RequestMapping(method =  RequestMethod.POST)
    public Agencia Post(@Validated @RequestBody Agencia agencia)
    {
        return agenciaService.salvar(agencia);
    }
	*/
	
}
