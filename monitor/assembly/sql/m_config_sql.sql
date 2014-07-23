/*
Navicat MySQL Data Transfer

Source Server         : localhost2
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : monitor

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2013-05-21 14:53:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for m_config_sql
-- ----------------------------
DROP TABLE IF EXISTS `m_config_sql`;
CREATE TABLE `m_config_sql` (
  `id` varchar(36) NOT NULL,
  `agent_id` varchar(36) NOT NULL,
  `sql_type` varchar(50) NOT NULL,
  `sql_url` varchar(300) NOT NULL,
  `sql_password` varchar(300) NOT NULL,
  `sql_text` text NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_config_sql
-- ----------------------------
