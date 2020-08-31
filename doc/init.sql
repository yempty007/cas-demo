/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : cas-sso

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 26/08/2020 22:11:59
*/

create database 'cas-sso';
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cas_user
-- ----------------------------
DROP TABLE IF EXISTS `cas_user`;
CREATE TABLE `cas_user`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `username`    varchar(20)        DEFAULT NULL,
    `password`    varchar(50)        DEFAULT NULL,
    `locked`      tinyint    NOT NULL DEFAULT 0,
    `create_time` datetime            DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000;

-- ----------------------------
-- Records of cas_user
-- ----------------------------
BEGIN;
INSERT INTO `cas_user`
VALUES (10000, 'admin', '$2a$10$Wr6ZFi912cNjS0gV4qa0b.5WQBxa6hbGq1gN2PV4k.iv7JbLdQwhO', '2020-08-25 23:05:50');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
