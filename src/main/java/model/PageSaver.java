package model;

import common.ObMessage;
import common.Observable;
import common.Observer;
import common.Page;

/**
 * Created by 徐江河 on 2016/12/24.
 */
public class PageSaver implements Observable {

    private static PageSaver instance;

    private PageSaver() {

    }

    public void notifyObservers(ObMessage arg) {

    }

    public void addObserver(Observer observer) {

    }

    public void removeObserver(Observer observer) {

    }

    public static PageSaver getInstance() {
        return instance;
    }
}
