package view;

import common.Page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

/**
 * Created by 徐江河 on 2016/12/24.
 */
class SubPreviewPanel extends JPanel implements MouseListener {

    private Page page;

	public SubPreviewPanel()  {
		super();
	        
	        /**设置场地容器的大小*/  
	        this.setSize( 120,110);  
	        this.setPreferredSize(new Dimension(120,110));  
	          this.setBackground(Color.white);
	        /**设置场地容器的布局*/  
	          this.setLayout(null);
	        //this.setLayout(new BorderLayout());  
	          //this.setLayout(BorderLayout.NORTH);
	        /**创建场地容器的内层容器*/  
	        JPanel inJPanel = new JPanel();  
	        inJPanel.setLayout(null);  
	        inJPanel.setSize(150, 110);  
	        inJPanel.setBackground(Color.white);
	        inJPanel.setLocation(this.getWidth()/2 - inJPanel.getSize().width/2,   
	                this.getHeight()/2 - inJPanel.getSize().height/2);  
	        inJPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));  
	          
	       // this.add(inJPanel,BorderLayout.NORTH);  
	        this.add(inJPanel);
	        addMouseListener(this); 
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
    

}
