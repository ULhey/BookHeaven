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

import br.com.bookHeaven.BookHeaven.Model.Cliente;
import br.com.bookHeaven.BookHeaven.Model.Livro;
import br.com.bookHeaven.BookHeaven.Persistence.ClienteDao;
import br.com.bookHeaven.BookHeaven.Persistence.LivroDao;
import br.com.bookHeaven.BookHeaven.Persistence.PedidoDao;

@Controller
public class ClienteLoginController {
	@Autowired
	private ClienteDao cdao;
	
	@Autowired
	private LivroDao ldao;
	
	@Autowired
	private PedidoDao pdao;

	private List<Cliente> clientes = new ArrayList<>();

	@RequestMapping(name = "logincliente", value = "/logincliente", method = RequestMethod.GET)
	public ModelAndView get() {
		if (!clientes.isEmpty()) {
			clientes.removeAll(clientes);
		}

		try {
			clientes.addAll(cdao.listar());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return new ModelAndView("logincliente");
	}

	@RequestMapping(name = "logincliente", value = "/logincliente", method = RequestMethod.POST)
	public RedirectView post(@RequestParam Map<String, String> param, RedirectAttributes model) {
		String email = param.get("email");
		String senha = param.get("senha");
		
		String pagAnt = param.get("pagAnt");
		String pesquisa = param.get("pesquisa");
		
		String dir = "";

		try {
			switch (param.get("botao")) {
			case "Entrar":
				String login = validaLogin(email, senha);
				if (!login.equals("")) {
					if (pagAnt.equals("pesquisalivro")) {
						model.addFlashAttribute("tituloPesquisa", pesquisa);
						model.addFlashAttribute("livros", buscarPesquisa(pesquisa));
					} else if (pagAnt.equals("detalhes")) {
						model.addFlashAttribute("tituloPesquisa", pesquisa);
						model.addFlashAttribute("pagAntBt", param.get("pagAntBt"));
						model.addFlashAttribute("detalhes", buscaLivro(param.get("tituloLivro")));
					}
					model.addFlashAttribute("qtdPedido", pdao.qtdPedido(buscarCliente(login)));
					model.addFlashAttribute("login", login);
					dir = pagAnt;
				} else {
					model.addFlashAttribute("pagAnt", pagAnt);
					model.addFlashAttribute("tituloPesquisa", pesquisa);
					model.addFlashAttribute("pagAntBt", param.get("pagAntBt"));
					model.addFlashAttribute("titulo", param.get("tituloLivro"));
					model.addFlashAttribute("erro", "Login ou Senha incorreto");
					dir = "logincliente";
				}
				break;
			case "Cadastre-se":
				model.addFlashAttribute("pagAnt", pagAnt);
				model.addFlashAttribute("tituloPesquisa", pesquisa);
				model.addFlashAttribute("livros", buscarPesquisa(pesquisa));
				model.addFlashAttribute("pagAntBt", param.get("pagAntBt"));
				model.addFlashAttribute("titulo", param.get("tituloLivro"));
				dir = "cadastrocliente";
				break;
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new RedirectView(dir);
	}

	private String validaLogin(String email, String senha){
		for (Cliente c : clientes) {
			if (c.getEmail().equals(email) && c.getSenha().equals(senha)) {
				return c.getNome();
			}
		}
		return "";
	}
	
	private Cliente buscarCliente(String nome) throws ClassNotFoundException, SQLException {
		for (Cliente c : cdao.listar()) {
			if (c.getNome().equals(nome)) {
				return c;
			}
		}
		return null;
	}
	
	private Livro buscaLivro(String titulo) throws ClassNotFoundException, SQLException {
		for (Livro l : ldao.listar()) {
			if (l.getTitulo().equals(titulo)) {
				return ldao.buscaLivro(l);
			}
		}
		
		return null;
	}
	
	private List<Livro> buscarPesquisa(String titulo) throws ClassNotFoundException, SQLException {
		return ldao.listarFiltro(titulo);
	}
}