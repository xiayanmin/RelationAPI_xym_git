package com.xym.winform.interPanel;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * @author admin
 *自定义一个面板，存放选择文件的必要组件
 *一个标签，一个文本框，一个按钮
 */
public class Panel_textAndbutton extends JPanel{
	
	private JLabel mylabel;//说明的标签
	private JTextField editfield;//文本框，存放地址
	private JButton selectbutton;//选择的按钮
	
	
	private JFrame myframe;//传进来的窗体，用来给choosefile提供容器
	
	/**
	 * 完成初始化，
	 */
	public Panel_textAndbutton(String labelstr,JFrame frame,int type){
		super();
		this.myframe=frame;
		this.mylabel=new JLabel(labelstr);
		this.editfield=new JTextField(15);
		this.selectbutton=new JButton("选择");
		this.setLayout(new FlowLayout());
		this.add(mylabel);
		this.add(editfield);
		this.add(selectbutton);
	
		this.selectbutton.addMouseListener(new ChooseFile(this.editfield, this.myframe,type));
	}
	
	
	public String getEditText(){
		
		return this.editfield.getText();
	}
	
	/**
	 * @return
	 * 获取使用的地址
	 */
	public String getnewFilePath(){
		String newfilepath=this.editfield.getText().trim().replace("\\", "/");
		StrTolist1(newfilepath, "/");
		
		return newfilepath;
	}
	
	/**
	 * @return
	 * 获取使用的名字
	 */
	public String getnewFileName(){
		String newfilename=this.editfield.getText().trim().replace("\\", "/");
		StrTolist1(newfilename, "/");
		
		return newfilename;
		
	}
	
	 /**
     * @param s
     * @param splitstr
     * @return
     * 这里处理带有符号的，分离要素，参数第一个字符串，第二个分离符号
     */
    private List<String> StrTolist1(String s,String splitstr){
    	String[] str=s.split(splitstr);
    	
    	List<String> liststr=new ArrayList<>();
    	for(int i=0;i<str.length;i++){
    		liststr.add(str[i]);
    	}
    	return liststr;
    }
	

}
