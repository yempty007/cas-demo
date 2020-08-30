package com.example.cas.client.b.util;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description session token 关联存储
 * @Author yempty
 * @Date 2020/8/30 19:12
 * @Version 1.0
 */
public class SessionMappingStorage {
    /**
     * session Map，key sessionId，value session
     */
    private final Map<String, HttpSession> MANAGED_SESSIONS = new HashMap();
    /**
     * token Map，key token，value sessionId
     */
    private final Map<String, String> ID_TO_SESSION_KEY_MAPPING = new HashMap();

    private static final Log log = LogFactory.get();

    public SessionMappingStorage() {
    }

    public synchronized void addSessionById(String mappingId, HttpSession session) {
        this.ID_TO_SESSION_KEY_MAPPING.put(session.getId(), mappingId);
        this.MANAGED_SESSIONS.put(mappingId, session);
    }

    public synchronized void removeBySessionById(String sessionId) {
        log.debug("Attempting to remove Session=[{}]", sessionId);
        String key = (String) this.ID_TO_SESSION_KEY_MAPPING.get(sessionId);
        if (log.isDebugEnabled()) {
            if (key != null) {
                log.debug("Found mapping for session.  Session Removed.");
            } else {
                log.debug("No mapping for session found.  Ignoring.");
            }
        }

        this.MANAGED_SESSIONS.remove(key);
        this.ID_TO_SESSION_KEY_MAPPING.remove(sessionId);
    }

    public synchronized HttpSession removeSessionByMappingId(String mappingId) {
        HttpSession session = this.MANAGED_SESSIONS.get(mappingId);
        if (session != null) {
            this.removeBySessionById(session.getId());
        }

        return session;
    }
}
