package com.accenture.banco.util;

import java.util.List;

import com.accenture.banco.entity.ContaCorrente;

public class FullInOutBalance extends InOutBalance{
	private List<ScopeFullInOutBalance> lista;
	
	public List<ScopeFullInOutBalance> getLista() {
		return lista;
	}
	public void setLista(List<ScopeFullInOutBalance> lista) {
		this.lista = lista;
	}
}


