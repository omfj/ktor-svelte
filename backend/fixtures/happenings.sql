INSERT INTO happenings (id, title, date, register_start, register_end, event_restrictions) VALUES
    (gen_random_uuid(), 'Sommerfest', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP - INTERVAL '10 minutes', CURRENT_TIMESTAMP + INTERVAL '1 day', 1),
    (gen_random_uuid(), 'Bedpres på mars', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '3 minutes', 1),
    (gen_random_uuid(), 'Bedpres med OceanGate', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '10 minutes', CURRENT_TIMESTAMP + INTERVAL '1 day', 1),
    (gen_random_uuid(), 'Fylla på heidis', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '30 minutes', CURRENT_TIMESTAMP + INTERVAL '3 days', 1);
