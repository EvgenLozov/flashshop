package excel;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 17.03.13
 * Time: 18:47
 * To change this template use File | Settings | File Templates.
 */
public class ExcelProductProviderSettings {

    private Workbook workbook;
    private Sheet sheet;

    private int headerRow;

    private int columnCategoryName;
    private int columnManufacturer;
    private int columnProductName;
    private int columnProductSku;
    private boolean columnProductAvailability;
    private int columnProductPrice;
    private int columnShortDescription;
    private int columnDescription;
    private int columnWarranty;

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(String pathToPriceList) throws BiffException, IOException {
        this.workbook = Workbook.getWorkbook(new File(pathToPriceList));
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet() {
        sheet = workbook.getSheet(0);;
    }

    public void setSheet(int numberOfSheet) {
        sheet = workbook.getSheet(numberOfSheet);;
    }

    public int getHeaderRow() {
        return headerRow;
    }

    public void setHeaderRow(int headerRow) {
        this.headerRow = headerRow;
    }

    public int getColumnCategoryName() {
        return columnCategoryName;
    }

    public void setColumnCategoryName(int columnCategoryName) {
        this.columnCategoryName = columnCategoryName;
    }

    public int getColumnManufacturer() {
        return columnManufacturer;
    }

    public void setColumnManufacturer(int columnManufacturer) {
        this.columnManufacturer = columnManufacturer;
    }

    public int getColumnProductName() {
        return columnProductName;
    }

    public void setColumnProductName(int columnProductName) {
        this.columnProductName = columnProductName;
    }

    public int getColumnProductSku() {
        return columnProductSku;
    }

    public void setColumnProductSku(int columnProductSku) {
        this.columnProductSku = columnProductSku;
    }

    public boolean isColumnProductAvailability() {
        return columnProductAvailability;
    }

    public void setColumnProductAvailability(boolean columnProductAvailability) {
        this.columnProductAvailability = columnProductAvailability;
    }

    public int getColumnProductPrice() {
        return columnProductPrice;
    }

    public void setColumnProductPrice(int columnProductPrice) {
        this.columnProductPrice = columnProductPrice;
    }

    public int getColumnShortDescription() {
        return columnShortDescription;
    }

    public void setColumnShortDescription(int columnShortDescription) {
        this.columnShortDescription = columnShortDescription;
    }

    public int getColumnDescription() {
        return columnDescription;
    }

    public void setColumnDescription(int columnDescription) {
        this.columnDescription = columnDescription;
    }

    public int getColumnWarranty() {
        return columnWarranty;
    }

    public void setColumnWarranty(int columnWarranty) {
        this.columnWarranty = columnWarranty;
    }
}
