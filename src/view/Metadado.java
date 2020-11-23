package view;

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

public class Metadado {

	private static String pathMetadado = "metadado.bin";
	private static int numeroDeArquivos = 0;
	public static int tamanhoTotal = 0;

	public static void criaMetadado() {
		try {
			OutputStream fos = new FileOutputStream(Metadado.pathMetadado);
			fos.close();
		} catch (IOException e) {
			System.out.println("Erro na criação do arquivo");
			e.printStackTrace();
		}
	}

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

	public static void carregarDados() {
		try {
			FileInputStream fis = new FileInputStream(Metadado.pathMetadado);
			Reader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String linha = br.readLine();
			while (linha != null) {
				TelaComArvore.numeroDeNos++;
				String[] valores = linha.split(";");
				TelaComArvore.arquivosDiretorio.add(valores[1]);
				linha = br.readLine();
			}
			TelaComArvore.updateArvore();
			br.close();
		} catch (IOException e) {
			numeroDeArquivos--;
			e.printStackTrace();
		}
	}

	public static boolean verificaArquivo(String filepath) {
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
					return false;
				linha = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static String[] buscaDadosArquivo(String nome) {
		try {
			InputStream fis = new FileInputStream(Metadado.pathMetadado);
			Reader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String linha = br.readLine();
			while (linha != null) {
				String[] valores = linha.split(";");
				if (nome.equals(valores[1])) {
					return valores;
				}
				linha = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

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

}