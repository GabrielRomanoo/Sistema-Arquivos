package view;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Metadado {
	
	private static String pathMetadado = "metadado.bin";
		
	public static void criaMetadado() {
		try {
			OutputStream fos = new FileOutputStream(Metadado.pathMetadado);
		} catch (FileNotFoundException e) {
        	System.out.println("Erro na criação do arquivo");
			e.printStackTrace();
		}	
	}
	
	public static void escreveMetadado() {
		
	}
	
	

}
