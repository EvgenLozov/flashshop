package controllers;

import com.flashshop.Logger;
import com.flashshop.WorkRoom;
import com.flashshop.actualizator.ActualisatorSettingsService;
import com.flashshop.actualizator.Actualizator;
import com.flashshop.actualizator.CachedActualizatorSettingsService;
import com.flashshop.actualizator.model.ActualizatorSettings;
import com.flashshop.actualizator.model.CategoryWithMargin;
import com.flashshop.executor.PeriodicPerformer;
import com.flashshop.executor.Task;
import org.codehaus.jackson.JsonNode;
import org.xml.sax.SAXException;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 16.01.13
 * Time: 21:39
 * To change this template use File | Settings | File Templates.
 */

public class ActualizatorController extends Controller {

    private static final PeriodicPerformer performer = new PeriodicPerformer (new Task(){
        volatile Actualizator actualizator = Actualizator.getInstance();

        @Override
        public void execute(){
            synchronized (WorkRoom.getInstance()){
                try {
                    actualizator.startActualization();
                } catch (SQLException e) {
                    Logger.getInstance().addLog(e);
                } catch (IOException e) {
                    Logger.getInstance().addLog(e);
                } catch (SAXException e) {
                    Logger.getInstance().addLog(e);
                } catch (ParserConfigurationException e) {
                    Logger.getInstance().addLog(e);
                }
            }
        }
    });

    public static Result start(){
        if (!performer.isRunning()) {
            performer.start();
            return ok("Обновление секретной информации на сайте начато...");
        } else
            return ok("Обновление секретной информации на сайте продолжается...");
    }

    public static Result stop(){
        performer.stop();
        return ok("Отключить обновление секретных данных на сайте!!! ");
    }

    public static Result setInterval(Long period){
        performer.setPeriod(period.intValue());
        return ok("Установлен новый интервал обновления информации: "+period);
    }

    public static Result setMargin() throws SQLException {
        JsonNode body = request().body().asJson();
        ArrayList margin = Json.fromJson(body, ArrayList.class);

        ArrayList<CategoryWithMargin> categories = new ArrayList<>();
        for(Object obj: margin)
        {
            Map map = (Map) obj;
            CategoryWithMargin category = new CategoryWithMargin();
            category.setCategoryId(Integer.parseInt((String) map.get("id")));
            category.setMargin(Integer.parseInt((String) map.get("margin")));
            category.setCategoryName((String) map.get("name"));
            categories.add(category);
        }

        ActualisatorSettingsService actualisatorSettingsService = CachedActualizatorSettingsService.getInstance();
        ActualizatorSettings actualizatorSettings = actualisatorSettingsService.getActualizatorSettings();
        actualizatorSettings.setCategories(categories);
        actualisatorSettingsService.setActualizatorSettings(actualizatorSettings);

        return ok("Установлены новые значения наценки");
    }

}
