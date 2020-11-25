package br.com.unisantos.sistema.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import br.com.unisantos.sistema.modelo.Hd;
import br.com.unisantos.sistema.modelo.Metadado;

/**
 * Classe que representa a Tela do programa
 * 
 * @author Gabriel Romano, Felipe Ferreira, Jaime Mathias, Willy Pestana, Marcus Vinicius e Will.
 *
 */

public class Tela {

	/**
	 * Declaração e inicialização dos atributos da classe.
	 */
	private JFrame frame;
	int numeroDeDiretorios;
	public static int numeroDeNos = 0;
	private static JTree tree;
	private static DefaultMutableTreeNode rootNode;
	public static List<DefaultMutableTreeNode> diretorios = new ArrayList<DefaultMutableTreeNode>();
	public static List<String> arquivosDiretorio = new ArrayList<String>(); 
	List<String> infosDiretorio = new ArrayList<String>();
	JTextArea textArea;
	JPanel panel;

	/**
	 * Lança a aplicação.
	 * @param args String[]
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela window = new Tela();
					@SuppressWarnings("unused")
					Hd hd = new Hd();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Construtor para inicializar a tela.
	 */
	public Tela() {
		initialize();
	}

	/**
	 * Método que inicializa os conteúdos do frame
	 */
	@SuppressWarnings("serial")
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.setVisible(true);

		rootNode = new DefaultMutableTreeNode("Disco (C:)");
		numeroDeNos = 0;
		numeroDeDiretorios = 0;
		tree = new JTree();
		tree.setEditable(true);
		tree.setShowsRootHandles(true);
		tree.setModel(new DefaultTreeModel(rootNode) {
			{
				DefaultMutableTreeNode node_1;
				node_1 = new DefaultMutableTreeNode("Diretorio 1");
				diretorios.add(node_1);
				numeroDeDiretorios++;
				rootNode.add(node_1);
			}
		});

		tree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
				treeValueChanged(evt);
			}
		});

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tree, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
					.addGap(23))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
						.addComponent(tree, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
					.addContainerGap())
		);

		textArea = new JTextArea();
		panel.add(textArea);
		frame.getContentPane().setLayout(groupLayout);
		textArea.setEditable(true);
		textArea.setVisible(true);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnNewMenu_1 = new JMenu("Arquivo");
		mnNewMenu_1.setFont(new Font("Rubik", Font.PLAIN, 12));
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Adicionar");
		mntmNewMenuItem_2.setFont(new Font("Rubik", Font.PLAIN, 12));
		mnNewMenu_1.add(mntmNewMenuItem_2);
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == mntmNewMenuItem_2) {
					JFileChooser fc = new JFileChooser();
					int i = fc.showOpenDialog(mntmNewMenuItem_2);
					if (i == JFileChooser.APPROVE_OPTION) {
						File f = fc.getSelectedFile();
						String filepath = f.getPath();
						try {
							numeroDeNos++;
							if (Metadado.verificaArquivo(filepath)) {
								Hd.escreverNoHd(filepath);
								arquivosDiretorio.add(f.getName());
								updateArvore();
							} else {
								JOptionPane.showMessageDialog(frame, "Este arquivo já está gravado no hd!");
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
				panel.updateUI();
			}
		});

		JMenuItem mntmNewMenuItem_1_1 = new JMenuItem("Remover");
		mntmNewMenuItem_1_1.setFont(new Font("Rubik", Font.PLAIN, 12));
		mnNewMenu_1.add(mntmNewMenuItem_1_1);
		mntmNewMenuItem_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean arquivoExiste = false;
				String nome = JOptionPane.showInputDialog(frame, "Digite o nome do arquivo (com extensão)");
				for (String string : arquivosDiretorio) {
					if (string.equals(nome)) {
						arquivoExiste = true;
					}
				}
				if (arquivoExiste != true) {
					JOptionPane.showMessageDialog(frame, "O arquivo " + nome + " não existe no hd!");
				} else {
					numeroDeNos--;
					Hd.excluiDoHd(nome);
					arquivosDiretorio.remove(nome);
					updateArvore();
					JOptionPane.showMessageDialog(frame, "Removido o " + nome + " arquivo do hd!");	
				}
				panel.updateUI();
				tree.updateUI();
			}
		});

		JMenu mnNewMenu = new JMenu("Comandos");
		mnNewMenu.setFont(new Font("Rubik", Font.PLAIN, 12));
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Visualizar Metadados");
		mntmNewMenuItem.setFont(new Font("Rubik", Font.PLAIN, 12));
		mnNewMenu.add(mntmNewMenuItem);
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String conteudoMetadado = Metadado.lerMetadado();
				if (conteudoMetadado.isEmpty())
					JOptionPane.showMessageDialog(frame, "O arquivo metadado.bin está vazio!");
				else
					textArea.setVisible(true);
					textArea.setText(conteudoMetadado);
			}
		});
		
		JMenuItem mntmTamanhoHd = new JMenuItem("Tamanho HD");
		mntmTamanhoHd.setFont(new Font("Rubik", Font.PLAIN, 12));
		mnNewMenu.add(mntmTamanhoHd);
		mntmTamanhoHd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Tamanho do HD: "+ Metadado.tamanhoTotal + " bytes");
			}
		});

		JMenu mnAjuda = new JMenu("Ajuda");
		mnAjuda.setFont(new Font("Rubik", Font.PLAIN, 12));
		menuBar.add(mnAjuda);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Guia...");
		mntmNewMenuItem_4.setFont(new Font("Rubik", Font.PLAIN, 12));
		mnAjuda.add(mntmNewMenuItem_4);
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Para mais informações, acesse: github.com/GabrielRomanoo/Sistema-Arquivos");
			}
		});

		JMenuItem mntmNewMenuItem_1_3 = new JMenuItem("Sobre...");
		mntmNewMenuItem_1_3.setFont(new Font("Rubik", Font.PLAIN, 12));
		mnAjuda.add(mntmNewMenuItem_1_3);
		mntmNewMenuItem_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "      Simulador de um Gerenciador de Arquivos\nDesenvolvedores: Gabriel Romano, Felipe Ferreira,\n Jaime Mathias, Willy Pestana, Marcus Vinicius e Will");
			}
		});
	}

	/**
	 * Método que atualiza a arvore de arquivos após uma remoção ou inserção.
	 */
	public static void updateArvore() {
		Tela.rootNode.removeAllChildren();
		diretorios.get(0).removeAllChildren();
		for (int i = 0; i < numeroDeNos; i++) {
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
			diretorios.get(0).add(new DefaultMutableTreeNode(arquivosDiretorio.get(i)));
			root.add(diretorios.get(0));
			model.reload(root);
		}
		expandAll();
	}

	/**
	 * Método que expande a arvore de arquivos.
	 */
	public static void expandAll() {
		int row = 0;
		while (row < tree.getRowCount()) {
			tree.expandRow(row);
			row++;
		}
	}

	/**
	 * Método que mostra na tela o conteúdo do arquivo ao clicar nele.
	 * 
	 * @param tse TreeSelectionEvent
	 */
	public void treeValueChanged(TreeSelectionEvent tse) {
		try {
		String node = tse.getNewLeadSelectionPath().getLastPathComponent().toString();
		arquivosDiretorio.forEach(elemento -> {
			if (node.toString().equals(elemento.toString())) {
				String extensao = node.toString().substring(node.length() - 3, node.length());
				if (extensao.equals("txt")) {
					String[] linhaMetadado = Metadado.buscaDadosArquivo(elemento.toString());
					String conteudo = Hd.buscarConteudo(linhaMetadado);
					textArea.setVisible(true);
					textArea.setText(conteudo);
				} else {
					JOptionPane.showMessageDialog(frame, "É possível apenas ler aquivos (.txt)");
				}
			}
		});
		}
		catch(Exception e) {
			System.out.println("Erro ao clicar na arvore!");
			e.printStackTrace();
		}
		panel.updateUI();
	}
}