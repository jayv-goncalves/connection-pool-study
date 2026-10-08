package com.study.connection_pool;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
public class SimpleConnectionPool {

    private static final int POOL_SIZE = 5;

    @Value("${db.url}")
    private String dbUrl;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    private BlockingQueue<Connection> pool;

    @PostConstruct
    public void init() throws SQLException {
        pool = new LinkedBlockingQueue<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.add(DriverManager.getConnection(dbUrl, username, password));
        }
        System.out.println("Pool initialized with " + pool.size() + " connections");
    }

    public Connection borrowConnection() throws InterruptedException {
        if(pool.isEmpty()) {
            System.out.println("Pool is empty! Operation blocked!");
            return null;
        }
        return pool.poll(10, TimeUnit.SECONDS);
    }

    public void returnConnection(Connection connection) {
        boolean result = pool.offer(connection);

        if(result) {
            System.out.println("Connection successfully returned!");
            return;
        }

        throw new RuntimeException("Connection returned to pool failed!");
    }

    @PreDestroy
    public void shutdown() {
        while (!pool.isEmpty()) {
            Connection con = pool.poll();

            if(con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}