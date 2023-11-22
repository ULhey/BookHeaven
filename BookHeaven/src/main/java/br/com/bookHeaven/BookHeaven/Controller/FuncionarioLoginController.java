package br.com.bookHeaven.BookHeaven.Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import br.com.bookHeaven.BookHeaven.Model.Funcionario;
import br.com.bookHeaven.BookHeaven.Persistence.FuncionarioDao;

@Controller
public class FuncionarioLoginController {
	@Autowired
	private FuncionarioDao fdao;	
	
	private List<Funcionario> funcionarios = new ArrayList<>();
	
	@RequestMapping(name = "loginfuncionario", value = "/loginfuncionario", method = RequestMethod.GET)
	public ModelAndView get() {
		if (!funcionarios.isEmpty()) { funcionarios.removeAll(funcionarios); }
		
		try {
			funcionarios.addAll(fdao.listar());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("loginfuncionarioget");
	}
	
	@RequestMapping(name = "loginfuncionario", value = "/loginfuncionario", method = RequestMethod.POST)
	public RedirectView post(@RequestParam Map<String, String> param, RedirectAttributes model) {
		String login = param.get("login");
		String senha = param.get("senha");
		
		String dir = "";
		
		try {
			switch (param.get("botao")) {
			case"Entrar":
				if (validaLogin(login, senha) == true) {
					dir = "cadastrolivro";
				} else {
					model.addFlashAttribute("erro", "Login não Cadastrado");
					dir = "loginfuncionario";
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new RedirectView(dir);
	}
	
	private boolean validaLogin(String login, String senha) {
		for (Funcionario f : funcionarios) {
			if (f.getNome().equals(login) && f.getSenha().equals(senha)) {
				return true;
			}
		}
		return false;
	}
}