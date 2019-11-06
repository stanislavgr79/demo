INSERT INTO roles (id, name) VALUES(1, 'ROLE_ADMIN')
ON DUPLICATE KEY UPDATE name = 'ROLE_ADMIN';

INSERT INTO roles (id, name) VALUES(2, 'ROLE_MANAGER')
ON DUPLICATE KEY UPDATE name = 'ROLE_MANAGER';

INSERT INTO roles (id, name) VALUES(3, 'ROLE_USER')
ON DUPLICATE KEY UPDATE name = 'ROLE_USER';

INSERT INTO workDB.users (id, accountNonExpired, accountNonLocked, credentialsNonExpired, email, enabled, password)
 VALUES (1, true, true, true, 'admin@admin.ru', true, '$2a$10$U/E/DK68AZtFlgqhic2pweRuAho8amkBxQ0ecQKy9lhX7wbWcfFFm')
ON DUPLICATE KEY UPDATE accountNonExpired = true , accountNonLocked = true, credentialsNonExpired = true, email = 'admin@admin.ru', enabled = true,
                        password = '$2a$10$U/E/DK68AZtFlgqhic2pweRuAho8amkBxQ0ecQKy9lhX7wbWcfFFm';

INSERT INTO workDB.users_roles (user_id, role_id) VALUES (1, 1)
ON DUPLICATE KEY UPDATE role_id = 1;


