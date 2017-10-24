package br.com.dac.lol.action;

public class ClienteBean extends Pessoa {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cpf;
	private String sexo;
	private String endereco;
	private String telefone;

	public ClienteBean(String nome, String email, String senha, String cpf, String sexo, String endereco,
			String telefone) {
		super(nome, email, senha);
		this.cpf = cpf;
		this.sexo = sexo;
		this.endereco = endereco;
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
