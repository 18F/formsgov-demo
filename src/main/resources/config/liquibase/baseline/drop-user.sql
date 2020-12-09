REVOKE ALL ON DATABASE :databaseName FROM :databaseAppRole;
DROP USER IF EXISTS :databaseAppUser;
DROP ROLE IF EXISTS :databaseAppRole;
