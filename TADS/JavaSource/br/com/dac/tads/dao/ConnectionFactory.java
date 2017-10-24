package br.com.dac.tads.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public Connection getConnection() throws SQLException, IOException {
		try {

			String url = "jdbc:mysql://localhost:3306/bd_tads";
			String usuario = "root";
			String senha = "admin";
			Connection con = DriverManager.getConnection(url, usuario, senha);
			return con;
		} catch (SQLException e) {
			throw new RuntimeException("Ocorreu um erro ao pegar a conexão.");
		}

	}

}
