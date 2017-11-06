package br.com.dac.lol.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import br.com.dac.lol.action.ClienteBean;

public class ClienteDAO {

	private final String stmtInserir = "INSERT INTO CLIENTE (NOME_CLIENTE, SEXO_CLIENTE, ENDERECO_CLIENTE, TELEFONE_CLIENTE, CPF_CLIENTE, EMAIL_CLIENTE, SENHA_CLIENTE)  VALUES(?,?,?,?,?,?,?)";
	private final String stmtAtualizar = "UPDATE CLIENTE SET ";
	private final String stmtConsultar = "SELECT * FROM CLIENTE WHERE ID_CLIENTE = ?";
	private final String stmtRemover = "DELETE FROM CLIENTE WHERE ID_CLIENTE = ?";

	public void cadastrarCliente(ClienteBean cliente) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtInserir, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getSexo());
			stmt.setString(3, cliente.getEndereco());
			stmt.setString(4, cliente.getTelefone());
			stmt.setString(5, cliente.getCpf());
			stmt.setString(6, cliente.getEmail());
			stmt.setString(7, cliente.getSenha());
			stmt.executeUpdate();
			cliente.setIdCliente(lerIdCliente(stmt));
		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao inserir um cliente no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {			
			throw new RuntimeException("Erro de IO ao inserir um cliente no banco de dados. Origem=" + e.getMessage());
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
	
	private int lerIdCliente(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
    }
	
	public void atualizarCliente(ClienteBean cliente) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(
					stmtAtualizar + 
					"NOME_CLIENTE = " + cliente.getNome() + "," +
					"SEXO_CLIENTE = " + cliente.getSexo() + "," +
					"ENDERECO_CLIENTE = " + cliente.getEndereco() + "," +
					"TELEFONE_CLIENTE = " + cliente.getTelefone() + "," +
					"CPF_CLIENTE = " + cliente.getCpf() + "," +
					"EMAIL_CLIENTE = " + cliente.getEmail() + "," +
					"SENHA_CLIENTE = " + cliente.getSenha() + 
					"WHERE ID_CLIENTE = " + cliente.getIdCliente()
					, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, cliente.getNome());
			stmt.executeUpdate();			
		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao atualizar um cliente no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO ao atualizar um cliente no banco de dados. Origem=" + e.getMessage());
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

	public void removerCliente(ClienteBean cliente) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtRemover, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, cliente.getIdCliente());
			stmt.executeUpdate();
			
		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao remover um cliente no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO ao remover um cliente no banco de dados. Origem=" + e.getMessage());
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

	public ClienteBean pesquisarCliente(int id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ClienteBean clienteLido;
		try {
			con = ConnectionFactory.getConnection();
			stmt = con.prepareStatement(stmtConsultar);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				clienteLido = new ClienteBean();
				clienteLido.setIdCliente(rs.getInt("ID_CLIENTE"));
				clienteLido.setNome(rs.getString("NOME_CLIENTE"));
				clienteLido.setSexo(rs.getString("SEXO_CLIENTE"));
				clienteLido.setEndereco(rs.getString("ENDERECO_CLIENTE"));
				clienteLido.setTelefone(rs.getString("TELEFONE_CLIENTE"));
				clienteLido.setCpf(rs.getString("CPF_CLIENTE"));
				clienteLido.setEmail(rs.getString("EMAIL_CLIENTE"));
				
				return clienteLido;
			} else {
				throw new RuntimeException("Não existe cliente com este id. Id=" + id);
			}

		} catch (SQLException ex) {
			throw new RuntimeException("Erro ao consultar um cliente no banco de dados. Origem=" + ex.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Erro de IO ao consultar um cliente no banco de dados. Origem=" + e.getMessage());
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
