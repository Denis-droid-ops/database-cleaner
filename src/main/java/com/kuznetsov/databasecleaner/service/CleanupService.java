package com.kuznetsov.databasecleaner.service;

import com.kuznetsov.databasecleaner.dto.CleanupRequest;

public interface CleanupService {
    void cleanup(CleanupRequest cleanupRequest);
}
