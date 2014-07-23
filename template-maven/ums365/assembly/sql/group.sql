/*
MySQL Data Transfer
Source Host: localhost
Source Database: tiao
Target Host: localhost
Target Database: tiao
Date: 2012/12/15 12:53:18
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for group
-- ----------------------------
CREATE TABLE `group` (
  `id` varchar(36) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
