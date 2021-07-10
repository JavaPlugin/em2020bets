package de.em2020.window;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Window.Type;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import de.em2020.main;
import de.em2020.manager.bets;
import de.em2020.manager.game;
import de.em2020.manager.userManager;
import de.em2020.window.admin.adminPanel;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.ScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JToolBar;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import java.awt.CardLayout;
import javax.swing.SpringLayout;
import java.awt.GridBagLayout;
import javax.swing.ScrollPaneConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.JTextArea;

public class window {

	private JFrame frmEmBets;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window window = new window();
					window.frmEmBets.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("null")
	private void initialize() {
		frmEmBets = new JFrame();
		frmEmBets.setResizable(false);
		frmEmBets.setTitle("EM 2020 Bets");
		frmEmBets.setBounds(100, 100, 851, 478);
		frmEmBets.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 157, 449);
		panel.setBackground(Color.LIGHT_GRAY);
		

		
		
		if(de.em2020.main.userManager == null) {
				JButton btnNewButton = new JButton("Login");
				panel.add(btnNewButton);
		
				JButton btnNewButton_1 = new JButton("SignIn");
				panel.add(btnNewButton_1);
				btnNewButton_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frmEmBets.dispose();
						signin.main(null);
					}
				});
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frmEmBets.dispose();
						login.main(null);
					}
				});
		}else {
				main.userManager.checkBets();
			
				JLabel lblNewLabel = new JLabel("Welcome " + main.userManager.getUnsername());
				panel.add(lblNewLabel);
				
				JLabel lblNewLabe2 = new JLabel("Your balance is " + main.userManager.getMoney() + " €");
				panel.add(lblNewLabe2);
		
				JButton btnNewButton_2 = new JButton("Logout");
				panel.add(btnNewButton_2);
				btnNewButton_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
				
				
				de.em2020.main.userManager = null;
				frmEmBets.dispose();
				main(null);
				
			}
		});
		}
		frmEmBets.getContentPane().setLayout(null);
		frmEmBets.getContentPane().add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(167, 27, 339, 336);
		frmEmBets.getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton_3 = new JButton("bet for this game");
		btnNewButton_3.setEnabled(false);
		btnNewButton_3.setBounds(167, 374, 339, 23);
		frmEmBets.getContentPane().add(btnNewButton_3);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		HashMap<Integer, game> gameInList = new HashMap<>();
		
		String[] nextGames = new String[] {"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
		int i = 0;
		for(game games : main.gameManager.getAll_games()) { 
			if(!games.isPlaying() && !games.hasResult()) {
				boolean exclude = false;
				if(main.userManager != null) {
					for(bets bet : main.userManager.getUser_betlist()) {
						if(bet.getGame().getName().equals(games.getName())) {
							exclude = true;
						}
					}
					if(!exclude) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm dd/MM/yyyy");
						gameInList.put(i, games);
						nextGames[i++] = games.getTeam1().toString().replaceAll("_", " ") + " vs. " + games.getTeam2().toString().replaceAll("_", " ") + " play " + dateFormat.format(games.getDate());
					}
				}else {
					SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm dd/MM/yyyy");
					gameInList.put(i, games);
					nextGames[i++] = games.getTeam1().toString().replaceAll("_", " ") + " vs. " + games.getTeam2().toString().replaceAll("_", " ") + " play " + dateFormat.format(games.getDate());
				}
			}
		} 
		
		JList list = new JList();
		list.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(de.em2020.main.userManager != null) {
					if(gameInList.get(list.getSelectedIndex()) != null) {
						btnNewButton_3.setEnabled(true);
					}
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(de.em2020.main.userManager != null) {
					System.out.println(e.getSource());
					//btnNewButton_3.setEnabled(false);
				}
			}
		});
		list.setVisibleRowCount(1000);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel() {
			String[] values = nextGames;
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(list);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(516, 27, 319, 336);
		frmEmBets.getContentPane().add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_2.add(scrollPane_1, BorderLayout.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("Next games");
		lblNewLabel_1.setBounds(167, 2, 339, 23);
		frmEmBets.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("My bets");
		lblNewLabel_1_1.setBounds(516, 0, 319, 23);
		frmEmBets.getContentPane().add(lblNewLabel_1_1);
		
		
		HashMap<Integer, bets> betsInList = new HashMap<>();
		
		String[] nextBets = new String[] {"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
		int b = 0;
		if(main.userManager != null) {
			for(bets bet : main.userManager.getUser_betlist()) { 
					
				SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm dd/MM/yyyy");
				betsInList.put(b, bet);
				
				String output = bet.getAmount() +" € that "+ bet.getGame().getTeam1() + " vs " + bet.getGame().getTeam2() + " play " +bet.getResult1() +":" + bet.getResult2();
				
				if(bet.getOutpayed()) {
					if(bet.getGame().getWinner().equals(bet.getWinner()) && bet.getResult1() == bet.getGame().getResult1() && bet.getResult2() == bet.getGame().getResult2()){//a=5
						
						output = output + " -> you won " + bet.getAmount()*5 + " €";
						
					}else if(bet.getWinner().equals(bet.getGame().getWinner())) {//a=2
						
						output = output + " -> you choosed the right team " + bet.getAmount()*2 + " €";
						
					}else {//a=0
						
						output = output + " -> you lost -"+ bet.getAmount() +" €";
						
					}
				}
				
				nextBets[b++] = output;
			}
		} 
		
		
		JList list_1 = new JList();
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_1.setModel(new AbstractListModel() {
			String[] values = nextBets;
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		
		scrollPane_1.setViewportView(list_1);
		
		JButton btnNewButton_4 = new JButton("open Admin Panel");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frmEmBets.dispose();
				adminPanel.main(null);
				
			}
		});
		btnNewButton_4.setBounds(663, 415, 172, 23);
		
		if(main.userManager != null) {
			frmEmBets.getContentPane().add(btnNewButton_4);
		}
		
		
		
		
		
		
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmEmBets.dispose();
				try {
					new bet(gameInList.get(list.getSelectedIndex()));
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
		});

		
	}
}
