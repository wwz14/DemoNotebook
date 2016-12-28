package view;

import common.Page;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import common.*;
import controller.HistoryNoteController;
import controller.ObserveBindController;
import controller.SaveNoteController;

/**
 * Created by 徐江河 on 2016/12/24.
 */
public class MainPanel extends JPanel implements Observer, Observable {

    private Set<Observer> observers = new HashSet<Observer>();

    // Controllers
    HistoryNoteController historyLoadController = HistoryNoteController.getInstance();
    SaveNoteController pageSaveController = SaveNoteController.getInstance();
    ObserveBindController binder = ObserveBindController.getInstance();
    // 以下为子Panel
    private PreviewPanel previewPane;

    // 以下为可能的内部状态变量
    int currentPage = 0;

    public MainPanel() {
        super();
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
        this.addObserver(previewPane);

    }

    private void initView() {
        // 调整布局，添加各个子Panel等
        // 例：
        previewPane.setLocation(0,0);
        this.add(previewPane);

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
}
