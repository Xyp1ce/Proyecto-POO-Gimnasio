public class Empleado {
    private long ID; // El ID se generaria aleatoriamente
    protected String type;
    protected String workSchedule;
    private String name;
    private long telephone;
    private String address;
    protected float salary;

    // constructor
    public Empleado(){}

    public Empleado(String type, String name, long telephone, String address){
        // ID = numero aleatorio
        this.type = type;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
    }

    // Getters
    public long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public long getTelephone() {
        return telephone;
    }

    public String getAddress() {
        return address;
    }

    // Setters
    public void setAddress(String address) { // En caso de que el wey cambie de direccion
        this.address = address;
    }

    // Metodos
    public void registrarEntrada() {}

    public void registrarSalida() {}

    public String toString() {}
}