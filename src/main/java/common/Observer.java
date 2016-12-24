package common;

/**
 * Created by 徐江河 on 2016/12/24.
 */
public interface Observer {
    void update(Observable o, ObMessage arg);
}
