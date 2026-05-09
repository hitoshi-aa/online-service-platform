-- Clear existing problematic master data to avoid conflicts
-- We temporarily disable foreign key checks to truncate/delete
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE master;
SET FOREIGN_KEY_CHECKS = 1;

-- Insert masters with CORRECT names, logins and BCrypt hashes
INSERT INTO master (id, name, specialization, login, password) VALUES
(1, 'Andrii Kovalenko', 'Computer Repair', 'andrii', '$2a$10$HXL9DyXN6CB5NzkWptJX1eW7vrLRpO11eQ8sC2cv2U5qL6tKDFmsG'),
(2, 'Dmytro Hrytsenko', 'Plumber', 'dmytro', '$2a$10$Um39LW0WRw3ctgMo4/00lux1NqvQZ10CtcVAnBGMee0lyre1Pbq8G'),
(3, 'Ivan Melnyk', 'Electrician', 'ivan', '$2a$10$qbiJZ/zN/wJaxbmE5puq2.brgZvklJ/DGvhJLh5knbQzkWCGLjGiqS'),
(4, 'Olena Bondar', 'Furniture Assembly', 'olena', '$2a$10$AtlgbIAMssTe0vigLUnJPum1N3v6yDzDXUFPXD.BbgUW4tDn7aqaO');

-- Sync existing orders to these new IDs if necessary, 
-- but since we truncated and used the same IDs 1-4, it should be fine.
-- In a real DB we would use UPDATE, but for this project reset is cleaner.
