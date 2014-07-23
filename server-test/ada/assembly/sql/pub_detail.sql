/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : ada

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2012-12-23 21:57:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `pub_detail`
-- ----------------------------
DROP TABLE IF EXISTS `pub_detail`;
CREATE TABLE `pub_detail` (
  `id` varchar(36) NOT NULL,
  `title` text,
  `content` text,
  `pub_id` varchar(36) NOT NULL,
  `gmt_created` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `seq` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pub_detail
-- ----------------------------
