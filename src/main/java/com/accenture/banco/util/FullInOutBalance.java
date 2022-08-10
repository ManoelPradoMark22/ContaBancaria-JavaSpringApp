package com.accenture.banco.util;

import java.util.List;

import com.accenture.banco.entity.ContaCorrente;

public class FullInOutBalance {
	private double valorTotal;
	private double entradas;
	private double saidas;
	private List<ScopeFullInOutBalance> lista;
	
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
	public List<ScopeFullInOutBalance> getLista() {
		return lista;
	}
	public void setLista(List<ScopeFullInOutBalance> lista) {
		this.lista = lista;
	}
}


