package br.com.dac.tads.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.dac.tads.action.EntregaBean;
import br.com.dac.tads.action.FuncionarioBean;

public class EntregaDAO {

	private final String stmtInserir = "INSERT INTO ENTREGA (ID_PEDIDO, DESCRICAO_ENTREGA, ENDERECO_ENTREGA, CLIENTE_ENTREGA, DATA_ENTREGA, STATUS)  VALUES(?,?,?,?,?,?)";
	private final String stmtMudaStatus = "UPDATE ENTREGA SET STATUS = ";
	private final String stmtListar = "SELECT * FROM ENTREGA";
	private final String stmtListarPorEmpregado = "SELECT * FROM FUNCIONARIO ENTREGA WHERE MATRICULA_FUNCIONARIO = ";
	private final String stmtRemover = "DELETE FROM CLIENTE WHERE ID_CLIENTE = ?";

	public void cadastrarEntrega(EntregaBean entrega) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtInserir, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, entrega.getIdPedido());
			stmt.setString(2, entrega.getDescricao());
			stmt.setString(3, entrega.getEndereco());
			stmt.setString(4, entrega.getCliente());
			stmt.setDate(5, (Date) entrega.getData());
			stmt.setString(6, entrega.getStatus());
			stmt.executeUpdate();
			entrega.setIdEntrega(lerIdEntrega(stmt));
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

	private int lerIdEntrega(PreparedStatement stmt) throws SQLException {
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		return rs.getInt(1);
	}

	public void confirmarEntrega(EntregaBean entrega) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtMudaStatus + "ENTREGUE" + " WHERE ID_ENTREGA = " + entrega.getIdEntrega(),
					PreparedStatement.RETURN_GENERATED_KEYS);

			stmt.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao confirmar entrega no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO ao confirmar entrega no banco de dados. Origem=" + e.getMessage());
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

	public void retornarEntrega(EntregaBean entrega) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(
					stmtMudaStatus + "NÃO ENTREGUE" + " WHERE ID_ENTREGA = " + entrega.getIdEntrega(),
					PreparedStatement.RETURN_GENERATED_KEYS);

			stmt.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao retornar entrega no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO ao retornar entrega no banco de dados. Origem=" + e.getMessage());
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

	public void cancelarEntrega(EntregaBean entrega) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtMudaStatus + "CANCELADO" + "WHERE ID_ENTREGA = " + entrega.getIdEntrega(),
					PreparedStatement.RETURN_GENERATED_KEYS);

			stmt.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao cancelar entrega no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO ao cancelar entrega no banco de dados. Origem=" + e.getMessage());
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

	public List<EntregaBean> listaEntregasFuncionario(FuncionarioBean entregador) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<EntregaBean> lista = new ArrayList();
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtListarPorEmpregado + entregador.getMatricula());
			rs = stmt.executeQuery();
			while (rs.next()) {
				EntregaBean entrega = new EntregaBean();
				entrega.setIdEntrega(rs.getInt("ID_ENTREGA"));
				entrega.setIdPedido(rs.getInt("ID_PEDIDO"));
				entrega.setDescricao(rs.getString("DESCRICAO_ENTREGA"));
				entrega.setEndereco(rs.getString("ENDERECO_ENTREGA"));
				entrega.setCliente(rs.getString("CLIENTE_ENTREGA"));
				entrega.setData(rs.getDate("DATA_ENTREGA"));
				entrega.setStatus(rs.getString("STATUS"));
				lista.add(entrega);
			}
			return lista;
		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao consultar uma lista de entregas por funcionario. Origem=" + ex.getMessage());
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

	public List<EntregaBean> listarEntregas() throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<EntregaBean> lista = new ArrayList();
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtListar);
			rs = stmt.executeQuery();
			while (rs.next()) {
				EntregaBean entrega = new EntregaBean();
				entrega.setIdEntrega(rs.getInt("ID_ENTREGA"));
				entrega.setIdPedido(rs.getInt("ID_PEDIDO"));
				entrega.setDescricao(rs.getString("DESCRICAO_ENTREGA"));
				entrega.setEndereco(rs.getString("ENDERECO_ENTREGA"));
				entrega.setCliente(rs.getString("CLIENTE_ENTREGA"));
				entrega.setData(rs.getDate("DATA_ENTREGA"));
				entrega.setStatus(rs.getString("STATUS"));
				lista.add(entrega);
			}
			return lista;
		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao consultar uma lista de entregas. Origem=" + ex.getMessage());
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
