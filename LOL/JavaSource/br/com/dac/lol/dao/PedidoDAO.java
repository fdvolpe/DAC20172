package br.com.dac.lol.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.dac.lol.action.ClienteBean;
import br.com.dac.lol.action.PedidoBean;

public class PedidoDAO {
	private final String stmtInserirPedido = "INSERT INTO PEDIDO (ENDERECO_PEDIDO, DATA_PEDIDO, STATUS, PRAZO_PEDIDO, VALOR_PEDIDO, ID_CLIENTE)  VALUES (?,?,?,?,?,?)";
	private final String stmtInserirPedidoRoupa = "INSERT INTO PEDIDO_ROUPA (ID_PEDIDO, ID_ROUPA)  VALUES (?,?)";
	private final String stmtInserirPedidoFuncionario = "INSERT INTO PEDIDO_FUNCIONARIO (MATRICULA_FUNCIONARIO, ID_PEDIDO)  VALUES (?,?)";
	private final String stmtConfirmar = "UPDATE PEDIDO SET STATUS = ";
	private final String stmtConsultarPedidoCliente = "SELECT ID_PEDIDO, ENDERECO_PEDIDO, DATA_PEDIDO, STATUS, PRAZO_PEDIDO, VALOR_PEDIDO, ID_CLIENTE FROM PEDIDO WHERE ID_CLIENTE = ?";
	private final String stmtConsultarPedido = "SELECT ID_PEDIDO, ENDERECO_PEDIDO, DATA_PEDIDO, STATUS, PRAZO_PEDIDO, VALOR_PEDIDO, ID_CLIENTE FROM PEDIDO WHERE ID_PEDIDO = ?";
	private final String stmtConsultarPedidosEmpresa = "SELECT ID_PEDIDO, ENDERECO_PEDIDO, DATA_PEDIDO, STATUS, PRAZO_PEDIDO, VALOR_PEDIDO, ID_CLIENTE FROM PEDIDO";
	private final String stmtCancelar = "DELETE FROM PEDIDO WHERE ID_PEDIDO = ?";

	public void cadastrarPedido(PedidoBean pedido) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtInserirPedido, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, pedido.getEndereco());
			stmt.setDate(2, (Date) pedido.getDataPedido());
			stmt.setString(3, pedido.getStatus());
			stmt.setInt(4, pedido.getPrazoTotal());
			stmt.setDouble(5, pedido.getValorTotal());
			stmt.setInt(6, pedido.getClientePedido().getIdCliente());
			stmt.executeUpdate();
			pedido.setIdPedido(lerIdPedido(stmt));
			
			stmt = con.prepareStatement(stmtInserirPedidoRoupa, PreparedStatement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < pedido.getRoupas().size(); i++) {
			stmt.setInt(1, pedido.getIdPedido());
			stmt.setInt(2, pedido.getRoupas().get(i).getIdRoupa());
			}			
			stmt.executeUpdate();
			
			stmt = con.prepareStatement(stmtInserirPedidoFuncionario, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, pedido.getFuncionarioPedido().getMatricula());
			stmt.setInt(2, pedido.getIdPedido());					
			stmt.executeUpdate();
			
		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao inserir um pedido no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO ao inserir um pedido no banco de dados. Origem=" + e.getMessage());
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

	private int lerIdPedido(PreparedStatement stmt) throws SQLException {
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		return rs.getInt(1);
	}

	public void cancelarPedido(PedidoBean pedido) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtCancelar, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, pedido.getIdPedido());
			stmt.executeUpdate();

		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao cancelar um pedido no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO ao cancelar um pedido no banco de dados. Origem=" + e.getMessage());
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

	public PedidoBean visualizarPedidosCliente(int id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PedidoBean clienteLido;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtConsultarPedidoCliente);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				clienteLido = new PedidoBean();
				clienteLido.setIdPedido(rs.getInt("ID_PEDIDO"));
				clienteLido.setEndereco(rs.getString("ENDERECO_PEDIDO"));
				clienteLido.setDataPedido(rs.getDate("DATA_PEDIDO"));
				clienteLido.setStatus(rs.getString("STATUS"));
				clienteLido.setPrazoTotal(rs.getInt("PRAZO_PEDIDO"));
				clienteLido.setValorTotal(rs.getDouble("VALOR_PEDIDO"));
				
				return clienteLido;
			} else {
				throw new RuntimeException("Não existe pedido com este cliente id. Id=" + id);
			}

		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao consultar um pedido no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException(
					"Erro de IO ao consultar um pedido no banco de dados. Origem=" + e.getMessage());
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
	
	public PedidoBean concultarPedido(int id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PedidoBean clienteLido;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtConsultarPedido);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				clienteLido = new PedidoBean();
				clienteLido.setIdPedido(rs.getInt("ID_PEDIDO"));
				clienteLido.setEndereco(rs.getString("ENDERECO_PEDIDO"));
				clienteLido.setDataPedido(rs.getDate("DATA_PEDIDO"));
				clienteLido.setStatus(rs.getString("STATUS"));
				clienteLido.setPrazoTotal(rs.getInt("PRAZO_PEDIDO"));
				clienteLido.setValorTotal(rs.getDouble("VALOR_PEDIDO"));
				
				return clienteLido;
			} else {
				throw new RuntimeException("Não existe pedido com este id. Id=" + id);
			}

		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao consultar um pedido no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException(
					"Erro de IO ao consultar um pedido no banco de dados. Origem=" + e.getMessage());
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
	
	public PedidoBean visualizarPedidosEmpresa() {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PedidoBean clienteLido;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtConsultarPedidosEmpresa);			
			rs = stmt.executeQuery();
			if (rs.next()) {
				clienteLido = new PedidoBean();
				clienteLido.setIdPedido(rs.getInt("ID_PEDIDO"));
				clienteLido.setEndereco(rs.getString("ENDERECO_PEDIDO"));
				clienteLido.setDataPedido(rs.getDate("DATA_PEDIDO"));
				clienteLido.setStatus(rs.getString("STATUS"));
				clienteLido.setPrazoTotal(rs.getInt("PRAZO_PEDIDO"));
				clienteLido.setValorTotal(rs.getDouble("VALOR_PEDIDO"));
				
				return clienteLido;
			} else {
				throw new RuntimeException("Não existe pedido com este cliente id.");
			}

		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao consultar um pedido no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException(
					"Erro de IO ao consultar um pedido no banco de dados. Origem=" + e.getMessage());
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
	
	public void confirmarLavagem(PedidoBean pedido) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(
					stmtConfirmar + 
					"CONFIRMADO " +
					"WHERE ID_PEDIDO = ?" 
					, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, pedido.getIdPedido());
			stmt.executeUpdate();			
		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao atualizar um pedido no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO ao atualizar um pedido no banco de dados. Origem=" + e.getMessage());
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
	
	public void confirmarPagamento(PedidoBean pedido) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(
					stmtConfirmar + 
					"PAGO " +
					"WHERE ID_PEDIDO = ?" 
					, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, pedido.getIdPedido());
			stmt.executeUpdate();			
		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao atualizar um pedido no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO ao atualizar um pedido no banco de dados. Origem=" + e.getMessage());
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

}
