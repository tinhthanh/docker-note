package com.citi.web

import com.citi.conf.SessionManager
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.stereotype.Controller

@Controller
class WebsocketController(val sessionManager: SessionManager) {

    @SubscribeMapping("/**")
    fun subscribe(headerAccessor: SimpMessageHeaderAccessor): String {
        return sessionManager.lastMessages[headerAccessor.destination!!]
    }
}