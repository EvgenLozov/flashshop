package com.flashshop.managers;

import java.sql.SQLException;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 15.03.13
 * Time: 21:52
 * To change this template use File | Settings | File Templates.
 */
public class NotBlockingDataBaseManager {
    private DataBaseManager dataBaseManager = DataBaseManager.getInstance();
    private static NotBlockingDataBaseManager ourInstance = new NotBlockingDataBaseManager();

    private NotBlockingDataBaseManager(){}

    public static NotBlockingDataBaseManager getInstance(){
        return ourInstance;
    }

    public Future<Set<String>> getProductSkuForCategory(final String categoryName){
        final Future<Set<String>> future = new Future<Set<String>>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dataBaseManager.getProductSkuForCategory(categoryName);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return future;
    }

}
