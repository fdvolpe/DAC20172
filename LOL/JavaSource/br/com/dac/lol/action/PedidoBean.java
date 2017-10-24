package br.com.dac.lol.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class PedidoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idPedido;
	private String endereco;
	private Date dataPedido;
	private String status;
	private Integer prazoTotal;
	private Double valorTotal;
	private ArrayList<RoupaBean> roupas;

	public PedidoBean(Integer idPedido, String endereco, Date dataPedido, String status, Integer prazoTotal,
			Double valorTotal, ArrayList<RoupaBean> roupas) {
		super();
		this.idPedido = idPedido;
		this.endereco = endereco;
		this.dataPedido = dataPedido;
		this.status = status;
		this.prazoTotal = prazoTotal;
		this.valorTotal = valorTotal;
		this.roupas = roupas;
	}

	public Integer getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPrazoTotal() {
		return prazoTotal;
	}

	public void setPrazoTotal(Integer prazoTotal) {
		this.prazoTotal = prazoTotal;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public ArrayList<RoupaBean> getRoupas() {
		return roupas;
	}

	public void setRoupas(ArrayList<RoupaBean> roupas) {
		this.roupas = roupas;
	}

}
