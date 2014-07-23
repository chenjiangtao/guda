/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : tiao

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2013-05-01 08:50:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `u_order`
-- ----------------------------
DROP TABLE IF EXISTS `u_order`;
CREATE TABLE `u_order` (
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
-- Records of u_order
-- ----------------------------
INSERT INTO `u_order` VALUES ('d00e54c0-92bd-4a1c-b648-c1998283285d', '1', null, null, '1', '2013-04-30 22:34:35', '2013-04-30 22:34:35');
INSERT INTO `u_order` VALUES ('d7b535a0-5e5e-48ec-86ba-4d3bd396e66c', '1', null, null, '1,1', '2013-04-30 22:25:31', '2013-04-30 22:25:31');
