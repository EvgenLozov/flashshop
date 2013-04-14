package excel;

import jxl.Cell;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 12.04.13
 * Time: 13:37
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractProductProvider implements ProductProvider{
    protected ExcelProductProviderSettings settings = new ExcelProductProviderSettings();

    protected  int currentRow;
    protected String currentCategory;


    protected abstract String getCategoryNameOfRow(int row);

    // визначає, чи являється рядок, номер якого передаємо у параметрі, категорією
    protected abstract boolean isCategory(int row);

    protected abstract boolean isLastProduct();

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
