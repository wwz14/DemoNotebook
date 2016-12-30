package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.text.View;

import common.*;
import common.impl.PageDefault;

import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private ArrayList<SubPreviewPanel> subPanelList = new ArrayList<SubPreviewPanel>();
    
    private int width;
    private int height;
   
    public PreviewPanel(final MainPanel parent) {
        super();
       
    	//获得屏幕大小
    	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    	width = (int)screensize.getWidth();
    	height = (int)screensize.getHeight();
  
        System.out.println("width:"+width);
        System.out.println("height:"+width);
       
        this.parent = parent;
        this.setBackground(Color.lightGray);
        setLayout(null);
        
        final GridBagConstraints vertical = new GridBagConstraints();
        vertical.fill = GridBagConstraints.VERTICAL;
        
        pagePanel = new JPanel();
       pagePanel.setLayout(new GridLayout(1,100,0,0)); 
        pagePanel.setLayout(null);
        pagePanel.setBackground(Color.lightGray);

        
//        for(int i = 0; i < 100; i++)  
//        {  
//            SubPreviewPanel court = new SubPreviewPanel();  
//            pagePanel.add(court);  
//        }  
        //滚动屏
        backPanel  = new JPanel();
        this.add(backPanel);
        backPanel.setBackground(Color.white);
        backPanel.setLocation(0,29);
        backPanel.setSize((int)width * 20 / 100, (int)height * 99 / 100-29);
        
        scrollPane = new JScrollPane(pagePanel); 
        backPanel.add(scrollPane);
        scrollPane.setBackground(Color.white);
        scrollPane.setLocation(0,0);
        scrollPane.setSize((int)width * 20 / 100, (int)height * 99 / 100-29);
        scrollPane.setVisible(true);
        //scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
      //  scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.H);
        //----------------------添加按钮-----------------------------------------------------------------------------------
        add = new JButton("添加");
        add.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		
        		ArrayList<Position> viewList = new ArrayList();
        		Page blank = new PageDefault(viewList,subPanelList.size());
        		SubPreviewPanel newpage = new SubPreviewPanel(blank);//创建新的sunpanel
        		System.out.println("页数： "+subPanelList.size());
        		newpage.setPage(blank);//设置xinpanel的页信息
        		
        		subPanelList.add(newpage);
        		pagePanel.removeAll();
//        		pagePanel.setLayout(new GridLayout(1,100,2,2));
        		
       	
        		//------　Use FlowLayout instead
        		
        		pagePanel.setLayout(new FlowLayout());
        		//------------------------------
      
        		for(SubPreviewPanel subpanel: subPanelList){
        			pagePanel.add(subpanel);
        		}
        		 backPanel.removeAll();
        		 scrollPane = new JScrollPane(pagePanel);
        		// pagePanel.repaint();
        		 backPanel.add(scrollPane);
        	        scrollPane.setLocation(0,0);
        	        scrollPane.setBackground(Color.white);
        	        scrollPane.setSize((int)width * 20 / 100, (int)height * 99 / 100-29);
        	        scrollPane.setVisible(true);
        	        scrollPane.repaint();    	       
        	     backPanel.repaint();   
        	     
        	     
        	     
        	     //------------- Refresh layout
        	     scrollPane.doLayout();
        	     // ---------------------
        	    	     
        	     //通知mainPanel将drawing加载为空白页       	     
        	     addObserver(parent);//添加mainPanel为preview的观察者
        	     ObMessage blankpage = new ObMessage(MessageType.PAGE_REPLACE,blank);
        	     notifyObservers(blankpage);
        	}
        });
        //-------------------------------------------按钮栏-------------------------------------------------------------------
        
        funcPanel = new JPanel();
        funcPanel.setLayout(null);
        funcPanel.setBackground(Color.lightGray);
        this.add(funcPanel);
        funcPanel.setSize((int)width * 20 / 100, 29);
        funcPanel.setLocation(0,0);
        funcPanel.add(add);
        add.setSize(57, 25);
        add.setLocation(210,0);        
        
    }
    
    public void notifyObservers(ObMessage arg) {
        for (Observer obs : observers) {
            obs.update(this,arg);
        }
    }

    public void update(Observable o, ObMessage arg) {
        // 处理HISTORY_UPDATE与PAGE_ALTERED
    	if(arg.getType().equals(MessageType.PAGE_ALTERED)){
    	//用于提示某一页内容发生了较大变动，由DrawingPanel发出，给PreviewPanel，附带内容为Page
    		Page updatePage = (PageDefault)arg.getContent();//更新后page的内容
    		int pagenumber = updatePage.getNumber();
    		subPanelList.get(pagenumber).setPage(updatePage);
    		subPanelList.get(pagenumber).repaint();//重画，可能会重画不出来   		
    	}
    	
    	if(arg.getType().equals(MessageType.HISTORY_UPDATE)){
    		subPanelList.clear();
    		ArrayList<Page> history_page = (ArrayList<Page>) arg.getContent();
    		//TODO 
    		for(Page page: history_page){
    			SubPreviewPanel subpanel = new SubPreviewPanel(page);
    			subpanel.setPage(page);//设置subpreview的历史信息和页数信息
    			subPanelList.add(subpanel);
    		}
    //显示历史信息		
    		pagePanel.removeAll();
    		//------　Use FlowLayout instead
    		pagePanel.setLayout(new FlowLayout());
    		
    		for(SubPreviewPanel subpanel: subPanelList){
    			pagePanel.add(subpanel);
    		}
    		 backPanel.removeAll();
    		 scrollPane = new JScrollPane(pagePanel);
    		// pagePanel.repaint();
    		 backPanel.add(scrollPane);
    	        scrollPane.setLocation(0,0);
    	        scrollPane.setBackground(Color.white);
    	        scrollPane.setSize((int)width * 20 / 100, (int)height * 99 / 100-29);
    	        scrollPane.setVisible(true);
    	        scrollPane.repaint();    	       
    	     backPanel.repaint();   
    	    	     
    	     //------------- Refresh layout
    	     scrollPane.doLayout();
    		
    	}
    }

	public void addObserver(Observer observer) {
		observers.add(observer);
		
	}

	public void removeObserver(Observer observer) {
		observers.remove(observer);
		
	}
}
