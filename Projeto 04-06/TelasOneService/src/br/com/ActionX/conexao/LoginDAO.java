package br.com.ActionX.conexao;

import br.com.ActionX.negocio.Funcionario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.ActionX.negocio.Login;
import java.util.ArrayList;
import java.util.List;

public class LoginDAO {

	public boolean insereLogin(Login fun) {

		Conexao con = new Conexao();
		PreparedStatement st = null;

		try {

			String sql = "";
			sql += "";
			sql = "INSERT INTO tb_login " + "(email, senha, idFuncionario)" + "VALUES" + "(?, ?, ?)";

			st = con.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			st.setString(1, fun.getEmail());
			st.setString(2, fun.getSenha());
			st.setInt(3, fun.getIdFuncionario());

			int linhasAfetadas = st.executeUpdate();

			if (linhasAfetadas > 0) {

				ResultSet rs = st.getGeneratedKeys();

				if (rs.next()) {
					int id = rs.getInt(1);
					fun.setIdLogin(id);

					return true;

				} else
					rs.close();
			} else {

				throw new SQLException("Erro inespersdo! nenhuma lista afetada");
			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

		} finally {

			con.getConexao();
		}

		return false;

	}

	public List<Login> buscaListaLogin() throws SQLException {

        Conexao con = new Conexao();
        con.getConexao(); //Obtendo a conexão
        PreparedStatement stmt = null;
        ResultSet resultado = null;

        //Prepara a lista de carros para retornar
        List<Login> listaDeLogins = new ArrayList<>();

        try {
            // Comando SQL na base = tabela de carros
            String sql = "select * from logins";

            //Executa a query (comando SQL)
            stmt = con.getConexao().prepareStatement(sql);
            resultado = stmt.executeQuery();

            //Para cada item retornado no comando (SQL) faça...
            while (resultado.next()) {
                Login login = new Login(); //Criando uma instância, novo carro na memória

                login.setIdLogin(resultado.getInt("idLogin"));
                login.setIdFuncionario(resultado.getInt("idFuncionario"));
                login.setEmail(resultado.getString("Email"));
                login.setSenha(resultado.getString("Senha"));
                
                listaDeLogins.add(login);
            }

            // Retorna a lista de carros
            return listaDeLogins;
        } catch (SQLException e) { //Caso dê alguma exceção
            System.out.println(e.getMessage());
            return null;
        } finally {
            // Após terminar, fecha a conexão, stmt, rs
            resultado.close();
            stmt.close();
            con.getConexao().close();
        }
        
    }
    
   public Login buscaLogin() throws SQLException {

        
                
      Conexao conexao = new Conexao();
		conexao.getConexao();

		try {

			String sql = "SELEC * from tb_login WHERE email = ? AND senha = ?";

			PreparedStatement comando = conexao.getConexao().prepareStatement(sql);
			ResultSet resultado = comando.executeQuery();

			Login login = new Login();

			if (resultado.next()) {

				
				login.setEmail(resultado.getString("email"));
				login.setSenha(resultado.getString("senha"));

			}

			resultado.close();
			comando.close();
			conexao.getConexao().close();

			return login;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
	
		}
		
	}   
    
}

