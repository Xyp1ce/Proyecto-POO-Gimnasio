public class ServicioAlCliente extends Empleado {
    private String idioma; // Bueno, regular, malo, peor
    private int noCustomers;
    private float commission;

    // Constructor
    public ServicioAlCliente(){ // vacio
        super();
        commission = 0;
    }

    public ServicioAlCliente(String idioma, int noCustomers) {
        super();
        this.idioma = idioma;
        this.noCustomers = noCustomers;
        commission = 0; // se calculara en base a las encuestas de satisfaccion
    }

    public ServicioAlCliente(String type, String name, long telephone, String address, String idioma, int noCustomers) {
        super(type, name, telephone, address);
        this.idioma = idioma;
        this.noCustomers = noCustomers;
        commission = 0;
    }

    // Getters
    public String getLanguague() {
        return idioma;
    }

    // Setters
    public void setComission(float commission) {
        this.commission = commission;
    }

    public void setsalario(float salario) {
        this.salario = salario;
    }

    // Metodos
    public void attendCustomer() {
        noCustomers++;
    }

    public void addCompliment() {}

    public void doSatisfactionSurvey() {}

    @Override
    public String toString() {
        return "ServicioAlCliente{" +
                "ID=" + ID +
                ", tipo='" + tipo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono=" + telefono +
                ", direccion='" + direccion + '\'' +
                ", salario=" + salario +
                ", idioma='" + idioma + '\'' +
                ", noCustomers=" + noCustomers +
                ", commission=" + commission +
                '}';
    }
}