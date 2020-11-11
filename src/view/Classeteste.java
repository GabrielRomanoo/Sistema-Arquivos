package view;

public class Classeteste {
	
	private String valor;
	private String nomeArquivo;

	public Classeteste(String valor) {
		this.valor = valor;
	}
	
	public Classeteste() {}
	
	public void imprime() {
		System.out.println(this.valor);
	}
	
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

}
