package br.com.bookHeaven.BookHeaven.Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.bookHeaven.BookHeaven.Model.Funcionario;
import br.com.bookHeaven.BookHeaven.Persistence.FuncionarioDao;

@Controller
public class FuncionarioLoginControllerGet {
	@Autowired
	private FuncionarioDao fdao;	
	
	private List<Funcionario> funcionarios = new ArrayList<>();
	
	@RequestMapping(name = "loginfuncionarioget", value = "/loginfuncionarioget", method = RequestMethod.GET)
	public ModelAndView get() {
		if (!funcionarios.isEmpty()) { funcionarios.removeAll(funcionarios); }
		
		try {
			funcionarios.addAll(fdao.listar());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("loginfuncionarioget");
	}
}