package view;

import common.Page;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

/**
 * Created by 徐江河 on 2016/12/24.
 */
class SubPreviewPanel extends JPanel {

    private Page page;

	public SubPreviewPanel() {
		super();
	        
	        /**设置场地容器的大小*/  
	        this.setSize( 160,160);  
	        this.setPreferredSize(new Dimension(160,160));  
	          
	        /**设置场地容器的布局*/  
	        this.setLayout(null);  
	          
	        /**创建场地容器的内层容器*/  
	        JPanel inJPanel = new JPanel();  
	        inJPanel.setLayout(null);  
	        inJPanel.setSize(128, 148);  
	        inJPanel.setBackground(Color.green);
	        inJPanel.setLocation(this.getWidth()/2 - inJPanel.getSize().width/2,   
	                this.getHeight()/2 - inJPanel.getSize().height/2);  
	        inJPanel.setBorder(BorderFactory.createLineBorder(Color.cyan, 1));  
	          
	        this.add(inJPanel);  
	}
    

}
