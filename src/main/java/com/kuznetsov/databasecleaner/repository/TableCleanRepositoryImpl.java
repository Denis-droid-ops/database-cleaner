package com.kuznetsov.databasecleaner.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
@RequiredArgsConstructor
public class TableCleanRepositoryImpl implements TableCleanRepository {

    private static final String CALL_CLEANUP_PROCEDURE_QUERY = "CALL cleanup_table_by_datetime(?, ?, ?, ?, ?, ?)";
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void executeCleanup(String schemaName, String tableName, String dateTimeColumn,
                               OffsetDateTime deleteBefore, int batchSize, int sleepMillis) {
        jdbcTemplate.update(CALL_CLEANUP_PROCEDURE_QUERY, schemaName, tableName,
                dateTimeColumn, deleteBefore, batchSize, sleepMillis);
    }
}