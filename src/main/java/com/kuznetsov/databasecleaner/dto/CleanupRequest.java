package com.kuznetsov.databasecleaner.dto;

import jakarta.validation.constraints.*;

import java.time.OffsetDateTime;

public record CleanupRequest(
        @NotBlank String schemaName,
        @NotBlank String tableName,
        @NotBlank String dateTimeColumn,
        @NotNull OffsetDateTime deleteBefore,
        @Min(1000) @Max(100_000) Integer batchSize,
        @Min(0) @Max(300) Integer sleepMillis) { }

