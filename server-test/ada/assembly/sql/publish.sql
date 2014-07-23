/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : ada

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2012-12-23 21:56:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `publish`
-- ----------------------------
DROP TABLE IF EXISTS `publish`;
CREATE TABLE `publish` (
  `id` varchar(36) NOT NULL,
  `body` text NOT NULL,
  `seq` int(5) DEFAULT NULL,
  `type` varchar(20) NOT NULL,
  `gmt_modify` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of publish
-- ----------------------------
INSERT INTO `publish` VALUES ('1', ' 东莞市雅嵩精密模具有限公司成立于1998年,办公室设在塘厦镇林村新亚洲工业区。本公司是集模具设计与制造,注塑成型、喷油丝印,冲压,压铸和组装等二次加工为一体的专业性生产企业。厂房占地面积约7000平方米，在职员工900余人，拥有一支长期从事出口模具设计、制造的工程师与技术工人队伍。', null, 'ada', '2012-12-22 16:27:45');
INSERT INTO `publish` VALUES ('2', 'ODM合作ODM合作ODM合作ODM合作ODM合作ODM合作ODM合作', null, 'odm', '2012-12-22 22:39:53');
INSERT INTO `publish` VALUES ('3', '展会动态展会动态介绍展会动态展会动态介绍展会动态展会动态介绍展会动态展会动态介绍展会动态展会动态介绍展会动态展会动态介绍', null, 'meet', '2012-12-22 22:40:52');
INSERT INTO `publish` VALUES ('4', '<li>联系人：李小姐</br></li>\r\n<li>手机：13612666123</br></li>\r\n\r\n<li>电话：0769-82191036</br></li>\r\n<li>传真：0769-82133211</br></li>\r\n<li>网址：http://www.adasemiprecious.com</br></li>\r\n<li>地址：广东市东莞塘厦镇林村新亚洲工业区178号</br></li>', null, 'contact', '2012-12-23 12:31:05');
