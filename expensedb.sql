CREATE SCHEMA `expensedb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `expensedb`;
SET SQL_SAFE_UPDATES = 1;

CREATE TABLE `user` (
	`id` int NOT NULL AUTO_INCREMENT,
	`username` varchar(15) NOT NULL UNIQUE,
	`password` varchar(255) NOT NULL,
	`first_name` varchar(45) DEFAULT NULL,
    `last_name` varchar(45) DEFAULT NULL,
    `avatar` varchar(100) DEFAULT NULL,
	`gender` bit(1) DEFAULT b'1',
	`date_of_birth` date DEFAULT NULL,
	`registration_date` date DEFAULT (DATE_FORMAT(NOW(), '%Y-%m-%d')),
	`email` varchar(45) DEFAULT NULL,
	`phone` varchar(10) DEFAULT NULL,
    `active` bit(1) DEFAULT b'1',
    `role` varchar(10) DEFAULT 'USER',
	PRIMARY KEY (`id`)
);

INSERT INTO `user`(`username`, `password` ,`first_name`, `gender`, `role`) 
VALUES ('admin', '$2a$10$IMc.iLFc2GlER.1nZT2or.IKqqnSB76N6Y5YRMHtYABYWrnPnExIK', 'admin', 1, 'ADMIN'),
('user', '$2a$10$IMc.iLFc2GlER.1nZT2or.IKqqnSB76N6Y5YRMHtYABYWrnPnExIK', 'user', 1, 'USER'),
('user1', '$2a$10$IMc.iLFc2GlER.1nZT2or.IKqqnSB76N6Y5YRMHtYABYWrnPnExIK', 'user1', 1, 'USER');

CREATE TABLE `custom_group` (
	`id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(45) NOT NULL,
    `active` bit(1) DEFAULT b'1',
	PRIMARY KEY (`id`)
);

INSERT INTO `custom_group`(`name`) VALUES ('Traveling');

CREATE TABLE `group_user` (
	`id` int NOT NULL AUTO_INCREMENT,
    `group_id` int DEFAULT NULL,
	`user_id` int DEFAULT NULL,
    `is_leader` bit(1) DEFAULT b'0',
    `active` bit(1) DEFAULT b'1',
	PRIMARY KEY (`id`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL,
    FOREIGN KEY (`group_id`) REFERENCES `custom_group` (`id`) ON DELETE SET NULL
);

INSERT INTO `group_user`(`group_id`, `user_id`, `is_leader`) VALUES (1, 1, 1), (1, 2, 0);

CREATE TABLE `expense` (
	`id` int NOT NULL AUTO_INCREMENT,
    `user_id` int DEFAULT NULL,
    `group_id` int DEFAULT NULL,
    `amount` decimal(12,2) DEFAULT 0,
	`purpose` varchar(255) NOT NULL,
	`description` longtext DEFAULT NULL,
    `date` datetime NOT NULL DEFAULT NOW(),
    `approved` bit(1) DEFAULT b'1',
    `confirmed` bit(1) DEFAULT b'1',
    `active` bit(1) DEFAULT b'1',
	PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL,
    FOREIGN KEY (`group_id`) REFERENCES `custom_group` (`id`) ON DELETE SET NULL
);

INSERT INTO `expense`(`user_id`, `amount`, `purpose`, `group_id`) 
VALUES (1, 100000, 'food', null),
(1, 100000, 'food', null),
(1, 100000, 'food', null),
(1, 100000, 'food', null),
(1, 100000, 'food', null),
(1, 100000, 'food', null),
(1, 100000, 'food', null),
(1, 100000, 'food', null),
(1, 100000, 'food', 1),
(1, 100000, 'food', 1),
(2, 100000, 'food', 1),
(2, 100000, 'food', 1);

CREATE TABLE `income` (
	`id` int NOT NULL AUTO_INCREMENT,
	`user_id` int DEFAULT NULL,
    `group_id` int DEFAULT NULL,
    `amount` decimal(12,2) DEFAULT 0,
	`source` varchar(255) NOT NULL,
    `description` longtext DEFAULT NULL,
    `date` datetime NOT NULL DEFAULT NOW(),
    `approved` bit(1) DEFAULT b'1',
    `confirmed` bit(1) DEFAULT b'1',
    `active` bit(1) DEFAULT b'1',
	PRIMARY KEY (`id`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL,
    FOREIGN KEY (`group_id`) REFERENCES `custom_group` (`id`) ON DELETE SET NULL
);

INSERT INTO `income`(`user_id`, `amount`, `source`, `group_id`) 
VALUES (1, 30000000, 'salary', null),
(1, 30000000, 'salary', null),
(1, 30000000, 'salary', null),
(1, 30000000, 'salary', null),
(1, 30000000, 'salary', null),
(1, 30000000, 'salary', null),
(1, 30000000, 'salary', null),
(1, 30000000, 'salary', null),
(1, 30000000, 'salary', null),
(1, 30000000, 'salary', 1),
(1, 30000000, 'salary', 1),
(2, 30000000, 'salary', 1),
(2, 30000000, 'salary', 1);