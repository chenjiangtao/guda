/*
Navicat MySQL Data Transfer

Source Server         : localhost2
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : monitor

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2013-05-26 12:45:58
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `m_warn`
-- ----------------------------
DROP TABLE IF EXISTS `m_warn`;
CREATE TABLE `m_warn` (
  `id` varchar(36) NOT NULL,
  `ip` varchar(20) NOT NULL,
  `k` varchar(200) NOT NULL,
  `cond` varchar(5) NOT NULL,
  `val` float default NULL,
  `email` varchar(500) default NULL,
  `phone` varchar(500) default NULL,
  `warn` varchar(30) default NULL,
  `gmt_created` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_warn
-- ----------------------------
INSERT INTO `m_warn` VALUES ('1', '127.0.0.1', 'cpu', '>', '50', null, null, null, '2013-04-19 16:04:59');
INSERT INTO `m_warn` VALUES ('2', '127.0.0.1', 'mem', '>', '1000', '123@163com', '15558131234', '', '2013-04-19 19:10:17');
