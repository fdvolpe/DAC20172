package br.com.dac.lol.action;

import java.util.Date;

public class FuncionarioBean extends Pessoa {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer matricula;
	private Date dataNasc;
	private String dataNascString;

	public FuncionarioBean(String nome, String email, String senha, Integer matricula, Date dataNasc) {
		super(nome, email, senha);
		this.matricula = matricula;
		this.dataNasc = dataNasc;
	}

	public FuncionarioBean(String nome, String email, String senha) {
		super(nome, email, senha);
	}
	
	public FuncionarioBean() {
		
	};

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getDataNascString() {
		return dataNascString;
	}

	public void setDataNascString(String dataNascString) {
		this.dataNascString = dataNascString;
	}

}
