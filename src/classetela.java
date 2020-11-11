import javax.swing.*;
import java.awt.*;

class classetela {
	public static void main(String[] args) {
		
		JFrame tela = new JFrame("Primeira tela");
		JLabel texto = new JLabel("Frase indicativa");
		tela.setBounds(100, 100, 600, 400);
		tela.getContentPane().setBackground(Color.YELLOW);
		tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		texto.setBounds(100, 100, 200, 30);
		tela.setLayout(null);
		tela.add(texto);

		JButton botao = new JButton("Pressione aqui");
		botao.setBounds(100, 200, 200, 30);
		botao.setBackground(Color.GREEN);
		botao.setForeground(Color.BLUE);
		botao.setToolTipText("Um exemplo de ToolTip");
		tela.add(botao);

		tela.setVisible(true);
	}
}