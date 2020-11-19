package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Arrays;

public class Metadado {

	private static String pathMetadado = "metadado.bin";
	private static int numeroDeArquivos = 0;

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

	public static void gravarNoMetadado(File file) {
		try {
			FileOutputStream fos = new FileOutputStream("metadado.bin", true);
			numeroDeArquivos++;
			String conteudo = numeroDeArquivos + "@" +";" + file.getName() + ";" + Files.size(file.toPath()) + ";";
			byte[] bytes = conteudo.getBytes();
			fos.write(bytes);
			fos.write('\n');
			fos.close();
		} catch (IOException e) {
			numeroDeArquivos--;
			e.printStackTrace();
		}
		
	}

	public static void carregarDados() {
		try {
			FileInputStream fis = new FileInputStream("metadado.bin");
			Reader isr = new InputStreamReader(fis); 
			BufferedReader br = new BufferedReader(isr);
			String linha = br.readLine();
			while (linha != null) {
				TelaComArvore.numeroDeNos++;
				String[] valores = linha.split(";");
				TelaComArvore.arquivosDiretorio.add(valores[1]);
				System.out.println(valores);
				linha = br.readLine(); 
			}
			TelaComArvore.updateArvore();
			fis.close();
		} catch (IOException e) {
			numeroDeArquivos--;
			e.printStackTrace();
		}
	}

}
