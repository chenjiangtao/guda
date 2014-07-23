/*
Navicat MySQL Data Transfer

Source Server         : localhost2
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : monitor

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2013-06-22 08:05:34
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `m_sys`
-- ----------------------------
DROP TABLE IF EXISTS `m_sys`;
CREATE TABLE `m_sys` (
  `id` varchar(36) NOT NULL,
  `ip` varchar(20) NOT NULL,
  `k` varchar(300) NOT NULL,
  `val` float NOT NULL,
  `val_text` text NOT NULL,
  `value_type` int(11) NOT NULL,
  `gmt_created` timestamp NOT NULL default '0000-00-00 00:00:00' on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_sys
-- ----------------------------
