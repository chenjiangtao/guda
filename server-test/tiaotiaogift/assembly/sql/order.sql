/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : tiao

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2013-04-30 08:52:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `order`
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `amount` float DEFAULT NULL,
  `sum` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `gmt_created` timestamp NULL DEFAULT NULL,
  `gmt_modify` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order
-- ----------------------------
