package br.com.ActionX.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.ActionX.conexao.FuncionarioReservaDAO;
import br.com.ActionX.conexao.FuncionariosDAO;
import br.com.ActionX.conexao.ReservaDAO;
import br.com.ActionX.conexao.UsuarioDAO;
import br.com.ActionX.negocio.Funcionario;
import br.com.ActionX.negocio.Reserva;
import br.com.ActionX.negocio.Usuario;

public class ListagemController {

	Scanner entrada = new Scanner(System.in);

	public void listaTodosFuncionarios() throws SQLException {

		FuncionariosDAO funcionario = new FuncionariosDAO();

		List<Funcionario> listaDeFuncionrios;
		listaDeFuncionrios = new ArrayList<>();

		listaDeFuncionrios = funcionario.obterListaDeFuncionarios();

		System.out.println("\nID_Funcionario\t|\tNome\t\t|\tE-mail\t\t|\tMatricula\t|\tTelefone\t|\t");
		// System.out.println("------------\t|\t----\t|\t---\t|\t------\t|\t--------\t|\t");

		for (Funcionario funcionarioLocal : listaDeFuncionrios) {

			System.out.print(funcionarioLocal.getIdFuncionario() + "\t\t|");
			System.out.print(funcionarioLocal.getNome() + "\t\t|");
			System.out.print(funcionarioLocal.getEmail() + "\t|\t");
			System.out.println(funcionarioLocal.getMatricula() + "\t|\t");
			System.out.print(funcionarioLocal.getTelefone() + "\t|\t");
			System.out.println("\n");

		}

	}

	public void listaTodosUsuarios() throws SQLException {

		UsuarioDAO usuario = new UsuarioDAO();

		List<Usuario> listaDeUsuarios;
		listaDeUsuarios = new ArrayList<>();

		listaDeUsuarios = usuario.obterListaDeUsuarios();

		System.out.println("\nID_Usuario\t|\tNome\t|\tCPF\t|\tE-mail\t|\tTelefone\t|\t");
		// System.out.println("------------\t|\t----\t|\t---\t|\t------\t|\t--------\t|\t");

		for (Usuario usuarioLocal : listaDeUsuarios) {
			System.out.print(usuarioLocal.getIdUsuario()+ "\t|\t");
			System.out.print(usuarioLocal.getNome() + "\t|\t");
			System.out.print(usuarioLocal.getCpf() + "\t|\t");
			System.out.print(usuarioLocal.getEmail() + "\t|\t");
			System.out.print(usuarioLocal.getTelefone() + "\t|\t");
			System.out.println("\n");

		}

	}

//	public void listaTodasReservar() throws SQLException, InterruptedException {
//
//		ReservaDAO reserva = new ReservaDAO();
//
//		List<Reserva> listaDeReserva;
//		listaDeReserva = new ArrayList<>();
//
//		listaDeReserva = reserva.obterListadeReservas();
//
//		System.out.println("\nID_Mesa\t|\tData da Reserva\t|\tHoras da Reserva\t|\tQuantidade de Pessoas\t|\t Status");
//	
//
//		
//	
//		for (Reserva reservaLocal : listaDeReserva) {
//			
//			System.out.print(reservaLocal.getIdMesa() + "\t|\t");
//			System.out.print(reservaLocal.getData() + "\t|\t");
//			System.out.print(reservaLocal.getHora() + "\t\t\t|\t");
//			System.out.print(reservaLocal.getQuantidadePessoa() + "\t\t\t|\t");
//			System.out.println(reservaLocal.getStatus() + "\t\t|\t");
//			System.out.println("\n");
//
//		}
//	}

	public void listaTodasReservasPorFuncionario() throws SQLException, InterruptedException {

		FuncionarioReservaDAO reserva = new FuncionarioReservaDAO();

		List<Reserva> listaDeReserva;
		listaDeReserva = new ArrayList<>();

		listaDeReserva = reserva.obterReservasPorFuncionario(1);

		System.out.println("\nID_Mesa\t|\tData da Reserva\t|\tHoras da Reserva\t|\tQuantidade de Pessoas\t|\t Status");

		for (Reserva reservaLocal : listaDeReserva) {

			System.out.print(reservaLocal.getIdMesa() + "\t|\t");
			System.out.print(reservaLocal.getData() + "\t|\t");
			System.out.print(reservaLocal.getHora() + "\t\t\t|\t");
			System.out.print(reservaLocal.getQuantidadePessoa() + "\t\t\t|\t");
			System.out.println(reservaLocal.getStatus() + "\t\t|\t");
			System.out.println("\n");

		}
	}

	public void listaPorcCpf() throws SQLException, InterruptedException {

		ReservaDAO reserva = new ReservaDAO();

		UsuarioDAO usuario = new UsuarioDAO();

		Usuario usuarioCPF = new Usuario();

		Reserva reservaCPF = new Reserva();

		System.out.println("nforme seu cpf");
		String cpf = entrada.nextLine();

		reservaCPF = reserva.buscaPorCPF(cpf);

		usuarioCPF = usuario.obterUsuarioPorCPF(cpf);

		System.out.println(
				"\nID_Mesa\t|\tData da Reserva\t|\tHoras da Reserva\t|\tQuantidade de Pessoas\t|\tStatus\t|\tNome\t|\tCPF\t|\tE-mail\t|\tTelefone");

		if (reserva != null && usuario != null) {
			System.out.print(reservaCPF.getIdMesa() + "\t|\t");
			System.out.print(reservaCPF.getData() + "\t|\t");
			System.out.print(reservaCPF.getHora() + "\t\t\t|\t");
			System.out.print(reservaCPF.getQuantidadePessoa() + "\t\t\t|\t");
			System.out.print(reservaCPF.getStatus() + "\t\t|\t");
			System.out.print(usuarioCPF.getNome() + "\t|\t");
			System.out.print(usuarioCPF.getCpf() + "\t|\t");
			System.out.print(usuarioCPF.getEmail() + "\t|\t");
			System.out.print(usuarioCPF.getTelefone() + "\t|\t");

		} else {
			System.out.println("Erro ao procurar cpf");

		}
	}
}
