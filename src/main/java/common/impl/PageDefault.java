package common.impl;

import common.Page;
import common.Position;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 徐江河 on 2016/12/24.
 */
public class PageDefault implements Page ,Serializable {

    private ArrayList<Position> view;
    private int number;

    public PageDefault(ArrayList<Position> view, int number) {
        this.view = view;
        this.number = number;
    }

    public ArrayList<Position> getView() {
        return view;
    }
    public int getNumber() { return number; }
}
