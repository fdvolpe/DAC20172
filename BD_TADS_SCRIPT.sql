-- MySQL Script generated by MySQL Workbench
-- Sun Oct  1 20:02:07 2017
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema bd_tads
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `bd_tads` ;

-- -----------------------------------------------------
-- Schema bd_tads
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bd_tads` DEFAULT CHARACTER SET utf8 ;
USE `bd_tads` ;

-- -----------------------------------------------------
-- Table `bd_tads`.`FUNCIONARIO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bd_tads`.`FUNCIONARIO` (
  `MATRICULA_FUNCIONARIO` INT NOT NULL AUTO_INCREMENT,
  `CPF_FUNCIONARIO` VARCHAR(14) NOT NULL,
  `TIPO_FUNCIONARIO` VARCHAR(20) NOT NULL,
  `NOME_FUNCIONARIO` VARCHAR(50) NOT NULL,
  `ENDERECO_FUNCIONARIO` VARCHAR(100) NOT NULL,
  `TELEFONE_FUNCIONARIO` VARCHAR(12) NOT NULL,
  `EMAIL_FUNCIONARIO` VARCHAR(50) NOT NULL,
  `SENHA_FUNCIONARIO` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`MATRICULA_FUNCIONARIO`),
  UNIQUE INDEX `MATRICULA_FUNCIONARIO_UNIQUE` (`MATRICULA_FUNCIONARIO` ASC),
  UNIQUE INDEX `CPF_FUNCIONARIO_UNIQUE` (`CPF_FUNCIONARIO` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bd_tads`.`ENTREGA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bd_tads`.`ENTREGA` (
  `ID_ENTREGA` INT NOT NULL AUTO_INCREMENT,
  `ID_PEDIDO` INT NOT NULL,
  `DESCRICAO_ENTREGA` VARCHAR(100) NOT NULL,
  `ENDERECO_ENTREGA` VARCHAR(100) NOT NULL,
  `CLIENTE_ENTREGA` VARCHAR(50) NOT NULL,
  `DATA_ENTREGA` DATE NOT NULL,
  `STATUS` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`ID_ENTREGA`),
  UNIQUE INDEX `ID_ENTREGA_UNIQUE` (`ID_ENTREGA` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bd_tads`.`FUNCIONARIO_ENTREGA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bd_tads`.`FUNCIONARIO_ENTREGA` (
  `ID_FUNCIONARIO_ENTREGA` INT NOT NULL AUTO_INCREMENT,
  `MATRICULA_FUNCIONARIO` INT NOT NULL,
  `ID_ENTREGA` INT NOT NULL,
  `STATUS` VARCHAR(16) NOT NULL,
  `MOTIVO` VARCHAR(500) NOT NULL,
  `DATA` DATE NOT NULL,
  `FUNCIONARIO_ENTREGAcol` VARCHAR(45) NULL,
  PRIMARY KEY (`ID_FUNCIONARIO_ENTREGA`),
  UNIQUE INDEX `ID_FUNCIONARIO_ENTREGA_UNIQUE` (`ID_FUNCIONARIO_ENTREGA` ASC),
  INDEX `FUNCIONARIO_ENTREGA_FUNCIONARIO_idx` (`MATRICULA_FUNCIONARIO` ASC),
  INDEX `FUNCIONARIO_ENTREGA_ENTREGA_idx` (`ID_ENTREGA` ASC),
  CONSTRAINT `FUNCIONARIO_ENTREGA_FUNCIONARIO`
    FOREIGN KEY (`MATRICULA_FUNCIONARIO`)
    REFERENCES `bd_tads`.`FUNCIONARIO` (`MATRICULA_FUNCIONARIO`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FUNCIONARIO_ENTREGA_ENTREGA`
    FOREIGN KEY (`ID_ENTREGA`)
    REFERENCES `bd_tads`.`ENTREGA` (`ID_ENTREGA`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
