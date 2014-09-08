package com.userinterface;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * This represents user interface for entering parameters.
 * It has two buttons, Exit and Submit. 
 * @author Qi Hu
 * @see com.actionlistener.exitAction
 * @see com.actionlistener.submitAction
 * @see com.dialog.LoginDatabaseEmpty
 * @see com.dialog.LoginDatabaseError
 * @see com.dialog.SetParameterError
 */
@SuppressWarnings("serial")
public class InputWindow extends JFrame {
	public JLabel database, passwordLabel, port, user, k, minsup, mincon;
	public JTextField userText, portText, databaseText, kText, supText, conText; 
	public JPasswordField passwordText; 
	public JButton exitButton,submitButton;
	
	public InputWindow() {
		super("Input"); 
		setSize(450,500); 
		setLocationRelativeTo(null);
		
		try 
		{ 
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 

		}catch(Exception e) {} 
		Container container = getContentPane(); 
		container.setBackground(Color.WHITE); 
		container.setLayout(null); 
		database = new JLabel("Please Input Database:",JLabel.RIGHT);
		port = new JLabel("Please Input IP and Port:",JLabel.RIGHT);
		passwordLabel = new JLabel("Password:",JLabel.RIGHT);
		user = new JLabel("Please Input Username:",JLabel.RIGHT);
		userText = new JTextField("root",30);
		userText.setForeground(Color.gray);;
		userText.addMouseListener( new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent e){
				userText.setText("");
				userText.setForeground(Color.black);
			}
		});
		portText = new JTextField("localhost:3306",30);
		databaseText = new JTextField(30);
		passwordText = new JPasswordField(10);
		k = new JLabel("Please Specify k for k-mean:",JLabel.CENTER);
		minsup = new JLabel("Please Specify Minimum Support:",JLabel.CENTER);
		mincon = new JLabel("Please Specify Minimum Confidence:",JLabel.LEFT);
		kText = new JTextField("1~20, must be integer",30);
		kText.setForeground(Color.gray);
		kText.addMouseListener( new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent e){
				kText.setText("");
				kText.setForeground(Color.black);
			}
		});
		supText = new JTextField("From 0.0 to 1.0",30);
		supText.setForeground(Color.gray);
		supText.addMouseListener( new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent e){
				supText.setText("");
				supText.setForeground(Color.black);
			}
		});
		conText = new JTextField("From 0.0 to 1.0",30);
		conText.setForeground(Color.gray);
		conText.addMouseListener( new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent e){
				conText.setText("");
				conText.setForeground(Color.black);
			}
		});
		exitButton = new JButton("Exit"); 
		submitButton = new JButton("Submit"); 
		database.setBounds(30,40,150,25); 
		port.setBounds(30,90,150,25);
		user.setBounds(30,140,150,25);
		passwordLabel.setBounds(30,190,150,25); 
		k.setBounds(30,240,200,25);
		minsup.setBounds(30,290,200,25);
		mincon.setBounds(30,340,230,25);
		userText.setBounds(200,140,150,25);
		portText.setBounds(200,90,150,25);
		databaseText.setBounds(200,40,150,25);
		passwordText.setBounds(200,190,150,25);
		kText.setBounds(250,240,150,25);
		supText.setBounds(250,290,150,25); 
		conText.setBounds(250,340,150,25);
		exitButton.setBounds(75,400,80,20); 
		submitButton.setBounds(225,400,80,20);
		container.add(database); 
		container.add(port);
		container.add(passwordLabel); 
		container.add(passwordText); 
		container.add(portText);
		container.add(databaseText);
		container.add(user);
		container.add(userText);
		container.add(k);
		container.add(minsup); 
		container.add(mincon); 
		container.add(kText);
		container.add(supText);
		container.add(conText);
		
		container.add(exitButton); 
		container.add(submitButton); 
		setVisible(true); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
	}
	
	public void dispose(){
		super.dispose();
	}
}
