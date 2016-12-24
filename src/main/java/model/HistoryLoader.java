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


    public void notifyObservers(ObMessage arg) {

    }

    public void addObserver(Observer observer) {

    }

    public void removeObserver(Observer observer) {

    }

    public static HistoryLoader getInstance() {
        return instance;
    }
}
