UPDATE pg_database SET datallowconn = 'false' WHERE datname = :databaseNameQuotes;
SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = :databaseNameQuotes;

DROP DATABASE IF EXISTS :databaseName;

UPDATE pg_database SET datallowconn = 'true' WHERE datname = :databaseNameQuotes;
