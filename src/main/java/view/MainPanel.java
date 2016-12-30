package view;

import common.Page;
import common.Position;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import common.*;
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
    
    // Controllers
    HistoryNoteController historyLoadController = HistoryNoteController.getInstance();
    SaveNoteController pageSaveController = SaveNoteController.getInstance();
    ObserveBindController binder = ObserveBindController.getInstance();
   
    // 子Panel
    private PreviewPanel previewPane;
    private DrawingPanel drawingPane;

    // 可能的内部状态变量
    int currentPage = 0;

    public MainPanel() {
        super();
    	
        initPanes();
        initView();
        
        binder.bindToAllModel(this); // 绑定Model相关模块

        // 等待加载历史pages，可以考虑加入超时判定
        //historyLoadController.loadHistoryNotes();
    }

    // 初始化子Panel，建立Observer/Observable关系
    private void initPanes() {
        previewPane = new PreviewPanel(this);
        drawingPane = new DrawingPanel();
        
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
        if (arg.getType()==MessageType.HISTORY_UPDATE) {
            // 可能主Panel需要更新，也可能不需要
            ArrayList<Page> pages = (ArrayList<Page>) arg.getContent();

            // 但子Panel需要
            notifyObservers(arg);
        } else {
            // ...
        }

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
