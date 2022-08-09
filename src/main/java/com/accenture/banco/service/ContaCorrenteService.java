package com.accenture.banco.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import com.accenture.banco.entity.Cliente;
import com.accenture.banco.entity.ContaCorrente;
import com.accenture.banco.entity.Extrato;
import com.accenture.banco.repository.ContaCorrenteRepo;
import com.accenture.banco.repository.ExtratoRepo;
import com.accenture.banco.util.Valor;

@Service
public class ContaCorrenteService {
	
	@Autowired
	private ContaCorrenteRepo contaCorrenteRepo;
	
	@Autowired
	private ExtratoService extratoService;
	
	public List<ContaCorrente> listaTodasContas(){
		return (List<ContaCorrente>) contaCorrenteRepo.findAll();
	}
	
	public Optional<List<ContaCorrente>> buscarContasPorCliente(Cliente cliente) throws ObjectNotFoundException{
		Optional<List<ContaCorrente>> contaCorrente = contaCorrenteRepo.findAllByCliente(cliente);
		return Optional.ofNullable(contaCorrente).orElseThrow(() -> new ObjectNotFoundException(null, "Contas não encontradas para o cpf indicado!"));
	}
	
	public ContaCorrente salvar(ContaCorrente contacorrente) {
		return contaCorrenteRepo.save(contacorrente);
	}
	
	public ContaCorrente buscarContaPorId(Integer id) throws ObjectNotFoundException{
		Optional<ContaCorrente> contaCorrente = contaCorrenteRepo.findById(id);
		return contaCorrente.orElseThrow(() -> new ObjectNotFoundException(null, "Conta não encontrada!"));
	}
	
	public Boolean deposito(Valor objBody){
		try {
			ContaCorrente contaCorrente = buscarContaPorId(objBody.getId());
			contaCorrente.setContaCorrenteSaldo(contaCorrente.getContaCorrenteSaldo() + objBody.getValor());
			salvar(contaCorrente);
			
			extratoService.gerarExtrato(contaCorrente, "deposito", objBody.getValor());
			
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public Boolean transferencia(Valor objBody, int idDestino) {
		ContaCorrente contaCorrenteOrigem = buscarContaPorId(objBody.getId());
		ContaCorrente contaCorrenteDestino = buscarContaPorId(idDestino);
		double valorEmContaOrigem = contaCorrenteOrigem.getContaCorrenteSaldo();
		double valorTransf = objBody.getValor();
		if(valorEmContaOrigem<valorTransf) {
			return false;
		}else {
			contaCorrenteDestino.setContaCorrenteSaldo(contaCorrenteDestino.getContaCorrenteSaldo() + valorTransf);
			salvar(contaCorrenteDestino);
			contaCorrenteOrigem.setContaCorrenteSaldo(valorEmContaOrigem - valorTransf);
			salvar(contaCorrenteOrigem);
			
			extratoService.gerarExtrato(contaCorrenteOrigem, "transferencia", valorTransf);
			
			return true;
		}
	}
	
	public Boolean saque(Valor objBody) {
		ContaCorrente contaCorrente = buscarContaPorId(objBody.getId());
		double valorEmConta = contaCorrente.getContaCorrenteSaldo();
		double valorSaque = objBody.getValor();
		if(valorEmConta<valorSaque) {
			return false;
		}else {
			contaCorrente.setContaCorrenteSaldo(valorEmConta - valorSaque);
			salvar(contaCorrente);
			extratoService.gerarExtrato(contaCorrente, "saque", valorSaque);
			return true;
		}
	}
}
