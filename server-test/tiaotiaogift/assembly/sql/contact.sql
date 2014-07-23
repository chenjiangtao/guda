/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : tiao

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2013-05-01 08:50:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `contact`
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
  `id` varchar(36) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `phone` varchar(21) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `address` varchar(2000) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `taobao_name` varchar(50) DEFAULT NULL,
  `taobao_id` varchar(50) DEFAULT NULL,
  `taobao_order_status` varchar(20) DEFAULT NULL,
  `paipai_id` varchar(50) DEFAULT NULL,
  `paipai_name` varchar(50) DEFAULT NULL,
  `paipai_order_status` varchar(20) DEFAULT NULL,
  `gmt_created` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contact
-- ----------------------------
INSERT INTO `contact` VALUES ('17eba596-6cae-42bc-8e8c-b333e729d828', '坨公子', '13588754574', '1', null, null, null, null, null, null, null, '2013-04-27 16:08:28');
INSERT INTO `contact` VALUES ('298e9dc6-447b-4ead-b653-fbc386407e12', '坨坨', '15558135733', '1', null, null, null, null, null, null, null, '2013-04-27 16:07:06');
INSERT INTO `contact` VALUES ('5971e082-8bb5-4c66-a2e2-e5ab589d038c', '陀23', '13588754574', '1', null, null, null, null, null, null, null, '2013-04-27 18:29:27');
INSERT INTO `contact` VALUES ('bdc49c5d-c3a8-491b-b48f-c1f768afff97', '王大妈', '13512341234', '1', null, null, null, null, null, null, null, '2013-04-27 16:41:36');
