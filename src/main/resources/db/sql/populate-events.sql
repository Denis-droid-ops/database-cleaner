INSERT INTO events (created_at, user_id, payload)
SELECT
        now() - (random() * interval '365 days') AS created_at,
        (random() * 1000000)::bigint AS user_id,
        jsonb_build_object(
            'action', 'click',
                'value', (random() * 1000)::int
            )
FROM generate_series(1, 1000000);

INSERT INTO events_double (created_at, user_id, payload)
SELECT
        now() - (random() * interval '365 days') AS created_at,
        (random() * 1000000)::bigint AS user_id,
        jsonb_build_object(
            'action', 'click',
                'value', (random() * 1000)::int
            )
FROM generate_series(1, 1000000);