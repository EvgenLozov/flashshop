package controllers;

import com.flashshop.Logger;
import com.flashshop.actualizator.ActualisatorSettingsService;
import com.flashshop.actualizator.CachedActualizatorSettingsService;
import com.flashshop.actualizator.model.ActualizatorSettings;
import com.flashshop.managers.DataBaseManager;
import com.flashshop.model.CachedPublishedCategories;
import com.flashshop.model.Category;
import com.flashshop.model.PublishedCategoriesService;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 11.01.13
 * Time: 22:30
 * To change this template use File | Settings | File Templates.
 */
public class FlashShopController extends Controller {

    public static Result getCategories() throws SQLException {
        PublishedCategoriesService service = new CachedPublishedCategories(DataBaseManager.getInstance());

        List<Category> categories = service.getPublishedCategories();

        return ok(Json.toJson(categories));
    }

    public static Result getCategoriesWithMargin () throws SQLException {
        ActualisatorSettingsService actualisatorSettingsService = CachedActualizatorSettingsService.getInstance();
        ActualizatorSettings actualizatorSettings = actualisatorSettingsService.getActualizatorSettings();

        return ok(Json.toJson(actualizatorSettings));
    }

    public static Result index(){
        return ok(index.render());
    }

    public static Result getLogFile(){
        response().setHeader("Content-Disposition", "attachment; filename=logger.txt");

        return ok(Logger.getInstance().getLoggerFile());
    }

}
