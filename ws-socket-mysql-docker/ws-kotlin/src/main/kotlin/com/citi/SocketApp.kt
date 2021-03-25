package com.citi

import com.citi.conf.CustomDispatcherServlet
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.DispatcherServlet

@SpringBootApplication
@EnableScheduling
@EnableAsync
class SocketApp

fun main(args: Array<String>) {
    runApplication<SocketApp>(*args)
}

@Bean
fun dispatcherServlet(): DispatcherServlet {
    return CustomDispatcherServlet()
}