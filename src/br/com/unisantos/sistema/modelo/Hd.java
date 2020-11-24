package br.com.unisantos.sistema.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.file.Files;

/**
 * Classe que representa um HD
 * 
 * @author Gabriel Romano, Felipe Ferreira, Jaime Mathias, Willy Pestana, Marcus Vinicius e Will.
 *
 */

public class Hd {

	public static String pathHd = "Hd.bin";

	/**
	 * Construtor que verifica se o Hd.bin já está criado. Caso contrário, carrega os dados do Hd.bin.
	 */
	public Hd() {
		File file = new File(Hd.pathHd);
		if (!file.exists()) {
			criaHd();
		} else {
			Metadado.carregarDados();
		}
	}

	/**
	 * Método que cria o arquivo Hd.bin de forma binária.
	 */
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
	
	/**
	 * Método estático que escreve o conteúdo de um arquivo no Hd.bin, a partir do nome desse arquivo.
	 * 
	 * @param caminho String
	 */
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

	/**
	 * Método estático para retornar o conteúdo de um arquivo no Hd.bin, a partir da linha desse arquivo no Metadado.bin.
	 * 
	 * @param linhaMetadado String[]
	 * @return String
	 */
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
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return conteudo;
	}

	/**
	 * Método estático para exlcuir os dados de um arquivo no Hd.bin, a partir do nome do arquivo.
	 * 
	 * @param nome String
	 */
	public static void excluiDoHd(String nome) {
		try {
			String conteudo = "";
			InputStream fis = new FileInputStream(Hd.pathHd);
			Reader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String[] linhaMetadado;
			linhaMetadado = Metadado.buscaDadosArquivo(nome);
			int byteInicio = Integer.parseInt(linhaMetadado[4]);
			int tamanho = Integer.parseInt(linhaMetadado[3]);
			int byteFinal = byteInicio + tamanho;
			String arquivo = "";
			arquivo = String.valueOf((char)br.read());
			for (int i = 0; i < Metadado.tamanhoTotal; i++) {
				arquivo += (char)br.read();
			}
			conteudo = arquivo.substring(0, byteInicio);
			conteudo +=  arquivo.substring(byteFinal, Metadado.tamanhoTotal);
			br.close();
			FileOutputStream fos = new FileOutputStream(Hd.pathHd);
			byte[] bytes = conteudo.getBytes();
			fos.write(bytes);
			fos.close();
			Metadado.atualizaMetadado(nome, tamanho);
			Metadado.excluidoMetadado(nome);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}