package com.algaworks.cobranca.modal;

public enum StatusTitulo {

	PENDENTE("Pendente"),
	RECEBIDO("Recebido");
	
	private String descricao;
	
	
	
	public String getDescricao() {
		return descricao;
	}

	StatusTitulo(String descricao) {
		this.descricao = descricao;
	}
	
}
   