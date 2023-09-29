package Clases;

public class Asiento {
    private int id;
    private char tipo;
    private char estado;
    private int avionId;

    public Asiento() {
    }

    public Asiento(int id, char tipo, char estado, int avionId) {
        this.id = id;
        this.tipo = tipo;
        this.estado = estado;
        this.avionId = avionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public int getAvionId() {
        return avionId;
    }

    public void setAvionId(int avionId) {
        this.avionId = avionId;
    }
}
