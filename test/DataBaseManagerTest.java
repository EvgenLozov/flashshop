import com.flashshop.managers.DataBaseManager;
import com.flashshop.managers.NotBlockingDataBaseManager;
import org.junit.Test;

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
}
