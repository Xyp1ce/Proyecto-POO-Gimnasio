public class ServicioAlCliente extends Empleado {
    private String languagueProfiency; // Bueno, regular, malo, de la verga
    private int noCustomers;
    private float commission;

    // Constructor
    public ServicioAlCliente(){ // vacio
        super();
        commission = 0;
    }

    public ServicioAlCliente(String languagueProfiency, int noCustomers) {
        super();
        this.languagueProfiency = languagueProfiency;
        this.noCustomers = noCustomers;
        commission = 0; // se calculara en base a las encuestas de satisfaccion
    }

    public ServicioAlCliente(String type, String name, long telephone, String address, String languagueProfiency, int noCustomers) {
        super(type, name, telephone, address);
        this.languagueProfiency = languagueProfiency;
        this.noCustomers = noCustomers;
        commission = 0;
    }

    // Getters
    public String getLanguague() {
        return languagueProfiency;
    }

    // Setters
    public void setComission(float commission) {
        this.commission = commission;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    // Metodos
    public void attendCustomer() {
        noCustomers++;
    }

    public void addCompliment() {}

    public void doSatisfactionSurvey() {}

    public String toString() {}
}