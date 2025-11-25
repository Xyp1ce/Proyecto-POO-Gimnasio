package interfaz;
import control.*;

public class Vista
{
	// ATRIBUTOS
	static Gimnasio gimnasio = new Gimnasio(); // INSTANCIA PRINCIPAL COMPARTIDA POR MENUS

	// CONSTRUCTORES
	public Vista() {}

	// METODOS
	public static void menu()
	{
		// DERIVA AL MENU PRINCIPAL PARA CENTRALIZAR NAVEGACION
		MenuPrincipal.menu();
	}
	// AGREGAR EMPLEADO
}