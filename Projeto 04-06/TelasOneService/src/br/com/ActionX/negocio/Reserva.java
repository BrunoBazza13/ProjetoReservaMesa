package br.com.ActionX.negocio;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {

    private int idUsuario;
    private int idMesa;
    private LocalDate data;
    private LocalTime hora;
    private int quantidadePessoa;
    private long diasFaltantes;
    private String status;
    private Usuario usuario;

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdFuncionario(int idFuncionario) {
    }

    public Reserva() {

        this.usuario = new Usuario();

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getDiasFaltantes() {
        return diasFaltantes;
    }

    public void setDiasFaltantes(long l) {
        this.diasFaltantes = l;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public int getQuantidadePessoa() {
        return quantidadePessoa;
    }

    public void setQuantidadePessoa(int quantidadePessoa) {
        this.quantidadePessoa = quantidadePessoa;
    }

}
