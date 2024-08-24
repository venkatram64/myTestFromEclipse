CREATE SCHEMA `my_eapp` ;
USE my_eapp;

CREATE TABLE `my_eapp`.`customers` (
  `customer_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50) NULL,
  `last_name` VARCHAR(50) NULL,
  `phone` VARCHAR(12) NULL,
  `email` VARCHAR(45) NULL,
  `street` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(45) NULL,
  `zip_code` VARCHAR(45) NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);
  
  CREATE TABLE `my_eapp`.`orders` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `customer_id` INT NULL,
  `order_date` DATETIME NULL DEFAULT now(),
  `shipping_date` DATETIME NULL,
  PRIMARY KEY (`order_id`),
  INDEX `customer_id_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `customer_id`
    FOREIGN KEY (`customer_id`)
    REFERENCES `my_eapp`.`customers` (`customer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE `my_eapp`.`products` (
  `product_id` INT NOT NULL AUTO_INCREMENT,
  `product_name` VARCHAR(45) NULL,
  `model_year` INT NULL,
  `list_price` DECIMAL(10,2) NULL,
  PRIMARY KEY (`product_id`));



CREATE TABLE `my_eapp`.`order_items` (
  `order_item_id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NULL,
  `product_id` INT NULL,
  `quantity` INT NULL,
  `list_price` DECIMAL(10,2) NULL,
  PRIMARY KEY (`order_item_id`),
  INDEX `order_id_idx` (`order_id` ASC) VISIBLE,
  INDEX `product_id_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `order_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `my_eapp`.`orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `my_eapp`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

