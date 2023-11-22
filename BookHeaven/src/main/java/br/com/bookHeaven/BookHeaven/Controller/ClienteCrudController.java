package br.com.bookHeaven.BookHeaven.Controller;

import java.sql.SQLException;
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
public class ClienteCrudController {
	@Autowired
	private PedidoDao pdao;
	
	@Autowired
	private LivroDao ldao;
	
	@Autowired
	private ClienteDao cdao;

	@RequestMapping(name = "cadastrocliente", value = "/cadastrocliente", method = RequestMethod.GET)
	public ModelAndView get() {
		return new ModelAndView("cadastrocliente");
	}

	@RequestMapping(name = "cadastrocliente", value = "/cadastrocliente", method = RequestMethod.POST)
	public RedirectView post(@RequestParam Map<String, String> param, RedirectAttributes model) {
		String nome = param.get("nome");
		String cpf = param.get("cpf");
		String dataNasc = param.get("dataNasc");
		String telefone = param.get("telefone");
		String endereco = param.get("endereco");
		String email = param.get("email");
		String senha = param.get("senha");
		
		String pagAnt = param.get("pagAnt");
		String pesquisa = param.get("pesquisa");
		
		String dir = "";

		try {
			switch (param.get("botao")) {
			case "Cadastrar":
				criar(nome, cpf, dataNasc, telefone, endereco, email, senha);
				if (pagAnt.equals("pesquisalivro")) {
					model.addFlashAttribute("tituloPesquisa", pesquisa);
					model.addFlashAttribute("livros", buscarPesquisa(pesquisa));
				} else if (pagAnt.equals("detalhes")) {
					model.addFlashAttribute("tituloPesquisa", pesquisa);
					model.addFlashAttribute("pagAntBt", param.get("pagAntBt"));
					model.addFlashAttribute("detalhes", buscaLivro(param.get("tituloLivro")));
				}
				model.addFlashAttribute("qtdPedido", pdao.qtdPedido(buscarCliente(nome)));
				model.addFlashAttribute("login", nome);
				dir = pagAnt;
				break;
			case "Entrar":
				model.addFlashAttribute("pagAnt", pagAnt);
				model.addFlashAttribute("tituloPesquisa", pesquisa);
				model.addFlashAttribute("livros", buscarPesquisa(pesquisa));
				model.addFlashAttribute("pagAntBt", param.get("pagAntBt"));
				model.addFlashAttribute("titulo", param.get("tituloLivro"));
				dir = "logincliente";
				break;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return new RedirectView(dir);
	}

	private void criar(String nome, String cpf, String dataNasc, String telefone, String endereco, String email,
			String senha) throws ClassNotFoundException, SQLException {
		Cliente c = new Cliente();

		c.setNome(nome);
		c.setCpf(cpf);
		c.setDataNasc(dataNasc);
		c.setTelefone(telefone);
		c.setEndereco(endereco);
		c.setEmail(email);
		c.setSenha(senha);

		cdao.procedure("i", c);
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