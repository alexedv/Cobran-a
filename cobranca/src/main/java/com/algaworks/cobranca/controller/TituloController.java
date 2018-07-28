package com.algaworks.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.cobranca.modal.StatusTitulo;
import com.algaworks.cobranca.modal.Titulo;
import com.algaworks.cobranca.repository.filter.TituloFilter;
import com.algaworks.cobranca.service.TituloService;


@Controller
@RequestMapping("/titulos")
public class TituloController {

	private static final String Cadastro_View = "CadastroTitulo";
	
	
	@Autowired
	private TituloService tituloService;
	
	
	@RequestMapping("/novo")
	public ModelAndView  novo() {
		ModelAndView mv = new ModelAndView(Cadastro_View);
		mv.addObject(new Titulo());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return Cadastro_View;
		}
		try {
			tituloService.salvar(titulo);
			attributes.addFlashAttribute("mensagem", "Título salvo com sucesso!");
			return "redirect:/titulos/novo";
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return Cadastro_View;
		}
	}
	
	
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") TituloFilter filtro) {
		
		
		List<Titulo> todosTitulos = tituloService.filtrar(filtro);
				
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		mv.addObject("titulos", todosTitulos);
		return mv;
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo ) {
				
		ModelAndView mv = new ModelAndView(Cadastro_View);
		mv.addObject(titulo);
		return mv;
	}
	
	
	@RequestMapping(value="{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		tituloService.excluir(codigo);
		attributes.addFlashAttribute("mensagem", "Título excluido com sucesso!");
		return "redirect:/titulos";
	}

	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo) {
		System.out.println();
		return tituloService.receber(codigo);		
	}
		
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulos(){
		return Arrays.asList(StatusTitulo.values());
	}

}
