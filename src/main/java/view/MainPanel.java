package view;

import common.Page;
import common.Position;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import common.*;
import common.impl.PageDefault;
import controller.HistoryNoteController;
import controller.ObserveBindController;
import controller.SaveNoteController;

/**
 * Created by 徐江河 on 2016/12/24.
 */
public class MainPanel extends JPanel implements Observer, Observable, ActionListener{

    private Set<Observer> observers = new HashSet<Observer>();

    public int width;
    public int height;
    
    public ArrayList<Page> pageList;
    public int totalPage = 0;
    public int currentPage = 1;
    
    // Controllers
    HistoryNoteController historyLoadController = HistoryNoteController.getInstance();
    SaveNoteController pageSaveController = SaveNoteController.getInstance();
    ObserveBindController binder = ObserveBindController.getInstance();
   
    // 子Panel
    private PreviewPanel previewPane;
    private DrawingPanel drawingPane;

    public MainPanel() {
        super();
    	
        initPanes();
        initView();
        
        binder.bindToAllModel(this); // 绑定Model相关模块

//        historyLoadController.loadHistoryNotes();
        pageList = historyLoadController.getPages();
        if (pageList != null) {
        	totalPage = pageList.size();
        }
        System.out.println("Total Page: "+totalPage);
    }

    // 初始化子Panel，建立Observer/Observable关系
    private void initPanes() {
        previewPane = new PreviewPanel(this);
        drawingPane = new DrawingPanel(this);
        
        this.addObserver(previewPane);
        this.addObserver(drawingPane);
    }

    // 调整布局和视图效果
    private void initView() {
    	//获得屏幕大小
    	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    	width = (int)screensize.getWidth();
    	height = (int)screensize.getHeight();
    	
    	this.setSize(width,height);
    	this.setLayout(null);
    	this.add(previewPane);
    	this.add(drawingPane);
    	previewPane.setLocation(0, (int)height * 5 / 100);
        previewPane.setSize((int)width * 20 / 100, (int)height * 99 / 100);
        drawingPane.setLocation((int)width * 21 / 100, (int)height * 5 / 100);
        drawingPane.setSize((int)width * 75 / 100, (int)height * 99 / 100);
        
        //设置drawingPanel的宽和高，在屏幕大小不一时保证背景笔记线的比例
        drawingPane.width = (int)width * 75 / 100;
        drawingPane.height = (int)width * 99 / 100;
        
        this.setVisible(true);
        
        // 要处理DrawingPanel与PreviewPanel的Observe关系

    }

    public void update(Observable o, ObMessage arg) {
    	System.out.println("附加的参数类型为: "+arg.getContent().getClass());
        if (arg.getType() == MessageType.HISTORY_UPDATE) {
        	ArrayList<Page> pages = (ArrayList<Page>) arg.getContent();
            notifyObservers(arg);
        }
        else if (arg.getType() == MessageType.PAGE_ALTERED) { // 从DrawingPanel中传来的方法
        	System.out.println("This is Main + page_alter");
        	notifyObservers(arg);	
        	Page thisPage = new PageDefault((ArrayList<Position>)arg.getContent(), currentPage);
        	pageList.add(thisPage);
        	pageSaveController.savePages(pageList);
        }
        else if (arg.getType() == MessageType.PAGE_REPLACE) {}
        	System.out.print("This is Main + page_replace"); // 从PreviewPanel中传来的方法
        	notifyObservers(arg);
    }

    public void notifyObservers(ObMessage arg) {
        for (Observer observer : observers) {
            observer.update(this, arg);
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

	public void actionPerformed(ActionEvent event) {
		
	} 
		
}
