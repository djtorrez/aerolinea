package Clases;

public class Reserva {
    private int id;
    private int destinoId;
    private int hrarioVueloId;

    public Reserva() {
    }

    public Reserva(int id, int destinoId, int hrarioVueloId) {
        this.id = id;
        this.destinoId = destinoId;
        this.hrarioVueloId = hrarioVueloId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(int destinoId) {
        this.destinoId = destinoId;
    }

    public int getHrarioVueloId() {
        return hrarioVueloId;
    }

    public void setHrarioVueloId(int hrarioVueloId) {
        this.hrarioVueloId = hrarioVueloId;
    }
}
