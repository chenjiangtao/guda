/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : tiao

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2013-04-26 13:47:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `msg_in`
-- ----------------------------
DROP TABLE IF EXISTS `msg_in`;
CREATE TABLE `msg_in` (
  `id` varchar(36) NOT NULL,
  `send_id` varchar(25) NOT NULL,
  `content` text,
  `gmt_created` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `recv_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of msg_in
-- ----------------------------
INSERT INTO `msg_in` VALUES ('e37b0e29-711e-4d1f-9925-90b7a91a191b', '8615990081937', 'ddfggvhhhjeq大学谁想', '2012-12-20 22:48:59', null);
