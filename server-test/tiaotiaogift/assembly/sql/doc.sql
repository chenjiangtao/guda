/*
MySQL Data Transfer
Source Host: localhost
Source Database: tiao
Target Host: localhost
Target Database: tiao
Date: 2012/12/12 13:29:27
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for doc
-- ----------------------------
CREATE TABLE `doc` (
  `id` varchar(36) NOT NULL,
  `code` varchar(16) NOT NULL,
  `title` varchar(100) NOT NULL,
  `content` text NOT NULL,
  `type` varchar(16) NOT NULL,
  `gmt_created` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
