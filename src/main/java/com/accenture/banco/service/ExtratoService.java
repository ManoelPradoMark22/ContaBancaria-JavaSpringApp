package com.accenture.banco.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.banco.entity.Cliente;
import com.accenture.banco.entity.ContaCorrente;
import com.accenture.banco.entity.Extrato;
import com.accenture.banco.repository.ExtratoRepo;

@Service
public class ExtratoService {
	
	@Autowired
	private ExtratoRepo extratoRepo;
	
	public List<Extrato> listaTodosExtratos(){
		return (List<Extrato>) extratoRepo.findAll();
	}
	
	public Extrato salvar(Extrato extrato) {
		return extratoRepo.save(extrato);
	}
	
	public void gerarExtrato(ContaCorrente contaCorrente, String operacao, double valor) {
		Extrato extrato = new Extrato();
		extrato.setContaCorrente(contaCorrente);
		extrato.setOperacao(operacao);
		extrato.setValorOperacao(valor);
		salvar(extrato);
	}
	
	public List<Extrato> buscarExtratosPorConta(ContaCorrente contaCorrente) throws ObjectNotFoundException{
		List<Extrato> extratos = extratoRepo.findAllByContaCorrente(contaCorrente);
		return Optional.ofNullable(extratos).orElseThrow(() -> new ObjectNotFoundException(null, "Extratos não encontradas para a conta indicada!"));
	}
}
