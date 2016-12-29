package model;

import common.ObMessage;
import common.Observable;
import common.Observer;
import common.Page;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 徐江河 on 2016/12/24.
 */
public class HistoryLoader implements Observable {

    private static HistoryLoader instance = new HistoryLoader();

    private Set<Observer> observers = new HashSet<Observer>();

    private ArrayList<Page> pages;

    public ArrayList<Page> getPages() {
        return pages;
    }

    private HistoryLoader(){
    }

    public void loadHistory() throws IOException {
        //第一次的时候需要创建文件
       String filename = "history.txt";
        File file=new File(filename);
        if(!file.exists()) {
                file.createNewFile();
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            pages = (ArrayList<Page>) ois.readObject();
            ois.close();
        }
        catch (Exception es) {
            System.out.println(es);
        }
    }



    public void notifyObservers(ObMessage arg) {
        for (Observer observer : observers) {
            observer.update(this, arg);
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public static HistoryLoader getInstance() {
        return instance;
    }
}
