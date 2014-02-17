SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `hipsterbility` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `hipsterbility` ;

-- -----------------------------------------------------
-- Table `hipsterbility`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hipsterbility`.`users` ;

CREATE TABLE IF NOT EXISTS `hipsterbility`.`users` (
  `idusers` INT NOT NULL AUTO_INCREMENT,
  `name` TEXT NULL,
  `password` TEXT NULL,
  `active` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`idusers`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hipsterbility`.`apps`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hipsterbility`.`apps` ;

CREATE TABLE IF NOT EXISTS `hipsterbility`.`apps` (
  `idapps` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`idapps`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hipsterbility`.`devices`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hipsterbility`.`devices` ;

CREATE TABLE IF NOT EXISTS `hipsterbility`.`devices` (
  `iddevices` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `os_version` VARCHAR(10) NULL,
  `type` TEXT NULL,
  `users_idusers` INT NOT NULL,
  PRIMARY KEY (`iddevices`),
  INDEX `fk_devices_users1_idx` (`users_idusers` ASC),
  CONSTRAINT `fk_devices_users1`
    FOREIGN KEY (`users_idusers`)
    REFERENCES `hipsterbility`.`users` (`idusers`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hipsterbility`.`sessions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hipsterbility`.`sessions` ;

CREATE TABLE IF NOT EXISTS `hipsterbility`.`sessions` (
  `idsessions` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `active` TINYINT NULL DEFAULT 0,
  `users_idusers` INT NULL,
  `apps_idapps` INT NOT NULL,
  `devices_iddevices` INT NOT NULL,
  PRIMARY KEY (`idsessions`),
  UNIQUE INDEX `idsessions_UNIQUE` (`idsessions` ASC),
  INDEX `fk_sessions_users1_idx` (`users_idusers` ASC),
  INDEX `fk_sessions_apps1_idx` (`apps_idapps` ASC),
  INDEX `fk_sessions_devices1_idx` (`devices_iddevices` ASC),
  CONSTRAINT `fk_sessions_users1`
    FOREIGN KEY (`users_idusers`)
    REFERENCES `hipsterbility`.`users` (`idusers`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sessions_apps1`
    FOREIGN KEY (`apps_idapps`)
    REFERENCES `hipsterbility`.`apps` (`idapps`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sessions_devices1`
    FOREIGN KEY (`devices_iddevices`)
    REFERENCES `hipsterbility`.`devices` (`iddevices`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hipsterbility`.`captures`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hipsterbility`.`captures` ;

CREATE TABLE IF NOT EXISTS `hipsterbility`.`captures` (
  `idcaptures` INT NOT NULL AUTO_INCREMENT,
  `file` TEXT NULL,
  `sessions_idsessions` INT NOT NULL,
  PRIMARY KEY (`idcaptures`),
  INDEX `fk_captures_sessions1_idx` (`sessions_idsessions` ASC),
  CONSTRAINT `fk_captures_sessions1`
    FOREIGN KEY (`sessions_idsessions`)
    REFERENCES `hipsterbility`.`sessions` (`idsessions`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hipsterbility`.`audios`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hipsterbility`.`audios` ;

CREATE TABLE IF NOT EXISTS `hipsterbility`.`audios` (
  `idaudios` INT NOT NULL AUTO_INCREMENT,
  `file` TEXT NULL,
  `sessions_idsessions` INT NOT NULL,
  PRIMARY KEY (`idaudios`),
  INDEX `fk_audios_sessions1_idx` (`sessions_idsessions` ASC),
  CONSTRAINT `fk_audios_sessions1`
    FOREIGN KEY (`sessions_idsessions`)
    REFERENCES `hipsterbility`.`sessions` (`idsessions`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hipsterbility`.`logs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hipsterbility`.`logs` ;

CREATE TABLE IF NOT EXISTS `hipsterbility`.`logs` (
  `idlogs` INT NOT NULL AUTO_INCREMENT,
  `file` TEXT NULL,
  `sessions_idsessions` INT NOT NULL,
  PRIMARY KEY (`idlogs`),
  INDEX `fk_logs_sessions1_idx` (`sessions_idsessions` ASC),
  CONSTRAINT `fk_logs_sessions1`
    FOREIGN KEY (`sessions_idsessions`)
    REFERENCES `hipsterbility`.`sessions` (`idsessions`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hipsterbility`.`videos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hipsterbility`.`videos` ;

CREATE TABLE IF NOT EXISTS `hipsterbility`.`videos` (
  `idvideos` INT NOT NULL AUTO_INCREMENT,
  `file` TEXT NULL,
  `sessions_idsessions` INT NOT NULL,
  PRIMARY KEY (`idvideos`),
  INDEX `fk_videos_sessions1_idx` (`sessions_idsessions` ASC),
  CONSTRAINT `fk_videos_sessions1`
    FOREIGN KEY (`sessions_idsessions`)
    REFERENCES `hipsterbility`.`sessions` (`idsessions`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hipsterbility`.`results`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hipsterbility`.`results` ;

CREATE TABLE IF NOT EXISTS `hipsterbility`.`results` (
  `idresults` INT NOT NULL,
  `file` TEXT NULL,
  `timestamp` TIMESTAMP NULL,
  `sessions_idsessions` INT NOT NULL,
  PRIMARY KEY (`idresults`),
  INDEX `fk_results_sessions1_idx` (`sessions_idsessions` ASC),
  CONSTRAINT `fk_results_sessions1`
    FOREIGN KEY (`sessions_idsessions`)
    REFERENCES `hipsterbility`.`sessions` (`idsessions`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hipsterbility`.`todos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hipsterbility`.`todos` ;

CREATE TABLE IF NOT EXISTS `hipsterbility`.`todos` (
  `idtodos` INT NOT NULL AUTO_INCREMENT,
  `name` TEXT NULL,
  `description` TEXT NULL,
  `active` TINYINT NULL,
  `sessions_idsessions` INT NOT NULL,
  PRIMARY KEY (`idtodos`),
  INDEX `fk_todos_sessions1_idx` (`sessions_idsessions` ASC),
  CONSTRAINT `fk_todos_sessions1`
    FOREIGN KEY (`sessions_idsessions`)
    REFERENCES `hipsterbility`.`sessions` (`idsessions`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hipsterbility`.`tasks`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hipsterbility`.`tasks` ;

CREATE TABLE IF NOT EXISTS `hipsterbility`.`tasks` (
  `idtasks` INT NOT NULL AUTO_INCREMENT,
  `name` TEXT NULL,
  `done` TINYINT NULL DEFAULT 0,
  `todos_idtodos` INT NOT NULL,
  PRIMARY KEY (`idtasks`),
  INDEX `fk_tasks_todos1_idx` (`todos_idtodos` ASC),
  CONSTRAINT `fk_tasks_todos1`
    FOREIGN KEY (`todos_idtodos`)
    REFERENCES `hipsterbility`.`todos` (`idtodos`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
