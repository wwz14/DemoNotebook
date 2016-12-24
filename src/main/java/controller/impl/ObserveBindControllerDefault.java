package controller.impl;

import common.Observer;
import controller.HistoryNoteController;
import controller.ObserveBindController;
import model.HistoryLoader;
import model.PageSaver;

/**
 * Created by 徐江河 on 2016/12/24.
 */
public class ObserveBindControllerDefault extends ObserveBindController {

    public void bindToAllModel(Observer observer) {
        PageSaver.getInstance().addObserver(observer);
        HistoryLoader.getInstance().addObserver(observer);
    }
}
