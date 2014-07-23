/*
MySQL Data Transfer
Source Host: localhost
Source Database: tiao
Target Host: localhost
Target Database: tiao
Date: 2012/12/15 12:53:26
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for group_contact_rel
-- ----------------------------
CREATE TABLE `group_contact_rel` (
  `id` varchar(36) NOT NULL,
  `group_id` varchar(36) NOT NULL,
  `contact_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
