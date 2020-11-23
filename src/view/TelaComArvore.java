package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

public class TelaComArvore {

	/* Declara as referencias */
	private JFrame frame;
	int numeroDeDiretorios;
	public static int numeroDeNos = 0;
	private static JTree tree;
	private static DefaultMutableTreeNode rootNode;
	JTextArea textArea;

	/* Listas auxiliares */
	public static List<DefaultMutableTreeNode> diretorios = new ArrayList<DefaultMutableTreeNode>();
	public static List<String> arquivosDiretorio = new ArrayList<String>(); // depois tem que mudar para varios
																			// diretorios
	List<String> infosDiretorio = new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaComArvore window = new TelaComArvore();
					Hd hd = new Hd();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaComArvore() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
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

		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		progressBar.setForeground(SystemColor.textHighlight);

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(tree, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
								.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE))
						.addGap(23)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(tree, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE).addContainerGap()));

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
//					System.out.println("removido arquivos do diretorio: " + diretorios.get(0).toString()); // retorna Pasta1
					JOptionPane.showMessageDialog(frame, "Removido o " + nome + " arquivo do hd!");	
				}
				panel.updateUI();
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
				textArea.setVisible(true);
				textArea.setText(conteudoMetadado);
			}
		});

		JMenuItem open = new JMenuItem("FileChooser");
		open.setFont(new Font("Rubik", Font.PLAIN, 12));
		mnNewMenu.add(open);
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == open) {
					JFileChooser fc = new JFileChooser();
					int i = fc.showOpenDialog(open);
					if (i == JFileChooser.APPROVE_OPTION) {
						File f = fc.getSelectedFile();
						String filepath = f.getPath();
						try {
							BufferedReader br = new BufferedReader(new FileReader(filepath));
							String s1 = "", s2 = "";
							while ((s1 = br.readLine()) != null) {
								s2 += s1 + "\n";
							}
							s2 = s2.substring(0, s2.length() - 1);
							textArea.setVisible(true);
							textArea.setText(s2);
							br.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		});

		JMenu mnNewMenu_2 = new JMenu("Op\u00E7\u00F5es");
		mnNewMenu_2.setFont(new Font("Rubik", Font.PLAIN, 12));
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Visualizar Log...");
		mntmNewMenuItem_3.setFont(new Font("Rubik", Font.PLAIN, 12));
		mnNewMenu_2.add(mntmNewMenuItem_3);

		JMenuItem mntmNewMenuItem_1_2 = new JMenuItem("Deletar Log");
		mntmNewMenuItem_1_2.setFont(new Font("Rubik", Font.PLAIN, 12));
		mnNewMenu_2.add(mntmNewMenuItem_1_2);

		JMenu mnAjuda = new JMenu("Ajuda");
		mnAjuda.setFont(new Font("Rubik", Font.PLAIN, 12));
		menuBar.add(mnAjuda);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Guia...");
		mntmNewMenuItem_4.setFont(new Font("Rubik", Font.PLAIN, 12));
		mnAjuda.add(mntmNewMenuItem_4);

		JMenuItem mntmNewMenuItem_1_3 = new JMenuItem("Sobre...");
		mntmNewMenuItem_1_3.setFont(new Font("Rubik", Font.PLAIN, 12));
		mnAjuda.add(mntmNewMenuItem_1_3);
	}

	public static void updateArvore() {
		TelaComArvore.rootNode.removeAllChildren();
		diretorios.get(0).removeAllChildren();
		for (int i = 0; i < numeroDeNos; i++) {
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
			diretorios.get(0).add(new DefaultMutableTreeNode(arquivosDiretorio.get(i)));
			root.add(diretorios.get(0));
			model.reload(root);
		}
//		System.out.println(diretorios.get(0).getNextNode().toString()); //imprime o primeiro nome do arquivo do diretorio 0
//		System.out.println(diretorios.get(0).toString());	
		expandAll();
	}

	public static void expandAll() {
		int row = 0;
		while (row < tree.getRowCount()) {
			tree.expandRow(row);
			row++;
		}
	}

	public void treeValueChanged(TreeSelectionEvent tse) {
		String node = tse.getNewLeadSelectionPath().getLastPathComponent().toString();
		arquivosDiretorio.forEach(elemento -> {
			if (node.toString() == elemento.toString()) {
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
}