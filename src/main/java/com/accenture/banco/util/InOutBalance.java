package com.accenture.banco.util;

import java.util.List;

import com.accenture.banco.entity.ContaCorrente;

public class InOutBalance {
	double valorTotal;
	double entradas;
	double saidas;
	
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
