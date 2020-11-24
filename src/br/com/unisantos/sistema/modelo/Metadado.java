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

import br.com.unisantos.sistema.view.Tela;

/**
 * Classe que representa um Metadado
 * 
 * @author Gabriel Romano, Felipe Ferreira, Jaime Mathias, Willy Pestana, Marcus Vinicius e Will.
 *
 */

public class Metadado {

	private static String pathMetadado = "Metadado.bin";
	private static int numeroDeArquivos = 0;
	public static int tamanhoTotal = 0;

	/**
	 * Método estático que cria o arquivo metadado.bin.
	 */
	public static void criaMetadado() {
		try {
			OutputStream fos = new FileOutputStream(Metadado.pathMetadado);
			fos.close();
		} catch (IOException e) {
			System.out.println("Erro na criação do arquivo");
			e.printStackTrace();
		}
	}

	/**
	 * Método que grava os dados de um arquivo no Metadado.bin, a partir do nome desse arquivo.
	 * 
	 * @param file File
	 */
	public static void gravarNoMetadado(File file) {
		try {
			FileOutputStream fos = new FileOutputStream(Metadado.pathMetadado, true);
			numeroDeArquivos++;
			File hd = new File(Hd.pathHd);
			String conteudo = numeroDeArquivos + "@" + ";" + file.getName() + ";" + file.getPath() + ";"
					+ Files.size(file.toPath()) + ";" + hd.length() + ";" + (hd.length() + file.length()) + ";";
			byte[] bytes = conteudo.getBytes();
			fos.write(bytes);
			fos.write('\n');
			fos.close();
			tamanhoTotal = (int) (hd.length() + file.length());
		} catch (IOException e) {
			numeroDeArquivos--;
			e.printStackTrace();
		}
	}

	/**
	 * Método que carrega os dados do Metadado.bin para o programa.
	 */
	public static void carregarDados() {
		try {
			FileInputStream fis = new FileInputStream(Metadado.pathMetadado);
			Reader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String linha = br.readLine();
			while (linha != null) {
				Tela.numeroDeNos++;
				String[] valores = linha.split(";");
				Tela.arquivosDiretorio.add(valores[1]);
				Metadado.tamanhoTotal = Integer.valueOf(valores[5]);
				linha = br.readLine();
			}
			Tela.updateArvore();
			br.close();
		} catch (IOException e) {
			numeroDeArquivos--;
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que verifica se um arquivo está gravado no Metadado.bin, a partir do nome desse arquivo.
	 * 
	 * @param filepath - String
	 * @return booelan
	 */
	public static boolean verificaArquivo(String filepath) {
		boolean existeArquivo = true;
		try {
			InputStream fis = new FileInputStream(Metadado.pathMetadado);
			Reader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String linha = br.readLine();
			while (linha != null) {
				String[] valores = linha.split(";");
				String[] numero = valores[0].split("@");
				numeroDeArquivos = Integer.parseInt(numero[0]);
				if (filepath.equals(valores[2]))
					existeArquivo = false;
				linha = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return existeArquivo;
	}

	/**
	 * Método que retorna a linha de um determinado arquivo no Metadado.bin, a partir do seu nome.
	 * 
	 * @param nome String
	 * @return String[]
	 */
	public static String[] buscaDadosArquivo(String nome) {
		String[] valor = null;
		try {
			InputStream fis = new FileInputStream(Metadado.pathMetadado);
			Reader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String linha = br.readLine();
			while (linha != null) {
				String[] valores = linha.split(";");
				if (nome.equals(valores[1])) {
					valor = valores;
				}
				linha = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return valor;
	}

	/**
	 * Método estático que retorna os dados do Metadado.bin.
	 * 
	 * @return String
	 */
	public static String lerMetadado() {
		String conteudo = "";
		try {
			InputStream fis = new FileInputStream(Metadado.pathMetadado);
			Reader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String linha = br.readLine();
			while (linha != null) {
				conteudo += linha + "\n";
				linha = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return conteudo;
	}

	/**
	 * Método estático que exclui os dados de uma determinado arquvio no Metadado.bin, a partir do nome desse arquivo.
	 * 
	 * @param nome String
	 */
	public static void excluidoMetadado(String nome) {
		String conteudo = "";
		try {
			InputStream fis = new FileInputStream(Metadado.pathMetadado);
			Reader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String linha = br.readLine();
			while (linha != null) {
				String[] valores = linha.split(";");
				if (!(valores[1].equals(nome))) {
					conteudo += linha + "\n";
				}
				linha = br.readLine();
			}
			br.close();
			FileOutputStream fos = new FileOutputStream(Metadado.pathMetadado);
			byte[] bytes = conteudo.getBytes();
			fos.write(bytes);
			fos.close();
			numeroDeArquivos--;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método estático que atualiza os dados do Metadado.bin após uma remoção, a partir do nome do arquivo e o seu tamanho.
	 * 
	 * @param nome String
	 * @param tamanho int
	 */
	public static void atualizaMetadado(String nome, int tamanho) {
		String conteudo = "";
		String[] linhaArquivo = Metadado.buscaDadosArquivo(nome);
		int numeroLinha = Integer.valueOf(linhaArquivo[0].substring(0, 1));
		try {
			InputStream fis = new FileInputStream(Metadado.pathMetadado);
			Reader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String linha = br.readLine();
			while (linha != null) {
				String[] valores = linha.split(";");
				if (Integer.valueOf(valores[0].substring(0, 1)) < numeroLinha) {
					conteudo += valores[0]+ ";" + valores[1] + ";" + valores[2] + ";"
							+ valores[3] + ";" + valores[4] + ";" + valores[5] + ";" + "\n";
				}
				if (Integer.valueOf(valores[0].substring(0, 1)) > numeroLinha) {
					int valor = Integer.valueOf(valores[4]) - tamanho;
					valores[4] = String.valueOf(valor);
					valor = Integer.valueOf(valores[5]) - tamanho;
					valores[5] = String.valueOf(valor);
					conteudo += valores[0]+ ";" + valores[1] + ";" + valores[2] + ";"
							+ valores[3] + ";" + valores[4] + ";" + valores[5] + ";" + "\n";
				}
				linha = br.readLine();
			}
			br.close();
			FileOutputStream fos = new FileOutputStream(Metadado.pathMetadado);
			byte[] bytes = conteudo.getBytes();
			fos.write(bytes);
			fos.close();
			tamanhoTotal -= Integer.valueOf(linhaArquivo[3]);
			numeroDeArquivos--;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}