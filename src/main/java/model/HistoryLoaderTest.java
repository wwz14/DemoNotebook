package model;

import common.Page;
import common.Position;

import java.util.ArrayList;

/**
 * Created by Alisa on 16/12/28.
 */
public class HistoryLoaderTest {
    public static void main(String[] args){
        HistoryLoader his=  HistoryLoader.getInstance();
        his.loadHistory();
        ArrayList<Page> pages =his.getPages();
        for(int i=0;i<pages.size();i++){
            Page page=pages.get(i);
            System.out.println(page.getNumber());
            ArrayList<Position> positions=page.getView();
            for(int j=0;j<positions.size();j++){
                Position position=positions.get(i);
                System.out.println(position.x);
                System.out.println(position.y);
                System.out.println(position.type);
            }
        }
    }
}
