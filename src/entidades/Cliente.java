package entidades;

import control.Gimnasio;

public class Cliente {
    private long ID;
    private String nombre;
    private String tipo;
    private int historialVisitas;
    private String telefono;

    // Constructor
    public Cliente() {
        setID();
        historialVisitas = 0;
    }

    public Cliente(String nombre, String tipo, String telefono) {
        setID();
        this.nombre = nombre;
        this.tipo = tipo;
        this.telefono = telefono;
        historialVisitas = 0;
    }

    // GETTERS Y SETTERS 
    public void setID() {
        long nuevoID;
        do {
            nuevoID = (long)(Math.random() * 90000000L) + 10000000L; // 8 dígitos
        } while (Gimnasio.validarIDcliente(nuevoID) != false);
        this.ID = nuevoID;
    }

    public long getID() {
        return ID;
    }

    public String getnombre() {
        return nombre;
    }

    public String gettelefono() {
        return telefono;
    }
    
    public void settelefono(String telefono) {
        this.telefono = telefono;
    }

    public String gettipo() {
        return tipo;
    }

    public void settipo(String tipo) {
        this.tipo = tipo;
    }


    // Metodos
    public void registrarVisita() {
        historialVisitas++;
    }

    @Override
    public String toString() {      //MUESTRA LA INFORMACION DEL CLIENTE
        return "Cliente{" +
                "ID=" + ID +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", historialVisitas='" + historialVisitas + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }

    // PROXIMAS FUNCIONES
    /*
    public boolean evaluarEntrenador() {    // true si lo recomienda false si no

    } 


    public boolean pagarMembresia() {  // true si se pago bien false si no
        
    }

    public boolean cancelarMembresia() { // true si se cancelo bien false si no
    
    }
    */

}