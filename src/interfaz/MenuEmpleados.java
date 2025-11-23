package interfaz;
import javax.swing.JOptionPane;
import entidades.*;
import control.*;

public class MenuEmpleados {
	public static void menu(Empleado empleado, Sucursal sucursal) {
    String opciones = "";
    do {
      opciones = JOptionPane.showInputDialog(null, "Bienvenido " +
          empleado.getNombre() +
          "\nPor favor Seleccione una de las siguientes opciones: "+
          "\n[1] Mostrar Informacion del Empleado"+
          "\n[2] Modificar Informacion del Empleado"+
          "\n[3] Registrar Entrada" +
          "\n[4] Registrar Salida" +
          "\n[5] Salir"+
          "\nOpcion:\t");

      if (opciones == null) {
        JOptionPane.showMessageDialog(null, "Regresando al menu de sucursal...");
        return;
      }

      switch(opciones){
        case "1": // MOSTRAR INFORMACION DEL EMPLEADO
          JOptionPane.showMessageDialog(null, empleado.toString());
          break;
        case "2": // MODIFICAR INFORMACION DEL EMPLEADO
          try {
            String in = JOptionPane.showInputDialog("Ingrese el nuevo telefono del empleado:");
            if (in == null) break;
            long telefono = Long.parseLong(in.trim());
            empleado.setTelefono(telefono);
            JOptionPane.showMessageDialog(null, "Informacion actualizada exitosamente");
          } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Telefono inválido.");
          }
          break;
        case "3": // REGISTRAR ENTRADA
          empleado.registrarEntrada();
          JOptionPane.showMessageDialog(null, "Entrada registrada exitosamente");
          break;
        case "4": // REGISTRAR SALIDA
          empleado.registrarSalida();
          JOptionPane.showMessageDialog(null, "Salida registrada exitosamente");
          break;
        case "5":
          JOptionPane.showMessageDialog(null, "Regresando al menu de sucursal...");
          MenuSucursal.menu(sucursal);
          return;
        default:
          JOptionPane.showMessageDialog(null, "Opcion invalida");
      }
    } while (!"5".equals(opciones));
  }
}
