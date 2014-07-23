/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : tiao

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2013-05-06 14:34:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `msg_out`
-- ----------------------------
DROP TABLE IF EXISTS `msg_out`;
CREATE TABLE `msg_out` (
  `id` varchar(36) NOT NULL,
  `send_id` varchar(36) DEFAULT NULL,
  `recv_id` varchar(21) NOT NULL,
  `content` text NOT NULL,
  `status` varchar(1) DEFAULT NULL,
  `send_time` timestamp NULL DEFAULT NULL,
  `gmt_modify` timestamp NULL DEFAULT NULL,
  `gmt_created` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of msg_out
-- ----------------------------
INSERT INTO `msg_out` VALUES ('a64a573a-d1ef-41ae-be9a-248a29f55bb7', '1', '15558135733', '123', '1', null, '2013-05-05 10:21:51', '2013-05-05 10:21:51');
INSERT INTO `msg_out` VALUES ('df23e9e0-f580-4b59-af91-d6efa24b544f', '1', '13588754574', '123', '1', null, '2013-05-05 10:21:51', '2013-05-05 10:21:51');
