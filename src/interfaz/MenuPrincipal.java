package interfaz;
import javax.swing.JOptionPane;
import control.*;

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
          JOptionPane.showMessageDialog(null, "Regresando (cancelado)");
          return;
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
          if (opcion == null) break; // usuario canceló
          try {
            opcSurcusal = Integer.parseInt(opcion.trim());
          } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida. Introduce el número de sucursal mostrado.");
            break;
          }
          if (gimnasio.buscarSucursal(opcSurcusal) != null) {
            MenuSucursal.menu(gimnasio.buscarSucursal(opcSurcusal));
          } else {
            JOptionPane.showMessageDialog(null, "No se encontró esa sucursal\n");
          }
          break;
        case "4":
          JOptionPane.showMessageDialog(null, "Saliendo del programa...");
          System.exit(0);
        default:
          JOptionPane.showMessageDialog(null, "Opcion invalida");
      }
    } while (!"4".equals(opciones));
  }

  public static void agregarSucursal(){
    try {
      String inputNo = JOptionPane.showInputDialog("Ingrese el numero de la sucursal");
      if (inputNo == null) return; // cancelar
      int noSucursal = Integer.parseInt(inputNo.trim());

      String nombre = JOptionPane.showInputDialog("Ingrese el nombre de la Sucursal:");
      if (nombre == null) return;
      String horario = JOptionPane.showInputDialog("Ingrese el horario en el siguiente formato hh:hh\n(Hora de apertura : Hora de Cierre)");
      if (horario == null) return;
      String ubicacion = JOptionPane.showInputDialog("Ingrese la Direccion de la Sucursal");
      if (ubicacion == null) return;
      String servicios = JOptionPane.showInputDialog("Ingrese los servicios especializados realizados por la sucursal");
      if (servicios == null) return;

      String cuotaStr = JOptionPane.showInputDialog("Ingrese la cuota");
      if (cuotaStr == null) return;
      float cuota = Float.parseFloat(cuotaStr.trim());

      Sucursal sucursal = new Sucursal(noSucursal, nombre, horario, ubicacion, servicios, cuota );
      gimnasio.addSucursal(sucursal);

      JOptionPane.showMessageDialog(null, "Sucursal agregada exitosamente");
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(null, "Entrada numérica inválida. Operación cancelada.");
    }
  }
}
