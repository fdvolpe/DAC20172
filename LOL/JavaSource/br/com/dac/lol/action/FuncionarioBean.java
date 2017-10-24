package br.com.dac.lol.action;

import java.util.Date;

public class FuncionarioBean extends Pessoa {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String matricula;
	private Date dataNasc;

	public FuncionarioBean(String nome, String email, String senha, String matricula, Date dataNasc) {
		super(nome, email, senha);
		this.matricula = matricula;
		this.dataNasc = dataNasc;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

}
