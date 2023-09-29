package Clases;

import java.sql.Date;

public class PruebaCovid {
    private int id;
    private byte tipo;
    private boolean resultado;
    private Date fecha;
    private int clienteId;

    public PruebaCovid() {
    }

    public PruebaCovid(int id, byte tipo, boolean resultado, Date fecha, int clienteId) {
        this.id = id;
        this.tipo = tipo;
        this.resultado = resultado;
        this.fecha = fecha;
        this.clienteId = clienteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getTipo() {
        return tipo;
    }

    public void setTipo(byte tipo) {
        this.tipo = tipo;
    }

    public boolean isResultado() {
        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
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
