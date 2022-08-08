package com.accenture.banco.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//@Entity
public class ContaSolidaria {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idContaSolidaria;
	
	private String nomeCausa;
	private String descricaoCausa;
	
	@ManyToOne
	@JoinColumn(name = "idContaCorrente")
	private ContaCorrente contaCorrente;
}
