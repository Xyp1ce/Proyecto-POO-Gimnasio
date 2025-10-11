public class Cliente {
    private long ID;
    private String name;
    private String type;
    private String historialVisitas;
    private String telephone;

    // Constructor
    public Cliente() {}

    public Cliente(String name, String type, String telephone) {
        // ID = numero aleatorio
        this.name = name;
        this.type = type;
        this.telephone = telephone;
        historialVisitas = 0;
    }

    // Getters
    public long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getType() {
        return type;
    }

    // Setters
    public void setType(String type) {
        this.type = type;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    // Metodos
    public void registerVisit() {}

    public boolean payMembresy() {} // true si se pago bien false si no

    public boolean cancelMembresy() {} // true si se cancelo bien false si no

    public String toSring() {}

    public boolean evaluarEntrenador() {} // true si lo recomienda false si no
}