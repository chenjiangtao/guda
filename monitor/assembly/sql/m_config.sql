/*
Navicat MySQL Data Transfer

Source Server         : localhost2
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : monitor

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2013-05-26 12:45:44
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `m_config`
-- ----------------------------
DROP TABLE IF EXISTS `m_config`;
CREATE TABLE `m_config` (
  `id` varchar(36) NOT NULL,
  `agent_id` varchar(36) NOT NULL,
  `sql_type` varchar(50) NOT NULL,
  `sql_url` varchar(300) NOT NULL,
  `sql_password` varchar(300) NOT NULL,
  `sql_schema` varchar(100) default NULL,
  `sql_text` text NOT NULL,
  `k` varchar(300) default NULL,
  `warn_cond` varchar(10) default NULL,
  `warn_val` float(10,0) default NULL,
  `warn_phone` varchar(500) default NULL,
  `warn_email` varchar(500) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of m_config
-- ----------------------------
