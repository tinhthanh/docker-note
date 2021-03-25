package com.citi.web

import com.citi.conf.DataDispatcherService
import com.citi.conf.SessionManager
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class ReceiverController(val dispatcherService: DataDispatcherService,
                         val sessionManager: SessionManager,
                         val db: JdbcTemplate) {
    var lastMessageQueue = HashMap<String, String>()
    @PostMapping("accept")
    fun receiveData(destination: String, @RequestBody data: String): String {
        sessionManager.lastMessages.put(destination, data)
        dispatcherService.dispatch(destination, data)
        lastMessageQueue[destination] = data
        return "ok"
    }

    @Scheduled(initialDelay = 5_000, fixedDelay = 30_000)
    fun storeLastMessageToDB() {
        val messageMap = lastMessageQueue
        lastMessageQueue = HashMap()
        db.batchUpdate("INSERT INTO websocket_message (destination, message) VALUES  (?,?) ON DUPLICATE KEY UPDATE message = VALUES (message)", messageMap.entries.map { arrayOf(it.key, it.value) })
    }
}