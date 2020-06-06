-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema meat_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema meat_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `meat_db` DEFAULT CHARACTER SET utf8 ;
USE `meat_db` ;

-- -----------------------------------------------------
-- Table `meat_db`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `meat_db`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `meat_db`.`goal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `meat_db`.`goal` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `meat_db`.`mark`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `meat_db`.`mark` (
  `user_id` INT NOT NULL,
  `goal_id` INT NOT NULL,
  `mark` INT NOT NULL,
  INDEX `fk_mark_user_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_mark_goal1_idx` (`goal_id` ASC) VISIBLE,
  CONSTRAINT `fk_mark_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `meat_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_mark_goal1`
    FOREIGN KEY (`goal_id`)
    REFERENCES `meat_db`.`goal` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
