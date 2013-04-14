import com.flashshop.actualizator.ActualisatorSettingsService;
import com.flashshop.actualizator.CachedActualizatorSettingsService;
import com.flashshop.actualizator.model.ActualizatorSettings;
import com.flashshop.actualizator.model.CategoryWithMargin;
import com.flashshop.executor.PeriodicPerformer;
import com.flashshop.executor.Task;
import com.flashshop.hotprice.PriceListCreator;
import com.flashshop.managers.DataBaseManager;
import com.flashshop.model.Category;
import com.flashshop.pricelist.CachedPriceListSettingsService;
import com.flashshop.pricelist.PriceListSettingsService;
import com.flashshop.pricelist.model.PriceListSettings;
import org.junit.Test;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 09.01.13
 * Time: 21:56
 * To change this template use File | Settings | File Templates.
 */
public class HotPriceTests {
    @Test
    public void testStart() throws Exception {
        PeriodicPerformer performer = new PeriodicPerformer(new Task() {
            @Override
            public void execute() {
                System.out.println("Job is done!");
            }
        });
        performer.setPeriod(1000);
        performer.start();
        Thread.sleep(2000);
        performer.stop();

        Thread.sleep(10000);



    }

    @Test
    public void testActualizatorSettings() throws Exception {
        ActualisatorSettingsService dataBaseManager = CachedActualizatorSettingsService.getInstance();
//        ActualizatorSettings actualizatorSettings = dataBaseManager.getActualizatorSettings();
        long start = System.currentTimeMillis();
        ActualizatorSettings actualizatorSettings = dataBaseManager.getActualizatorSettings();
        System.out.println(System.currentTimeMillis() - start);

        actualizatorSettings.setPeriod(70000);
        CategoryWithMargin category = new CategoryWithMargin();
        category.setCategoryId(10);
        category.setCategoryName("Категория10");
        category.setMargin(10);
        actualizatorSettings.add(category);

        start = System.currentTimeMillis();
        dataBaseManager.setActualizatorSettings(actualizatorSettings);
        System.out.println(System.currentTimeMillis() - start);

        Thread.sleep(2000);

    }

    @Test
    public void testPriceListSettings() throws Exception {
        PriceListSettingsService service = CachedPriceListSettingsService.getInstance();
        long start = System.currentTimeMillis();
        PriceListSettings priceListSettings = service.getPriceListSettings(1);
        System.out.println(System.currentTimeMillis() - start);

        priceListSettings.setInterval(50000);
        priceListSettings.setId(2);
        Category category = new Category();
        category.setCategoryId(50);
        category.setCategoryName("категория20");
        priceListSettings.add(category);

        start = System.currentTimeMillis();
        service.setPriceListSettings(priceListSettings);
        System.out.println(System.currentTimeMillis() - start);

        Thread.sleep(2000);
    }

    @Test
    public void testName() throws Exception {
        PriceListCreator.getInstance().refreshPrice();

    }
}
