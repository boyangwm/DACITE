package com.dialog;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JLabel;

import com.userinterface.InputWindow;

/**
 * This represents a dialog showing database is empty.
 * @author Qi Hu
 *
 */
@SuppressWarnings("serial")
public class LoginDatabaseEmpty extends JDialog{
	public LoginDatabaseEmpty (InputWindow frame){
		super(frame, "Warning", true);
		setLocationRelativeTo(null);
		setBounds(500, 200, 400, 130);
		Container container = getContentPane();
		container.setBackground(Color.WHITE); 
		container.setLayout(null); 
		JLabel label = new JLabel("Databse is empty, no table found!"
				,JLabel.LEFT);
		label.setBounds(30,30,400,25);
		container.add(label);
	}
}

