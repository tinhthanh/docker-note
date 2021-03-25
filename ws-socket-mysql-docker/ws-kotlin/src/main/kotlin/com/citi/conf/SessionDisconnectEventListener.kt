package com.citi.conf

import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionDisconnectEvent

@Component
class SessionDisconnectEventListener(private val sessionManager: SessionManager) : ApplicationListener<SessionDisconnectEvent> {
    override fun onApplicationEvent(sessionDisconnectEvent: SessionDisconnectEvent) {
        sessionManager.sessions.entries.removeIf { it.key == sessionDisconnectEvent.sessionId }
    }
}