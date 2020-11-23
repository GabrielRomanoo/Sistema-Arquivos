package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;

public class Hd {

	public static String pathHd = "hd.bin";

	public Hd() {
		verificaHd();
	}

	public void verificaHd() {
		File file = new File(Hd.pathHd);
		if (!file.exists()) {
			criaHd();
		} else {
			Metadado.carregarDados();
		}
	}

	private void criaHd() {
		try {
			OutputStream fos = new FileOutputStream(Hd.pathHd);
			Metadado.criaMetadado();
			fos.close();
		} catch (IOException e) {
			System.out.println("Erro na criação do arquivo");
			e.printStackTrace();
		}
	}

	private void leHd() {
		try {
			InputStream fis = new FileInputStream(Hd.pathHd);
			fis.close();
		} catch (IOException e) {
			System.out.println("Erro na leitura do arquivo hd.bin");
			e.printStackTrace();
		}
	}

	public static void escreverNoHd(String caminho) {
		try {
			File file = new File(caminho);
			Metadado.gravarNoMetadado(file);
			FileOutputStream fos = new FileOutputStream(Hd.pathHd, true);
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
			InputStream fis = new FileInputStream(Hd.pathHd);
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

	public static void excluiDoHd(String nome) {
		try {
			
			String conteudo = "";
			InputStream fis = new FileInputStream(Hd.pathHd);
			Reader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String[] linhaMetadado;
			linhaMetadado = Metadado.buscaDadosArquivo(nome);
			int bytetInicio = Integer.parseInt(linhaMetadado[4]);
			int tamanho = Integer.parseInt(linhaMetadado[3]);
			System.out.println("tamanho :" + tamanho);
			System.out.println("byte inicio : " + bytetInicio);
			System.out.println("byte final: " + (bytetInicio+ tamanho));
			int i = 0;	
			String arquivo = br.readLine();
			conteudo += arquivo.substring(0, bytetInicio);
			conteudo += arquivo.substring(bytetInicio+tamanho, Metadado.tamanhoTotal);
			br.close();
			FileOutputStream fos = new FileOutputStream(Hd.pathHd);
			byte[] bytes = conteudo.getBytes();
			fos.write(bytes);
			fos.close();
			Metadado.excluidoMetadado(nome);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}