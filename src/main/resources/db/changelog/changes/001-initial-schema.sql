-- wordpress.products definition

CREATE TABLE `products` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `color` tinyint DEFAULT NULL,
                            `description` varchar(255) DEFAULT NULL,
                            `email` varchar(255) DEFAULT NULL,
                            `expedition_date` datetime(6) DEFAULT NULL,
                            `expiration_date` datetime(6) DEFAULT NULL,
                            `name` varchar(255) DEFAULT NULL,
                            `price` decimal(38,2) DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            CONSTRAINT `products_chk_1` CHECK ((`color` between 0 and 4))
);