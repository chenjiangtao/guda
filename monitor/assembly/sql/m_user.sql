/*
Navicat MySQL Data Transfer

Source Server         : localhost2
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : monitor

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2013-06-05 20:03:27
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `m_user`
-- ----------------------------
DROP TABLE IF EXISTS `m_user`;
CREATE TABLE `m_user` (
  `id` varchar(36) default NULL,
  `user_id` varchar(36) default NULL,
  `password` varchar(36) default NULL,
  `status` varchar(1) default NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_user
-- ----------------------------
