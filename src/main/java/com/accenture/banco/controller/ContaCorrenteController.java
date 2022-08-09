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
			Optional<List<ContaCorrente>> contasCorrentes = contaCorrenteService.buscarContasPorCliente(cliente);
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
	public ResponseEntity<String> depositar(@RequestBody Valor objBody, @PathVariable Integer id) throws Exception {
		try {
			objBody.setValor(Math.abs(objBody.getValor()));
			objBody.setId(id);
			contaCorrenteService.deposito(objBody);
			return ResponseEntity.ok().body("Sucesso ao depositar");
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	//transferencia
	@RequestMapping(value="/transferencia/{idOrigem}/{idDestino}", method = RequestMethod.PUT)
	public ResponseEntity<String> depositar(@RequestBody Valor objBody, @PathVariable Integer idOrigem, @PathVariable Integer idDestino) throws Exception {
		try {
			objBody.setValor(Math.abs(objBody.getValor()));
			objBody.setId(idOrigem);
			Boolean result = contaCorrenteService.transferencia(objBody, idDestino);
			return result ? ResponseEntity.ok().body("Transferência Efetuada com sucesso!") : ResponseEntity.ok().body("Saldo insuficiente!");
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	//saque
	@RequestMapping(value="/saque/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> sacar(@RequestBody Valor objBody, @PathVariable Integer id) {
		try{
			objBody.setValor(Math.abs(objBody.getValor()));
			objBody.setId(id);
			Boolean result = contaCorrenteService.saque(objBody);
			return result ? ResponseEntity.ok().body("Saque Efetuado com sucesso!") : ResponseEntity.ok().body("Saldo insuficiente!");
		}catch(NumberFormatException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}catch(Exception e){
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
