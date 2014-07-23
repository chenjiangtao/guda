/*
Navicat MySQL Data Transfer

Source Server         : localhost2
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : tiao

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2013-08-05 13:15:40
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `u_notify`
-- ----------------------------
DROP TABLE IF EXISTS `u_notify`;
CREATE TABLE `u_notify` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `rule` varchar(2000) default NULL,
  `session_key` varchar(200) default NULL,
  `session_invalid_time` timestamp NULL default NULL,
  `gmt_created` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  `gmt_modify` timestamp NULL default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_notify
-- ----------------------------
INSERT INTO `u_notify` VALUES ('b8b109e6-f6b8-46a4-a045-5ba92bc3bc13', 'b3afc781-c1f6-49b7-847e-b1289ccfdcc6', 'ItemZeroStock,TradeSellerShip,TradeSuccess,TradeTimeoutRemind,TradeRated,TradeBuyerPay,TradeCreate,', null, null, '2013-08-03 23:02:31', '2013-08-03 23:02:31');
