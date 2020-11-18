package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Hd {
	
	private String pathHd = "hd.bin";

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

	public void verificaArquivo(String nome) {
		try {
			InputStream fos = new FileInputStream(nome);
			//escrever o arquivo no hd
			escreverNoHd(fos);
		} catch (FileNotFoundException e) {
			System.out.println("Erro na leitura do arquivo, no metodo verificaArquivo");
			e.printStackTrace();
		}
	}

	private void escreverNoHd(InputStream fos) {
		//se preocupar como dizer onde o arquivo inicia e termina, e indicar isso no metadado
		Metadado.escreveMetadado();
	}
}
