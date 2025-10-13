package entidades;

import control.Gimnasio;

public class Empleado {
    protected long ID; // El ID ES UN NUMERO DE 8 DIGITOS QUE SE GENERA ALEATORIAMENTE
    protected String tipo;
    protected String tareas_programadas;    //ANTES DE LLAMABA WORK_SCHEDULE    
    protected String nombre;
    protected long telefono;
    protected String direccion;
    protected float salario;

    // CONSTRUCTORES
    public Empleado(){
        setID();
    }

    public Empleado(String tipo, String nombre, long telefono, String direccion){
        setID();
        this.tipo = tipo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // GETTERS Y SETTERS
    public void setID() {
        long nuevoID;
        do {
            nuevoID = (long)(Math.random() * 90000000L) + 10000000L; // 8 dígitos
        } while (Gimnasio.validarIDempleado(nuevoID) != false);
        this.ID = nuevoID;
    }

    public long getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

    public long getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) { // En caso de que el wey cambie de direccion
        this.direccion = direccion;
    }

    // METODOS
    public void registrarEntrada() {}

    public void registrarSalida() {}

    public String toString() {
        return "Empleado{" +
                "ID=" + ID +
                ", tipo='" + tipo + '\'' +
                ", tareas_programadas='" + tareas_programadas + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono=" + telefono +
                ", direccion='" + direccion + '\'' +
                ", salario=" + salario +
                '}';
    }
}