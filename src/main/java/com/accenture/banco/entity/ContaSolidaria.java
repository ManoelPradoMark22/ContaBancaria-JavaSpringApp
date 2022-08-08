package com.accenture.banco.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ContaSolidaria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idContaSolidaria;
	
	@Column(nullable = false)
	private String nomeCausa;
	@Column(nullable = false)
	private String descricaoCausa;
	
	@ManyToOne
	@JoinColumn(name = "idContaCorrente")
	private ContaCorrente contaCorrente;

	public Integer getIdContaSolidaria() {
		return idContaSolidaria;
	}

	public void setIdContaSolidaria(Integer idContaSolidaria) {
		this.idContaSolidaria = idContaSolidaria;
	}

	public String getNomeCausa() {
		return nomeCausa;
	}

	public void setNomeCausa(String nomeCausa) {
		this.nomeCausa = nomeCausa;
	}

	public String getDescricaoCausa() {
		return descricaoCausa;
	}

	public void setDescricaoCausa(String descricaoCausa) {
		this.descricaoCausa = descricaoCausa;
	}

	public ContaCorrente getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(ContaCorrente contaCorrente) {
		this.contaCorrente = contaCorrente;
	}
}
