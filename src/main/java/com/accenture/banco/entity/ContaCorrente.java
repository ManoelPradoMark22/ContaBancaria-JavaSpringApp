package com.accenture.banco.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//@Entity
public class ContaCorrente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idContaCorrente;
	
	private double contaCorrenteSaldo;
	
	@ManyToOne
	@JoinColumn(name = "idCliente")
	private Cliente cliente;
}
