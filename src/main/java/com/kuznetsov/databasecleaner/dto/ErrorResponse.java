package com.kuznetsov.databasecleaner.dto;

import java.time.Instant;

public record ErrorResponse(String message, Instant time) { }
