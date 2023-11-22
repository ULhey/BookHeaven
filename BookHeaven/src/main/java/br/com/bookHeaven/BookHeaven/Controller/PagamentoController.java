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
import br.com.bookHeaven.BookHeaven.Model.ItemPedido;
import br.com.bookHeaven.BookHeaven.Model.Livro;
import br.com.bookHeaven.BookHeaven.Model.Pagamento;
import br.com.bookHeaven.BookHeaven.Model.Pedido;
import br.com.bookHeaven.BookHeaven.Persistence.ClienteDao;
import br.com.bookHeaven.BookHeaven.Persistence.LivroDao;
import br.com.bookHeaven.BookHeaven.Persistence.PagamentoDao;
import br.com.bookHeaven.BookHeaven.Persistence.PedidoDao;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PagamentoController {
	@Autowired
	private ClienteDao cdao;
	
	@Autowired
	private LivroDao ldao;
	
	@Autowired
	private PedidoDao pdao;
	
	@Autowired
	private PagamentoDao pagDao;
	
	private List<ItemPedido> pedidos = new ArrayList<>();
	private List<Pagamento> pagamentos = new ArrayList<>();
	
	@RequestMapping(name = "pagamento", value = "/pagamento", method = RequestMethod.GET)
	public ModelAndView get(@RequestParam Map<String, String> param) {
		if (!pedidos.isEmpty()) {
			pedidos.removeAll(pedidos);
		}
		
		if (!pagamentos.isEmpty()) {
			pagamentos.removeAll(pagamentos);
		}
		
		try {
			pedidos.addAll(pdao.listar());
			pagamentos.addAll(pagDao.listar());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("pagamento");
	}
	
	@RequestMapping(name = "pagamento", value = "/pagamento", method = RequestMethod.POST)
	public RedirectView post(@RequestParam Map<String, String> param, HttpServletRequest req, RedirectAttributes model) {
		String pagAnt = param.get("pagAnt");
		String pesquisa = param.get("pesquisa");
		String login = param.get("login");
		
		String dir = "";
		
		try {
			switch (param.get("botao")) {
			case "Voltar": 
				if (pagAnt.equals("pesquisalivro")) {
					model.addFlashAttribute("tituloPesquisa", pesquisa);
					model.addFlashAttribute("livros", buscarPesquisa(pesquisa));
				} else if (pagAnt.equals("detalhes")) {
					model.addFlashAttribute("tituloPesquisa", pesquisa);
					model.addFlashAttribute("pagAntBt", param.get("pagAntBt"));
					model.addFlashAttribute("detalhes", buscaLivro(param.get("tituloLivro")));
				}
				model.addFlashAttribute("qtdPedido", param.get("qtdPedido"));
				model.addFlashAttribute("login", login);
				dir = pagAnt;
				break;
			case "Excluir":
				String idPedido = param.get("idPedido");
				excluir(Integer.parseInt(idPedido));
				model.addFlashAttribute("pagAnt", pagAnt);
				model.addFlashAttribute("tituloPesquisa", pesquisa);
				model.addFlashAttribute("pagAntBt", param.get("pagAntBt"));
				model.addFlashAttribute("titulo", param.get("tituloLivro"));
				model.addFlashAttribute("login", login);
				model.addFlashAttribute("pedidos", pdao.listar(buscarCliente(login)));
				model.addFlashAttribute("total", pdao.valorTotal(buscarCliente(login)));
				model.addFlashAttribute("idsPedido", pdao.listaIdsPedidos(buscarCliente(login)));
				model.addFlashAttribute("qtdPedido", Integer.parseInt(param.get("qtdPedido")) - 1);
				dir = "pagamento";
				break;
			case "Finalizar Compra":
				model.addFlashAttribute("total", param.get("total"));
                model.addFlashAttribute("pagAnt", pagAnt);
				model.addFlashAttribute("tituloPesquisa", pesquisa);
				model.addFlashAttribute("pagAntBt", param.get("pagAntBt"));
				model.addFlashAttribute("titulo", param.get("tituloLivro"));
				model.addFlashAttribute("login", login);
				model.addFlashAttribute("teste", "pago");
				model.addFlashAttribute("pedidos", pdao.listar(buscarCliente(login)));
				model.addFlashAttribute("total", pdao.valorTotal(buscarCliente(login)));
				model.addFlashAttribute("idsPedido", pdao.listaIdsPedidos(buscarCliente(login)));
                dir = "pagamento";
                break;
			case "Pagar":
				String [] list = req.getParameterValues("idPedido");
				for (int a = 0; a < list.length; a++) {
					registrarPagamento(Integer.parseInt(list[a]), Float.parseFloat(param.get("total")));
				}
				model.addFlashAttribute("login", login);
				model.addFlashAttribute("qtdPedido",0);
				dir = "index";
				break;
			}
               
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new RedirectView(dir);
	}

	private void registrarPagamento(int idPedido, float total) throws ClassNotFoundException, SQLException {
		Pagamento p = new Pagamento();
		
		p.setCod(61 + pagamentos.size());
		
		for (ItemPedido ip : pedidos) {
			if (p.getPedido().getId() == idPedido) {
				p.setPedido(ip.getPedido());
			}
		}
		
		p.setSubTotal(total);
		
		pagDao.procedure("i", p);
	}
	
	private void excluir(int idPedido) throws ClassNotFoundException, SQLException {
		for (ItemPedido ip : pedidos) {
			if (ip.getPedido().getId() == idPedido) {
				pdao.procedure("d", ip);
				pedidos.remove(ip);
				break;
			}
		}
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