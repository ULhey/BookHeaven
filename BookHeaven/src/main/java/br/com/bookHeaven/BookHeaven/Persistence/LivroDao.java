package br.com.bookHeaven.BookHeaven.Persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.bookHeaven.BookHeaven.Model.Livro;

@Repository
public class LivroDao {
	private Connection c;
	@Autowired
	private GenericDao gdao;

	public void procedure(String acao, Livro l) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		
		String sql = "{CALL crudLivro (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
		CallableStatement cs = c.prepareCall(sql);
		
		cs.setString(1, acao);
		cs.setInt(2, l.getCod());
		cs.setString(3, l.getTitulo());
		cs.setInt(4, l.getNumPaginas());
		cs.setFloat(5, l.getPreco());
		cs.setString(6, l.getGenero());
		cs.setString(7, l.getDataPublicao());
		cs.setString(8, l.getNomeAutor());
		cs.setString(9, l.getSinopse());
		cs.setString(10, l.getLinkImagem());
		cs.registerOutParameter(11, Types.BOOLEAN);
		
		cs.execute();
		cs.close();
		
		c.close();
	}

	public Livro buscaLivro(Livro l) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		
		String sql = "SELECT ID, titulo, numPagina, preco, genero, dataPublicacao, nomeAutor, sinopse, linkImagem, numVendas FROM livro WHERE ID = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, l.getCod());
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			l.setCod(rs.getInt("ID"));
			l.setTitulo(rs.getString("titulo"));
			l.setNumPaginas(rs.getInt("numPagina"));
			l.setPreco(rs.getFloat("preco"));
			l.setGenero(rs.getString("genero"));
			l.setDataPublicao(rs.getString("dataPublicacao"));
			l.setNomeAutor(rs.getString("nomeAutor"));
			l.setSinopse(rs.getString("sinopse"));
			l.setLinkImagem(rs.getString("linkImagem"));
			l.setNumVendas(rs.getInt("numVendas"));
		}
		
		rs.close();
		ps.close();

		c.close();
		return l;
	}
	
	public List<Livro> listar() throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		List<Livro> listas = new ArrayList<>();
		
		String sql = "SELECT ID, titulo, numPagina, preco, genero, dataPublicacao, nomeAutor, sinopse, linkImagem, numVendas FROM livro";
		
		PreparedStatement ps = c.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Livro l = new Livro();
			
			l.setCod(rs.getInt("ID"));
			l.setTitulo(rs.getString("titulo"));
			l.setNumPaginas(rs.getInt("numPagina"));
			l.setPreco(rs.getFloat("preco"));
			l.setGenero(rs.getString("genero"));
			l.setDataPublicao(rs.getString("dataPublicacao"));
			l.setNomeAutor(rs.getString("nomeAutor"));
			l.setSinopse(rs.getString("sinopse"));
			l.setLinkImagem(rs.getString("linkImagem"));
			l.setNumVendas(rs.getInt("numVendas"));
			
			listas.add(l);
		}
		
		rs.close();
		ps.close();

		c.close();
		
		return listas;
	}
	
	public List<Livro> listarMaisVendidos() throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		List<Livro> listas = new ArrayList<>();
		
		String sql = "SELECT TOP(4) ID, titulo, numPagina, preco, genero, dataPublicacao, nomeAutor, sinopse, linkImagem, numVendas FROM livro ORDER BY numVendas DESC";
		
		PreparedStatement ps = c.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Livro l = new Livro();
			
			l.setCod(rs.getInt("ID"));
			l.setTitulo(rs.getString("titulo"));
			l.setNumPaginas(rs.getInt("numPagina"));
			l.setPreco(rs.getFloat("preco"));
			l.setGenero(rs.getString("genero"));
			l.setDataPublicao(rs.getString("dataPublicacao"));
			l.setNomeAutor(rs.getString("nomeAutor"));
			l.setSinopse(rs.getString("sinopse"));
			l.setLinkImagem(rs.getString("linkImagem"));
			l.setNumVendas(rs.getInt("numVendas"));
			
			listas.add(l);
		}
		
		
		rs.close();
		ps.close();

		c.close();
		
		return listas;
	}
	
	public List<Livro> listarLancamentos() throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		List<Livro> listas = new ArrayList<>();
		
		String sql = "SELECT TOP(4) ID, titulo, numPagina, preco, genero, dataPublicacao, nomeAutor, sinopse, linkImagem, numVendas FROM livro ORDER BY dataPublicacao DESC";
		
		PreparedStatement ps = c.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Livro l = new Livro();
			
			l.setCod(rs.getInt("ID"));
			l.setTitulo(rs.getString("titulo"));
			l.setNumPaginas(rs.getInt("numPagina"));
			l.setPreco(rs.getFloat("preco"));
			l.setGenero(rs.getString("genero"));
			l.setDataPublicao(rs.getString("dataPublicacao"));
			l.setNomeAutor(rs.getString("nomeAutor"));
			l.setSinopse(rs.getString("sinopse"));
			l.setLinkImagem(rs.getString("linkImagem"));
			l.setNumVendas(rs.getInt("numVendas"));
			
			listas.add(l);
		}
		
		rs.close();
		ps.close();

		c.close();
		
		return listas;
	}
	
	public List<Livro> listarFiltro(String filtro) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		List<Livro> listas = new ArrayList<>();
		
		String sql = "SELECT ID, titulo, numPagina, preco, genero, dataPublicacao, nomeAutor, sinopse, linkImagem, numVendas FROM livro "
				+ "WHERE titulo LIKE ? OR nomeAutor LIKE ? OR genero LIKE ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		
		String pesquisa = "%" + filtro + "%";
		
		ps.setString(1, pesquisa);
		ps.setString(2, pesquisa);
		ps.setString(3, pesquisa);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Livro l = new Livro();
			
			l.setCod(rs.getInt("ID"));
			l.setTitulo(rs.getString("titulo"));
			l.setNumPaginas(rs.getInt("numPagina"));
			l.setPreco(rs.getFloat("preco"));
			l.setGenero(rs.getString("genero"));
			l.setDataPublicao(rs.getString("dataPublicacao"));
			l.setNomeAutor(rs.getString("nomeAutor"));
			l.setSinopse(rs.getString("sinopse"));
			l.setLinkImagem(rs.getString("linkImagem"));
			l.setNumVendas(rs.getInt("numVendas"));
			
			listas.add(l);
		}
		
		rs.close();
		ps.close();

		c.close();
		
		return listas;
	}
}