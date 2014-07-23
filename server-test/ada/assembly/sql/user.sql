/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : ada

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2013-04-03 17:02:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `name` varchar(10) DEFAULT NULL,
  `password` varchar(36) NOT NULL,
  `email` varchar(36) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `qq` varchar(30) DEFAULT NULL,
  `sex` int(2) NOT NULL,
  `profession_id` varchar(36) NOT NULL,
  `city_id` int(11) NOT NULL,
  `status` int(2) NOT NULL,
  `grade` int(3) DEFAULT NULL,
  `height` int(3) DEFAULT NULL,
  `style` varchar(500) DEFAULT NULL,
  `advance` varchar(500) DEFAULT NULL,
  `comment` varchar(1000) DEFAULT NULL,
  `hit` int(11) DEFAULT NULL,
  `login_count` int(11) DEFAULT NULL,
  `dir` varchar(36) NOT NULL,
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
