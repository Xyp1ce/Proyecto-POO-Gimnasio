public class Sucursal {
    private String horario;
    private String ubicacion;
    private String servicios;
    private float cuota;
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

    public Cliente getClientes() {
        String clientes = "";
        for (int i = 0; i < noClientes; i++) {
            clientes += clientes[i].toString();
            if (i < noClientes- 1) // evita agregar el salto de linea si es el ultimo cliente
                ubc += '\n';
        }
        return clientes;
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
    // Setters

    // Metodos
    public void addClient(Cliente cliente) {}

    public void removeClient(long ID) {}

    public void registerEmploye(Empleado emleado) {}

    public void removeEmploye(long ID) {}

    public String toString() {}
}