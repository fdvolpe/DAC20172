package br.com.dac.lol.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.dac.lol.action.FuncionarioBean;

public class FuncionarioDAO {
	private final String stmtInserir = "INSERT INTO FUNCIONARIO (NOME_FUNCIONARIO, EMAIL_FUNCIONARIO, DATA_NASC_FUNCIONARIO, SENHA_FUNCIONARIO)  VALUES(?,?,?,?)";
	private final String stmtAtualizar = "UPDATE FUNCIONARIO SET ";
	private final String stmtConsultar = "SELECT * FROM FUNCIONARIO WHERE MATRICULA_FUNCIONARIO = ?";
	private final String stmtRemover = "DELETE FROM FUNCIONARIO WHERE MATRICULA_FUNCIONARIO = ?";

	public void cadastrarFuncionario(FuncionarioBean funcionario) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtInserir, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getEmail());
			stmt.setString(3, funcionario.getDataNascString());
			stmt.setString(4, funcionario.getSenha());
			stmt.executeUpdate();
			funcionario.setMatricula(lerMatriculaFuncionario(stmt));
		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao inserir um funcionario no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO ao inserir um funcionario no banco de dados. Origem=" + e.getMessage());
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

	public void atualizarFuncionario(FuncionarioBean funcionario) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtAtualizar
					+ "NOME_FUNCIONARIO = " + funcionario.getNome() + "," 
					+ "EMAIL_FUNCIONARIO = " + funcionario.getEmail() + "," 
					+ "DATA_NASC_FUNCIONARIO = " + funcionario.getDataNascString() + ","
					+ "SENHA_FUNCIONARIO = " + funcionario.getSenha()
					+ "WHERE MATRICULA_FUNCIONARIO = " + funcionario.getMatricula(), PreparedStatement.RETURN_GENERATED_KEYS);
			
			stmt.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao atualizar um funcionario no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException(
					"Erro de IO ao atualizar um funcionario no banco de dados. Origem=" + e.getMessage());
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

	public void removerFuncionario(FuncionarioBean funcionario) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtRemover, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, funcionario.getMatricula());
			stmt.executeUpdate();

		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao remover um funcionario no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO ao remover um funcionario no banco de dados. Origem=" + e.getMessage());
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

	public FuncionarioBean pesquisarFuncioario(int matricula) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		FuncionarioBean funcionarioLido;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtConsultar);
			stmt.setInt(1, matricula);
			rs = stmt.executeQuery();
			if (rs.next()) {
				funcionarioLido = new FuncionarioBean();
				funcionarioLido.setMatricula(rs.getInt("MATRICULA_FUNCIONARIO"));
				funcionarioLido.setNome(rs.getString("NOME_FUNCIONARIO"));
				funcionarioLido.setDataNascString(rs.getString("DATA_NASC_FUNCIONARIO"));
				funcionarioLido.setEmail(rs.getString("EMAIL_FUNCIONARIO"));

				return funcionarioLido;
			} else {
				throw new RuntimeException("Não existe funcionario com esta matricula. Id=" + matricula);
			}

		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao consultar um funcionario no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException(
					"Erro de IO ao consultar um funcionario no banco de dados. Origem=" + e.getMessage());
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
				System.out.println("Erro ao fechar result set. Ex=" + ex.getMessage());
			}
			;
			try {
				stmt.close();
			} catch (Exception ex) {
				System.out.println("Erro ao fechar stmt. Ex=" + ex.getMessage());
			}
			;
			try {
				con.close();
				;
			} catch (Exception ex) {
				System.out.println("Erro ao fechar conexão. Ex=" + ex.getMessage());
			}
			;
		}

	}

}
