package com.flashshop;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 26.01.13
 * Time: 22:25
 * To change this template use File | Settings | File Templates.
 */
public class WorkRoom {
    private static WorkRoom ourInstance = new WorkRoom();

    public static WorkRoom getInstance() {
        return ourInstance;
    }

    private WorkRoom() {
    }
}
