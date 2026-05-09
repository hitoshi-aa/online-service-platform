SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE order_services;
TRUNCATE TABLE service_order;
TRUNCATE TABLE services;
TRUNCATE TABLE master;
TRUNCATE TABLE client;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO client VALUES (1, 'Ivan Petrov', '+380111111111'), (2, 'Anna Shevchenko', '+380222222222'), (3, 'Maksym Bondarenko', '+380333333333'), (4, 'Oksana Petrenko', '+380444444444'), (5, 'Petro Sydorenko', '+380555555555');
INSERT INTO master VALUES (1, 'Andrii Kovalenko', 'Computer Repair', 'andrii', '$2a$10$HXL9DyXN6CB5NzkWptJX1eW7vrLRpO11eQ8sC2cv2U5qL6tKDFmsG'), (2, 'Dmytro Hrytsenko', 'Plumber', 'dmytro', '$2a$10$Um39LW0WRw3ctgMo4/00lux1NqvQZ10CtcVAnBGMee0lyre1Pbq8G'), (3, 'Ivan Melnyk', 'Electrician', 'ivan', '$2a$10$qbiJZ/zN/wJaxbmE5puq2.brgZvklJ/DGvhJLh5knbQzkWCGLjGiqS'), (4, 'Olena Bondar', 'Furniture Assembly', 'olena', '$2a$10$AtlgbIAMssTe0vigLUnJPum1N3v6yDzDXUFPXD.BbgUW4tDn7aqaO');
INSERT INTO services VALUES (1, 'Electrical Repair', 500), (2, 'Pipe Installation', 1200), (3, 'PC Diagnostics', 700), (4, 'Furniture Assembly', 900);
INSERT INTO service_order VALUES (1, NOW(), 1, 1, 'Completed'), (2, NOW(), 2, 2, 'In Progress'), (3, NOW(), 3, 3, 'Waiting'), (4, NOW(), 4, 4, 'Completed'), (5, NOW(), 5, 1, 'In Progress');
INSERT INTO order_services VALUES (1, 1), (1, 3), (2, 2), (3, 3), (3, 4), (4, 4), (5, 1);
