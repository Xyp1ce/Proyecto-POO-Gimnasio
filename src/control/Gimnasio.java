package control;

import java.io.Serializable;

import entidades.*;
import interfaz.*;

public class Gimnasio implements Serializable{
	private static final long serialVersionUID = 1L;
	//ATRIBUTOS
    private String nombre = "Gimnasio de los papus pros";
    private static Sucursal sucursales[];

    //MAIN
    public static void main(String[] args) {
        Gimnasio gimnasio = new Gimnasio();
        gimnasio.generarSucursales();
        System.out.println(gimnasio);
        Vista.menu();
    }

    // CONSTRUCTORES
    public Gimnasio(){
        sucursales = new Sucursal[0]; 
        generarSucursales();
    }

    // GETTERS Y SETTERS
    public String getNombre() {
        return nombre;
    }

    public String getUbicaciones() {
        String ubc = "";
        boolean first = true;
        for (int i = 0; i < sucursales.length; i++) {
            Sucursal sucursal = sucursales[i];
            if (sucursal != null) {
                if (!first) {
                    ubc += ",";
                }
                ubc += sucursal.getUbicacion();
                first = false;
            }
        }
        return ubc;
    }

    ////////////    METODOS    ////////////

    //VALIDA SI SE REPIDE EL ID DE UN EMPLEADO EN TODAS LAS SUCURSALES
    public static boolean validarIDempleado(long ID) { // TRUE SI EXISTE EL EMPLEADO, FALSE SI NO
        for (Sucursal sucursal : sucursales) {
            for (Empleado empleado : sucursal.getEmpleados()) {
                if (empleado.getID() == ID) {
                    return true;
                }
            }
        }
        return false;
    }

    //VALIDA SI SE REPIDE EL ID DE UN CLIENTE EN TODAS LAS SUCURSALES
    public static boolean validarIDcliente(long ID) { // TRUE SI EXISTE EL CLIENTE, FALSE SI NO
        for (Sucursal sucursal : sucursales) {
            for (Cliente cliente : sucursal.getClientes()) {
                if (cliente.getID() == ID) {
                    return true;
                }
            }
        }
        return false;
    }

    //GENERAR UNA LISTA DE 4 SUCURSALES CON DATOS ALEATORIOS
    public void generarSucursales() {
        int[] numeros = {0, 1, 2, 3};
        String[] nombres = {"Peninsula", "Plaza Rio", "Santa Fe", "Dubai"};
        String[] horarios = {"6:00-22:00", "5:00-23:00", "24 horas", "7:00-21:00"};
        String[] ubicaciones = {"Centro", "Norte", "Sur", "Este", "Oeste", "Playa", "Montaña", "Valle"};
        String[] servicios = {"Pesas, Cardio", "Yoga, Pilates", "Natación, Spa", "Crossfit, Boxeo"};
        float[] cuotas = {300.0f, 450.0f, 600.0f, 750.0f};

        for (int i = 0; i < 4; i++) {
            int noSucursal = numeros[(int)(Math.random() * numeros.length)];
            String nombre = nombres[(int)(Math.random()) * nombres.length];
            String horario = horarios[(int)(Math.random() * horarios.length)];
            String ubicacion = ubicaciones[(int)(Math.random() * ubicaciones.length)];
            String servicio = servicios[(int)(Math.random() * servicios.length)];
            float cuota = cuotas[(int)(Math.random() * cuotas.length)];

            addSucursal(new Sucursal(noSucursal, nombre, horario, ubicacion, servicio, cuota));
        }
    }

    //METODO PARA AGREGAR UNA SUCURSAL A LA LISTA DE SUCURSALES
    public void addSucursal(Sucursal sucursal) {
        // CREAR UN ARREGLO TEMPORAL CON UN ESPACIO EXTRA
        Sucursal[] temp = new Sucursal[sucursales.length + 1];
        // COPIAR LAS SUCURSALES EXISTENTES AL ARREGLO TEMPORAL
        for (int i = 0; i < sucursales.length; i++) {
            temp[i] = sucursales[i];
        }
        // AGREGAR LA NUEVA SUCURSAL AL FINAL DEL ARREGLO TEMPORAL
        temp[sucursales.length] = sucursal;
        // REASIGNAR EL ARREGLO TEMPORAL A LA VARIABLE DE SUCURSALES
        sucursales = temp;
    }

    public void removeSucursal(int noSucursal) {
        int count = 0;
        // CONTAR CUANTAS SUCURSALES NO TIENEN EL NUMERO DE SUCURSAL A ELIMINAR
        for (Sucursal s : sucursales) {
            if (s != null && s.getNoSucursal() != noSucursal) {
                count++;
            }
        }
        Sucursal[] temp = new Sucursal[count];
        int idx = 0;
        for (Sucursal s : sucursales) {
            if (s != null && s.getNoSucursal() != noSucursal) {
                temp[idx++] = s;
            }
        }
        sucursales = temp;
    }

    @Override
    public String toString() {
        String header =
            "\n==============================\n" +
            "      " + nombre + "\n" +
            "==============================\n" +
            "Ubicaciones: " + getUbicaciones() + "\n" +
            "No. de Sucursales: " + sucursales.length + "\n" +
            "--------------------------------\n" +
            "Datos de Sucursales:\n";

        String sucursalesStr = "";
        int idx = 1;
        for (int i = 0; i < sucursales.length; i++) {
            Sucursal sucursal = sucursales[i];
            if (sucursal != null) {
                sucursalesStr += "Sucursal #" + idx + "\n";
                sucursalesStr += "  Horario    : " + sucursal.getHorario() + "\n";
                sucursalesStr += "  Ubicación  : " + sucursal.getUbicacion() + "\n";
                sucursalesStr += "  Servicios  : " + sucursal.getServicios() + "\n";
                sucursalesStr += "  Cuota      : $" + String.format("%.2f", sucursal.getCuota()) + "\n";
                sucursalesStr += "--------------------------------\n";
                idx++;
            }
        }
        return header + sucursalesStr;
    }

    public String twoString() {
        String header =
            "\n==============================\n" +
            "      " + nombre + "\n" +
            "==============================\n" +
            "Ubicaciones: " + getUbicaciones() + "\n" +
            "No. de Sucursales: " + sucursales.length + "\n" +
            "-----------------------------------------------------\n" +
            "Datos de Sucursales:\n";

        String sucursalesStr = "";
        int idx = 1;
        for (int i = 0; i < sucursales.length; i++) {
            Sucursal sucursal = sucursales[i];
            if (sucursal != null) {
                sucursalesStr += "Sucursal #" + idx + "\n";
                sucursalesStr += "  Horario    : " + sucursal.getHorario() + " ||";
                sucursalesStr += "  Ubicación  : " + sucursal.getUbicacion() + "\n";
                sucursalesStr += "----------------------------------------------------\n";
                idx++;
            }
        }
        return header + sucursalesStr;
    }
}
