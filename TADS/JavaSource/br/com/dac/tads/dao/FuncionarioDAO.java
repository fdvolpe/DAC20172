package br.com.dac.tads.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.dac.tads.action.FuncionarioBean;

public class FuncionarioDAO {
	private final String stmtInserir = "INSERT INTO FUNCIONARIO (CPF_FUNCIONARIO, TIPO_FUNCIONARIO, NOME_FUNCIONARIO, ENDERECO_FUNCIONARIO, TELEFONE_FUNCIONARIO, EMAIL_FUNCIONARIO, SENHA_FUNCIONARIO)  VALUES(?,?,?,?,?,?,?)";
	
	public void cadastrarFuncionario(FuncionarioBean funcionario) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtInserir, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, funcionario.getCpf());
			stmt.setString(2, funcionario.getTipo());
			stmt.setString(3, funcionario.getNome());
			stmt.setString(4, funcionario.getEndereco());
			stmt.setString(5, funcionario.getTelefone());
			stmt.setString(6, funcionario.getEmail());
			stmt.setString(7, funcionario.getSenha());
			stmt.executeUpdate();
			funcionario.setMatricula(lerMatriculaFuncionario(stmt));
		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao inserir uma entrega no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO ao inserir uma entrega no banco de dados. Origem=" + e.getMessage());
		} finally {
			try {
				stmt.close();
			} catch (Exception ex) {
				System.out.println("Erro ao fechar stmt. Ex=" + ex.getMessage());
			}
			;
			try {
				con.close();
			} catch (Exception ex) {
				System.out.println("Erro ao fechar conexão. Ex=" + ex.getMessage());
			}
			;
		}
	}

	private int lerMatriculaFuncionario(PreparedStatement stmt) throws SQLException {
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		return rs.getInt(1);
	}
}
