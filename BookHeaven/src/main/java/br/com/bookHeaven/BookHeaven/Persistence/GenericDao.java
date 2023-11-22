package br.com.bookHeaven.BookHeaven.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
public class GenericDao {
	private Connection c;
	
	public Connection conexao() throws ClassNotFoundException, SQLException {

		String hostName = "localhost";
		String dbName = "BookHeaven";
		String user = "sa";
		String senha = "123456";

		Class.forName("net.sourceforge.jtds.jdbc.Driver");

		c = DriverManager.getConnection("jdbc:jtds:sqlserver://" + hostName + ":1433;databaseName=" + dbName + ";user=" + user + ";password=" + senha);
		
		return c;
	}
}