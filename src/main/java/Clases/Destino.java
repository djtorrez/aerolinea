package Clases;

import java.io.Serializable;

public class Destino implements Serializable {
    private int id;
    private String nombre;
    private boolean estado;

    public Destino() {
    }

    public Destino(String nombre, boolean estado) {
        this.nombre = nombre;
        this.estado = estado;
    }

    public Destino(int id, String nombre, boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Destino{"+ this.getId() +", " + this.getNombre() + ", "+ this.isEstado() +"}";
    }
}
