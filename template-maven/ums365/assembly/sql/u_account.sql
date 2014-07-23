/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : tiao

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2013-05-01 08:50:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `u_account`
-- ----------------------------
DROP TABLE IF EXISTS `u_account`;
CREATE TABLE `u_account` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `balance` int(11) DEFAULT NULL,
  `balance_lock` int(11) DEFAULT NULL,
  `gmt_modify` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_account
-- ----------------------------
INSERT INTO `u_account` VALUES ('1dc17e97-89d6-4283-be4f-eb9c9f120b82', '5a0f2150-ddd8-4db2-9e81-8b92c07c5a42', '2', '0', '2013-05-01 08:02:12');
