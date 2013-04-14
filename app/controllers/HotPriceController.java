package controllers;

import com.flashshop.Logger;
import com.flashshop.Performers;
import com.flashshop.WorkRoom;
import com.flashshop.actualizator.ActualisatorSettingsService;
import com.flashshop.actualizator.CachedActualizatorSettingsService;
import com.flashshop.actualizator.model.ActualizatorSettings;
import com.flashshop.actualizator.model.CategoryWithMargin;
import com.flashshop.executor.PeriodicPerformer;
import com.flashshop.executor.Task;
import com.flashshop.hotprice.PriceListCreator;
import com.flashshop.managers.DataBaseManager;
import com.flashshop.model.Category;
import com.flashshop.pricelist.CachedPriceListSettingsService;
import com.flashshop.pricelist.PriceListSettingsService;
import com.flashshop.pricelist.model.PriceListSettings;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.xml.sax.SAXException;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 09.01.13
 * Time: 22:35
 * To change this template use File | Settings | File Templates.
 */
public class HotPriceController extends Controller {
    static HashMap<Integer,PeriodicPerformer> performers = Performers.getPerformer();


    private static final PeriodicPerformer performer = new PeriodicPerformer(new Task() {
        volatile PriceListCreator priceListCreator = PriceListCreator.getInstance();

        @Override
        public void execute() {
                synchronized (WorkRoom.getInstance()){
                    try {
                        priceListCreator.refreshPrice();
                    } catch (Exception e) {
                        Logger.getInstance().addLog(e);
                    }
                }
        }
    });

    private PeriodicPerformer getPeriodicPerformer(Long id){
        return  null;
    }

    public static Result start(String id) {
        if (!performers.get(Integer.parseInt(id)).isRunning()) {
            performers.get(Integer.parseInt(id)).start();
            return ok("Обновление прайс-листа началось.");
        } else
            return ok("Обновление прайс-листа уже выполняется.");
    }

    public static Result stop(String id){
        performers.get(Integer.parseInt(id)).stop();
        return ok("Отключить обновление прайс-листа. ");
    }

    public static Result getPrice() throws Exception {
        response().setHeader("Content-Disposition", "attachment; filename=priceList.xml");

        return ok(PriceListCreator.getInstance().getPriceList());
    }

    public static Result setInterval(Long period, String id){
        performers.get(Integer.parseInt(id)).setPeriod(period.intValue());
        return ok("Установлен новый интервал обновления прайс-листа: "+period);
    }

    public static Result setCategories() throws SQLException {
        JsonNode body = request().body().asJson();
        ArrayList selectedCategories = Json.fromJson(body.get("checkedCategories"), ArrayList.class);
        Integer settingsId = Json.fromJson(body.get("settingId"), Integer.class);
        ArrayList<Category> categories = new ArrayList<Category>();
        for(Object obj: selectedCategories)
        {
            Map map = (Map) obj;
            Category category = new Category();
            category.setCategoryId(Integer.parseInt((String) map.get("id")));
            category.setCategoryName((String) map.get("name"));
            categories.add(category);
        }
        PriceListSettingsService priceListSettingsService = CachedPriceListSettingsService.getInstance();
        PriceListSettings priceListSettings = priceListSettingsService.getPriceListSettings(settingsId);
        priceListSettings.setSelectedCategory(categories);
        priceListSettingsService.setPriceListSettings(priceListSettings);

        return  ok();
    }

    public static Result getSelectedCategories(Long id) throws SQLException {
        PriceListSettingsService priceListSettingsService = CachedPriceListSettingsService.getInstance();
        PriceListSettings priceListSettings = priceListSettingsService.getPriceListSettings(id.intValue());

        return ok(Json.toJson(priceListSettings));
    }

    public static Result getAllPriceListSettings() throws SQLException {
        ArrayList<PriceListSettings> allPriceListSettings = DataBaseManager.getInstance().getAllPriceListSettings();
        return ok(Json.toJson(allPriceListSettings));
    }



}

