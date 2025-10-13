package control;

import entidades.Empleado;
import entidades.Cliente;

public class Gimnasio {
    //ATRIBUTOS
    private String nombre = "Gimnasio de los papus pros";
    // No agregue el atributo de ubicaciones porque ese ira en
    // la clase surcursal, tiene mas sentido
    private static Sucursal sucursales[];

    //MAIN
    public static void main(String[] args) {
        Gimnasio gimnasio = new Gimnasio();
        gimnasio.generarSucursales();
        System.out.println(gimnasio);
    }

    // CONSTRUCTORES
    public Gimnasio(){
        sucursales = new Sucursal[0]; 
    }

    // GETTERS Y SETTERS
    public String getNombre() {
        return nombre;
    }

    public String getUbicaciones() {
        StringBuilder ubc = new StringBuilder();
        boolean first = true;
        for (Sucursal sucursal : sucursales) {
            if (sucursal != null) {
                if (!first) {
                    ubc.append(",");
                }
                ubc.append(sucursal.getUbicacion());
                first = false;
            }
        }
        return ubc.toString();
    }

    ////////////    METODOS    ////////////

    //VALIDA SI SE REPIDE EL ID DE UN EMPLEADO EN TODAS LAS SUCURSALES
    public static boolean validarIDempleado(long ID) { // true si existe el empleado, false si no
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
    public static boolean validarIDcliente(long ID) { // true si existe el cliente, false si no
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
        String[] horarios = {"6:00-22:00", "5:00-23:00", "24 horas", "7:00-21:00"};
        String[] ubicaciones = {"Centro", "Norte", "Sur", "Este", "Oeste", "Playa", "Montaña", "Valle"};
        String[] servicios = {"Pesas, Cardio", "Yoga, Pilates", "Natación, Spa", "Crossfit, Boxeo"};
        float[] cuotas = {300.0f, 450.0f, 600.0f, 750.0f};

        for (int i = 0; i < 4; i++) {
            int noSucursal = numeros[(int)(Math.random() * numeros.length)];
            String horario = horarios[(int)(Math.random() * horarios.length)];
            String ubicacion = ubicaciones[(int)(Math.random() * ubicaciones.length)];
            String servicio = servicios[(int)(Math.random() * servicios.length)];
            float cuota = cuotas[(int)(Math.random() * cuotas.length)];

            addSucursal(new Sucursal(noSucursal, horario, ubicacion, servicio, cuota));
        }
    }

    public void addSucursal(Sucursal sucursal) {
        // Crear un arreglo temporal con tamaño +1
        Sucursal[] temp = new Sucursal[sucursales.length + 1];
        // Copiar las sucursales existentes
        for (int i = 0; i < sucursales.length; i++) {
            temp[i] = sucursales[i];
        }
        // Agregar la nueva sucursal al final
        temp[sucursales.length] = sucursal;
        // Reasignar el arreglo original
        sucursales = temp;
    }

    public void removeSucursal(int noSucursal) {
        int count = 0;
        // Contar cuántas sucursales quedan después de eliminar
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
            "==============================\n" +
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
}