package de.em2020.window.admin;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import de.API.EinSpigotPlugin.SQL.SQL;
import de.em2020.main;
import de.em2020.enums.Teams;
import de.em2020.manager.bets;
import de.em2020.manager.game;
import de.em2020.manager.gameManager;
import de.em2020.window.window;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.HashMap;
import java.util.Calendar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JRadioButton;

public class adminPanel extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adminPanel frame = new adminPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public adminPanel() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 819, 574);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setToolTipText("");
		comboBox.setModel(new DefaultComboBoxModel(Teams.values()));
		comboBox.setBounds(0, 29, 204, 22);
		panel.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Add new game");
		lblNewLabel.setBounds(0, 11, 191, 14);
		panel.add(lblNewLabel);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(Teams.values()));
		comboBox_1.setBounds(239, 29, 204, 22);
		panel.add(comboBox_1);
		
		JLabel lblNewLabel_1 = new JLabel("vs.");
		lblNewLabel_1.setBounds(214, 33, 30, 14);
		panel.add(lblNewLabel_1);
		
		JSpinner spinner_4 = new JSpinner();
		spinner_4.setModel(new SpinnerDateModel(new Date(1625090400000L), new Date(1625090400000L), new Date(1656626400000L), Calendar.DAY_OF_YEAR));
		spinner_4.setBounds(486, 30, 114, 20);
		panel.add(spinner_4);
		
		JButton btnNewButton = new JButton("Add game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!Teams.valueOf(comboBox.getSelectedItem().toString()).equals(Teams.valueOf(comboBox_1.getSelectedItem().toString()))) {
					
					Date date = (Date)spinner_4.getValue();
					
					if(System.currentTimeMillis() < date.getTime()) {
						new gameManager().addnewGame(Teams.valueOf(comboBox.getSelectedItem().toString()), Teams.valueOf(comboBox_1.getSelectedItem().toString()), (Date)spinner_4.getValue());
						main.gameManager = new gameManager();
						dispose();
						JOptionPane.showMessageDialog(null,"added game succesfully!");
						main(null);
					}else {
						JOptionPane.showMessageDialog(null,"please specify a date that has not yet been");
					}
				}else {
					JOptionPane.showMessageDialog(null,"please specify different teams");
				}
			}
		});
		btnNewButton.setBounds(624, 29, 169, 23);
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(61, 78, 339, 336);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		
		HashMap<Integer, game> gameInList = new HashMap<>();
		
		String[] nextGames = new String[] {"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
		int i = 0;
		for(game games : main.gameManager.getAll_games()) {
				boolean exclude = false;
					if(games.hasResult()) {
							exclude = true;
					}
					if(!exclude) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm dd/MM/yyyy");
						gameInList.put(i, games);
						nextGames[i++] = games.getTeam1().toString().replaceAll("_", " ") + " vs. " + games.getTeam2().toString().replaceAll("_", " ") + " play " + dateFormat.format(games.getDate());
					}
		} 
		
		JList list = new JList();
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
		
		JButton btnNewButton_1 = new JButton("Delete game");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(gameInList.get(list.getSelectedIndex()) != null) {
					
					main.gameManager.removeGame(gameInList.get(list.getSelectedIndex()));
					
					SQL.query("DELETE * FROM user_bet_data WHERE game_id= '" + gameInList.get(list.getSelectedIndex()).getName() + "'");
					main.gameManager = new gameManager();
					
					dispose();
					
					main(null);
					
				}
				
			}
		});
		btnNewButton_1.setBounds(410, 391, 339, 23);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Close Window");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				window.main(null);
			}
		});
		btnNewButton_2.setBounds(10, 501, 783, 23);
		panel.add(btnNewButton_2);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(-1), null, new Integer(1)));
		spinner.setBounds(410, 157, 30, 20);
		panel.add(spinner);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(new Integer(0), new Integer(-1), null, new Integer(1)));
		spinner_1.setBounds(472, 157, 30, 20);
		panel.add(spinner_1);
		
		JLabel lblNewLabel_2 = new JLabel(":");
		lblNewLabel_2.setBounds(450, 160, 46, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Set game result (-1 = no result)");
		lblNewLabel_3.setBounds(410, 106, 339, 14);
		panel.add(lblNewLabel_3);
		
		JButton btnNewButton_3 = new JButton("Send");
		btnNewButton_3.setBounds(531, 156, 218, 23);
		panel.add(btnNewButton_3);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("");
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setBounds(410, 127, 21, 23);
		panel.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("");
		rdbtnNewRadioButton_1.setBounds(475, 127, 109, 23);
		panel.add(rdbtnNewRadioButton_1);
		
		JLabel lblNewLabel_4 = new JLabel("Set my Balance");
		lblNewLabel_4.setBounds(0, 440, 793, 14);
		panel.add(lblNewLabel_4);
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setModel(new SpinnerNumberModel(main.userManager.getMoney(), new Integer(0), null, new Integer(1)));
		spinner_2.setBounds(0, 465, 99, 20);
		panel.add(spinner_2);
		
		JButton btnNewButton_4 = new JButton("Send");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				main.userManager.setMoney((Integer)spinner_2.getValue());
				
				dispose();
				
				JOptionPane.showMessageDialog(null,"you have changed your credit!");
				
				main(null);
				
			}
		});
		btnNewButton_4.setBounds(128, 465, 116, 23);
		panel.add(btnNewButton_4);
		
		rdbtnNewRadioButton.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(rdbtnNewRadioButton.isSelected()) {
					rdbtnNewRadioButton_1.setSelected(false);
				}else {
					rdbtnNewRadioButton_1.setSelected(true);
				}
			}
		});
		rdbtnNewRadioButton_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(rdbtnNewRadioButton_1.isSelected()) {
					rdbtnNewRadioButton.setSelected(false);
				}else {
					rdbtnNewRadioButton.setSelected(true);
				}
			}
		});
		
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(gameInList.get(list.getSelectedIndex()) != null) {
					
					game g = gameInList.get(list.getSelectedIndex());
					
					g.setResult1((Integer)spinner.getValue());
					g.setResult2((Integer)spinner_1.getValue());
					
					if(rdbtnNewRadioButton.isSelected()) {
						g.setWinner(g.getTeam1());
					}else {
						g.setWinner(g.getTeam2());
					}
					main.gameManager.addGame(g);
				
					main.gameManager = new gameManager();
					
					dispose();
				
					JOptionPane.showMessageDialog(null,"result successfully set!");
					
					main(null);
					
				}
				
			}
		});
		
	}
}
