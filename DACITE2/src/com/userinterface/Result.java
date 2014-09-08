package com.userinterface;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 * This represents user interface for choosing specific table and attribute.
 * It has two buttons, Exit and Search Rules
 * @author Qi Hu
 * @see com.actionlistener.searchAction
 * @see com.itemlistener.TableNameSelected
 */
@SuppressWarnings("serial")
public class Result extends JFrame {
	public JLabel Tablename, Attributename;
	public Choice table;
	public Choice attribute;
	public JButton exitButton,searchButton;
	
	public Result() {
		super("Result");
		setSize(450,250); 
		setLocationRelativeTo(null);
		
		try 
		{ 
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		}catch(Exception e) {} 
		
		Container container = getContentPane(); 
		container.setBackground(Color.WHITE); 
		container.setLayout(null); 
		
		Tablename = new JLabel("Please Select Table:",JLabel.CENTER); 
		Attributename = new JLabel("Please Selet Attribute:", JLabel.CENTER);
		Tablename.setBounds(30,40,200,25); 
		Attributename.setBounds(30,90,200,25);
		table = new Choice();
		table.setBounds(250, 40, 150, 25);
		attribute = new Choice();
		attribute.setBounds(250, 90, 150, 25);
		exitButton = new JButton("Exit"); 
		searchButton = new JButton("Search Rules");
		exitButton.setBounds(100,175,80,20); 
		searchButton.setBounds(250,175,120,20);
		
		container.add(Tablename);
		container.add(Attributename);
		container.add(table);
		container.add(attribute);
		container.add(exitButton); 
		container.add(searchButton);
		setVisible(false); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
	}
	
	public void dispose(){
		super.dispose();
	}
}
