package com.kuznetsov.databasecleaner.repository;

import java.time.OffsetDateTime;

/**
 * Репозиторий для очистки таблиц от старых данных.
 */
public interface TableCleanRepository {

    /**
     * Запуск хранимой процедуры очистки таблицы.
     *
     * @param schemaName     схема (например, "public")
     * @param tableName      имя таблицы
     * @param dateTimeColumn имя колонки с типом timestamp
     * @param deleteBefore   дата/время, до которых удалять записи
     * @param batchSize      размер батча (количество строк за один шаг)
     * @param sleepMillis    пауза между итерациями очистки (в миллисекундах)
     */
    void executeCleanup(
            String schemaName,
            String tableName,
            String dateTimeColumn,
            OffsetDateTime deleteBefore,
            int batchSize,
            int sleepMillis
    );
}
