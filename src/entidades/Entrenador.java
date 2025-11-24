package entidades;

public class Entrenador extends Empleado {
    // ATRIBUTOS PARTICULARES DEL ENTRENADOR
    private String[] especialidades;
    private String[] certificaciones;
    private String[] rutinas;
    private float comision;

    // CONSTRUCTORES
    public Entrenador() {
        super();
        inicializarColecciones();
        comision = 0;
    }

    public Entrenador(String tipoEmpleado, String nombre, long telefono, String direccion, String especialidad, String certificacion, String rutina) {
        super(tipoEmpleado, nombre, telefono, direccion);
        inicializarColecciones();
        agregarEspecialidad(especialidad);
        agregarCertificacion(certificacion);
        agregarRutina(rutina);
        comision = 0;
    }

    private void inicializarColecciones() {
        especialidades = new String[0];
        certificaciones = new String[0];
        rutinas = new String[0];
    }

    // GETTERS Y SETTERS
    public String[] obtenerEspecialidades() {
        return especialidades;
    }

    public String[] obtenerCertificaciones() {
        return certificaciones;
    }

    public String[] obtenerRutinas() {
        return rutinas;
    }

    public float obtenerComision() {
        return comision;
    }

    public void definirComision(float comision) {
        this.comision = comision;
    }

    // METODOS PARTICULARES
    public void agregarEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.trim().isEmpty())
            return;
        for (String registro : especialidades) {
            if (registro.equalsIgnoreCase(especialidad))
                return;
        }
        String[] temporal = new String[especialidades.length + 1];
        for (int i = 0; i < especialidades.length; i++) {
            temporal[i] = especialidades[i];
        }
        temporal[especialidades.length] = especialidad;
        especialidades = temporal;
    }

    public void agregarCertificacion(String certificacion) {
        if (certificacion == null || certificacion.trim().isEmpty())
            return;
        for (String registro : certificaciones) {
            if (registro.equalsIgnoreCase(certificacion))
                return;
        }
        String[] temporal = new String[certificaciones.length + 1];
        for (int i = 0; i < certificaciones.length; i++) {
            temporal[i] = certificaciones[i];
        }
        temporal[certificaciones.length] = certificacion;
        certificaciones = temporal;
    }

    public void agregarRutina(String rutina) {
        if (rutina == null || rutina.trim().isEmpty())
            return;
        for (String registro : rutinas) {
            if (registro.equalsIgnoreCase(rutina))
                return;
        }
        String[] temporal = new String[rutinas.length + 1];
        for (int i = 0; i < rutinas.length; i++) {
            temporal[i] = rutinas[i];
        }
        temporal[rutinas.length] = rutina;
        rutinas = temporal;
    }

    public void evaluarProgreso() {}

    // METODOS POLIMORFICOS
    @Override
    public void registrarEntrada() {
        tareasProgramadas = "ENTRENADOR EN SERVICIO";
    }

    @Override
    public void registrarSalida() {
        tareasProgramadas = "ENTRENADOR FUERA DE SERVICIO";
    }

    @Override
    public String generarDescripcion() {
        return "Entrenador{" +
                "identificador=" + obtenerIdentificador() +
                ", nombre='" + obtenerNombre() + '\'' +
                ", especialidades=" + formatearArreglo(especialidades) +
                ", certificaciones=" + formatearArreglo(certificaciones) +
                ", rutinas=" + formatearArreglo(rutinas) +
                ", comision=" + comision +
                ", salario=" + obtenerSalario() +
                '}';
    }

    private String formatearArreglo(String[] datos) {
        if (datos == null || datos.length == 0)
            return "[]";
        String texto = "[";
        for (int i = 0; i < datos.length; i++) {
            texto += datos[i];
            if (i < datos.length - 1)
                texto += ", ";
        }
        texto += "]";
        return texto;
    }
}
