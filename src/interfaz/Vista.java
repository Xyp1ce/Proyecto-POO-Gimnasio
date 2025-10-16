package interfaz;
import control.*;
import entidades.*;
import javax.swing.JOptionPane;

public class Vista {
    //ATRIBUTOS
    static Gimnasio gimnasio = new Gimnasio();

    //CONSTRUCTORES
    public Vista() {}
    //GETTERS Y SETTERS

    //METODOS
    public static void menu() {
        String opcion = "";
        do{
            opcion = JOptionPane.showInputDialog(null,  "Bienvenido al nuevo super genial" + gimnasio.getNombre()+
                                                                "\nPor favor Seleccione una de las siguientes opciones: "+
                                                                "\n[1] Agregar Sucursal"+
                                                                "\n[2] Mostrar lista de sucursales"+
                                                                "\n[3] Seleccionar Sucursal"+
                                                                "\n[4] Agregar Cliente"+
                                                                "\n[5] Salir"+
                                                                "\nOpcion:\t");
            switch(opcion){
                case "1": //LLAMAR AL METODO ADECUADO PARA AGREGAR UNA SUCURSAL
                    agregarSucursal();
                break;
                case "2":
                    JOptionPane.showMessageDialog(null, gimnasio.toString());
                break;
                case "3":
                break;
                case "4":
                
                break;
                case "5":
                    JOptionPane.showMessageDialog(null, "Saliendo del programa...");
                    System.exit(0);
                break;
                default:
                    JOptionPane.showMessageDialog(null, "Opcion invalida");
            }


        }while(opcion != "5" || opcion != "Cancelar");
        System.exit(0);
    }

    public static void agregarSucursal(){
        int noSucursal = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el numero de la sucursal"));
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre de la Sucursal:");
        String horario = JOptionPane.showInputDialog("Ingrese el horario en el siguiente formato hh:hh\n(Hora de apertura : Hora de Cierre)");
        String ubicacion = JOptionPane.showInputDialog("Ingrese la Direccion de la Sucursal");
        String servicios = JOptionPane.showInputDialog("Ingrese los servicios especializados realizados por la sucursal");
        float cuota = Float.parseFloat(JOptionPane.showInputDialog("Ingrese la cuota"));

        Sucursal sucursal = new Sucursal(noSucursal, nombre, horario, ubicacion, servicios, cuota );
        gimnasio.addSucursal(sucursal);

        JOptionPane.showMessageDialog(null, "Sucursal agregada exitosamente");
    }
    
    public static void agregarCliente(){
		String nombre = JOptionPane.showInputDialog("Ingrese el nombre del cliente:");
		String tipo = JOptionPane.showInputDialog("Ingrese el tipo de cliente:\n[1] Regular\n[2] Premium");
		String telefono = JOptionPane.showInputDialog("Ingrese el telefono del cliente:");

		Cliente cliente = new Cliente(nombre, tipo, telefono);
		// gimnasio.addCliente(cliente); // Falta seleccionar la sucursal donde se agregara el cliente

		JOptionPane.showMessageDialog(null, "Cliente agregado exitosamente");
	}

}
