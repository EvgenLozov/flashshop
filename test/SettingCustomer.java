import com.flashshop.actualizator.ActualisatorSettingsService;
import com.flashshop.actualizator.CachedActualizatorSettingsService;
import com.flashshop.actualizator.model.ActualizatorSettings;
import com.flashshop.managers.DataBaseManager;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 22.01.13
 * Time: 23:39
 * To change this template use File | Settings | File Templates.
 */
public class SettingCustomer {

    public static void main(String[] args) {
        SettingCustomer settingCustomer = new SettingCustomer(DataBaseManager.getInstance());
        // todo use this object
        SettingCustomer settingCustomer2 = new SettingCustomer(CachedActualizatorSettingsService.getInstance());

    }

    public SettingCustomer(ActualisatorSettingsService settingsService) {
        this.settingsService = settingsService;
    }

    private ActualisatorSettingsService settingsService;

    public void doSome() throws SQLException {
        ActualizatorSettings settings = settingsService.getActualizatorSettings();

        // todo some do
    }

    public void soSomeOther() throws SQLException {
        //todo

        ActualizatorSettings settings = new ActualizatorSettings();


        settingsService.setActualizatorSettings(settings);
    }

}
