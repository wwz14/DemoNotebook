package common;

import java.util.ArrayList;

/**
 * Created by 徐江河 on 2016/12/24.
 */
public interface Page {
    ArrayList<Position> getView();
    int getNumber();
}
