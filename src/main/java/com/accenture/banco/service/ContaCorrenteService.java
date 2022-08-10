package com.accenture.banco.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

import com.accenture.banco.entity.Cliente;
import com.accenture.banco.entity.ContaCorrente;
import com.accenture.banco.entity.Extrato;
import com.accenture.banco.repository.ContaCorrenteRepo;
import com.accenture.banco.repository.ExtratoRepo;
import com.accenture.banco.util.FullBalance;
import com.accenture.banco.util.FullInOutBalance;
import com.accenture.banco.util.InOutBalance;
import com.accenture.banco.util.ScopeFullInOutBalance;
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
	
	public double formatCasasDecimais(double numero) {
	       return (Math.round(numero*100.0)/100.0);
	}
	
	public double getBalance(List<ContaCorrente> lista) {
		double saldo=0;
		for(ContaCorrente conta : lista) {
			saldo += conta.getContaCorrenteSaldo();
		}
		return formatCasasDecimais(saldo);
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
		fullbal.setValorTotal(formatCasasDecimais(saldo));
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
				if(extrato.getOperacao().equals("deposito") || extrato.getOperacao().equals("transferenciaEntrada")) {
					entradas += extrato.getValorOperacao();
				}else {
					saidas += extrato.getValorOperacao();
				}
			}
		}
		
		InOutBalance balance = new InOutBalance();
		balance.setValorTotal(formatCasasDecimais(saldo));
		balance.setEntradas(formatCasasDecimais(entradas));
		balance.setSaidas(formatCasasDecimais(saidas));
		
		return balance;
	}
	
	public FullInOutBalance getFullInOutBalance(List<ContaCorrente> lista) {
		double saldo=0;
		double entradas = 0;
		double saidas = 0;
		
		List<ScopeFullInOutBalance> inOutAccountList = new ArrayList<ScopeFullInOutBalance>();
		
		for(ContaCorrente conta : lista) {
			saldo += conta.getContaCorrenteSaldo();
			
			List<Extrato> extratos = extratoService.buscarExtratosPorConta(conta);
			
			ScopeFullInOutBalance inOutAccount = new ScopeFullInOutBalance();
			inOutAccount.setIdAccount(conta.getIdContaCorrente());
			
			for(Extrato extrato : extratos) {				
				if(extrato.getOperacao().equals("deposito") || extrato.getOperacao().equals("transferenciaEntrada")) {
					entradas += extrato.getValorOperacao();
					inOutAccount.setEntradas(formatCasasDecimais(inOutAccount.getEntradas() + extrato.getValorOperacao()));
				}else {
					saidas += extrato.getValorOperacao();
					inOutAccount.setSaidas(formatCasasDecimais(inOutAccount.getSaidas() + extrato.getValorOperacao()));
				}
			}
			inOutAccount.setValorTotal(formatCasasDecimais(inOutAccount.getEntradas() - inOutAccount.getSaidas()));
			inOutAccountList.add(inOutAccount);
		}
		
		FullInOutBalance balance = new FullInOutBalance();
		balance.setValorTotal(formatCasasDecimais(saldo));
		balance.setEntradas(formatCasasDecimais(entradas));
		balance.setSaidas(formatCasasDecimais(saidas));
		balance.setLista(inOutAccountList);;
		
		return balance;
	}
	
	public ResponseEntity<String> deposito(Valor objBody){
		try {
			ContaCorrente contaCorrente = buscarContaPorId(objBody.getId());
			double valorDepositado = objBody.getValor();
			double novoSaldo = contaCorrente.getContaCorrenteSaldo() + valorDepositado;
			contaCorrente.setContaCorrenteSaldo(novoSaldo);
			salvar(contaCorrente);
			
			extratoService.gerarExtrato(contaCorrente, "deposito", valorDepositado);
			return ResponseEntity.ok().body("Depósito de 'R$" + valorDepositado +"' efetuado com sucesso! Novo saldo: 'R$" + formatCasasDecimais(novoSaldo) +"'.");
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	public ResponseEntity<String> transferencia(Valor objBody, int idDestino) {		
		try {
			ContaCorrente contaCorrenteOrigem = buscarContaPorId(objBody.getId());
			ContaCorrente contaCorrenteDestino = buscarContaPorId(idDestino);
			double valorEmContaOrigem = contaCorrenteOrigem.getContaCorrenteSaldo();
			double valorTransf = objBody.getValor();
			if(valorEmContaOrigem<valorTransf) {
				return ResponseEntity.badRequest().body("Saldo insuficiente para a transferência! Seu saldo é: R$" + valorEmContaOrigem);
			}else {
				contaCorrenteDestino.setContaCorrenteSaldo(contaCorrenteDestino.getContaCorrenteSaldo() + valorTransf);
				salvar(contaCorrenteDestino);
				double novoSaldo = valorEmContaOrigem - valorTransf;
				contaCorrenteOrigem.setContaCorrenteSaldo(novoSaldo);
				salvar(contaCorrenteOrigem);
				
				extratoService.gerarExtrato(contaCorrenteOrigem, "transferenciaSaida", valorTransf);
				extratoService.gerarExtrato(contaCorrenteDestino, "transferenciaEntrada", valorTransf);
				
				return ResponseEntity.ok().body("Tranferência de 'R$" + valorTransf +"' efetuada com sucesso! Novo saldo: 'R$" + formatCasasDecimais(novoSaldo) +"'.");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	public ResponseEntity<String> saque(Valor objBody) {
		try {
			ContaCorrente contaCorrente = buscarContaPorId(objBody.getId());
			double valorEmConta = contaCorrente.getContaCorrenteSaldo();
			double valorSaque = objBody.getValor();
			if(valorEmConta<valorSaque) {
				return ResponseEntity.badRequest().body("Saldo insuficiente para o saque! Seu saldo é: R$" + valorEmConta);
			}else {
				double novoSaldo = valorEmConta - valorSaque;
				contaCorrente.setContaCorrenteSaldo(novoSaldo);
				salvar(contaCorrente);
				extratoService.gerarExtrato(contaCorrente, "saque", valorSaque);
				return ResponseEntity.ok().body("Saque de 'R$" + valorSaque +"' efetuado com sucesso! Novo saldo: 'R$" + formatCasasDecimais(novoSaldo) +"'.");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
