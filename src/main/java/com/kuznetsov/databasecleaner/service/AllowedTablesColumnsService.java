package com.kuznetsov.databasecleaner.service;

import com.kuznetsov.databasecleaner.config.DatabaseCleanerProperties;
import com.kuznetsov.databasecleaner.repository.InformationSchemaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class AllowedTablesColumnsService {

    private final InformationSchemaRepository informationSchemaRepository;
    private final DatabaseCleanerProperties databaseCleanerProperties;
    private Set<String> allowedTables;

    @PostConstruct
    public void init() {
        this.allowedTables = informationSchemaRepository.getAllTables();
        allowedTables.forEach(t -> log.info("Allowed table: {}", t));
        databaseCleanerProperties.getAllowedDateTimeColumns()
                .forEach(c -> log.info("Allowed datetime column: {}", c));
    }

    public boolean isAllowedTable(String schema, String table) {
        return allowedTables.contains(schema + "." + table);
    }

    public boolean isAllowedColumn(String schema, String table, String column) {
        return databaseCleanerProperties.getAllowedDateTimeColumns()
                .contains(schema + "." + table + "." + column);
    }
}

