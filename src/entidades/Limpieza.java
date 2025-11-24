package entidades;

public class Limpieza extends Empleado {
    private static final long serialVersionUID = 1L;
    // ATRIBUTO PARTICIPANTE
    private String areaAsignada;

    // CONSTRUCTORES
    public Limpieza() {
        super();
        areaAsignada = "";
    }

    public Limpieza(String area) {
        super();
        areaAsignada = area;
    }

    public Limpieza(String tipoEmpleado, String nombre, long telefono, String direccion, String area) {
        super(tipoEmpleado, nombre, telefono, direccion);
        areaAsignada = area;
    }

    // GETTERS Y SETTERS
    public String obtenerAreaAsignada() {
        return areaAsignada;
    }

    public void definirAreaAsignada(String areaAsignada) {
        this.areaAsignada = areaAsignada;
    }

    // METODOS PARTICULARES
    public void limpiarArea() {}

    public void reportarDano() {}

    // METODOS POLIMORFICOS
    @Override
    public void registrarEntrada() {
        tareasProgramadas = "LIMPIEZA EN SERVICIO";
    }

    @Override
    public void registrarSalida() {
        tareasProgramadas = "LIMPIEZA FUERA DE SERVICIO";
    }

    @Override
    public String generarDescripcion() {
        return "Limpieza{" +
                "identificador=" + obtenerIdentificador() +
                ", nombre='" + obtenerNombre() + '\'' +
                ", telefono=" + obtenerTelefono() +
                ", direccion='" + obtenerDireccion() + '\'' +
                ", areaAsignada='" + areaAsignada + '\'' +
                '}';
    }
}