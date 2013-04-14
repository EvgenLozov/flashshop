package com.flashshop.pricelist;

import com.flashshop.pricelist.model.PriceListSettings;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 27.01.13
 * Time: 23:15
 * To change this template use File | Settings | File Templates.
 */
public interface PriceListSettingsService {
    public PriceListSettings getPriceListSettings(Integer settingsId) throws SQLException;
    public void setPriceListSettings(PriceListSettings priceListSettings) throws SQLException;
}
