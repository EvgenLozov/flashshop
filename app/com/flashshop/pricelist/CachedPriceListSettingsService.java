package com.flashshop.pricelist;

import com.flashshop.Logger;
import com.flashshop.managers.DataBaseManager;
import com.flashshop.pricelist.model.PriceListSettings;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 28.01.13
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
public class CachedPriceListSettingsService implements PriceListSettingsService {
    private PriceListSettingsService service;

    volatile private PriceListSettings priceListSettings;

    private static CachedPriceListSettingsService ourInstance = new CachedPriceListSettingsService(DataBaseManager.getInstance());

    public static CachedPriceListSettingsService getInstance() {
        return ourInstance;
    }

    private CachedPriceListSettingsService(PriceListSettingsService priceListSettingsService) {
        service = priceListSettingsService;
    }

    @Override
    public PriceListSettings getPriceListSettings(Integer settingsId) throws SQLException {
        if ((priceListSettings == null) || (settingsId != priceListSettings.getId())){
            return service.getPriceListSettings(settingsId);
        }
        return priceListSettings;
    }


    @Override
    public void setPriceListSettings(final PriceListSettings priceListSettingsl) throws SQLException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    service.setPriceListSettings(priceListSettingsl);
                    priceListSettings = priceListSettingsl;
                } catch (SQLException e) {
                    Logger.getInstance().addLog(e);
                }
            }
        }).start();
    }
}
