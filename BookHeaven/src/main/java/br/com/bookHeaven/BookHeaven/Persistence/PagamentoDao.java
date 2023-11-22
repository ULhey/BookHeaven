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
import br.com.bookHeaven.BookHeaven.Model.Pagamento;
import br.com.bookHeaven.BookHeaven.Model.Pedido;

@Repository
public class PagamentoDao {
	private Connection c;
	@Autowired
	private GenericDao gdao;
	
	public void procedure(String acao, Pagamento p) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		
		String sql = "{CALL crudPagamento (?, ?, ?, ?, ?) }";
		CallableStatement cs = c.prepareCall(sql);
		
		cs.setString(1, acao);
		cs.setInt(2, p.getCod());
		cs.setInt(3, p.getPedido().getId());
		cs.setFloat(4, p.getSubTotal());
		cs.registerOutParameter(5, Types.BOOLEAN);
		
		cs.execute();
		cs.close();
		
		c.close();
	}
	
	public List<Pagamento> listar() throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		List<Pagamento> listas = new ArrayList<>();
		
		String sql = "SELECT * FROM viewPagamento";
		
		PreparedStatement ps = c.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Pagamento pag = new Pagamento();
			
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
			
			pag.setPedido(p);
			
			pag.setCod(rs.getInt("idPag"));
			pag.setDataPagamento(rs.getString("dataPagamento"));
			pag.setSubTotal(rs.getFloat("TOTAL"));
			
			listas.add(pag);
		}
		
		rs.close();
		ps.close();

		c.close();
		
		return listas;
	}
}