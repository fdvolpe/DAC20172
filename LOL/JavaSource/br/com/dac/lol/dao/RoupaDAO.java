package br.com.dac.lol.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.dac.lol.action.RoupaBean;

public class RoupaDAO {
	private final String stmtInserir = "INSERT INTO ROUPA (NOME_ROUPA, PRAZO_ROUPA, VALOR_ROUPA, FUNCIONARIO_CRIADOR)  VALUES(?,?,?,?)";
	private final String stmtAtualizar = "UPDATE ROUPA SET ";
	private final String stmtConsultar = "SELECT * FROM ROUPA WHERE ID_ROUPA = ?";
	private final String stmtRemover = "DELETE FROM ROUPA WHERE ID_ROUPA = ?";

	public void cadastrarFuncionario(RoupaBean roupa) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtInserir, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, roupa.getNome());
			stmt.setInt(2, roupa.getPrazo());
			stmt.setDouble(3, roupa.getValor());
			stmt.setInt(4, roupa.getFuncionarioCriador().getMatricula());
			stmt.executeUpdate();
			roupa.setIdRoupa(lerIdRoupa(stmt));
		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao inserir uma roupa no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO ao inserir uma roupa no banco de dados. Origem=" + e.getMessage());
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

	private int lerIdRoupa(PreparedStatement stmt) throws SQLException {
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		return rs.getInt(1);
	}

	public void atualizarRoupa(RoupaBean roupa) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtAtualizar + "NOME_ROUPA = " + roupa.getNome() + "," + "PRAZO_ROUPA = "
					+ roupa.getPrazo() + "," + "VALOR_ROUPA = " + roupa.getValor() + "," + "FUNCIONARIO_CRIADOR = "
					+ roupa.getFuncionarioCriador().getMatricula() + "," + "WHERE ID_ROUPA = " + roupa.getIdRoupa(),
					PreparedStatement.RETURN_GENERATED_KEYS);

			stmt.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao atualizar uma roupa no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO ao atualizar uma roupa no banco de dados. Origem=" + e.getMessage());
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

	public void removerRoupa(RoupaBean roupa) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtRemover, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, roupa.getIdRoupa());
			stmt.executeUpdate();

		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao remover uma roupa no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO ao remover uma roupa no banco de dados. Origem=" + e.getMessage());
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

	public RoupaBean pesquisarCliente(int id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		RoupaBean roupaLida;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtConsultar);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				roupaLida = new RoupaBean();
				roupaLida.setIdRoupa(rs.getInt("ID_ROUPA"));
				roupaLida.setNome(rs.getString("NOME_ROUPA"));
				roupaLida.setPrazo(rs.getInt("PRAZO_ROUPA"));
				roupaLida.setValor(rs.getDouble("VALOR_ROUPA"));

				return roupaLida;
			} else {
				throw new RuntimeException("Não existe roupa com este ID. Id=" + id);
			}

		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao consultar uma roupa no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException(
					"Erro de IO ao consultar uma roupa no banco de dados. Origem=" + e.getMessage());
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
