package br.com.ActionX.conexao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import br.com.ActionX.negocio.Reserva;

public class FuncionarioReservaDAO {

	
	public List<Reserva> obterReservasPorFuncionario(int idFuncionario) throws SQLException {
	 
		List<Reserva> reservas = new ArrayList<>();

		
		
	    Conexao conexao = new Conexao();
	    PreparedStatement st = null;
	    ResultSet rs = null;
            
            
	    try {
	        String sql = "SELECT r.* FROM tb_reserva r " +
	                     "INNER JOIN tb_funcionario_reserva fr ON r.idMesa = fr.idMesa " +
	                     "WHERE fr.idFuncionario = ?";
	       
	        st = conexao.getConexao().prepareStatement(sql);
	        st.setInt(1, idFuncionario);
	        rs = st.executeQuery();

	        while (rs.next()) {
	            Reserva reserva = new Reserva();

	           
	            reserva.setIdMesa(rs.getInt("idMesa"));
	          
	            String dataSQL = rs.getString("dataReserva");
				LocalDate data = LocalDate.parse(dataSQL);
				reserva.setData(data);

				Time time = rs.getTime("horaReserva");
				LocalTime time2 = time.toLocalTime();
				reserva.setHora(time2);

				reserva.setQuantidadePessoa(rs.getInt("quantidadePessoas"));
				reserva.setStatus(rs.getString("statusReserva"));
	            
	            
	            
	            reservas.add(reserva);
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    } finally {
	        // Feche os recursos (ResultSet, PreparedStatement, Connection)
	        if (rs != null) {
	            rs.close();
	        }
	        if (st != null) {
	            st.close();
	        }
	        conexao.getConexao().close();
	    }

	    return reservas;
	}
	
	
}
