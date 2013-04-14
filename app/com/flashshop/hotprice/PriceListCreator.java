package com.flashshop.hotprice;

import com.flashshop.hotprice.catalog.Catalog;
import com.flashshop.hotprice.model.Item;
import com.flashshop.hotprice.model.Price;
import com.flashshop.hotprice.url_product.CompositeProvider;
import com.flashshop.hotprice.url_product.ProductSkuProvider;
import com.flashshop.hotprice.url_product.ProviderFromCategory;
import com.flashshop.hotprice.url_product.ProviderFromFile;
import com.flashshop.managers.DataBaseManager;
import com.flashshop.model.Category;
import com.flashshop.pricelist.CachedPriceListSettingsService;
import com.flashshop.pricelist.model.PriceListSettings;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 11.11.12
 * Time: 11:45
 * To change this template use File | Settings | File Templates.
 */
public class PriceListCreator {

    public static PriceListCreator instance = new PriceListCreator();

    public final static String PRICELIST_FILE = "public"+File.separator+"pricelists"+File.separator+"pricelist-hotprice.xml";
    public final static String URL_IMAGE_CATALOG="http://flashshop.com.ua/components/com_virtuemart/shop_image/product/";

    public static PriceListCreator getInstance(){
        return instance;
    }

    private PriceListCreator() {
    }

    public File getPriceList(){
        return new File(PRICELIST_FILE);
    }

    private CompositeProvider getCompositeProvider(){
        CompositeProvider compositeProvider = new CompositeProvider();

        try {
            PriceListSettings settings = CachedPriceListSettingsService.getInstance().getPriceListSettings(1);

            for(Category category: settings.getSelectedCategory())
                compositeProvider.addProvider(new ProviderFromCategory(category.getCategoryName()));

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

        }

        return compositeProvider;
    }

    public void refreshPrice() throws Exception {
        Catalog.clear();
        ProductSkuProvider provider = getCompositeProvider();

        Price price = new Price();

        for (String productSku: provider.getSetOfProductSku()){
            Item item = getAvailableItem(productSku);
            if (item!=null)
                price.addItem(item);
        }
        price.setCatalog(Catalog.getCategories());
        save(price);
    }

    public Item getAvailableItem(String productSku) throws SQLException {

        ResultSet resultSet = DataBaseManager.getInstance().getProductForHotprice(productSku);

        if (!resultSet.next()){
            throw new  RuntimeException("Product with product_sku  '"+productSku+"' does not exist.");
        }

        System.out.println(resultSet.getString("product_in_stock")+" "+productSku);

        if (Integer.parseInt(resultSet.getString("product_in_stock"))!=5)
            return null;

        String s;

        Item item = new Item();
        item.setId(resultSet.getString("product_id"));
        item.setName( resultSet.getString("product_name") );
        item.setUrl(resultSet.getString("product_url_ok"));
        item.setPrice(Double.parseDouble(resultSet.getString("product_price")));
        item.setCategoryId(Catalog.getManufacturerId(resultSet.getString("category_name"), resultSet.getString("mf_name")));
        item.setVendor(resultSet.getString("mf_name"));
        item.setImage(URL_IMAGE_CATALOG+resultSet.getString("product_full_image"));
        item.setDescription(resultSet.getString("product_s_desc"));

        return item;
    }
    public void  save (Price price) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance( Price.class );

        // marshall into XML via System.out
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "windows-1251");

        FileOutputStream outputStream = new FileOutputStream(PRICELIST_FILE);


        marshaller.marshal( price, outputStream);

    }
}
