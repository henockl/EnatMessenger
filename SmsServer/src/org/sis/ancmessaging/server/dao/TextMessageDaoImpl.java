package org.sis.ancmessaging.server.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.sis.ancmessaging.server.domain.TextMessage;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * Author: Henock
 * Date: 1/30/12
 * Time: 5:27 PM
 * To change this template use File | Settings | File Templates.
 */

@Repository
@Transactional
public class TextMessageDaoImpl extends BaseDao implements TextMessageDao {
    @Override
    public void save(TextMessage textMessage) {
        getSession().save(textMessage);
    }

    @Override
    public TextMessage getBySmscCodeAndRecipient(int smscCode, String recipient) {
        Criteria criteria = getSession().createCriteria(TextMessage.class)
                                        .add(Restrictions.eq("smscCode", smscCode))
                                        .add(Restrictions.eq("recipient", recipient))
                                        .add(Restrictions.ne("status", "A"));
                                        
        return (TextMessage) criteria.uniqueResult();
    }

    @Override
    public void changeStatus(TextMessage textMessage, String newStatus) {
        TextMessage oldMessage = (TextMessage) getSession().get(TextMessage.class, textMessage.getMessageId());
        oldMessage.setStatus(newStatus);
        getSession().update(oldMessage);
    }

    @Override
    public TextMessage getMessageInProgress(long seqId) {
        Criteria criteria = getSession().createCriteria(TextMessage.class)
                                        .add(Restrictions.eq("seqId", seqId))
                                        .add(Restrictions.eq("status", "P"));
        return (TextMessage) criteria.uniqueResult();
    }
}
