package Clases;

public class Cliente {
    private int id;
    private String nombre;
    private String ci;
    private int telefono;

    public Cliente() {
    }

    public Cliente(int id, String nombre, String ci, int telefono) {
        this.id = id;
        this.nombre = nombre;
        this.ci = ci;
        this.telefono = telefono;
    }

    public Cliente(String nombre, String ci, int telefono) {
        this.nombre = nombre;
        this.ci = ci;
        this.telefono = telefono;
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

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    @Override
    public boolean equals(Object elOtro) {
        if (elOtro == null) {
            return false;
        }
        if (!(elOtro instanceof Cliente)) {
            return false;
        }
        if (((Cliente) elOtro).getId() != this.getId() || !((Cliente) elOtro).getNombre().equals(this.getNombre())
            || !((Cliente) elOtro).getCi().equals(this.getCi()) || ((Cliente) elOtro).getTelefono() != this.getTelefono()) {
            return false;
        }
        return true;
    }


}
