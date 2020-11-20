package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.file.Files;

public class Hd {

	public static String pathHd = "hd.bin";

	public Hd() {
		verificaHd();
	}

	public void verificaHd() {
		File file = new File(this.pathHd);
		if (!file.exists()) {
			criaHd();
		} else {
			// carregar os dados do metadado e colocar na arvore e listas
			Metadado.carregarDados();
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
		try {
			InputStream fis = new FileInputStream(this.pathHd);
		} catch (FileNotFoundException e) {
			System.out.println("Erro na leitura do arquivo");
			e.printStackTrace();
		}
	}

	public static void escreverNoHd(String caminho) {
		// se preocupar como dizer onde o arquivo inicia e termina, e indicar isso no
		// metadado
		try {
			File file = new File(caminho);
			Metadado.gravarNoMetadado(file);
			FileOutputStream fos = new FileOutputStream("hd.bin", true);
			byte[] bytes = Files.readAllBytes(file.toPath());
			fos.write(bytes);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String buscarConteudo(String[] linhaMetadado) {
		String conteudo = "";
		try {
			InputStream fis = new FileInputStream("hd.bin");
			Reader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			int bytetInicio = Integer.parseInt(linhaMetadado[4]);
			int tamanho = Integer.parseInt(linhaMetadado[3]);
			br.skip(bytetInicio);
			for (int i = 0; i < tamanho; i++) {
				conteudo += (char)br.read();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return conteudo;
	}
}