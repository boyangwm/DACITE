package com.userinterface;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * This represents user interface showing results.
 * @author Qi Hu
 *
 */
@SuppressWarnings("serial")
public class ResultShown extends JFrame{
	public JTextArea resultText;
	
	public ResultShown() {	
		try 
		{ 
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		}catch(Exception e) {} 
		Container container = getContentPane(); 
		container.setBackground(Color.WHITE); 
		
		resultText = new JTextArea(100,100);
		JScrollPane sp = new JScrollPane(resultText);
		container.add(sp);
		setTitle("Show Result");
		setSize(450,450);
		setVisible(false); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
	}
	
}
