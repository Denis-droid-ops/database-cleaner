package com.kuznetsov.databasecleaner.validator;

import com.kuznetsov.databasecleaner.dto.CleanupRequest;
import com.kuznetsov.databasecleaner.service.AllowedTablesColumnsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class DatabaseDataValidator implements Validator {

    private final AllowedTablesColumnsService allowedTablesColumnsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return CleanupRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CleanupRequest req = (CleanupRequest) target;

        if (!allowedTablesColumnsService.isAllowedTable(req.schemaName(), req.tableName())) {
            errors.rejectValue("tableName",
                    "invalid.table",
                    "Table " + req.schemaName() + "." + req.tableName() + " is not allowed");
            return;
        }

        if (!allowedTablesColumnsService.isAllowedColumn(req.schemaName(), req.tableName(), req.dateTimeColumn())) {
            errors.rejectValue("dateTimeColumn",
                    "invalid.column",
                    "Column " + req.dateTimeColumn() + " is not allowed in table " +
                            req.schemaName() + "." + req.tableName());
        }
    }
}
