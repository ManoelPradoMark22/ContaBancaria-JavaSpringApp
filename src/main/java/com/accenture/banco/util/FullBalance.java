package com.accenture.banco.util;

import java.util.List;

import com.accenture.banco.entity.ContaCorrente;

public class FullBalance {
	private double valorTotal;
	private List<ContaCorrente> lista;
	
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public List<ContaCorrente> getLista() {
		return lista;
	}
	public void setLista(List<ContaCorrente> lista) {
		this.lista = lista;
	}	
}
