package de.em2020.window;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class signin {

	private JFrame frmSignin;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					signin window = new signin();
					window.frmSignin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public signin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSignin = new JFrame();
		frmSignin.setResizable(false);
		frmSignin.setTitle("SignIn");
		frmSignin.setBounds(100, 100, 384, 358);
		frmSignin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSignin.getContentPane().setLayout(null);
		
		usernameField = new JTextField();
		usernameField.setBounds(41, 45, 284, 20);
		frmSignin.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		JButton btnNewButton = new JButton("SignIn");
		btnNewButton.setBounds(130, 272, 89, 23);
		frmSignin.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(41, 11, 178, 23);
		frmSignin.getContentPane().add(lblNewLabel);
		
		JLabel lblPaddword = new JLabel("Password");
		lblPaddword.setBounds(41, 76, 178, 23);
		frmSignin.getContentPane().add(lblPaddword);
		
		passwordField = new JPasswordField();
		passwordField.setToolTipText("");
		passwordField.setBounds(41, 110, 284, 20);
		frmSignin.getContentPane().add(passwordField);
		
		JLabel lblTet = new JLabel("");
		lblTet.setForeground(Color.RED);
		lblTet.setBounds(41, 238, 284, 33);
		frmSignin.getContentPane().add(lblTet);
		
		JLabel lblPaddword_1 = new JLabel("repeat Password");
		lblPaddword_1.setBounds(41, 141, 178, 23);
		frmSignin.getContentPane().add(lblPaddword_1);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setToolTipText("");
		passwordField_1.setBounds(41, 175, 284, 20);
		frmSignin.getContentPane().add(passwordField_1);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("I am older than 18");
		chckbxNewCheckBox.setBounds(41, 215, 284, 23);
		frmSignin.getContentPane().add(chckbxNewCheckBox);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(1);
				if(!(usernameField.getText().isEmpty()) || !(passwordField.getText().isEmpty()) || !(passwordField_1.getText().isEmpty())) {
					
					if(chckbxNewCheckBox.isSelected()) {
						
						String username = usernameField.getText();
						
						if(passwordField.getText().equals(passwordField_1.getText())) {
							
							String password = de.em2020.main.getSHA256(passwordField.getText());
							
							if(!de.em2020.main.user_data.containsPlayer(username)) {
								
								de.em2020.main.user_data.createPlayer(username).addValue(password).addValue(20).addValue("").send();
								
								frmSignin.dispose();
								
								JOptionPane.showMessageDialog(null,"successfully registered");
								
								login.main(null);
							}else {
								lblTet.setText("this user already exists!");
							}
							
						}else {
							lblTet.setText("the passwords do not match!");
						}
						
					}else {
						lblTet.setText("please confirm that you are over 18!");
					}
					
				}else {
					lblTet.setText("Please fill in username and password!");
				}
				
			}
		});
	}
}
