package org.example.repository.impl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public class DataBaseContainer {

    @Container
    public static PostgreSQLContainer<?> container;

    @BeforeAll
    public static void setUp() {
        container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.3"))
                .withDatabaseName("Music")
                .withUsername("postgres")
                .withInitScript("db-migration.sql")
                .withPassword("111");
        container.start();

    }


    @AfterAll
    public static void tearDown() {
        if (container != null) {
            container.stop();
        }
    }
}
