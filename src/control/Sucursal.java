package control;

import entidades.Empleado;
import entidades.Cliente;

public class Sucursal {
    private int noSucursal;
    private String horario;
    private String ubicacion;
    private String servicios;
    private float cuota;
    private Empleado empleados[];
    private Cliente clientes[];
    private int noClientes;

    // Constructor
    public Sucursal() {}

    public Sucursal(int noSucursal, String horario, String ubicacion, String servicios, float cuota) {
        this.noSucursal = 0;
        this.horario = horario;
        this.ubicacion = ubicacion;
        this.servicios = servicios;
        this.cuota = cuota;
        clientes = new Cliente[100]; // Maximo 100 clientes por sucursal
        noClientes = 0;
    }

    // Getters
    public int getNoSucursal() {
        return noSucursal;
    }

    public void setNoSucursal(int noSucursal) {
        this.noSucursal = noSucursal;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public float getCuota() {
        return cuota;
    }

    public void setCuota(float cuota) {
        this.cuota = cuota;
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Empleado[] getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Empleado[] empleados) {
        this.empleados = empleados;
    }

    public Cliente[] getClientes() {
        return clientes;
    }

    public void setClientes(Cliente[] clientes) {
        this.clientes = clientes;
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