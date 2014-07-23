/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50612
Source Host           : localhost:3306
Source Database       : info

Target Server Type    : MYSQL
Target Server Version : 50612
File Encoding         : 65001

Date: 2013-10-22 19:14:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `info_img`
-- ----------------------------
DROP TABLE IF EXISTS `info_img`;
CREATE TABLE `info_img` (
  `id` varchar(36) NOT NULL,
  `path` varchar(500) NOT NULL,
  `detail_id` varchar(36) DEFAULT NULL,
  `order_num` int(11) DEFAULT NULL,
  `comment` varchar(500) DEFAULT NULL,
  `gmt_created` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of info_img
-- ----------------------------
