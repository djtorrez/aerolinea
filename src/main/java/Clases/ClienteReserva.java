package Clases;

public class ClienteReserva {
    private int clienteId;
    private int reservaId;

    public ClienteReserva(int clienteId, int reservaId) {
        this.clienteId = clienteId;
        this.reservaId = reservaId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getReservaId() {
        return reservaId;
    }

    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }
}
