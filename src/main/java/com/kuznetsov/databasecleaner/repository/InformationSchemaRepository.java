package com.kuznetsov.databasecleaner.repository;

import java.util.Set;

/**
 * Репозиторий для работы с системной схемой БД {@code information_schema}.
 * Предоставляет методы для получения метаданных о таблицах.
 */
public interface InformationSchemaRepository {

    /**
     * Возвращает множество всех таблиц в базе данных в формате {@code schema.table},
     * исключая системные схемы (например, {@code pg_catalog}, {@code information_schema})
     * и служебные таблицы приложения (например, {@code cleanup_jobs}, {@code cleanup_locks} и др.).
     *
     * @return множество строк, где каждая строка имеет вид {@code schema.table}.
     *         Если таблиц не найдено — возвращается пустое множество.
     */
    Set<String> getAllTables();
}
