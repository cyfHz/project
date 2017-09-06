package com.kingmon.project.webservice.common.token.repository;

import java.io.Serializable;
import java.util.Collection;
import com.kingmon.project.webservice.common.token.AuthToken;

public interface ITokenRepository {
    void saveAuthToken(AuthToken authToken);

    
    void deleteAuthToken(Serializable tokenId);

  
    AuthToken getAuthToken(Serializable tokenId);

    Collection<AuthToken> getAllAuthTokens();
}
