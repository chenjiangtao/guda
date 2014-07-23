/*
Navicat MySQL Data Transfer

Source Server         : tiao
Source Server Version : 50529
Source Host           : localhost:3306
Source Database       : sms

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2013-05-13 13:47:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ums_action_log`
-- ----------------------------
DROP TABLE IF EXISTS `ums_action_log`;
CREATE TABLE `ums_action_log` (
  `ID` char(36) NOT NULL,
  `OPERATOR_ID` varchar(36) DEFAULT NULL,
  `OPERATOR_NAME` varchar(36) DEFAULT NULL,
  `OPERATOR_MENU` varchar(36) DEFAULT NULL,
  `OPERATOR_TYPE` varchar(36) DEFAULT NULL,
  `OPERATOR_IP` varchar(36) DEFAULT NULL,
  `COMMENT` varchar(500) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_action_log
-- ----------------------------
INSERT INTO `ums_action_log` VALUES ('daa4b8bc-e79c-42f4-98e9-7cce0687747c', 'admin', '系统管理员', '网关配置', '新增', '0:0:0:0:0:0:0:1', '新增网关', '2013-05-13 13:34:11');

-- ----------------------------
-- Table structure for `ums_app_info`
-- ----------------------------
DROP TABLE IF EXISTS `ums_app_info`;
CREATE TABLE `ums_app_info` (
  `ID` char(36) NOT NULL,
  `APP_ID` varchar(16) NOT NULL,
  `APP_NAME` varchar(90) NOT NULL,
  `APP_CODE` varchar(16) NOT NULL,
  `IP` varchar(500) DEFAULT NULL,
  `PORT` varchar(200) DEFAULT NULL,
  `USERNAME` varchar(50) NOT NULL,
  `PASSWORD` varchar(50) NOT NULL,
  `FLOW_DAY` int(11) DEFAULT NULL,
  `FLOW_MONTH` int(11) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `FEE` int(11) DEFAULT NULL,
  `FEE_TYPE` int(11) DEFAULT NULL,
  `SIGN_NAME` varchar(30) DEFAULT NULL,
  `STATUS` char(1) NOT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `GMT_MODIFIED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `IS_OUT_PROV` varchar(1) DEFAULT NULL,
  `EMAIL_MD5` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_app_info
-- ----------------------------
INSERT INTO `ums_app_info` VALUES ('21f183c3-bc3b-48eb-b089-080a06af75fa', '0000', 'ums', '5555', '127.0.0.1', '8080', 'umsname2', '196a4758191e42f7', null, null, '1', '1', '1', '省电力', '1', '2012-09-03 09:54:33', '2012-09-18 11:03:44', null, null);

-- ----------------------------
-- Table structure for `ums_app_sub`
-- ----------------------------
DROP TABLE IF EXISTS `ums_app_sub`;
CREATE TABLE `ums_app_sub` (
  `ID` char(36) NOT NULL,
  `APP_ID` varchar(36) NOT NULL,
  `APP_SUB_ID` varchar(36) NOT NULL,
  `APP_SUB_CODE` varchar(16) NOT NULL,
  `APP_SUB_NAME` varchar(50) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `GMT_MODIFIED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_app_sub
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_area`
-- ----------------------------
DROP TABLE IF EXISTS `ums_area`;
CREATE TABLE `ums_area` (
  `ID` varchar(36) NOT NULL,
  `AREA_CODE` varchar(36) NOT NULL,
  `AREA_NAME` varchar(36) DEFAULT NULL,
  `PARENT_ID` varchar(36) DEFAULT NULL,
  `ORDER` int(11) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_area
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_black_list`
-- ----------------------------
DROP TABLE IF EXISTS `ums_black_list`;
CREATE TABLE `ums_black_list` (
  `ID` char(36) NOT NULL,
  `USER_ID` varchar(36) NOT NULL,
  `APP_ID` varchar(36) NOT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `GMT_MODIFIED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_black_list
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_contact`
-- ----------------------------
DROP TABLE IF EXISTS `ums_contact`;
CREATE TABLE `ums_contact` (
  `ID` varchar(36) NOT NULL,
  `USER_ID` varchar(36) NOT NULL,
  `USER_NAME` varchar(36) DEFAULT NULL,
  `PHONE` varchar(20) NOT NULL,
  `EMAIL` varchar(36) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_contact
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_flow_log`
-- ----------------------------
DROP TABLE IF EXISTS `ums_flow_log`;
CREATE TABLE `ums_flow_log` (
  `ID` char(36) NOT NULL,
  `APP_ID` varchar(16) NOT NULL,
  `FLOW_MON_TOTAL` int(11) DEFAULT NULL,
  `FLOW_TODAY` int(11) NOT NULL,
  `GMT_CREATED` date NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_flow_log
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_gateway_info`
-- ----------------------------
DROP TABLE IF EXISTS `ums_gateway_info`;
CREATE TABLE `ums_gateway_info` (
  `ID` char(36) NOT NULL,
  `GATEWAY_NAME` varchar(36) NOT NULL,
  `GATEWAY_STATE` char(1) DEFAULT NULL,
  `TYPE` varchar(10) NOT NULL,
  `SP_NUMBER` varchar(20) NOT NULL,
  `HOST` varchar(20) NOT NULL,
  `PORT` int(11) NOT NULL,
  `LOCAL_HOST` varchar(20) DEFAULT NULL,
  `LOCAL_PORT` int(11) DEFAULT NULL,
  `SOURCE_ADDR` varchar(20) NOT NULL,
  `SHARED_SECRET` varchar(20) NOT NULL,
  `VERSION` varchar(5) NOT NULL,
  `READ_TIMEOUT` int(11) DEFAULT NULL,
  `RECONNECT_INTERVAL` int(11) DEFAULT NULL,
  `TRANSACTION_TIMEOUT` int(11) DEFAULT NULL,
  `HEARTBEAT_INTERVAL` int(11) DEFAULT NULL,
  `HEARTBEAT_NORESPONSEOUT` int(11) DEFAULT NULL,
  `DEBUG` int(11) DEFAULT NULL,
  `CORP_ID` varchar(20) DEFAULT NULL,
  `STATUS` char(1) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `GMT_MODIFIED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `IS_OUT_PROV` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_gateway_info
-- ----------------------------
INSERT INTO `ums_gateway_info` VALUES ('d5a6c32f-5c35-455e-8460-a26b36cc8aa7', 'cmpp1', '1', 'CMPP', '106575', '127.0.0.1', '8891', '', null, 'qb581', '1234', '1', '5', '5', '5', '5', '5', null, '', '2', '2013-05-13 13:34:11', '2013-05-13 13:34:11', '');

-- ----------------------------
-- Table structure for `ums_gateway_rule`
-- ----------------------------
DROP TABLE IF EXISTS `ums_gateway_rule`;
CREATE TABLE `ums_gateway_rule` (
  `ID` char(36) NOT NULL,
  `GATEWAY_ID` varchar(50) NOT NULL,
  `RECEIVE_REGX` varchar(300) DEFAULT NULL,
  `APP_ID_REGX` varchar(1500) DEFAULT NULL,
  `CONTENT_REGX` varchar(600) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `GMT_MODIFIED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_gateway_rule
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_group`
-- ----------------------------
DROP TABLE IF EXISTS `ums_group`;
CREATE TABLE `ums_group` (
  `ID` char(36) NOT NULL,
  `GROUP_NAME` varchar(90) NOT NULL,
  `USER_ID` varchar(36) NOT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_group
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_group_user_rel`
-- ----------------------------
DROP TABLE IF EXISTS `ums_group_user_rel`;
CREATE TABLE `ums_group_user_rel` (
  `ID` char(36) NOT NULL,
  `GROUP_ID` varchar(36) NOT NULL,
  `USER_DESC` varchar(36) NOT NULL,
  `COMMENTS` varchar(36) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_group_user_rel
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_keyword_info`
-- ----------------------------
DROP TABLE IF EXISTS `ums_keyword_info`;
CREATE TABLE `ums_keyword_info` (
  `ID` char(36) NOT NULL,
  `KEY_WORD` varchar(36) NOT NULL,
  `APP_ID` varchar(36) NOT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `GMT_MODIFIED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_keyword_info
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_lock_app`
-- ----------------------------
DROP TABLE IF EXISTS `ums_lock_app`;
CREATE TABLE `ums_lock_app` (
  `APP_ID` varchar(6) NOT NULL,
  `HOST` varchar(60) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`APP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_lock_app
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_msg_draft`
-- ----------------------------
DROP TABLE IF EXISTS `ums_msg_draft`;
CREATE TABLE `ums_msg_draft` (
  `ID` char(36) NOT NULL,
  `USER_ID` varchar(60) DEFAULT NULL,
  `RECV_ID` varchar(2000) DEFAULT NULL,
  `CONTENT` varchar(2000) DEFAULT NULL,
  `ACK` int(11) DEFAULT NULL,
  `SEND_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `VALID_TIME` varchar(10) DEFAULT NULL,
  `IDENTITY` char(1) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `GMT_MODIFIED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_msg_draft
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_msg_in`
-- ----------------------------
DROP TABLE IF EXISTS `ums_msg_in`;
CREATE TABLE `ums_msg_in` (
  `ID` char(36) NOT NULL,
  `BATCH_NO` varchar(14) NOT NULL,
  `SERIAL_NO` varchar(14) NOT NULL,
  `SEND_ID` varchar(60) NOT NULL,
  `RECV_ID` varchar(60) NOT NULL,
  `CONTENT` varchar(2000) NOT NULL,
  `MSG_TYPE` int(11) NOT NULL,
  `STATUS` varchar(1) NOT NULL,
  `APP_ID` varchar(12) NOT NULL,
  `SUB_APP` varchar(6) DEFAULT NULL,
  `APP_SERIAL_NO` varchar(35) DEFAULT NULL,
  `MEDIA_ID` varchar(36) DEFAULT NULL,
  `RET_CODE` varchar(4) DEFAULT NULL,
  `ERROR_MSG` varchar(500) DEFAULT NULL,
  `ACK` int(11) NOT NULL,
  `REPLY` varchar(30) DEFAULT NULL,
  `DOCOUNT` int(11) NOT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `GMT_MODIFIED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_msg_in
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_msg_in_error`
-- ----------------------------
DROP TABLE IF EXISTS `ums_msg_in_error`;
CREATE TABLE `ums_msg_in_error` (
  `ID` char(36) NOT NULL,
  `SEND_ID` varchar(60) DEFAULT NULL,
  `RECV_ID` varchar(60) DEFAULT NULL,
  `CONTENT` varchar(2000) DEFAULT NULL,
  `MSG_TYPE` int(11) DEFAULT NULL,
  `APP_ID` varchar(12) DEFAULT NULL,
  `SUB_APP` varchar(6) DEFAULT NULL,
  `APP_SERIAL_NO` varchar(35) DEFAULT NULL,
  `MEDIA_ID` varchar(36) DEFAULT NULL,
  `RET_CODE` varchar(4) DEFAULT NULL,
  `ERROR_MSG` varchar(200) DEFAULT NULL,
  `ACK` int(11) NOT NULL,
  `REPLY` varchar(30) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_msg_in_error
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_msg_in_rule`
-- ----------------------------
DROP TABLE IF EXISTS `ums_msg_in_rule`;
CREATE TABLE `ums_msg_in_rule` (
  `ID` char(36) NOT NULL,
  `WORD` varchar(60) NOT NULL,
  `APP_ID` varchar(10) NOT NULL,
  `SUB_APP_ID` varchar(10) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `GMT_MODIFIED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_msg_in_rule
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_msg_out`
-- ----------------------------
DROP TABLE IF EXISTS `ums_msg_out`;
CREATE TABLE `ums_msg_out` (
  `ID` char(36) NOT NULL,
  `BATCH_NO` varchar(14) NOT NULL,
  `SERIAL_NO` varchar(14) NOT NULL,
  `SEND_ID` varchar(36) DEFAULT NULL,
  `USER_ID` varchar(36) DEFAULT NULL,
  `RECV_ID` varchar(60) NOT NULL,
  `CONTENT` varchar(6000) NOT NULL,
  `MSG_TYPE` int(11) NOT NULL,
  `STATUS` varchar(36) NOT NULL,
  `APP_ID` varchar(12) NOT NULL,
  `SUB_APP` varchar(6) DEFAULT NULL,
  `APP_SERIAL_NO` varchar(35) DEFAULT NULL,
  `MEDIA_ID` varchar(36) DEFAULT NULL,
  `RET_CODE` varchar(4) DEFAULT NULL,
  `ERROR_MSG` varchar(200) DEFAULT NULL,
  `ACK` int(11) NOT NULL,
  `REPLY` varchar(30) DEFAULT NULL,
  `RETRY` int(11) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `BATCH_MODE` char(1) DEFAULT NULL,
  `CONTENT_MODE` int(11) DEFAULT NULL,
  `TIME_SET_FLAG` char(1) DEFAULT NULL,
  `SET_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `REPLY_DES` varchar(64) DEFAULT NULL,
  `FEE` int(11) DEFAULT NULL,
  `FEE_TYPE` int(11) DEFAULT NULL,
  `MSG_ID` varchar(20) DEFAULT NULL,
  `DOCOUNT` int(11) DEFAULT NULL,
  `SEND_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `VALID_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `GROUP_SERIAL` varchar(36) DEFAULT NULL,
  `ORG_NO` varchar(36) DEFAULT NULL,
  `FLOW_NO` varchar(36) DEFAULT NULL,
  `CREATE_USER` varchar(36) DEFAULT NULL,
  `BIZ_TYPE` varchar(150) DEFAULT NULL,
  `BIZ_NAME` varchar(150) DEFAULT NULL,
  `IDENTITY` char(1) DEFAULT NULL,
  `MOBILE_STATUS` char(1) DEFAULT NULL,
  `TEMPLATE_ID` varchar(10) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `GMT_MODIFIED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `HOST` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_msg_out
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_msg_out_ucs`
-- ----------------------------
DROP TABLE IF EXISTS `ums_msg_out_ucs`;
CREATE TABLE `ums_msg_out_ucs` (
  `ID` char(36) NOT NULL,
  `BATCH_NO` varchar(14) NOT NULL,
  `SERIAL_NO` varchar(14) NOT NULL,
  `SEND_ID` varchar(36) DEFAULT NULL,
  `USER_ID` varchar(36) DEFAULT NULL,
  `RECV_ID` varchar(60) NOT NULL,
  `CONTENT` varchar(6000) NOT NULL,
  `MSG_TYPE` int(11) NOT NULL,
  `STATUS` varchar(36) NOT NULL,
  `APP_ID` varchar(12) NOT NULL,
  `SUB_APP` varchar(6) DEFAULT NULL,
  `APP_SERIAL_NO` varchar(35) DEFAULT NULL,
  `MEDIA_ID` varchar(36) DEFAULT NULL,
  `RET_CODE` varchar(4) DEFAULT NULL,
  `ERROR_MSG` varchar(200) DEFAULT NULL,
  `ACK` int(11) NOT NULL,
  `REPLY` varchar(30) DEFAULT NULL,
  `RETRY` int(11) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `BATCH_MODE` char(1) DEFAULT NULL,
  `CONTENT_MODE` int(11) DEFAULT NULL,
  `TIME_SET_FLAG` char(1) DEFAULT NULL,
  `SET_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `REPLY_DES` varchar(64) DEFAULT NULL,
  `FEE` int(11) DEFAULT NULL,
  `FEE_TYPE` int(11) DEFAULT NULL,
  `MSG_ID` varchar(20) DEFAULT NULL,
  `DOCOUNT` int(11) DEFAULT NULL,
  `SEND_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `VALID_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `GROUP_SERIAL` varchar(36) DEFAULT NULL,
  `ORG_NO` varchar(36) DEFAULT NULL,
  `FLOW_NO` varchar(36) DEFAULT NULL,
  `CREATE_USER` varchar(36) DEFAULT NULL,
  `BIZ_TYPE` varchar(150) DEFAULT NULL,
  `BIZ_NAME` varchar(150) DEFAULT NULL,
  `IDENTITY` char(1) DEFAULT NULL,
  `MOBILE_STATUS` char(1) DEFAULT NULL,
  `TEMPLATE_ID` varchar(10) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `GMT_MODIFIED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `HOST` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_msg_out_ucs
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_msg_template`
-- ----------------------------
DROP TABLE IF EXISTS `ums_msg_template`;
CREATE TABLE `ums_msg_template` (
  `ID` varchar(36) NOT NULL,
  `TEMPLATE_ID` varchar(10) NOT NULL,
  `TYPE` varchar(10) NOT NULL,
  `CONTENT` varchar(2000) NOT NULL,
  `MSG_TYPE` char(1) DEFAULT NULL,
  `APP_ID` varchar(10) NOT NULL,
  `SUB_APP_ID` varchar(10) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `START_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `END_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `RECV_COMMENTS` varchar(500) DEFAULT NULL,
  `VALID_TIME_SCOPE` varchar(20) DEFAULT NULL,
  `ERROR_ACTION` char(1) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `GMT_MODIFIED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `BIZ_NAME` varchar(150) DEFAULT NULL,
  `SUB_BIZ_TYPE` varchar(150) DEFAULT NULL,
  `BIZ_TYPE` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_msg_template
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_organization`
-- ----------------------------
DROP TABLE IF EXISTS `ums_organization`;
CREATE TABLE `ums_organization` (
  `ID` char(36) NOT NULL,
  `ORG_NAME` varchar(1000) NOT NULL,
  `PARENT_ID` varchar(36) DEFAULT NULL,
  `ORDER` int(11) DEFAULT NULL,
  `ORG_MANAGER` varchar(36) DEFAULT NULL,
  `ORG_VICE_MANAGER` varchar(36) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_organization
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_out_app_reply`
-- ----------------------------
DROP TABLE IF EXISTS `ums_out_app_reply`;
CREATE TABLE `ums_out_app_reply` (
  `ID` char(36) NOT NULL,
  `REPLY` varchar(16) DEFAULT NULL,
  `REPLY_DES` int(11) NOT NULL,
  `SEND_ID` varchar(16) DEFAULT NULL,
  `RECEIVE_ID` varchar(21) DEFAULT NULL,
  `BATCH_NO` varchar(14) DEFAULT NULL,
  `SERIAL_NO` varchar(14) DEFAULT NULL,
  `APP_ID` varchar(12) DEFAULT NULL,
  `SUB_APP` varchar(6) DEFAULT NULL,
  `APP_SERIAL_NO` varchar(35) DEFAULT NULL,
  `ACK` int(11) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_out_app_reply
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_out_reply`
-- ----------------------------
DROP TABLE IF EXISTS `ums_out_reply`;
CREATE TABLE `ums_out_reply` (
  `ID` char(36) NOT NULL,
  `REPLY` varchar(16) NOT NULL,
  `REPLY_DES` int(11) NOT NULL,
  `SEND_ID` varchar(16) DEFAULT NULL,
  `RECEIVE_ID` varchar(21) DEFAULT NULL,
  `BATCH_NO` varchar(14) DEFAULT NULL,
  `SERIAL_NO` varchar(14) DEFAULT NULL,
  `APP_ID` varchar(12) DEFAULT NULL,
  `SUB_APP` varchar(6) DEFAULT NULL,
  `APP_SERIAL_NO` varchar(35) DEFAULT NULL,
  `ACK` int(11) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_out_reply
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_qrtz_blob_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `ums_qrtz_blob_triggers`;
CREATE TABLE `ums_qrtz_blob_triggers` (
  `trigger_name` varchar(80) NOT NULL,
  `trigger_group` varchar(80) NOT NULL,
  `blob_data` blob,
  PRIMARY KEY (`trigger_name`,`trigger_group`),
  CONSTRAINT `ums_qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`trigger_name`, `trigger_group`) REFERENCES `ums_qrtz_triggers` (`trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_qrtz_calendars`
-- ----------------------------
DROP TABLE IF EXISTS `ums_qrtz_calendars`;
CREATE TABLE `ums_qrtz_calendars` (
  `calendar_name` varchar(80) NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_qrtz_cron_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `ums_qrtz_cron_triggers`;
CREATE TABLE `ums_qrtz_cron_triggers` (
  `trigger_name` varchar(80) NOT NULL,
  `trigger_group` varchar(80) NOT NULL,
  `cron_expression` varchar(120) NOT NULL,
  `time_zone_id` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`trigger_name`,`trigger_group`),
  CONSTRAINT `ums_qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`trigger_name`, `trigger_group`) REFERENCES `ums_qrtz_triggers` (`trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_qrtz_cron_triggers
-- ----------------------------
INSERT INTO `ums_qrtz_cron_triggers` VALUES ('filingTriggerBean', 'DEFAULT', '0 0/10 18,19,20,21,22,23,0,1,2,3,4,5 * * ?', 'Asia/Shanghai');
INSERT INTO `ums_qrtz_cron_triggers` VALUES ('statTriggerBean', 'DEFAULT', ' 0 0 5 * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for `ums_qrtz_fired_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `ums_qrtz_fired_triggers`;
CREATE TABLE `ums_qrtz_fired_triggers` (
  `entry_id` varchar(95) NOT NULL,
  `trigger_name` varchar(80) NOT NULL,
  `trigger_group` varchar(80) NOT NULL,
  `is_volatile` int(11) NOT NULL,
  `instance_name` varchar(80) NOT NULL,
  `fired_time` bigint(20) NOT NULL,
  `priority` int(11) NOT NULL,
  `state` varchar(16) NOT NULL,
  `job_name` varchar(80) DEFAULT NULL,
  `job_group` varchar(80) DEFAULT NULL,
  `is_stateful` int(11) DEFAULT NULL,
  `requests_recovery` int(11) DEFAULT NULL,
  PRIMARY KEY (`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_qrtz_fired_triggers
-- ----------------------------
INSERT INTO `ums_qrtz_fired_triggers` VALUES ('gang-pc13684231821351368423185510', 'clusterMonitorTriggerBean', 'DEFAULT', '0', 'gang-pc1368423182135', '1368424024200', '5', 'EXECUTING', 'clusterMonitorJobDetail', 'DEFAULT', '0', '0');

-- ----------------------------
-- Table structure for `ums_qrtz_job_details`
-- ----------------------------
DROP TABLE IF EXISTS `ums_qrtz_job_details`;
CREATE TABLE `ums_qrtz_job_details` (
  `job_name` varchar(80) NOT NULL,
  `job_group` varchar(80) NOT NULL,
  `description` varchar(120) DEFAULT NULL,
  `job_class_name` varchar(128) NOT NULL,
  `is_durable` int(11) NOT NULL,
  `is_volatile` int(11) NOT NULL,
  `is_stateful` int(11) NOT NULL,
  `requests_recovery` int(11) NOT NULL,
  `job_data` blob,
  PRIMARY KEY (`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_qrtz_job_details
-- ----------------------------
INSERT INTO `ums_qrtz_job_details` VALUES ('clusterMonitorJobDetail', 'DEFAULT', null, 'net.zoneland.ums.biz.msg.quartz.support.MethodInvokingJobDetailFactoryBean$MethodInvokingJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000274000C7461726765744F626A656374737200316E65742E7A6F6E656C616E642E756D732E62697A2E6D73672E71756172747A2E436C75737465724D6F6E69746F724A6F620000000000000001020000787074000C7461726765744D6574686F6474000F65786563757465496E7465726E616C7800);
INSERT INTO `ums_qrtz_job_details` VALUES ('filingJobDetail', 'DEFAULT', null, 'net.zoneland.ums.biz.msg.quartz.support.MethodInvokingJobDetailFactoryBean$MethodInvokingJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000274000C7461726765744F626A656374737200296E65742E7A6F6E656C616E642E756D732E62697A2E6D73672E71756172747A2E46696C696E674A6F62C0002AEAADD6CB6302000149000E726570656174496E74657276616C78700000000374000C7461726765744D6574686F6474000F65786563757465496E7465726E616C7800);
INSERT INTO `ums_qrtz_job_details` VALUES ('sendDelaySmsJobDetail', 'DEFAULT', null, 'net.zoneland.ums.biz.msg.quartz.support.MethodInvokingJobDetailFactoryBean$MethodInvokingJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000274000C7461726765744F626A6563747372002F6E65742E7A6F6E656C616E642E756D732E62697A2E6D73672E71756172747A2E53656E6444656C6179536D734A6F62560EE228DEE18252020000787074000C7461726765744D6574686F6474000F65786563757465496E7465726E616C7800);
INSERT INTO `ums_qrtz_job_details` VALUES ('sendFailureSmsJobDetail', 'DEFAULT', null, 'net.zoneland.ums.biz.msg.quartz.support.MethodInvokingJobDetailFactoryBean$MethodInvokingJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000274000C7461726765744F626A656374737200316E65742E7A6F6E656C616E642E756D732E62697A2E6D73672E71756172747A2E53656E644661696C757265536D734A6F620F3A8EF467EAA90B020000787074000C7461726765744D6574686F6474000F65786563757465496E7465726E616C7800);
INSERT INTO `ums_qrtz_job_details` VALUES ('statJobDetail', 'DEFAULT', null, 'net.zoneland.ums.biz.msg.quartz.support.MethodInvokingJobDetailFactoryBean$MethodInvokingJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000274000C7461726765744F626A656374737200276E65742E7A6F6E656C616E642E756D732E62697A2E6D73672E71756172747A2E537461744A6F6289E878B4445CA494020000787074000C7461726765744D6574686F6474000F65786563757465496E7465726E616C7800);

-- ----------------------------
-- Table structure for `ums_qrtz_job_listeners`
-- ----------------------------
DROP TABLE IF EXISTS `ums_qrtz_job_listeners`;
CREATE TABLE `ums_qrtz_job_listeners` (
  `job_name` varchar(80) NOT NULL,
  `job_group` varchar(80) NOT NULL,
  `job_listener` varchar(80) NOT NULL,
  PRIMARY KEY (`job_name`,`job_group`,`job_listener`),
  CONSTRAINT `ums_qrtz_job_listeners_ibfk_1` FOREIGN KEY (`job_name`, `job_group`) REFERENCES `ums_qrtz_job_details` (`job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_qrtz_job_listeners
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_qrtz_locks`
-- ----------------------------
DROP TABLE IF EXISTS `ums_qrtz_locks`;
CREATE TABLE `ums_qrtz_locks` (
  `lock_name` varchar(40) NOT NULL,
  PRIMARY KEY (`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_qrtz_locks
-- ----------------------------
INSERT INTO `ums_qrtz_locks` VALUES ('CALENDAR_ACCESS');
INSERT INTO `ums_qrtz_locks` VALUES ('JOB_ACCESS');
INSERT INTO `ums_qrtz_locks` VALUES ('MISFIRE_ACCESS');
INSERT INTO `ums_qrtz_locks` VALUES ('STATE_ACCESS');
INSERT INTO `ums_qrtz_locks` VALUES ('TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for `ums_qrtz_paused_trigger_grps`
-- ----------------------------
DROP TABLE IF EXISTS `ums_qrtz_paused_trigger_grps`;
CREATE TABLE `ums_qrtz_paused_trigger_grps` (
  `trigger_group` varchar(80) NOT NULL,
  PRIMARY KEY (`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_qrtz_scheduler_state`
-- ----------------------------
DROP TABLE IF EXISTS `ums_qrtz_scheduler_state`;
CREATE TABLE `ums_qrtz_scheduler_state` (
  `instance_name` varchar(80) NOT NULL,
  `last_checkin_time` bigint(20) NOT NULL,
  `checkin_interval` bigint(20) NOT NULL,
  PRIMARY KEY (`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_qrtz_scheduler_state
-- ----------------------------
INSERT INTO `ums_qrtz_scheduler_state` VALUES ('gang-pc1368423182135', '1368424044638', '2000');

-- ----------------------------
-- Table structure for `ums_qrtz_simple_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `ums_qrtz_simple_triggers`;
CREATE TABLE `ums_qrtz_simple_triggers` (
  `trigger_name` varchar(80) NOT NULL,
  `trigger_group` varchar(80) NOT NULL,
  `repeat_count` bigint(20) NOT NULL,
  `repeat_interval` bigint(20) NOT NULL,
  `times_triggered` bigint(20) NOT NULL,
  PRIMARY KEY (`trigger_name`,`trigger_group`),
  CONSTRAINT `ums_qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`trigger_name`, `trigger_group`) REFERENCES `ums_qrtz_triggers` (`trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_qrtz_simple_triggers
-- ----------------------------
INSERT INTO `ums_qrtz_simple_triggers` VALUES ('clusterMonitorTriggerBean', 'DEFAULT', '-1', '5', '962739');
INSERT INTO `ums_qrtz_simple_triggers` VALUES ('sendDelaySmsTriggerBean', 'DEFAULT', '-1', '30000', '161');
INSERT INTO `ums_qrtz_simple_triggers` VALUES ('sendFailureSmsTriggerBean', 'DEFAULT', '-1', '30000', '161');

-- ----------------------------
-- Table structure for `ums_qrtz_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `ums_qrtz_triggers`;
CREATE TABLE `ums_qrtz_triggers` (
  `trigger_name` varchar(80) NOT NULL,
  `trigger_group` varchar(80) NOT NULL,
  `job_name` varchar(80) NOT NULL,
  `job_group` varchar(80) NOT NULL,
  `is_volatile` int(11) NOT NULL,
  `description` varchar(120) DEFAULT NULL,
  `next_fire_time` bigint(20) DEFAULT NULL,
  `prev_fire_time` bigint(20) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `trigger_state` varchar(16) NOT NULL,
  `trigger_type` varchar(8) NOT NULL,
  `start_time` bigint(20) NOT NULL,
  `end_time` bigint(20) DEFAULT NULL,
  `calendar_name` varchar(80) DEFAULT NULL,
  `misfire_instr` smallint(6) DEFAULT NULL,
  `job_data` blob,
  PRIMARY KEY (`trigger_name`,`trigger_group`),
  KEY `job_name` (`job_name`,`job_group`),
  CONSTRAINT `ums_qrtz_triggers_ibfk_1` FOREIGN KEY (`job_name`, `job_group`) REFERENCES `ums_qrtz_job_details` (`job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_qrtz_triggers
-- ----------------------------
INSERT INTO `ums_qrtz_triggers` VALUES ('clusterMonitorTriggerBean', 'DEFAULT', 'clusterMonitorJobDetail', 'DEFAULT', '0', null, '1368424024205', '1368424024200', '5', 'WAITING', 'SIMPLE', '1368419210510', '0', null, '0', '');
INSERT INTO `ums_qrtz_triggers` VALUES ('filingTriggerBean', 'DEFAULT', 'filingJobDetail', 'DEFAULT', '0', null, '1368439200000', '-1', '5', 'WAITING', 'CRON', '1368419150000', '0', null, '0', '');
INSERT INTO `ums_qrtz_triggers` VALUES ('sendDelaySmsTriggerBean', 'DEFAULT', 'sendDelaySmsJobDetail', 'DEFAULT', '0', null, '1368424040566', '1368424010566', '5', 'WAITING', 'SIMPLE', '1368419210566', '0', null, '0', '');
INSERT INTO `ums_qrtz_triggers` VALUES ('sendFailureSmsTriggerBean', 'DEFAULT', 'sendFailureSmsJobDetail', 'DEFAULT', '0', null, '1368424040500', '1368424010500', '5', 'WAITING', 'SIMPLE', '1368419210500', '0', null, '0', '');
INSERT INTO `ums_qrtz_triggers` VALUES ('statTriggerBean', 'DEFAULT', 'statJobDetail', 'DEFAULT', '0', null, '1368478800000', '-1', '5', 'WAITING', 'CRON', '1368419150000', '0', null, '0', '');

-- ----------------------------
-- Table structure for `ums_qrtz_trigger_listeners`
-- ----------------------------
DROP TABLE IF EXISTS `ums_qrtz_trigger_listeners`;
CREATE TABLE `ums_qrtz_trigger_listeners` (
  `trigger_name` varchar(80) NOT NULL,
  `trigger_group` varchar(80) NOT NULL,
  `trigger_listener` varchar(80) NOT NULL,
  PRIMARY KEY (`trigger_name`,`trigger_group`,`trigger_listener`),
  CONSTRAINT `ums_qrtz_trigger_listeners_ibfk_1` FOREIGN KEY (`trigger_name`, `trigger_group`) REFERENCES `ums_qrtz_triggers` (`trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_qrtz_trigger_listeners
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_role`
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role` (
  `ID` varchar(36) NOT NULL,
  `ROLE_NAME` varchar(36) NOT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_role
-- ----------------------------
INSERT INTO `ums_role` VALUES ('1', 'ROLE_NORMAL', '2012-08-14 16:55:37');
INSERT INTO `ums_role` VALUES ('2', 'ROLE_APP', '2012-08-14 16:55:37');
INSERT INTO `ums_role` VALUES ('3', 'ROLE_ADMIN', '2012-08-14 16:55:37');

-- ----------------------------
-- Table structure for `ums_stat`
-- ----------------------------
DROP TABLE IF EXISTS `ums_stat`;
CREATE TABLE `ums_stat` (
  `ID` char(36) NOT NULL,
  `STAT_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `APP_ID` varchar(4) NOT NULL,
  `MSG_TYPE` varchar(6) NOT NULL,
  `CMPP_106` int(11) DEFAULT NULL,
  `CMPP_95598` int(11) DEFAULT NULL,
  `SGIP_106` int(11) DEFAULT NULL,
  `SGIP_95598` int(11) DEFAULT NULL,
  `SMGP_106` int(11) DEFAULT NULL,
  `SMGP_95598` int(11) DEFAULT NULL,
  `STAT_106` int(11) DEFAULT NULL,
  `STAT_95598` int(11) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_stat
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_sync_org`
-- ----------------------------
DROP TABLE IF EXISTS `ums_sync_org`;
CREATE TABLE `ums_sync_org` (
  `ID` varchar(36) NOT NULL,
  `ORG_NAME` varchar(1000) DEFAULT NULL,
  `PARENT_ID` varchar(36) DEFAULT NULL,
  `OPER_TYPE` char(1) DEFAULT NULL,
  `GMT_MODIFIED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_sync_org
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_sync_user`
-- ----------------------------
DROP TABLE IF EXISTS `ums_sync_user`;
CREATE TABLE `ums_sync_user` (
  `ID` varchar(36) NOT NULL,
  `USER_ID` varchar(36) NOT NULL,
  `USER_NAME` varchar(36) DEFAULT NULL,
  `PASSWORD` varchar(36) DEFAULT NULL,
  `PHONE` varchar(20) DEFAULT NULL,
  `ORGANIZATION_ID` varchar(36) DEFAULT NULL,
  `ORG_NAME` varchar(2000) DEFAULT NULL,
  `OPER_TYPE` varchar(1) DEFAULT NULL,
  `GMT_MODIFIED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_sync_user
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_tel_describe`
-- ----------------------------
DROP TABLE IF EXISTS `ums_tel_describe`;
CREATE TABLE `ums_tel_describe` (
  `TEL` char(7) NOT NULL,
  `PROVINCE` varchar(30) DEFAULT NULL,
  `SIMTYPE` varchar(50) DEFAULT NULL,
  `CITY` varchar(30) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_tel_describe
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_user_area_rel`
-- ----------------------------
DROP TABLE IF EXISTS `ums_user_area_rel`;
CREATE TABLE `ums_user_area_rel` (
  `ID` varchar(36) NOT NULL,
  `AREA_ID` varchar(36) NOT NULL,
  `USER_ID` varchar(36) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_user_area_rel
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_user_info`
-- ----------------------------
DROP TABLE IF EXISTS `ums_user_info`;
CREATE TABLE `ums_user_info` (
  `ID` varchar(36) NOT NULL,
  `USER_ID` varchar(36) NOT NULL,
  `USER_NAME` varchar(36) NOT NULL,
  `PASSWORD` varchar(36) NOT NULL,
  `EMPLOYEE_ID` varchar(36) DEFAULT NULL,
  `PHONE` varchar(36) NOT NULL,
  `PHONE_SEC` varchar(36) DEFAULT NULL,
  `EMAIL` varchar(36) DEFAULT NULL,
  `ORGANIZATION_ID` varchar(36) NOT NULL,
  `ORG_ADMIN` char(1) DEFAULT NULL,
  `QQ` int(11) DEFAULT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `STATUS` varchar(1) DEFAULT NULL,
  `ORG_NAME` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_user_info
-- ----------------------------
INSERT INTO `ums_user_info` VALUES ('753adf42-04e1-4d50-88e9-e335ca142896', 'admin', '系统管理员', 'ac59075b964b0715', '7', '13566402695', null, '635545642@qq.com', 'd16a41ea-3127-4f78-9763-116903841947', '1', null, '2013-05-13 13:01:57', '1', null);
INSERT INTO `ums_user_info` VALUES ('753adf42-04e1-4d50-88e9-e335ca142897', 'appadmin', '应用管理员', 'ac59075b964b0715', '8', '13578768999', null, '387545642@qq.com', 'd16a41ea-3127-4f78-9763-116903841947', '1', null, '2013-05-13 13:01:58', '1', null);
INSERT INTO `ums_user_info` VALUES ('753adf42-04e1-4d50-88e9-e335ca142898', 'normal', '普通用户', '52d04dc20036dbd8', '9', '15813585858', null, '875445642@qq.com', 'd16a41ea-3127-4f78-9763-116903841947', '0', null, '2013-05-13 13:02:00', '1', null);

-- ----------------------------
-- Table structure for `ums_user_role_app_rel`
-- ----------------------------
DROP TABLE IF EXISTS `ums_user_role_app_rel`;
CREATE TABLE `ums_user_role_app_rel` (
  `ID` char(36) NOT NULL,
  `ROLE_USER_REL_ID` varchar(36) NOT NULL,
  `APP_ID` varchar(36) NOT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_user_role_app_rel
-- ----------------------------

-- ----------------------------
-- Table structure for `ums_user_role_rel`
-- ----------------------------
DROP TABLE IF EXISTS `ums_user_role_rel`;
CREATE TABLE `ums_user_role_rel` (
  `ID` varchar(36) NOT NULL,
  `ROLE_NAME` varchar(36) NOT NULL,
  `USER_ID` varchar(36) NOT NULL,
  `GMT_CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ums_user_role_rel
-- ----------------------------
INSERT INTO `ums_user_role_rel` VALUES ('1f44dd1a-41af-40b0-a2f4-a4ad0676ad8c', 'ROLE_NORMAL', '753adf42-04e1-4d50-88e9-e335ca142898', '2012-10-15 13:23:48');
INSERT INTO `ums_user_role_rel` VALUES ('6677bdc8-f1c1-4981-af7d-886d50f52515', 'ROLE_ADMIN', '753adf42-04e1-4d50-88e9-e335ca142896', '2012-09-22 12:12:37');
INSERT INTO `ums_user_role_rel` VALUES ('fef7a17e-df6d-4c08-bfb2-fa412be15859', 'ROLE_APP', '753adf42-04e1-4d50-88e9-e335ca142897', '2012-11-05 00:17:57');
