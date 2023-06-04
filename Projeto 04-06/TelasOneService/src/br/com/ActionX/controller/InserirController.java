package br.com.ActionX.controller;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import br.com.ActionX.conexao.FuncionariosDAO;
import br.com.ActionX.conexao.LoginDAO;
import br.com.ActionX.conexao.ReservaDAO;
import br.com.ActionX.conexao.UsuarioDAO;
import br.com.ActionX.negocio.Funcionario;
import br.com.ActionX.negocio.FuncionarioReserva;
import br.com.ActionX.negocio.Login;
import br.com.ActionX.negocio.Reserva;
import br.com.ActionX.negocio.Usuario;
import javax.swing.JOptionPane;

public class InserirController {

	Scanner entrada = new Scanner(System.in);

	public void buscaReservaPorFuncionario() throws SQLException {

		FuncionarioReserva funcionarioR = new FuncionarioReserva();

		System.out.println("Informe seu id para buscar uma lista");
		int id = entrada.nextInt();

		funcionarioR.setIdFuncionario(id);
		funcionarioR.setFuncionario(funcionarioR);

	}

	public void insereLogin(int idFuncionario, Funcionario funcionario) {

		Login loginFuncionario = new Login();
		LoginDAO lD = new LoginDAO();

		String email = "";
		do {

			System.out.print("\nEmail: ");
			email = entrada.nextLine();

		} while (!email.equalsIgnoreCase(funcionario.getEmail()));

		System.out.print("\nSenha: ");
		String senha = entrada.nextLine();

		loginFuncionario.setEmail(email);
		loginFuncionario.setSenha(senha);
		loginFuncionario.setIdFuncionario(idFuncionario);

		lD.insereLogin(loginFuncionario);

	}
        
        public Login ValidaLogin(String email, String senha) throws SQLException{
        
        Login result = new Login();
        LoginDAO loginDAO = new LoginDAO();
        
        result = loginDAO.buscaLogin();
        
        if(result == null){
            return null;
        }
        if(result.getEmail().equals(email) && result.getSenha().equals(senha)){
            return result;
        }
       
        return null;
    }

	public void atualizaReserva() {

		try {

			Reserva reserva = new Reserva();

			System.out.println("\n --- ATUALIZAR STATUS DA RESERVA ---");

			System.out.println("Infrome o ID da reserva que deeja ser atualizado");
			int id = entrada.nextInt();

			ReservaDAO lDAO = new ReservaDAO();

			reserva = lDAO.buscaPorId(id);

			System.out.printf("Informe o status da reserva: ");
			entrada.nextLine();
			String status = entrada.nextLine();

			reserva.setStatus(status);

			lDAO.atualizaStatusDeReserva(reserva);

			System.out.println("reserva atualizada com sucesso");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public Usuario insereUsuario(int mesa) {

		try {

			Usuario usuario;
			usuario = new Usuario();

			System.out.println("\n--- INSIRA SEUS DADOS---");

			entrada.nextLine();
			System.out.print("\nIforme o nome: ");
			String nome = entrada.nextLine().toUpperCase();

			System.out.print("Informe o cpf: ");
			String cpf = entrada.nextLine().toUpperCase();

			System.out.print("Informe o e-mail: ");
			String email = entrada.nextLine().toUpperCase();

			System.out.print("Informe o telefone: ");
			String telefone = entrada.nextLine().toUpperCase();

			usuario.setNome(nome);
			usuario.setCpf(cpf);
			usuario.setEmail(email);
			usuario.setTelefone(telefone);
			usuario.setIdMesa(mesa);

			UsuarioDAO uD = new UsuarioDAO();
			uD.insereUsuario(usuario);
			System.out.println("\n Usuario cadastrado com sucesso");

			return usuario;

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		return null;

	}

	public void insereFuncionario() {

		try {

			Funcionario funcionario = new Funcionario();

			System.out.println("\n--- INSIRA OS DADOS DO FUNCIONARIO---");

			System.out.print("\nIforme o nome do funcionario: ");
			String nome = entrada.nextLine().toUpperCase();

			System.out.print("\nInforme seu e-mail: ");
			String email = entrada.nextLine();

			System.out.print("\nInforme sua matricula: ");
			int matricula = entrada.nextInt();

			entrada.nextLine();

			System.out.print("\nInforme o telefone: ");
			String telefone = entrada.nextLine().toUpperCase();

			funcionario.setNome(nome);
			funcionario.setEmail(email);
			funcionario.setMatricula(matricula);
			funcionario.setTelefone(telefone);

			FuncionariosDAO lD = new FuncionariosDAO(); // criando obj do tipo FuncionariosDAO

			int idFuncionario = lD.insereFuncionario(funcionario);

			System.out.println("Cadastre um email e uma senha:");

			insereLogin(idFuncionario, funcionario);

			System.out.println("\n Funcionario cadastrado com sucesso");

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

	}

	public void realizarReserva() {

		try {

			Reserva reserva = new Reserva();

			System.out.println("\n--- RESERVA DE MESA ---");

			System.out.print("\nInforme uma data: ");
			LocalDate dataReserva = LocalDate.parse(entrada.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

			System.out.print("Informe um horario: ");
			LocalTime horarioReserva = LocalTime.parse(entrada.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));

			System.out.print("Informe quantidade de pessoas: ");
			int quantidadePessoas = entrada.nextInt();

			String status = "Em aberto";

			reserva.setData(dataReserva);
			reserva.setHora(horarioReserva);
			reserva.setQuantidadePessoa(quantidadePessoas);
			reserva.setStatus(status);

			ReservaDAO rD = new ReservaDAO();

			boolean mesaId = rD.realizaReserva(reserva);

			int idFuncionario = 1;


			if (mesaId != false) {

			//	insereUsuario(mesaId);
				

				LocalDateTime dataHoraReserva = LocalDateTime.of(dataReserva, horarioReserva);
				LocalDateTime dataHoraAtual = LocalDateTime.now();

				Duration duracaoAteReserva = Duration.between(dataHoraAtual, dataHoraReserva);
				Duration duracaoAteCancelamento = Duration.ofMinutes(1);

				if (duracaoAteReserva.isNegative()) {
					reserva.setStatus("Reserva cancelada");
					rD.atualizaStatusDeReserva(reserva);

				} else if (duracaoAteReserva.compareTo(duracaoAteCancelamento) <= 0) {
					reserva.setStatus("Em aberto");

					rD.atualizaStatus(reserva);

				} else {
					reserva.setStatus("Reservado");
					;

					rD.atualizaStatus(reserva);

				}
			} else {
				System.out.println("Erro ao realizar reserva");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}