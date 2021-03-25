package com.citi.conf

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration
import org.springframework.web.socket.handler.WebSocketHandlerDecorator

@Configuration
@EnableWebSocketMessageBroker
class WebsocketConfig(private val sessionManager: SessionManager) : WebSocketMessageBrokerConfigurer {

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*")
    }

    override fun configureWebSocketTransport(registry: WebSocketTransportRegistration) {
        registry.addDecoratorFactory() {
            CustomWebSocketHandlerDecorator(sessionManager, it)
        }
    }

    class CustomWebSocketHandlerDecorator(private val sessionManager: SessionManager, delegate: WebSocketHandler) : WebSocketHandlerDecorator(delegate) {
        override fun afterConnectionEstablished(session: WebSocketSession) {
            super.afterConnectionEstablished(session)
            sessionManager.sessions.putIfAbsent(session.id, WSSession(session))
        }
    }
}