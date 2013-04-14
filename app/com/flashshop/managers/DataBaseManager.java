package com.flashshop.managers;

import com.flashshop.Product;
import com.flashshop.actualizator.ActualisatorSettingsService;
import com.flashshop.actualizator.model.ActualizatorSettings;
import com.flashshop.actualizator.model.CategoryWithMargin;
import com.flashshop.model.Category;
import com.flashshop.model.PublishedCategoriesService;
import com.flashshop.pricelist.PriceListSettingsService;
import com.flashshop.pricelist.model.PriceListSettings;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseManager implements ActualisatorSettingsService,PublishedCategoriesService,PriceListSettingsService {

    private static DataBaseManager ourInstance = new DataBaseManager();

    private final static String DB_URL = "jdbc:mysql://flashs00.mysql.ukraine.com.ua/flashs00_db";
    private final static String USER = "flashs00_db";
    private final static String PASSWORD = "23111989kjpjd";

    private Connection c;

    public static DataBaseManager getInstance() {
        return ourInstance;
    }

    private DataBaseManager() {
        try {
            c = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.WARNING, e.getMessage());
        }

    }

    public void addTradePosition(Product product) throws SQLException {
        CallableStatement callableStatement = c.prepareCall("{call add_trade_position(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        callableStatement.setString(1,product.categoryName);
        callableStatement.setString(2,product.manufacturer);
        callableStatement.setString(3,product.productName);
        callableStatement.setString(4,product.productSku);
        callableStatement.setString(5,product.productPrice);
        callableStatement.setString(6,product.shortDescription);
        callableStatement.setString(7,product.description);
        callableStatement.setString(8,product.productModel);
        callableStatement.setInt(9,product.providerId);
        callableStatement.execute();
        callableStatement.close();
    }

    public void changePriceBySku(String productSku, String price) throws SQLException {
        String query = "UPDATE `jos_vm_product_price` SET `product_price` = ? WHERE `product_id`=(SELECT product_id FROM jos_vm_product WHERE product_sku= ? );";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        preparedStatement.setString(1,price);
        preparedStatement.setString(2,productSku);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public Set<String> getSetOfProductSku() throws SQLException {
        Statement statement = c.createStatement();
        String query = "SELECT `product_sku` FROM `jos_vm_product`";
        ResultSet r = statement.executeQuery(query);
        Set<String> setOfProductSku = new HashSet<String>();
        while (r.next())
            setOfProductSku.add(r.getString("product_sku"));
        r.close();
        statement.close();

        return setOfProductSku;
    }

    public void changeAvailabilityBySku(String productSku, boolean productAvailability) throws SQLException {
        String quantityInStock;
        if(productAvailability)
            quantityInStock = "5";
        else
            quantityInStock = "0";

        String query = "UPDATE `jos_vm_product` SET `product_in_stock` = ? WHERE `product_sku` = ? ;";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        try{
            preparedStatement.setString(1,quantityInStock);
            preparedStatement.setString(2,productSku);
            preparedStatement.executeUpdate();
        }
        finally {
            preparedStatement.close();
        }
    }

    public Set<String> getProductSkuForCategory(String categoryName) throws SQLException {
        Statement statement = c.createStatement();
        String query = "SELECT product_sku FROM jos_vm_product " +
                "JOIN jos_vm_product_category_xref " +
                "ON jos_vm_product_category_xref.product_id=jos_vm_product.product_id " +
                "JOIN jos_vm_category " +
                "ON jos_vm_category.category_id=jos_vm_product_category_xref.category_id " +
                "WHERE category_name='"+categoryName+"'";

        ResultSet r = statement.executeQuery(query);
        Set<String> setOfProductSku = new HashSet<String>();
        while (r.next()){
            setOfProductSku.add(r.getString("product_sku"));
        }
        r.close();
        statement.close();

        return  setOfProductSku;
    }
    public void addUrlOfProductToDataBase(String productSku, String productUrl) throws SQLException, UnsupportedEncodingException {
        String query = "UPDATE `jos_vm_product` SET `product_url_ok`= ? WHERE `product_sku`= ?;";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        try{
            preparedStatement.setString(1,productUrl);
            preparedStatement.setString(2,productSku);
            preparedStatement.executeUpdate();
        }
        finally {
            preparedStatement.close();
        }

    }

    public void changeDescription(String productSku, String description) throws SQLException {
        String query = "UPDATE `jos_vm_product` SET `product_desc`= ? WHERE `product_sku`= ? ;";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        try{
            preparedStatement.setString(1,description);
            preparedStatement.setString(2,productSku);
            preparedStatement.executeUpdate();
        }
        finally {
            preparedStatement.close();
        }
    }

    public void changeWarranty(String product_sku, String productWarranty) throws SQLException {
        String query = "UPDATE `jos_vm_product` SET `product_warranty`= ? WHERE `product_sku`= ? ;";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        try{
            preparedStatement.setString(1,productWarranty);
            preparedStatement.setString(2,product_sku);
            preparedStatement.executeUpdate();
        }
        finally {
            preparedStatement.close();
        }
    }

    public boolean isExistDescription(String productSku) throws SQLException {
        String query = "SELECT product_desc FROM jos_vm_product WHERE product_sku= ? ;";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        preparedStatement.setString(1,productSku);
        ResultSet resultSet = preparedStatement.executeQuery(query);

        if (!resultSet.next())
            return  false;
        String description = resultSet.getString("product_desc");

        resultSet.close();
        preparedStatement.close();

        return !description.isEmpty();
    }

    public List<String> getProductNameForCategory(String categoryName) throws SQLException {
        Statement statement = c.createStatement();
        List<String> productNames = new ArrayList<String>();
        String query = "SELECT product_name FROM jos_vm_category"+
        " Join jos_vm_product_category_xref on jos_vm_category.category_id = jos_vm_product_category_xref.category_id"+
        " join jos_vm_product on jos_vm_product.product_id = jos_vm_product_category_xref.product_id"+
        " WHERE category_name='"+categoryName+"';";

        ResultSet r = statement.executeQuery(query);
        while (r.next()){
            productNames.add(r.getString("product_name"));
        }
        return productNames;
    }

    public ResultSet getProductForHotprice(String productSku) throws SQLException {
        Statement statement = c.createStatement();
        String query = "call get_product_for_hotprice('"+productSku+"')";
        ResultSet resultSet = statement.executeQuery(query);

        return resultSet;
    }

    public List<String> getCategories() throws SQLException {
        Statement statement = c.createStatement();
        String query = "SELECT category_name FROM jos_vm_category;";
        ResultSet resultSet = statement.executeQuery(query);
        List<String> categories = new ArrayList<String>();
        while (resultSet.next()){
            categories.add(resultSet.getString("category_name"));
        }
        return categories;
    }

    public List<Category> getPublishedCategories() throws SQLException {
        Statement statement = c.createStatement();
        String query = "SELECT category_name,category_id FROM jos_vm_category where category_publish = 'Y';";
        ResultSet resultSet = statement.executeQuery(query);
        List<Category> categories = new ArrayList<Category>();
        while (resultSet.next()){
            Category nextCategory = new Category();
            nextCategory.setCategoryName(resultSet.getString("category_name"));
            nextCategory.setCategoryId(resultSet.getInt("category_id"));
            categories.add(nextCategory);
        }
        return categories;
    }

    public ActualizatorSettings getActualizatorSettings() throws SQLException {
        String query = "SELECT actualizator_settings.*, actualizator_settings_to_categories.margin, jos_vm_category.category_name, jos_vm_category.category_id " +
                "FROM actualizator_settings " +
                "LEFT JOIN actualizator_settings_to_categories ON actualizator_settings_to_categories.actualizator_settings_id = actualizator_settings.id " +
                "LEFT JOIN jos_vm_category ON jos_vm_category.category_id = actualizator_settings_to_categories.category_id " +
                "where actualizator_settings.id = '1'";
        Statement statement = c.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        ActualizatorSettings actualizatorSettings = new ActualizatorSettings();

        while (resultSet.next()){
            actualizatorSettings.setPeriod(resultSet.getInt("period"));
            actualizatorSettings.setSettingId(resultSet.getInt("id"));
            if(resultSet.getString("category_id") != null)
            {
                CategoryWithMargin category = new CategoryWithMargin();
                category.setCategoryId(resultSet.getInt("category_id"));
                category.setCategoryName(resultSet.getString("category_name"));
                category.setMargin( resultSet.getInt("margin") );
                actualizatorSettings.add(category);
            }
        }
        resultSet.close();
        statement.close();

        return actualizatorSettings;
    }

    public void setActualizatorSettings(ActualizatorSettings actualizatorSettings) throws SQLException {
        try{
            c.setAutoCommit(false);
            String deleteQuery = "DELETE FROM `actualizator_settings_to_categories` " +
                    "WHERE `actualizator_settings_id`= ? ";
            PreparedStatement preparedStatementDelete = c.prepareStatement(deleteQuery);
            try{
                preparedStatementDelete.setInt(1, 1);
                preparedStatementDelete.executeUpdate();
            }
            finally {
                preparedStatementDelete.close();
            }


            String updatePeriod = "UPDATE `actualizator_settings` SET `period`= ?  WHERE `id`= ? ";
            PreparedStatement preparedStatementUpdatePeriod = c.prepareStatement(updatePeriod);
            try{
                preparedStatementUpdatePeriod.setInt(1,actualizatorSettings.getPeriod());
                preparedStatementUpdatePeriod.setInt(2,1);
                preparedStatementUpdatePeriod.executeUpdate();
            }
            finally {
                preparedStatementUpdatePeriod.close();
            }

            String updateCategory = "INSERT INTO `actualizator_settings_to_categories` (`actualizator_settings_id`, `category_id`, `margin`)"+
                    "VALUES (?,?,?);";
            PreparedStatement preparedStatementUpdateCategory = c.prepareStatement(updateCategory);
            try{
                preparedStatementUpdateCategory.setInt(1,actualizatorSettings.getSettingId());
                for (CategoryWithMargin category: actualizatorSettings.getCategories()){
                    preparedStatementUpdateCategory.setInt(2,category.getCategoryId());
                    preparedStatementUpdateCategory.setInt(3,category.getMargin());
                    preparedStatementUpdateCategory.executeUpdate();

                }
            }
            finally {
                preparedStatementUpdateCategory.close();
            }
            c.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction failed.");
                c.rollback();
            }
            catch (SQLException se) {
                System.out.println("Rollback failed.");
                se.printStackTrace();
            }
        }
    }

    @Override
    public PriceListSettings getPriceListSettings(Integer settingsid) throws SQLException {
        String query = "SELECT price_list_settings.*, jos_vm_category.category_name, jos_vm_category.category_id " +
                "FROM price_list_settings " +
                "LEFT JOIN price_list_settings_to_categories ON price_list_settings_to_categories.price_list_settings_id=price_list_settings.id " +
                "LEFT JOIN jos_vm_category ON jos_vm_category.category_id=price_list_settings_to_categories.category_id " +
                "WHERE price_list_settings.id= ? ";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        preparedStatement.setInt(1,settingsid);
        ResultSet resultSet = preparedStatement.executeQuery();

        PriceListSettings priceListSettings = new PriceListSettings();
        while (resultSet.next()){
            priceListSettings.setInterval(resultSet.getInt("period"));
            priceListSettings.setId(resultSet.getInt("id"));
            priceListSettings.setName(resultSet.getString("name"));
            if(resultSet.getString("category_id") != null)
            {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt("category_id"));
                category.setCategoryName(resultSet.getString("category_name"));
                priceListSettings.add(category);
            }
        }

        if (priceListSettings.getId()==null){
            throw new RuntimeException("PriseListSettings with id: "+settingsid+" does not exist.");
        }
        resultSet.close();
        preparedStatement.close();

        return priceListSettings;
    }

    public ArrayList<PriceListSettings> getAllPriceListSettings() throws SQLException {
        String query = "SELECT * FROM price_list_settings;";
        Statement statement = c.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<PriceListSettings> allPriceListSettings = new ArrayList<PriceListSettings>();
        while (resultSet.next()){
            PriceListSettings priceListSettings = new PriceListSettings();
            priceListSettings.setId(resultSet.getInt(1));
            priceListSettings.setName(resultSet.getString(2));
            allPriceListSettings.add(priceListSettings);
        }
        resultSet.close();
        statement.close();

        return allPriceListSettings;
    }

    @Override
    public void setPriceListSettings(PriceListSettings priceListSettings) throws SQLException {
        try{
            c.setAutoCommit(false);
            String deleteQuery = "DELETE FROM price_list_settings_to_categories WHERE price_list_settings_id = ? ;";
            PreparedStatement preparedStatementDelete = c.prepareStatement(deleteQuery);
            try{
                preparedStatementDelete.setInt(1,priceListSettings.getId());
                preparedStatementDelete.executeUpdate();
            }
            finally {
                preparedStatementDelete.close();
            }


            String updatePeriod = "UPDATE price_list_settings SET period= ? WHERE `id`= ? ;";
            PreparedStatement preparedStatementUpdatePeriod = c.prepareStatement(updatePeriod);
            try{
                preparedStatementUpdatePeriod.setInt(1,priceListSettings.getInterval());
                preparedStatementUpdatePeriod.setInt(2,priceListSettings.getId());
                preparedStatementUpdatePeriod.executeUpdate();
            }
            finally {
                preparedStatementUpdatePeriod.close();
            }

            String updateCategory = "INSERT INTO price_list_settings_to_categories (price_list_settings_id, category_id) VALUES (?,?)";
            PreparedStatement preparedStatementUpdateCategory = c.prepareStatement(updateCategory);
            try{
                preparedStatementUpdateCategory.setInt(1,priceListSettings.getId());

                for (Category category: priceListSettings.getSelectedCategory()){
                    preparedStatementUpdateCategory.setInt(2,category.getCategoryId());
                    preparedStatementUpdateCategory.executeUpdate();
                }
            }
            finally {
                preparedStatementUpdateCategory.close();
            }
             c.commit();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            try {
                System.out.println("Transaction failed.");
                c.rollback();
            }
            catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    public boolean isProductExist(Product product) throws SQLException {
        String query = "SELECT * FROM jos_vm_product where product_model= ?";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        ResultSet resultSet = null;

        try {
            preparedStatement.setString(1,product.productModel);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        }
         finally {
            preparedStatement.close();
            if(resultSet != null)
                resultSet.close();
        }
    }

    public void updateProduct(String productModel,int providerId, String price, String productInStock) throws SQLException {
        String query = "UPDATE `jos_vm_product`,`jos_vm_product_price` " +
                "SET `jos_vm_product_price`.`product_price`= ?, `jos_vm_product`.`product_in_stock`= ?, `jos_vm_product`.`provider_id`= ?  " +
                "WHERE `jos_vm_product`.`product_id` = `jos_vm_product_price`.`product_id` AND `product_model`= ?;";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        try {
            preparedStatement.setString(1,price);
            preparedStatement.setString(2, productInStock);
            preparedStatement.setInt(3,providerId);
            preparedStatement.setString(4,productModel);
            preparedStatement.execute();
        } finally {
            preparedStatement.close();
        }


    }
}
