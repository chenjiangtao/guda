/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : tiao

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2013-05-01 10:51:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `u_account_log`
-- ----------------------------
DROP TABLE IF EXISTS `u_account_log`;
CREATE TABLE `u_account_log` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `amount_before` int(11) NOT NULL,
  `sum` int(11) NOT NULL,
  `action` varchar(30) DEFAULT NULL,
  `amount_after` int(11) NOT NULL,
  `gmt_created` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_account_log
-- ----------------------------
INSERT INTO `u_account_log` VALUES ('1a3cae44-310d-484f-9f57-9a3a280cf46f', '5a0f2150-ddd8-4db2-9e81-8b92c07c5a42', '5', '2', '扣除', '3', '2013-05-01 10:45:45');
INSERT INTO `u_account_log` VALUES ('22cadbc1-2fe0-4649-9b32-b7ae6a94fc8f', '5a0f2150-ddd8-4db2-9e81-8b92c07c5a42', '5', '2', '扣除', '3', '2013-05-01 10:46:16');
INSERT INTO `u_account_log` VALUES ('51741ea4-d16d-4e4b-b2ea-73ada5db0650', '5a0f2150-ddd8-4db2-9e81-8b92c07c5a42', '13', '4', '扣除', '9', '2013-05-01 10:37:05');
INSERT INTO `u_account_log` VALUES ('9908b0ca-64d4-410b-b845-0a54f74f0ac4', '5a0f2150-ddd8-4db2-9e81-8b92c07c5a42', '13', '4', '扣除', '9', '2013-05-01 10:40:59');
INSERT INTO `u_account_log` VALUES ('a2d40259-0e28-4d79-9ec7-c477dc4c84d3', '5a0f2150-ddd8-4db2-9e81-8b92c07c5a42', '5', '2', '扣除', '3', '2013-05-01 10:46:46');
INSERT INTO `u_account_log` VALUES ('b2faaa21-c656-4f3e-b875-765ade8aabd7', '5a0f2150-ddd8-4db2-9e81-8b92c07c5a42', '7', '5', '充值', '12', '2013-05-01 10:30:48');
INSERT INTO `u_account_log` VALUES ('bf5dee65-d8bb-4e95-acf3-6069e851573c', '5a0f2150-ddd8-4db2-9e81-8b92c07c5a42', '7', '5', '充值', '12', '2013-05-01 10:28:11');
INSERT INTO `u_account_log` VALUES ('f58fb04b-fa50-4fa4-94c9-d6aa5f3a19d5', '5a0f2150-ddd8-4db2-9e81-8b92c07c5a42', '12', '5', '充值', '17', '2013-05-01 10:32:37');
