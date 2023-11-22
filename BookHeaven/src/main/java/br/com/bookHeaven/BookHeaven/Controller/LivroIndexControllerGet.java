package br.com.bookHeaven.BookHeaven.Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.bookHeaven.BookHeaven.Model.Cliente;
import br.com.bookHeaven.BookHeaven.Model.Livro;
import br.com.bookHeaven.BookHeaven.Persistence.ClienteDao;
import br.com.bookHeaven.BookHeaven.Persistence.LivroDao;

@Controller
public class LivroIndexControllerGet {
	@Autowired
	private LivroDao ldao;
	
	@Autowired
	private ClienteDao cdao;
	
	private List<Livro> livrosMaisVendidos = new ArrayList<>();
	private List<Livro> livrosLancamentos = new ArrayList<>();
	private List<Cliente> clientes = new ArrayList<>();
	
	@RequestMapping(name = "indexget", value = "/indexget", method = RequestMethod.GET)
	public ModelAndView get(ModelMap model) {
		if (!livrosMaisVendidos.isEmpty()) { livrosMaisVendidos.removeAll(livrosMaisVendidos); }
		if (!livrosLancamentos.isEmpty()) { livrosLancamentos.removeAll(livrosLancamentos); }
		
		if (!clientes.isEmpty()) {
			clientes.removeAll(clientes);
		}
		
		try {
			clientes.addAll(cdao.listar());
			livrosMaisVendidos.addAll(maisVendidos());
			livrosLancamentos.addAll(lancamentos());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("maisVendidos", livrosMaisVendidos);
		model.addAttribute("lancamentos", livrosLancamentos);
		
		return new ModelAndView("indexget");
	}
	
	private List<Livro> maisVendidos() throws ClassNotFoundException, SQLException {
		return ldao.listarMaisVendidos();
	}
	
	private List<Livro> lancamentos() throws ClassNotFoundException, SQLException {
		return ldao.listarLancamentos();
	}

}