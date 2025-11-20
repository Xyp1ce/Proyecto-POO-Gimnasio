package interfaz;
import javax.swing.JOptionPane;
import control.*;
import entidades.*;

public class MenuSucursal{
  Sucursal sucursal;

	public static void menu(Sucursal opcSucursal) {
    // SUCURSAL MANDADA DESDE MENU PRINCIPAL
    Sucursal sucursal = opcSucursal;
    String opciones = "";
    String opcion = "";
    do{
      opciones = JOptionPane.showInputDialog(null, "Bienvenido a la sucursal " +
          sucursal.toString() +
          "\nPor favor Seleccione una de las siguientes opciones: "+
          "\n[1] Agregar Sucursal"+
          "\n[2] Mostrar Lista de Sucursales"+
          "\n[3] Seleccionar Sucursal"+
          "\n[4] Salir"+
          "\nOpcion:\t");
      switch(opciones){
        case null:
          JOptionPane.showMessageDialog(null, "Saliendo del programa...");
          System.exit(0);
        case "1": //LLAMAR AL METODO ADECUADO PARA AGREGAR UNA SUCURSAL
          break;
        case "2":
          break;
        case "3": 
          break;
        case "4":
          JOptionPane.showMessageDialog(null, "Saliendo del programa...");
        default:
          JOptionPane.showMessageDialog(null, "Opcion invalida");
      }
    }while(opciones != "4");
    System.exit(0);
    // MENU EMPLEADOS
    
    // MENU CLIENTES
    
  }
}
