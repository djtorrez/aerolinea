package Clases;

import java.sql.Date;
import java.sql.Time;


public class HorarioVuelo {
    private int id;
    private Date fecha;
    private Time hora;
    private boolean estado;

    public HorarioVuelo(Date fecha, Time hora, boolean estado) {
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    public HorarioVuelo(int id, Date fecha, Time hora, boolean estado) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
