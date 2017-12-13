-- In Spring Boot using Spring Data, we use ~/src/main/resources/schema.sql to create the database schema.
-- We no longer have to use ~/src/main/resources/database/h2/calendar-schema.sql to create the database schema.
-- *** NOTE: We are going to use the following property:
-- "jpa.database-platform.hibernate.ddl-auto=create-drop"
-- So we dont need to create a schema manually.

-- the end --
