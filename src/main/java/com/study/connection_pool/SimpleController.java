package com.study.connection_pool;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class SimpleController {

    public SimpleController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    private final DatabaseService databaseService;

    @GetMapping(path = "/getTestData")
    public String hello() throws SQLException, InterruptedException {
        // TODO 1: call DatabaseService to get the testData
        databaseService.getTestData();
        return "Hello World";
    }
}
