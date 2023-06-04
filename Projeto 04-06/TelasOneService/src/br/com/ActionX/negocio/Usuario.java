package br.com.ActionX.negocio;

public class Usuario extends Pessoa {

	private int idUsuario;
        private Reserva reserva;
        private int idMesa;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

	public int getIdMesa() {
		return idUsuario;
	}

	public void setIdMesa(int idMesa) {
		this.idMesa = idMesa;
	}

}
