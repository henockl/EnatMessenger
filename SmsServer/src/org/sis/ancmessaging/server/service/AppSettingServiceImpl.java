package org.sis.ancmessaging.server.service;

import org.sis.ancmessaging.server.dao.AppSettingDao;
import org.sis.ancmessaging.server.domain.AppSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Author: Henock
 * Date: 10/24/11
 * Time: 8:33 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AppSettingServiceImpl implements AppSettingService {
    @Autowired
    private AppSettingDao appSettingDao;
    @Override
    public AppSetting getSetting() {
        return appSettingDao.getSetting();
    }
}
