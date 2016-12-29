package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.*;
import common.*;

import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.ActionEvent;

/**
 * Created by 徐江河 on 2016/12/24.
 */
class PreviewPanel extends JPanel implements Observable, Observer {

    private MainPanel parent;
    private JButton add;
    private JPanel funcPanel;
    private JPanel backPanel;
    private JPanel pagePanel;
    private JScrollPane scrollPane;
    private Set<Observer> observers = new HashSet<Observer>();
   
    

    public PreviewPanel(MainPanel parent) {
        super();
        this.parent = parent;
        this.setBackground(Color.cyan);
        setLayout(null);
        
        final GridBagConstraints vertical = new GridBagConstraints();
        vertical.fill = GridBagConstraints.VERTICAL;
        
        pagePanel = new JPanel();
        pagePanel.setLayout(new GridBagLayout()); 
        pagePanel.setBackground(Color.RED);
        
//        for(int i = 0; i < 100; i++)  
//        {  
//            SubPreviewPanel court = new SubPreviewPanel();  
//            pagePanel.add(court);  
//        }  
        //滚动屏
        backPanel  = new JPanel();
        this.add(backPanel);
        backPanel.setBackground(Color.ORANGE);
        backPanel.setLocation(0,42);
        backPanel.setSize(200, 758);
        
        scrollPane = new JScrollPane(pagePanel); 
        backPanel.add(scrollPane);
        scrollPane.setLocation(0,42);
        scrollPane.setSize(200, 758);
        scrollPane.setVisible(true);
        //scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
      //  scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.H);
        //----------------------添加按钮-----------------------------------------------------------------------------------
        add = new JButton("新页");
        add.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		SubPreviewPanel newpage = new SubPreviewPanel();
        		 
        		 pagePanel.setLayout(new GridBagLayout()); 
        		 pagePanel.add(newpage,vertical);
        		 backPanel.removeAll();
        		 scrollPane = new JScrollPane(pagePanel);
        		 pagePanel.repaint();
        		 backPanel.add(scrollPane);
        	        scrollPane.setLocation(0,0);
        	        scrollPane.setBackground(Color.PINK);
        	        scrollPane.setSize(200, 758);
        	        scrollPane.setVisible(true);
        	        scrollPane.repaint();    	       
        	     backPanel.repaint();   
        	}
        });
        //-------------------------------------------按钮栏-------------------------------------------------------------------
        
        funcPanel = new JPanel();
        funcPanel.setLayout(null);
        funcPanel.setBackground(Color.blue);
        this.add(funcPanel);
        funcPanel.setSize(200, 42);
        funcPanel.setLocation(0,0);
        funcPanel.add(add);
        add.setSize(45, 42);
        add.setLocation(156,0);
        
        
        
      
        
       // add.addActionListener(l);     
        
    }
    
    public void notifyObservers(ObMessage arg) {
        for (Observer obs : observers) {
            obs.update(this,arg);
        }
    }

    public void update(Observable o, ObMessage arg) {
        // 处理HISTORY_UPDATE与PAGE_ALTERED
    }

	public void addObserver(Observer observer) {
		observers.add(observer);
		
	}

	public void removeObserver(Observer observer) {
		observers.remove(observer);
		
	}
}
