/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : monitor

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2013-09-15 20:30:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `m_agent`
-- ----------------------------
DROP TABLE IF EXISTS `m_agent`;
CREATE TABLE `m_agent` (
  `id` varchar(36) NOT NULL,
  `ip` varchar(16) NOT NULL,
  `k` varchar(200) NOT NULL,
  `order_num` int(5) DEFAULT NULL,
  `host` varchar(100) DEFAULT NULL,
  `value_type` int(1) DEFAULT NULL,
  `gmt_created` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `comment` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_agent
-- ----------------------------

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
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_sys
-- ----------------------------

-- ----------------------------
-- Table structure for `m_user`
-- ----------------------------
DROP TABLE IF EXISTS `m_user`;
CREATE TABLE `m_user` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `user_id` varchar(36) DEFAULT NULL,
  `password` varchar(36) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_user
-- ----------------------------
INSERT INTO `m_user` VALUES ('1', 'test', 'afa9c01e4457dd59', '1');
INSERT INTO `m_user` VALUES ('2', 'user', 'afa9c01e4457dd59', '1');

-- ----------------------------
-- Table structure for `m_warn`
-- ----------------------------
DROP TABLE IF EXISTS `m_warn`;
CREATE TABLE `m_warn` (
  `id` varchar(36) NOT NULL,
  `ip` varchar(20) NOT NULL,
  `k` varchar(200) NOT NULL,
  `cond` varchar(5) NOT NULL,
  `val` float DEFAULT NULL,
  `email` varchar(500) DEFAULT NULL,
  `phone` varchar(500) DEFAULT NULL,
  `warn` varchar(30) DEFAULT NULL,
  `gmt_created` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_warn
-- ----------------------------
INSERT INTO `m_warn` VALUES ('6ffa5733-f143-493f-bf8d-b23f18a7be5a', '172.16.1.210', 'db file scattered read', '>', '0.8', '52313882@qq.com;gang0119@gmail.com', null, 'email', null);
