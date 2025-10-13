public class Gimnasio {
    //ATRIBUTOS
    private String nombre = "Gimnasio de los papus pros";
    // No agregue el atributo de ubicaciones porque ese ira en
    // la clase surcursal, tiene mas sentido
    private static Sucursal sucursales[];

    //MAIN
    public static void main(String[] args) {
        Gimnasio gimnasio = new Gimnasio();
        System.out.println(gimnasio);
    }

    // CONSTRUCTORES
    public Gimnasio(){
        sucursales = new Sucursal[10]; // Maximo 10 sucursales por lo tanto maximo 1000 clientes
    }

    // GETTERS Y SETTERS
    public String getNombre() {
        return nombre;
    }

    public String getUbicaciones() {
        String ubc = "";
        for (Sucursal sucursal : sucursales) {
            ubc += sucursal.getUbicacion();
            if (sucursal != sucursales[sucursales.length - 1]) // evita agregar la coma si es la ultima ubicacion
                ubc += ",";
        }
        return ubc;
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

    public void addSucursal(Sucursal sucursal) {}

    public void removeSucursal(int noSucursal) {} // La eliminaremos en base al numero de sucursal

    @Override
    public String toString() {
        return "Gimnasio: " + nombre + "\nUbicaciones: " + getUbicaciones();
    }
}