package com.kuznetsov.databasecleaner.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class InformationSchemaRepositoryImpl implements InformationSchemaRepository {

    private static final String GET_TABLES_QUERY =
            "SELECT table_schema || '.' || table_name " +
                    "FROM information_schema.tables " +
                    "WHERE table_type = 'BASE TABLE' " +
                    "AND table_schema NOT IN ('pg_catalog', 'information_schema') " +
                    "AND table_name NOT IN ('databasechangelog', 'databasechangeloglock')";
    private final JdbcTemplate jdbc;

    @Override
    public Set<String> getAllTables() {
        return new HashSet<>(jdbc.queryForList(
                GET_TABLES_QUERY,
                String.class
        ));
    }
}

