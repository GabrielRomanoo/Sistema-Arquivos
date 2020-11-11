package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;

public class Tela {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hd hd = new Hd();
					Tela window = new Tela();
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
	public Tela() {
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
		
		JTree tree = new JTree();
		tree.setEditable(true);
		tree.setShowsRootHandles(true);
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Diret\u00F3rio") {
				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("Pasta1");
						node_1.add(new DefaultMutableTreeNode(""));
					add(node_1);
				}
			}
		));		
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		progressBar.setForeground(SystemColor.textHighlight);
		
		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(tree, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollBar, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addComponent(tree, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
		);
		
		JFormattedTextField frmtdtxtfldDigiteONome = new JFormattedTextField();
		frmtdtxtfldDigiteONome.hide();
		frmtdtxtfldDigiteONome.setText("Digite o nome do arquivo");
		panel.add(frmtdtxtfldDigiteONome);
		
		JButton btnNewButton = new JButton("enviar");
		btnNewButton.hide();
		panel.add(btnNewButton);
		frame.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Arquivo");
		mnNewMenu.setFont(new Font("Rubik", Font.PLAIN, 12));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Novo");
		mntmNewMenuItem.setFont(new Font("Rubik", Font.PLAIN, 12));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmtdtxtfldDigiteONome.show();
				btnNewButton.show();
				panel.updateUI();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Deletar");
		mntmNewMenuItem_1.setFont(new Font("Rubik", Font.PLAIN, 12));
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_1 = new JMenu("Comandos");
		mnNewMenu_1.setFont(new Font("Rubik", Font.PLAIN, 12));
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Comando 1");
		mntmNewMenuItem_2.setFont(new Font("Rubik", Font.PLAIN, 12));
		mnNewMenu_1.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_1_1 = new JMenuItem("Comando 2");
		mntmNewMenuItem_1_1.setFont(new Font("Rubik", Font.PLAIN, 12));
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
}
