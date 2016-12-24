package controller;

import common.Page;
import controller.impl.SaveNoteControllerDefault;

import java.util.List;

/**
 * Created by 徐江河 on 2016/12/24.
 */
public abstract class SaveNoteController {
    private static SaveNoteController instance = new SaveNoteControllerDefault();

    public abstract void savePages(List<Page> pages);
    public static SaveNoteController getInstance() {
        return instance;
    }
}
