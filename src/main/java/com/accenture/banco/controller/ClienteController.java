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
import com.accenture.banco.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	//Listar Clientes
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Cliente>> listarClientes(){
		List<Cliente> clientes = clienteService.listaTodosClientes();
		//se a requisicao for ok() 200 - entao retornamos alunos no body
		return ResponseEntity.ok().body(clientes);
	}
	
	//Pesquisar Cliente por CPF
	@RequestMapping(value="/{cpf}", method = RequestMethod.GET)
	public ResponseEntity<Optional> buscaPorID(@PathVariable String cpf) throws ObjectNotFoundException{
		try {
			Cliente cliente = clienteService.buscarClientePorCpf(cpf);
			return ResponseEntity.ok().body(Optional.ofNullable(cliente));
		}catch(ObjectNotFoundException e){
			return ResponseEntity.badRequest().body(Optional.ofNullable(e.getMessage()));
		}catch(Exception e){
			return ResponseEntity.badRequest().body(Optional.ofNullable(e.getMessage()));
		}
	}
	
	//Cadastrar Cliente
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> inserircliente(@RequestBody Cliente objcliente) {
		try {
			if (clienteService.checkExistingCpf(objcliente.getClienteCPF())) throw new Exception("Cpf Já existente!");
			
			if (!clienteService.isValidCpf(objcliente.getClienteCPF())) throw new Exception("Cpf inválido!");
			
			Cliente cliente = clienteService.salvar(objcliente);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getIdCliente()).toUri();
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
