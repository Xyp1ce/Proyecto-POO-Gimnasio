public class Gimnasio {
    private nombre;
    // No agregue el atributo de ubicaciones porque ese ira en
    // la clase surcursal, tiene mas sentido
    private Sucursal sucursales[];
    private int cantSucursales;

    // Constructor
    public Gimnasio(){
        sucursales = new Sucursal[10]; // Maximo 10 sucursales por lo tanto maximo 1000 clientes
        cantSucursales = 0;
    }

    // Getters
    public String getNombre() {
        sucursales = new Sucursal[10];
        cantSucursales = 0;
        return nombre;
    }

    public String getUbicaciones() {
        String ubc = "";
        for (int i = 0; i < cantSucursales; i++) {
            ubc += sucursalesp[i].getUbicacion();
            if (i < cantSucursales - 1) // evita agregar la coma si es la ultima ubicacion
                ubc += ', ';
        }
        return ubc;
    }

    // No hay Setters porque no alteraremos la informacion del gimnasio

    public void addSucursal(Sucursal sucursal) {}

    public void removeSucursal(int noSucursal) {} // La eliminaremos en base al numero de sucursal

    public String toString() {}
}