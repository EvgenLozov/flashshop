package com.flashshop.executor;

import com.flashshop.Logger;
import com.flashshop.hotprice.PriceListCreator;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 10.01.13
 * Time: 22:49
 * To change this template use File | Settings | File Templates.
 */
public class PeriodicPerformer {

    volatile private int period = 20000;
    volatile private  boolean switcher = false;

    private Task task;
    private Thread thread;

    public PeriodicPerformer(Task task) {
        this.task = task;
    }

    public void start() {
        if (isRunning())
            return;

        switcher = true;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (switcher){

                    try {
                        task.execute();
                    } catch (Exception e) {
                        Logger.getInstance().addLog(e);
                    }
                    try {
                        Thread.sleep(period);
                    } catch (InterruptedException e) {
                        Logger.getInstance().addLog(e);
                    }
                }
            }
        });

        thread.start();
    }

    public void stop() {
        switcher = false;
        System.out.println(switcher);
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public boolean isRunning() {
        if (thread == null)
            return false;

        return thread.isAlive();
    }
}
