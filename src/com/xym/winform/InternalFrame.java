package com.xym.winform;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.xym.enumAndsome.interface_interFrame_Set;
import com.xym.winform.interPanel.Panel_0;
import com.xym.winform.interPanel.Panel_10;
import com.xym.winform.interPanel.Panel_11;
import com.xym.winform.interPanel.Panel_12;
import com.xym.winform.interPanel.Panel_13;
import com.xym.winform.interPanel.Panel_14;
import com.xym.winform.interPanel.Panel_15;
import com.xym.winform.interPanel.Panel_20;


public class InternalFrame extends JInternalFrame implements interface_interFrame_Set{

	
	private String title;//标题
	private InternalFrame inFrame;//内部窗体
	private JFrame frame;//主窗体
	
	/**
	 * @param title
	 * @param inFrame
	 * 
	 * 初始化窗体，内部涵盖子窗体与标题
	 */
	public InternalFrame(String title,InternalFrame inFrame,JFrame frame) {
		super();
		this.title=title;
		this.inFrame=inFrame;
		this.frame=frame;
		//窗体外观
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		SetStitle();	
		
	}
	/**
	 * 设置窗体的一些属性
	 */
	private void SetStitle(){
		setTitle(title);// 设置内部窗体的标题
		setResizable(false);// 设置不允许自由调整大小
		setClosable(true);// 设置提供关闭按钮
		setIconifiable(true);// 设置提供图标化按钮
		setMaximizable(false);// 设置提供最大化按钮	
		// 获得图片的路径
		//URL resource = this.getClass().getResource("/1~1.png"); 
		//ImageIcon icon = new ImageIcon(resource); // 创建图片对象
		//setFrameIcon(icon); // 设置窗体图标
	}
	
	
	/**
	 * 调用展示预处理的子窗体,对应编号0,下面类似
	 */
	@Override
	public void setInter_0(){

		Panel_0 p=new Panel_0(this.frame);
		getContentPane().add(p.getPanel(),BorderLayout.NORTH);
	}
	@Override
	public void setInter_10() {
		// TODO Auto-generated method stub
		Panel_10 p=new Panel_10(this.frame);
		getContentPane().add(p.getPanel(),BorderLayout.NORTH);
		
	}
	@Override
	public void setInter_11() {
		// TODO Auto-generated method stub
		Panel_11 p=new Panel_11(this.frame);
		getContentPane().add(p.getPanel(),BorderLayout.NORTH);
	}
	@Override
	public void setInter_12() {
		// TODO Auto-generated method stub
		Panel_12 p=new Panel_12(this.frame);
		getContentPane().add(p.getPanel(),BorderLayout.NORTH);
	}
	@Override
	public void setInter_13() {
		// TODO Auto-generated method stub
		Panel_13 p=new Panel_13(this.frame);
		getContentPane().add(p.getPanel(),BorderLayout.NORTH);
	}
	@Override
	public void setInter_14() {
		// TODO Auto-generated method stub
		Panel_14 p=new Panel_14(this.frame);
		getContentPane().add(p.getPanel(),BorderLayout.NORTH);
	}
	@Override
	public void setInter_15() {
		// TODO Auto-generated method stub
		Panel_15 p=new Panel_15(this.frame);
		getContentPane().add(p.getPanel(),BorderLayout.NORTH);
	}
	@Override
	public void setInter_20() {
		// TODO Auto-generated method stub
		Panel_20 p=new Panel_20(this.frame);
		getContentPane().add(p.getPanel(),BorderLayout.NORTH);
	}
	@Override
	public void setInter_30() {
		// TODO Auto-generated method stub
		
	}
	
	
}
