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

@Repository
public class ClienteDao {
	private Connection c;
	
	@Autowired
	private GenericDao gdao;
	
	public void procedure(String acao, Cliente cli) throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		
		String sql = "{CALL crudCliente (?, ?, ?, ?, ?, ?, ?, ?, ?) }";
		CallableStatement cs = c.prepareCall(sql);
		
		cs.setString(1, acao);
		cs.setString(2, cli.getNome());
		cs.setString(3, cli.getCpf());
		cs.setString(4, cli.getDataNasc());
		cs.setString(5, cli.getTelefone());
		cs.setString(6, cli.getEndereco());
		cs.setString(7, cli.getEmail());
		cs.setString(8, cli.getSenha());
		cs.registerOutParameter(9, Types.BOOLEAN);
		
		cs.execute();
		cs.close();
		
		c.close();
	}
	
	public List<Cliente> listar() throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		List<Cliente> listas = new ArrayList<>();
		
		String sql = "SELECT nome, cpf, dataNasc, telefone, endereco, email, senha FROM cliente";
		
		PreparedStatement ps = c.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Cliente c = new Cliente();
			
			c.setNome(rs.getString("nome"));
			c.setCpf(rs.getString("cpf"));
			c.setDataNasc(rs.getString("dataNasc"));
			c.setTelefone(rs.getString("telefone"));
			c.setEndereco(rs.getString("endereco"));
			c.setEmail(rs.getString("email"));
			c.setSenha(rs.getString("senha"));
			
			listas.add(c);
		}
		
		rs.close();
		ps.close();

		c.close();
		
		return listas;
	}
}