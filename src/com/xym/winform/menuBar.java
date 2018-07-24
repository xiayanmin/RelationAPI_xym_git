package com.xym.winform;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author admin
 *放置主菜单
 *
 */
public class menuBar extends JMenuBar{
	private List<JMenu> jmymenu=null;//主菜单的集合，	/预处理菜单/分步菜单/一步处理，快捷处理/帮助菜单
	private List<List<JMenuItem>> jmymenuitems=null;//子菜单的集合
	private List<InternalFrame> jmyframes = null;//子窗体的集合，对应每个子菜单
	
	private JDesktopPane desktoppanel;//面板容器
	private JLabel stateLabel;//一个状态栏
	private JFrame frame;//主窗体
	
	/**
	 * @param desktoppanel 传过来的面板容器
	 * @param label 一个状态栏
	 * @param mymenuItems 传过来的子菜单的选项的名称
	 */
	public menuBar(JDesktopPane desktoppanel,JLabel label,List<String> jmenu_names,List<List<String>> mymenuItems ,JFrame frame){
		super();
		this.setSize(new Dimension(20,24));
		this.desktoppanel=desktoppanel;
		this.stateLabel=label;
		this.frame=frame;
		this.jmymenuitems=myinitMenuItems(mymenuItems);
		this.jmyframes=myinitInterFrame();
		this.jmymenu=myinitMenu(jmenu_names);	
		
		myinit();
	}
	
	
	/**
	 * 完成菜单的添加
	 */
	private void myinit(){
		
		for(int i=0;i<this.jmymenu.size();i++){
			this.add(this.jmymenu.get(i));
		}
	}
	
	/**
	 * 初始化主菜单
	 * 初始化每个子菜单的事件
	 */
	private List<JMenu> myinitMenu(List<String> jmenu_names){
		List<JMenu> menu=new ArrayList<>();
		
		JMenu imenu;
		for(int i=0;i<jmenu_names.size();i++){
			
			imenu=new JMenu();
			imenu.setText(jmenu_names.get(i));
			menu.add(imenu);
		}
		
		
		for(int i=0;i<menu.size();i++){
			
			for(int j=0;j<this.jmymenuitems.get(i).size();j++){

				jmymenuitems.get(i).get(j).addActionListener(
						new ItemListener(this.jmyframes.get(i) ,this.jmymenuitems.get(i).get(j).getText(),(i*10+j),this.frame));
				menu.get(i).add(jmymenuitems.get(i).get(j));
			}
		}
		return menu;
	}
	
	/**
	 * @param mymenuItems
	 * @return
	 * 初始化子菜单，由子菜单的名称确定
	 */
	private List<List<JMenuItem>> myinitMenuItems(List<List<String>> mymenuItems){
		List<List<JMenuItem>> iMenuItem=new ArrayList<>();
		List<JMenuItem> jMenuItem;
		
		for(int i=0;i<mymenuItems.size();i++){
			
			jMenuItem=new ArrayList<>();
			for(int j=0;j<mymenuItems.get(i).size();j++){
				JMenuItem item=new JMenuItem(mymenuItems.get(i).get(j));
				
				jMenuItem.add(item);
			}
			
			iMenuItem.add(jMenuItem);
		}
		
		return iMenuItem;
	}
	
	/**
	 * @return
	 * 初始化子窗体
	 * 
	 */
	private List<InternalFrame> myinitInterFrame(){
		List<InternalFrame> interF_map=new ArrayList<>();
		
		InternalFrame inframe;
		for(int i=0;i<this.jmymenuitems.size();i++){
			inframe=null;
			interF_map.add(inframe);
		}
		
		return interF_map;
	}
	
	private class ItemListener implements ActionListener{
		InternalFrame inFrame;
		String title;
		int type=0;
		JFrame frame;
		public ItemListener(InternalFrame inFrame, String title,int type,JFrame frame) {
			this.inFrame = inFrame;
		    this.title = title;
		    this.type=type;
		    this.frame=frame;
		}
		public void actionPerformed(ActionEvent e){
			
			if (inFrame == null || inFrame.isClosed()) {
				// 获得桌面面板中的所有内部窗体
				JInternalFrame[] allFrames =desktoppanel.getAllFrames();
				// 获得桌面面板中拥有内部窗体的数量
				int titleBarHight = 30 * allFrames.length;
				int x = 10 + titleBarHight, y = x;// 设置窗体的显示位置
				int width = 350, height = 170;// 设置窗体的大小
				inFrame = new InternalFrame(title,inFrame,this.frame);// 创建指定标题的内部窗体
				
				switch(type){		//分类处理子窗体的功能	
				case 0://预处理的
					 height = 240;
					inFrame.setInter_0();
					break;
				case 10://1开头的是分步处理的，这是分布处理的第一个
					height = 200;
					inFrame.setInter_10();
					break;
				case 11:
					height = 200;
					inFrame.setInter_11();
					break;
				case 12:
					height=180;
					inFrame.setInter_12();
					break;
				case 13:
					height=170;
					inFrame.setInter_13();
					break;
				case 14:
					height=200;
					inFrame.setInter_14();
					break;
				case 15:
					height=200;
					inFrame.setInter_15();
					break;
				case 20:
					//height=200;
					//inFrame.setInter_20();
					break;
				case 30:
					
					break;
				
				
				}
				// 设置窗体的显示位置及大小
				inFrame.setBounds(x, y, width, height);
				inFrame.setVisible(true);// 设置窗体可见
				desktoppanel.add(inFrame);// 将窗体添加到桌面面板中
			}
			try {
				inFrame.setSelected(true);// 选中窗体
			} catch (PropertyVetoException propertyVetoE) {
				propertyVetoE.printStackTrace();
			}
			System.out.print("测试");
		}
	}
	
}
