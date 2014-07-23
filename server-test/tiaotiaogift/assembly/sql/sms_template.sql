/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : tiao

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2013-05-01 08:50:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sms_template`
-- ----------------------------
DROP TABLE IF EXISTS `sms_template`;
CREATE TABLE `sms_template` (
  `id` varchar(36) NOT NULL,
  `content` text NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `type` varchar(10) DEFAULT NULL,
  `gmt_created` timestamp NULL DEFAULT NULL,
  `gmt_modify` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sms_template
-- ----------------------------
INSERT INTO `sms_template` VALUES ('5a4e26d4-b897-485e-8379-80741e58624b', '巴巴', '1', '普通短信', '2013-04-27 12:25:01', '2013-04-27 12:25:01');
INSERT INTO `sms_template` VALUES ('bae5ce31-9936-4b96-8f14-68c9b5ffec4e', '新增333', '1', '普通短信', '2013-04-27 12:24:49', '2013-04-27 12:24:49');
