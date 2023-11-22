package br.com.bookHeaven.BookHeaven.Persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.bookHeaven.BookHeaven.Model.Funcionario;

@Repository
public class FuncionarioDao {
	private Connection c;
	
	@Autowired
	private GenericDao gdao;
	
	public List<Funcionario> listar() throws ClassNotFoundException, SQLException {
		c = gdao.conexao();
		List<Funcionario> listas = new ArrayList<>();
		
		String sql = "SELECT ID, loginFunc, senha FROM funcionario";
		
		PreparedStatement ps = c.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Funcionario f = new Funcionario();
			
			f.setId(rs.getInt("ID"));
			f.setNome(rs.getString("loginFunc"));
			f.setSenha(rs.getString("senha"));
			
			listas.add(f);
		}
		
		rs.close();
		ps.close();

		c.close();
		
		return listas;
	}
}