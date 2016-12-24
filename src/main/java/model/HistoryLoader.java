package model;

import common.ObMessage;
import common.Observable;
import common.Observer;

/**
 * Created by 徐江河 on 2016/12/24.
 */
public class HistoryLoader implements Observable {

    private static HistoryLoader instance = new HistoryLoader();

    private HistoryLoader(){
    }

    public void loadHistory() {
        // TODO 从文件解序列化
    }

    public void notifyObservers(ObMessage arg) {
        // TODO Observable接口实现参照MainPanel已经写好的
    }

    public void addObserver(Observer observer) {

    }

    public void removeObserver(Observer observer) {

    }

    public static HistoryLoader getInstance() {
        return instance;
    }
}
