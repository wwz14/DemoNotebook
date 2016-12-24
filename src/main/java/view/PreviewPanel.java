package view;

import javax.swing.*;
import common.*;

/**
 * Created by 徐江河 on 2016/12/24.
 */
class PreviewPanel extends JPanel implements Observer {

    private MainPanel parent;

    public PreviewPanel(MainPanel parent) {
        super();
        this.parent = parent;
    }

    public void update(Observable o, ObMessage arg) {

    }
}
