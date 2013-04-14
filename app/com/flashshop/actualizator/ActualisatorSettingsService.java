package com.flashshop.actualizator;

import com.flashshop.actualizator.model.ActualizatorSettings;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 22.01.13
 * Time: 23:03
 * To change this template use File | Settings | File Templates.
 */
public interface ActualisatorSettingsService {
    public ActualizatorSettings getActualizatorSettings() throws SQLException;
    public void setActualizatorSettings(ActualizatorSettings actualizatorSettings) throws SQLException;
}
