package view;

import common.Page;
import common.Position;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.HeadlessException;
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

    // Controllers
    HistoryNoteController historyLoadController = HistoryNoteController.getInstance();
    SaveNoteController pageSaveController = SaveNoteController.getInstance();
    ObserveBindController binder = ObserveBindController.getInstance();
   
    // 以下为子Panel
    private PreviewPanel previewPane;
    private DrawingPanel drawingPane;

    // 以下为可能的内部状态变量
    int currentPage = 0;

    public MainPanel() {
        super();
    	
        initPanes();
        initView();
        // 绑定Model相关模块
        binder.bindToAllModel(this);

        // 等待加载历史pages，可以考虑加入超时判定
        //historyLoadController.loadHistoryNotes();
    }

    private void initPanes() {
        // 初始化各个子Panel，建立Observer/Observable关系
        // 例：
        previewPane = new PreviewPanel(this);
        drawingPane = new DrawingPanel();
        
        this.addObserver(previewPane);
        this.addObserver(drawingPane);
    }

    private void initView() {
        // 调整布局，添加各个子Panel等
        // 例：
    	this.setSize(700,500);
    	this.setLayout(null);
    	this.add(previewPane);
    	this.add(drawingPane);
    	previewPane.setLocation(0,0);
        previewPane.setSize(200,800);
        drawingPane.setLocation(200, 100);
        drawingPane.setSize(600, 500);
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
		
    public static void main(String[] args){
    	JFrame jf = new JFrame();
    	MainPanel mp = new MainPanel();
    	jf.add(mp);
    	jf.setSize(800, 600);
    	jf.setVisible(true);
    }
    
}
