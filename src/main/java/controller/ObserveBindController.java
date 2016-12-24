package controller;

import common.Observer;
import controller.impl.ObserveBindControllerDefault;

/**
 * Created by 徐江河 on 2016/12/24.
 */
public abstract class ObserveBindController {
    private static ObserveBindController instance = new ObserveBindControllerDefault();

    public abstract void bindToAllModel(Observer observer);
    public static ObserveBindController getInstance() {
        return instance;
    }
}
