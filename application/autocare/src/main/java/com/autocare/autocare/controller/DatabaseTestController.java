package com.autocare.autocare.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseTestController {

    private final JdbcTemplate jdbcTemplate;
    private final MongoTemplate mongoTemplate;

    public DatabaseTestController(
            JdbcTemplate jdbcTemplate,
            MongoTemplate mongoTemplate) {

        this.jdbcTemplate = jdbcTemplate;
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/test-databases")
    public Map<String, Object> testDatabases() {

        Map<String, Object> result = new LinkedHashMap<>();

        try {
            String oracleUser = jdbcTemplate.queryForObject(
                    "SELECT USER FROM dual",
                    String.class);

            Long customerCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM CUSTOMER",
                    Long.class);

            result.put("Oracle Status", "CONNECTED");
            result.put("Oracle User", oracleUser);
            result.put("Customer Count", customerCount);

        } catch (Exception e) {
            result.put("Oracle Status", "FAILED");
            result.put("Oracle Error", e.getMessage());
        }

        try {
            Set<String> collections = mongoTemplate.getCollectionNames();

            result.put("MongoDB Status", "CONNECTED");
            result.put("MongoDB Database", mongoTemplate.getDb().getName());
            result.put("MongoDB Collections", collections);

        } catch (Exception e) {
            result.put("MongoDB Status", "FAILED");
            result.put("MongoDB Error", e.getMessage());
        }

        return result;
    }
}