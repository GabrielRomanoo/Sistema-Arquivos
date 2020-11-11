package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Hd {
	
	private String pathHd = "hd.bin";
	private String pathMetadado = "metadado.bin";

	public Hd() {
		verificaHd();
	}
	
	public void verificaHd() {
		File file = new File(this.pathHd);
		if (!file.exists()) {
			criaHd();
		} else {
			
		}
	}
	
	private void criaHd() {
		try {
			OutputStream fos = new FileOutputStream(this.pathHd);
			Metadado.criaMetadado();
		} catch (FileNotFoundException e) {
        	System.out.println("Erro na criação do arquivo");
			e.printStackTrace();
		}	
	}
	
	private void leHd() {
		
	}
}
