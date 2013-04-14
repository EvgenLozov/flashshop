import com.flashshop.Logger;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 27.01.13
 * Time: 19:29
 * To change this template use File | Settings | File Templates.
 */
public class LoggerTest {
    @Test
    public void testName() throws Exception {

        try {
            throw new Exception("Exception message");
        }
        catch (Exception e)
        {

            Logger.getInstance().addLog(e);
        }


    }
}
