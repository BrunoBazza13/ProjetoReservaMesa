package br.com.ActionX.negocio;



public class Funcionario extends Pessoa {

	private int idFuncionario;
	private int idLogin;
	private int re;
	
	
	
	public int getMatricula() {
		return re;
	}
	public void setMatricula(int re) {
		this.re = re;
	}
	public int getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	public int getIdLogin() {
		return idLogin;
	}
	public void setIdLogin(int idLogin) {
		this.idLogin = idLogin;
	}
	
}
