package com.flashshop.actualizator;

import com.flashshop.Categories;
import com.flashshop.OverseerActualizer;
import com.flashshop.Product;
import com.flashshop.Slave;
import com.flashshop.actualizator.model.ActualizatorSettings;
import com.flashshop.managers.DataBaseManager;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 04.11.12
 * Time: 12:59
 * To change this template use File | Settings | File Templates.
 */
public class Actualizator {
    private static Actualizator instance = new Actualizator();

    private static int DEFAULT_MARGIN = 7;
    private ActualisatorSettingsService actualisatorSettingsService = CachedActualizatorSettingsService.getInstance();

    private Actualizator(){

    }

    public static Actualizator getInstance(){
        return instance;
    }

    public void startActualization() throws SQLException, IOException, SAXException, ParserConfigurationException {
        DataBaseManager dataBaseManager = DataBaseManager.getInstance();
        Set<String> setProductSku = dataBaseManager.getSetOfProductSku();
        int countProduct = 0;
        OverseerActualizer overseer = new OverseerActualizer(Categories.getCategories());
        while (overseer.hasNext()) {
            Product product = overseer.next();
                if (setProductSku.contains(product.productSku)) {
                setProductSku.remove(product.productSku);
                if (product.productAvailability)
                    dataBaseManager.changePriceBySku(product.productSku,getPriceWithMargin(product));

                if (Slave.SET_DESCRIPTION && !dataBaseManager.isExistDescription(product.productSku))
                    dataBaseManager.changeDescription(product.productSku,product.description);
                dataBaseManager.changeWarranty(product.productSku,product.warranty);
                dataBaseManager.changeAvailabilityBySku(product.productSku,product.productAvailability);
                System.out.println("Handle product of SKU: "+product.productSku+". Product availability : " + product.productAvailability);
                countProduct++;
            }
        }
        System.out.println("Count handled product "+countProduct);
       for (String productSku: setProductSku)
        dataBaseManager.changeAvailabilityBySku(productSku,false);

    }
    private String getPriceWithMargin(Product product) throws IOException {
        try {
            ActualizatorSettings actualizatorSettings = actualisatorSettingsService.getActualizatorSettings();

            int margin = actualizatorSettings.getMargin(product.categoryName) != null ? actualizatorSettings.getMargin(product.categoryName) : DEFAULT_MARGIN;
            return (Math.round(Double.parseDouble(product.productPrice)+ margin) + "");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List getMargins () throws SQLException {
        ActualisatorSettingsService settingsService = CachedActualizatorSettingsService.getInstance();
        ActualizatorSettings settings = settingsService.getActualizatorSettings();
        return settings.getCategories();

    }

}
