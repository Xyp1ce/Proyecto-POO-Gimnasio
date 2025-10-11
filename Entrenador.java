public class Entrenador extends Empleado {
    // Varios son arreglos porque pueden tener varias
    private String especialidades[];
    private String certificaciones[];
    private String rutines[];
    private float commssion;
    private int noEspecialidades;
    private int noCertificacion;
    private int noRutines;

    // Constructor
    public Entrenador() {
        super();
    }

    public Entrenador(String especialidad, String certificaciones, String rutines) {
        super();
        this.especialidad = especialidad;
        this.certificaciones = certificaciones;
        this.rutines = rutines;
        commssion = 0;
    }

    public Entrenador(String type, String name, long telephone, String address, String especialidad, String certificaciones, String rutines) {
        super(type, name, telephone, address);
        this.especialidad = especialidad;
        this.certificaciones = certificaciones;
        this.rutines = rutines;
        commssion = 0;
        noEspecialidades = 0;
        noCertificacion = 0;
        noRutines = 0;
    }

    // Getters
    public String getEspecialidad() {
        String msg = "";
        for(int i = 0; i < noEspecialidades; i++) {
            msg+=especialidades[i];
            if(i < noEspecialidades - 1)
                msg+=", ";
        }
        return msg;
    }

    public String getCertificaciones() {
        String msg = "";
        for(int i = 0; i < noCertificacion; i++) {
            msg+=certificaciones[i];
            if(i < noCertificacion - 1)
                msg+=", ";
        }
        return msg;
    }

    public String getRutines() {
        String msg = "";
        for(int i = 0; i < noRutines; i++) {
            msg+=rutines[i];
            if(i < noRutines - 1)
                msg+=", ";
        }
        return msg;
    }

    // Setters
    public void setCommssion(float commssion) {
        this.commssion = commssion;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    // Metodos
    public void evaluarProgreso() {}

    public String toString() {}

    public int addRutine() {
        noRutines++;
    }

    public int addCertificacion() {
        noCertificacion++;
    }

    public int addEspecialidad() {
        noEspecialidades++;
    }
}