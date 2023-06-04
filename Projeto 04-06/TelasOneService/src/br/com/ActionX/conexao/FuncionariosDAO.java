package br.com.ActionX.conexao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import br.com.ActionX.negocio.Funcionario;

public class FuncionariosDAO {

	public int insereFuncionario(Funcionario fun) throws SQLException {

		Conexao conexao = new Conexao();
		PreparedStatement st = null;

		try {

			String sql = "";
			sql += "";
			sql = "INSERT INTO tb_funcionario"
					+ "(nome, email, matricula, telefone)"
					+ "VALUES" + "(?, ?, ?, ?)";

			st = conexao.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			st.setString(1, fun.getNome());
			st.setString(2, fun.getEmail());
			st.setInt(3, fun.getMatricula());
			st.setString(4, fun.getTelefone());
			
			int linhasAfetadas = st.executeUpdate();

			if (linhasAfetadas > 0) {

				ResultSet rs = st.getGeneratedKeys();

				if (rs.next()) {
					int id = rs.getInt(1);
					fun.setIdFuncionario(id);

					return id;

				}
				rs.close();
			} else {

				throw new SQLException("Erro inespersdo! nenhuma lista afetada");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			conexao.fechaConexao();
		}
		return -1;
	}

	public List<Funcionario> obterListaDeFuncionarios() throws SQLException {

		try {

			Conexao con = new Conexao();
			con.getConexao();

			String sql = " select * from tb_funcionario; ";

			PreparedStatement comando = con.getConexao().prepareStatement(sql);
			ResultSet resultado = comando.executeQuery();

			List<Funcionario> listaDeFuncionarios;
			listaDeFuncionarios = new ArrayList<>();

			while (resultado.next()) {

				Funcionario funcionario = new Funcionario();

				funcionario.setIdFuncionario(resultado.getInt("idFuncionario"));
				funcionario.setNome(resultado.getString("nome"));
				funcionario.setEmail(resultado.getString("email"));
				funcionario.setTelefone(resultado.getString("telefone"));
				

				listaDeFuncionarios.add(funcionario);

			}

			resultado.close();
			comando.close();
			con.getConexao().close();

			return listaDeFuncionarios;

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			return null;

		}

	}

	
	
	
}
