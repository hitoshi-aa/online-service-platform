-- Add status column to service_order
ALTER TABLE service_order ADD COLUMN IF NOT EXISTS status VARCHAR(50) DEFAULT 'Waiting';

-- Reset data to include all masters and statuses
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE order_services;
TRUNCATE TABLE service_order;
SET FOREIGN_KEY_CHECKS = 1;

-- Creating more orders to cover all masters (1-4)
INSERT INTO service_order (id, order_date, client_id, master_id, status) VALUES 
(1, NOW(), 1, 1, 'Completed'),
(2, NOW(), 2, 2, 'In Progress'),
(3, NOW(), 3, 3, 'Waiting'),
(4, NOW(), 1, 4, 'Completed'),
(5, NOW(), 2, 1, 'In Progress'),
(6, NOW(), 3, 2, 'Waiting'),
(7, NOW(), 1, 3, 'Completed'),
(8, NOW(), 2, 4, 'In Progress');

-- Link services to all orders (N:M)
INSERT INTO order_services (order_id, service_id) VALUES 
(1, 1), (1, 3),
(2, 2),
(3, 3), (3, 4),
(4, 1), (4, 4),
(5, 2), (5, 3),
(6, 1),
(7, 4),
(8, 2), (8, 3);
