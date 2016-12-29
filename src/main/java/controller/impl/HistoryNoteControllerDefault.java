package controller.impl;

import controller.HistoryNoteController;
import model.HistoryLoader;

import java.io.IOException;

/**
 * Created by 徐江河 on 2016/12/24.
 */
public class HistoryNoteControllerDefault extends HistoryNoteController {
    public void loadHistoryNotes() {
        try {
            HistoryLoader.getInstance().loadHistory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
