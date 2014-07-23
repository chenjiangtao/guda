/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : ada

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2012-12-23 21:57:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `msg`
-- ----------------------------
DROP TABLE IF EXISTS `msg`;
CREATE TABLE `msg` (
  `id` varchar(36) NOT NULL,
  `title` varchar(300) DEFAULT NULL,
  `content` text NOT NULL,
  `contact_name` varchar(20) NOT NULL,
  `contact_info` varchar(100) NOT NULL,
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of msg
-- ----------------------------
INSERT INTO `msg` VALUES ('1', '关于', '留言内容放大风大是辅导课哇哦i官方特热', '吴先生', '联系方式', '2012-12-23 09:24:12');
INSERT INTO `msg` VALUES ('2', '短发放大', ' 短发放大费大幅降低阿婆分哇放到i皮肤打破分哇飞啊饿挖', '女士', '联系方式', '2012-12-23 09:24:14');
INSERT INTO `msg` VALUES ('a12d0a8e-9912-4a5e-a02c-e7ecb12f3b01', null, '我的问题我的问题', '好先生', '爱爱爱', '2012-12-23 09:27:25');
