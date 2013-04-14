package com.flashshop;



import com.flashshop.executor.PeriodicPerformer;
import com.flashshop.executor.Task;
import com.flashshop.hotprice.PriceListCreator;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 13.03.13
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
public class Performers {
    static HashMap<Integer,PeriodicPerformer> performers = new HashMap<Integer,PeriodicPerformer>();

    static {
        performers.put(1,new PeriodicPerformer(new Task() {
            volatile PriceListCreator priceListCreator = PriceListCreator.getInstance();

            @Override
            public void execute() throws IOException, SAXException, SQLException, ParserConfigurationException {

                    try {
                        System.out.println("Create pricelist for 1");
                        //priceListCreator.refreshPrice();
                    } catch (Exception e) {
                        Logger.getInstance().addLog(e);
                    }
                }

        }));

        performers.put(2,new PeriodicPerformer(new Task() {
            @Override
            public void execute() throws IOException, SAXException, SQLException, ParserConfigurationException {
                System.out.println("Create pricelist for 2");
            }
        }));

        performers.put(3,new PeriodicPerformer(new Task() {
            @Override
            public void execute() throws IOException, SAXException, SQLException, ParserConfigurationException {
                System.out.println("Create pricelist for 3");
            }
        }));

    }

    public static HashMap<Integer,PeriodicPerformer> getPerformer (){
        return performers;

    }
}
