public class Limpieza extends Empleado {
    private String area;

    // Constructor
    public Limpieza() {
        super();
    }

    public Limpieza(String area) {
        super();
        this.area = area;
    }

    public Limpieza(String type, String name, long telephone, String address, String area) {
        super(type, name, telephone, address);
        this.area = area;
    }

    // Getters
    public String getArea() {
        return area;
    }

    // Setters
    public void setArea(String area) {
        this.area = area;
    }

    public void setsalario(float salario) {
        this.salario = salario;
    }

    // Metodos
    public void limpiarArea() {}

    public void reportarDano() {}

    @Override
    public String toString() {
        return "Limpieza{" +
                "ID=" + ID +
                ", tipo='" + tipo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono=" + telefono +
                ", direccion='" + direccion + '\'' +
                ", salario=" + salario +
                ", area='" + area + '\'' +
                '}';
    }
}