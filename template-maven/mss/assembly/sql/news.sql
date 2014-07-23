/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : ada

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2012-12-23 21:56:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `news`
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` varchar(36) NOT NULL,
  `source` varchar(300) NOT NULL,
  `title` varchar(500) NOT NULL,
  `content` text NOT NULL,
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES ('1', '第一篇新闻来源', '第一篇新闻标题', '第一篇新闻MINA可以看成是事件驱动的。通常在网络通讯中，可以将整个过程划分为几个基本的阶段，如建立连接、数据通信、关闭连接。数据通信一般包括数据的发送和接收，由于在通信过程中，可能要多次发送和接收数据，以进行不同的业务交互。数据通信一般包括数据的发送和接收，由于在通信过程中，可能要多次发送和接收数据，以进行不同的业务交互。', '2012-12-23 12:06:35');
INSERT INTO `news` VALUES ('2', '第二篇新闻', '第二篇新闻', '第二篇新闻数据通信一般包括数据的发送和接收，由于在通信过程中，可能要多次发送和接收数据，以进行不同的业务交互。数据通信一般包括数据的发送和接收，由于在通信过程中，可能要多次发送和接收数据，以进行不同的业务交互。数据通信一般包括数据的发送和接收，由于在通信过程中，可能要多次发送和接收数据，以进行不同的业务交互。数据通信一般包括数据的发送和接收，由于在通信过程中，可能要多次发送和接收数据，以进行不同的业务交互。数据通信一般包括数据的发送和接收，由于在通信过程中，可能要多次发送和接收数据，以进行不同的业务交互。数据通信一般包括数据的发送和接收，由于在通信过程中，可能要多次发送和接收数据，以进行不同的业务交互。', '2012-12-23 12:06:39');
INSERT INTO `news` VALUES ('3', '第三篇新闻来源', '第三篇新闻标题', '第三篇内容内容', '2012-12-23 12:16:58');
INSERT INTO `news` VALUES ('4', '第四篇新闻来源', '第四篇新闻标题', '第四篇新闻内容', '2012-12-23 12:17:26');
INSERT INTO `news` VALUES ('5', '第五篇新闻来源', '第五篇新闻标题', '第五篇新闻内容', '2012-12-23 12:18:14');
