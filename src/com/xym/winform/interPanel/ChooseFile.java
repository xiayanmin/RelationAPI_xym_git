package com.xym.winform.interPanel;

import java.awt.FileDialog;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * @author admin
 *选择文件的地方
 *保存文件的地方
 */
public class ChooseFile extends MouseAdapter{

	private int type;//判断是打开的还是保存，1是打开，2是保存
	
	private JTextField filePathFild;//显示地址的文本框
	private JFrame frame;//传进来的主容器
	private FileDialog fileDialog;//弹出的用于保存啥的文件
	private String filePath;//文件地址
	private String fileName;//文件名称
	
	private String newfilePath;//用于实际保存的文件名称
	private String newfileName;//用于实际保存的文件名称
	
	
	
	public ChooseFile(JTextField filePathfild,JFrame frame,int type){
		this.filePathFild=filePathfild;
		this.frame=frame;
		this.type=type;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e){
		super.mouseClicked(e);
		
		switch(type){
		case 1:
			fileDialog=new FileDialog(frame,"打开",FileDialog.LOAD);
			fileDialog.show(true);
			filePath=fileDialog.getDirectory();
			fileName=fileDialog.getFile();
			
			if(filePath == null ||  fileName == null){
			}else{
				filePathFild.setText(filePath+fileName);
			}
			break;
			
		case 2:
			fileDialog=new FileDialog(frame, "保存", FileDialog.SAVE);
			fileDialog.show(true);
			filePath=fileDialog.getDirectory();
			fileName=fileDialog.getFile();
			
			if(filePath == null ||  fileName == null){
			}else{
				filePathFild.setText(filePath+fileName);
			}
			
			break;
		}
		
	}
	
}
