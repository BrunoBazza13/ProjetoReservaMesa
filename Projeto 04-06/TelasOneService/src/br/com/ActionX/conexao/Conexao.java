package br.com.ActionX.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	
	private String statusConexao = "nao conectado";

	public Connection getConexao() {

		Connection conexao = null;

		try {

			String nomeDriver =  "com.mysql.cj.jdbc.Driver";

			Class.forName(nomeDriver);

			String servidor = "localhost";
			String schema = "db_reserva";
			String url = "jdbc:mysql://" + servidor + "/" + schema;
			String usuario = "root";
			String senha = "modi091325";

			conexao = (Connection) DriverManager.getConnection(url, usuario, senha);

			if (conexao != null) {
				statusConexao = "conectado";
		
			} else {
				statusConexao = "nao conectado";
			}

			return conexao;

		} catch (ClassNotFoundException e) {
			System.out.println("drive de conexao nao encontrado");
			return null;
		
		} catch (SQLException e) {
			System.out.println("falha na conexao");
			System.out.println(e.getMessage());
			return null;

		}

	}

	public String getStatusConexao() {
		return statusConexao;

	}

	public boolean fechaConexao() {
		
		try {
		
			getConexao().close();
			statusConexao = "conexao fechada";
			return true;
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;

		}

	}

	public Connection reiniciaConexao() {
		fechaConexao();
		return getConexao();

	}

	
}
