package controller.impl;

import common.Page;
import controller.SaveNoteController;
import model.PageSaver;

import java.util.List;

/**
 * Created by 徐江河 on 2016/12/24.
 */
public class SaveNoteControllerDefault extends SaveNoteController {
    public void savePages(List<Page> pages) {
        PageSaver.getInstance().savePages(pages);
    }
}
