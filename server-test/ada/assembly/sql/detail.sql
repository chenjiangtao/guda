/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : ada

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2013-04-03 16:32:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `detail`
-- ----------------------------
DROP TABLE IF EXISTS `detail`;
CREATE TABLE `detail` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `content` text,
  `gmt_modify` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of detail
-- ----------------------------
