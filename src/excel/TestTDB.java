package excel;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 26.03.13
 * Time: 17:49
 * To change this template use File | Settings | File Templates.
 */
public class TestTDB {
    public static void main(String[] args) {
//        TDBProductProvider tdbProductProvider = TDBProductProvider.getInstance();
//        while (tdbProductProvider.hasNextProduct())
//            System.out.println(tdbProductProvider.nextProduct());
//        System.out.println("Next price");

        KpiServiceProductProvider kpiServiceProductProvider = KpiServiceProductProvider.getInstance();
        while (kpiServiceProductProvider.hasNextProduct())
            System.out.println(kpiServiceProductProvider.nextProduct());
    }
}
