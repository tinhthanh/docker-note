package com.citi.conf

import org.apache.commons.lang3.StringUtils
import org.springframework.context.ApplicationListener
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionSubscribeEvent

@Component
class SessionSubscribeEventListener(private val sessionManager: SessionManager) : ApplicationListener<SessionSubscribeEvent> {
    override fun onApplicationEvent(sessionSubscribeEvent: SessionSubscribeEvent) {
        val headerAccessor = StompHeaderAccessor.wrap(sessionSubscribeEvent.message)
        val destination = headerAccessor.destination
        val wsSession = sessionManager.sessions[headerAccessor.sessionId]
        if (StringUtils.isNotBlank(destination)) {
            wsSession!!.addDestination(destination!!.intern())
        }
    }
}