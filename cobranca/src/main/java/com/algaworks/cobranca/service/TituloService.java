package com.algaworks.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.cobranca.modal.StatusTitulo;
import com.algaworks.cobranca.modal.Titulo;
import com.algaworks.cobranca.repository.Titulos;
import com.algaworks.cobranca.repository.filter.TituloFilter;

@Service
public class TituloService {

	@Autowired
	private Titulos titulos;
	
	public void salvar(Titulo titulo) {
		try {
			titulos.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data invalido.");
		}
	}

	public void excluir(Long codigo) {
		titulos.delete(codigo);		
	}

	public String receber(Long codigo) {
		
		Titulo titulo = titulos.findOne(codigo);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		
		titulos.save(titulo);
		
		//return titulo.getStatus().getDescricao();
		return StatusTitulo.RECEBIDO.getDescricao();
	}

	public List<Titulo> filtrar(TituloFilter filtro) {
		
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		
		return titulos.findByDescricaoContaining(descricao);
	}
	
}
