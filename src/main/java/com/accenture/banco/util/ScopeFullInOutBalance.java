package com.accenture.banco.util;

import java.util.List;

import com.accenture.banco.entity.ContaCorrente;

public class ScopeFullInOutBalance {
	private int idAccount;
	private double valorTotal;
	private double entradas;
	private double saidas;
	
	public int getIdAccount() {
		return idAccount;
	}
	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public double getEntradas() {
		return entradas;
	}
	public void setEntradas(double entradas) {
		this.entradas = entradas;
	}
	public double getSaidas() {
		return saidas;
	}
	public void setSaidas(double saidas) {
		this.saidas = saidas;
	}
}


