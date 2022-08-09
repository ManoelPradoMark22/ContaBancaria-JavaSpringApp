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
import com.accenture.banco.service.ClienteService;
import com.accenture.banco.service.ContaCorrenteService;
import com.accenture.banco.util.Valor;

@RestController
@RequestMapping("/conta")
public class ContaCorrenteController {
	
	@Autowired
	private ContaCorrenteService contaCorrenteService;
	
	@Autowired
	private ClienteService clienteService;
	
	//Listar Contas Correntes
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ContaCorrente>> listarContasCorrentes(){
		List<ContaCorrente> contascorrentes = contaCorrenteService.listaTodasContas();
		//se a requisicao for ok() 200 - entao retornamos alunos no body
		return ResponseEntity.ok().body(contascorrentes);
	}	
	
	//Pesquisar Contas pelo cpf do cliente
	@RequestMapping(value="/{cpf}", method = RequestMethod.GET)
	public ResponseEntity<Optional> buscaPorCpf(@PathVariable String cpf) throws ObjectNotFoundException{
		try {
			Cliente cliente = clienteService.buscarClientePorCpf(cpf);
			Optional<List<ContaCorrente>> contasCorrentes = contaCorrenteService.buscarContasPorCpf(cliente);
			return ResponseEntity.ok().body(Optional.ofNullable(contasCorrentes));
		}catch(ObjectNotFoundException e){
			return ResponseEntity.badRequest().body(Optional.ofNullable(e.getMessage()));
		}catch(Exception e){
			return ResponseEntity.badRequest().body(Optional.ofNullable(e.getMessage()));
		}
	}
	
	//Cadastrar Conta Corrente
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> inserircliente(@RequestBody ContaCorrente objcontacorrente) {
		ContaCorrente contacorrente = contaCorrenteService.salvar(objcontacorrente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(contacorrente.getIdContaCorrente()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	//deposito
	@RequestMapping(value="/deposito/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody Valor objBody, @PathVariable Integer id) {
		objBody.setId(id);
		contaCorrenteService.deposito(objBody);
		return ResponseEntity.noContent().build();
	}
	
	/*
	@RequestMapping(method =  RequestMethod.POST)
    public Agencia Post(@Validated @RequestBody Agencia agencia)
    {
        return agenciaService.salvar(agencia);
    }
	*/
	
}
