DO
$do$
BEGIN
   IF EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'vulpes_user') THEN
        RAISE NOTICE 'Role "vulpes" already exists. Skipping.';
    ELSE
        CREATE USER vulpes_user LOGIN;
    END IF;
END
$do$;

