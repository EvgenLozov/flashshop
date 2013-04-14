package com.flashshop.managers;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 13.03.13
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */
public class Future<T> {
    T obj;
    boolean complete;

    public synchronized T get(){
        while (!complete){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public synchronized void set(T t){
        obj = t;
        complete = true;
        notifyAll();
    }

    public boolean isComplete(){
        return complete;
    }
}
