package control;

import entidades.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@SuppressWarnings("unused")
public class Persistencia {
	//ATRIBUTOS
	
	
	//GETTERS Y SETTERS
	
	
	
	//CONSTRUCTORES
	
	

	//METODOS
	//METODO PARA LEER LOS OBJETOS SERIALIZADOS
	public static Gimnasio obtenerDatos() {	
		FileInputStream fisPer = null;
		
		try {
			fisPer = new FileInputStream("Gimnasio.ser");
			ObjectInputStream oisPer = new ObjectInputStream(fisPer);
			
			//LEER EL OBJETO SERIALIZADO
			Gimnasio g = (Gimnasio) oisPer.readObject();
			return g;
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static void guardarDatos() {
		FileOutputStream archivo;
		File f =  new File("Gimnasio.ser");
		
		try {
			archivo = new FileOutputStream(f, false);
			ObjectOutputStream oos = new ObjectOutputStream(archivo);
			oos.writeObject(Gimnasio.class);
			oos.close();
			archivo.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}