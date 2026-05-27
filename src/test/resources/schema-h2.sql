-- seq 테이블들의 유니크 키 이름을 식별할 수 있게 바뀜
-- point location not null -> point varchar(1) 변경됨 | Point 형식이 없기 때문에 도서관의 location필드는 항상 null로 조회됨

CREATE SCHEMA IF NOT EXISTS bookspot_test;
SET SCHEMA bookspot_test;

DROP TABLE IF EXISTS bag_book;
DROP TABLE IF EXISTS shelf_books;
DROP TABLE IF EXISTS shelves;
DROP TABLE IF EXISTS users;

DROP TABLE IF EXISTS book_codes;
DROP TABLE IF EXISTS library_stock;
DROP TABLE IF EXISTS library;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS book_code;


-- bookspot.book_codes definition
CREATE TABLE `book_codes` (
  `id` int NOT NULL,
  `name` varchar(100) NOT NULL,
  `parent_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `book_codes_FK` (`parent_id`),
  CONSTRAINT `book_codes_FK` FOREIGN KEY (`parent_id`) REFERENCES `book_codes` (`id`)
) ENGINE=InnoDB;

-- bookspot.library definition
CREATE TABLE `library` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `library_code` varchar(100) NOT NULL,
  `location` varchar(1),
  `name` varchar(255) NOT NULL,
  `updated_at` date DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `naru_detail` varchar(100) DEFAULT NULL,
  `stock_updated_at` date DEFAULT NULL,
  `closed_info` varchar(255) DEFAULT NULL,
  `contact_number` varchar(255) DEFAULT NULL,
  `home_page` varchar(255) DEFAULT NULL,
  `operating_info` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `library_un` (`library_code`)
) ENGINE=InnoDB;

-- bookspot.book definition
CREATE TABLE `book` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `isbn13` varchar(13) NOT NULL,
  `title` varchar(300) DEFAULT NULL,
  `subject_code` int DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `publication_year` year DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `loan_count` int NOT NULL DEFAULT '0',
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `monthly_loan_increase` INT UNSIGNED DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKdjx0bsw5qtlpa3ertiyf8j0bc` (`isbn13`)
) ENGINE=InnoDB;

-- bookspot.library_stock definition
CREATE TABLE `library_stock` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `book_id` bigint NOT NULL,
  `library_id` bigint NOT NULL,
  `created_at` date DEFAULT NULL,
  `updated_at_time` datetime DEFAULT NULL,
  `subject_code` VARCHAR(40) NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `library_stock_un` (`book_id`,`library_id`),
  KEY `library_stock_FK_1` (`library_id`),
  CONSTRAINT `library_stock_FK` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `library_stock_FK_1` FOREIGN KEY (`library_id`) REFERENCES `library` (`id`)
) ENGINE=InnoDB;

-- bookspot_sample.book_code definition
CREATE TABLE `book_code` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `book_code_book_code_FK` (`parent_id`),
  CONSTRAINT `book_code_book_code_FK` FOREIGN KEY (`parent_id`) REFERENCES `book_code` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- bookspot_sample.users definition
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `provider` varchar(20) NOT NULL,
  `provider_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `book_bag_size` int NOT NULL DEFAULT '0',
  `shelf_size` int NOT NULL DEFAULT '0',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_provider_providerId` (`provider`, `provider_id`),
  UNIQUE KEY `UK9y75j1x5sby3xb53ko6344y4` (`provider`, `provider_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- bookspot_sample.bag_book definition
CREATE TABLE `bag_book` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `book_id` bigint NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_user_book` (`user_id`, `book_id`),
  KEY `fk_book` (`book_id`),
  CONSTRAINT `fk_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- bookspot_sample.shelves definition
CREATE TABLE `shelves` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `book_count` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `is_public` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK26j0prhaxbvf2c3mf9f2p5dck` (`user_id`),
  CONSTRAINT `FK26j0prhaxbvf2c3mf9f2p5dck` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- bookspot_sample.shelf_books definition
CREATE TABLE `shelf_books` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shelf_id` bigint NOT NULL,
  `book_id` bigint NOT NULL,
  `idx` int NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_shelf_book` (`shelf_id`, `book_id`),
  KEY `fk_shelf_book_book` (`book_id`),
  CONSTRAINT `fk_shelf` FOREIGN KEY (`shelf_id`) REFERENCES `shelves` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_shelf_book_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;