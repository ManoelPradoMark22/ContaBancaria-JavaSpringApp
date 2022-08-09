package com.accenture.banco.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.banco.entity.Cliente;
import com.accenture.banco.entity.ContaCorrente;
import com.accenture.banco.repository.ContaCorrenteRepo;
import com.accenture.banco.util.Valor;

@Service
public class ContaCorrenteService {
	
	@Autowired
	private ContaCorrenteRepo contaCorrenteRepo;
	
	public List<ContaCorrente> listaTodasContas(){
		return (List<ContaCorrente>) contaCorrenteRepo.findAll();
	}
	
	public Optional<List<ContaCorrente>> buscarContasPorCpf(Cliente cliente) throws ObjectNotFoundException{
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
	
	public ContaCorrente deposito(Valor objBody) {
		ContaCorrente contaCorrente = buscarContaPorId(objBody.getId());
		contaCorrente.setContaCorrenteSaldo(contaCorrente.getContaCorrenteSaldo() + objBody.getValor());
		return salvar(contaCorrente); 
	}
}
