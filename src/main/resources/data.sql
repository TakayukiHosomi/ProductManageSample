CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    stock INT,
    category INT
);

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

INSERT INTO products (name, stock, category) VALUES ('product_A', 100, 1);
INSERT INTO products (name, stock, category) VALUES ('product_B', 200, 1);
INSERT INTO products (name, stock, category) VALUES ('product_C', 300, 1);
INSERT INTO products (name, stock, category) VALUES ('product_D', 1100, 2);
INSERT INTO products (name, stock, category) VALUES ('product_E', 2200, 2);
INSERT INTO products (name, stock, category) VALUES ('product_F', 3300, 2);