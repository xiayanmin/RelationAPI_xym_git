package com.xym.winform;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.xym.enumAndsome.getMenuAndItems;

public class mainFrame extends JFrame{

	final JLabel backLabel = new JLabel("测试");// 创建一个标签组件对象
	//final JLabel southLabel = new JLabel();// 创建一个zhuangtai标签组件对象
	JPanel south=new JPanel();//状态栏面板
	JLabel stateLabel=new JLabel();//窗体信息
	JLabel czyStatrLabel=new JLabel();//登录用户名
	JLabel nowDateLabel=new JLabel();//当前日期
	JLabel nameLabel=new JLabel();//版权
	private  String level;//等级
	
	
	public mainFrame(String user ,String strlev) {
		super();
		setTitle("~标签关联规则挖掘系统~");
		setBounds(100, 100, 700, 600);
		setLocationRelativeTo(null);//设置窗口位置
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 创建一个桌面面板对象
		final JDesktopPane desktopPane = new JDesktopPane();
		//
		
		//创建状态栏
		stateLabel.setText("@标签关联规则挖掘");
		nameLabel.setText("制作人:");
		south.setLayout(new GridLayout(1,4));
		south.add(stateLabel);
		south.add(czyStatrLabel);
		south.add(nowDateLabel);
		south.add(nameLabel);
		
		//获取日期
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");//可以方便地修改日期格式
		String hehe = dateFormat.format( now ); 
		nowDateLabel.setText("当前日期："+hehe);
		czyStatrLabel.setText("当前用户："+user);
		
		
		desktopPane.setBackground(new Color(189,252,201));
		getContentPane().add(desktopPane, BorderLayout.CENTER);
		getContentPane().add(south,BorderLayout.SOUTH);
		
		// 将标签组件添加到指定索引位置
		desktopPane.add(backLabel, new Integer(Integer.MIN_VALUE));
		//添加自定义的菜单栏
		menuBar bar=new menuBar(desktopPane,backLabel,getMenuAndItems.getMenuName(),getMenuAndItems.getMenuItemsName(),this);
		this.setJMenuBar(bar);
		
		//
		URL resource = this.getClass().getResource("/icon.png"); 
		ImageIcon icon = new ImageIcon(resource); // 创建图片对象
		setIconImage(icon.getImage()); // 设置窗体图标
		
	}

	
}
