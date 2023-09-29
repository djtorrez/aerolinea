package Clases;

public class ReservaAsiento {
    private int reservaId;
    private int asientoId;

    public ReservaAsiento(int reservaId, int asientoId) {
        this.reservaId = reservaId;
        this.asientoId = asientoId;
    }

    public int getReservaId() {
        return reservaId;
    }

    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }

    public int getAsientoId() {
        return asientoId;
    }

    public void setAsientoId(int asientoId) {
        this.asientoId = asientoId;
    }
}
