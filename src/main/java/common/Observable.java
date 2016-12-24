package common;

import common.Observer;

/**
 * Created by 徐江河 on 2016/12/24.
 */
public interface Observable {
    void notifyObservers(ObMessage arg);
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
}
