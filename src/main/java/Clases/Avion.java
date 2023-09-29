package Clases;

public class Avion {
    private int id;
    private String modelo;
    private String fabricante;
    private boolean estado;
    private String matricula;

    public Avion() {
    }

    public Avion(int id, String modelo, String fabricante, boolean estado, String matricula) {
        this.id = id;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.estado = estado;
        this.matricula = matricula;
    }

    public Avion(String modelo, String fabricante, boolean estado, String matricula) {
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.estado = estado;
        this.matricula = matricula;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
