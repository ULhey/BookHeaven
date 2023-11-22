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

import br.com.bookHeaven.BookHeaven.Model.Livro;
import br.com.bookHeaven.BookHeaven.Persistence.LivroDao;

@Controller
public class LivroCrudController {
	@Autowired
	private LivroDao ldao;

	private List<Livro> livros = new ArrayList<>();

	@RequestMapping(name = "cadastrolivro", value = "/cadastrolivro", method = RequestMethod.GET)
	public ModelAndView get(ModelMap model) {
		if (!livros.isEmpty()) {
			livros.removeAll(livros);
		}

		try {
			livros.addAll(ldao.listar());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return new ModelAndView("cadastrolivro");
	}

	@RequestMapping(name = "cadastrolivro", value = "/cadastrolivro", method = RequestMethod.POST)
	public ModelAndView post(@RequestParam Map<String, String> param, ModelMap model) {

		String cod = param.get("cod");
		String titulo = param.get("titulo");
		String numPaginas = param.get("numpag");
		String preco = param.get("preco");
		String genero = param.get("genero");
		String dataPublicao = param.get("dataPub");
		String nomeAutor = param.get("autor");
		String sinopse = param.get("sinopse");
		String linkImagem = param.get("link");
		
		String dir = "";
		
		try {
			switch (param.get("botao")) {
			case "Cadastrar":
				criar(titulo, Integer.parseInt(numPaginas), Float.parseFloat(preco), genero, dataPublicao, nomeAutor,
						sinopse, linkImagem);
				dir = "cadastrolivro";
				break;
			case "Buscar":
				model.addAttribute("cadastrolivro", buscar(Integer.parseInt(cod)));
				dir = "cadastrolivro";
				break;
			case "Alterar":
				atualizar(Integer.parseInt(cod), titulo, Integer.parseInt(numPaginas), Float.parseFloat(preco), genero,
						dataPublicao, nomeAutor, sinopse, linkImagem);
				dir = "cadastrolivro";
				break;
			case "Excluir":
				deletar(Integer.parseInt(cod));
				dir = "cadastrolivro";
				break;
			case "Listar":
				model.addAttribute("livros", livros);
				dir = "cadastrolivro";
				break;
			case "Logoff":
				dir = "index";
				break;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return new ModelAndView(dir);
	}

	private void criar(String titulo, int numPaginas, float preco, String genero, String dataPublicao,
			String nomeAutor, String sinopse, String linkImagem) throws ClassNotFoundException, SQLException {
		Livro l = new Livro();
		
		l.setCod(livros.size() + 1);
		l.setTitulo(titulo);
		l.setNumPaginas(numPaginas);
		l.setPreco(preco);
		l.setGenero(genero);
		l.setDataPublicao(dataPublicao);
		l.setNomeAutor(nomeAutor);
		l.setSinopse(sinopse);
		l.setLinkImagem(linkImagem);
		
		ldao.procedure("i", l);
		livros.add(l);
	}

	private Livro buscar(int cod) throws ClassNotFoundException, SQLException {
		for (Livro l : livros) {
			if (l.getCod() == cod) {
				return ldao.buscaLivro(l);
			}
		}
		return null;
	}

	private void atualizar(int cod, String titulo, int numPaginas, float preco, String genero, String dataPublicao,
			String nomeAutor, String sinopse, String linkImagem) throws ClassNotFoundException, SQLException {
		for (Livro l : livros) {
			if (l.getCod() == cod) {
				l.setTitulo(titulo);
				l.setNumPaginas(numPaginas);
				l.setPreco(preco);
				l.setGenero(genero);
				l.setDataPublicao(dataPublicao);
				l.setNomeAutor(nomeAutor);
				l.setSinopse(sinopse);
				l.setLinkImagem(linkImagem);
				
				ldao.procedure("u", l);
				break;
			}
		}
	}

	private void deletar(int cod) throws ClassNotFoundException, SQLException {
		for (Livro l : livros) {
			if (l.getCod() == cod) {	
				ldao.procedure("d", l);
				livros.remove(l);
				break;
			}
		}
	}

	public List<Livro> listar() throws ClassNotFoundException, SQLException {
		return ldao.listar();
	}
}