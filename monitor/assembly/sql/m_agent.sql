/*
Navicat MySQL Data Transfer

Source Server         : localhost2
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : monitor

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2013-05-26 12:45:38
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
  `host` varchar(100) default NULL,
  `gmt_created` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  `comment` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_agent
-- ----------------------------
INSERT INTO `m_agent` VALUES ('39e3a0d0-0419-488b-982a-8a31ba59c05e', '192.168.1.109', '硬解析率', 'foodoon-PC', '2013-05-26 12:37:54', '硬解析率');
INSERT INTO `m_agent` VALUES ('54076c0d-49a6-4706-a122-9c2ed98d1b60', '192.168.1.109', '当前登录数', 'foodoon-PC', '2013-05-26 12:37:54', '当前登录数');
INSERT INTO `m_agent` VALUES ('75e2c0ec-453e-4ff7-9ce5-4f8768a2aeda', '192.168.1.109', '用户回滚数', 'foodoon-PC', '2013-05-26 12:35:58', '用户回滚数');
INSERT INTO `m_agent` VALUES ('92c6b1a1-df67-4c8e-858c-239de1b93135', '192.168.1.109', 'REDO WRITES', 'foodoon-PC', '2013-05-26 12:35:58', 'REDO WRITES');
INSERT INTO `m_agent` VALUES ('94dbb94f-133e-4583-b0de-500a5dbe959e', '192.168.1.109', '死锁数', 'foodoon-PC', '2013-05-26 12:35:59', '死锁数');
INSERT INTO `m_agent` VALUES ('edfe0183-ab90-4173-b1db-86bc416d5c48', '192.168.1.109', 'TABLE SCANS (LONG TABLES)', 'foodoon-PC', '2013-05-26 12:37:57', 'TABLE SCANS (LONG TABLES)');
