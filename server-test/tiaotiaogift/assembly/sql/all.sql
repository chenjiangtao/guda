/*
Navicat MySQL Data Transfer

Source Server         : localhost2
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : tiao

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2013-08-07 07:29:45
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `contact`
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
  `id` varchar(36) NOT NULL,
  `name` varchar(50) default NULL,
  `phone` varchar(21) NOT NULL,
  `email` varchar(200) default NULL,
  `user_id` varchar(36) NOT NULL,
  `address` varchar(2000) default NULL,
  `taobao_name` varchar(50) default NULL,
  `taobao_id` varchar(50) default NULL,
  `taobao_order_status` varchar(20) default NULL,
  `paipai_id` varchar(50) default NULL,
  `paipai_name` varchar(50) default NULL,
  `paipai_order_status` varchar(20) default NULL,
  `delivery_no` varchar(50) default NULL,
  `delivery_name` varchar(50) default NULL,
  `gmt_created` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contact
-- ----------------------------

-- ----------------------------
-- Table structure for `doc`
-- ----------------------------
DROP TABLE IF EXISTS `doc`;
CREATE TABLE `doc` (
  `id` varchar(36) NOT NULL,
  `code` varchar(16) NOT NULL,
  `title` varchar(100) NOT NULL,
  `content` text NOT NULL,
  `type` varchar(16) NOT NULL,
  `gmt_created` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of doc
-- ----------------------------
INSERT INTO `doc` VALUES ('0dbf90d4-8f8e-4e57-a3e4-87dca2acccdd', '124', '手机感染恶意木马成短信群发机', '<h2>手机感染恶意木马成短信群发机</h2><div class=contain-body><span style=float:rigt>2012-12-1 11:13:24</span><br/><hr/>垃圾短信一般由一种叫做“短信群发机”的专业设备或系统发送。360安全中心近日截获两款可传播垃圾短信的手机木马，手机中招后会在后台偷偷群发垃圾短信，中招手机因此变成“短信群发机”，有的甚至还会屏蔽回馈短信，用户很难察觉直至话费被耗尽。据360安全中心统计显示，目前木马感染量已超过30万人次。<br/>据介绍，这两款恶意软件名称分别为“网兜”和“com.android.softkey”，其通过伪装成客户端软件、系统软件后进行大肆传播。水货手机预装这些恶意软件现象较为普遍。手机中招后自动接收服务器指令，然后偷偷发送大量广告信息，有的甚至具有屏蔽回馈短信的功能。<br/>360安全专家进一步介绍，这些发送出来的短信多为广告或诈骗短信，这很像近期用户手机感染“短信僵尸”木马后的表现，都是在用户毫不知情的情况下私发短信。区别在于，上述两款软件会将短信发给指定手机号码而并非手机内联系人。目前，电信运营商正在严查群发短信现象，中招用户很可能因此被“拉黑”甚至被禁止发送短信。据360安全中心统计显示，感染此木马的用户已超过30万人次。</div>', 'news', '2012-12-12 15:58:57');
INSERT INTO `doc` VALUES ('4d916b15-791e-49fd-a5ba-81336803ae30', '126', '如何拆分长短信', '<h2>长短信的拆分</h2><div class=contain-body>这里以移动CMPP2.0版本为例说明：<br/>首先短消息内容的编码方式有以下几种:<br/>1, Msg_Fmt为0表示ASCII串<br/>2, Msg_Fmt为3表示短信写卡操作<br/>3, Msg_Fmt为4表示二进制信息<br/>4, Msg_Fmt为8表示UCS2编码<br/>5, Msg_Fmt为15表示含GB汉字<br/>常见的有0,8,15这三种。<br/>移动CMPP2.0中规定，短消息的编码如果为0，则短消息内容长度不能超过160个字节，其他<=140个字节。因此对于超过规定长度的需要进行拆分，由于GBK和UCS转换成的字节长度并不相等，而且UCS每个汉字对应三字节，容易导致拆分不当而导致乱码这里建议拆分字符串而不是转换成字节数组后再进行拆分。<br/>对于需要拆分的短信有几个字段需要注意：<br/>1,设置TP_udhi=1 <br/>2,消息内容加入6个字节的udhi头做为前缀<br/>6个字节的TP_udhi协议头如下<br/>05 00 03 XX MM NN<br/>byte 1 : 05, 表示剩余协议头的长度<br/>byte 2 : 00, 这个值在GSM 03.40规范9.2.3.24.1中规定，表示随后的这批超长短信的标识位长度为1（格式中的XX值）。<br/>byte 3 : 03, 这个值表示剩下短信标识的长度<br/>byte 4 : XX，这批短信的唯一标志，事实上，SME(手机或者SP)把消息合并完之后，就重新记录，所以这个标志是否唯一并不是很 重要。<br/>byte 5 : MM, 这批短信的数量。如果一个超长短信总共5条，这里的值就是5。<br/>byte 6 : NN, 这批短信的数量。如果当前短信是这批短信中的第一条的值是1，第二条的值是2。<br/></div>', 'knowlege', '2012-12-12 15:54:32');
INSERT INTO `doc` VALUES ('5c2ee1ec-294f-4686-a4b5-fa4b9000b86e', '126', '什么是特服号', '<h2>关于特服号</h2><div class=contain-body>SP的全称是服务提供商，服务提供商的种类有多种，有网络服务提供商，应用服务提供商等等，这里是指网络服务提供商。SP可以简单的理解为用户和电信，联通，移动之间的桥梁。SP通过运营商提供的增值接口为用户提供服务，然后由运营商在用户的手机费和宽带费中扣除相关服务费，最后运营商和SP再按照比例分成。</div><h2>如何申请特服号</h2><div class=contain-body>关于码号：95×××或者96×××，是信产部（管理95）和各个省的信管局（管理96）直接管理的号码。属于号码资源里面的。在此号码上，我们可以做呼叫中心、声讯和IVR业务，由于号码是信管局或者信产部直接管理的，包括资费的申请以及所开展的业务都是到信管局直接报批，不用找运营商的增值业务部门，只是直接找运营商的市场部联系接洽就可以了。那么申请95XXX特服需要准备些什么材料呢？<br/>1, 向工信部申请呼叫中心许可证。<br/>2, 获批后，在全国备案呼叫中心许可证。<br/>3, 同时向申请95电话号码使用证。<br/>4, 95短号审批后，备案95电话号码及开通组网方式。<br/></div><h2>申请特服号的条件</h2><div class=contain-body>申请特服号的条件如下：<br/>1, 内资企业，注册资金 1000 万以上。 <br/>2, 全部自然人法人签字（企业法人向上追溯到自然人法人） 。 <br/>3, 银行、证券、保险、航空等行业具备跨省 15 个以上的经营规模，需要提 供相关上级部委颁发的开业批 复或工商营业执照。 <br/>4, 具有全网呼叫中心许可证。</div>', 'knowlege', '2012-12-12 15:53:34');
INSERT INTO `doc` VALUES ('7c9941ed-b769-41e0-86eb-fac35137f904', '126', '什么是短信群发', '<h2>关于短信群发</h2><div class=contain-body>目前短信群发有几种概念:<br/>1)通过手机编辑短信内容后，选择多个接收方发送。<br/>2)卡发短信，卡发短信又叫虚拟网关短信，通常都是显示的随机的全国各地的手机号码。<br/>3)网关发送短信，网关短信是以特服号955**，106或各地区号如0898，0371开头显示的短信号码<br/></div><h2>正规网关群发短信</h2><div class=contain-body>移动、联通、电信的通道发送，手机方接收的号码显示特服号码，比如10658XXX，或0757XXX。综上所述，采用网关发送，显示特服号码的短信群发平台才是正规且有保障的，如果用户选择国家明令禁止的卡发平台，将无法保障使用利益，甚至可能被工商、信管等执法部门的查处。判断短信平台是否正规的最简单方法就是看手机端接收显示的号码。</div>', 'knowlege', '2012-12-12 15:52:17');
INSERT INTO `doc` VALUES ('9f2eb8de-71a3-43f5-81aa-411ff3555097', '123', '什么是短信网关', '<h2>短信网关</h2><div class=contain-body>短信网关，英文简称ISMG,全称Internet Short Message Gateway，主要是为了解决各网络、各运营商之间的短信互通和SP(服务提供商)的接入问题。它为应用单位收发短信而提供的一个动态数据交换平台系统。<img width=100px height=100px style=float:right src=$homeServer.getURI(/static/images/ums/kefu.gif)/>通过该系统的接口软件，可以将短信平台与各种系统和软件进行无缝高效相连，将应用单位的系统随时产生的动态信息转变成手机短信，通过梦网平台连接移动和联通的短信中心以端口特服号码进行实时中发送和接受，为各种系统（或软件）建立一个快速的短信双向（或单向）通道，以便手机用户采用短信方式与SP双向通信，接收SP提供的信息服务。 </div><h2>适用业务场景</h2><div class=contain-body>短信网关的主要用途是解决企业与个人之间的短信交互问题。譬如某企业需要定时给用户发送满意度调查，并可以收到用户的反馈。对于这种大量的短信发送，接收就需要专业的平台支持。另外短信相对现有的通讯方式来说，算实时性，稳定性较高的一种，因此对于通知，信息反馈，等要求送达及时，可靠的业务都可以通过短信的方式通讯。因此使用的业务场景有：<br/>1)机房监控告警，机房可以对一些主机，应用，数据库的指标进行监控，并设定告警阀值，在超过阀值的时候，可以通过发送短信的方式告警，做到响应及时。<br/>2)企业通过短信方式发起问卷调查。<br/>3)通过短信校验码的方式核实个人身份。<br/>4)优惠促销活动通知。<br/></div>', 'knowlege', '2012-12-12 15:50:02');
INSERT INTO `doc` VALUES ('aca9d60d-ec0f-46f6-b8f2-c0776e9845db', '124', '红十字会明年推短信捐款', '<h2>红十字会明年推短信捐款</h2><div class=contain-body><span style=float:rigt>2012-12-1 09:03:24</span><br/><hr/>昨天，记者从市红十字会获悉，为方便群众小额捐款，明年，本市将在全市推广手机短信、手机支付、网上支付等方便群众的小额捐款。<br/>目前，市红十字会的捐赠方式包括邮局或现场募捐、银行转账、网上捐赠三种类型为主。发送短信的捐赠方式曾试点使用，如“7·21”特大灾害时即启用了短信捐款平台，发送短信自动默认捐款1元。明年，市红十字会将在全市范围内积极推广手机短信、手机支付、网上支付等方便群众的小额捐款。同时，市红十字会还计划在机场、火车站、地铁、邮政等窗口行业设置红十字募捐箱。并将及时反馈募捐箱取款情况，通过网络等媒体向社会公告。<br/>此外，市红十字会还计划对六大重点行业进行救护培训。涉及国家公务员、窗口服务员、家政服务员、社区急救员、文明引导员和养老服务员。其中，将要求公务员必须取得16学时红十字急救员证书。而对窗口服务员的培训将重点在地铁和邮政两个系统推进。</div>', 'news', '2012-12-12 15:56:59');
INSERT INTO `doc` VALUES ('b265ef74-b992-462f-ab97-1cfbfe748ccb', '124', '什么是SP(service　provider)', '<h2>关于SP(service　provider)服务提供商 </h2><div class=contain-body>SP的全称是服务提供商，服务提供商的种类有多种，有网络服务提供商，应用服务提供商等等，这里是指网络服务提供商。SP可以简单的理解为用户和电信，联通，移动之间的桥梁。SP通过运营商提供的增值接口为用户提供服务，然后由运营商在用户的手机费和宽带费中扣除相关服务费，最后运营商和SP再按照比例分成。</div>', 'knowlege', '2012-12-12 15:51:24');
INSERT INTO `doc` VALUES ('be7b0c79-941e-482f-81e2-94656d6120d4', '123', 'iMessage导致美国传统短信发送量减少', '<h2>iMessage导致美国传统短信发送量减少</h2><div class=contain-body><span style=float:rigt>2012-12-1 09:13:24</span><br/><hr/>据短信群发报道，苹果产品中使用的iMessage致使美国地区的短信发送量减少。移动分析师Chetan Sharma最近发布的报道显示，美国地区在今年Q3其短信发送量从以往的696条/月下降到678条/月。《纽约时报》指出，这种局面的改变致使美国地区的短信收入正在遭受不断减少的经历，现在越来越多的用户都转而使用了像iMessage这样基于流量的信息发送接收服务。<br/>而像苹果公司的iMessage以及Facebook的Messenger这样的讯息服务，都是采用了蜂窝数据实现发送和接收过程。现在用户为了获得更优惠的套餐，往往都选择使用这样的工具发送以及接受信息，而不再采用传统的手机文本短信的服务。但是，Sharma还是很冷静地指出，现在就认定手机文本短信市场将会继续走低还为时过早。<br/>另外，用户短信方式的改变并不意味着美国电信运行商收入的减少。据Sharma所做的调查发现，美国三大运营商，其部分用户的账单中有45%都是来自于移动数据。</div>', 'news', '2012-12-12 15:57:43');
INSERT INTO `doc` VALUES ('d15d26da-65d8-4e9d-b091-d09712a37f75', '123', '会员营销，推广', '<h2>会员营销，推广 </h2><div class=contain-body>商家打折，优惠促销信息，新产品发布等均可以采用短信方式通知会员，做到通知及时。</div>', 'uses', '2012-12-12 15:57:57');
INSERT INTO `doc` VALUES ('e9eab7d2-7db6-465b-9f21-8c8ef370b088', '123', '机房监控告警', '<h2>机房监控告警 </h2><div class=contain-body>企业在服务器投入使用越来越多的情况下，会需要更多的人力成本做监控方便的工作，主机，应用，数据库的健康状况，而通过监控平台设定告警阀值后，对超过阀值的情况自动通过短信的方式告警，将减少客户投诉，提高故障处理响应速度。节省人力成本的投入。</div><h2>应用场景说明 </h2><div class=contain-body>主要分为主机，应用和数据库<br/>1)服务器负载过高，CPU过高，磁盘IO频繁等情况，可以采用自动化的方式，定时采样数据后通过短信方式及时通知。<br/>   2)应用健康检查，可以采用自动化的方式，定时检测端口，url是否可用，否则通过短信方式及时通知。<br/>3)数据库健康检查，可以采用自动化的方式，定时查询缓存命中，锁表情况，否则通过短信方式及时通知。<br/></div>', 'uses', '2012-12-12 15:58:02');

-- ----------------------------
-- Table structure for `group`
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
  `id` varchar(36) NOT NULL,
  `name` varchar(50) default NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of group
-- ----------------------------

-- ----------------------------
-- Table structure for `msg_in`
-- ----------------------------
DROP TABLE IF EXISTS `msg_in`;
CREATE TABLE `msg_in` (
  `id` varchar(36) NOT NULL,
  `send_id` varchar(25) NOT NULL,
  `content` text,
  `gmt_created` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  `recv_id` varchar(36) default NULL,
  `status` varchar(1) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of msg_in
-- ----------------------------

-- ----------------------------
-- Table structure for `msg_out`
-- ----------------------------
DROP TABLE IF EXISTS `msg_out`;
CREATE TABLE `msg_out` (
  `id` varchar(36) NOT NULL,
  `send_id` varchar(36) default NULL,
  `recv_id` varchar(21) NOT NULL,
  `content` text NOT NULL,
  `status` varchar(1) default NULL,
  `send_time` timestamp NULL default NULL,
  `gmt_modify` timestamp NULL default NULL,
  `gmt_created` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of msg_out
-- ----------------------------
INSERT INTO `msg_out` VALUES ('0d64a41c-3513-4437-991d-6b4c6cfcbfc4', 'bcdc364d-0b07-4492-aa42-45f67189889b', '13588754574', 'fdafda', '1', null, '2013-05-09 18:24:15', '2013-05-09 18:24:15');
INSERT INTO `msg_out` VALUES ('59e13a78-10ae-4e14-ad3a-5cbc6bc6399c', 'bcdc364d-0b07-4492-aa42-45f67189889b', '13588754574', 'fdafda', '1', null, '2013-05-09 18:22:46', '2013-05-09 18:22:46');
INSERT INTO `msg_out` VALUES ('61dc97b9-79ec-467c-9d74-0556892ab084', 'ccdf0f4c-ba84-44e2-b444-949d4ccb5731', '13588754574', 'agafda', '1', null, '2013-05-09 18:22:18', '2013-05-09 18:22:18');
INSERT INTO `msg_out` VALUES ('728b428d-5f6b-49b9-b946-78aa654655b7', 'bcdc364d-0b07-4492-aa42-45f67189889b', '15558135733', 'fdafda', '1', null, '2013-05-09 18:24:15', '2013-05-09 18:24:15');

-- ----------------------------
-- Table structure for `sms_template`
-- ----------------------------
DROP TABLE IF EXISTS `sms_template`;
CREATE TABLE `sms_template` (
  `id` varchar(36) NOT NULL,
  `content` text NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `type` varchar(10) default NULL,
  `gmt_created` timestamp NULL default NULL,
  `gmt_modify` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sms_template
-- ----------------------------

-- ----------------------------
-- Table structure for `u_account`
-- ----------------------------
DROP TABLE IF EXISTS `u_account`;
CREATE TABLE `u_account` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `balance` int(11) default NULL,
  `balance_lock` int(11) default NULL,
  `gmt_modify` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_account
-- ----------------------------
INSERT INTO `u_account` VALUES ('0330eb2d-26e1-4190-84e8-4591013883ec', '1', '258', '0', '2013-05-04 12:39:24');
INSERT INTO `u_account` VALUES ('1dc17e97-89d6-4283-be4f-eb9c9f120b82', '5a0f2150-ddd8-4db2-9e81-8b92c07c5a42', '2', '0', '2013-05-01 08:02:12');
INSERT INTO `u_account` VALUES ('5b25409d-0a45-4f9a-b089-02110ec20ee2', 'e5d29864-630a-4ad4-8252-979f84eeea43', '3', '0', '2013-05-02 12:07:02');
INSERT INTO `u_account` VALUES ('ba08b7a3-75ef-47fe-a379-fd07d2321f18', '513d8068-6b2e-44df-95ce-cda9cb8953c2', '3', '0', '2013-05-01 12:01:55');
INSERT INTO `u_account` VALUES ('e4edf225-b316-4937-b4c6-76060e8e9f2b', 'a752f3ac-18b9-4e65-8411-a2a1fb4b84cb', '3', '0', '2013-05-07 07:52:55');

-- ----------------------------
-- Table structure for `u_account_log`
-- ----------------------------
DROP TABLE IF EXISTS `u_account_log`;
CREATE TABLE `u_account_log` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `amount_before` int(11) NOT NULL,
  `sum` int(11) NOT NULL,
  `action` varchar(30) default NULL,
  `amount_after` int(11) NOT NULL,
  `gmt_created` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_account_log
-- ----------------------------
INSERT INTO `u_account_log` VALUES ('3453f1fc-57cf-4d78-80f3-ebcdf8ddaa9c', 'bcdc364d-0b07-4492-aa42-45f67189889b', '2', '2', '扣除', '0', '2013-05-09 18:24:15');
INSERT INTO `u_account_log` VALUES ('7bc35326-feb6-483d-abc3-6d40f5d48482', 'ccdf0f4c-ba84-44e2-b444-949d4ccb5731', '3', '1', '扣除', '2', '2013-05-09 18:22:18');
INSERT INTO `u_account_log` VALUES ('f5f394c1-b01a-4a5e-928e-f8e51e95819d', 'bcdc364d-0b07-4492-aa42-45f67189889b', '3', '1', '扣除', '2', '2013-05-09 18:22:46');

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

-- ----------------------------
-- Table structure for `u_order`
-- ----------------------------
DROP TABLE IF EXISTS `u_order`;
CREATE TABLE `u_order` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `amount` float default NULL,
  `sum` int(11) default NULL,
  `status` varchar(255) default NULL,
  `gmt_created` timestamp NULL default NULL,
  `gmt_modify` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_order
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  `password` varchar(36) NOT NULL,
  `phone` varchar(21) NOT NULL,
  `email` varchar(30) default NULL,
  `status` varchar(1) default NULL,
  `gmt_created` timestamp NULL default NULL,
  `code` varchar(36) NOT NULL,
  `grade` int(11) default NULL,
  `link_id` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'biance', 'afa9c01e4457dd59', '12345678900', '1@163.com', '1', null, '1', '5', '1');
INSERT INTO `user` VALUES ('49530319-7efe-4933-9b44-8f5e141ee006', 'gang', 'b64c8ca4f9345dcb', '15990081937', '3@qq.com', '1', null, '', '11', '2');
INSERT INTO `user` VALUES ('513d8068-6b2e-44df-95ce-cda9cb8953c2', '1234', 'cf85a661ddb500bc', '15558135733', '52313885@qq.com', '1', '2013-05-01 12:01:53', '64812736-e318-4dc3-82ab-c68a32e67e9b', '0', '3');
INSERT INTO `user` VALUES ('7f736dcb-acc7-463f-8a17-bf8eebf34de6', 'shaqiyaoyao', 'acf44112d8973048', '15157625312', '376866582@qq.com', '1', null, '1b1ce89a-6cee-412b-9802-720e1e293f4d', null, '4');
INSERT INTO `user` VALUES ('a752f3ac-18b9-4e65-8411-a2a1fb4b84cb', 'oumei', 'afa9c01e4457dd59', '12345678909', '1111@123.com', '1', null, '', '5', '5');
INSERT INTO `user` VALUES ('b3afc781-c1f6-49b7-847e-b1289ccfdcc6', 'admin', 'ad5137b713f74360', '13545678909', '2@qq.com', '1', null, '', '11', '6');
INSERT INTO `user` VALUES ('c21d98b5-74bb-4e6d-88b1-f87ee17c3368', 'enetor', '8d56e608a0a08623', '13819139340', 'jiangwei_home@126.com', null, null, '', null, '7');
INSERT INTO `user` VALUES ('e5d29864-630a-4ad4-8252-979f84eeea43', 'abcd', 'afa9c01e4457dd59', '15558135733', '52313882@qq.com', '2', '2013-05-02 12:06:59', '91b9bf6d-2191-46cb-bf04-7e29dd3a9b16', '0', '8');
