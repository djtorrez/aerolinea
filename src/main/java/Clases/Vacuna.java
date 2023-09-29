package Clases;

import java.sql.Date;

public class Vacuna {
    private int id;
    private byte nombre;
    private byte numeroDosis;
    private Date fecha;
    private int clienteId;

    public Vacuna() {
    }

    public Vacuna(int id, byte nombre, byte numeroDosis, Date fecha, int clienteId) {
        this.id = id;
        this.nombre = nombre;
        this.numeroDosis = numeroDosis;
        this.fecha = fecha;
        this.clienteId = clienteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getNombre() {
        return nombre;
    }

    public void setNombre(byte nombre) {
        this.nombre = nombre;
    }

    public byte getNumeroDosis() {
        return numeroDosis;
    }

    public void setNumeroDosis(byte numeroDosis) {
        this.numeroDosis = numeroDosis;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
}
