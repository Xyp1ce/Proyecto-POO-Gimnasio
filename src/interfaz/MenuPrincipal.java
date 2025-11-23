package interfaz;
import javax.swing.JOptionPane;
import control.*;
import entidades.*;

public class MenuPrincipal {

  static Gimnasio gimnasio = new Gimnasio();

  public static void menu() {
    String opciones = "";
    String opcion = "";
    int opcSurcusal = -1;
    do{
      opciones = JOptionPane.showInputDialog(null, "Bienvenido al nuevo super genial " +
          gimnasio.twoString() +
          "\nPor favor Seleccione una de las siguientes opciones: "+
          "\n[1] Agregar Sucursal"+
          "\n[2] Mostrar Lista de Sucursales"+
          "\n[3] Seleccionar Sucursal"+
          "\n[4] Salir"+
          "\nOpcion:\t");
      switch(opciones){
        case null:
          JOptionPane.showMessageDialog(null, "Opcion invalida");
          System.exit(0);
        case "1": //LLAMAR AL METODO ADECUADO PARA AGREGAR UNA SUCURSAL
          agregarSucursal();
          break;
        case "2":
          JOptionPane.showMessageDialog(null, gimnasio.toString());
          break;
        case "3": 
          // SUCURSAL SELECCIONADA Y LLAMAR A SUS METODOS
          // MENU SUCURSAL
          opcion = JOptionPane.showInputDialog(null,"Selecciona una sucursal\n" + gimnasio.twoString());
          opcSurcusal = Integer.parseInt(opcion);
          if(gimnasio.buscarSucursal(opcSurcusal) != null)
            MenuSucursal.menu(gimnasio.buscarSucursal(opcSurcusal));
          else 
            JOptionPane.showMessageDialog(null, "No se encontro esa sucursal\n");
          break;
        case "4":
          JOptionPane.showMessageDialog(null, "Saliendo del programa...");
          System.exit(0);
        default:
          JOptionPane.showMessageDialog(null, "Opcion invalida");
      }
    }while(opciones != "4");
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
    // gimnasio.addCliente(cliente); // FALTA SELECCIONAR LA SUCURSAR DONDE SE VA A GUARDAR EL SEMESTRE

    JOptionPane.showMessageDialog(null, "Cliente agregado exitosamente");
  }
}
