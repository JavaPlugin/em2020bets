package de.em2020.window;

import java.awt.Choice;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.em2020.main;
import de.em2020.manager.bets;
import de.em2020.manager.game;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Window.Type;

public class bet {

	private JFrame frmTitle;
	private game game;

	public bet(game game) {
		this.game = game;
		initialize();
		frmTitle.setVisible(true);

	}

	private void initialize() {
		frmTitle = new JFrame();
		frmTitle.setResizable(false);
		frmTitle.setTitle(game.getTeam1() + " " + game.getTeam2() + " " + game.getGame_time());
		frmTitle.setBounds(100, 100, 250, 323);
		frmTitle.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmTitle.getContentPane().setLayout(null);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton(game.getTeam1().toString());
		rdbtnNewRadioButton.setSelected(true);

		JRadioButton rdbtnTeam = new JRadioButton(game.getTeam2().toString());
		rdbtnTeam.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(rdbtnTeam.isSelected()) {
					rdbtnNewRadioButton.setSelected(false);
				}else {
					rdbtnNewRadioButton.setSelected(true);
				}
			}
		});
		rdbtnNewRadioButton.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(rdbtnNewRadioButton.isSelected()) {
					rdbtnTeam.setSelected(false);
				}else {
					rdbtnTeam.setSelected(true);
				}
			}
		});
		rdbtnTeam.setBounds(123, 41, 109, 23);
		frmTitle.getContentPane().add(rdbtnTeam);
		rdbtnNewRadioButton.setBounds(6, 41, 109, 23);
		frmTitle.getContentPane().add(rdbtnNewRadioButton);
		
		JLabel lblNewLabel_1 = new JLabel("which team will win?");
		lblNewLabel_1.setBounds(10, 11, 199, 23);
		frmTitle.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("how will they play?");
		lblNewLabel_1_1.setBounds(6, 71, 109, 23);
		frmTitle.getContentPane().add(lblNewLabel_1_1);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
			}
		});
		spinner.setBounds(27, 105, 50, 20);
		frmTitle.getContentPane().add(spinner);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		spinner_1.setBounds(128, 105, 50, 20);
		frmTitle.getContentPane().add(spinner_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("your game imput!");
		lblNewLabel_1_1_1.setBounds(6, 136, 109, 23);
		frmTitle.getContentPane().add(lblNewLabel_1_1_1);
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setModel(new SpinnerNumberModel(1, 1, main.userManager.getMoney().intValue(), 1));
		spinner_2.setBounds(27, 170, 50, 20);
		frmTitle.getContentPane().add(spinner_2);
		
		
		JButton btnNewButton = new JButton("Send your bet");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				bets bets;
				
				if(rdbtnNewRadioButton.isSelected()) {
					bets = new bets(game, game.getTeam1(), Integer.parseInt(spinner.getValue().toString()), Integer.parseInt(spinner_1.getValue().toString()), Integer.parseInt(spinner_2.getValue().toString()),false);
				}else {
					bets = new bets(game, game.getTeam2(), Integer.parseInt(spinner.getValue().toString()), Integer.parseInt(spinner_1.getValue().toString()), Integer.parseInt(spinner_2.getValue().toString()),false);
				}
				
				if(game.isPlaying()) {
					frmTitle.dispose();
					JOptionPane.showMessageDialog(null,"the game alreasy startet!");
					window.main(null);
				}else {
					frmTitle.dispose();
					main.userManager.addUserBet(bets);
					main.userManager.removeMoney(bets.getAmount());
					JOptionPane.showMessageDialog(null,"bet successfully submitted!");
					window.main(null);
				}
				
			}
		});
		btnNewButton.setBounds(27, 250, 170, 23);
		frmTitle.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("\u20AC");
		lblNewLabel.setBounds(87, 173, 46, 14);
		frmTitle.getContentPane().add(lblNewLabel);
		
		
	}
}
