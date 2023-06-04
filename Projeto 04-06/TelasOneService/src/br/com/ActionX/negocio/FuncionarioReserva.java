package br.com.ActionX.negocio;

public class FuncionarioReserva {

	private int idFuncionario;
	private int idMesa;

	private FuncionarioReserva funcionario;
	
	
	
	public FuncionarioReserva getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioReserva funcionario) {
		this.funcionario = funcionario;
	}

	public FuncionarioReserva() {

	}

	public FuncionarioReserva(int idFuncionario, int idMesa) {

		this.idFuncionario = idFuncionario;
		this.idMesa = idMesa;
	}

	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public int getIdMesa() {
		return idMesa;
	}

	public void setIdMesa(int idMesa) {
		this.idMesa = idMesa;
	}

}
