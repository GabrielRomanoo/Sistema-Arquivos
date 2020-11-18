package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class TelaComArvore {

	private JFrame frame;
	Hd hd = new Hd();
	int numeroDeDiretorios;
	int numberOfNodes;
	JTree tree;
	DefaultMutableTreeNode rootNode;
	List<DefaultMutableTreeNode> diretorios = new ArrayList<DefaultMutableTreeNode>();
	List<String> arquivosDiretorio = new ArrayList<String>(); // depois tem que mudar para varios diretorios
	List<String> infosDiretorio = new ArrayList<String>();
	List<Integer> numerosDeNos = new ArrayList<Integer>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaComArvore window = new TelaComArvore();
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JScrollBar scrollBar = new JScrollBar();
		JPanel panel = new JPanel();

		// fazer uma lista de rootNode
		rootNode = new DefaultMutableTreeNode("Diretorio");
		numberOfNodes = 0;
//		tree = new JTree(new DefaultTreeModel(rootNode));
		tree = new JTree();
		tree.setModel(new DefaultTreeModel(rootNode) {
			{
				DefaultMutableTreeNode node_1;
				node_1 = new DefaultMutableTreeNode("Pasta1");
				diretorios.add(node_1);
//				node_1.add(new DefaultMutableTreeNode("ok"));
				rootNode.add(node_1);
			}
		});
		tree.setEditable(true);
		tree.setShowsRootHandles(true);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		progressBar.setForeground(SystemColor.textHighlight);

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(tree, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
								.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(scrollBar, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollBar, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
				.addComponent(tree, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE));

		JFormattedTextField frmtdtxtfldDigiteONome = new JFormattedTextField();
		frmtdtxtfldDigiteONome.setVisible(false);
		frmtdtxtfldDigiteONome.setText("Digite o nome");
		panel.add(frmtdtxtfldDigiteONome);

		JButton btnNewButton = new JButton("enviar");
		btnNewButton.setVisible(false);
		panel.add(btnNewButton);
		frame.getContentPane().setLayout(groupLayout);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmtdtxtfldDigiteONome.setVisible(false);
				btnNewButton.setVisible(false);
				// fazer a programação de guardar as informações
				panel.updateUI();
			}
		});

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Arquivo");
		mnNewMenu.setFont(new Font("Rubik", Font.PLAIN, 12));
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Novo");
		mntmNewMenuItem.setFont(new Font("Rubik", Font.PLAIN, 12));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmtdtxtfldDigiteONome.setVisible(true);
				btnNewButton.setVisible(true);
				panel.updateUI();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Deletar");
		mntmNewMenuItem_1.setFont(new Font("Rubik", Font.PLAIN, 12));
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);

		JMenu mnNewMenu_1 = new JMenu("Comandos");
		mnNewMenu_1.setFont(new Font("Rubik", Font.PLAIN, 12));
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("add-no");
		mntmNewMenuItem_2.setFont(new Font("Rubik", Font.PLAIN, 12));
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numberOfNodes++;
				String nome = JOptionPane.showInputDialog(frame, "Digite o nome do arquivo");
				// hd.verificaArquivo(nome); //tem que terminar de implementar
				arquivosDiretorio.add(nome);
				updateArvore();
				panel.updateUI();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);

		JMenuItem mntmNewMenuItem_1_1 = new JMenuItem("del-no");
		mntmNewMenuItem_1_1.setFont(new Font("Rubik", Font.PLAIN, 12));
		mntmNewMenuItem_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (numberOfNodes > 0) {
					numberOfNodes--;
				}
				String nome = JOptionPane.showInputDialog(frame, "Digite o nome do arquivo");
				arquivosDiretorio.remove(nome);
				updateArvore();
//				System.out.println("removido arquivos do diretorio: " + diretorios.get(0).toString()); // retorna Pasta1
				panel.updateUI();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_1_1);

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

	public void updateArvore() {
		rootNode.removeAllChildren();
		diretorios.get(0).removeAllChildren();
		for (int i = 0; i < numberOfNodes; i++) {
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
			diretorios.get(0).add(new DefaultMutableTreeNode(arquivosDiretorio.get(i)));
			root.add(diretorios.get(0));
			model.reload(root);
		}
//			System.out.println(diretorios.get(0).getNextNode().toString()); //imprime o primeiro nome do arquivo do diretorio 0
//			System.out.println(diretorios.get(0).toString());	
		expandAll();
	}

	public void expandAll() {
		int row = 0;
		while (row < tree.getRowCount()) {
			tree.expandRow(row);
			row++;
		}
	}
}