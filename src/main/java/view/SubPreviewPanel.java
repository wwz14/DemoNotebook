package view;

import common.MessageType;
import common.ObMessage;
import common.Observable;
import common.Observer;
import common.Page;
import common.Position;
import type.StyleType;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

/**
 * Created by 徐江河 on 2016/12/24.
 */
class SubPreviewPanel extends JPanel implements MouseListener,Observable{

    private Page page;
    private int id;
    private int width;
    private int height;
    private PreviewPanel parent;
    private Set<Observer> observers = new HashSet<Observer>();

	public SubPreviewPanel(Page p,PreviewPanel previewPanel)  {
		super();
		    this.parent = previewPanel;
	        this.id =  p.getNumber();
	        this.page = p;
	      //获得屏幕大小
	    	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	    	width = (int)screensize.getWidth();
	    	height = (int)screensize.getHeight();
	        /**设置场地容器的大小*/  
	        this.setSize(120, 110);  
	        this.setPreferredSize(new Dimension(120,110));  
	          this.setBackground(Color.white);
	        /**设置场地容器的布局*/  
	          this.setLayout(null);
	        //this.setLayout(new BorderLayout());  
	          //this.setLayout(BorderLayout.NORTH);
	        /**创建场地容器的内层容器*/  
	        SmallDrawPanel inJPanel = new SmallDrawPanel(p.getView()); 
	        inJPanel.setLayout(null);  
	        inJPanel.setSize((int)width * 75 / 1000, (int)height * 99 / 1000);  
	        inJPanel.setBackground(Color.white);
	        inJPanel.setLocation(this.getWidth()/2 - inJPanel.getSize().width/2,   
	                this.getHeight()/2 - inJPanel.getSize().height/2);  
	        inJPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));  
	          
	       // this.add(inJPanel,BorderLayout.NORTH);  
	        this.add(inJPanel);
	        addMouseListener(this); 
	        addObserver(parent);//添加previewPanel为观察者
	        
	}

	public void mouseClicked(MouseEvent e) {
		ObMessage thepage = new ObMessage(MessageType.PAGE_REPLACE,page);
 	     notifyObservers(thepage);
		
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

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void notifyObservers(ObMessage arg) {
		  for (Observer obs : observers) {
	            obs.update(this,arg);
	        }	
	}

	public void addObserver(Observer observer) {
		
		observers.add(observer);
	}

	public void removeObserver(Observer observer) {
		// TODO Auto-generated method stub
		
	}
    
	//画线
	

}
