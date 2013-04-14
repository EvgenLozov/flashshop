import com.flashshop.Product;
import com.flashshop.managers.DataBaseManager;
import com.flashshop.managers.NotBlockingDataBaseManager;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 08.02.13
 * Time: 20:12
 * To change this template use File | Settings | File Templates.
 */
public class DataBaseManagerTest {
    @Test
    public void testChangePriceBySku() throws Exception {
        DataBaseManager dataBaseManager = DataBaseManager.getInstance();
        dataBaseManager.changePriceBySku("40606","135");
        dataBaseManager.changeAvailabilityBySku("40606",true);

    }

    @Test
    public void testFuture() throws Exception {
        DataBaseManager dbm = DataBaseManager.getInstance();
        NotBlockingDataBaseManager nbdbm = NotBlockingDataBaseManager.getInstance();

        long start = System.currentTimeMillis();
        dbm.getProductSkuForCategory("Флешки");
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        nbdbm.getProductSkuForCategory("Флешки");
        System.out.println(System.currentTimeMillis() - start);

    }

    @Test

    public void addTradePositionTest() throws SQLException {
        DataBaseManager dataBaseManager = DataBaseManager.getInstance();
        Product product = new Product();
        product.productModel = "someModel";
        product.providerId = 1;
        product.categoryName = "Флешки";
        product.manufacturer = "Transcend";
        product.productName = "Test Add Trade ";
        product.productPrice = "23";
        product.productSku = "54354434";

        dataBaseManager.addTradePosition(product);
    }

    @Test
    public void updateProduct() throws SQLException {
        DataBaseManager.getInstance().updateProduct("rtdfgdg33",2,"100","5");
    }
}
