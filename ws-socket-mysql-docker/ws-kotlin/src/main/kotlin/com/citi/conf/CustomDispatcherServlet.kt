package com.citi.conf

import org.springframework.web.servlet.DispatcherServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomDispatcherServlet : DispatcherServlet() {
    override fun doDispatch(request: HttpServletRequest, response: HttpServletResponse) {
        super.doDispatch(request, response)
        val location = response.getHeader("Sec-WebSocket-Location")
        if (location != null && location.isNotEmpty()) {
            val origin = response.getHeader("Origin")
            if (origin != null && origin.startsWith("https")) {
                response.setHeader("Sec-WebSocket-Location", location.replace("ws://", "wss://"))
            }
        }
    }
}