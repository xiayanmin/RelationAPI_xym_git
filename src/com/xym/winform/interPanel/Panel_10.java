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
import com.xym.tools.CallToWrite;
import com.xym.tools.littletools;
import com.xym.tools.myReadExcel;
import com.xym.tools.R_api_api.R_aa_6;

/**
 * @author admin
 *制作获取标签数的面板
 */
public class Panel_10 implements CallToWrite {

	private JPanel myPanel=new JPanel();
	private JFrame frame;//传进来的用于做主容器的
	private Panel_textAndbutton p_api=new Panel_textAndbutton("api地址:",this.frame,1);//选择使用api的部分
	private Panel_textAndbutton p_mashup=new Panel_textAndbutton("mashup地址:",this.frame,1);//选择使用mashup的部分
	private Panel_textAndbutton save_p=new Panel_textAndbutton("输出结果地址:",this.frame,2);//选择使用mashup的部分
	private JButton b_Start=new JButton("获取标签数");//开始按钮
	private JLabel backLabel=new JLabel("无状态");//处理状态
	
	private R_aa_6 r_aa;
	private String str="";
	private String apipath;
	private String apiname;
	private String mashuppath;
	private String mashupname;
	private	String savepath;
	private String savename;
	
	public Panel_10(JFrame frame){
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
		this.myPanel.add(p_api);
		this.myPanel.add(p_mashup);
		this.myPanel.add(save_p);
		this.myPanel.add(p1);
		this.myPanel.add(backLabel);
		
	}
	
	
	/**
	 * 调用获取标签对的方法
	 * 并显示在子窗体上
	 * @throws IOException 
	 */
	private void myGetBQD() throws IOException{
		apipath=littletools.getnewFilePath(p_api.getEditText().trim());
		apiname=littletools.getnewFileName(p_api.getEditText().trim());
		mashuppath=littletools.getnewFilePath(p_mashup.getEditText().trim());
		mashupname=littletools.getnewFileName(p_mashup.getEditText().trim());
		savepath=littletools.getnewFilePath(save_p.getEditText().trim());
		savename=littletools.getnewFileName(save_p.getEditText().trim());
		
		myReadExcel readE=new myReadExcel();
		readE.readExcel(new File(apipath+apiname), excelType.apiType);
		readE.readExcel(new File(mashuppath+mashupname), excelType.mashupType);
		
		r_aa=new R_aa_6(readE.mashupList, readE.apiList,this);	
		

		

	}

	@Override
	public void StartWrite()  {
		// TODO Auto-generated method stub

		str="AA标签对数目:筛选前("+(int)r_aa.getAllcount_before()+"),"+"筛选后("+(int)r_aa.getAllcount_after()+")";
		
		try {
			littletools.writeTxt(savepath, savename, str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.backLabel.setText(str);
	}

	@Override
	public void StartWrite(int type) {
		// TODO Auto-generated method stub
		
	}
}
