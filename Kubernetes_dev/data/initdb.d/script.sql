CREATE DATABASE IF NOT EXISTS orderpayments;

CREATE TABLE IF NOT EXISTS orderpayments.tb_order_payments (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `order_id` VARCHAR(255) NOT NULL,
    `payment_status` VARCHAR(255) NOT NULL,
    `qr_code` VARCHAR(255)
);