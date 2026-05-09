CREATE TABLE client (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(255),
                        phone VARCHAR(255)
);

CREATE TABLE master (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(255),
                        specialization VARCHAR(255)
);

CREATE TABLE services (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255),
                          price DOUBLE
);

CREATE TABLE service_order (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               order_date DATETIME,
                               client_id BIGINT,
                               master_id BIGINT,

                               CONSTRAINT fk_order_client
                                   FOREIGN KEY (client_id)
                                       REFERENCES client(id),

                               CONSTRAINT fk_order_master
                                   FOREIGN KEY (master_id)
                                       REFERENCES master(id)
);

CREATE TABLE review (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        rating INT,
                        comment VARCHAR(500),
                        client_id BIGINT,

                        CONSTRAINT fk_review_client
                            FOREIGN KEY (client_id)
                                REFERENCES client(id)
);
