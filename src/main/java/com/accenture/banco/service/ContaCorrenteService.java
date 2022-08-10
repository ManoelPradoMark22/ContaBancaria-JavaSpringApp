package com.accenture.banco.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

import com.accenture.banco.entity.Cliente;
import com.accenture.banco.entity.ContaCorrente;
import com.accenture.banco.entity.Extrato;
import com.accenture.banco.repository.ContaCorrenteRepo;
import com.accenture.banco.repository.ExtratoRepo;
import com.accenture.banco.util.FullBalance;
import com.accenture.banco.util.InOutBalance;
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
	
	public List<ContaCorrente> buscarContasPorCliente(Cliente cliente) throws ObjectNotFoundException{
		List<ContaCorrente> contaCorrente = contaCorrenteRepo.findAllByCliente(cliente);
		return Optional.ofNullable(contaCorrente).orElseThrow(() -> new ObjectNotFoundException(null, "Contas não encontradas para o dado indicado!"));
	}
	
	public ContaCorrente salvar(ContaCorrente contacorrente) {
		return contaCorrenteRepo.save(contacorrente);
	}
	
	public ContaCorrente buscarContaPorId(Integer id) throws ObjectNotFoundException{
		Optional<ContaCorrente> contaCorrente = contaCorrenteRepo.findById(id);
		return contaCorrente.orElseThrow(() -> new ObjectNotFoundException(null, "Conta não encontrada!"));
	}
	
	public double getBalance(List<ContaCorrente> lista) {
		double saldo=0;
		for(ContaCorrente conta : lista) {
			saldo += conta.getContaCorrenteSaldo();
		}
		return saldo;
	}
	
	public FullBalance getFullBalance(List<ContaCorrente> lista) {
		double saldo=0;
		List<ContaCorrente> contaCorr = new ArrayList<ContaCorrente>();
		
		for(ContaCorrente conta : lista) {
			saldo += conta.getContaCorrenteSaldo();
			ContaCorrente c = new ContaCorrente();
			c.setContaCorrenteSaldo(conta.getContaCorrenteSaldo());
			c.setIdContaCorrente(conta.getIdContaCorrente());
			contaCorr.add(c);
		}
		
		FullBalance fullbal = new FullBalance();
		fullbal.setValorTotal(saldo);
		fullbal.setLista(contaCorr);
		
		
		return fullbal;
	}
	
	public InOutBalance getInOutBalance(List<ContaCorrente> lista) {
		double saldo=0;
		double entradas = 0;
		double saidas = 0;
		
		for(ContaCorrente conta : lista) {
			saldo += conta.getContaCorrenteSaldo();
			
			List<Extrato> extratos = extratoService.buscarExtratosPorConta(conta);
			for(Extrato extrato : extratos) {
				if(extrato.getOperacao().equals("deposito")) {
					entradas += extrato.getValorOperacao();
				}else {
					saidas += extrato.getValorOperacao();
				}
			}
		}
		
		InOutBalance balance = new InOutBalance();
		balance.setValorTotal(saldo);
		balance.setEntradas(entradas);
		balance.setSaidas(saidas);
		
		return balance;
	}
	
	public String formatarNumero(double numero) {
	       return new DecimalFormat("#,##0.00").format(numero);
	}
	
	public String deposito(Valor objBody){
		try {
			ContaCorrente contaCorrente = buscarContaPorId(objBody.getId());
			double valorDepositado = objBody.getValor();
			double novoSaldo = contaCorrente.getContaCorrenteSaldo() + valorDepositado;
			contaCorrente.setContaCorrenteSaldo(novoSaldo);
			salvar(contaCorrente);
			
			extratoService.gerarExtrato(contaCorrente, "deposito", valorDepositado);
			
			return "Depósito de 'R$" + valorDepositado +"' efetuado com sucesso! Novo saldo: 'R$" + formatarNumero(novoSaldo) +"'.";
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	public String transferencia(Valor objBody, int idDestino) {		
		try {
			ContaCorrente contaCorrenteOrigem = buscarContaPorId(objBody.getId());
			ContaCorrente contaCorrenteDestino = buscarContaPorId(idDestino);
			double valorEmContaOrigem = contaCorrenteOrigem.getContaCorrenteSaldo();
			double valorTransf = objBody.getValor();
			if(valorEmContaOrigem<valorTransf) {
				return "Saldo insuficiente para a transferência! Seu saldo é: R$" + valorEmContaOrigem;
			}else {
				contaCorrenteDestino.setContaCorrenteSaldo(contaCorrenteDestino.getContaCorrenteSaldo() + valorTransf);
				salvar(contaCorrenteDestino);
				double novoSaldo = valorEmContaOrigem - valorTransf;
				contaCorrenteOrigem.setContaCorrenteSaldo(novoSaldo);
				salvar(contaCorrenteOrigem);
				
				extratoService.gerarExtrato(contaCorrenteOrigem, "transferencia", valorTransf);
				
				return "Tranferência de 'R$" + valorTransf +"' efetuada com sucesso! Novo saldo: 'R$" + formatarNumero(novoSaldo) +"'.";
			}
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	public String saque(Valor objBody) {
		try {
			ContaCorrente contaCorrente = buscarContaPorId(objBody.getId());
			double valorEmConta = contaCorrente.getContaCorrenteSaldo();
			double valorSaque = objBody.getValor();
			if(valorEmConta<valorSaque) {
				return "Saldo insuficiente para o saque! Seu saldo é: R$" + valorEmConta;
			}else {
				double novoSaldo = valorEmConta - valorSaque;
				contaCorrente.setContaCorrenteSaldo(novoSaldo);
				salvar(contaCorrente);
				extratoService.gerarExtrato(contaCorrente, "saque", valorSaque);
				return "Saque de 'R$" + valorSaque +"' efetuado com sucesso! Novo saldo: 'R$" + formatarNumero(novoSaldo) +"'.";
			}
		}catch(Exception e) {
			return e.getMessage();
		}
	}
}
