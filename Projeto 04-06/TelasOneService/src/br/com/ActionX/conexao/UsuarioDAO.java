package br.com.ActionX.conexao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ActionX.negocio.Usuario;

public class UsuarioDAO {

	public int insereUsuario(Usuario usu) {

		Conexao conexao = new Conexao();
		PreparedStatement st = null;

		try {

			String sql = "";
			sql += "";
			sql = "INSERT INTO tb_usuario" + "(nome, cpf, email, telefone)" + "VALUES" + "(?, ?, ?, ?)";

			st = conexao.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			st.setString(1, usu.getNome());
			st.setString(2, usu.getCpf());
			st.setString(3, usu.getEmail());
			st.setString(4, usu.getTelefone());
			//st.setInt(5, usu.getReserva().getIdMesa());

			int linhasAfetadas = st.executeUpdate();

			if (linhasAfetadas > 0) {

				ResultSet rs = st.getGeneratedKeys();

				if (rs.next()) {
					int id = rs.getInt(1);
					usu.setIdUsuario(id);

					return id;

				} else
					rs.close();
			} else {

				throw new SQLException("Erro inespersdo! nenhuma lista afetada");
			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());
		} finally {
			conexao.getConexao();
		}
		return -1;
	}

	public List<Usuario> obterListaDeUsuarios() throws SQLException {

		try {

			Conexao con = new Conexao();
			con.getConexao();

			String sql = " select * from tb_usuario; ";

			PreparedStatement comando = con.getConexao().prepareStatement(sql);
			ResultSet resultado = comando.executeQuery();

			List<Usuario> listaDeUsuarios;
			listaDeUsuarios = new ArrayList<>();
			while (resultado.next()) {

				Usuario usuario = new Usuario();

				usuario.setIdUsuario(resultado.getInt("idUsuario"));
				usuario.setNome(resultado.getString("nome"));
				usuario.setCpf(resultado.getString("cpf"));
				usuario.setEmail(resultado.getString("email"));
				usuario.setTelefone(resultado.getString("telefone"));

				listaDeUsuarios.add(usuario); // estou add na lista de usuarios os valores pegos da tabela usuario e
												// armazenando nos atributos

			}

			resultado.close();
			comando.close();
			con.getConexao().close();

			return listaDeUsuarios;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	public Usuario obterUsuarioPorCPF(String cpf) throws SQLException {
	    try {
	        Usuario usuario = new Usuario();

	        Conexao con = new Conexao();
	        con.getConexao();

	        String sql = "SELECT * FROM tb_usuario WHERE cpf = ?";

	        PreparedStatement comando = con.getConexao().prepareStatement(sql);
	        comando.setString(1, cpf);
	        ResultSet resultado = comando.executeQuery();

	        if (resultado.next()) {
	            usuario.setIdUsuario(resultado.getInt("idUsuario"));
	            usuario.setNome(resultado.getString("nome"));
	            usuario.setCpf(resultado.getString("cpf"));
	            usuario.setEmail(resultado.getString("email"));
	            usuario.setTelefone(resultado.getString("telefone"));
	        }

	        resultado.close();
	        comando.close();
	        con.getConexao().close();

	        return usuario;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return null;
	    }
	}

//	public boolean atualizaIdUsuario(int mesaId, int usuarioId) {
//	    Conexao conexao = new Conexao();
//	    PreparedStatement st = null;
//
//	    try {
//	        String sql = "UPDATE tb_mesa SET idUsuario = ? WHERE idMesa = ?";
//	        st = conexao.getConexao().prepareStatement(sql);
//
//	        st.setInt(1, usuarioId);
//	        st.setInt(2, mesaId);
//
//	        int linhasAfetadas = st.executeUpdate();
//
//	        return linhasAfetadas > 0;
//	    } catch (SQLException e) {
//	        System.out.println(e.getMessage());
//	    } finally {
//	        conexao.fechaConexao();
//	    }
//
//	    return false;
//	}

}
