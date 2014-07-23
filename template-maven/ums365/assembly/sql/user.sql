/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : tiao

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2013-05-01 08:50:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  `password` varchar(36) NOT NULL,
  `phone` varchar(21) NOT NULL,
  `email` varchar(30) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  `gmt_created` timestamp NULL DEFAULT NULL,
  `code` varchar(36) NOT NULL,
  `grade` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'afa9c01e4457dd59', '13588754574', '12@qq.com', '1', '2013-02-18 15:56:39', '', '11');
INSERT INTO `user` VALUES ('2323d6b2-aecd-4d87-a190-926e0ab8e4d5', '1', 'afa9c01e4457dd59', '15558135733', '52313882@qq.com', '1', '2013-05-01 07:55:59', '', '5');
INSERT INTO `user` VALUES ('28fd4e1d-3f98-4bc2-a0a1-d77951791445', 'bian', 'afa9c01e4457dd59', '13588754574', 'abdddc@123.com', '1', null, '5d367e83-659a-4604-90fc-81b431789f0a', '13');
INSERT INTO `user` VALUES ('2d8af348-d8a6-41c7-98e1-73d500521954', 'admin1', 'afa9c01e4457dd59', '13588754574', '123@qq.com', '0', null, '', '1');
INSERT INTO `user` VALUES ('4b412ded-7e3b-4e40-ac49-89b9cef810b5', 'gang1', 'afa9c01e4457dd59', '13588754574', '523138821@qq.com', '1', null, '', null);
INSERT INTO `user` VALUES ('51d1e2e2-8dac-463a-b4de-4729f5548b32', 'gang', 'afa9c01e4457dd59', '13588754574', 'abc@qq.com', null, null, '', null);
INSERT INTO `user` VALUES ('5a0f2150-ddd8-4db2-9e81-8b92c07c5a42', 'test5', 'afa9c01e4457dd59', '15558135733', '52313882@qq.com', '1', '2013-05-01 08:02:12', '', '5');
INSERT INTO `user` VALUES ('5c5d39e5-5f05-4dca-9a1c-842982e6968c', 'abcd', 'afa9c01e4457dd59', '13588754574', 'abc@123.com', null, null, '', null);
INSERT INTO `user` VALUES ('82c8f09e-c7cf-4f26-8172-55084030da46', 'admin3', 'afa9c01e4457dd59', '13588754574', 'abcef@123.com', '2', null, '5ed724f4-c770-4518-8fcf-b795cbd8a682', null);
INSERT INTO `user` VALUES ('947ecd8e-d11c-4814-bd5b-dc824a0454fb', 'admin2', 'afa9c01e4457dd59', '13588754574', 'abcc@123.com', '2', null, '64459f50-9103-4277-a555-976ef3ecaf47', null);
INSERT INTO `user` VALUES ('abed1e4d-ab7d-4781-8683-0ff972da1711', 'gang1234', 'afa9c01e4457dd59', '13588754574', '52313882@qq.com', '1', null, 'afd82743-a13e-4898-882e-3734fb58dfb5', null);
