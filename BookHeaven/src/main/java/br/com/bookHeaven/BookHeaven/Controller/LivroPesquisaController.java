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
public class LivroPesquisaController {
	@Autowired
	private LivroDao ldao;
	
	@Autowired
	private ClienteDao cdao;
	
	@Autowired
	private PedidoDao pdao;
	
	private List<Livro> livros = new ArrayList<>();
	private List<Cliente> clientes = new ArrayList<>();
	
	@RequestMapping(name = "pesquisalivro", value = "/pesquisalivro", method = RequestMethod.GET)
	public ModelAndView get() {
		if (!livros.isEmpty()) {
			livros.removeAll(livros);
		}
		
		if (!clientes.isEmpty()) {
			clientes.removeAll(clientes);
		}

		try {
			clientes.addAll(cdao.listar());
			livros.addAll(ldao.listar());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("pesquisalivro");
	}
	
	@RequestMapping(name = "pesquisalivro", value = "/pesquisalivro", method = RequestMethod.POST)
	public RedirectView post(@RequestParam Map<String, String> param, RedirectAttributes model) {
		String pesquisa = param.get("pesquisa");
		String login = param.get("login");
		
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
            		model.addFlashAttribute("tituloPesquisa", pesquisa);
            		model.addFlashAttribute("pagAnt", "pesquisalivro");
					dir = "logincliente";
				} else {
					model.addFlashAttribute("login", login);
					model.addFlashAttribute("pagAnt", "index");
					model.addFlashAttribute("pedidos", pdao.listar(buscarCliente(login)));
					model.addFlashAttribute("total", pdao.valorTotal(buscarCliente(login)));
					model.addFlashAttribute("idsPedido", pdao.listaIdsPedidos(buscarCliente(login)));
					model.addFlashAttribute("tituloPesquisa", pesquisa);
            		model.addFlashAttribute("pagAnt", "pesquisalivro");
            		model.addFlashAttribute("qtdPedido", param.get("qtdPedido"));
					dir = "pagamento";						
				}
				break;
            case "Pesquisar":
				model.addFlashAttribute("login", login);
				model.addFlashAttribute("livros", buscarFiltro(pesquisa));
				model.addFlashAttribute("tituloPesquisa", pesquisa);
				model.addFlashAttribute("qtdPedido", param.get("qtdPedido"));
				dir = "pesquisalivro";
				break;
            case "Comprar":
                model.addFlashAttribute("login", login);
                model.addFlashAttribute("tituloPesquisa", pesquisa);
                model.addFlashAttribute("detalhes", buscar(param.get("titulo")));
                model.addFlashAttribute("pagAntBt", "pesquisalivro");
                model.addFlashAttribute("qtdPedido", param.get("qtdPedido"));
                dir = "detalhes";
                break;
            case "Logar":
            	if (login.isEmpty() || login.isBlank()) {
            		model.addFlashAttribute("tituloPesquisa", pesquisa);
            		model.addFlashAttribute("pagAnt", "pesquisalivro");
            		dir = "logincliente";
            	}
            	break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return new RedirectView(dir);
	}
	
	private List<Livro> buscarFiltro(String pesquisa) throws ClassNotFoundException, SQLException{
		return ldao.listarFiltro(pesquisa);
	}
	
	private Cliente buscarCliente(String nome) throws ClassNotFoundException, SQLException {
		for (Cliente c : clientes) {
			if (c.getNome().equals(nome)) {
				return c;
			}
		}
		return null;
	}

	private Livro buscar(String titulo) throws ClassNotFoundException, SQLException {
		for (Livro l : livros) {
			if (l.getTitulo().equals(titulo)) {
				return ldao.buscaLivro(l);
			}
		}
		return null;
	}
}