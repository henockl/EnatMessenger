package org.sis.ancmessaging.server.dao;

import org.sis.ancmessaging.server.domain.AppSetting;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * Author: Henock
 * Date: 10/24/11
 * Time: 8:28 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Transactional
public class AppSettingDaoImpl extends BaseDao implements AppSettingDao {

    @Override
    public AppSetting getSetting() {
        return (AppSetting) getSession().createCriteria(AppSetting.class)
                                   .uniqueResult();

    }
}
