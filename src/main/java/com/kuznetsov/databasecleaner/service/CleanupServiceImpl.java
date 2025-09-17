package com.kuznetsov.databasecleaner.service;

import com.kuznetsov.databasecleaner.dto.CleanupRequest;
import com.kuznetsov.databasecleaner.repository.TableCleanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class CleanupServiceImpl implements CleanupService {

    private final TableCleanRepository tableCleanRepository;

    public void cleanup(CleanupRequest cleanupRequest) {
        log.info("Старт процесса очистки таблицы {}.{} для колонки {}. " +
                "Параметры очистки: дата и время, до которого идет очистка - {}, " +
                "размер пакета - {} записей, пауза - {} мс",
                cleanupRequest.schemaName(), cleanupRequest.tableName(), cleanupRequest.dateTimeColumn(),
                cleanupRequest.deleteBefore(), cleanupRequest.batchSize(), cleanupRequest.sleepMillis());
        tableCleanRepository.executeCleanup(
                cleanupRequest.schemaName(),
                cleanupRequest.tableName(),
                cleanupRequest.dateTimeColumn(),
                cleanupRequest.deleteBefore(),
                cleanupRequest.batchSize(),
                cleanupRequest.sleepMillis()
        );
        log.info("Очистка для таблицы {}.{} завершена", cleanupRequest.schemaName(), cleanupRequest.tableName());
    }

}
