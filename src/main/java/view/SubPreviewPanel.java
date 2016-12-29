package view;

import common.Page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

/**
 * Created by 徐江河 on 2016/12/24.
 */
class SubPreviewPanel extends JPanel {

    private Page page;

	public SubPreviewPanel() {
		super();
	        
	        /**设置场地容器的大小*/  
	        this.setSize( 120,110);  
	        this.setPreferredSize(new Dimension(120,110));  
	          
	        /**设置场地容器的布局*/  
	        this.setLayout(null);  
	          
	        /**创建场地容器的内层容器*/  
	        JPanel inJPanel = new JPanel();  
	        inJPanel.setLayout(null);  
	        inJPanel.setSize(150, 110);  
	        inJPanel.setBackground(Color.green);
	        inJPanel.setLocation(this.getWidth()/2 - inJPanel.getSize().width/2,   
	                this.getHeight()/2 - inJPanel.getSize().height/2);  
	        inJPanel.setBorder(BorderFactory.createLineBorder(Color.cyan, 1));  
	          
	        this.add(inJPanel);  
	}
    

}
