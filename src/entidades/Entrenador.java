public class Entrenador extends Empleado {
    // Varios son arreglos porque pueden tener varias
    private String especialidades[];
    private String certificaciones[];
    private String rutinas[];
    private float commssion;

    // Constructor
    public Entrenador() {
        super();
    }

    public Entrenador(String especialidad, String certificaciones, String rutinas) {
        super();
        addEspecialidad(especialidad);
        addCertificacion(certificaciones);
        addRutina(rutinas);
        commssion = 0;
    }

    public Entrenador(String tipo, String nombre, long telefono, String direccion, String especialidad, String certificaciones, String rutinas) {
        super(tipo, nombre, telefono, direccion);
        addEspecialidad(especialidad);
        addCertificacion(certificaciones);
        addRutina(rutinas);
        commssion = 0;
    }

    // Getters
    public String[] getEspecialidad() {
        return especialidades;
    }

    public String[] getCertificaciones() {
        return certificaciones;
    }

    public String[] getRutinas() {
        return rutinas;
    }
    public float getCommssion() {
        return commssion;
    }

    // Setters
    public void setCommssion(float commssion) {
        this.commssion = commssion;
    }

    public void setsalario(float salario) {
        this.salario = salario;
    }

    // Metodos
    public void addEspecialidad(String especialidad) { //AGREGAR ESPECIALIDAD CUANDO YA SE TIENE
        // CICLO QUE VERIFICA SI YA EXISTE LA ESPECIALIDAD, EN CASO DE QUE NO, LA AGREGA
        for (String esp : especialidades) {
            if (esp.equalsIgnoreCase(especialidad)) {
                return; // Ya existe, no agregar
            }
        }
        //EN CASO DE NO EXISTIR, COMO NO SE CUENTA CON EL NUMERO DE ESPECIALIDADES, SE AGREGA ESPACIO AL ARREGLO
        String temp[] = new String[especialidades.length + 1];
        for (int i = 0; i < especialidades.length; i++) {
            temp[i] = especialidades[i];
        }
        especialidades = temp;
        especialidades[especialidades.length - 1] = especialidad;
    }

    public void addCertificacion(String certificacion) { //AGREGAR CERTIFICACION CUANDO YA SE TIENE
        // CICLO QUE VERIFICA SI YA EXISTE LA CERTIFICACION, EN CASO DE QUE NO, LA AGREGA
        for (String cert : certificaciones) {
            if (cert.equalsIgnoreCase(certificacion)) {
                return; // Ya existe, no agregar
            }
        }

        //EN CASO DE NO EXISTIR, COMO NO SE CUENTA CON EL NUMERO DE CERTIFICACIONES, SE AGREGA ESPACIO AL ARREGLO
        String temp[] = new String[certificaciones.length + 1];
        for (int i = 0; i < certificaciones.length; i++) {
            temp[i] = certificaciones[i];
        }
        certificaciones = temp;
        certificaciones[certificaciones.length - 1] = certificacion;
    }
    public void addRutina(String rutina) { //AGREGAR RUTINA CUANDO YA SE TIENE
        // CICLO QUE VERIFICA SI YA EXISTE LA RUTINA, EN CASO DE QUE NO, LA AGREGA
        for (String rut : rutinas) {
            if (rut.equalsIgnoreCase(rutina)) {
                return; // Ya existe, no agregar
            }
        }

        //EN CASO DE NO EXISTIR, COMO NO SE CUENTA CON EL NUMERO DE RUTINAS, SE AGREGA ESPACIO AL ARREGLO
        String temp[] = new String[rutinas.length + 1];
        for (int i = 0; i < rutinas.length; i++) {
            temp[i] = rutinas[i];
        }
        rutinas = temp;
        rutinas[rutinas.length - 1] = rutina;
    }
    public void evaluarProgreso() {

    }

    public String toString() {
        return "Entrenador{" +
                "ID=" + getID() +
                ", tipo='" + tipo + '\'' +
                ", tareas_programadas='" + tareas_programadas + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", telefono=" + getTelefono() +
                ", direccion='" + getDireccion() + '\'' +
                ", salario=" + salario +
                ", especialidades=" + getEspecialidad() +
                ", certificaciones=" + getCertificaciones() +
                ", rutinas=" + getRutinas() +
                ", commssion=" + commssion +
                '}';
    }

}