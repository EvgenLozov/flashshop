package com.flashshop;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 27.01.13
 * Time: 17:38
 * To change this template use File | Settings | File Templates.
 */
public class Logger {
    private static final String LOGGER_FILE="public"+File.separator+"logger.txt";

    private static Logger ourInstance = new Logger();

    public static Logger getInstance() {
        return ourInstance;
    }

    private Logger() {
    }

    public void addLog(Exception exception){
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(LOGGER_FILE, true), "UTF-8"));
            pw.write(getCurrentData());
            exception.printStackTrace(pw);
            pw.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public File getLoggerFile() {
        return new File(LOGGER_FILE);
    }

    private String getCurrentData(){
        return "\n"+(new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(new GregorianCalendar().getTime()))+"\n";
}
}
