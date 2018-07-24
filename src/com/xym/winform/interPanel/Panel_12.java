package com.xym.winform.interPanel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.xym.enumAndsome.myEnum.excelType;
import com.xym.tools.littletools;
import com.xym.tools.myReadExcel;
import com.xym.tools.R_mashup.R_mashup_6;

/**
 * @author admin
 *制作获取mashup标签对数的面板
 */
public class Panel_12 {

	private JPanel myPanel=new JPanel();
	private JFrame frame;//传进来的用于做主容器的
	private Panel_textAndbutton p_mashup=new Panel_textAndbutton("mashup地址:",this.frame,1);//选择使用mashup的部分
	private Panel_textAndbutton save_p=new Panel_textAndbutton("输出结果地址:",this.frame,2);//选择使用mashup的部分
	private JButton b_Start=new JButton("获取标签数");//开始按钮
	private JLabel backLabel=new JLabel("无状态");//处理状态
	
	public Panel_12(JFrame frame){
		super();
		this.frame=frame;
		setPanel();
	}
	
	
	/**
	 * @return
	 * 开放的获的这个面板的方法
	 */
	public JPanel getPanel(){
		return this.myPanel;
	}
	
	/**
	 * 制作这个面板的方法入口
	 */
	private void setPanel(){

		b_Start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				backLabel.setText("处理中...");
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							myGetBQD();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}).start();
				
			}
		});
		JPanel p1=new JPanel();
		p1.add(b_Start);
		
		this.myPanel.setLayout(new GridLayout(5, 1));
		this.myPanel.add(p_mashup);
		this.myPanel.add(save_p);
		this.myPanel.add(p1);
		this.myPanel.add(backLabel);
		
	}
	
	/**
	 * 调用获取mashup标签对的方法
	 * 并显示在子窗体上
	 * @throws IOException 
	 */
	private void myGetBQD() throws IOException{
		System.out.println("mshup标签对开始处理");
		String str="";
		
		String mashuppath=littletools.getnewFilePath(p_mashup.getEditText().trim());
		String mashupname=littletools.getnewFileName(p_mashup.getEditText().trim());
		
		String savepath=littletools.getnewFilePath(save_p.getEditText().trim());
		String savename=littletools.getnewFileName(save_p.getEditText().trim());
		//System.out.println("mshup标签对读取数据开始");
		myReadExcel readE=new myReadExcel();
		readE.readExcel(new File(mashuppath+mashupname), excelType.mashupType);
		//System.out.println("mshup标签对读取数据结束");
		//System.out.println("读取的mashup的数目:"+readE.mashupList.size());
		R_mashup_6 r_mashup=new R_mashup_6(readE.mashupList);	
		

		str="Mashup标签对总数:"+r_mashup.getCount();
		
		littletools.writeTxt(savepath, savename, str);
		
		this.backLabel.setText(str);

		System.out.println("mshup标签对结束");
	}
	
	
}
