package com.citi.conf

import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor

class CustomHandshakeInterceptor : HandshakeInterceptor {
    override fun beforeHandshake(request: ServerHttpRequest, response: ServerHttpResponse, handler: WebSocketHandler, attributes: MutableMap<String, Any>): Boolean {
        attributes["user"] = currentUser(request)
        return true
    }

    override fun afterHandshake(request: ServerHttpRequest, response: ServerHttpResponse, handler: WebSocketHandler, exception: Exception?) {
    }

    private fun currentUser(request: ServerHttpRequest): Long {
        return 0
    }

}