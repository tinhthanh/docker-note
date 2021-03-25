package com.citi.conf

import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class DataDispatcherService(private val messagingTemplate: SimpMessageSendingOperations,
                            private val sessionManager: SessionManager) {

    fun dispatch(destination: String, data: String) {
        val sessions = sessionManager.sessions.values.stream().filter { session -> session.destinations.contains(destination) }.collect(Collectors.toList())
        if (destination.startsWith("/user/")) {
            val dest = destination.substring("/user/".length - 1)
            sessions.forEach { messagingTemplate.convertAndSendToUser(it.getSessionId(), dest, data, createHeaders(it.getSessionId())) }
        } else {
            messagingTemplate.convertAndSend(destination, data)
            sessions.forEach { it.lastSendTimestamp = System.currentTimeMillis() }
        }
    }

    private fun createHeaders(sessionId: String): MessageHeaders {
        val headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE)
        headerAccessor.sessionId = sessionId
        headerAccessor.setLeaveMutable(true)
        return headerAccessor.messageHeaders
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 5000)
    fun sendKeepaliveMessage() {
        sessionManager.sessions.values.filter { it.destinations.isNotEmpty() && System.currentTimeMillis() - it.lastSendTimestamp > 30_000 }
                .forEach {
                    it.destinations.forEach { dest -> messagingTemplate.convertAndSend(dest, "p") }
                    it.lastSendTimestamp = System.currentTimeMillis()
                }
    }
}