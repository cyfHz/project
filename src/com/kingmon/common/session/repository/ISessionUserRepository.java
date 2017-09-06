package com.kingmon.common.session.repository;

import java.io.Serializable;
import com.kingmon.common.session.SessionUser;

public interface ISessionUserRepository {

	/**
	 * 存储sessionUser
	 * @param sessionUser
	 */
    void saveSessionUser(SessionUser sessionUser);

    /**
     * 删除sessionUser
     * @param sessionId
     */
    void deleteSessionUser(Serializable sessionId);

    /**
     * 获取SessionUser
     * @param sessionId
     * @return
     */
    SessionUser getSessionUser(Serializable sessionId);

//    /**
//     * 获取所有SessionUser
//     * @return
//     */
//    Collection<SessionUser> getAllSessionUsers();
}
