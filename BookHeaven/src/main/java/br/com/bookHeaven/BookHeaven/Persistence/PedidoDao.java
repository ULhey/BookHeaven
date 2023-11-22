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

import br.com.bookHeaven.BookHeaven.Model.Cliente;
import br.com.bookHeaven.BookHeaven.Model.ItemPedido;
import br.com.bookHeaven.BookHeaven.Model.Livro;
import br.com.bookHeaven.BookHeaven.Model.Pedido;

@Repository
public class PedidoDao {
	private Connection c;
	
	@Autowired
	private GenericDao gdao;
	
	public void procedure(String acao, ItemPedido ip) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		
		String sql = "{CALL crudItemPedido (?, ?, ?, ?, ?, ?, ?) }";
		CallableStatement cs = c.prepareCall(sql);
		
		cs.setString(1, acao);
		cs.setInt(2, ip.getPedido().getId());
		cs.setString(3, ip.getPedido().getCliente().getCpf());
		cs.setInt(4, ip.getLivro().getCod());
		cs.setFloat(5, ip.getLivro().getPreco());
		cs.setInt(6, ip.getQtd());
		cs.registerOutParameter(7, Types.BOOLEAN);
		
		cs.execute();
		cs.close();
		
		c.close();
	}
	
	public List<ItemPedido> listar() throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		List<ItemPedido> listas = new ArrayList<>();
		
		String sql = "SELECT * FROM viewPedido";
		
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
			
			Cliente c = new Cliente();
			
			c.setNome(rs.getString("nome"));
			c.setCpf(rs.getString("cpf"));
			c.setDataNasc(rs.getString("dataNasc"));
			c.setTelefone(rs.getString("telefone"));
			c.setEndereco(rs.getString("endereco"));
			c.setEmail(rs.getString("email"));
			c.setSenha(rs.getString("senha"));
			
			Pedido p = new Pedido();
			
			p.setCliente(c);
			p.setId(rs.getInt("idPedido"));
			p.setDataPedido(rs.getString("dataPedido"));
			p.setSituacao(rs.getString("situacao"));
			
			ItemPedido ip = new ItemPedido();
			ip.setLivro(l);
			ip.setPedido(p);
			ip.setQtd(rs.getInt("quantidade"));
			
			listas.add(ip);
		}
		
		rs.close();
		ps.close();

		c.close();
		
		return listas;
	}
	
	public List<ItemPedido> listar(Cliente cli) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		List<ItemPedido> listas = new ArrayList<>();
		
		String sql = "SELECT * FROM viewPedido WHERE cpf = ? AND situacao = 'pendente'";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cli.getCpf());
		
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
			
			Cliente c = new Cliente();
			
			c.setNome(rs.getString("nome"));
			c.setCpf(rs.getString("cpf"));
			c.setDataNasc(rs.getString("dataNasc"));
			c.setTelefone(rs.getString("telefone"));
			c.setEndereco(rs.getString("endereco"));
			c.setEmail(rs.getString("email"));
			c.setSenha(rs.getString("senha"));
			
			Pedido p = new Pedido();
			
			p.setCliente(c);
			p.setId(rs.getInt("idPedido"));
			p.setDataPedido(rs.getString("dataPedido"));
			p.setSituacao(rs.getString("situacao"));
			
			ItemPedido ip = new ItemPedido();
			ip.setLivro(l);
			ip.setPedido(p);
			ip.setQtd(rs.getInt("quantidade"));
			
			listas.add(ip);
		}
		
		rs.close();
		ps.close();

		c.close();
		
		return listas;
	}
	
	public List<Integer> listaIdsPedidos(Cliente cli) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		List<Integer> lista = new ArrayList<>();
		
		String sql = "SELECT idPedido FROM pedido WHERE cpf = ? AND situacao = 'pendente'";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cli.getCpf());
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			lista.add(rs.getInt("idPedido"));
		}
		
		rs.close();
		ps.close();

		c.close();
		
		return lista;
	}
	
	public float valorTotal(Cliente cli) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		float valor = 0;
		
		String sql = "SELECT * FROM viewCalculaTotal WHERE cpf = ? AND situacao = 'pendente'";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cli.getCpf());
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			valor = rs.getFloat("subTotal");
		}
		
		rs.close();
		ps.close();

		c.close();
		
		return valor;
	}
	
	public int qtdPedido(Cliente cli) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		int valor = 0;
		
		String sql = "SELECT * FROM viewQtdPedido WHERE cpf = ? AND situacao = 'pendente'";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cli.getCpf());
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			valor = rs.getInt("pedidos");
		}
		
		rs.close();
		ps.close();

		c.close();
		
		return valor;
	}
}