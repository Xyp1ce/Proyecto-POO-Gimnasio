package control;

import entidades.Empleado;
import entidades.Cliente;

public class Sucursal {
    private String horario;
    private String ubicacion;
    private String servicios;
    private float cuota;
    private Empleado empleados[];
    private Cliente clientes[];
    private int noClientes;

    // Constructor
    public Sucursal() {}

    public Sucursal(String horario, String ubicacion, String servicios, float cuota) {
        this.horario = horario;
        this.ubicacion = ubicacion;
        this.servicios = servicios;
        this.cuota = cuota;
        clientes = new Cliente[100]; // Maximo 100 clientes por sucursal
        noClientes = 0;
    }

    // Getters

    public String getHorario() {
        return horario;
    }

    public float getCuota() {
        return cuota;
    }

    public String getServicios() {
        return servicios;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public Empleado[] getEmpleados() {
        return empleados;
    }

    public Cliente[] getClientes() {
        return clientes;
    }

    // Metodos
    public void addClient(Cliente cliente) {}

    public void removeClient(long ID) {}

    public void registerEmploye(Empleado emleado) {}

    public void removeEmploye(long ID) {}

    public String toString() {    //MUESTRA LA INFORMACION DE LA SUCURSAL
        return "Sucursal{" +
                "horario='" + horario + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", servicios='" + servicios + '\'' +
                ", cuota=" + cuota +
                ", noClientes=" + noClientes +
                '}';

    }
}