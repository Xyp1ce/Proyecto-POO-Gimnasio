package entidades;

public class ServicioAlCliente extends Empleado {
    private static final long serialVersionUID = 1L;
    // ATRIBUTOS PARTICULARES DEL SERVICIO
    private String idiomaServicio;
    private int clientesAtendidos;
    private float comision;

    // CONSTRUCTORES
    public ServicioAlCliente() {
        super();
        idiomaServicio = "";
        clientesAtendidos = 0;
        comision = 0;
    }

    public ServicioAlCliente(String idioma, int clientesAtendidos) {
        super();
        this.idiomaServicio = idioma;
        this.clientesAtendidos = clientesAtendidos;
        comision = 0;
    }

    public ServicioAlCliente(String tipoEmpleado, String nombre, long telefono, String direccion, String idioma, int clientesAtendidos) {
        super(tipoEmpleado, nombre, telefono, direccion);
        this.idiomaServicio = idioma;
        this.clientesAtendidos = clientesAtendidos;
        comision = 0;
    }

    // GETTERS Y SETTERS
    public String obtenerIdiomaServicio() {
        return idiomaServicio;
    }

    public void definirIdiomaServicio(String idiomaServicio) {
        this.idiomaServicio = idiomaServicio;
    }

    public int obtenerClientesAtendidos() {
        return clientesAtendidos;
    }

    public void definirClientesAtendidos(int clientesAtendidos) {
        this.clientesAtendidos = clientesAtendidos;
    }

    public float obtenerComision() {
        return comision;
    }

    public void definirComision(float comision) {
        this.comision = comision;
    }

    // METODOS PARTICULARES
    public void atenderCliente() {
        clientesAtendidos++;
    }

    public void registrarEncuestaSatisfaccion() {}

    public void registrarFelicitacion() {}

    // METODOS POLIMORFICOS
    @Override
    public void registrarEntrada() {
        tareasProgramadas = "SERVICIO AL CLIENTE EN SERVICIO";
    }

    @Override
    public void registrarSalida() {
        tareasProgramadas = "SERVICIO AL CLIENTE FUERA DE SERVICIO";
    }

    @Override
    public String generarDescripcion() {
        return "ServicioAlCliente{" +
                "identificador=" + obtenerIdentificador() +
                ", nombre='" + obtenerNombre() + '\'' +
                ", idiomaServicio='" + idiomaServicio + '\'' +
                ", clientesAtendidos=" + clientesAtendidos +
                ", comision=" + comision +
                '}';
    }
}