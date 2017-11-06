package br.com.dac.tads.action;

import java.io.Serializable;
import java.util.Date;

public class EntregaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idEntrega;
	private Integer idPedido;
	private String descricao;
	private String endereco;
	private String cliente;
	private Date data;
	private String status;

	public EntregaBean(Integer idEntrega, Integer idPedido, String descricao, String endereco, String clienteId,
			Date data, String status) {
		super();
		this.idEntrega = idEntrega;
		this.idPedido = idPedido;
		this.descricao = descricao;
		this.endereco = endereco;
		this.cliente = clienteId;
		this.data = data;
		this.status = status;
	}

	public EntregaBean() {
		super();
	}

	public Integer getIdEntrega() {
		return idEntrega;
	}

	public void setIdEntrega(Integer idEntrega) {
		this.idEntrega = idEntrega;
	}

	public Integer getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
