package de.em2020.window;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JTextField;

import de.em2020.main;
import de.em2020.manager.bets;
import de.em2020.manager.userManager;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.Color;

public class login {

	private JFrame frmLogin;
	private JTextField usernameField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login window = new login();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setResizable(false);
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 384, 358);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		usernameField = new JTextField();
		usernameField.setBounds(41, 82, 284, 20);
		frmLogin.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setBounds(130, 249, 89, 23);
		frmLogin.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(41, 48, 178, 23);
		frmLogin.getContentPane().add(lblNewLabel);
		
		JLabel lblPaddword = new JLabel("Password");
		lblPaddword.setBounds(41, 143, 178, 23);
		frmLogin.getContentPane().add(lblPaddword);
		
		passwordField = new JPasswordField();
		passwordField.setToolTipText("");
		passwordField.setBounds(41, 184, 284, 20);
		frmLogin.getContentPane().add(passwordField);
		
		JLabel lblTet = new JLabel("");
		lblTet.setForeground(Color.RED);
		lblTet.setBounds(88, 215, 178, 23);
		frmLogin.getContentPane().add(lblTet);
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(usernameField.getText() != null && usernameField.getText() != "" && passwordField.getText() != null && passwordField.getText() != "") {
					try {
						String username = usernameField.getText();
						String password = main.getSHA256(passwordField.getText());
						
						if(de.em2020.main.user_data.containsPlayer(username)) {
							
							if(main.user_data.getPlayer(username).setGetTable("password").getString().equals(password)) {
								
								main.userManager = new userManager(username, password);
								frmLogin.dispose();
								JOptionPane.showMessageDialog(null,"successfully logged in");
								
								main.userManager.checkBets();
								
								window.main(null);
								
							}else {
								lblTet.setText("wrong password!");
							}
							
						}else {
							lblTet.setText("This user does not exist!");
						}
						
					} catch (Exception e2) {
						lblTet.setText("There was an error!");
						e2.printStackTrace();
					}
				}else {
					lblTet.setText("Please fill in username and password!");
				}
				

				
				
				
				
			}
		});
	}
	
}
