package com.kuznetsov.databasecleaner.controller;

import com.kuznetsov.databasecleaner.dto.CleanupRequest;
import com.kuznetsov.databasecleaner.service.CleanupService;
import com.kuznetsov.databasecleaner.validator.DatabaseDataValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cleaner")
@RequiredArgsConstructor
public class CleanerController {

    private final CleanupService cleanupService;
    private final DatabaseDataValidator databaseDataValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(databaseDataValidator);
    }

    @PostMapping("/cleanup")
    public ResponseEntity<?> cleanup(@Valid @RequestBody CleanupRequest cleanupRequest) {
        cleanupService.cleanup(cleanupRequest);
        return ResponseEntity.accepted().build();
    }
}
