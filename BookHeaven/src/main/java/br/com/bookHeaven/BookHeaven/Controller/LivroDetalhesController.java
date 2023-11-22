package br.com.bookHeaven.BookHeaven.Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import br.com.bookHeaven.BookHeaven.Model.Cliente;
import br.com.bookHeaven.BookHeaven.Model.ItemPedido;
import br.com.bookHeaven.BookHeaven.Model.Livro;
import br.com.bookHeaven.BookHeaven.Model.Pedido;
import br.com.bookHeaven.BookHeaven.Persistence.ClienteDao;
import br.com.bookHeaven.BookHeaven.Persistence.LivroDao;
import br.com.bookHeaven.BookHeaven.Persistence.PedidoDao;

@Controller
public class LivroDetalhesController {
	@Autowired
	private LivroDao ldao;

	@Autowired
	private ClienteDao cdao;

	@Autowired
	private PedidoDao pdao;

	private List<Livro> livros = new ArrayList<>();
	private List<ItemPedido> pedidos = new ArrayList<>();
	private List<Cliente> clientes = new ArrayList<>();

	@RequestMapping(name = "detalhes", value = "/detalhes", method = RequestMethod.GET)
	public ModelAndView get(ModelMap model) {
		if (!clientes.isEmpty()) {
			clientes.removeAll(clientes);
		}

		if (!livros.isEmpty()) {
			livros.removeAll(livros);
		}

		if (!pedidos.isEmpty()) {
			pedidos.removeAll(pedidos);
		}

		try {
			clientes.addAll(cdao.listar());
			livros.addAll(ldao.listar());
			pedidos.addAll(pdao.listar());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return new ModelAndView("detalhes");
	}

	@RequestMapping(name = "detalhes", value = "/detalhes", method = RequestMethod.POST)
	public RedirectView post(@RequestParam Map<String, String> param, RedirectAttributes model) {
		String pesquisa = param.get("pesquisa");
		String login = param.get("login");
		String pagAntBt = param.get("pagAntBt");

		String dir = "";

		try {
			switch (param.get("botao")) {
			case "Home":
				model.addFlashAttribute("login", login);
				model.addFlashAttribute("qtdPedido", param.get("qtdPedido"));
				dir = "index";
				break;
			case "Carrinho":
				if (login.isEmpty() || login.isBlank()) {
					model.addFlashAttribute("pagAnt", "detalhes");
					model.addFlashAttribute("pagAntBt", pagAntBt);
					model.addFlashAttribute("titulo", param.get("tituloLivro"));
					model.addFlashAttribute("tituloPesquisa", pesquisa);
					model.addFlashAttribute("qtdPedido", param.get("qtdPedido"));
					dir = "logincliente";
				} else {
					model.addFlashAttribute("login", login);
					model.addFlashAttribute("pagAnt", "index");
					model.addFlashAttribute("pedidos", pdao.listar(buscarCliente(login)));
					model.addFlashAttribute("total", pdao.valorTotal(buscarCliente(login)));
					model.addFlashAttribute("idsPedido", pdao.listaIdsPedidos(buscarCliente(login)));
					model.addFlashAttribute("pagAnt", "detalhes");
					model.addFlashAttribute("pagAntBt", pagAntBt);
					model.addFlashAttribute("titulo", param.get("tituloLivro"));
					model.addFlashAttribute("tituloPesquisa", pesquisa);
					model.addFlashAttribute("qtdPedido", param.get("qtdPedido"));
					dir = "pagamento";
				}
				break;
			case "Pesquisar":
				model.addFlashAttribute("login", login);
				model.addFlashAttribute("livros", ldao.listarFiltro(pesquisa));
				model.addFlashAttribute("tituloPesquisa", pesquisa);
				model.addFlashAttribute("qtdPedido", param.get("qtdPedido"));
				dir = "pesquisalivro";
				break;
			case "Voltar":
				model.addFlashAttribute("login", login);
				model.addFlashAttribute("livros", ldao.listarFiltro(pesquisa));
				model.addFlashAttribute("tituloPesquisa", pesquisa);
				model.addFlashAttribute("qtdPedido", param.get("qtdPedido"));
				dir = pagAntBt;
				break;
			case "Comprar":
				String idLivro = param.get("idLivro");
				String qtd = param.get("quantidade");

				if (!login.equals("")) {
					registraPedido(login, Integer.parseInt(idLivro), Integer.parseInt(qtd));
					model.addFlashAttribute("login", login);
					model.addFlashAttribute("pagAntBt", pagAntBt);
					model.addFlashAttribute("detalhes", buscarLivro(param.get("tituloLivro")));
					model.addFlashAttribute("tituloPesquisa", pesquisa);
					model.addFlashAttribute("qtdPedido", param.get("qtdPedido"));
					model.addFlashAttribute("qtdPedido", pdao.qtdPedido(buscarCliente(login)));
					dir = "detalhes";
				} else {
					model.addFlashAttribute("tituloPesquisa", pesquisa);
					model.addFlashAttribute("pagAnt", "pesquisalivro");
					model.addFlashAttribute("pagAnt", "detalhes");
					model.addFlashAttribute("pagAntBt", pagAntBt);
					model.addFlashAttribute("titulo", param.get("tituloLivro"));
					dir = "logincliente";
				}
				break;
			case "Logar":
				if (login.isEmpty() || login.isBlank()) {
					model.addFlashAttribute("pagAnt", "detalhes");
					model.addFlashAttribute("pagAntBt", pagAntBt);
					model.addFlashAttribute("titulo", param.get("tituloLivro"));
					model.addFlashAttribute("tituloPesquisa", pesquisa);
					dir = "logincliente";
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new RedirectView(dir);
	}

	private void registraPedido(String login, int idLivro, int qtd) throws ClassNotFoundException, SQLException {
		Pedido p = new Pedido();
		
		p.setId(1001 + pedidos.size());
		
		for (Cliente c : clientes) {
			if (c.getNome().equals(login)) {
				p.setCliente(c);
			}
		}
		
		ItemPedido ip = new ItemPedido();
		
		ip.setId(51 + pedidos.size());
		ip.setQtd(qtd);
		
		for (Livro l : livros) {
			if (l.getCod() == idLivro) {
				ip.setLivro(l);
			}
		}
		
		ip.setPedido(p);
		
		pdao.procedure("i", ip);
		pedidos.add(ip);
	}

	private Cliente buscarCliente(String nome) throws ClassNotFoundException, SQLException {
		for (Cliente c : clientes) {
			if (c.getNome().equals(nome)) {
				return c;
			}
		}
		return null;
	}

	private Livro buscarLivro(String titulo) throws ClassNotFoundException, SQLException {
		for (Livro l : livros) {
			if (l.getTitulo().equals(titulo)) {
				return ldao.buscaLivro(l);
			}
		}
		return null;
	}
}