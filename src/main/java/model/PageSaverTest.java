package model;


import common.Page;
import common.Position;
import common.impl.PageDefault;

import java.util.ArrayList;

/**
 * Created by Alisa on 16/12/28.
 */
public class PageSaverTest {

    public static void main(String[] args){
        ArrayList<Page> pages=new ArrayList<Page>();
        ArrayList<Position> view=new ArrayList<Position> ();
        Position position=new Position();
        position.x=1;
        position.y=2;
        position.type=3;
        view.add(position);
        int number=6;
        Page page=new PageDefault(view,number);
        pages.add(page);
        PageSaver.getInstance().savePages(pages);
    }

}