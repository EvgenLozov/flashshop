package excel;

import com.flashshop.Product;
import jxl.Cell;
import jxl.read.biff.BiffException;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 12.04.13
 * Time: 20:00
 * To change this template use File | Settings | File Templates.
 */
public class TDBProductProvider extends AbstractProductProvider {

    private static TDBProductProvider tdbProductProvider = new TDBProductProvider();

    {
        try {
            settings.setWorkbook("d:\\Other\\pechat\\TDB_Dealers_08-02-2013.xls");
        } catch (BiffException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        settings.setSheet();

        settings.setHeaderRow(3);

        settings.setColumnProductName(4);
        settings.setColumnManufacturer(2);
        settings.setColumnProductModel(3);
        settings.setColumnProductPrice(5);
        settings.setColumnWarranty(6);
        settings.setColumnShortDescription(11);

        currentRow =  settings.getHeaderRow();
    }

    private TDBProductProvider(){
    }

    public static TDBProductProvider getInstance(){
        return tdbProductProvider;
    }

    @Override
    protected String getCategoryNameOfRow(int row) {
        return settings.getSheet().getRow(row)[1].getContents();
    }

    @Override
    protected boolean isCategory(int row) {
        if (getRow(row)[1].getContents().equals(getRow(row)[4].getContents()))
            return true;

        return false;
    }

    protected boolean isLastProduct() {
        if (getNextProductRow()[0].getContents().equals("     КУРСИ")){
            return true;
        }
        return false;
    }

    @Override
    protected String findProductModel(Cell[] currentRow) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected String findProductManufacturer(Cell[] currentRow) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected boolean getProductAvailability(Cell[] currentRow) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Product nextProduct() {
        Product product = new Product();
        product.categoryName = currentCategory;
        product.manufacturer = getProductManufacturer(getCurrentRow());
        product.productModel = getProductModel(getCurrentRow());
        product.productName = getCurrentRow()[settings.getColumnProductName()].getContents();
        product.productPrice = getCurrentRow()[settings.getColumnProductPrice()].getContents();
        product.warranty = getCurrentRow()[settings.getColumnWarranty()].getContents();
        product.shortDescription = getCurrentRow()[settings.getColumnShortDescription()].getContents();
        return product;

    }
}
