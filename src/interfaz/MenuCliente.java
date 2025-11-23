package interfaz;
import javax.swing.JOptionPane;
import control.Sucursal;
import entidades.*;

public class MenuCliente {
  public static void menu(Cliente cliente, Sucursal sucursal) {
    String opciones = "";
    do {
      opciones = JOptionPane.showInputDialog(null, "Bienvenido " +
          cliente.getnombre() +
          "\nPor favor Seleccione una de las siguientes opciones: "+
          "\n[1] Mostrar Informacion del Cliente"+
          "\n[2] Modificar Informacion del Cliente"+
          "\n[3] Registrar Entrada" +
          "\n[4] Registrar Salida" +
          "\n[5] Salir"+
          "\nOpcion:\t");

      if (opciones == null) {
        JOptionPane.showMessageDialog(null, "Regresando al menu de sucursal...");
        return;
      }

      switch(opciones){
        case "1": // MOSTRAR INFORMACION DEL CLIENTE
          JOptionPane.showMessageDialog(null, cliente.toString());
          break;
        case "2": // MODIFICAR INFORMACION DEL CLIENTE
          try {
            String in = JOptionPane.showInputDialog("Ingrese el nuevo telefono del cliente:");
            if (in == null) break;
            long telefono = Long.parseLong(in.trim());
            cliente.settelefono(telefono);
            JOptionPane.showMessageDialog(null, "Informacion actualizada exitosamente");
          } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Telefono inválido.");
          }
          break;
        case "3": // REGISTRAR ENTRADA
          cliente.registrarVisita();
          JOptionPane.showMessageDialog(null, "Entrada registrada exitosamente");
          break;
        case "4": // REGISTRAR SALIDA
          JOptionPane.showMessageDialog(null, "Salida registrada exitosamente");
          break;
        case "5":
          JOptionPane.showMessageDialog(null, "Regresando al menu sucursal...");
          MenuSucursal.menu(sucursal);
          return;
        default:
          JOptionPane.showMessageDialog(null, "Opcion invalida");
      }
    } while (!"5".equals(opciones));

  }
}
