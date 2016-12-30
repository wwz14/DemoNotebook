package controller;

import java.util.ArrayList;

import common.Page;

import controller.impl.HistoryNoteControllerDefault;

/**
 * Created by 徐江河 on 2016/12/24.
 */
public abstract class HistoryNoteController {
    private static HistoryNoteController instance = new HistoryNoteControllerDefault();

    public abstract ArrayList<Page> getPages();
    public abstract void loadHistoryNotes();

    public static HistoryNoteController getInstance() {
        return instance;
    }
}
