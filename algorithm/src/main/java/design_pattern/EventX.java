package design_pattern;

import java.util.Observable;

public class EventX extends Observable {

    public void doSomething() {
        System.out.println("-- start work--");
        super.setChanged();
        super.notifyObservers("嘿嘿");
    }

    public static void main(String[] args) {
        EventX x = new EventX();
        x.addObserver(new Observer1());
        x.addObserver(new Observer2());
        x.doSomething();
    }

}
