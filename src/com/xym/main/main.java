package com.xym.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.xym.winform.mainFrame;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		main m=new main();
		m.mytest();
	}

	public void mytest(){
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mainFrame m=new mainFrame(null, null);
		m.setVisible(true);
		
	}	
	
}
