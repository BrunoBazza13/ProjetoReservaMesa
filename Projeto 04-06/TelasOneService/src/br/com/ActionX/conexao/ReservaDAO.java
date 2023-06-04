package br.com.ActionX.conexao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import br.com.ActionX.negocio.Reserva;
import br.com.ActionX.negocio.Usuario;
import java.awt.EventQueue;
import javax.swing.SwingUtilities;
import visual.TelaReserva2;

public class ReservaDAO {

    public boolean realizaReserva(Reserva res) throws SQLException {

        Conexao con = new Conexao();
        PreparedStatement stmt = null;

        try {

            String sql = "";
            sql += "";
            sql = "INSERT INTO tb_reserva" + "(idUsuario, dataReserva, horaReserva, quantidadePessoas, statusReserva)" + "VALUES"
                    + "(?, ?, ?, ?, ?)";

            stmt = con.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, res.getUsuario().getIdUsuario());
            stmt.setObject(2, res.getData());
            stmt.setObject(3, res.getHora());
            stmt.setInt(4, res.getQuantidadePessoa());
            stmt.setString(5, res.getStatus());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {

                ResultSet rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    res.setIdMesa(id);

                    return true;

                } else {
                    rs.close();
                }
            } else {

                throw new SQLException("Erro inespersdo! nenhuma lista afetada");
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        } finally {
            con.getConexao();
        }
        return false;
    }

    public List<Reserva> obterListadeReservas() throws SQLException {

        try {

            Conexao con = new Conexao();
            con.getConexao();

            String sql = "";
            sql += "SELECT b.idMesa, a.idUsuario, a.nome, a.cpf, a.email, b.dataReserva, b.horaReserva, b.quantidadePessoas, b.statusReserva  FROM tb_usuario a"
                    + "INNER JOIN tb_reserva b ON a.idUsuario = b.idUsuario";

            PreparedStatement comando = con.getConexao().prepareStatement(sql);

            ResultSet resultado = comando.executeQuery();

            List<Reserva> listaDeReserva;
            listaDeReserva = new ArrayList<>();

            while (resultado.next()) {

                Reserva reserva = new Reserva();
                Usuario usuario = new Usuario();

                reserva.setIdMesa(resultado.getInt("idMesa"));

                String dataSQL = resultado.getString("dataReserva");
                LocalDate data = LocalDate.parse(dataSQL);
                reserva.setData(data);

                Time time = resultado.getTime("horaReserva");
                LocalTime time2 = time.toLocalTime();
                reserva.setHora(time2);

                reserva.setQuantidadePessoa(resultado.getInt("quantidadePessoas"));
                reserva.setStatus(resultado.getString("statusReserva"));

                usuario.setNome(resultado.getString("nome"));
                usuario.setCpf(resultado.getString("cpf"));
                usuario.setEmail(resultado.getString("email"));

                reserva.setUsuario(usuario);

                listaDeReserva.add(reserva);

            }

            resultado.close();
            comando.close();
            con.getConexao().close();

            return listaDeReserva;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return null;

        }

    }

    public void atualizaStatus(Reserva reserva) throws SQLException {

        Duration duracaoAteCancelamento = Duration.ofMinutes(1);

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            reserva.setStatus("Reserva cancelada");

            try {
                atualizaStatusDeReserva(reserva);

            } catch (SQLException e) {

                e.printStackTrace();
            }

            executorService.shutdown();

        }, duracaoAteCancelamento.toMillis(), TimeUnit.MILLISECONDS);

    }

    public List<Integer> obterListaDeIds() throws SQLException {

        Conexao con = new Conexao();
        con.getConexao();

        String sql = "SELECT idMesa FROM tb_mesa";

        PreparedStatement comando = con.getConexao().prepareStatement(sql);
        ResultSet resultado = comando.executeQuery();

        List<Integer> listaDeIds = new ArrayList<>();
        while (resultado.next()) {
            int id = resultado.getInt("idMesa");
            listaDeIds.add(id);
        }

        resultado.close();

        comando.close();

        con.getConexao().close();

        return listaDeIds;
    }

    public Reserva buscaPorId(int id) throws SQLException {

        Reserva liv = new Reserva();

        Conexao con = new Conexao();
        con.getConexao(); //Obtendo a conexão
        PreparedStatement stmt = null;
        ResultSet resultado = null;

        try {

            String sql = "";
            sql += "";
            sql += "SELECT * from tb_mesa " + "WHERE idMesa = ?";

            stmt = con.getConexao().prepareStatement(sql);

            stmt.setInt(1, id);
            resultado = stmt.executeQuery();

            if (resultado.next()) {

                liv.setIdMesa(resultado.getInt("idMesa"));
                String dataSQL = resultado.getString("dataReserva");
                LocalDate data = LocalDate.parse(dataSQL);
                liv.setData(data);

                Time time = resultado.getTime("horaReserva");
                LocalTime time2 = time.toLocalTime();
                liv.setHora(time2);

                liv.setQuantidadePessoa(resultado.getInt("quantidadePessoas"));
                liv.setStatus(resultado.getString("statusReserva"));

                return liv;

            }
            return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            resultado.close();
            stmt.close();
            con.getConexao().close();
        }
        return null;
    }

    public void atualizaStatusDeReserva(Reserva reserva) throws SQLException {

        Conexao con = new Conexao();

        PreparedStatement stmt = null;

        try {

            String sql = "";
            sql += "";
            sql += "UPDATE tb_reserva " + "SET statusReserva = ? " + "WHERE idMesa = ?";

            stmt = con.getConexao().prepareStatement(sql);

            stmt.setString(1, reserva.getStatus());
            stmt.setInt(2, reserva.getIdMesa());

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            con.getConexao().close();
        }

    }

    public Reserva buscaPorCPF(String cpfUsuario) {

        Reserva liv = new Reserva();

        Conexao con = new Conexao();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            String sql = "";
            sql += "";
            sql += "SELECT r.idMesa, r.dataReserva, r.horaReserva, r.quantidadePessoas, r.statusReserva, u.idUsuario, u.nome, u.cpf, u.email, u.telefone "
                    + "FROM tb_reserva r "
                    + "JOIN tb_usuario u ON r.idUsuario = u.idUsuario "
                    + "WHERE u.cpf = ?";

            stmt = con.getConexao().prepareStatement(sql);

            stmt.setString(1, cpfUsuario);
            rs = stmt.executeQuery();

            if (rs.next()) {

                liv.setIdMesa(rs.getInt("idMesa"));
                String dataSQL = rs.getString("dataReserva");
                LocalDate data = LocalDate.parse(dataSQL);
                liv.setData(data);

                Time time = rs.getTime("horaReserva");
                LocalTime time2 = time.toLocalTime();
                liv.setHora(time2);

                liv.setQuantidadePessoa(rs.getInt("quantidadePessoas"));
                liv.setStatus(rs.getString("statusReserva"));

                Usuario usuario = new Usuario();

                usuario.setNome(rs.getString("nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTelefone(rs.getString("telefone"));

                liv.setUsuario(usuario);

                return liv;

            }
            return null;

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            con.fechaConexao();

        }
        return null;

    }

    public void adicionarFuncionarioReserva(int idFuncionario, int idReserva) {
        Conexao con = new Conexao();
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO tb_funcionario_reserva (idFuncionario, idMesa) VALUES (?, ?)";

            stmt = con.getConexao().prepareStatement(sql);
            stmt.setInt(1, idFuncionario);
            stmt.setInt(2, idReserva);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Funcionário adicionado à reserva com sucesso.");

            } else {
                System.out.println("Erro ao adicionar funcionário à reserva.");

            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());

        } finally {

            if (stmt != null) {

                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("Erro ao fechar o statement: " + e.getMessage());
                }
            }
            con.fechaConexao();
        }
    }

}
