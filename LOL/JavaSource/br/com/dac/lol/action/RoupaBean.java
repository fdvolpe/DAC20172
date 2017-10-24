package br.com.dac.lol.action;

import java.io.Serializable;

public class RoupaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idRoupa;
	private String nome;
	private Integer prazo;
	private Double valor;
	private FuncionarioBean funcionarioCriador;

	public RoupaBean(Integer idRoupa, String nome, Integer prazo, Double valor, FuncionarioBean funcionarioCriador) {
		super();
		this.idRoupa = idRoupa;
		this.nome = nome;
		this.prazo = prazo;
		this.valor = valor;
		this.funcionarioCriador = funcionarioCriador;
	}

	public Integer getIdRoupa() {
		return idRoupa;
	}

	public void setIdRoupa(Integer idRoupa) {
		this.idRoupa = idRoupa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getPrazo() {
		return prazo;
	}

	public void setPrazo(Integer prazo) {
		this.prazo = prazo;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public FuncionarioBean getFuncionarioCriador() {
		return funcionarioCriador;
	}

	public void setFuncionarioCriador(FuncionarioBean funcionarioCriador) {
		this.funcionarioCriador = funcionarioCriador;
	}

}
