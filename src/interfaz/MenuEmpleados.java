package interfaz;
import javax.swing.JOptionPane;
import entidades.*;
import control.*;

public class MenuEmpleados
{
	public static void menu(Empleado empleado, Sucursal sucursal)
	{
		// ADMINISTRA LAS ACCIONES DISPONIBLES PARA EL EMPLEADO SELECCIONADO
		String opciones = "";
		do
		{
			opciones = JOptionPane.showInputDialog(null, "Bienvenido " +
					empleado.obtenerNombre() +
					"\nPor favor Seleccione una de las siguientes opciones: " +
					"\n[1] Mostrar Informacion del Empleado" +
					"\n[2] Modificar Informacion del Empleado" +
					"\n[3] Registrar Entrada" +
					"\n[4] Registrar Salida" +
					"\n[5] Salir" +
					"\nOpcion:\t");

			if (opciones == null)
			{
				// REGRESAR SIN ALTERAR DATOS CUANDO SE CANCELA EL DIALOGO
				JOptionPane.showMessageDialog(null, "Regresando al menu de sucursal...");
				return;
			}

			switch (opciones)
			{
				case "1": // MOSTRAR INFORMACION DEL EMPLEADO
				{
					JOptionPane.showMessageDialog(null, empleado.toString());
					break;
				}
				case "2": // MODIFICAR INFORMACION DEL EMPLEADO
				{
					try
					{
						String in = JOptionPane.showInputDialog("Ingrese el nuevo telefono del empleado:");
						if (in == null) break;
						long telefono = Long.parseLong(in.trim());
						empleado.definirTelefono(telefono);
						JOptionPane.showMessageDialog(null, "Informacion actualizada exitosamente");
					}
					catch (NumberFormatException ex)
					{
						JOptionPane.showMessageDialog(null, "Telefono invalido.");
					}
					break;
				}
				case "3": // REGISTRAR ENTRADA
				{
					// ACTUALIZA MARCA DE ENTRADA PARA REPORTES DE ASISTENCIA
					empleado.registrarEntrada();
					JOptionPane.showMessageDialog(null, "Entrada registrada exitosamente");
					break;
				}
				case "4": // REGISTRAR SALIDA
				{
					// COMPLETA EL CICLO DE ASISTENCIA PARA EL EMPLEADO
					empleado.registrarSalida();
					JOptionPane.showMessageDialog(null, "Salida registrada exitosamente");
					break;
				}
				case "5":
				{
					JOptionPane.showMessageDialog(null, "Regresando al menu de sucursal...");
					MenuSucursal.menu(sucursal);
					return;
				}
				default:
						JOptionPane.showMessageDialog(null, "Opcion invalida");
			}
		}
		while (!"5".equals(opciones));
	}
}