package excel;

import com.flashshop.providerintegration.ProductProvider;
import jxl.Cell;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 12.04.13
 * Time: 13:37
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractProductProvider implements ProductProvider {
    protected ExcelProductProviderSettings settings = new ExcelProductProviderSettings();

    protected  int currentRow;
    protected String currentCategory;

    protected abstract String getCategoryNameOfRow(int row);

    // визначає, чи являється рядок, номер якого передаємо у параметрі, категорією
    protected abstract boolean isCategory(int row);

    protected abstract boolean isLastProduct();

    protected abstract String findProductModel(Cell[] currentRow);

    protected abstract String findProductManufacturer(Cell[] currentRow);

    protected abstract boolean getProductAvailability(Cell[] currentRow);

    protected Cell[] getRow(int row){
        return settings.getSheet().getRow(row);
    }

    protected Cell[] getCurrentRow() {
        return settings.getSheet().getRow(currentRow);
    }

    // повертає масив ячеек наступного рядка
    protected Cell[] getNextProductRow(){
       return settings.getSheet().getRow(currentRow+1);

    }

    protected String getProductModel(Cell[] productRow){
        if (settings.getColumnProductModel() != 0)
            return productRow[settings.getColumnProductModel()].getContents();
        else
            return findProductModel(productRow);
    }

    protected String getProductManufacturer(Cell[] productRow){
        if (settings.getColumnManufacturer() != 0)
            return productRow[settings.getColumnManufacturer()].getContents();
        else
            return findProductManufacturer(productRow);
    }

    @Override
    public boolean hasNextProduct() {
        if (isLastProduct())
            return false;
        else {
            while (isCategory(currentRow)){
                currentCategory = getCategoryNameOfRow(currentRow);
                System.out.println("Set new category:" + currentCategory);
                currentRow++;
            }
            currentRow++;
        }
        return true;
    }
}
