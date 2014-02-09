SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `hipsterbility` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `hipsterbility` ;

-- -----------------------------------------------------
-- Table `hipsterbility`.`captures`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `hipsterbility`.`captures` (
  `idcaptures` INT NOT NULL AUTO_INCREMENT ,
  `file` TEXT NULL ,
  PRIMARY KEY (`idcaptures`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hipsterbility`.`logs`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `hipsterbility`.`logs` (
  `idlogs` INT NOT NULL AUTO_INCREMENT ,
  `file` TEXT NULL ,
  PRIMARY KEY (`idlogs`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hipsterbility`.`users`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `hipsterbility`.`users` (
  `idusers` INT NOT NULL AUTO_INCREMENT ,
  `name` TEXT NULL ,
  `password` TEXT NULL ,
  `active` TINYINT NULL DEFAULT 0 ,
  PRIMARY KEY (`idusers`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hipsterbility`.`audios`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `hipsterbility`.`audios` (
  `idaudios` INT NOT NULL AUTO_INCREMENT ,
  `file` TEXT NULL ,
  PRIMARY KEY (`idaudios`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hipsterbility`.`videos`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `hipsterbility`.`videos` (
  `idvideos` INT NOT NULL AUTO_INCREMENT ,
  `file` TEXT NULL ,
  PRIMARY KEY (`idvideos`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hipsterbility`.`results`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `hipsterbility`.`results` (
  `idresults` INT NOT NULL ,
  `file` TEXT NULL ,
  `timestamp` TIMESTAMP NULL ,
  PRIMARY KEY (`idresults`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hipsterbility`.`tasks`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `hipsterbility`.`tasks` (
  `idtasks` INT NOT NULL AUTO_INCREMENT ,
  `name` TEXT NULL ,
  `done` TINYINT NULL DEFAULT 0 ,
  PRIMARY KEY (`idtasks`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hipsterbility`.`todos`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `hipsterbility`.`todos` (
  `idtodos` INT NOT NULL AUTO_INCREMENT ,
  `name` TEXT NULL ,
  `description` TEXT NULL ,
  `active` TINYINT NULL ,
  `tasks_idtasks` INT NOT NULL ,
  PRIMARY KEY (`idtodos`) ,
  INDEX `fk_todos_tasks1_idx` (`tasks_idtasks` ASC) ,
  CONSTRAINT `fk_todos_tasks1`
    FOREIGN KEY (`tasks_idtasks` )
    REFERENCES `hipsterbility`.`tasks` (`idtasks` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hipsterbility`.`sessions`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `hipsterbility`.`sessions` (
  `idsessions` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  `active` TINYINT NULL DEFAULT 0 ,
  `captures_idcaptures` INT NOT NULL ,
  `logs_idlogs` INT NOT NULL ,
  `users_idusers` INT NOT NULL ,
  `audios_idaudios1` INT NOT NULL ,
  `videos_idvideos` INT NOT NULL ,
  `results_idresults` INT NOT NULL ,
  `todos_idtodos` INT NOT NULL ,
  PRIMARY KEY (`idsessions`) ,
  UNIQUE INDEX `idsessions_UNIQUE` (`idsessions` ASC) ,
  INDEX `fk_sessions_captures1_idx` (`captures_idcaptures` ASC) ,
  INDEX `fk_sessions_logs1_idx` (`logs_idlogs` ASC) ,
  INDEX `fk_sessions_users1_idx` (`users_idusers` ASC) ,
  INDEX `fk_sessions_audios1_idx` (`audios_idaudios1` ASC) ,
  INDEX `fk_sessions_videos1_idx` (`videos_idvideos` ASC) ,
  INDEX `fk_sessions_results1_idx` (`results_idresults` ASC) ,
  INDEX `fk_sessions_todos1_idx` (`todos_idtodos` ASC) ,
  CONSTRAINT `fk_sessions_captures1`
    FOREIGN KEY (`captures_idcaptures` )
    REFERENCES `hipsterbility`.`captures` (`idcaptures` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sessions_logs1`
    FOREIGN KEY (`logs_idlogs` )
    REFERENCES `hipsterbility`.`logs` (`idlogs` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sessions_users1`
    FOREIGN KEY (`users_idusers` )
    REFERENCES `hipsterbility`.`users` (`idusers` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sessions_audios1`
    FOREIGN KEY (`audios_idaudios1` )
    REFERENCES `hipsterbility`.`audios` (`idaudios` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sessions_videos1`
    FOREIGN KEY (`videos_idvideos` )
    REFERENCES `hipsterbility`.`videos` (`idvideos` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sessions_results1`
    FOREIGN KEY (`results_idresults` )
    REFERENCES `hipsterbility`.`results` (`idresults` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sessions_todos1`
    FOREIGN KEY (`todos_idtodos` )
    REFERENCES `hipsterbility`.`todos` (`idtodos` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
