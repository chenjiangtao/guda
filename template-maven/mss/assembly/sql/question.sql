/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : ada

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2012-12-23 21:57:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` varchar(36) NOT NULL,
  `q` varchar(1000) NOT NULL,
  `a` text NOT NULL,
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES ('1', '请问贵公司xxx', '回答贵公司，，，', '2012-12-23 20:41:00');
INSERT INTO `question` VALUES ('2', '请问贵公司的发发发大水积分i破额外分哇额', '非damp根据奥普菲卡钦购房人广发卡就破发放大机foe万负担哦i个vaergfafewa', '2012-12-23 20:41:33');
INSERT INTO `question` VALUES ('3', '请问贵公司大家佛i阿伟发往分哇阿凡达年saw放大分哇', '放大放大分五期共同发挥发饿挖法大赛风大滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答多个反反复复反反复复反反复复反反复复反反复复反反复复吩咐', '2012-12-23 20:42:04');
INSERT INTO `question` VALUES ('4', '啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊', '反反复复反反复复反反复复反反复复反反复复反反复复反反复复', '2012-12-23 20:42:16');
INSERT INTO `question` VALUES ('5', '啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊反反复复反反复复反反复复反反复复反反复复年男男女女男男女女男男女女男男女女', '闷闷闷闷闷闷闷闷闷闷闷闷闷闷闷闷闷闷闷闷闷闷闷闷闷', '2012-12-23 20:42:52');
INSERT INTO `question` VALUES ('6', '停停停停停停停停停停停停停停停停停停停停停停停停停停停停停停停停停', '隐隐约约隐隐约约隐隐约约隐隐约约隐隐约约隐隐约约隐隐约约隐隐约约隐隐约约', '2012-12-23 20:42:49');
