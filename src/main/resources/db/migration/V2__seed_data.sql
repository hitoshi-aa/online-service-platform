INSERT INTO client(name, phone)
VALUES
    ('Ivan Petrov', '+380111111111'),
    ('Anna Shevchenko', '+380222222222'),
    ('Maksym Bondarenko', '+380333333333'),
    ('Olena Kovalenko', '+380444444444'),
    ('Dmytro Melnyk', '+380555555555');

INSERT INTO master(name, specialization)
VALUES
    ('Oleg Master', 'Electrician'),
    ('Sergey Master', 'Plumber'),
    ('Andriy Fixer', 'Computer Repair'),
    ('Roman Builder', 'Furniture Assembly');

INSERT INTO services(name, price)
VALUES
    ('Electrical Repair', 500),
    ('Pipe Installation', 1200),
    ('PC Diagnostics', 700),
    ('Furniture Assembly', 900),
    ('Home Appliance Repair', 1500);

INSERT INTO service_order(order_date, client_id, master_id)
VALUES
    (NOW(), 1, 1),
    (NOW(), 2, 2),
    (NOW(), 3, 3),
    (NOW(), 4, 4);

INSERT INTO review(rating, comment, client_id)
VALUES
    (5, 'Excellent service', 1),
    (4, 'Good job', 2),
    (5, 'Very fast repair', 3),
    (3, 'Average experience', 4),
    (5, 'Highly recommended', 5);