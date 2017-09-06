
package com.kingmon.common.session.repository.impl;

import com.kingmon.base.util.seril.JdkSerialUtil;
import com.kingmon.common.redis.JedisManager;
import com.kingmon.common.session.SessionUser;
import com.kingmon.common.session.repository.ISessionUserRepository;
import java.io.Serializable;

public class RedisSessionUserRepository implements ISessionUserRepository {
	
    private static final int SESSION_VAL_TIME_SPAN = 18000;
    private static final int DB_INDEX = 0;
    
    private JedisManager jedisManager;
    
    @Override
    public void saveSessionUser(SessionUser sessionUser) {
        if (sessionUser == null || sessionUser.getSessionId()== null)
            throw new NullPointerException("session is empty");
        try {
            byte[] key = JdkSerialUtil.serialize(sessionUser.getSessionId());
            byte[] value = JdkSerialUtil.serialize(sessionUser);
            int expireTime = SESSION_VAL_TIME_SPAN;
            getJedisManager().saveValueByKey(DB_INDEX, key, value, expireTime);
        } catch (Exception e) {
        	e.printStackTrace();
        	//LogUtil.genErrorLog(RedisSessionUserRepository.class,"saveSession",e.getMessage(), e);
        }
    }

    @Override
    public void deleteSessionUser(Serializable sessionId) {
        if (sessionId == null) {
            throw new NullPointerException("session id is empty");
        }
        try {
            getJedisManager().deleteByKey(DB_INDEX,JdkSerialUtil.serialize(sessionId));
        } catch (Exception e) {
        	//LogUtil.genErrorLog(RedisSessionUserRepository.class,"deleteSession",e.getMessage(), e);
        }
    }
    @Override
    public SessionUser getSessionUser(Serializable sessionId) {
        if (sessionId == null){
        	 throw new NullPointerException("session id is empty");
        }
        SessionUser sessionUser = null;
        try {
            byte[] value = getJedisManager().getValueByKey(DB_INDEX, JdkSerialUtil.serialize(sessionId));
            sessionUser = JdkSerialUtil.deserialize(value, SessionUser.class);
        } catch (Exception e) {
        	//LogUtil.genErrorLog(RedisSessionUserRepository.class,"getSession",e.getMessage(), e);
        }
        return sessionUser;
    }

//    @Override
//    public Collection<SessionUser> getAllSessionUsers() {
////    	   System.out.println("get all sessions");
//           try {//shiro-session
//        	   Set<byte[]> keys = getJedisManager().keys(DB_INDEX,"*"+REDIS_SHIRO_SESSION + "*"); 
//        	   if (!CollectionUtils.isEmpty(keys)) {
//                   List<Session> values = new ArrayList<Session>(keys.size());
//                   for (byte[] key : keys) {
//                       Session value = SerializeUtil.deserialize(getJedisManager().getValueByKey(DB_INDEX, key), Session.class);
////                       System.out.println("get all sessions---活动session---"+ value.getId());
//                       if (value != null) {
//                           values.add(value);
//                       }
//                   }
//                   return Collections.unmodifiableList(values);
//               } else {
//                   return Collections.emptyList();
//               }
//           } catch (Exception t) {
//        	   LogUtil.genErrorLog(RedisSessionUserRepository.class,"getSession",t.getMessage(), t);
//               throw new CacheException(t);
//           }
//    }

    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }
}
