package com.citi.conf

import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

class WSSession(val session: WebSocketSession,
                val destinations: MutableSet<String> = ConcurrentHashMap.newKeySet(),
                var lastSendTimestamp: Long = 0) {
    fun getSessionId(): String {
        return session.id
    }

    fun addDestination(destination: String) {
        destinations.add(destination)
    }

    fun removeDestination(destination: String) {
        this.destinations.remove(destination)
    }
}