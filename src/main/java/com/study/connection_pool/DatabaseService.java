package com.study.connection_pool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class DatabaseService {

    @Autowired
    private SimpleConnectionPool simpleConnectionPool;

    public void getTestData() throws SQLException, InterruptedException {
        Connection con = simpleConnectionPool.borrowConnection();

        try {
            String query = "SELECT 1";
            try(Statement statement = con.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    while(resultSet.next()) {
                        System.out.println("Success! Result: " + resultSet.getInt(1));
                    }
                }
            }
        } finally {
            simpleConnectionPool.returnConnection(con);
        }
    }
}
