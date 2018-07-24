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
import com.xym.tools.CallToWrite;
import com.xym.tools.littletools;
import com.xym.tools.myReadExcel;
import com.xym.tools.myWriteExcel;
import com.xym.tools.R_api_api.R_aa_6;

/**
 * @author admin
 *制作输出api与api之间关联规则的面板
 */

public class Panel_11 implements CallToWrite {
	
	private JPanel myPanel=new JPanel();
	private JFrame frame;//传进来的用于做主容器的
	private Panel_textAndbutton p_api=new Panel_textAndbutton("api地址:",this.frame,1);//选择使用api的部分
	private Panel_textAndbutton p_mashup=new Panel_textAndbutton("mashup地址:",this.frame,1);//选择使用mashup的部分
	private Panel_textAndbutton p_Ralation_Save=new Panel_textAndbutton("输出路径", this.frame, 2);//选择导出的位置与名称
	private JButton b_Start=new JButton("输出关联规则");//开始按钮
	private JLabel backLabel=new JLabel("无状态");//处理状态
	
	
	private R_aa_6 r_aa;
	private String str="";
	private String apipath;
	private String apiname;
	private String mashuppath;
	private String mashupname;
	private	String savepath;
	private String savename;
	
	/**
	 * @param frame
	 * 完成初始化
	 */
	public Panel_11(JFrame frame){
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
						myOutput();
					}
					
				}).start();
			}
		});
		JPanel p1=new JPanel();
		p1.add(b_Start);
		
		this.myPanel.setLayout(new GridLayout(5, 1));
		this.myPanel.add(p_api);
		this.myPanel.add(p_mashup);
		this.myPanel.add(p_Ralation_Save);
		this.myPanel.add(p1);
		this.myPanel.add(backLabel);
		
	}
	
	/**
	 * 调用获得api的关联规则的方法，并选择导出
	 */
	private void myOutput(){
		apipath=littletools.getnewFilePath(p_api.getEditText().trim());
		apiname=littletools.getnewFileName(p_api.getEditText().trim());
		mashuppath=littletools.getnewFilePath(p_mashup.getEditText().trim());
		mashupname=littletools.getnewFileName(p_mashup.getEditText().trim());
		savepath=littletools.getnewFilePath(p_Ralation_Save.getEditText().trim());
		savename=littletools.getnewFileName(p_Ralation_Save.getEditText().trim());
		System.out.println(savepath+","+savename);
		
		myReadExcel readE=new myReadExcel();
		readE.readExcel(new File(apipath+apiname), excelType.apiType);
		readE.readExcel(new File(mashuppath+mashupname), excelType.mashupType);
		
		r_aa=new R_aa_6(readE.mashupList, readE.apiList,this);

		
	}


	@Override
	public void StartWrite() {
		// TODO Auto-generated method stub
		boolean isok= myWriteExcel.write_11(r_aa.getNewlist(),  savepath, savename, getMenuAndItems.getTitles_11());
		if(isok){
			str="完成";
		}else{
			str="失败,可能有相同名称文件被占用！";
		}
		
		
		this.backLabel.setText(str);
	}


	@Override
	public void StartWrite(int type) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
