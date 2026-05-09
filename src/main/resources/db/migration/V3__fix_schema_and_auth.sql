-- Many-to-many relationship table (idempotent)
CREATE TABLE IF NOT EXISTS order_services (
    order_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    PRIMARY KEY (order_id, service_id),
    CONSTRAINT fk_os_order FOREIGN KEY (order_id) REFERENCES service_order(id) ON DELETE CASCADE,
    CONSTRAINT fk_os_service FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE
);

-- Add login and password columns to master (safe way)
DROP PROCEDURE IF EXISTS add_col_if_not_exists;
DELIMITER //
CREATE PROCEDURE add_col_if_not_exists()
BEGIN
    IF NOT EXISTS (SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'master' AND COLUMN_NAME = 'login') THEN
        ALTER TABLE master ADD COLUMN login VARCHAR(50) UNIQUE;
    END IF;
    IF NOT EXISTS (SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'master' AND COLUMN_NAME = 'password') THEN
        ALTER TABLE master ADD COLUMN password VARCHAR(255);
    END IF;
END //
DELIMITER ;
CALL add_col_if_not_exists();
DROP PROCEDURE add_col_if_not_exists;

-- Add indexes
DROP PROCEDURE IF EXISTS add_idx_if_not_exists;
DELIMITER //
CREATE PROCEDURE add_idx_if_not_exists(IN idx_name VARCHAR(255), IN table_name VARCHAR(255), IN col_name VARCHAR(255))
BEGIN
    IF NOT EXISTS (SELECT * FROM information_schema.STATISTICS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = table_name AND INDEX_NAME = idx_name) THEN
        SET @s = CONCAT('CREATE INDEX ', idx_name, ' ON ', table_name, '(', col_name, ')');
        PREPARE stmt FROM @s;
        EXECUTE stmt;
    END IF;
END //
DELIMITER ;
CALL add_idx_if_not_exists('idx_client_name', 'client', 'name');
CALL add_idx_if_not_exists('idx_client_phone', 'client', 'phone');
CALL add_idx_if_not_exists('idx_master_login', 'master', 'login');
CALL add_idx_if_not_exists('idx_order_date', 'service_order', 'order_date');
DROP PROCEDURE add_idx_if_not_exists;
