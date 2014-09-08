package com.dialog;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JLabel;

import com.userinterface.InputWindow;

/**
 * This represents a dialog showing inputs of database are invalid.
 * @author Qi Hu
 *
 */
@SuppressWarnings("serial")
public class LoginDatabaseError extends JDialog{
	public LoginDatabaseError (InputWindow frame){
		super(frame, "Error", true);
		setLocationRelativeTo(null);
		setBounds(500, 200, 400, 130);
		Container container = getContentPane();
		container.setBackground(Color.WHITE); 
		container.setLayout(null); 
		JLabel label = new JLabel("Sorry cannot connect to database, please check input!"
				,JLabel.LEFT);
		label.setBounds(30,30,400,25);
		container.add(label);
	}
}
