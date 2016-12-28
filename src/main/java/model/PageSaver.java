package model;

import common.ObMessage;
import common.Observable;
import common.Observer;
import common.Page;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class PageSaver implements Observable {

    private static PageSaver instance = new PageSaver();

    private Set<Observer> observers = new HashSet<Observer>();


    private PageSaver() {

    }

    public void savePages(ArrayList<Page> pages) {
        String filename="history.txt";
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(pages);
            oos.close();
            System.out.println("success to save");
        } catch (Exception es) {
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

    public static PageSaver getInstance() {
        return instance;
    }
}
