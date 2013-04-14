package com.flashshop.actualizator;

import com.flashshop.Logger;
import com.flashshop.actualizator.model.ActualizatorSettings;
import com.flashshop.managers.DataBaseManager;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 22.01.13
 * Time: 23:08
 * To change this template use File | Settings | File Templates.
 */
public class CachedActualizatorSettingsService implements ActualisatorSettingsService{
    private ActualisatorSettingsService service;

    volatile private ActualizatorSettings actualizatorSettings;

    private static CachedActualizatorSettingsService instance = new CachedActualizatorSettingsService(DataBaseManager.getInstance());

    private CachedActualizatorSettingsService(ActualisatorSettingsService actualisatorSettingsService) {
        service = actualisatorSettingsService;
    }

    public static CachedActualizatorSettingsService getInstance()
    {
        return instance;
    }

    @Override
    public ActualizatorSettings getActualizatorSettings() throws SQLException {
        if(actualizatorSettings == null)
        {
            actualizatorSettings = service.getActualizatorSettings();
        }
        return actualizatorSettings;
    }

    @Override
    public void setActualizatorSettings(final ActualizatorSettings actualizatorSettingsl) throws SQLException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    service.setActualizatorSettings(actualizatorSettingsl);
                    actualizatorSettings = actualizatorSettingsl;
                } catch (SQLException e) {
                    Logger.getInstance().addLog(e);
                }
            }
        }).start();
    }
}
