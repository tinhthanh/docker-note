package com.citi.conf

import org.apache.commons.lang3.StringUtils
import org.springframework.context.ApplicationListener
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent
import java.util.*
import java.util.stream.Stream

class SessionUnsubscribeListener(private val sessionManager: SessionManager) : ApplicationListener<SessionUnsubscribeEvent> {
    override fun onApplicationEvent(sessionUnsubscribeEvent: SessionUnsubscribeEvent) {
        val headerAccessor = StompHeaderAccessor.wrap(sessionUnsubscribeEvent.message)
        Stream.of(headerAccessor.destination, headerAccessor.getHeader("simpSubscriptionId")!!.toString())
                .filter { StringUtils.isNotBlank(it) }
                .findFirst()
                .ifPresent { sessionManager.sessions[headerAccessor.sessionId]!!.removeDestination(it) }
    }
}