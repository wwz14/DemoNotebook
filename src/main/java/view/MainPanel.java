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
    
    // 菜单栏 ———我觉得MainFrame这里应该不需要菜单栏了……主要的两个功能“保存”“清除所有”用按钮代替了……
 	/*JMenuBar mb;
 	JMenu menu1, menu2, menu3;  
 	JMenuItem i1, i2, i3;  */
 	
    // 以下为子Panel
    private PreviewPanel previewPane;
    private DrawingPanel drawingPane;

    
    // 以下为可能的内部状态变量
    int currentPage = 0;

    public MainPanel() {
        super();
    	
        // 上部菜单栏  
        /*mb = new JMenuBar();  
 		mb.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));  
 		
 		// 加入菜单栏的组件  
 		menu1 = new JMenu("  文件     ");  
 		menu2 = new JMenu("  编辑     "); 
 		menu3 = new JMenu("  帮助     "); 
 		i1 = new JMenuItem("打开                    ", new ImageIcon("img/open.png"));    
 		i2 = new JMenuItem("保存                    ", new ImageIcon("img/save.png")); 
 		i3 = new JMenuItem("清空                    ", new ImageIcon("img/clear.png")); 
 		menu1.add(i1); 
 		menu1.addSeparator(); 
 		menu1.add(i2);   
 		menu2.add(i3);   
 		mb.add(menu1);   
 		mb.add(menu2);  
 		mb.add(menu3);  
 		this.add(mb, BorderLayout.NORTH);    
     	
 		i1.addActionListener(this);  
		i2.addActionListener(this);  
		i3.addActionListener(this);  */
		
        initPanes();
        initView();
        // 绑定Model相关模块
        binder.bindToAllModel(this);

        // 等待加载历史pages，可以考虑加入超时判定
        historyLoadController.loadHistoryNotes();
    }

    private void initPanes() {
        // 初始化各个子Panel，建立Observer/Observable关系
        // 例：
        previewPane = new PreviewPanel(this);
        drawingPane = new DrawingPanel();
        
        
        this.addObserver(previewPane);

    }

    private void initView() {
        // 调整布局，添加各个子Panel等
        // 例：
        previewPane.setLocation(0,0);
        this.add(previewPane);
        drawingPane.setLocation(200, 100);
        this.add(drawingPane);

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
		if (event.getActionCommand().equals("清空                    ")) {   
			drawingPane.thedraw.removeAllElements();  
		} 
		else if (event.getActionCommand().trim().equals("保存")) {  
			JFileChooser sfc = new JFileChooser();  
			int flag = -1;  
			try {    
				flag = sfc.showSaveDialog(this);   // 显示保存文件的对话框   
			} catch (HeadlessException he) {  
				System.out.println("Save File Dialog Exception!");
				he.printStackTrace();
			}
			// 获取选择文件的路径   
			if (flag == JFileChooser.APPROVE_OPTION) {  
				String filename = sfc.getSelectedFile().getPath();  
				try {   
					FileOutputStream fos = new FileOutputStream(filename);    
					ObjectOutputStream oos = new ObjectOutputStream(fos);   
					oos.writeObject(drawingPane.thedraw);  
					oos.close();   
				} catch (Exception ex) {  
					ex.printStackTrace();  
				}  
			}   
		} 
		else if (event.getActionCommand().trim().equals("打开")) {  
			JFileChooser ofc = new JFileChooser();   
			int flag = -1;   
			try {  
				flag = ofc.showOpenDialog(this);  
			} catch (HeadlessException he) {    
				System.out.println("Open File Dialog Exception!"); 
				he.printStackTrace();
			}    
			// 获取选择文件的路径 
			if (flag == JFileChooser.APPROVE_OPTION) {  
				String filename = ofc.getSelectedFile().getPath();  
				try {    
					FileInputStream fis = new FileInputStream(filename);     
					ObjectInputStream ois = new ObjectInputStream(fis);      
					drawingPane.thedraw = (Vector<Position>) ois.readObject();     
					ois.close();  
				}
				catch (Exception ex) {    
					System.out.println(ex);    
				}  
			}  
		}    
		repaint();
	}
}
