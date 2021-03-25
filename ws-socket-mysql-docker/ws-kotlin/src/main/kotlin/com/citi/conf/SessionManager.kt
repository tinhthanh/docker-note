package com.citi.conf

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.logging.Logger;

@Component
class SessionManager(val db: JdbcTemplate) {
    val logger: Logger = Logger.getLogger(SessionManager::class.java.simpleName)
    val sessions: ConcurrentHashMap<String, WSSession> = ConcurrentHashMap()
    val lastMessages: LoadingCache<String, String> = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.DAYS).build(MessageLoader(db))

    class MessageLoader(private val db: JdbcTemplate) : CacheLoader<String, String>() {
        override fun load(key: String): String {
            try {
                return db.queryForObject("SELECT message FROM websocket_message WHERE destination =?", arrayOf(key)) { rs, _ -> rs.getString("message") }!!
            } catch (x: Exception) {

            }
            return "[]"
        }

    }
}