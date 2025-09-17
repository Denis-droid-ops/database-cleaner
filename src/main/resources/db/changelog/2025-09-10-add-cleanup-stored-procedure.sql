--liquibase formatted sql
--changeset dkuznetsov:1 endDelimiter:"$$"
CREATE OR REPLACE PROCEDURE cleanup_table_by_datetime(
    schema_name        text,
    table_name         text,
    datetime_column    text,
    delete_before      timestamptz,
    batch_size         integer,
    sleep_millis       integer
)

LANGUAGE plpgsql
AS $$

DECLARE
    v_sql text;
    v_deleted integer;
    v_lock_key bigint := ('x' || substr(md5(schema_name || '.' || table_name ),1,16))::bit(64)::bigint;

BEGIN

    IF NOT pg_try_advisory_lock(v_lock_key) THEN
        RAISE EXCEPTION 'cleanup_table_by_datetime: table % is already being cleaned', schema_name || '.' || table_name;
    END IF;

    LOOP

        v_sql := format(
            'WITH del AS (
                 SELECT ctid FROM %I.%I WHERE %I < ''%s'' ORDER BY %I ASC LIMIT %s
             )
             DELETE FROM %I.%I t USING del WHERE t.ctid = del.ctid',
            schema_name, table_name, datetime_column,
            to_char(delete_before, 'YYYY-MM-DD HH24:MI:SS.US'),
            datetime_column,
            batch_size,
            schema_name, table_name
        );

        EXECUTE v_sql;

        GET DIAGNOSTICS v_deleted = ROW_COUNT;

        IF v_deleted = 0 THEN
        EXIT;
        END IF;

        COMMIT;

        IF sleep_millis > 0 THEN
            PERFORM pg_sleep(sleep_millis / 1000.0);
        END IF;

    END LOOP;

    PERFORM pg_advisory_unlock(v_lock_key);
END;
$$;