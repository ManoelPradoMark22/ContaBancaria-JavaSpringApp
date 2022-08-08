package com.accenture.banco.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//@Entity
public class Extrato {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idExtrato;
	
	private String operacao;
	private Date dataHoraMovimento;
	private double valorOperacao;
	
	@ManyToOne
	@JoinColumn(name = "idContaCorrente")
	private ContaCorrente contaCorrente;
}
