/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : ada

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2013-04-03 16:32:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `profession`
-- ----------------------------
DROP TABLE IF EXISTS `profession`;
CREATE TABLE `profession` (
  `id` varchar(36) NOT NULL,
  `name` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of profession
-- ----------------------------
