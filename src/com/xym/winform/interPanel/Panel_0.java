package com.xym.winform.interPanel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.xym.enumAndsome.getMenuAndItems;
import com.xym.enumAndsome.myEnum.excelType;
import com.xym.tools.dealPre;
import com.xym.tools.littletools;
import com.xym.tools.myReadExcel;
import com.xym.tools.myWriteExcel;

/**
 * @author admin
 *预处理窗体的面板
 */
public class Panel_0{

	private JPanel myPanel=new JPanel();
	
	private JFrame frame;//传进来的用于做主容器的
	private Panel_textAndbutton p_api=new Panel_textAndbutton("api地址:",this.frame,1);//选择使用api的部分
	private Panel_textAndbutton p_mashup=new Panel_textAndbutton("mashup地址:",this.frame,1);//选择使用mashup的部分
	private Panel_textAndbutton newapi_Save=new Panel_textAndbutton("新api输出路径", this.frame, 2);//选择导出的位置与名称
	private Panel_textAndbutton newmashup_Save=new Panel_textAndbutton("新mashup输出路径", this.frame, 2);//选择导出的位置与名称
	private JButton b_Start=new JButton("开始预处理");//预处理开始按钮
	private JLabel backLabel=new JLabel("无状态");//处理状态
	
	
	public Panel_0(JFrame frame){
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
						myrepeat();
					}
					
				}).start();
			}
		});
		JPanel p1=new JPanel();
		p1.add(b_Start);
		
		this.myPanel.setLayout(new GridLayout(6, 1));
		this.myPanel.add(p_api);
		this.myPanel.add(p_mashup);
		this.myPanel.add(newapi_Save);
		this.myPanel.add(newmashup_Save);
		this.myPanel.add(p1);
		this.myPanel.add(backLabel);
		
	}
	
	
	
	
	/**
	 * 调用预处理方法
	 * 并显示在子窗体上
	 */
	private void myrepeat(){
		String str="";
		
		String apipath=littletools.getnewFilePath(p_api.getEditText().trim());
		String apiname=littletools.getnewFileName(p_api.getEditText().trim());
		String mashuppath=littletools.getnewFilePath(p_mashup.getEditText().trim());
		String mashupname=littletools.getnewFileName(p_mashup.getEditText().trim());
		String newapipath=littletools.getnewFilePath(newapi_Save.getEditText().trim());
		String newapiname=littletools.getnewFileName(newapi_Save.getEditText().trim());
		String newmashuppath=littletools.getnewFilePath(newmashup_Save.getEditText().trim());
		String newmashupname=littletools.getnewFileName(newmashup_Save.getEditText().trim());
		
		myReadExcel readE=new myReadExcel();
		readE.readExcel(new File(apipath+apiname), excelType.apiType);
		readE.readExcel(new File(mashuppath+mashupname), excelType.mashupType);
		
		
		dealPre pre=new dealPre(readE.mashupList, readE.apiList);
		str=pre.dealRepeat();
		
		
		
		myWriteExcel.write_0_api(pre.getNewApi(), newapipath, newapiname, getMenuAndItems.getTitles_0_api());
		myWriteExcel.write_0_mashup(pre.getNewMahsup(), newmashuppath, newmashupname, getMenuAndItems.getTitle_0_mashup());
		
		str="预处理完成"+"\r\n";
		this.backLabel.setText(str);
	}
}
