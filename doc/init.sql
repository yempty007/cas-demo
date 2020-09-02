-- ----------------------------
-- create database cas-sso
-- ----------------------------

-- ----------------------------
-- Table structure for cas_user
-- ----------------------------
DROP TABLE IF EXISTS `cas_user`;
CREATE TABLE `cas_user`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `username`    varchar(20)        DEFAULT NULL,
    `password`    varchar(100)        DEFAULT NULL,
    `locked`      tinyint    NOT NULL DEFAULT 0,
    `create_time` datetime            DEFAULT NULL,
    PRIMARY KEY (`id`)
) AUTO_INCREMENT = 10000;

-- ----------------------------
-- Records of cas_user
-- ----------------------------
INSERT INTO `cas_user`
VALUES (1, 'admin', '$2a$10$Wr6ZFi912cNjS0gV4qa0b.5WQBxa6hbGq1gN2PV4k.iv7JbLdQwhO', '2020-08-25 23:05:50');
