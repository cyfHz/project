
package com.kingmon.project.webservice.common.token.repository.impl;
import com.kingmon.base.util.seril.JdkSerialUtil;
import com.kingmon.common.redis.JedisManager;
import com.kingmon.project.webservice.common.token.AuthToken;
import com.kingmon.project.webservice.common.token.repository.ITokenRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.util.CollectionUtils;

public class RedisTokenRepositoryImpl implements ITokenRepository {
	
    private static final int SESSION_VAL_TIME_SPAN = 18000;
    private static final int DB_INDEX = 0;
    
    private static final String REDIS_WEBSERVICE_TOKEN = "rwt";
    
    private JedisManager jedisManager;
    
    private String addPrefix(Serializable tokenId) {
        return REDIS_WEBSERVICE_TOKEN+":" + tokenId;
    }
    
    @Override
    public void saveAuthToken(AuthToken authToken) {
        if (authToken == null || authToken.getTokenId()== null)
            throw new NullPointerException("authToken is empty");
        try {
            byte[] key = JdkSerialUtil.serialize(addPrefix( authToken.getTokenId() ));
            byte[] value = JdkSerialUtil.serialize(authToken);
            int expireTime = SESSION_VAL_TIME_SPAN;
            getJedisManager().saveValueByKey(DB_INDEX, key, value, expireTime);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    @Override
    public void deleteAuthToken(Serializable tokenId) {
        if (tokenId == null) {
            throw new NullPointerException("tokenId  is empty");
        }
        try {
            getJedisManager().deleteByKey(DB_INDEX,JdkSerialUtil.serialize( addPrefix( tokenId) ));
        } catch (Exception e) {
        }
    }
    @Override
    public AuthToken getAuthToken(Serializable tokenId) {
        if (tokenId == null){
        	 throw new NullPointerException("tokenId is empty");
        }
        AuthToken authToken = null;
        try {
            byte[] value = getJedisManager().getValueByKey(DB_INDEX, JdkSerialUtil.serialize( addPrefix(tokenId) ));
            authToken = JdkSerialUtil.deserialize(value, AuthToken.class);
        } catch (Exception e) {
        }
        return authToken;
    }

    @Override
    public Collection<AuthToken> getAllAuthTokens() {
           try {
        	   Set<byte[]> keys = getJedisManager().keys(DB_INDEX,"*"+REDIS_WEBSERVICE_TOKEN + "*"); 
        	   if (!CollectionUtils.isEmpty(keys)) {
                   List<AuthToken> values = new ArrayList<AuthToken>(keys.size());
                   for (byte[] key : keys) {
                	   AuthToken value = JdkSerialUtil.deserialize(getJedisManager().getValueByKey(DB_INDEX, key), AuthToken.class);
                       if (value != null) {
                           values.add(value);
                       }
                   }
                   return Collections.unmodifiableList(values);
               } else {
                   return Collections.emptyList();
               }
           } catch (Exception t) {
           }
           return Collections.emptyList();
    }

    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }
}
