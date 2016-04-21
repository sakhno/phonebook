-- -----------------------------------------------------
-- Schema phonebook
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `phonebook_antonsakhno`;

-- -----------------------------------------------------
-- Schema phonebook
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `phonebook_antonsakhno`
DEFAULT CHARACTER SET utf8;
USE `phonebook`;

-- -----------------------------------------------------
-- Table `phonebook`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `phonebook_antonsakhno`.`users` (
  `id`       SERIAL PRIMARY KEY,
  `login`    VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name`     VARCHAR(45) NULL
)
  ENGINE = InnoDB
  CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `phonebook`.`contact`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `phonebook_antonsakhno`.`contact` (
  `id`          SERIAL PRIMARY KEY,
  `lastname`    VARCHAR(45) NOT NULL,
  `firstname`   VARCHAR(45) NOT NULL,
  `middlename`  VARCHAR(45) NOT NULL,
  `mobilephone` VARCHAR(45) NOT NULL,
  `homephone`   VARCHAR(45) NULL,
  `address`     VARCHAR(45) NULL,
  `email`       VARCHAR(45) NULL,
  `user_id`     INT REFERENCES users (id)
)
  ENGINE = InnoDB
  CHARACTER SET = utf8;

