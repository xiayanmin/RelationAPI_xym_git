package com.xym.winform.interPanel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.xym.enumAndsome.getMenuAndItems;
import com.xym.enumAndsome.myEnum.excelType;
import com.xym.tools.CallToWrite;
import com.xym.tools.littletools;
import com.xym.tools.myReadExcel;
import com.xym.tools.myWriteExcel;
import com.xym.tools.R_mashup.R_mashup_6_speedup;

/**
 * @author admin
 *制作输出mashup关联规则的面板
 */
public class Panel_13 implements CallToWrite{

	private JPanel myPanel=new JPanel();
	private JFrame frame;//传进来的用于做主容器的
	private Panel_textAndbutton p_api=new Panel_textAndbutton("api地址:",this.frame,1);//选择使用api的部分
	private Panel_textAndbutton p_mashup=new Panel_textAndbutton("mashup地址:",this.frame,1);//选择使用mashup的部分
	private Panel_textAndbutton p_Ralation_Save=new Panel_textAndbutton("输出路径", this.frame, 2);//选择导出的位置与名称
	private JButton b_Start=new JButton("输出关联规则");//开始按钮
	private JLabel backLabel=new JLabel("。。。");//处理状态
	private JCheckBox checkbox=new JCheckBox("是否输出未筛选结果");
	
	
	private R_mashup_6_speedup r_mashup,r_mashup_1;
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
	public Panel_13(JFrame frame){
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
		p1.add(checkbox);
		p1.add(b_Start);
		
		this.myPanel.setLayout(new GridLayout(5, 1));
		this.myPanel.add(p_api);
		this.myPanel.add(p_mashup);
		this.myPanel.add(p_Ralation_Save);
		this.myPanel.add(p1);
		this.myPanel.add(backLabel);
		
	}
	
	/**
	 * 调用获得mashup的关联规则的方法，并选择导出
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
		//r_mashup是开启筛选的，下r_mashup_1是开启不筛选的方法，可以注释掉
		r_mashup=new R_mashup_6_speedup(readE.mashupList, readE.apiList,this);
		r_mashup.OpenWithSX();
		if(checkbox.isSelected()){
			r_mashup_1=new R_mashup_6_speedup(readE.mashupList, readE.apiList,this);
			r_mashup_1.OpenWithNoSX();
		}
	
	}

	@Override
	public void StartWrite() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		boolean isok= myWriteExcel.write_11(r_mashup.getRuseltList(),  savepath, savename, getMenuAndItems.getTitles_11());
		if(isok){
			str=str+"完成:"+savename+"! ";
		}else{
			str="失败,可能有相同名称文件被占用！";
		}
		
		
		this.backLabel.setText(str);				
	}

	@Override
	public void StartWrite(int type) {
		// TODO Auto-generated method stub
		if(type == 1){
			boolean isok= myWriteExcel.write_11(r_mashup_1.getRuseltList(),  savepath, savename+"_NoSX", getMenuAndItems.getTitles_11());
			if(isok){
				str=str+"完成:"+savename+"_NoSX"+"! ";
			}else{
				str="失败,可能有相同名称文件被占用！";
			}
			
			
			this.backLabel.setText(str);		
		}
	}
	
}
