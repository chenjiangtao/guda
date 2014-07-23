/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : ada

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2012-12-23 21:56:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `prod`
-- ----------------------------
DROP TABLE IF EXISTS `prod`;
CREATE TABLE `prod` (
  `id` varchar(36) NOT NULL,
  `type` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `describ` text,
  `img` varchar(100) NOT NULL,
  `price` float(10,0) DEFAULT NULL,
  `discount` float(10,0) DEFAULT NULL,
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of prod
-- ----------------------------
INSERT INTO `prod` VALUES ('1', 'jew', 'jew1', '首饰的描述首饰的描述首饰的描述首饰的描述首饰的描述首饰的描述首饰的描述首饰的描述首饰的描述首饰的描述首饰的描述首饰的描述首饰的描述首饰的描述首饰的描述首饰的描述首饰的描述首饰的描述首饰的描述首饰的描述首饰的描述', 'item1.jpg', '1000', '1', '2012-12-23 20:06:10');
INSERT INTO `prod` VALUES ('2', 'jew', 'jew2', '首饰首饰 know your company from internet, and want to visit your factory before Chirstamas as I have a client from UK who is interested in your company, first we want to know your equipment and prodution capacity. Could tell me the contact name, contact information ? ', 'item2.jpg', '800', '1', '2012-12-23 20:06:17');
INSERT INTO `prod` VALUES ('3', 'jew', 'jew3', 'fdafdaThanks for your warm treat when we visited your factory last time. Your factory is good and have enough capacity to carry on large order production, the 5S on production site is good, we will consider to place order in your factory next time if your price is competitive. ', 'item3.jpg', '500', '1', '2012-12-23 20:06:19');
INSERT INTO `prod` VALUES ('4', 'jew', 'jew4', 'jew4 descThanks for your warm treat when we visited your factory last time. Your factory is good and have enough capacity to carry on large order production, the 5S on production site is good, we will consider to place order in your factory next time if your price is competitive. ', 'item4.jpg', '700', '1', '2012-12-23 20:06:28');
INSERT INTO `prod` VALUES ('5', 'jew', 'jew5', 'jew5描述描述Thanks for your warm treat when we visited your factory last time. Your factory is good and have enough capacity to carry on large order production, the 5S on production site is good, we will consider to place order in your factory next time if your price is competitive. ', 'item5.jpg', '300', '1', '2012-12-23 20:06:23');
INSERT INTO `prod` VALUES ('6', 'jew', 'jew6', 'jew6 miaoshuThanks for your warm treat when we visited your factory last time. Your factory is good and have enough capacity to carry on large order production, the 5S on production site is good, we will consider to place order in your factory next time if your price is competitive. ', 'item6.jpg', '1200', '1', '2012-12-23 20:06:25');
INSERT INTO `prod` VALUES ('7', 'jew', 'jew7', 'jew7777Thanks for your warm treat when we visited your factory last time. Your factory is good and have enough capacity to carry on large order production, the 5S on production site is good, we will consider to place order in your factory next time if your price is competitive. ', 'item3.jpg', '60', '1', '2012-12-23 20:06:26');
INSERT INTO `prod` VALUES ('8', 'jew', 'jew8', 'jew8888Thanks for your warm treat when we visited your factory last time. Your factory is good and have enough capacity to carry on large order production, the 5S on production site is good, we will consider to place order in your factory next time if your price is competitive. ', 'item4.jpg', '100', '1', '2012-12-23 20:06:22');
INSERT INTO `prod` VALUES ('9', 'jew', 'jew9', 'jew9999Thanks for your warm treat when we visited your factory last time. Your factory is good and have enough capacity to carry on large order production, the 5S on production site is good, we will consider to place order in your factory next time if your price is competitive. ', 'item2.jpg', '40', '1', '2012-12-23 20:06:21');
