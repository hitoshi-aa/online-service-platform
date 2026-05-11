package com.onlineservise.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        System.out.println("[DB-INIT] Checking database state...");
        try {
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS client (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), phone VARCHAR(255))");
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS master (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), specialization VARCHAR(255), login VARCHAR(50), password VARCHAR(255))");
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS services (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), price DOUBLE)");
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS service_order (id BIGINT PRIMARY KEY AUTO_INCREMENT, order_date DATETIME, client_id BIGINT, master_id BIGINT, status VARCHAR(50))");
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS order_services (order_id BIGINT, service_id BIGINT, PRIMARY KEY (order_id, service_id))");

            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM master", Integer.class);
            if (count == 0) {
                System.out.println("[DB-INIT] Seeding initial data...");
                
                String mSql = "INSERT INTO master (id, name, specialization, login, password) VALUES (?, ?, ?, ?, ?)";
                jdbcTemplate.update(mSql, 1, "Andrii Kovalenko", "Computer Repair", "andrii", passwordEncoder.encode("andrii123"));
                jdbcTemplate.update(mSql, 2, "Dmytro Hrytsenko", "Plumber", "dmytro", passwordEncoder.encode("dmytro123"));
                jdbcTemplate.update(mSql, 3, "Ivan Melnyk", "Electrician", "ivan", passwordEncoder.encode("ivan123"));
                jdbcTemplate.update(mSql, 4, "Olena Bondar", "Furniture Assembly", "olena", passwordEncoder.encode("olena123"));

                jdbcTemplate.update("INSERT INTO services VALUES (1, 'Electrical Repair', 500), (2, 'Pipe Installation', 1200), (3, 'PC Diagnostics', 700), (4, 'Furniture Assembly', 900)");
                jdbcTemplate.update("INSERT INTO client VALUES (1, 'Ivan Petrov', '+380111111111'), (2, 'Anna Shevchenko', '+380222222222'), (3, 'Maksym Bondarenko', '+380333333333')");
                
                // Orders with services
                jdbcTemplate.update("INSERT INTO service_order VALUES (1, NOW(), 1, 1, 'Completed'), (2, NOW(), 2, 2, 'In Progress'), (3, NOW(), 3, 3, 'Waiting'), (4, NOW(), 1, 4, 'Completed')");
                jdbcTemplate.update("INSERT INTO order_services VALUES (1, 1), (1, 3), (2, 2), (3, 3), (4, 4)");

                System.out.println("[DB-INIT] Database seeding completed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
