/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : ada

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2012-12-23 21:57:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `msg_reply`
-- ----------------------------
DROP TABLE IF EXISTS `msg_reply`;
CREATE TABLE `msg_reply` (
  `id` varchar(36) NOT NULL,
  `content` text NOT NULL,
  `msg_id` varchar(36) NOT NULL,
  `gmt_created` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of msg_reply
-- ----------------------------
INSERT INTO `msg_reply` VALUES ('1', '回复吴先生', '1', '2012-12-22 22:59:16');
INSERT INTO `msg_reply` VALUES ('2', '回复吴女士', '2', '2012-12-22 22:59:35');
