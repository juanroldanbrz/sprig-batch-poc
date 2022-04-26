CREATE TABLE `original_data` (
                                 `id` INT(9) UNSIGNED NOT NULL AUTO_INCREMENT,
                                 `name` VARCHAR(100) NOT NULL,
                                 `email` VARCHAR(100) NOT NULL,
                                 CONSTRAINT `pk_original_data` PRIMARY KEY(`id`)
);

INSERT INTO `original_data` (name, email) values ('juan', 'juan@asd.com');
INSERT INTO `original_data` (name, email) values ('pedro', 'pedro@asd.com');
INSERT INTO `original_data` (name, email) values ('pablo', 'pablo@asd.com');
INSERT INTO `original_data` (name, email) values ('luis', 'luis@asd.com');
INSERT INTO `original_data` (name, email) values ('elsa', 'elsa@asd.com');
INSERT INTO `original_data` (name, email) values ('arturo', 'arturo@asd.com');
INSERT INTO `original_data` (name, email) values ('karen', 'karen@asd.com');
INSERT INTO `original_data` (name, email) values ('karina', 'karina@asd.com');
INSERT INTO `original_data` (name, email) values ('eduardo', 'eduardo@asd.com');
INSERT INTO `original_data` (name, email) values ('gerbasio', 'gerbasio@asd.com');
