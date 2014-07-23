/*
Navicat MySQL Data Transfer

Source Server         : local_news
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : news

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2014-06-10 13:35:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `j_action_vedio`
-- ----------------------------
DROP TABLE IF EXISTS `j_action_vedio`;
CREATE TABLE `j_action_vedio` (
  `id` varchar(36) NOT NULL,
  `user_name` varchar(30) NOT NULL,
  `action` varchar(10) NOT NULL,
  `comment` varchar(1000) DEFAULT NULL,
  `from_ip` varchar(20) DEFAULT NULL,
  `gmt_create` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of j_action_vedio
-- ----------------------------
INSERT INTO `j_action_vedio` VALUES ('711dad7a-da9d-4654-87bf-f20421a43a31', '游客', '1', 'test', '0:0:0:0:0:0:0:1', '2014-03-28 00:00:00');
INSERT INTO `j_action_vedio` VALUES ('cd6b9d95-8f6c-4514-96e4-eef9131a45f3', '游客', '1', 'test', '0:0:0:0:0:0:0:1', '2014-03-28 00:00:00');

-- ----------------------------
-- Table structure for `jc_acquisition`
-- ----------------------------
DROP TABLE IF EXISTS `jc_acquisition`;
CREATE TABLE `jc_acquisition` (
  `acquisition_id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL,
  `channel_id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `acq_name` varchar(50) NOT NULL COMMENT '采集名称',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '停止时间',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '当前状态(0:静止;1:采集;2:暂停)',
  `curr_num` int(11) NOT NULL DEFAULT '0' COMMENT '当前号码',
  `curr_item` int(11) NOT NULL DEFAULT '0' COMMENT '当前条数',
  `total_item` int(11) NOT NULL DEFAULT '0' COMMENT '每页总条数',
  `pause_time` int(11) NOT NULL DEFAULT '0' COMMENT '暂停时间(毫秒)',
  `page_encoding` varchar(20) NOT NULL DEFAULT 'GBK' COMMENT '页面编码',
  `plan_list` longtext COMMENT '采集列表',
  `dynamic_addr` varchar(255) DEFAULT NULL COMMENT '动态地址',
  `dynamic_start` int(11) DEFAULT NULL COMMENT '页码开始',
  `dynamic_end` int(11) DEFAULT NULL COMMENT '页码结束',
  `linkset_start` varchar(255) DEFAULT NULL COMMENT '内容链接区开始',
  `linkset_end` varchar(255) DEFAULT NULL COMMENT '内容链接区结束',
  `link_start` varchar(255) DEFAULT NULL COMMENT '内容链接开始',
  `link_end` varchar(255) DEFAULT NULL COMMENT '内容链接结束',
  `title_start` varchar(255) DEFAULT NULL COMMENT '标题开始',
  `title_end` varchar(255) DEFAULT NULL COMMENT '标题结束',
  `keywords_start` varchar(255) DEFAULT NULL COMMENT '关键字开始',
  `keywords_end` varchar(255) DEFAULT NULL COMMENT '关键字结束',
  `description_start` varchar(255) DEFAULT NULL COMMENT '描述开始',
  `description_end` varchar(255) DEFAULT NULL COMMENT '描述结束',
  `content_start` varchar(255) DEFAULT NULL COMMENT '内容开始',
  `content_end` varchar(255) DEFAULT NULL COMMENT '内容结束',
  `pagination_start` varchar(255) DEFAULT NULL COMMENT '内容分页开始',
  `pagination_end` varchar(255) DEFAULT NULL COMMENT '内容分页结束',
  `queue` int(11) NOT NULL DEFAULT '0' COMMENT '队列',
  `repeat_check_type` varchar(20) NOT NULL DEFAULT 'NONE' COMMENT '重复类型',
  `img_acqu` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否采集图片',
  `content_prefix` varchar(255) DEFAULT NULL COMMENT '内容地址补全url',
  `img_prefix` varchar(255) DEFAULT NULL COMMENT '图片地址补全url',
  `view_start` varchar(255) DEFAULT NULL COMMENT '浏览量开始',
  `view_end` varchar(255) DEFAULT NULL COMMENT '浏览量结束',
  `view_id_start` varchar(255) DEFAULT NULL COMMENT 'id前缀',
  `view_id_end` varchar(255) DEFAULT NULL COMMENT 'id后缀',
  `view_link` varchar(255) DEFAULT NULL COMMENT '浏览量动态访问地址',
  `releaseTime_start` varchar(255) DEFAULT NULL COMMENT '发布时间开始',
  `releaseTime_end` varchar(255) DEFAULT NULL COMMENT '发布时间结束',
  `author_start` varchar(255) DEFAULT NULL COMMENT '作者开始',
  `author_end` varchar(255) DEFAULT NULL COMMENT '作者结束',
  `origin_start` varchar(255) DEFAULT NULL COMMENT '来源开始',
  `origin_end` varchar(255) DEFAULT NULL COMMENT '来源结束',
  `releaseTime_format` varchar(255) DEFAULT NULL COMMENT '发布时间格式',
  PRIMARY KEY (`acquisition_id`),
  KEY `fk_jc_acquisition_channel` (`channel_id`),
  KEY `fk_jc_acquisition_contenttype` (`type_id`),
  KEY `fk_jc_acquisition_site` (`site_id`),
  KEY `fk_jc_acquisition_user` (`user_id`),
  CONSTRAINT `fk_jc_acquisition_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`),
  CONSTRAINT `fk_jc_acquisition_contenttype` FOREIGN KEY (`type_id`) REFERENCES `jc_content_type` (`type_id`),
  CONSTRAINT `fk_jc_acquisition_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
  CONSTRAINT `fk_jc_acquisition_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='CMS采集表';

-- ----------------------------
-- Records of jc_acquisition
-- ----------------------------
INSERT INTO `jc_acquisition` VALUES ('2', '1', '40', '1', '1', '新浪社会与法栏目', '2013-09-23 17:29:08', '2013-09-23 17:29:31', '0', '0', '0', '0', '100', 'gb2312', '', 'http://roll.finance.sina.com.cn/finance/jj4/jjdt/index_[page].shtml', '7', '300', '<ul class=\"list_009\">', '<div class=\"hs01\"></div>', '<li><a href=\"', '\" target=\"_blank\">', '<title>', '_基金动态_新浪财经_新浪网</title>', null, null, '', '', '<!-- 正文内容 begin -->', '<!-- publish_helper_end -->', null, null, '0', 'NONE', '0', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `jc_acquisition` VALUES ('3', '1', '13', '1', '1', '社会与法律', '2013-09-23 17:29:33', '2013-09-23 17:29:47', '0', '0', '0', '0', '500', 'GBK', 'http://roll.news.sina.com.cn/news/shxw/fz-shyf/index.shtml', '', '2', '10', '<ul class=\"list_009\">', '<div class=\"hs01\"></div>', '<li><a href=\"', '\" target=\"_blank\">', '<title>', '_新浪新闻</title>', null, null, '<meta name=description content=\"', '<meta name=\"sudameta\" content=\"sinaog:0\" />', '<!-- 正文内容 begin -->', '<!-- publish_helper_end -->', null, null, '0', 'NONE', '1', '', '', '', '', '', '', '', '', '', '', '', '', '', 'yyyy-MM-dd HH:mm:ss');

-- ----------------------------
-- Table structure for `jc_acquisition_history`
-- ----------------------------
DROP TABLE IF EXISTS `jc_acquisition_history`;
CREATE TABLE `jc_acquisition_history` (
  `history_id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_url` varchar(255) NOT NULL DEFAULT '' COMMENT '栏目地址',
  `content_url` varchar(255) NOT NULL DEFAULT '' COMMENT '内容地址',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `description` varchar(20) NOT NULL DEFAULT '' COMMENT '描述',
  `acquisition_id` int(11) DEFAULT NULL COMMENT '采集源',
  `content_id` int(11) DEFAULT NULL COMMENT '内容',
  PRIMARY KEY (`history_id`),
  KEY `fk_acquisition_history_acquisition` (`acquisition_id`),
  CONSTRAINT `fk_jc_history_acquisition` FOREIGN KEY (`acquisition_id`) REFERENCES `jc_acquisition` (`acquisition_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采集历史记录表';

-- ----------------------------
-- Records of jc_acquisition_history
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_acquisition_temp`
-- ----------------------------
DROP TABLE IF EXISTS `jc_acquisition_temp`;
CREATE TABLE `jc_acquisition_temp` (
  `temp_id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT NULL,
  `channel_url` varchar(255) NOT NULL DEFAULT '' COMMENT '栏目地址',
  `content_url` varchar(255) NOT NULL DEFAULT '' COMMENT '内容地址',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `percent` int(3) NOT NULL DEFAULT '0' COMMENT '百分比',
  `description` varchar(20) NOT NULL DEFAULT '' COMMENT '描述',
  `seq` int(3) NOT NULL DEFAULT '0' COMMENT '顺序',
  PRIMARY KEY (`temp_id`),
  KEY `fk_jc_temp_site` (`site_id`),
  CONSTRAINT `fk_jc_temp_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采集进度临时表';

-- ----------------------------
-- Records of jc_acquisition_temp
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_advertising`
-- ----------------------------
DROP TABLE IF EXISTS `jc_advertising`;
CREATE TABLE `jc_advertising` (
  `advertising_id` int(11) NOT NULL AUTO_INCREMENT,
  `adspace_id` int(11) NOT NULL,
  `site_id` int(11) NOT NULL,
  `ad_name` varchar(100) NOT NULL COMMENT '广告名称',
  `category` varchar(50) NOT NULL COMMENT '广告类型',
  `ad_code` longtext COMMENT '广告代码',
  `ad_weight` int(11) NOT NULL DEFAULT '1' COMMENT '广告权重',
  `display_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '展现次数',
  `click_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '点击次数',
  `start_time` date DEFAULT NULL COMMENT '开始时间',
  `end_time` date DEFAULT NULL COMMENT '结束时间',
  `is_enabled` char(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  PRIMARY KEY (`advertising_id`),
  KEY `fk_jc_advertising_site` (`site_id`),
  KEY `fk_jc_space_advertising` (`adspace_id`),
  CONSTRAINT `fk_jc_advertising_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
  CONSTRAINT `fk_jc_space_advertising` FOREIGN KEY (`adspace_id`) REFERENCES `jc_advertising_space` (`adspace_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='CMS广告表';

-- ----------------------------
-- Records of jc_advertising
-- ----------------------------
INSERT INTO `jc_advertising` VALUES ('1', '1', '1', 'banner', 'image', null, '1', '127', '0', null, null, '1');
INSERT INTO `jc_advertising` VALUES ('2', '2', '1', '通栏广告1', 'image', null, '1', '123', '2', null, null, '1');
INSERT INTO `jc_advertising` VALUES ('3', '3', '1', '视频广告上', 'image', null, '1', '0', '0', null, null, '1');
INSERT INTO `jc_advertising` VALUES ('4', '4', '1', '视频广告下', 'image', null, '1', '0', '0', null, null, '1');
INSERT INTO `jc_advertising` VALUES ('5', '5', '1', '留言板本周热点广告', 'image', null, '1', '0', '0', null, null, '1');

-- ----------------------------
-- Table structure for `jc_advertising_attr`
-- ----------------------------
DROP TABLE IF EXISTS `jc_advertising_attr`;
CREATE TABLE `jc_advertising_attr` (
  `advertising_id` int(11) NOT NULL,
  `attr_name` varchar(50) NOT NULL COMMENT '名称',
  `attr_value` varchar(255) DEFAULT NULL COMMENT '值',
  KEY `fk_jc_params_advertising` (`advertising_id`),
  CONSTRAINT `fk_jc_params_advertising` FOREIGN KEY (`advertising_id`) REFERENCES `jc_advertising` (`advertising_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS广告属性表';

-- ----------------------------
-- Records of jc_advertising_attr
-- ----------------------------
INSERT INTO `jc_advertising_attr` VALUES ('1', 'image_title', '查看网站');
INSERT INTO `jc_advertising_attr` VALUES ('1', 'image_url', '/r/cms/www/def/img/banner.gif');
INSERT INTO `jc_advertising_attr` VALUES ('1', 'image_target', '_blank');
INSERT INTO `jc_advertising_attr` VALUES ('1', 'image_link', 'http://www.foodoon.com');
INSERT INTO `jc_advertising_attr` VALUES ('1', 'image_width', '735');
INSERT INTO `jc_advertising_attr` VALUES ('1', 'image_height', '70');
INSERT INTO `jc_advertising_attr` VALUES ('2', 'image_title', '网站');
INSERT INTO `jc_advertising_attr` VALUES ('2', 'image_url', '/r/cms/www/def/img/banner1.jpg');
INSERT INTO `jc_advertising_attr` VALUES ('2', 'image_target', '_blank');
INSERT INTO `jc_advertising_attr` VALUES ('2', 'image_link', 'http://www.foodoon.com');
INSERT INTO `jc_advertising_attr` VALUES ('2', 'image_width', '960');
INSERT INTO `jc_advertising_attr` VALUES ('2', 'image_height', '60');
INSERT INTO `jc_advertising_attr` VALUES ('3', 'image_height', '89');
INSERT INTO `jc_advertising_attr` VALUES ('3', 'image_link', 'http://');
INSERT INTO `jc_advertising_attr` VALUES ('3', 'image_target', '_blank');
INSERT INTO `jc_advertising_attr` VALUES ('3', 'image_url', '/u/cms/www/201112/17144805im1p.jpg');
INSERT INTO `jc_advertising_attr` VALUES ('3', 'image_width', '980');
INSERT INTO `jc_advertising_attr` VALUES ('4', 'image_height', '90');
INSERT INTO `jc_advertising_attr` VALUES ('4', 'image_link', 'http://');
INSERT INTO `jc_advertising_attr` VALUES ('4', 'image_target', '_blank');
INSERT INTO `jc_advertising_attr` VALUES ('4', 'image_url', '/u/cms/www/201112/17145028j3bj.jpg');
INSERT INTO `jc_advertising_attr` VALUES ('4', 'image_width', '980');
INSERT INTO `jc_advertising_attr` VALUES ('5', 'image_height', '109');
INSERT INTO `jc_advertising_attr` VALUES ('5', 'image_link', 'http://foodoon.com');
INSERT INTO `jc_advertising_attr` VALUES ('5', 'image_target', '_blank');
INSERT INTO `jc_advertising_attr` VALUES ('5', 'image_url', '/u/cms/www/201112/18155751wi1k.gif');
INSERT INTO `jc_advertising_attr` VALUES ('5', 'image_width', '215');

-- ----------------------------
-- Table structure for `jc_advertising_space`
-- ----------------------------
DROP TABLE IF EXISTS `jc_advertising_space`;
CREATE TABLE `jc_advertising_space` (
  `adspace_id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL,
  `ad_name` varchar(100) NOT NULL COMMENT '名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `is_enabled` char(1) NOT NULL COMMENT '是否启用',
  PRIMARY KEY (`adspace_id`),
  KEY `fk_jc_adspace_site` (`site_id`),
  CONSTRAINT `fk_jc_adspace_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='CMS广告版位表';

-- ----------------------------
-- Records of jc_advertising_space
-- ----------------------------
INSERT INTO `jc_advertising_space` VALUES ('1', '1', '页头banner', '全站页头banner', '1');
INSERT INTO `jc_advertising_space` VALUES ('2', '1', '通栏广告', '页面中间通栏广告', '1');
INSERT INTO `jc_advertising_space` VALUES ('3', '1', '视频广告上', '', '1');
INSERT INTO `jc_advertising_space` VALUES ('4', '1', '视频广告下', '', '1');
INSERT INTO `jc_advertising_space` VALUES ('5', '1', '留言板本周热点广告', '', '1');

-- ----------------------------
-- Table structure for `jc_channel`
-- ----------------------------
DROP TABLE IF EXISTS `jc_channel`;
CREATE TABLE `jc_channel` (
  `channel_id` int(11) NOT NULL AUTO_INCREMENT,
  `model_id` int(11) NOT NULL COMMENT '模型ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `parent_id` int(11) DEFAULT NULL COMMENT '父栏目ID',
  `channel_path` varchar(30) DEFAULT NULL COMMENT '访问路径',
  `lft` int(11) NOT NULL DEFAULT '1' COMMENT '树左边',
  `rgt` int(11) NOT NULL DEFAULT '2' COMMENT '树右边',
  `priority` int(11) NOT NULL DEFAULT '10' COMMENT '排列顺序',
  `has_content` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有内容',
  `is_display` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否显示',
  PRIMARY KEY (`channel_id`),
  KEY `fk_jc_channel_model` (`model_id`),
  KEY `fk_jc_channel_parent` (`parent_id`),
  KEY `fk_jc_channel_site` (`site_id`),
  CONSTRAINT `fk_jc_channel_model` FOREIGN KEY (`model_id`) REFERENCES `jc_model` (`model_id`),
  CONSTRAINT `fk_jc_channel_parent` FOREIGN KEY (`parent_id`) REFERENCES `jc_channel` (`channel_id`),
  CONSTRAINT `fk_jc_channel_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8 COMMENT='CMS栏目表';

-- ----------------------------
-- Records of jc_channel
-- ----------------------------
INSERT INTO `jc_channel` VALUES ('1', '1', '1', null, 'news', '1', '16', '1', '1', '1');
INSERT INTO `jc_channel` VALUES ('11', '1', '1', '1', 'gnxw', '2', '3', '10', '1', '1');
INSERT INTO `jc_channel` VALUES ('12', '1', '1', '1', 'world', '4', '5', '10', '1', '1');
INSERT INTO `jc_channel` VALUES ('13', '1', '1', '1', 'shehui', '6', '7', '10', '1', '1');
INSERT INTO `jc_channel` VALUES ('14', '1', '1', '1', 'review', '8', '9', '10', '1', '1');
INSERT INTO `jc_channel` VALUES ('15', '1', '1', '1', 'photo', '10', '11', '10', '1', '1');
INSERT INTO `jc_channel` VALUES ('40', '1', '1', '1', 'jjsd', '12', '13', '10', '1', '1');
INSERT INTO `jc_channel` VALUES ('41', '1', '1', '1', 'cjbd', '14', '15', '10', '1', '1');
INSERT INTO `jc_channel` VALUES ('42', '5', '1', null, 'picture', '19', '26', '2', '1', '1');
INSERT INTO `jc_channel` VALUES ('43', '5', '1', '42', 'wyty', '20', '21', '10', '1', '1');
INSERT INTO `jc_channel` VALUES ('44', '5', '1', '42', 'mrzx', '22', '23', '10', '1', '1');
INSERT INTO `jc_channel` VALUES ('45', '5', '1', '42', 'whxy', '24', '25', '10', '1', '1');
INSERT INTO `jc_channel` VALUES ('46', '6', '1', null, 'video', '27', '34', '3', '1', '1');
INSERT INTO `jc_channel` VALUES ('49', '6', '1', '46', 'dianshiju', '28', '29', '10', '1', '1');
INSERT INTO `jc_channel` VALUES ('50', '6', '1', '46', 'jlp', '30', '31', '10', '1', '1');
INSERT INTO `jc_channel` VALUES ('61', '6', '1', '46', 'm', '32', '33', '10', '1', '1');
INSERT INTO `jc_channel` VALUES ('66', '11', '1', null, 'live', '37', '38', '11', '1', '1');
INSERT INTO `jc_channel` VALUES ('67', '12', '1', null, 'interview', '39', '40', '10', '1', '1');

-- ----------------------------
-- Table structure for `jc_channel_attr`
-- ----------------------------
DROP TABLE IF EXISTS `jc_channel_attr`;
CREATE TABLE `jc_channel_attr` (
  `channel_id` int(11) NOT NULL,
  `attr_name` varchar(30) NOT NULL COMMENT '名称',
  `attr_value` varchar(255) DEFAULT NULL COMMENT '值',
  KEY `fk_jc_attr_channel` (`channel_id`),
  CONSTRAINT `fk_jc_attr_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目扩展属性表';

-- ----------------------------
-- Records of jc_channel_attr
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_channel_ext`
-- ----------------------------
DROP TABLE IF EXISTS `jc_channel_ext`;
CREATE TABLE `jc_channel_ext` (
  `channel_id` int(11) NOT NULL,
  `channel_name` varchar(100) NOT NULL COMMENT '名称',
  `final_step` tinyint(4) DEFAULT '2' COMMENT '终审级别',
  `after_check` tinyint(4) DEFAULT NULL COMMENT '审核后(1:不能修改删除;2:修改后退回;3:修改后不变)',
  `is_static_channel` char(1) NOT NULL DEFAULT '0' COMMENT '是否栏目静态化',
  `is_static_content` char(1) NOT NULL DEFAULT '0' COMMENT '是否内容静态化',
  `is_access_by_dir` char(1) NOT NULL DEFAULT '1' COMMENT '是否使用目录访问',
  `is_list_child` char(1) NOT NULL DEFAULT '0' COMMENT '是否使用子栏目列表',
  `page_size` int(11) NOT NULL DEFAULT '20' COMMENT '每页多少条记录',
  `channel_rule` varchar(150) DEFAULT NULL COMMENT '栏目页生成规则',
  `content_rule` varchar(150) DEFAULT NULL COMMENT '内容页生成规则',
  `link` varchar(255) DEFAULT NULL COMMENT '外部链接',
  `tpl_channel` varchar(100) DEFAULT NULL COMMENT '栏目页模板',
  `tpl_content` varchar(100) DEFAULT NULL COMMENT '内容页模板',
  `title_img` varchar(100) DEFAULT NULL COMMENT '缩略图',
  `content_img` varchar(100) DEFAULT NULL COMMENT '内容图',
  `has_title_img` tinyint(1) NOT NULL DEFAULT '0' COMMENT '内容是否有缩略图',
  `has_content_img` tinyint(1) NOT NULL DEFAULT '0' COMMENT '内容是否有内容图',
  `title_img_width` int(11) NOT NULL DEFAULT '139' COMMENT '内容标题图宽度',
  `title_img_height` int(11) NOT NULL DEFAULT '139' COMMENT '内容标题图高度',
  `content_img_width` int(11) NOT NULL DEFAULT '310' COMMENT '内容内容图宽度',
  `content_img_height` int(11) NOT NULL DEFAULT '310' COMMENT '内容内容图高度',
  `comment_control` int(11) NOT NULL DEFAULT '0' COMMENT '评论(0:匿名;1:会员;2:关闭)',
  `allow_updown` tinyint(1) NOT NULL DEFAULT '1' COMMENT '顶踩(true:开放;false:关闭)',
  `is_blank` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否新窗口打开',
  `title` varchar(255) DEFAULT NULL COMMENT 'TITLE',
  `keywords` varchar(255) DEFAULT NULL COMMENT 'KEYWORDS',
  `description` varchar(255) DEFAULT NULL COMMENT 'DESCRIPTION',
  PRIMARY KEY (`channel_id`),
  CONSTRAINT `fk_jc_ext_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目内容表';

-- ----------------------------
-- Records of jc_channel_ext
-- ----------------------------
INSERT INTO `jc_channel_ext` VALUES ('1', '新闻', null, null, '0', '0', '0', '0', '20', null, null, null, '/WEB-INF/t/cms/www/def/channel/news.html', null, null, null, '0', '0', '139', '139', '310', '310', '0', '1', '0', '新闻资讯', '新闻资讯', '新闻资讯');
INSERT INTO `jc_channel_ext` VALUES ('11', '国内新闻', null, null, '0', '0', '0', '0', '20', null, null, null, '/WEB-INF/t/cms/www/def/channel/news.html', '/WEB-INF/t/cms/www/def/content/news.html', null, null, '0', '0', '139', '139', '310', '310', '0', '1', '0', '国内新闻', '国内新闻', '国内新闻');
INSERT INTO `jc_channel_ext` VALUES ('12', '国际新闻', null, null, '0', '0', '0', '0', '20', null, null, null, '/WEB-INF/t/cms/www/def/channel/news_sub.html', null, null, null, '0', '0', '139', '139', '310', '310', '0', '1', '0', '国际新闻', '国际新闻', '国际新闻');
INSERT INTO `jc_channel_ext` VALUES ('13', '社会热点', null, null, '0', '0', '0', '0', '20', null, null, null, '/WEB-INF/t/cms/www/def/channel/news_sub.html', null, null, null, '0', '0', '139', '139', '310', '310', '0', '1', '0', '社会热点', '社会热点', '社会热点');
INSERT INTO `jc_channel_ext` VALUES ('14', '时事评论', null, null, '0', '0', '0', '0', '20', null, null, null, '/WEB-INF/t/cms/www/def/channel/news_sub.html', null, null, null, '0', '0', '139', '139', '310', '310', '0', '1', '0', '时事评论', '时事评论', '时事评论');
INSERT INTO `jc_channel_ext` VALUES ('15', '图片新闻', null, null, '0', '0', '1', '0', '20', null, null, null, '/WEB-INF/t/cms/www/def/channel/pic_sub.html', '/WEB-INF/t/cms/www/def/content/pic_content_new.html', null, null, '1', '0', '139', '139', '310', '310', '0', '1', '0', '图片新闻', '图片新闻', '图片新闻');
INSERT INTO `jc_channel_ext` VALUES ('40', '基金视点', null, null, '0', '0', '0', '0', '20', null, null, null, '/WEB-INF/t/cms/www/def/channel/news_sub.html', '/WEB-INF/t/cms/www/def/content/news_content.html', null, null, '0', '0', '139', '139', '310', '310', '0', '1', '0', null, null, null);
INSERT INTO `jc_channel_ext` VALUES ('41', '财经报道', null, null, '0', '0', '0', '0', '20', null, null, null, '/WEB-INF/t/cms/www/def/channel/news_sub.html', '/WEB-INF/t/cms/www/def/content/news_content.html', null, null, '0', '0', '139', '139', '310', '310', '0', '1', '0', null, null, null);
INSERT INTO `jc_channel_ext` VALUES ('42', '图库', null, null, '0', '0', '0', '0', '20', null, null, null, '/WEB-INF/t/cms/www/def/channel/pic.html', '/WEB-INF/t/cms/www/def/content/pic_content.html', null, null, '0', '0', '139', '139', '310', '310', '0', '1', '0', null, null, null);
INSERT INTO `jc_channel_ext` VALUES ('43', '娱乐图集', null, null, '0', '0', '0', '0', '20', null, null, null, '/WEB-INF/t/cms/www/def/channel/pic_sub.html', '/WEB-INF/t/cms/www/def/content/pic_content.html', null, null, '1', '0', '67', '50', '310', '310', '0', '1', '0', null, null, null);
INSERT INTO `jc_channel_ext` VALUES ('44', '娱乐新闻', null, null, '0', '0', '0', '0', '20', null, null, null, '/WEB-INF/t/cms/www/def/channel/pic_sub.html', '/WEB-INF/t/cms/www/def/content/pic_content.html', null, null, '0', '0', '139', '139', '310', '310', '0', '1', '0', null, null, null);
INSERT INTO `jc_channel_ext` VALUES ('45', '文化校园', null, null, '0', '0', '0', '0', '20', null, null, null, '/WEB-INF/t/cms/www/def/channel/pic_sub.html', '/WEB-INF/t/cms/www/def/content/pic_content.html', null, null, '0', '0', '139', '139', '310', '310', '0', '1', '0', null, null, null);
INSERT INTO `jc_channel_ext` VALUES ('46', '视频', null, null, '0', '0', '0', '0', '20', null, null, null, null, null, null, null, '0', '0', '139', '139', '310', '310', '0', '1', '0', null, null, null);
INSERT INTO `jc_channel_ext` VALUES ('49', '电视剧', null, null, '0', '0', '0', '0', '20', null, null, null, '/WEB-INF/t/cms/www/def/channel/video_sub.html', '/WEB-INF/t/cms/www/def/content/video_content.html', null, null, '1', '0', '139', '139', '310', '310', '0', '1', '0', null, null, null);
INSERT INTO `jc_channel_ext` VALUES ('50', '纪录片', null, null, '0', '0', '0', '0', '20', null, null, null, '/WEB-INF/t/cms/www/def/channel/video_sub.html', '/WEB-INF/t/cms/www/def/content/video_content.html', null, null, '1', '0', '139', '139', '310', '310', '0', '1', '0', null, null, null);
INSERT INTO `jc_channel_ext` VALUES ('61', '电影', null, null, '0', '0', '0', '0', '20', null, null, null, '/WEB-INF/t/cms/www/def/channel/video_sub.html', null, null, null, '1', '0', '139', '139', '310', '310', '0', '1', '0', '电影', '电影', '电影');
INSERT INTO `jc_channel_ext` VALUES ('66', '直播', null, null, '0', '0', '0', '0', '20', null, null, null, '/WEB-INF/t/cms/www/def/channel/live.html', null, null, null, '0', '0', '139', '139', '310', '310', '0', '1', '0', '直播', '直播', '直播');
INSERT INTO `jc_channel_ext` VALUES ('67', '访谈', null, null, '0', '0', '0', '0', '20', null, null, null, '/WEB-INF/t/cms/www/def/channel/interview.html', null, null, null, '0', '0', '139', '139', '310', '310', '0', '1', '0', null, null, null);

-- ----------------------------
-- Table structure for `jc_channel_model`
-- ----------------------------
DROP TABLE IF EXISTS `jc_channel_model`;
CREATE TABLE `jc_channel_model` (
  `channel_id` int(11) NOT NULL,
  `model_id` int(11) NOT NULL COMMENT '模型id',
  `tpl_content` varchar(100) DEFAULT NULL COMMENT '内容模板',
  `priority` int(11) NOT NULL DEFAULT '10' COMMENT '排序',
  PRIMARY KEY (`channel_id`,`priority`),
  KEY `fk_jc_model_channel_m` (`model_id`),
  CONSTRAINT `fk_jc_channel_model_c` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`),
  CONSTRAINT `fk_jc_model_channel_m` FOREIGN KEY (`model_id`) REFERENCES `jc_model` (`model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='栏目可选内容模型表';

-- ----------------------------
-- Records of jc_channel_model
-- ----------------------------
INSERT INTO `jc_channel_model` VALUES ('1', '1', '', '0');
INSERT INTO `jc_channel_model` VALUES ('1', '5', '/WEB-INF/t/cms/www/def/content/pic_later.html', '3');
INSERT INTO `jc_channel_model` VALUES ('1', '6', '', '4');
INSERT INTO `jc_channel_model` VALUES ('11', '1', '/WEB-INF/t/cms/www/def/content/news_content.html', '0');
INSERT INTO `jc_channel_model` VALUES ('11', '5', '', '1');
INSERT INTO `jc_channel_model` VALUES ('11', '6', '', '2');
INSERT INTO `jc_channel_model` VALUES ('12', '1', '/WEB-INF/t/cms/www/def/content/news_content.html', '0');
INSERT INTO `jc_channel_model` VALUES ('12', '5', '', '1');
INSERT INTO `jc_channel_model` VALUES ('12', '6', '', '2');
INSERT INTO `jc_channel_model` VALUES ('13', '1', '/WEB-INF/t/cms/www/def/content/news_content.html', '0');
INSERT INTO `jc_channel_model` VALUES ('13', '5', '', '1');
INSERT INTO `jc_channel_model` VALUES ('13', '6', '', '2');
INSERT INTO `jc_channel_model` VALUES ('14', '1', '/WEB-INF/t/cms/www/def/content/news_content.html', '0');
INSERT INTO `jc_channel_model` VALUES ('14', '5', '', '1');
INSERT INTO `jc_channel_model` VALUES ('14', '6', '', '2');
INSERT INTO `jc_channel_model` VALUES ('15', '1', '/WEB-INF/t/cms/www/def/content/news_content.html', '0');
INSERT INTO `jc_channel_model` VALUES ('15', '5', '', '1');
INSERT INTO `jc_channel_model` VALUES ('15', '6', '', '2');
INSERT INTO `jc_channel_model` VALUES ('40', '1', '/WEB-INF/t/cms/www/def/content/news_content.html', '0');
INSERT INTO `jc_channel_model` VALUES ('40', '5', '', '1');
INSERT INTO `jc_channel_model` VALUES ('40', '6', '', '2');
INSERT INTO `jc_channel_model` VALUES ('41', '1', '/WEB-INF/t/cms/www/def/content/news_content.html', '0');
INSERT INTO `jc_channel_model` VALUES ('41', '5', '', '1');
INSERT INTO `jc_channel_model` VALUES ('41', '6', '', '2');
INSERT INTO `jc_channel_model` VALUES ('43', '1', '', '0');
INSERT INTO `jc_channel_model` VALUES ('43', '5', '', '1');
INSERT INTO `jc_channel_model` VALUES ('43', '6', '', '2');
INSERT INTO `jc_channel_model` VALUES ('44', '1', '', '0');
INSERT INTO `jc_channel_model` VALUES ('44', '5', '', '1');
INSERT INTO `jc_channel_model` VALUES ('44', '6', '', '2');
INSERT INTO `jc_channel_model` VALUES ('45', '1', '', '0');
INSERT INTO `jc_channel_model` VALUES ('45', '5', '', '2');
INSERT INTO `jc_channel_model` VALUES ('45', '6', '', '3');
INSERT INTO `jc_channel_model` VALUES ('49', '6', '', '0');
INSERT INTO `jc_channel_model` VALUES ('50', '6', '', '0');
INSERT INTO `jc_channel_model` VALUES ('61', '6', '', '0');
INSERT INTO `jc_channel_model` VALUES ('66', '11', '/WEB-INF/t/cms/www/def/content/live_content.html', '0');
INSERT INTO `jc_channel_model` VALUES ('67', '12', '/WEB-INF/t/cms/www/def/content/interview_content.html', '0');

-- ----------------------------
-- Table structure for `jc_channel_txt`
-- ----------------------------
DROP TABLE IF EXISTS `jc_channel_txt`;
CREATE TABLE `jc_channel_txt` (
  `channel_id` int(11) NOT NULL,
  `txt` longtext COMMENT '栏目内容',
  `txt1` longtext COMMENT '扩展内容1',
  `txt2` longtext COMMENT '扩展内容2',
  `txt3` longtext COMMENT '扩展内容3',
  PRIMARY KEY (`channel_id`),
  CONSTRAINT `fk_jc_txt_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目文本表';

-- ----------------------------
-- Records of jc_channel_txt
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_channel_user`
-- ----------------------------
DROP TABLE IF EXISTS `jc_channel_user`;
CREATE TABLE `jc_channel_user` (
  `channel_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`channel_id`,`user_id`),
  KEY `fk_jc_channel_user` (`user_id`),
  CONSTRAINT `fk_jc_channel_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`),
  CONSTRAINT `fk_jc_user_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目用户关联表';

-- ----------------------------
-- Records of jc_channel_user
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_chnl_group_contri`
-- ----------------------------
DROP TABLE IF EXISTS `jc_chnl_group_contri`;
CREATE TABLE `jc_chnl_group_contri` (
  `channel_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`channel_id`,`group_id`),
  KEY `fk_jc_channel_group_c` (`group_id`),
  CONSTRAINT `fk_jc_channel_group_c` FOREIGN KEY (`group_id`) REFERENCES `jc_group` (`group_id`),
  CONSTRAINT `fk_jc_group_channel_c` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目投稿会员组关联表';

-- ----------------------------
-- Records of jc_chnl_group_contri
-- ----------------------------
INSERT INTO `jc_chnl_group_contri` VALUES ('66', '1');
INSERT INTO `jc_chnl_group_contri` VALUES ('66', '2');

-- ----------------------------
-- Table structure for `jc_chnl_group_view`
-- ----------------------------
DROP TABLE IF EXISTS `jc_chnl_group_view`;
CREATE TABLE `jc_chnl_group_view` (
  `channel_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`channel_id`,`group_id`),
  KEY `fk_jc_channel_group_v` (`group_id`),
  CONSTRAINT `fk_jc_channel_group_v` FOREIGN KEY (`group_id`) REFERENCES `jc_group` (`group_id`),
  CONSTRAINT `fk_jc_group_channel_v` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目浏览会员组关联表';

-- ----------------------------
-- Records of jc_chnl_group_view
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_comment`
-- ----------------------------
DROP TABLE IF EXISTS `jc_comment`;
CREATE TABLE `jc_comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_user_id` int(11) DEFAULT NULL COMMENT '评论用户ID',
  `reply_user_id` int(11) DEFAULT NULL COMMENT '回复用户ID',
  `content_id` int(11) NOT NULL COMMENT '内容ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `create_time` datetime NOT NULL COMMENT '评论时间',
  `reply_time` datetime DEFAULT NULL COMMENT '回复时间',
  `ups` smallint(6) NOT NULL DEFAULT '0' COMMENT '支持数',
  `downs` smallint(6) NOT NULL DEFAULT '0' COMMENT '反对数',
  `is_recommend` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否推荐',
  `is_checked` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否审核',
  PRIMARY KEY (`comment_id`),
  KEY `fk_jc_comment_content` (`content_id`),
  KEY `fk_jc_comment_reply` (`reply_user_id`),
  KEY `fk_jc_comment_site` (`site_id`),
  KEY `fk_jc_comment_user` (`comment_user_id`),
  CONSTRAINT `fk_jc_comment_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`),
  CONSTRAINT `fk_jc_comment_reply` FOREIGN KEY (`reply_user_id`) REFERENCES `jc_user` (`user_id`),
  CONSTRAINT `fk_jc_comment_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
  CONSTRAINT `fk_jc_comment_user` FOREIGN KEY (`comment_user_id`) REFERENCES `jc_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS评论表';

-- ----------------------------
-- Records of jc_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_comment_ext`
-- ----------------------------
DROP TABLE IF EXISTS `jc_comment_ext`;
CREATE TABLE `jc_comment_ext` (
  `comment_id` int(11) NOT NULL,
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `text` longtext COMMENT '评论内容',
  `reply` longtext COMMENT '回复内容',
  KEY `fk_jc_ext_comment` (`comment_id`),
  CONSTRAINT `fk_jc_ext_comment` FOREIGN KEY (`comment_id`) REFERENCES `jc_comment` (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS评论扩展表';

-- ----------------------------
-- Records of jc_comment_ext
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_config`
-- ----------------------------
DROP TABLE IF EXISTS `jc_config`;
CREATE TABLE `jc_config` (
  `config_id` int(11) NOT NULL,
  `context_path` varchar(20) DEFAULT '/ROOT' COMMENT '部署路径',
  `servlet_point` varchar(20) DEFAULT NULL COMMENT 'Servlet挂载点',
  `port` int(11) DEFAULT NULL COMMENT '端口',
  `db_file_uri` varchar(50) NOT NULL DEFAULT '/dbfile.svl?n=' COMMENT '数据库附件访问地址',
  `is_upload_to_db` tinyint(1) NOT NULL DEFAULT '0' COMMENT '上传附件至数据库',
  `def_img` varchar(255) NOT NULL DEFAULT '/ROOT/r/cms/www/default/no_picture.gif' COMMENT '图片不存在时默认图片',
  `login_url` varchar(255) NOT NULL DEFAULT '/login.jspx' COMMENT '登录地址',
  `process_url` varchar(255) DEFAULT NULL COMMENT '登录后处理地址',
  `mark_on` tinyint(1) NOT NULL DEFAULT '1' COMMENT '开启图片水印',
  `mark_width` int(11) NOT NULL DEFAULT '120' COMMENT '图片最小宽度',
  `mark_height` int(11) NOT NULL DEFAULT '120' COMMENT '图片最小高度',
  `mark_image` varchar(100) DEFAULT '/r/cms/www/watermark.png' COMMENT '图片水印',
  `mark_content` varchar(100) NOT NULL DEFAULT '为民网' COMMENT '文字水印内容',
  `mark_size` int(11) NOT NULL DEFAULT '20' COMMENT '文字水印大小',
  `mark_color` varchar(10) NOT NULL DEFAULT '#FF0000' COMMENT '文字水印颜色',
  `mark_alpha` int(11) NOT NULL DEFAULT '50' COMMENT '水印透明度（0-100）',
  `mark_position` int(11) NOT NULL DEFAULT '1' COMMENT '水印位置(0-5)',
  `mark_offset_x` int(11) NOT NULL DEFAULT '0' COMMENT 'x坐标偏移量',
  `mark_offset_y` int(11) NOT NULL DEFAULT '0' COMMENT 'y坐标偏移量',
  `count_clear_time` date NOT NULL COMMENT '计数器清除时间',
  `count_copy_time` datetime NOT NULL COMMENT '计数器拷贝时间',
  `download_code` varchar(32) NOT NULL DEFAULT 'foodoon' COMMENT '下载防盗链md5混淆码',
  `download_time` int(11) NOT NULL DEFAULT '12' COMMENT '下载有效时间（小时）',
  `email_host` varchar(50) DEFAULT NULL COMMENT '邮件发送服务器',
  `email_encoding` varchar(20) DEFAULT NULL COMMENT '邮件发送编码',
  `email_username` varchar(100) DEFAULT NULL COMMENT '邮箱用户名',
  `email_password` varchar(100) DEFAULT NULL COMMENT '邮箱密码',
  `email_personal` varchar(100) DEFAULT NULL COMMENT '邮箱发件人',
  `email_validate` tinyint(1) DEFAULT '0' COMMENT '开启邮箱验证',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS配置表';

-- ----------------------------
-- Records of jc_config
-- ----------------------------
INSERT INTO `jc_config` VALUES ('1', '', null, '8080', '/dbfile.svl?n=', '0', '/r/cms/www/no_picture.gif', '/login.jspx', null, '1', '120', '120', '/r/cms/www/watermark.png', 'www.foodoon.com', '20', '#FF0000', '50', '1', '0', '0', '2014-06-10', '2014-06-10 09:41:58', 'foodoon', '12', null, null, null, null, null, '0');

-- ----------------------------
-- Table structure for `jc_config_attr`
-- ----------------------------
DROP TABLE IF EXISTS `jc_config_attr`;
CREATE TABLE `jc_config_attr` (
  `config_id` int(11) NOT NULL,
  `attr_name` varchar(30) NOT NULL COMMENT '名称',
  `attr_value` varchar(255) DEFAULT NULL COMMENT '值',
  KEY `fk_jc_attr_config` (`config_id`),
  CONSTRAINT `fk_jc_attr_config` FOREIGN KEY (`config_id`) REFERENCES `jc_config` (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS配置属性表';

-- ----------------------------
-- Records of jc_config_attr
-- ----------------------------
INSERT INTO `jc_config_attr` VALUES ('1', 'password_min_len', '3');
INSERT INTO `jc_config_attr` VALUES ('1', 'username_reserved', '');
INSERT INTO `jc_config_attr` VALUES ('1', 'register_on', 'true');
INSERT INTO `jc_config_attr` VALUES ('1', 'member_on', 'true');
INSERT INTO `jc_config_attr` VALUES ('1', 'username_min_len', '3');
INSERT INTO `jc_config_attr` VALUES ('1', 'version', 'v1.0');

-- ----------------------------
-- Table structure for `jc_content`
-- ----------------------------
DROP TABLE IF EXISTS `jc_content`;
CREATE TABLE `jc_content` (
  `content_id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_id` int(11) NOT NULL COMMENT '栏目ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `type_id` int(11) NOT NULL COMMENT '属性ID',
  `model_id` int(11) NOT NULL COMMENT '模型ID',
  `site_id` int(11) NOT NULL COMMENT '站点ID',
  `sort_date` datetime NOT NULL COMMENT '排序日期',
  `top_level` tinyint(4) NOT NULL DEFAULT '0' COMMENT '固顶级别',
  `has_title_img` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有标题图',
  `is_recommend` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否推荐',
  `status` tinyint(4) NOT NULL DEFAULT '2' COMMENT '状态(0:草稿;1:审核中;2:审核通过;3:回收站)',
  `views_day` int(11) NOT NULL DEFAULT '0' COMMENT '日访问数',
  `comments_day` smallint(6) NOT NULL DEFAULT '0' COMMENT '日评论数',
  `downloads_day` smallint(6) NOT NULL DEFAULT '0' COMMENT '日下载数',
  `ups_day` smallint(6) NOT NULL DEFAULT '0' COMMENT '日顶数',
  PRIMARY KEY (`content_id`),
  KEY `fk_jc_content_site` (`site_id`),
  KEY `fk_jc_content_type` (`type_id`),
  KEY `fk_jc_content_user` (`user_id`),
  KEY `fk_jc_contentchannel` (`channel_id`),
  KEY `fk_jc_content_model` (`model_id`),
  CONSTRAINT `fk_jc_contentchannel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`),
  CONSTRAINT `fk_jc_content_model` FOREIGN KEY (`model_id`) REFERENCES `jc_model` (`model_id`),
  CONSTRAINT `fk_jc_content_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
  CONSTRAINT `fk_jc_content_type` FOREIGN KEY (`type_id`) REFERENCES `jc_content_type` (`type_id`),
  CONSTRAINT `fk_jc_content_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=553 DEFAULT CHARSET=utf8 COMMENT='CMS内容表';

-- ----------------------------
-- Records of jc_content
-- ----------------------------
INSERT INTO `jc_content` VALUES ('256', '41', '1', '1', '1', '1', '2011-12-19 09:23:31', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('257', '41', '1', '1', '1', '1', '2011-12-19 09:23:58', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('258', '41', '1', '1', '1', '1', '2011-12-19 09:25:32', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('259', '41', '1', '1', '1', '1', '2011-12-19 09:26:22', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('342', '11', '1', '1', '1', '1', '2013-07-05 10:56:56', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('398', '40', '1', '1', '1', '1', '2013-07-08 15:21:28', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('399', '40', '1', '1', '1', '1', '2013-07-08 15:21:28', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('400', '40', '1', '1', '1', '1', '2013-07-08 15:21:29', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('401', '40', '1', '1', '1', '1', '2013-07-08 15:21:33', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('402', '40', '1', '1', '1', '1', '2013-07-08 15:21:35', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('403', '40', '1', '1', '1', '1', '2013-07-08 15:21:35', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('404', '40', '1', '1', '1', '1', '2013-07-08 15:21:36', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('405', '40', '1', '1', '1', '1', '2013-07-08 15:21:37', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('406', '40', '1', '1', '1', '1', '2013-07-08 15:21:37', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('407', '40', '1', '1', '1', '1', '2013-07-08 15:21:38', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('408', '41', '1', '1', '1', '1', '2013-07-08 15:21:38', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('409', '41', '1', '1', '1', '1', '2013-07-08 15:21:39', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('410', '41', '1', '1', '1', '1', '2013-07-08 15:21:40', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('411', '41', '1', '1', '1', '1', '2013-07-08 15:21:41', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('412', '41', '1', '1', '1', '1', '2013-07-08 15:21:41', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('413', '41', '1', '1', '1', '1', '2013-07-08 15:21:42', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('414', '12', '1', '1', '1', '1', '2013-07-08 15:21:43', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('415', '12', '1', '1', '1', '1', '2013-07-08 15:21:43', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('416', '12', '1', '1', '1', '1', '2013-07-08 15:21:44', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('417', '12', '1', '1', '1', '1', '2013-07-08 15:21:45', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('418', '13', '1', '1', '1', '1', '2013-07-08 15:33:50', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('419', '13', '1', '1', '1', '1', '2013-07-08 15:33:51', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('420', '13', '1', '1', '1', '1', '2013-07-08 15:33:52', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('421', '13', '1', '1', '1', '1', '2013-07-08 15:33:53', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('422', '13', '1', '1', '1', '1', '2013-07-08 15:33:54', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('423', '13', '1', '1', '1', '1', '2013-07-08 15:33:55', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('424', '13', '1', '1', '1', '1', '2013-07-08 15:33:56', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('425', '13', '1', '1', '1', '1', '2013-07-08 15:33:57', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('426', '13', '1', '1', '1', '1', '2013-07-08 15:33:58', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('427', '13', '1', '1', '1', '1', '2013-07-08 15:33:59', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('428', '13', '1', '1', '1', '1', '2013-07-08 15:34:00', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('429', '13', '1', '1', '1', '1', '2013-07-08 15:34:01', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('430', '13', '1', '1', '1', '1', '2013-07-08 15:34:02', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('431', '13', '1', '1', '1', '1', '2013-07-08 15:34:03', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('432', '13', '1', '1', '1', '1', '2013-07-08 15:34:04', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('433', '13', '1', '2', '1', '1', '2013-07-08 15:34:05', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('434', '12', '1', '1', '1', '1', '2013-07-08 15:34:06', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('435', '13', '1', '1', '1', '1', '2013-07-08 15:34:07', '0', '0', '1', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('436', '15', '1', '2', '1', '1', '2013-07-08 15:34:08', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('437', '15', '1', '2', '1', '1', '2013-07-08 15:34:09', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('438', '15', '1', '2', '1', '1', '2013-07-08 15:34:11', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('439', '15', '1', '2', '1', '1', '2013-07-08 15:34:11', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('440', '14', '1', '1', '1', '1', '2013-07-08 15:34:12', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('441', '12', '1', '1', '1', '1', '2013-07-08 15:34:13', '0', '0', '1', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('442', '14', '1', '1', '1', '1', '2013-07-08 15:34:14', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('443', '14', '1', '1', '1', '1', '2013-07-08 15:34:15', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('444', '11', '1', '1', '1', '1', '2013-07-08 15:34:17', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('445', '14', '1', '1', '1', '1', '2013-07-08 15:34:18', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('446', '11', '1', '1', '1', '1', '2013-07-08 15:34:18', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('447', '12', '1', '1', '1', '1', '2013-07-08 15:34:20', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('448', '11', '1', '1', '1', '1', '2013-07-08 15:34:21', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('487', '43', '1', '2', '5', '1', '2013-08-21 11:34:29', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('488', '43', '1', '2', '5', '1', '2013-08-21 11:59:29', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('489', '43', '1', '2', '5', '1', '2013-08-21 13:40:11', '0', '0', '1', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('490', '43', '1', '2', '5', '1', '2013-08-21 14:35:35', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('491', '43', '1', '2', '5', '1', '2013-08-21 14:47:08', '0', '0', '1', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('492', '43', '1', '2', '5', '1', '2013-08-21 14:49:52', '0', '0', '1', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('493', '43', '1', '2', '5', '1', '2013-08-21 14:54:53', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('494', '43', '1', '2', '5', '1', '2013-08-21 14:58:46', '0', '0', '1', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('495', '44', '1', '1', '1', '1', '2013-08-21 15:12:25', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('496', '44', '1', '1', '1', '1', '2013-08-21 15:19:59', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('498', '44', '1', '1', '1', '1', '2013-08-22 09:13:41', '0', '0', '1', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('499', '45', '1', '1', '1', '1', '2013-08-22 09:21:00', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('500', '45', '1', '1', '1', '1', '2013-08-22 09:28:09', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('501', '45', '1', '1', '1', '1', '2013-08-22 09:45:50', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('506', '11', '1', '3', '5', '1', '2013-09-10 09:22:39', '0', '0', '1', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('507', '11', '1', '3', '1', '1', '2013-09-10 09:50:40', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('508', '11', '1', '3', '1', '1', '2013-09-10 09:53:55', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('509', '11', '1', '1', '1', '1', '2013-09-10 10:28:25', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('510', '11', '1', '3', '5', '1', '2013-09-10 10:38:52', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('511', '12', '1', '3', '5', '1', '2013-09-10 10:45:34', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('512', '12', '1', '2', '1', '1', '2013-09-10 14:40:05', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('513', '45', '1', '2', '5', '1', '2013-09-12 10:59:15', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('514', '45', '1', '2', '5', '1', '2013-09-12 11:04:04', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('515', '45', '1', '2', '5', '1', '2013-09-12 11:08:22', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('516', '45', '1', '2', '5', '1', '2013-09-12 11:12:45', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('517', '45', '1', '2', '5', '1', '2013-09-12 11:19:03', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('518', '45', '1', '2', '5', '1', '2013-09-12 11:22:50', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('519', '44', '1', '3', '5', '1', '2013-09-12 12:07:42', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('520', '44', '1', '3', '5', '1', '2013-09-12 13:43:06', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('521', '44', '1', '2', '5', '1', '2013-09-12 13:45:55', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('522', '44', '1', '2', '5', '1', '2013-09-12 13:49:36', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('523', '44', '1', '2', '5', '1', '2013-09-12 13:52:46', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('524', '44', '1', '3', '5', '1', '2013-09-12 13:58:30', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('525', '44', '1', '3', '5', '1', '2013-09-12 14:08:58', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('526', '44', '1', '3', '5', '1', '2013-09-12 14:14:53', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('527', '44', '1', '3', '5', '1', '2013-09-12 14:23:30', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('528', '44', '1', '2', '5', '1', '2013-09-12 14:28:33', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('529', '44', '1', '2', '5', '1', '2013-09-12 14:33:47', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('530', '44', '1', '2', '5', '1', '2013-09-12 14:57:01', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('531', '44', '1', '3', '5', '1', '2013-09-12 15:07:03', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('532', '44', '1', '3', '5', '1', '2013-09-12 15:11:43', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('533', '44', '1', '3', '5', '1', '2013-09-12 15:23:00', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('534', '44', '1', '2', '5', '1', '2013-09-12 15:36:56', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('535', '44', '1', '2', '5', '1', '2013-09-12 15:43:13', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('536', '44', '1', '3', '5', '1', '2013-09-12 15:48:01', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('548', '11', '1', '1', '1', '1', '2014-03-10 07:56:45', '0', '0', '0', '3', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('550', '11', '1', '1', '1', '1', '2014-05-11 11:07:44', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('551', '67', '1', '1', '12', '1', '2014-06-08 07:04:10', '0', '0', '0', '2', '0', '0', '0', '0');
INSERT INTO `jc_content` VALUES ('552', '66', '1', '1', '11', '1', '2014-06-09 23:22:02', '0', '0', '0', '2', '2', '0', '0', '0');

-- ----------------------------
-- Table structure for `jc_content_attachment`
-- ----------------------------
DROP TABLE IF EXISTS `jc_content_attachment`;
CREATE TABLE `jc_content_attachment` (
  `content_id` int(11) NOT NULL,
  `priority` int(11) NOT NULL COMMENT '排列顺序',
  `attachment_path` varchar(255) NOT NULL COMMENT '附件路径',
  `attachment_name` varchar(100) NOT NULL COMMENT '附件名称',
  `filename` varchar(100) DEFAULT NULL COMMENT '文件名',
  `download_count` int(11) NOT NULL DEFAULT '0' COMMENT '下载次数',
  KEY `fk_jc_attachment_content` (`content_id`),
  CONSTRAINT `fk_jc_attachment_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容附件表';

-- ----------------------------
-- Records of jc_content_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_content_attr`
-- ----------------------------
DROP TABLE IF EXISTS `jc_content_attr`;
CREATE TABLE `jc_content_attr` (
  `content_id` int(11) NOT NULL,
  `attr_name` varchar(30) NOT NULL COMMENT '名称',
  `attr_value` varchar(255) DEFAULT NULL COMMENT '值',
  KEY `fk_jc_attr_content` (`content_id`),
  CONSTRAINT `fk_jc_attr_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容扩展属性表';

-- ----------------------------
-- Records of jc_content_attr
-- ----------------------------
INSERT INTO `jc_content_attr` VALUES ('552', 'serverPath', 'a123');
INSERT INTO `jc_content_attr` VALUES ('552', 'serverUrl', 'abc');

-- ----------------------------
-- Table structure for `jc_content_channel`
-- ----------------------------
DROP TABLE IF EXISTS `jc_content_channel`;
CREATE TABLE `jc_content_channel` (
  `channel_id` int(11) NOT NULL,
  `content_id` int(11) NOT NULL,
  PRIMARY KEY (`channel_id`,`content_id`),
  KEY `fk_jc_channel_content` (`content_id`),
  CONSTRAINT `fk_jc_channel_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`),
  CONSTRAINT `fk_jc_content_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容栏目关联表';

-- ----------------------------
-- Records of jc_content_channel
-- ----------------------------
INSERT INTO `jc_content_channel` VALUES ('41', '256');
INSERT INTO `jc_content_channel` VALUES ('41', '257');
INSERT INTO `jc_content_channel` VALUES ('41', '258');
INSERT INTO `jc_content_channel` VALUES ('41', '259');
INSERT INTO `jc_content_channel` VALUES ('11', '342');
INSERT INTO `jc_content_channel` VALUES ('40', '398');
INSERT INTO `jc_content_channel` VALUES ('40', '399');
INSERT INTO `jc_content_channel` VALUES ('40', '400');
INSERT INTO `jc_content_channel` VALUES ('40', '401');
INSERT INTO `jc_content_channel` VALUES ('40', '402');
INSERT INTO `jc_content_channel` VALUES ('40', '403');
INSERT INTO `jc_content_channel` VALUES ('40', '404');
INSERT INTO `jc_content_channel` VALUES ('40', '405');
INSERT INTO `jc_content_channel` VALUES ('40', '406');
INSERT INTO `jc_content_channel` VALUES ('40', '407');
INSERT INTO `jc_content_channel` VALUES ('40', '408');
INSERT INTO `jc_content_channel` VALUES ('40', '409');
INSERT INTO `jc_content_channel` VALUES ('40', '410');
INSERT INTO `jc_content_channel` VALUES ('40', '411');
INSERT INTO `jc_content_channel` VALUES ('40', '412');
INSERT INTO `jc_content_channel` VALUES ('40', '413');
INSERT INTO `jc_content_channel` VALUES ('40', '414');
INSERT INTO `jc_content_channel` VALUES ('40', '415');
INSERT INTO `jc_content_channel` VALUES ('40', '416');
INSERT INTO `jc_content_channel` VALUES ('40', '417');
INSERT INTO `jc_content_channel` VALUES ('13', '418');
INSERT INTO `jc_content_channel` VALUES ('13', '419');
INSERT INTO `jc_content_channel` VALUES ('13', '420');
INSERT INTO `jc_content_channel` VALUES ('13', '421');
INSERT INTO `jc_content_channel` VALUES ('13', '422');
INSERT INTO `jc_content_channel` VALUES ('13', '423');
INSERT INTO `jc_content_channel` VALUES ('13', '424');
INSERT INTO `jc_content_channel` VALUES ('13', '425');
INSERT INTO `jc_content_channel` VALUES ('13', '426');
INSERT INTO `jc_content_channel` VALUES ('13', '427');
INSERT INTO `jc_content_channel` VALUES ('13', '428');
INSERT INTO `jc_content_channel` VALUES ('13', '429');
INSERT INTO `jc_content_channel` VALUES ('13', '430');
INSERT INTO `jc_content_channel` VALUES ('13', '431');
INSERT INTO `jc_content_channel` VALUES ('13', '432');
INSERT INTO `jc_content_channel` VALUES ('13', '433');
INSERT INTO `jc_content_channel` VALUES ('13', '434');
INSERT INTO `jc_content_channel` VALUES ('13', '435');
INSERT INTO `jc_content_channel` VALUES ('13', '436');
INSERT INTO `jc_content_channel` VALUES ('15', '436');
INSERT INTO `jc_content_channel` VALUES ('13', '437');
INSERT INTO `jc_content_channel` VALUES ('15', '437');
INSERT INTO `jc_content_channel` VALUES ('13', '438');
INSERT INTO `jc_content_channel` VALUES ('15', '438');
INSERT INTO `jc_content_channel` VALUES ('13', '439');
INSERT INTO `jc_content_channel` VALUES ('15', '439');
INSERT INTO `jc_content_channel` VALUES ('13', '440');
INSERT INTO `jc_content_channel` VALUES ('13', '441');
INSERT INTO `jc_content_channel` VALUES ('13', '442');
INSERT INTO `jc_content_channel` VALUES ('13', '443');
INSERT INTO `jc_content_channel` VALUES ('11', '444');
INSERT INTO `jc_content_channel` VALUES ('13', '444');
INSERT INTO `jc_content_channel` VALUES ('13', '445');
INSERT INTO `jc_content_channel` VALUES ('11', '446');
INSERT INTO `jc_content_channel` VALUES ('13', '446');
INSERT INTO `jc_content_channel` VALUES ('12', '447');
INSERT INTO `jc_content_channel` VALUES ('13', '447');
INSERT INTO `jc_content_channel` VALUES ('11', '448');
INSERT INTO `jc_content_channel` VALUES ('13', '448');
INSERT INTO `jc_content_channel` VALUES ('43', '487');
INSERT INTO `jc_content_channel` VALUES ('43', '488');
INSERT INTO `jc_content_channel` VALUES ('43', '489');
INSERT INTO `jc_content_channel` VALUES ('43', '490');
INSERT INTO `jc_content_channel` VALUES ('43', '491');
INSERT INTO `jc_content_channel` VALUES ('43', '492');
INSERT INTO `jc_content_channel` VALUES ('43', '493');
INSERT INTO `jc_content_channel` VALUES ('43', '494');
INSERT INTO `jc_content_channel` VALUES ('44', '495');
INSERT INTO `jc_content_channel` VALUES ('44', '496');
INSERT INTO `jc_content_channel` VALUES ('44', '498');
INSERT INTO `jc_content_channel` VALUES ('45', '499');
INSERT INTO `jc_content_channel` VALUES ('45', '500');
INSERT INTO `jc_content_channel` VALUES ('45', '501');
INSERT INTO `jc_content_channel` VALUES ('11', '506');
INSERT INTO `jc_content_channel` VALUES ('11', '507');
INSERT INTO `jc_content_channel` VALUES ('11', '508');
INSERT INTO `jc_content_channel` VALUES ('11', '509');
INSERT INTO `jc_content_channel` VALUES ('11', '510');
INSERT INTO `jc_content_channel` VALUES ('11', '511');
INSERT INTO `jc_content_channel` VALUES ('13', '512');
INSERT INTO `jc_content_channel` VALUES ('45', '513');
INSERT INTO `jc_content_channel` VALUES ('45', '514');
INSERT INTO `jc_content_channel` VALUES ('45', '515');
INSERT INTO `jc_content_channel` VALUES ('45', '516');
INSERT INTO `jc_content_channel` VALUES ('45', '517');
INSERT INTO `jc_content_channel` VALUES ('45', '518');
INSERT INTO `jc_content_channel` VALUES ('44', '519');
INSERT INTO `jc_content_channel` VALUES ('44', '520');
INSERT INTO `jc_content_channel` VALUES ('44', '521');
INSERT INTO `jc_content_channel` VALUES ('44', '522');
INSERT INTO `jc_content_channel` VALUES ('44', '523');
INSERT INTO `jc_content_channel` VALUES ('44', '524');
INSERT INTO `jc_content_channel` VALUES ('44', '525');
INSERT INTO `jc_content_channel` VALUES ('44', '526');
INSERT INTO `jc_content_channel` VALUES ('44', '527');
INSERT INTO `jc_content_channel` VALUES ('44', '528');
INSERT INTO `jc_content_channel` VALUES ('44', '529');
INSERT INTO `jc_content_channel` VALUES ('44', '530');
INSERT INTO `jc_content_channel` VALUES ('44', '531');
INSERT INTO `jc_content_channel` VALUES ('44', '532');
INSERT INTO `jc_content_channel` VALUES ('44', '533');
INSERT INTO `jc_content_channel` VALUES ('44', '534');
INSERT INTO `jc_content_channel` VALUES ('44', '535');
INSERT INTO `jc_content_channel` VALUES ('44', '536');
INSERT INTO `jc_content_channel` VALUES ('11', '548');
INSERT INTO `jc_content_channel` VALUES ('11', '550');
INSERT INTO `jc_content_channel` VALUES ('67', '551');
INSERT INTO `jc_content_channel` VALUES ('66', '552');

-- ----------------------------
-- Table structure for `jc_content_check`
-- ----------------------------
DROP TABLE IF EXISTS `jc_content_check`;
CREATE TABLE `jc_content_check` (
  `content_id` int(11) NOT NULL,
  `check_step` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核步数',
  `check_opinion` varchar(255) DEFAULT NULL COMMENT '审核意见',
  `is_rejected` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否退回',
  `reviewer` int(11) DEFAULT NULL COMMENT '终审者',
  `check_date` datetime DEFAULT NULL COMMENT '终审时间',
  PRIMARY KEY (`content_id`),
  KEY `fk_jc_content_check_user` (`reviewer`),
  CONSTRAINT `fk_jc_check_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`),
  CONSTRAINT `fk_jc_content_check_user` FOREIGN KEY (`reviewer`) REFERENCES `jc_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容审核信息表';

-- ----------------------------
-- Records of jc_content_check
-- ----------------------------
INSERT INTO `jc_content_check` VALUES ('256', '2', null, '0', '1', '2011-12-19 09:23:31');
INSERT INTO `jc_content_check` VALUES ('257', '2', null, '0', '1', '2011-12-19 09:23:58');
INSERT INTO `jc_content_check` VALUES ('258', '2', null, '0', '1', '2011-12-19 09:25:32');
INSERT INTO `jc_content_check` VALUES ('259', '2', null, '0', '1', '2011-12-19 09:26:22');
INSERT INTO `jc_content_check` VALUES ('342', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('398', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('399', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('400', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('401', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('402', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('403', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('404', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('405', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('406', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('407', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('408', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('409', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('410', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('411', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('412', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('413', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('414', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('415', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('416', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('417', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('418', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('419', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('420', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('421', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('422', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('423', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('424', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('425', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('426', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('427', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('428', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('429', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('430', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('431', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('432', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('433', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('434', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('435', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('436', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('437', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('438', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('439', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('440', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('441', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('442', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('443', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('444', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('445', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('446', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('447', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('448', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('487', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('488', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('489', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('490', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('491', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('492', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('493', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('494', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('495', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('496', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('498', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('499', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('500', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('501', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('506', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('507', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('508', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('509', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('510', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('511', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('512', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('513', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('514', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('515', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('516', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('517', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('518', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('519', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('520', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('521', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('522', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('523', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('524', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('525', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('526', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('527', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('528', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('529', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('530', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('531', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('532', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('533', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('534', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('535', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('536', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('548', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('550', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('551', '2', null, '0', null, null);
INSERT INTO `jc_content_check` VALUES ('552', '2', null, '0', null, null);

-- ----------------------------
-- Table structure for `jc_content_count`
-- ----------------------------
DROP TABLE IF EXISTS `jc_content_count`;
CREATE TABLE `jc_content_count` (
  `content_id` int(11) NOT NULL,
  `views` int(11) NOT NULL DEFAULT '0' COMMENT '总访问数',
  `views_month` int(11) NOT NULL DEFAULT '0' COMMENT '月访问数',
  `views_week` int(11) NOT NULL DEFAULT '0' COMMENT '周访问数',
  `views_day` int(11) NOT NULL DEFAULT '0' COMMENT '日访问数',
  `comments` int(11) NOT NULL DEFAULT '0' COMMENT '总评论数',
  `comments_month` int(11) NOT NULL DEFAULT '0' COMMENT '月评论数',
  `comments_week` smallint(6) NOT NULL DEFAULT '0' COMMENT '周评论数',
  `comments_day` smallint(6) NOT NULL DEFAULT '0' COMMENT '日评论数',
  `downloads` int(11) NOT NULL DEFAULT '0' COMMENT '总下载数',
  `downloads_month` int(11) NOT NULL DEFAULT '0' COMMENT '月下载数',
  `downloads_week` smallint(6) NOT NULL DEFAULT '0' COMMENT '周下载数',
  `downloads_day` smallint(6) NOT NULL DEFAULT '0' COMMENT '日下载数',
  `ups` int(11) NOT NULL DEFAULT '0' COMMENT '总顶数',
  `ups_month` int(11) NOT NULL DEFAULT '0' COMMENT '月顶数',
  `ups_week` smallint(6) NOT NULL DEFAULT '0' COMMENT '周顶数',
  `ups_day` smallint(6) NOT NULL DEFAULT '0' COMMENT '日顶数',
  `downs` int(11) NOT NULL DEFAULT '0' COMMENT '总踩数',
  PRIMARY KEY (`content_id`),
  CONSTRAINT `fk_jc_count_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容计数表';

-- ----------------------------
-- Records of jc_content_count
-- ----------------------------
INSERT INTO `jc_content_count` VALUES ('256', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('257', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('258', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('259', '19', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('342', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('398', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('399', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('400', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('401', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('402', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('403', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('404', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('405', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('406', '6', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('407', '5', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('408', '5', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('409', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('410', '6', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('411', '10', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('412', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('413', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('414', '7', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('415', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('416', '16', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('417', '10', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('418', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('419', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('420', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('421', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('422', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('423', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('424', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('425', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('426', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('427', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('428', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('429', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('430', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('431', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('432', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('433', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('434', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('435', '7', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('436', '6', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('437', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('438', '19', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('439', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('440', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('441', '13', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('442', '8', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('443', '10', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('444', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('445', '11', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('446', '7', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('447', '15', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('448', '39', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('487', '10', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('488', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('489', '6', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('490', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('491', '13', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('492', '15', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('493', '20', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('494', '28', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('495', '5', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('496', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('498', '5', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('499', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('500', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('501', '6', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('506', '10', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('507', '13', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('508', '11', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('509', '22', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('510', '29', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('511', '36', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('512', '50', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('513', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('514', '9', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('515', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('516', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('517', '6', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('518', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('519', '6', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('520', '5', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('521', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('522', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('523', '4', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('524', '8', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('525', '11', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('526', '8', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('527', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('528', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('529', '6', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('530', '15', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('531', '8', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('532', '6', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('533', '7', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('534', '11', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('535', '43', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('536', '45', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('548', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('550', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('551', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `jc_content_count` VALUES ('552', '16', '16', '16', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for `jc_content_ext`
-- ----------------------------
DROP TABLE IF EXISTS `jc_content_ext`;
CREATE TABLE `jc_content_ext` (
  `content_id` int(11) NOT NULL,
  `title` varchar(150) NOT NULL COMMENT '标题',
  `short_title` varchar(150) DEFAULT NULL COMMENT '简短标题',
  `author` varchar(100) DEFAULT NULL COMMENT '作者',
  `origin` varchar(100) DEFAULT NULL COMMENT '来源',
  `origin_url` varchar(255) DEFAULT NULL COMMENT '来源链接',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `release_date` datetime NOT NULL COMMENT '发布日期',
  `media_path` varchar(255) DEFAULT NULL COMMENT '媒体路径',
  `media_type` varchar(20) DEFAULT NULL COMMENT '媒体类型',
  `title_color` varchar(10) DEFAULT NULL COMMENT '标题颜色',
  `is_bold` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否加粗',
  `title_img` varchar(100) DEFAULT NULL COMMENT '标题图片',
  `content_img` varchar(100) DEFAULT NULL COMMENT '内容图片',
  `type_img` varchar(100) DEFAULT NULL COMMENT '类型图片',
  `link` varchar(255) DEFAULT NULL COMMENT '外部链接',
  `tpl_content` varchar(100) DEFAULT NULL COMMENT '指定模板',
  `need_regenerate` tinyint(1) NOT NULL DEFAULT '1' COMMENT '需要重新生成静态页',
  PRIMARY KEY (`content_id`),
  CONSTRAINT `fk_jc_ext_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容扩展表';

-- ----------------------------
-- Records of jc_content_ext
-- ----------------------------
INSERT INTO `jc_content_ext` VALUES ('256', '年底最保值手机搜罗 1.5GHz双核2000元', null, null, null, null, '2011年即将结束，每年的这个时候都是购机的旺季。对于用户而言，购买手机最关心的就是价格问题，降到心理价位就要入手？NO！NO！NO！从手机市场来看，价格便宜但仍需稳定，如果今天手机报价3500元，明天就跌至3000元，这样的情况谁也不愿碰到。相反对于官方定价的手机，比如小米手机', '2011-12-19 09:23:31', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('257', '康菲石油食言 称无证据显示渤海溢油致环境污染', null, null, null, null, '马军对记者表示，渤海是半封闭的内海，平均水深仅为20多米，溢油对渤海环境的影响是比较严重的危害。而且，溢油造成的油基泥浆至今还未完全清理干净，对环境的影响还将长期存在。他说，康菲公司对公众隐瞒不报在先，屡屡误导陈述在后，同时一再试图回避承担其造成的生态损害和养殖等经济利益损失，其公信力已经丧失', '2011-12-19 09:23:58', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('258', '跨国车企巨头揽本土高管 撤回“外脑”', null, null, null, null, '随着在华项目越来越多，包括大众汽车在内的不少跨国汽车巨头陆续遇到高管人才储备断档的瓶颈。这在竞争日益激烈的中国车市，能否顺利解决这一难题，将成为影响企业在华业绩的重要因素', '2011-12-19 09:25:32', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('259', '小米手机今日开放购买：3小时10万库存售罄', null, null, null, null, '12月18日消息，小米手机今日正式开放销售，不过，在宣布开放销售仅仅3个小时后，小米官方腾讯微博发布公告，称今天凌晨开放购买3小时，12月在线销售10万库存已售罄。小米还表示，下一轮开放购买，请关注小米论坛和官方微博的通知', '2011-12-19 09:26:22', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('342', '中石化下属单位曝腐败窝案 配件供货流程每个科室都需“打点”', null, null, null, null, null, '2013-07-05 10:56:56', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('398', '五只基金延长募集期 低风险产品也需帮忙资金', null, null, null, null, null, '2013-07-08 15:21:28', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('399', '首家独立基金销售机构昨试业', null, null, null, null, null, '2013-07-08 15:21:28', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('400', '上周A股开户增8% 基金开户数降31%', null, null, null, null, null, '2013-07-08 15:21:29', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('401', '分级债基稳健份额频启比例配售', null, null, null, null, null, '2013-07-08 15:21:33', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('402', '长信利鑫分级债基A6月下旬打开申购', null, null, null, null, null, '2013-07-08 15:21:35', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('403', '郭树清：推动机构投资者参与上市公司治理', null, null, null, null, null, '2013-07-08 15:21:35', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('404', '郭树清：基金公司要牢固树立客户为中心理念 ', null, null, null, null, null, '2013-07-08 15:21:36', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('405', '郭树清：发展财富管理行业', null, null, null, null, null, '2013-07-08 15:21:37', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('406', '郭树清：个人投资者为资本?场作出巨大贡献', null, null, null, null, null, '2013-07-08 15:21:37', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('407', '郭树清：我们需要一个强大的财富管理行业', null, null, null, null, null, '2013-07-08 15:21:38', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('408', '郭树清：基金公司要坚持价值投资 发挥引领作用', null, null, null, null, null, '2013-07-08 15:21:38', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('409', '郭树清：基金公司应加快向现代财富管理转型', null, null, null, null, null, '2013-07-08 15:21:39', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('410', '郭树清要求基金投资不能靠小道消息 盲目跟风', null, null, null, null, null, '2013-07-08 15:21:40', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('411', '中国基金业协会理事会及监事会选举结果', null, null, null, null, null, '2013-07-08 15:21:41', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('412', '郭树清：基金研究不能靠小道消息', null, null, null, null, null, '2013-07-08 15:21:41', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('413', '郭树清:基金公司应加快向现代财富管理转型', null, null, null, null, null, '2013-07-08 15:21:42', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('414', '基金业将迈入千基时代 呼唤退出机制', null, null, null, null, null, '2013-07-08 15:21:43', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('415', '基金数量破百间隔缩至半年', null, null, null, null, null, '2013-07-08 15:21:43', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('416', '告别股基独大 基金产品走向多元', null, null, null, null, null, '2013-07-08 15:21:44', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('417', '基金经理扩军跟不上基金增长', null, null, null, null, null, '2013-07-08 15:21:45', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('418', '音乐人苏越案终审:因合同诈骗罪获刑15年|苏越|诈骗罪|终审', null, null, null, null, '本报讯(记者邱伟)历时3年零3个月的司法程序，著名音乐人苏越今天等到了自己的最终刑罚：北京市高级法院终审驳回了检方要求顶格判处苏越无期徒刑的抗诉意见，维持了北京市第二中级法院的一审判决：以合同诈骗罪判处苏越15年有期徒刑。苏越合同诈骗500\">\r\n', '2013-07-08 15:33:50', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('419', '2名男子扒窃20元被判7个月罚1千 |男子|扒窃|扒窃入刑', null, null, null, null, '人民网宜州7月5日电(记者林婉仪通讯员吴镇全、吴文)“太不值了，扒20元判7个月，还罚1千，出去后再也不能干这个了”。7月5日上午，因扒窃20元而被一审判处有期徒刑7个月、并处罚金1千元的两名男子银某华和刘某忠在宜州市看守所里懊悔不已。据检察\">\r\n', '2013-07-08 15:33:51', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('420', '3名无业青年因缺钱抢劫超市杀害老板|抢劫|超市|杀害', null, null, null, null, '河北新闻网讯(通讯员韩文江、张宏立)因为缺钱花，几名无业青年结伙抢劫超市，得手后还将超市老板杀害。6月27日，白沟新城公安分局连续奋战六昼夜，成功侦破“621”抢劫杀人案，抓获犯罪嫌疑人崔某、郝某。6月21日下午5时30分，白沟新城公安分局\">\r\n', '2013-07-08 15:33:52', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('421', '青年从地摊买仿真手枪袭击车辆被批捕|仿真枪|袭击|寻衅滋事', null, null, null, null, '河北新闻网讯(通讯员李红宇、刘焕江)外出游玩时从地摊买来仿真手枪，却用来袭击他人车辆从中取乐，到头来还是搬起石头砸自己的脚。7月3日，昌黎县检察院对一起持仿真手枪袭击车辆寻衅滋事案件的犯罪嫌疑人赵兵依法批准逮捕。赵兵，男，19岁，昌黎人，中专\">\r\n', '2013-07-08 15:33:53', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('422', '厨师猥亵9岁女孩致死 其妻发现尸体后报案|厨师|致死|报案', null, null, null, null, '温州一男子猥亵工友幼女，孩子反抗时竟被捂死9岁“小候鸟”惨遭禽兽厨师毒手□通讯员龙公宣本报记者解亮早报讯昨天，网上一直在传：温州市中心某知名酒店的厨师，“强奸一个10来岁的小女孩致死，已潜逃。她的妻子在家中发现女孩尸体后报了案。”这件事传得沸\">\r\n', '2013-07-08 15:33:54', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('423', '山东抢夺公交方向盘嫌犯被批捕 警方重新定性|方向盘|男子|公交车', null, null, null, null, '【坐过站就抢公交车方向盘？被定性为涉嫌危害公共安全！】6月24日，山东淄博89路公交上，乘客王某因坐过站，突然抢夺司机的方向盘，载着乘客的公交车差点撞上栏杆！原本王某被处以行政拘留10天的处罚，后经公安机关调查，最终抢方向盘者被按涉嫌危害公\">\r\n', '2013-07-08 15:33:55', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('424', '2名男子偷狗被发现后用射狗毒针射死主人|摩托车|随州|毒针', null, null, null, null, '荆楚网消息(记者高东起伍迪特约记者王继斌)昨日上午8时许，两名陌生男子骑摩托车窜至随州一个村庄，用安装有毒针的弓弩将狗射死。狗主人发现后出门追赶，盗狗贼竟将毒针射向主人，并用喷雾器喷洒有毒气雾，致一人当场死亡一人中毒。作案后，两盗狗贼骑车逃\">\r\n', '2013-07-08 15:33:56', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('425', '女子刺死与丈夫暧昧女网友|刺死|暧昧|外遇', null, null, null, null, '本报咸阳讯(记者张林赵方)29岁的牛某无意间发现，丈夫与32岁的女网友史某有暧昧关系，她先是打电话责骂，气愤不过又打车从兴平赶到咸阳史某住处。7月5日，两人在小区楼下争吵，推搡中，牛某持刀刺向史某脖子，瞬间鲜血喷出。男子赶到抱起受伤女子5日晚\">\r\n', '2013-07-08 15:33:57', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('426', '祖孙3人等红灯时被喷药车喷农药|农药|红灯|喷药', null, null, null, null, '本报宝鸡讯(记者宋秉琴实习记者鱼茜瑶)7月5日上午10时许，宝鸡市有老两口带着孙女等红灯时，被突然出现的喷药车喷了一身药水，老人说喷药车到来时应该响个音乐，提醒路人及时躲避。目前孙女正住院观察治疗。喷药车静悄悄也不响音乐王女士介绍，5日上午，', '2013-07-08 15:33:58', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('427', '小伙不满他人摆架子将其捅死|捅死|摆架子|自首', null, null, null, null, '本报讯(首席记者张鹏翔)7月2日凌晨，景泰县城发生一起血案，一小伙在街头被捅身亡。案发后，景泰县公安局刑侦部门仅用3小时速破这起故意伤害致死案。经审理查明，今年6月30日，小亮给朋友说日前在喝酒过程中，李某给自己“摆了口”，心里很不爽，要找', '2013-07-08 15:33:59', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('428', '女生夜晚约见网友被带至河滩强奸|强奸|网友|抢劫', null, null, null, null, '本报讯(通讯员杨永平首席记者邢剑扬)5月18日晚9时许，武都区一卫校女学生小菁和小娜一起与网友见面，谁知遭到强奸抢劫。日前，涉嫌强奸罪、抢劫罪的杨某某，涉嫌强奸罪的庞某某、李某某被陇南市武都区人民检察院批准逮捕。今年年初，杨某某通过QQ聊天\"', '2013-07-08 15:34:00', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('429', '嫌疑人与村民产生纠纷杀死对方一家3口跳水自尽|嫌疑人|村民|跳水', null, null, null, null, '中新网宜昌7月7日电(记者郭晓莹)记者7日从湖北当阳市宣传部门获悉，7日上午11时许，当阳市木店村发生一起凶杀案，一家三口被害，嫌犯跳入堰塘自杀身亡。经初步调查，犯罪嫌疑人周某与村民望某因纠纷产生矛盾，当日上午11时许，周窜至该村四组望某家，\"', '2013-07-08 15:34:01', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('430', '女医生拒绝加号遭患者家属脚踹|女医生|加号|医生', null, null, null, null, '本报讯前天上午，在首都儿科研究所附属儿童医院里，一位男性病人家属在要求加号遭到医生拒绝后，突然从背后用脚踹女医生，造成女医生脚部受轻伤。目前，打人者已经被警方带走。昨天上午，记者来到位于雅宝路的儿研所2层的急诊室门前，对于前日上午发生的女医生\"', '2013-07-08 15:34:02', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('431', '女保洁员自称被同事欺负 趁其熟睡用菜刀砍死|保洁员|菜刀|熟睡', null, null, null, null, '本报讯自称被同屋姐妹“欺负得狠了”，河南来京打工的女保洁员朱红青趁同屋李某熟睡之机，用菜刀猛砍李某头面部几十刀，将其砍死。朱红青还将前来劝架的同宿舍另一名女保洁员面部砍了三刀，造成对方轻伤。日前，市一中院以故意杀人罪一审判处朱红青死缓。现年\"', '2013-07-08 15:34:03', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('432', '五旬夫妻街头持刀连刺4人 路人自发制服行凶者|行凶|男子|制服', null, null, null, null, '城市晚报讯7月7日10时30分，长春市解放大路西民主大街交会处，只因为一场争执，一对年近五旬的夫妻当街持刀刺伤4人，而被刺伤的4人是一家四口。凶案发生时，正在此执勤的长春市交警支队朝阳大队51岁的民警魏军发现后立即大声喝止，魏军不顾生死冲上\"', '2013-07-08 15:34:04', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('433', '出租司机每年起诉交警几十次 买书自学交安法|交警|违法|维权', null, null, null, null, '41岁“的哥”刘杰算是和交警杠上了。今年有多少次起诉西安交警，他记不清了，“总有几十个了吧，去年更多”。虽然每次的维权成本至少千元，还“赔着本”，但他仍打算继续维权，“这是个意识问题，现在看着赔了，长远看，如果能让交警部门规范执法，值得！”', '2013-07-08 15:34:05', null, null, null, '0', null, null, '/u/cms/www/201309/29093548tinl.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('434', '僧人欲砍仇家路遇好友 以为其想报信将其砍死', null, null, null, null, '本报宝鸡讯(记者张莉实习记者陈喆)6月27日，宝鸡市陈仓区贾村镇贾村十组的张宝林被人用斧头砍伤，经医院抢救不治身亡，此前，该村一位71岁的白姓老人也被杀害，而行凶者竟是一名出家人。出事前两人关系不错50岁的张宝林平时在镇上修鞋，在邻居们的眼里\"', '2013-07-08 15:34:06', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('435', '报社副主任挪500万被判6年 辩称非公款上诉', null, null, null, null, '新京报讯(记者张媛)因将单位500万元转入女友账户用于购买理财，某报社实业中心副主任岂某及女友马某，被西城法院以挪用公款罪一审判刑6年。二人以涉案款非公款等原因，提出上诉。500万理财获利8000案发前，48岁的岂某为某报社实业中心副主任兼\"', '2013-07-08 15:34:07', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('436', '内地夫妇因航班延误闹香港机场袭警被拘捕', null, null, null, null, '南都讯记者石秋菊发自香港航班延误，乘客鼓噪，闹事打砸，这一幕发生在7月5日的香港机场。一对内地夫妇的男事主砸国泰航空的柜台更泼汽水，被香港警方拘捕时，妻子更涉嫌推倒一名警员，最后夫妇两人都被警方带走调查。香港警方表示，涉案夫妇中丈夫姓徐，称\"', '2013-07-08 15:34:08', null, null, null, '0', null, null, '/u/cms/www/201309/29094750vq4w.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('437', '男子嫖娼后用迷药抢劫卖淫女', null, null, null, null, '晨报讯(记者颜斐)王某嫖娼后骗卖淫女喝下他放入安眠药的饮料，然后待对方熟睡后实施抢劫。近日朝阳区检察院以涉嫌抢劫罪对抢劫卖淫女的王某批准逮捕。今年5月，王某通过微信与张某取得联系，并在微信上谈妥了卖淫嫖娼的价钱。6月2日晚，王某在支付了嫖资后\"', '2013-07-08 15:34:09', null, null, null, '0', null, null, '/u/cms/www/201309/2909470165q8.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('438', '骗子假冒导演谎称能安排演员诈骗获缓刑', null, null, null, null, '晨报讯(记者李庭煊)尹某谎称自己是中影数字基地的执行导演，以安排彭某做特邀演员为诱饵，骗取其1万元。近日怀柔法院以诈骗罪判处尹某拘役4个月，缓刑6个月。去年3月，尹某在怀柔区杨宋镇安乐庄村租了一个群众演员大院，招募群众演员。9月彭某前来应聘，\"', '2013-07-08 15:34:11', null, null, null, '0', null, null, '/u/cms/www/201309/2909462878ng.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('439', '司机穿拖鞋开车低头点烟致追尾造成1人死亡', null, null, null, null, '扬子晚报讯(记者郭一鹏通讯员张晓冬)穿着拖鞋开车，还在行驶中低头点烟，结果抬头时突然发现前面有辆电动车，司机赶紧踩了一脚刹车，哪知拖鞋打滑，车辆没有控制住直接撞了上去。电动车上两名老人倒地后，其中一人因伤势过重不幸死亡。前不久，南京大厂发生\"', '2013-07-08 15:34:11', null, null, null, '0', null, null, '/u/cms/www/201309/29094130ex66.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('440', '男子非法拘禁妻子情夫 殴打侮辱并索钱被诉', null, null, null, null, '晨报讯(记者颜斐)发现妻子有了情人郭某，徐某以讨债为名，伙同两个朋友将郭某拘禁3天，其间不仅实施殴打和侮辱行为，还向郭某索要3000万元补偿费。近日，徐某因涉嫌非法拘禁罪和敲诈勒索罪被公诉到朝阳法院，两同伙也因非法拘禁罪被公诉。据指控，徐某\"', '2013-07-08 15:34:12', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('441', '伪造国字头研究院承接水电项目 曾通过政府审查', null, null, null, null, '本报记者赵志锋中国区域特色经济研究院，听起来多么唬人的机构。如果不是法院在审理民事案件中查清它的真实面目，不知道这个国字头机构还要存在多久。在法院司法建议的推动下，今年5月，这个活动在甘肃省的中国区域特色经济研究院西部中心(以下简称西部中心\"\r\n', '2013-07-08 15:34:13', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('442', '城管局司机袒护无证幼儿园踢飞凳子阻挠采访', null, null, null, null, '法制网记者张冲法制网通讯员郭佳音一段摄录于6月25日的视频，清晰地记录了黑龙江省哈尔滨市城管局一名职工的粗暴行为。画面显示：城管督察车上下来的男子，走到麻辣烫摊前，趁女记者孙晓卓不备一脚将她坐的塑料凳踢飞，孙重重地摔在地上。目前，这名城管局职\"\r\n', '2013-07-08 15:34:14', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('443', '2名女乘客因航班延误殴打地勤人员被拘留5天', null, null, null, null, '温都讯昨晚，市公安局机场分局对“6·28瑞安籍乘客殴打国航地勤人员”一事作出处理：对殴打国航地勤人员朱某某(女)的陈某某(女)、刘某某(女)均给予行政拘留5日并处200元罚款的处罚。记者了解到，昨天这两名女乘客从北京返回温州后，到候机楼派出\"\r\n', '2013-07-08 15:34:15', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('444', '假记者向百名官员寄艳照敲诈信被快递员识破', null, null, null, null, '荆楚网讯(记者翟方)“你好！我是xxx新闻网的记者，我已查到你生活中不为人知的另一面，只要我在网上一传，你将前途尽毁甚至面临牢狱之灾……三天内汇给我25万元人民币，我就毁灭证据。”7月3日下午2时54分，一中年男子走进襄阳市樊城区一家快递公\">\r\n', '2013-07-08 15:34:17', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('445', '男子欠债万元没钱还 自导绑架案骗老婆付赎金', null, null, null, null, '据江淮晨报报道，铜陵一男子在外花天酒地，借债一万元无钱归还，竟与朋友合伙骗自己老婆。7月3日，该男子声称自己被人“绑架”，要求妻子拿“赎金”一万元救自己。最后老婆没骗到，自己的朋友却因吸毒被公安机关依法行政拘留。7月3日，铜陵市长江路派出所在\"\r\n', '2013-07-08 15:34:18', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('446', '新娘婚宴上逃婚 新郎起诉索要精神损失费', null, null, null, null, '本报讯李展辉钟玲珑首席记者程呈报道：新娘婚宴上逃婚，新郎痛苦不已索精神损害赔偿。7月4日，泰和县人民法院一审审理了一起婚约纠纷。2012年9月，吴子华与女子黄晓菊谈起了恋爱，期间，黄晓菊不断向吴子华索要钱财，吴子华先后10次通过银行汇款近3\">\r\n', '2013-07-08 15:34:18', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('447', '醉酒男子强奸孕妇未遂 持刀返回找手机伤人', null, null, null, null, '东南网7月8日讯(海峡导报记者蔡晶晶通讯员杨媛林曼娜)酒后翻墙进别人家，不顾女主人怀有9个月身孕，竟想实施强奸，没能得逞的他还用刀划伤孕妇的婆婆。昨日，漳州市漳浦县检察院以涉嫌强奸罪对黄某龙依法批准逮捕。4月19日晚上，家住漳浦县深土镇的犯\">\r\n', '2013-07-08 15:34:20', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('448', '两名少年为挣钱在多个省市抢劫杀死7人', null, null, null, null, null, '2013-07-08 15:34:21', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('487', '金马影展公布焦点导演', null, null, null, null, '金马50惊喜连连，金马国际影展也端出超级菜色，继公布蔡明亮新作《郊游》担任开幕片，昨天（9月9日）发布的首波片单，聚焦两位当今最受期待的大师候选人，一位是甫以《蓝色是最温暖的颜色》荣获本届戛纳影展金棕榈奖的法国导演阿布戴柯西胥，一位是以《分居风暴》囊括柏林影展金熊奖与奥斯卡最佳外语片的伊朗导演阿斯哈法哈蒂。金马影展不仅会放映他们最新得奖作品，还把两人所有影片一网打尽，让影迷完整见证他们创作历程，探勘大师如何养成。', '2013-08-21 11:34:29', null, null, null, '0', null, null, '/u/cms/www/201309/111745157ps9.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('488', '“蛊术”首现《狄仁杰》 9月28日登陆大银幕', null, null, null, null, '由华谊兄弟出品发行，鬼才导演徐克执导，“金手指”陈国富监制，华谊兄弟总裁王中磊任总制片的古装悬疑巨制《狄仁杰之神都龙王》近日公布了终极预告片，片中曝光了龙王、巨兽、水下神驹等令人震撼的元素，让观众瞠目结舌，而片中“手臂长出怪草”和“朝堂之上惊现毒虫”的镜头则让观众在震撼之外增添了几分好奇。', '2013-08-21 11:59:29', null, null, null, '0', null, null, '/u/cms/www/201309/11173804j6hj.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('489', '《金刚王》曝解密功夫秘籍', null, null, null, null, '“亚洲舞王”南贤俊演绎魔尸金刚“疯猿”', '2013-08-21 13:40:11', null, null, null, '0', null, null, '/u/cms/www/201309/11173334yvdd.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('490', '《大明劫》曝概念海报 冯远征戴立忍末日战', null, null, null, null, '《大明劫》曝概念海报 冯远征戴立忍末日战', '2013-08-21 14:35:35', null, null, null, '0', null, null, '/u/cms/www/201309/11172219hw2t.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('491', '《抹布女》收视攀升 海清张译激情床照曝光', null, null, null, null, '网易娱乐9月11日报道 由海清、张译、芦芳生联袂主演的都市生活轻喜剧《抹布女也有春天》，以火爆网络的“女汉子”都市新女性造型，及热门的抹布女话题新一轮收视正在节节攀升至0.9。该剧接连不断的搞笑故事情节和主演们夸张的漫画式表演，新婚之夜海清和张译二人在床上甜蜜一吻时，海清身下的床板突然塌陷，两人只能相视的尴尬一笑。后续中并上演着激情床戏部份，颠覆帅气女汉子。', '2013-08-21 14:47:08', null, null, null, '0', null, null, '/u/cms/www/201309/1117164754x7.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('492', '《老公的春天》播出过半', null, null, null, null, '4月19日报道 陈好产后首部作品《新编辑部故事》将于本月20日全国上星播出。日前片方再度公布一组剧照，女一号安妮的扮演者陈好一袭墨绿旗袍出镜，墨镜、皮草、奢侈品手包尽显运营总监的时尚女魔头气派，而陈好的婀娜身段和姣好面容让人很难相信这是她产后拍摄的作品。另外cosplay风格的照片尽显六人搞怪趣味，陈好还与黄海波穿上凤冠霞帔上演成亲戏码，用百变造型来诠释重口味喜剧，时尚造型与搞怪的视觉冲撞也让观众的期待值高涨。', '2013-08-21 14:49:52', null, null, null, '0', null, null, '/u/cms/www/201309/11171211kmih.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('493', '《错伏》今晚大结局', null, null, null, null, '2月5日报道 由霍建华、陈乔恩、韩栋等人主演的《笑傲江湖》将于今晚登陆湖南卫视播出。日前片方推出了韩栋饰演的田伯光的一组剧照，剧中田伯光经典造型一一提前曝光，从采花大盗到不可不戒和尚，韩栋的造型也随之变化。', '2013-08-21 14:54:53', null, null, null, '0', null, null, '/u/cms/www/201309/111707541g63.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('494', '《桐柏英雄》画册曝光', null, null, null, null, null, '2013-08-21 14:58:46', null, null, null, '0', null, null, '/u/cms/www/201309/11170224nw94.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('495', '刘晓庆今日大婚 第四任老公系将门之后政协委员', null, null, null, null, '8月20日早上，刘晓庆团队透露刘晓庆与第四任丈夫已与去年注册结婚，声明中还称其丈夫比刘晓庆大，是将门之后，事业成功。晚些时分，得知刘晓庆将于美国时间8月20日在美国举行婚礼。', '2013-08-21 15:12:25', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('496', '赵本山女儿正式进军娱乐圈 将与潘长江女儿比拼', null, null, null, null, '浙江卫视宣布推出全国首档青年励志节目，陈宝国儿子陈月末、潘长江女儿潘阳等30多位明星子女均已确定加盟。赵本山女儿赵一涵也有望加盟，藉此正式进军娱乐圈，并将与潘长江女儿潘阳同台对决。', '2013-08-21 15:19:59', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('498', '冯小刚定春晚相声小品类基调：唱赞歌一律不要', null, null, null, null, '前日的碰头会上，冯小刚大胆地提出要摒弃那些煽情、唱赞歌的东西，所有创作者要放开手脚，敢写敢说，呈现出带尖带刺、有棱有角的相声和小品来。', '2013-08-22 09:13:41', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('499', '朱为群：大数据时代亟须透明税制', null, null, null, null, null, '2013-08-22 09:21:00', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('500', '宋石男：谣言是世界上最古老的传媒', null, null, null, null, '专栏作家，西南民族大学教师，为《南方周末》、《新京报》、《东方早报》、《看历史》等平媒撰稿，现在《看天下》开有备受好评的“风物志”叙事专栏，在《南方都市报》开有个论专栏及历史评论专栏，在《新快报》开有“微言宋听”一周微博点评专栏。', '2013-08-22 09:28:09', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('501', '俞天任：麦克阿瑟在日本到底做了什么？', null, null, null, null, null, '2013-08-22 09:45:50', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('506', '云南山洪冲断路桥 致4人遇难7人失踪', null, null, null, null, '9月9日，出事的客车残骸。云南省大理州云龙县只嘎村一桥梁9月8日因山洪泥石流灾害突然断裂，导致一辆大客车和一辆微型车坠河。截至目前已经导致4人死亡，7人失踪，26人受伤送往医院治疗。', '2013-09-10 09:22:39', null, null, null, '0', null, null, '/u/cms/www/201309/10092229195q.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('507', '广州又现“楼歪歪” 三栋居民楼倾斜', null, '胥柏波', null, null, '上月初，广州番禺区厦滘村一居民楼因地基下陷，房屋倾斜被拆除，5日晚至今，当地又连续有三栋居民楼发生倾斜。目前，周边十余栋受影响的房屋村民已经全部疏散，当地街道办开放临时安置点接纳受影响群众，而疑似事故元凶的附近工地已停止施工', '2013-09-10 09:50:40', null, null, null, '0', null, null, '/u/cms/www/201309/100950366ij3.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('508', '海口酒行发生爆炸两人受伤 玻璃碎片炸飞50米外', null, '毛鑫', null, null, '今天早上8点40分左右，海口市蓝天路一家酒行发生爆炸，两人受伤被送往医院。现场破坏严重，50米外能看到被炸飞的玻璃碎片，停在路边的多部车辆受损。目前警方已介入调查', '2013-09-10 09:53:55', null, null, null, '0', null, null, '/u/cms/www/201309/100953460bvo.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('509', '朱镕基新书曾提前印样本向中央领导征求意见', null, null, null, null, '京华时报讯 由人民出版社出版的《朱镕基上海讲话实录》于8月12日向全国发行。昨天，人民出版社常务副社长任超做客中新网时介绍，该书首印110万册全部发出，并已取得65万册的销售佳绩。任超表示，该书出版过程中，朱镕基同志要求实事求是，如实呈现当时的一些情况', '2013-09-10 10:28:25', '/u/cms/www/201309/10102800aa0b.mp4', 'FLV', null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('510', '官方称翻越澳门大学新校区围墙属偷渡行为', null, null, null, null, '澳门大学横琴新校区围墙外，围栏上间隔一段就悬挂了“严禁翻越澳门大学围墙，违者将予以行政处罚”字样的警示横幅。', '2013-09-10 10:38:52', null, null, null, '0', null, null, '/u/cms/www/201309/10103806oifr.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('511', '毛泽东逝世37周年 亲属及工作人员现身纪念', null, null, null, null, '9月9日，毛泽东亲属、身边工作人员来到毛泽东纪念堂。', '2013-09-10 10:45:34', null, null, null, '0', null, null, '/u/cms/www/201309/10104531ukj5.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('512', '房价上涨最快的城市排行 去那买房让你赚翻', null, null, null, null, null, '2013-09-10 14:40:05', null, null, null, '0', null, null, '/u/cms/www/201309/10144918u244.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('513', '北影开学典礼新生睡倒一片 红衣女抢镜', null, null, null, null, '网易娱乐9月6日报道（图/李道忠 文/小易）9月5日，北京电影学院开学典礼如期举行。2013届新生和老师们挤满了学院礼堂，其中一位红衣女生相当抢镜。在不到30分钟的校领导演讲后，新生们终于还是难抵瞌睡虫袭击，睡倒一片。据知，今年的表演系招收人数超过了往年，众多学生中年龄最小的仅有16岁，全校各系共招573人。据在校生表示，表演系之所以考的人数众多也和高考文化课分数低有关，所以很多报考的学生并非成绩很好。', '2013-09-12 10:59:15', null, null, null, '0', null, null, '/u/cms/www/201309/12105908yuqz.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('514', '北影表演系新生自曝K歌照', null, null, null, null, '网易娱乐9月10日报道(图文/小易) 日前，几名认证为“北京电影学院2013级表演本科”的新生，在微博上曝光了同学们聚在一起唱歌的照片，还感慨“中国好同学，我们班唱歌都太好听了。”', '2013-09-12 11:04:04', null, null, null, '0', null, null, '/u/cms/www/201309/121104013vts.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('515', '中戏办开学典礼 2013级新生首曝光', null, null, null, null, '网易娱乐9月10日报道（图文/小易）9月9日上午，中央戏剧学院2013—2014学年开学典礼在学院昌平校区举行，来自中国乃至世界各地的570名新生参加了典礼。', '2013-09-12 11:08:22', null, null, null, '0', null, null, '/u/cms/www/201309/12110817ejy1.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('516', '高考36年变迁珍贵影像', null, null, null, null, '1977年冬天，中断了十年又重新恢复的高考制度，开始改变这个庞大国家无数人的命运。一纸试卷废除了“推荐上大学”，给当时渴望改变命运的人们一个公平竞争的机会。很多人借此叩开了另一个世界的大门，走上辉煌的人生道路。', '2013-09-12 11:12:45', null, null, null, '0', null, null, '/u/cms/www/201309/1211124392sy.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('517', '美国富二代大学生的奢侈生活', null, null, null, null, ' 在美国，日趋上涨的大学花费已经严重威胁到了低收入家庭的学生，大量学生即使在毕业四五年后还身背学债。而另一方面，一种迎合富人家庭大学生的服务新模式已经出现。', '2013-09-12 11:19:03', null, null, null, '0', null, null, '/u/cms/www/201309/1211190051if.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('518', '华中师范大学新生家长“打地铺”过夜', null, null, null, null, '2013年9月7日，武汉华中师范大学新生入学首日，学校在该校佑铭体育馆内为新生家长们提供免费住宿。当晚有400多名学生家长在体育馆打地铺过夜。这是该校连续第8年在新生开学报到时为家长提供免费住宿。', '2013-09-12 11:22:50', null, null, null, '0', null, null, '/u/cms/www/201309/12112247lfhe.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('519', '李冰冰时尚大片', null, null, null, null, '网易娱乐9月12日报道(图文/小易)日前，李冰冰为某知名时尚杂志拍摄的封面大片曝光。', '2013-09-12 12:07:42', null, null, null, '0', null, null, '/u/cms/www/201309/12140033b5mx.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('520', '52岁刘德华上海开唱与舞伴玩借位吻', null, null, null, null, '网易娱乐9月12日报道(图文/CFP) 9月11日，刘德华上海演唱会举行。为搏粉丝开心他也是下足了工夫，仅靠4条钢索支撑的Gondola-无盖吊篮缓缓升上五层楼高再沿著舞台顶层钢架横行。表演中刘德华不仅与舞伴玩借位吻，还大秀八块肌肉情。情绪激动的刘德华最后更泣不成声。', '2013-09-12 13:43:06', null, null, null, '0', null, null, '/u/cms/www/201309/12135944xk9f.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('521', '全智贤着衬衫秀长腿', null, null, null, null, '网易娱乐9月12日报道(图文/东星) 全智贤日前离开香港返韩国。当天，全智贤穿上长恤衫服装，大秀长腿配戴墨镜和黑鞋现身香港机场，助手为她辨理登机手续后，在外籍保安护送下离港入闸，最后还跟保安握手及道谢，便向传媒挥手离开。', '2013-09-12 13:45:55', null, null, null, '0', null, null, '/u/cms/www/201309/12134552bk69.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('522', '王智写真演绎复古风情', null, null, null, null, '网易娱乐9月12日报道(图文/小易) 正值中秋来临之际，新生代功夫女星王智推出一组复古写真。', '2013-09-12 13:49:36', null, null, null, '0', null, null, '/u/cms/www/201309/12134933sxsx.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('523', '宋祖英升任团长后首带队演出', null, null, null, null, '网易娱乐9月12日报道(图文/小易) 10日，宋祖英升任团长后首次带队海政文工团到海校献唱，获得热烈掌声。今年8月，宋祖英由海政文工团副团长升任正团长，此前曾传她会减少演艺工作。', '2013-09-12 13:52:46', null, null, null, '0', null, null, '/u/cms/www/201309/12135243x57l.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('524', '杨幂亮相纽约时装周', null, null, null, null, '网易娱乐9月11日报道(图/小易) 近日，杨幂亮相纽约时装周。', '2013-09-12 13:58:30', null, null, null, '0', null, null, '/u/cms/www/201309/12135827xy0y.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('525', '王丽坤纯美大片', null, null, null, null, '网易娱乐9月11日报道(图文/小易) 近日，素颜女神王丽坤应某知名杂志邀约拍摄了一组纯美大片。', '2013-09-12 14:08:58', null, null, null, '0', null, null, '/u/cms/www/201309/12140851yp14.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('526', '张智霖签新公司获直升机接送 空姐护驾', null, null, null, null, '网易娱乐9月11日报道(图文/CFP) 10日，张智霖高价签约中国3D数码娱乐有限公司。因为《冲上云霄2》而身价倍增的张智霖签约新公司获空姐陪伴、飞机接送。', '2013-09-12 14:14:53', null, null, null, '0', null, null, '/u/cms/www/201309/121414505xah.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('527', '何晟铭出席敦煌保护活动 首谈《盗墓笔记》', null, null, null, null, '网易娱乐9月10日报道(图文/小易) 9月9日，敦煌保护基金启动仪式在杭州举行，网易CEO丁磊、演员何晟铭等出席活动并呼吁社会各界共同关注敦煌、保护文化，让敦煌文明代代相传。何晟铭作为整场活动唯一表演嘉宾，现场颇为应景的演唱一曲《佛说》。而主持人也在采访中问到：“听说您要出演《盗墓笔记》？会不会到敦煌来采景呢？”何晟铭则幽默的回答：“大早上的别提晚上的事，要盗墓也得去长白山。敦煌文化需要大家共同保护，如果有机会希望能亲自去敦煌拍片。”', '2013-09-12 14:23:30', null, null, null, '0', null, null, '/u/cms/www/201309/12142322wa0z.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('528', '宋佳封面大片曝光 恬淡清新演绎早秋时尚', null, null, null, null, '网易娱乐9月9日报道（图文/小易）近日，小宋佳曝光一组封面大片。照片中著名演员小宋佳恬淡清新，将早秋时尚演绎的淋漓尽致。据悉，由小宋佳主演的《四十九日祭》已完美收官，将于2014年播出。', '2013-09-12 14:28:33', null, null, null, '0', null, null, '/u/cms/www/201309/12142830poeb.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('529', '吴秀波大奔头亮相 尽显迷人绅士范儿', null, null, null, null, '网易娱乐9月9日报道（图文/小易）近日，吴秀波出席某品牌活动，当晚吴秀波身着一身黑色西装，内搭彩色长丝巾尽显儒雅绅士风度，梳着黑色大奔儿头，整个造性让人眼前一亮。', '2013-09-12 14:33:47', null, null, null, '0', null, null, '/u/cms/www/201309/12143342sfmg.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('530', '曾黎复古造型亮相纽约时装周 红唇成亮点', null, null, null, null, '网易娱乐9月10日报道(文/小易) 女星曾黎近日受邀赴美出席2014纽约春夏时装周，秀场上以一袭复古抹胸裙成功抢镜，性感红唇更是亮点所在。而秀场上的性感佳人曾黎，近日也被曝私下其实是大大咧咧的性格，反差极大。', '2013-09-12 14:57:01', null, null, null, '0', null, null, '/u/cms/www/201309/121456599tnd.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('531', '关之琳穿超短裙庆生', null, null, null, null, '网易娱乐9月8日报道 (图文/CFP) 9月07日，关之琳现身北京出席某慈善晚宴，主办方特意为关之琳庆生。', '2013-09-12 15:07:03', null, null, null, '0', null, null, '/u/cms/www/201309/12150701tmkd.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('532', '黄晓明现身俄罗斯中国电影节', null, null, null, null, '网易娱乐9月7日报道(图文/CFP) 圣彼得堡当地时间9月4日晚，俄罗斯2013中国电影节在圣彼得堡开幕，开幕式现场星光熠熠，成龙携手章子怡作为推广大使到场助阵，开幕影片《中国合伙人》主创导演陈可、主演黄晓明、佟大为、邓超、杜鹃等也专程赶来出席开幕式。仪式开始前，章子怡与好友佟大为、黄晓明在异国相逢开心合影，勾肩搭背尽显亲密无间。', '2013-09-12 15:11:43', null, null, null, '0', null, null, '/u/cms/www/201309/12151139jrv6.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('533', '景甜与范冰冰相谈甚欢', null, null, null, null, '2013 Jade Cool Guy全国总决赛在北京举行，景甜作为神秘颁奖嘉宾出席了活动。红毯结束后，景甜同范冰冰一起入座。两人在台下相谈甚欢。', '2013-09-12 15:23:00', null, null, null, '0', null, null, '/u/cms/www/201309/12152257va9g.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('534', '小宋佳时尚大片', null, null, null, null, '日前，小宋佳曝光了一组最新时尚大片。', '2013-09-12 15:36:56', null, null, null, '0', null, null, '/u/cms/www/201309/121536505s2h.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('535', '熊乃瑾清新写真', null, null, null, null, '日前，熊乃瑾曝光了一组清新写真。', '2013-09-12 15:43:13', null, null, null, '0', null, null, '/u/cms/www/201309/121543059cct.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('536', '章子怡献唱公益节目讲述梦想', null, null, null, null, '9月1日，一年一度的大型公益节目「开学第一课」将如约和全国青少年见面。今年，以＂梦想＂为主题的节目上，章子怡作为讲述人，为全国青少年献上＂梦想的坚持＂为主题的第一课，并在节目结尾献唱歌曲「梦想长大了」。中国女航天员王亚平、轮椅上的舞者寥智、钢琴家郎朗、88岁高龄的资深老师潘其华为＂梦想＂讲述他们心中的第一课。', '2013-09-12 15:48:01', null, null, null, '0', null, null, '/u/cms/www/201309/121547582f5t.jpg', null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('548', '测试新闻', null, null, null, null, null, '2014-03-10 07:56:45', null, null, null, '0', null, null, null, null, '/WEB-INF/t/cms/www/def/content/新闻内容.html', '1');
INSERT INTO `jc_content_ext` VALUES ('550', '老人公交车上发病抢救无效去世 家属索赔31万元', '老人公交车上发病抢救无效去世 家属索赔31万元', '王雄涛', null, null, '属将公交所属的温州交运集团城东公交有限公司告上法庭，要求赔偿损失31万多元。', '2014-05-11 11:07:44', null, null, null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('551', '赵又廷高圆圆', null, null, null, null, null, '2014-06-08 07:04:10', '/u/cms/www/201406/08070401nz46.avi', 'REAL', null, '0', null, null, null, null, null, '1');
INSERT INTO `jc_content_ext` VALUES ('552', '测试直播', null, null, null, null, null, '2014-06-09 23:22:01', null, null, null, '0', null, null, null, null, '/WEB-INF/t/cms/www/def/content/live_content.html', '1');

-- ----------------------------
-- Table structure for `jc_content_group_view`
-- ----------------------------
DROP TABLE IF EXISTS `jc_content_group_view`;
CREATE TABLE `jc_content_group_view` (
  `content_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`content_id`,`group_id`),
  KEY `fk_jc_content_group_v` (`group_id`),
  CONSTRAINT `fk_jc_content_group_v` FOREIGN KEY (`group_id`) REFERENCES `jc_group` (`group_id`),
  CONSTRAINT `fk_jc_group_content_v` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容浏览会员组关联表';

-- ----------------------------
-- Records of jc_content_group_view
-- ----------------------------
INSERT INTO `jc_content_group_view` VALUES ('550', '1');

-- ----------------------------
-- Table structure for `jc_content_picture`
-- ----------------------------
DROP TABLE IF EXISTS `jc_content_picture`;
CREATE TABLE `jc_content_picture` (
  `content_id` int(11) NOT NULL,
  `priority` int(11) NOT NULL COMMENT '排列顺序',
  `img_path` varchar(100) NOT NULL COMMENT '图片地址',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`content_id`,`priority`),
  CONSTRAINT `fk_jc_picture_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容图片表';

-- ----------------------------
-- Records of jc_content_picture
-- ----------------------------
INSERT INTO `jc_content_picture` VALUES ('487', '0', '/u/cms/www/201309/11174436pnoi.jpg', '金马50惊喜连连，金马国际影展也端出超级菜色，继公布蔡明亮新作《郊游》担任开幕片，昨天（9月9日）发布的首波片单，聚焦两位当今最受期待的大师候选人，一位是甫以《蓝色是最温暖的颜色》荣获本届戛纳影展金棕榈奖的法国导演阿布戴柯西胥，一位是以《分居风暴》囊括柏林影展金熊奖与奥斯卡最佳外语片的伊朗导演阿斯哈法哈蒂。金马影展不仅会放映他们最新得奖作品，还把两人所有影片一网打尽，让影迷完整见证他们创作历程，探勘大师如何养成。');
INSERT INTO `jc_content_picture` VALUES ('487', '1', '/u/cms/www/201309/11174439eb3l.jpg', '《海滩的那一天》让法哈蒂首夺柏林影展最佳导演');
INSERT INTO `jc_content_picture` VALUES ('487', '2', '/u/cms/www/201309/1117444317kg.jpg', '阿布戴柯西胥（Abdellatif Kechiche）是有北非血統的法籍导演');
INSERT INTO `jc_content_picture` VALUES ('487', '3', '/u/cms/www/201309/111744466aux.jpg', '阿斯哈法哈蒂（Asghar Farhadi）是把伊朗从单纯写实带向错综复杂的代表性人物');
INSERT INTO `jc_content_picture` VALUES ('488', '0', '/u/cms/www/201309/111737053v9i.jpg', '由华谊兄弟出品发行，鬼才导演徐克执导，“金手指”陈国富监制，华谊兄弟总裁王中磊任总制片的古装悬疑巨制《狄仁杰之神都龙王》近日公布了终极预告片，片中曝光了龙王、巨兽、水下神驹等令人震撼的元素，让观众瞠目结舌，而片中“手臂长出怪草”和“朝堂之上惊现毒虫”的镜头则让观众在震撼之外增添了几分好奇。');
INSERT INTO `jc_content_picture` VALUES ('488', '1', '/u/cms/www/201309/11173708ffx2.jpg', '由华谊兄弟出品发行，鬼才导演徐克执导，“金手指”陈国富监制，华谊兄弟总裁王中磊任总制片的古装悬疑巨制《狄仁杰之神都龙王》近日公布了终极预告片，片中曝光了龙王、巨兽、水下神驹等令人震撼的元素，让观众瞠目结舌，而片中“手臂长出怪草”和“朝堂之上惊现毒虫”的镜头则让观众在震撼之外增添了几分好奇。');
INSERT INTO `jc_content_picture` VALUES ('488', '2', '/u/cms/www/201309/11173711toqx.jpg', '由华谊兄弟出品发行，鬼才导演徐克执导，“金手指”陈国富监制，华谊兄弟总裁王中磊任总制片的古装悬疑巨制《狄仁杰之神都龙王》近日公布了终极预告片，片中曝光了龙王、巨兽、水下神驹等令人震撼的元素，让观众瞠目结舌，而片中“手臂长出怪草”和“朝堂之上惊现毒虫”的镜头则让观众在震撼之外增添了几分好奇。');
INSERT INTO `jc_content_picture` VALUES ('488', '3', '/u/cms/www/201309/11173719ykxk.jpg', '由华谊兄弟出品发行，鬼才导演徐克执导，“金手指”陈国富监制，华谊兄弟总裁王中磊任总制片的古装悬疑巨制《狄仁杰之神都龙王》近日公布了终极预告片，片中曝光了龙王、巨兽、水下神驹等令人震撼的元素，让观众瞠目结舌，而片中“手臂长出怪草”和“朝堂之上惊现毒虫”的镜头则让观众在震撼之外增添了几分好奇。');
INSERT INTO `jc_content_picture` VALUES ('488', '4', '/u/cms/www/201309/11173718do4k.jpg', '');
INSERT INTO `jc_content_picture` VALUES ('488', '5', '/u/cms/www/201309/11173724ul7o.jpg', '由华谊兄弟出品发行，鬼才导演徐克执导，“金手指”陈国富监制，华谊兄弟总裁王中磊任总制片的古装悬疑巨制《狄仁杰之神都龙王》近日公布了终极预告片，片中曝光了龙王、巨兽、水下神驹等令人震撼的元素，让观众瞠目结舌，而片中“手臂长出怪草”和“朝堂之上惊现毒虫”的镜头则让观众在震撼之外增添了几分好奇。');
INSERT INTO `jc_content_picture` VALUES ('489', '0', '/u/cms/www/201309/11173244ggfv.jpg', '“亚洲舞王”南贤俊演绎魔尸金刚“疯猿”');
INSERT INTO `jc_content_picture` VALUES ('489', '1', '/u/cms/www/201309/1117323496nx.jpg', '《金刚王?死亡救赎》三战之“伤坛”海报');
INSERT INTO `jc_content_picture` VALUES ('489', '2', '/u/cms/www/201309/11173222qknp.jpg', '金刚王释延能神情凝重');
INSERT INTO `jc_content_picture` VALUES ('489', '3', '/u/cms/www/201309/111732181p5u.jpg', '魔尸金刚“疯猿”阴狠毒辣');
INSERT INTO `jc_content_picture` VALUES ('489', '4', '/u/cms/www/201309/11173213qlec.jpg', '释延能、南贤俊“伤坛”对决');
INSERT INTO `jc_content_picture` VALUES ('489', '5', '/u/cms/www/201309/11173208w8ji.jpg', '释延能、南贤俊水下激烈对打');
INSERT INTO `jc_content_picture` VALUES ('490', '0', '/u/cms/www/201309/11172127ltqx.jpg', '集中焚烧染病而死的士兵');
INSERT INTO `jc_content_picture` VALUES ('490', '1', '/u/cms/www/201309/111721308eyj.jpg', '军营病况危急 士兵带面巾制药');
INSERT INTO `jc_content_picture` VALUES ('490', '2', '/u/cms/www/201309/11172134wikw.jpg', '满城瘟疫弥漫 士兵屠宰战马');
INSERT INTO `jc_content_picture` VALUES ('490', '3', '/u/cms/www/201309/11172137nwy5.jpg', '孙传庭检阅出战士兵');
INSERT INTO `jc_content_picture` VALUES ('491', '0', '/u/cms/www/201309/11171546aux9.jpg', '网易娱乐9月11日报道 由海清、张译、芦芳生联袂主演的都市生活轻喜剧《抹布女也有春天》，以火爆网络的“女汉子”都市新女性造型，及热门的抹布女话题新一轮收视正在节节攀升至0.9。该剧接连不断的搞笑故事情节和主演们夸张的漫画式表演，新婚之夜海清和张译二人在床上甜蜜一吻时，海清身下的床板突然塌陷，两人只能相视的尴尬一笑。后续中并上演着激情床戏部份，颠覆帅气女汉子。');
INSERT INTO `jc_content_picture` VALUES ('491', '1', '/u/cms/www/201309/111715499z6t.jpg', '海清张译裸戏搞笑');
INSERT INTO `jc_content_picture` VALUES ('491', '2', '/u/cms/www/201309/11171553t5kf.jpg', '甜蜜爱情温暖相拥');
INSERT INTO `jc_content_picture` VALUES ('491', '3', '/u/cms/www/201309/11171558kiit.jpg', '《抹布女》剧照');
INSERT INTO `jc_content_picture` VALUES ('491', '4', '/u/cms/www/201309/11171603em6o.jpg', '张译上裸爆笑床照');
INSERT INTO `jc_content_picture` VALUES ('491', '5', '/u/cms/www/201309/11171606wnmy.jpg', '夫妻共枕');
INSERT INTO `jc_content_picture` VALUES ('492', '0', '/u/cms/www/201309/11171054ja61.jpg', '张国立紧紧抱住王雅捷');
INSERT INTO `jc_content_picture` VALUES ('492', '1', '/u/cms/www/201309/11171058td8f.jpg', '艾娇娇让人心生爱怜');
INSERT INTO `jc_content_picture` VALUES ('492', '2', '/u/cms/www/201309/11171102ae78.jpg', '王雅捷大闹新居');
INSERT INTO `jc_content_picture` VALUES ('492', '3', '/u/cms/www/201309/11171106qaui.jpg', '王雅捷深陷情感纠葛');
INSERT INTO `jc_content_picture` VALUES ('492', '4', '/u/cms/www/201309/11171109m2sr.jpg', '王雅捷内心痛楚');
INSERT INTO `jc_content_picture` VALUES ('493', '0', '/u/cms/www/201309/11170703j2h2.jpg', '《错伏》剧照 沙溢');
INSERT INTO `jc_content_picture` VALUES ('493', '1', '/u/cms/www/201309/111707071tn0.jpg', '《错伏》剧照 沙溢 申军谊');
INSERT INTO `jc_content_picture` VALUES ('493', '2', '/u/cms/www/201309/11170712hp37.jpg', '《错伏》剧照 沙溢 罗海琼');
INSERT INTO `jc_content_picture` VALUES ('493', '3', '/u/cms/www/201309/111707001w2k.jpg', '《错伏》剧照 罗海');
INSERT INTO `jc_content_picture` VALUES ('493', '4', '/u/cms/www/201309/11170656zz9b.jpg', '《错伏》剧照 罗海琼');
INSERT INTO `jc_content_picture` VALUES ('493', '5', '/u/cms/www/201309/11170651qc2f.jpg', '《错伏》剧照 罗海琼沙溢');
INSERT INTO `jc_content_picture` VALUES ('494', '0', '/u/cms/www/201309/11165640djk4.jpg', ' 网易娱乐9月11日报道 由上海好剧影视有限公司、上海宜辰工作室联合出品，何润东、李沁、阚清子、张勋杰联袂主演的大型情感励志剧《璀璨人生》正在湖南卫视金鹰独播剧场热播。剧中，“高富帅”何润东爱上了“灰姑娘”李沁，在童话般的国度——瑞士浪漫谱写“璀璨人生”。  ');
INSERT INTO `jc_content_picture` VALUES ('494', '1', '/u/cms/www/201309/111656366w4b.jpg', '何润东与李沁');
INSERT INTO `jc_content_picture` VALUES ('494', '2', '/u/cms/www/201309/11165633po01.jpg', '何润东携手李沁寻真爱');
INSERT INTO `jc_content_picture` VALUES ('494', '3', '/u/cms/www/201309/111656292jx9.jpg', '何润东恋上李沁');
INSERT INTO `jc_content_picture` VALUES ('494', '4', '/u/cms/www/201309/11165624319n.jpg', '何润东李沁异国浪漫恋爱');
INSERT INTO `jc_content_picture` VALUES ('494', '5', '/u/cms/www/201309/11165619lkm8.jpg', '何润东李沁情定瑞士');
INSERT INTO `jc_content_picture` VALUES ('506', '0', '/u/cms/www/201309/22100555lytj.jpg', '9月9日，出事的客车残骸。云南省大理州云龙县只嘎村一桥梁9月8日因山洪泥石流灾害突然断裂，导致一辆大客车和一辆微型车坠河。截至目前已经导致4人死亡，7人失踪，26人受伤送往医院治疗。中新社发 王星皓 摄\r\n');
INSERT INTO `jc_content_picture` VALUES ('506', '1', '/u/cms/www/201309/22100558gfsb.jpg', '9月9日，出事的客车残骸。云南省大理州云龙县只嘎村一桥梁9月8日因山洪泥石流灾害突然断裂，导致一辆大客车和一辆微型车坠河。截至目前已经导致4人死亡，7人失踪，26人受伤送往医院治疗。中新社发 王星皓 摄\r\n');
INSERT INTO `jc_content_picture` VALUES ('506', '2', '/u/cms/www/201309/22100601l1us.jpg', '9月9日，出事的客车残骸。云南省大理州云龙县只嘎村一桥梁9月8日因山洪泥石流灾害突然断裂，导致一辆大客车和一辆微型车坠河。截至目前已经导致4人死亡，7人失踪，26人受伤送往医院治疗。中新社发 王星皓 摄\r\n');
INSERT INTO `jc_content_picture` VALUES ('506', '3', '/u/cms/www/201309/22100606t8mw.jpg', '9月9日，出事的客车残骸。云南省大理州云龙县只嘎村一桥梁9月8日因山洪泥石流灾害突然断裂，导致一辆大客车和一辆微型车坠河。截至目前已经导致4人死亡，7人失踪，26人受伤送往医院治疗。中新社发 王星皓 摄\r\n');
INSERT INTO `jc_content_picture` VALUES ('506', '4', '/u/cms/www/201309/22100611o2gl.jpg', '9月9日，出事的客车残骸。云南省大理州云龙县只嘎村一桥梁9月8日因山洪泥石流灾害突然断裂，导致一辆大客车和一辆微型车坠河。截至目前已经导致4人死亡，7人失踪，26人受伤送往医院治疗。中新社发 王星皓 摄\r\n');
INSERT INTO `jc_content_picture` VALUES ('510', '0', '/u/cms/www/201309/22094752xoxd.jpg', '澳门大学横琴新校区围墙外，围栏上间隔一段就悬挂了“严禁翻越澳门大学围墙，违者将予以行政处罚”字样的警示横幅。\r\n ');
INSERT INTO `jc_content_picture` VALUES ('510', '1', '/u/cms/www/201309/22094906lrj8.jpg', '澳门大学横琴新校区图书馆。新华社记者张金加摄');
INSERT INTO `jc_content_picture` VALUES ('510', '2', '/u/cms/www/201309/22094911xe9x.jpg', '2013年7月19日拍摄的横琴岛澳门大学新校区。横琴校区采用澳门法律管治，以围栏和人工河与横琴岛其他区域隔离，并在澳门路氹城和横琴校区之间设置唯一的出入口，透过一条澳门首条的人车两用海底隧道连接两岸，方便澳大教师学生在毋须过关的情况下便可以上班上学。 新华社记者 魏蒙 摄\r\n');
INSERT INTO `jc_content_picture` VALUES ('510', '3', '/u/cms/www/201309/22094915t8h1.jpg', ' 澳门大学横琴新校区大会堂。新华社记者张金加摄');
INSERT INTO `jc_content_picture` VALUES ('510', '4', '/u/cms/www/201309/22094918gnze.jpg', '7月19日，工人们在横琴岛澳门大学新校区清运建筑垃圾。澳门大学校园的水、电、燃气、通讯、警察、消防和邮政等服务均由澳门提供，与澳门本地无异。 新华社记者 魏蒙 摄');
INSERT INTO `jc_content_picture` VALUES ('511', '0', '/u/cms/www/201309/22093458gynd.jpg', '9月9日，毛泽东亲属、身边工作人员来到毛泽东纪念堂。他们是：毛泽东侄女毛小青（右五）、侄外孙女毛雅慧（左二），毛泽东身边工作人员钱嗣杰（右四）、张木齐（左四）、刘学骞（左三）、曾文（右三）。（中红网李学叶摄）');
INSERT INTO `jc_content_picture` VALUES ('511', '1', '/u/cms/www/201309/22093502mmft.jpg', '女儿李敏率子孙向毛泽东敬献的花篮。（中红网李学叶摄）');
INSERT INTO `jc_content_picture` VALUES ('511', '2', '/u/cms/www/201309/22093506l8pv.jpg', '毛泽东身边工作人员在毛主席纪念堂。自左至右为：曾文、周福明、林增升、张木齐、王笃恭。（中红网郑全摄）');
INSERT INTO `jc_content_picture` VALUES ('511', '3', '/u/cms/www/201309/22093509qm3l.jpg', '毛泽东身边工作人员王笃恭（右三）、耿福东（右四）、张木齐（左二）和中国毛体书法艺术研究会常务副会长刘桂芳（左一）在毛主席纪念堂。（中红网李学叶摄）');
INSERT INTO `jc_content_picture` VALUES ('511', '4', '/u/cms/www/201309/22093513srmf.jpg', ' 毛岸英生前妻子刘思齐（左一）与毛泽东侄外孙女毛雅慧在毛主席纪念堂。（中）（中红网李学叶摄）');
INSERT INTO `jc_content_picture` VALUES ('513', '0', '/u/cms/www/201309/12105715powr.jpg', '北影开学第一天');
INSERT INTO `jc_content_picture` VALUES ('513', '1', '/u/cms/www/201309/12105715u0i2.jpg', '北影开学第一天');
INSERT INTO `jc_content_picture` VALUES ('513', '2', '/u/cms/www/201309/12105716f0ks.jpg', '北影开学第一天');
INSERT INTO `jc_content_picture` VALUES ('513', '3', '/u/cms/www/201309/12105716avso.jpg', '北影开学第一天');
INSERT INTO `jc_content_picture` VALUES ('513', '4', '/u/cms/www/201309/12105716u081.jpg', '北影开学第一天');
INSERT INTO `jc_content_picture` VALUES ('513', '5', '/u/cms/www/201309/12105717rgdv.jpg', '北影开学第一天');
INSERT INTO `jc_content_picture` VALUES ('514', '0', '/u/cms/www/201309/12110240hrzh.jpg', '北影表演系新生自曝K歌照');
INSERT INTO `jc_content_picture` VALUES ('514', '1', '/u/cms/www/201309/12110145r34m.jpg', '北影表演系新生K歌竖中指');
INSERT INTO `jc_content_picture` VALUES ('514', '2', '/u/cms/www/201309/121101457hlo.jpg', '北影表演系新生自曝K歌照');
INSERT INTO `jc_content_picture` VALUES ('514', '3', '/u/cms/www/201309/12110146fexh.jpg', '北影表演系新生自曝K歌照');
INSERT INTO `jc_content_picture` VALUES ('514', '4', '/u/cms/www/201309/12110146ynj3.jpg', '北影表演系新生自曝K歌照');
INSERT INTO `jc_content_picture` VALUES ('515', '0', '/u/cms/www/201309/12110726rqp4.jpg', '中戏2013级开学典礼');
INSERT INTO `jc_content_picture` VALUES ('515', '1', '/u/cms/www/201309/12110726kcxc.jpg', '中戏2013级开学典礼');
INSERT INTO `jc_content_picture` VALUES ('515', '2', '/u/cms/www/201309/1211072762wj.jpg', '中戏2013级开学典礼');
INSERT INTO `jc_content_picture` VALUES ('515', '3', '/u/cms/www/201309/121107270owq.jpg', '中戏2013级开学典礼');
INSERT INTO `jc_content_picture` VALUES ('515', '4', '/u/cms/www/201309/12110728cz8z.jpg', '中戏2013级开学典礼');
INSERT INTO `jc_content_picture` VALUES ('515', '5', '/u/cms/www/201309/12110728hvna.jpg', '中戏2013级开学典礼');
INSERT INTO `jc_content_picture` VALUES ('516', '0', '/u/cms/www/201309/12111106zad3.jpg', '1994年8月27日，三峡坝区三斗坪镇东岳庙村10组移民黎开英的儿子望军，以651分的好成绩考入清华大学汽车工程系，该村的乡亲们纷纷来到他家祝贺。摄影/周国强/东方IC');
INSERT INTO `jc_content_picture` VALUES ('516', '1', '/u/cms/www/201309/12111106zedy.jpg', '1977年冬天，中断了十年又重新恢复的高考制度，开始改变这个庞大国家无数人的命运。一纸试卷废除了“推荐上大学”，给当时渴望改变命运的人们一个公平竞争的机会。很多人借此叩开了另一个世界的大门，走上辉煌的人生道路。');
INSERT INTO `jc_content_picture` VALUES ('516', '2', '/u/cms/www/201309/12111106lobh.jpg', '1977年的招生对象为工人、农民、上山下乡和回乡知识青年、复原军人、干部和应届毕业生。');
INSERT INTO `jc_content_picture` VALUES ('516', '3', '/u/cms/www/201309/12111106jy1l.jpg', '1980年，开考前两名女生在交谈。她们的笑容给沉闷的考场带来一丝轻松的气息。摄影/任曙林');
INSERT INTO `jc_content_picture` VALUES ('516', '4', '/u/cms/www/201309/12111107ctqg.jpg', '1980年，高中教师于大卫在考场外鼓励学生。摄影/任曙林');
INSERT INTO `jc_content_picture` VALUES ('516', '5', '/u/cms/www/201309/12111107l4wg.jpg', '1986年，江苏省滨海县只有宋红斌一人通过艺术院校的复试，有参加高考的资格，因此考场里只有他一个人。这张准考证属于宋红斌。');
INSERT INTO `jc_content_picture` VALUES ('517', '0', '/u/cms/www/201309/12111518vk7v.jpg', '针对富人家的孩子，已经有一整套完善的产业链为他们服务，从高中的升学咨询指导到上文提到的大学管家式服务，而且获利不菲，例如帮助学生申请大学的升学指导服务的收费约为28995美元');
INSERT INTO `jc_content_picture` VALUES ('517', '1', '/u/cms/www/201309/12111518l3p5.jpg', '在美国，日趋上涨的大学花费已经严重威胁到了低收入家庭的学生，大量学生即使在毕业四五年后还身背学债。而另一方面，一种迎合富人家庭大学生的服务新模式已经出现。');
INSERT INTO `jc_content_picture` VALUES ('517', '2', '/u/cms/www/201309/12111518igwr.jpg', '216华尔街日报最近就报道了一家新成立的公司“波士顿大学顾问集团');
INSERT INTO `jc_content_picture` VALUES ('517', '3', '/u/cms/www/201309/12111518kvd2.jpg', '几个波士顿地区的学生接受采访时说，他们雇用“波士顿大学顾问集团”来帮他们完成任务，比如说买300瓶MerleNorman的香水并运到沙特阿拉伯，等待水管工，支付超速罚单等等');
INSERT INTO `jc_content_picture` VALUES ('517', '4', '/u/cms/www/201309/121115195muw.jpg', '“波士顿大学顾问集团”的创始人AJRich告诉赫芬顿邮报说，这种类型的管家式服务已经早就出现了');
INSERT INTO `jc_content_picture` VALUES ('517', '5', '/u/cms/www/201309/12111519tyc2.jpg', '他认为他的公司所做的是“赋予学生更多的权利”而不是把他们当婴儿一样纵容，他还说公司的服务事实上很划算：“我们的同行收费可是我们的三倍”。');
INSERT INTO `jc_content_picture` VALUES ('518', '0', '/u/cms/www/201309/12112124imh5.jpg', '9月7日晚，在武汉华中师范大学佑铭体育馆内，400余名学生家长集体打地铺过夜。楚林/东方IC');
INSERT INTO `jc_content_picture` VALUES ('518', '1', '/u/cms/www/201309/121121253f1z.jpg', '9月7日晚，在武汉华中师范大学佑铭体育馆内，400余名学生家长集体打地铺过夜。楚林/东方IC');
INSERT INTO `jc_content_picture` VALUES ('518', '2', '/u/cms/www/201309/121121265tk1.jpg', '9月7日晚，在武汉华中师范大学佑铭体育馆内，400余名学生家长集体打地铺过夜。楚林/东方IC');
INSERT INTO `jc_content_picture` VALUES ('518', '3', '/u/cms/www/201309/121121267h0z.jpg', '9月7日晚，在武汉华中师范大学佑铭体育馆内，400余名学生家长集体打地铺过夜。楚林/东方IC');
INSERT INTO `jc_content_picture` VALUES ('518', '4', '/u/cms/www/201309/12112127795n.jpg', '9月7日晚，在武汉华中师范大学佑铭体育馆内，400余名学生家长集体打地铺过夜。楚林/东方IC');
INSERT INTO `jc_content_picture` VALUES ('518', '5', '/u/cms/www/201309/12112128ltfy.jpg', '9月7日晚，在武汉华中师范大学佑铭体育馆内，400余名学生家长集体打地铺过夜。楚林/东方IC');
INSERT INTO `jc_content_picture` VALUES ('519', '0', '/u/cms/www/201309/12120435rmvm.jpg', '李冰冰');
INSERT INTO `jc_content_picture` VALUES ('519', '1', '/u/cms/www/201309/12120436on4w.jpg', '李冰冰');
INSERT INTO `jc_content_picture` VALUES ('519', '2', '/u/cms/www/201309/121204384v5d.jpg', '李冰冰');
INSERT INTO `jc_content_picture` VALUES ('519', '3', '/u/cms/www/201309/121204408a3o.jpg', '李冰冰');
INSERT INTO `jc_content_picture` VALUES ('519', '4', '/u/cms/www/201309/1212044200gm.jpg', '李冰冰');
INSERT INTO `jc_content_picture` VALUES ('520', '0', '/u/cms/www/201309/12134158qzrx.jpg', '刘德华');
INSERT INTO `jc_content_picture` VALUES ('520', '1', '/u/cms/www/201309/12134158q84h.jpg', '刘德华');
INSERT INTO `jc_content_picture` VALUES ('520', '2', '/u/cms/www/201309/12134159kbxa.jpg', '刘德华');
INSERT INTO `jc_content_picture` VALUES ('520', '3', '/u/cms/www/201309/12134159robe.jpg', '刘德华');
INSERT INTO `jc_content_picture` VALUES ('520', '4', '/u/cms/www/201309/121341597c2x.jpg', '刘德华');
INSERT INTO `jc_content_picture` VALUES ('520', '5', '/u/cms/www/201309/12134200cwyl.jpg', '刘德华');
INSERT INTO `jc_content_picture` VALUES ('521', '0', '/u/cms/www/201309/12134450mwvm.jpg', '全智贤');
INSERT INTO `jc_content_picture` VALUES ('521', '1', '/u/cms/www/201309/12134450lo6n.jpg', '全智贤');
INSERT INTO `jc_content_picture` VALUES ('521', '2', '/u/cms/www/201309/12134451j9p9.jpg', '全智贤');
INSERT INTO `jc_content_picture` VALUES ('521', '3', '/u/cms/www/201309/12134451k1zj.jpg', '全智贤');
INSERT INTO `jc_content_picture` VALUES ('521', '4', '/u/cms/www/201309/12134452b2g1.jpg', '全智贤');
INSERT INTO `jc_content_picture` VALUES ('521', '5', '/u/cms/www/201309/12134452bb5z.jpg', '全智贤');
INSERT INTO `jc_content_picture` VALUES ('522', '0', '/u/cms/www/201309/121348109k92.jpg', '王智');
INSERT INTO `jc_content_picture` VALUES ('522', '1', '/u/cms/www/201309/121348103ux5.jpg', '王智');
INSERT INTO `jc_content_picture` VALUES ('522', '2', '/u/cms/www/201309/12134811y9du.jpg', '王智');
INSERT INTO `jc_content_picture` VALUES ('522', '3', '/u/cms/www/201309/121348110tfi.jpg', '王智');
INSERT INTO `jc_content_picture` VALUES ('522', '4', '/u/cms/www/201309/12134812unit.jpg', '王智');
INSERT INTO `jc_content_picture` VALUES ('523', '0', '/u/cms/www/201309/12135148im9s.jpg', '宋祖英升任团长后首带队演出');
INSERT INTO `jc_content_picture` VALUES ('523', '1', '/u/cms/www/201309/12135149bhlg.jpg', '宋祖英升任团长后首带队演出');
INSERT INTO `jc_content_picture` VALUES ('523', '2', '/u/cms/www/201309/12135149s2wm.jpg', '宋祖英升任团长后首带队演出');
INSERT INTO `jc_content_picture` VALUES ('523', '3', '/u/cms/www/201309/12135149ar88.jpg', '宋祖英升任团长后首带队演出');
INSERT INTO `jc_content_picture` VALUES ('524', '0', '/u/cms/www/201309/12135606e58j.jpg', '杨幂');
INSERT INTO `jc_content_picture` VALUES ('524', '1', '/u/cms/www/201309/12135607vqhp.jpg', '杨幂');
INSERT INTO `jc_content_picture` VALUES ('524', '2', '/u/cms/www/201309/12135607ftvv.jpg', '杨幂');
INSERT INTO `jc_content_picture` VALUES ('524', '3', '/u/cms/www/201309/12135608l3ai.jpg', '杨幂');
INSERT INTO `jc_content_picture` VALUES ('524', '4', '/u/cms/www/201309/12135608ad8p.jpg', '杨幂');
INSERT INTO `jc_content_picture` VALUES ('524', '5', '/u/cms/www/201309/12135609wexl.jpg', '杨幂');
INSERT INTO `jc_content_picture` VALUES ('525', '0', '/u/cms/www/201309/12140434n4th.jpg', '');
INSERT INTO `jc_content_picture` VALUES ('525', '1', '/u/cms/www/201309/12140434ixtu.jpg', '');
INSERT INTO `jc_content_picture` VALUES ('525', '2', '/u/cms/www/201309/12140435relm.jpg', '');
INSERT INTO `jc_content_picture` VALUES ('525', '3', '/u/cms/www/201309/12140436rua8.jpg', '');
INSERT INTO `jc_content_picture` VALUES ('525', '4', '/u/cms/www/201309/12140436wwsc.jpg', '');
INSERT INTO `jc_content_picture` VALUES ('526', '0', '/u/cms/www/201309/12141311nuua.jpg', '张智霖与众空姐');
INSERT INTO `jc_content_picture` VALUES ('526', '1', '/u/cms/www/201309/12141311xxbj.jpg', '张智霖与众空姐');
INSERT INTO `jc_content_picture` VALUES ('526', '2', '/u/cms/www/201309/12141312d3p6.jpg', '张智霖与众空姐');
INSERT INTO `jc_content_picture` VALUES ('526', '3', '/u/cms/www/201309/12141312r6eb.jpg', '张智霖与众空姐');
INSERT INTO `jc_content_picture` VALUES ('526', '4', '/u/cms/www/201309/1214131399h0.jpg', '张智霖签新公司获直升机接送');
INSERT INTO `jc_content_picture` VALUES ('526', '5', '/u/cms/www/201309/12141313ndlq.jpg', '张智霖签新公司获直升机接送');
INSERT INTO `jc_content_picture` VALUES ('527', '0', '/u/cms/www/201309/12142118fe7p.jpg', '何晟铭出席敦煌保护活动');
INSERT INTO `jc_content_picture` VALUES ('527', '1', '/u/cms/www/201309/12142119m8y5.jpg', '何晟铭出席敦煌保护活动');
INSERT INTO `jc_content_picture` VALUES ('527', '2', '/u/cms/www/201309/121421197sla.jpg', '何晟铭演唱');
INSERT INTO `jc_content_picture` VALUES ('527', '3', '/u/cms/www/201309/12142120u2tx.jpg', '何晟铭演唱');
INSERT INTO `jc_content_picture` VALUES ('527', '4', '/u/cms/www/201309/12142120loqu.jpg', '何晟铭');
INSERT INTO `jc_content_picture` VALUES ('527', '5', '/u/cms/www/201309/1214212172bk.jpg', '何晟铭、韩栋');
INSERT INTO `jc_content_picture` VALUES ('528', '0', '/u/cms/www/201309/12142734jwmh.jpg', '宋佳');
INSERT INTO `jc_content_picture` VALUES ('528', '1', '/u/cms/www/201309/12142735jqmu.jpg', '宋佳');
INSERT INTO `jc_content_picture` VALUES ('528', '2', '/u/cms/www/201309/12142735rhib.jpg', '宋佳');
INSERT INTO `jc_content_picture` VALUES ('528', '3', '/u/cms/www/201309/1214273617d5.jpg', '宋佳');
INSERT INTO `jc_content_picture` VALUES ('528', '4', '/u/cms/www/201309/12142736ue6c.jpg', '宋佳');
INSERT INTO `jc_content_picture` VALUES ('528', '5', '/u/cms/www/201309/12142737cr9y.jpg', '宋佳');
INSERT INTO `jc_content_picture` VALUES ('529', '0', '/u/cms/www/201309/1214325480ue.jpg', '吴秀波');
INSERT INTO `jc_content_picture` VALUES ('529', '1', '/u/cms/www/201309/12143254y1wh.jpg', '吴秀波');
INSERT INTO `jc_content_picture` VALUES ('529', '2', '/u/cms/www/201309/12143255ukxu.jpg', '吴秀波');
INSERT INTO `jc_content_picture` VALUES ('529', '3', '/u/cms/www/201309/121432556ttu.jpg', '吴秀波');
INSERT INTO `jc_content_picture` VALUES ('529', '4', '/u/cms/www/201309/12143255ryzh.jpg', '吴秀波');
INSERT INTO `jc_content_picture` VALUES ('529', '5', '/u/cms/www/201309/12143256g1bo.jpg', '吴秀波');
INSERT INTO `jc_content_picture` VALUES ('530', '0', '/u/cms/www/201309/12145434qkiz.jpg', '曾黎');
INSERT INTO `jc_content_picture` VALUES ('530', '1', '/u/cms/www/201309/12145434959z.jpg', '曾黎');
INSERT INTO `jc_content_picture` VALUES ('530', '2', '/u/cms/www/201309/121454345gdz.jpg', '曾黎');
INSERT INTO `jc_content_picture` VALUES ('530', '3', '/u/cms/www/201309/12145435lmi6.jpg', '曾黎');
INSERT INTO `jc_content_picture` VALUES ('530', '4', '/u/cms/www/201309/12145435u9c1.jpg', '曾黎');
INSERT INTO `jc_content_picture` VALUES ('531', '0', '/u/cms/www/201309/12150556da16.jpg', '关之琳');
INSERT INTO `jc_content_picture` VALUES ('531', '1', '/u/cms/www/201309/12150556inf7.jpg', '关之琳');
INSERT INTO `jc_content_picture` VALUES ('531', '2', '/u/cms/www/201309/12150557etcp.jpg', '关之琳');
INSERT INTO `jc_content_picture` VALUES ('531', '3', '/u/cms/www/201309/12150557dzrz.jpg', '关之琳');
INSERT INTO `jc_content_picture` VALUES ('532', '0', '/u/cms/www/201309/12150936264i.jpg', '《中国合伙人》剧组');
INSERT INTO `jc_content_picture` VALUES ('532', '1', '/u/cms/www/201309/121509368460.jpg', '黄晓明章子怡佟大为');
INSERT INTO `jc_content_picture` VALUES ('532', '2', '/u/cms/www/201309/12150936cnpa.jpg', '《中国合伙人》剧组');
INSERT INTO `jc_content_picture` VALUES ('532', '3', '/u/cms/www/201309/12150937xe3g.jpg', '佟大为黄晓明邓超');
INSERT INTO `jc_content_picture` VALUES ('532', '4', '/u/cms/www/201309/121509371kyv.jpg', '《中国合伙人》剧组');
INSERT INTO `jc_content_picture` VALUES ('533', '0', '/u/cms/www/201309/12152115n7ee.jpg', '景甜亮相红毯');
INSERT INTO `jc_content_picture` VALUES ('533', '1', '/u/cms/www/201309/12152115dymp.jpg', '景甜与范冰冰在台下');
INSERT INTO `jc_content_picture` VALUES ('533', '2', '/u/cms/www/201309/1215211539ic.jpg', '景甜与范冰冰在台下');
INSERT INTO `jc_content_picture` VALUES ('533', '3', '/u/cms/www/201309/12152115oqbj.jpg', '景甜与范冰冰在台下');
INSERT INTO `jc_content_picture` VALUES ('533', '4', '/u/cms/www/201309/12152116am9n.jpg', '景甜作为颁奖嘉宾登台');
INSERT INTO `jc_content_picture` VALUES ('534', '0', '/u/cms/www/201309/12153550461x.jpg', '小宋佳时尚大片');
INSERT INTO `jc_content_picture` VALUES ('534', '1', '/u/cms/www/201309/12153550ryso.jpg', '小宋佳时尚大片');
INSERT INTO `jc_content_picture` VALUES ('534', '2', '/u/cms/www/201309/12153551r5kz.jpg', '小宋佳时尚大片');
INSERT INTO `jc_content_picture` VALUES ('534', '3', '/u/cms/www/201309/12153551igy5.jpg', '小宋佳时尚大片');
INSERT INTO `jc_content_picture` VALUES ('534', '4', '/u/cms/www/201309/12153551fflz.jpg', '小宋佳时尚大片');
INSERT INTO `jc_content_picture` VALUES ('534', '5', '/u/cms/www/201309/121535526foq.jpg', '小宋佳时尚大片');
INSERT INTO `jc_content_picture` VALUES ('535', '0', '/u/cms/www/201309/121542148jkc.jpg', '熊乃瑾清新写真');
INSERT INTO `jc_content_picture` VALUES ('535', '1', '/u/cms/www/201309/121542159xil.jpg', '熊乃瑾清新写真');
INSERT INTO `jc_content_picture` VALUES ('535', '2', '/u/cms/www/201309/12154215ryyk.jpg', '熊乃瑾清新写真');
INSERT INTO `jc_content_picture` VALUES ('535', '3', '/u/cms/www/201309/12154215y02n.jpg', '熊乃瑾清新写真');
INSERT INTO `jc_content_picture` VALUES ('535', '4', '/u/cms/www/201309/121542158t1n.jpg', '熊乃瑾清新写真');
INSERT INTO `jc_content_picture` VALUES ('535', '5', '/u/cms/www/201309/12154216egtt.jpg', '熊乃瑾清新写真');
INSERT INTO `jc_content_picture` VALUES ('536', '0', '/u/cms/www/201309/12154708we6w.jpg', '章子怡');
INSERT INTO `jc_content_picture` VALUES ('536', '1', '/u/cms/www/201309/12154708kkn6.jpg', '章子怡');
INSERT INTO `jc_content_picture` VALUES ('536', '2', '/u/cms/www/201309/12154708nsye.jpg', '章子怡');
INSERT INTO `jc_content_picture` VALUES ('536', '3', '/u/cms/www/201309/12154709iod3.jpg', '章子怡');
INSERT INTO `jc_content_picture` VALUES ('536', '4', '/u/cms/www/201309/12154709ypkl.jpg', '章子怡');

-- ----------------------------
-- Table structure for `jc_content_tag`
-- ----------------------------
DROP TABLE IF EXISTS `jc_content_tag`;
CREATE TABLE `jc_content_tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(50) NOT NULL COMMENT 'tag名称',
  `ref_counter` int(11) NOT NULL DEFAULT '1' COMMENT '被引用的次数',
  PRIMARY KEY (`tag_id`),
  UNIQUE KEY `ak_tag_name` (`tag_name`)
) ENGINE=InnoDB AUTO_INCREMENT=484 DEFAULT CHARSET=utf8 COMMENT='CMS内容TAG表';

-- ----------------------------
-- Records of jc_content_tag
-- ----------------------------
INSERT INTO `jc_content_tag` VALUES ('1', '2011', '1');
INSERT INTO `jc_content_tag` VALUES ('2', '中国', '3');
INSERT INTO `jc_content_tag` VALUES ('9', '机构', '0');
INSERT INTO `jc_content_tag` VALUES ('16', '基金', '0');
INSERT INTO `jc_content_tag` VALUES ('52', '调控', '0');
INSERT INTO `jc_content_tag` VALUES ('54', '受贿', '1');
INSERT INTO `jc_content_tag` VALUES ('55', '测试', '2');
INSERT INTO `jc_content_tag` VALUES ('68', '项目', '1');
INSERT INTO `jc_content_tag` VALUES ('73', '推拿', '8');
INSERT INTO `jc_content_tag` VALUES ('74', '第一集', '2');
INSERT INTO `jc_content_tag` VALUES ('75', '第二集', '2');
INSERT INTO `jc_content_tag` VALUES ('76', '第三集', '2');
INSERT INTO `jc_content_tag` VALUES ('77', '第四集', '2');
INSERT INTO `jc_content_tag` VALUES ('78', '第五集', '1');
INSERT INTO `jc_content_tag` VALUES ('79', '第六集', '1');
INSERT INTO `jc_content_tag` VALUES ('80', '第七集', '1');
INSERT INTO `jc_content_tag` VALUES ('81', '第八集', '1');
INSERT INTO `jc_content_tag` VALUES ('82', '51区', '1');
INSERT INTO `jc_content_tag` VALUES ('83', '美国', '2');
INSERT INTO `jc_content_tag` VALUES ('84', '最', '1');
INSERT INTO `jc_content_tag` VALUES ('85', '神秘', '1');
INSERT INTO `jc_content_tag` VALUES ('86', '军事基地', '1');
INSERT INTO `jc_content_tag` VALUES ('87', '斯大林', '1');
INSERT INTO `jc_content_tag` VALUES ('88', '格勒', '1');
INSERT INTO `jc_content_tag` VALUES ('89', '大', '1');
INSERT INTO `jc_content_tag` VALUES ('90', '血战', '1');
INSERT INTO `jc_content_tag` VALUES ('91', '70周', '1');
INSERT INTO `jc_content_tag` VALUES ('92', '年祭', '1');
INSERT INTO `jc_content_tag` VALUES ('93', '纽约', '3');
INSERT INTO `jc_content_tag` VALUES ('94', '男子', '5');
INSERT INTO `jc_content_tag` VALUES ('95', '刺青', '1');
INSERT INTO `jc_content_tag` VALUES ('96', '摩托', '1');
INSERT INTO `jc_content_tag` VALUES ('97', '救援', '1');
INSERT INTO `jc_content_tag` VALUES ('98', '镖', '1');
INSERT INTO `jc_content_tag` VALUES ('99', '行天下', '1');
INSERT INTO `jc_content_tag` VALUES ('100', '揭秘', '1');
INSERT INTO `jc_content_tag` VALUES ('101', '镖局', '1');
INSERT INTO `jc_content_tag` VALUES ('102', '两名', '1');
INSERT INTO `jc_content_tag` VALUES ('103', '少年', '1');
INSERT INTO `jc_content_tag` VALUES ('104', '为', '1');
INSERT INTO `jc_content_tag` VALUES ('105', '挣钱', '1');
INSERT INTO `jc_content_tag` VALUES ('106', '多个', '1');
INSERT INTO `jc_content_tag` VALUES ('107', '省市', '1');
INSERT INTO `jc_content_tag` VALUES ('108', '抢劫', '2');
INSERT INTO `jc_content_tag` VALUES ('109', '杀死', '1');
INSERT INTO `jc_content_tag` VALUES ('110', '7人', '1');
INSERT INTO `jc_content_tag` VALUES ('122', '女', '1');
INSERT INTO `jc_content_tag` VALUES ('134', '海报', '1');
INSERT INTO `jc_content_tag` VALUES ('157', '刘晓庆', '1');
INSERT INTO `jc_content_tag` VALUES ('158', '大婚', '1');
INSERT INTO `jc_content_tag` VALUES ('159', '老公', '2');
INSERT INTO `jc_content_tag` VALUES ('160', '将门之后', '1');
INSERT INTO `jc_content_tag` VALUES ('161', '政协委员', '1');
INSERT INTO `jc_content_tag` VALUES ('162', '赵本山', '1');
INSERT INTO `jc_content_tag` VALUES ('163', '娱乐圈', '1');
INSERT INTO `jc_content_tag` VALUES ('164', '潘长江', '1');
INSERT INTO `jc_content_tag` VALUES ('165', '比拼', '1');
INSERT INTO `jc_content_tag` VALUES ('166', '全民目击', '1');
INSERT INTO `jc_content_tag` VALUES ('167', '预告', '1');
INSERT INTO `jc_content_tag` VALUES ('168', '孙红雷', '1');
INSERT INTO `jc_content_tag` VALUES ('169', '郭富城', '1');
INSERT INTO `jc_content_tag` VALUES ('170', '上演', '1');
INSERT INTO `jc_content_tag` VALUES ('171', '游戏', '1');
INSERT INTO `jc_content_tag` VALUES ('172', '冯小刚', '1');
INSERT INTO `jc_content_tag` VALUES ('173', '春晚', '1');
INSERT INTO `jc_content_tag` VALUES ('174', '相声小品', '1');
INSERT INTO `jc_content_tag` VALUES ('175', '唱赞歌', '1');
INSERT INTO `jc_content_tag` VALUES ('179', '时代', '1');
INSERT INTO `jc_content_tag` VALUES ('180', '亟须', '1');
INSERT INTO `jc_content_tag` VALUES ('181', '透明', '1');
INSERT INTO `jc_content_tag` VALUES ('182', '税制', '1');
INSERT INTO `jc_content_tag` VALUES ('183', '朱为群', '1');
INSERT INTO `jc_content_tag` VALUES ('184', '大数据', '1');
INSERT INTO `jc_content_tag` VALUES ('185', '宋石男', '1');
INSERT INTO `jc_content_tag` VALUES ('186', '谣言', '1');
INSERT INTO `jc_content_tag` VALUES ('187', '世界上', '1');
INSERT INTO `jc_content_tag` VALUES ('188', '最古老', '1');
INSERT INTO `jc_content_tag` VALUES ('189', '传媒', '1');
INSERT INTO `jc_content_tag` VALUES ('190', '俞天任', '1');
INSERT INTO `jc_content_tag` VALUES ('191', '麦克阿瑟', '1');
INSERT INTO `jc_content_tag` VALUES ('192', '日本', '1');
INSERT INTO `jc_content_tag` VALUES ('193', '记者', '1');
INSERT INTO `jc_content_tag` VALUES ('194', '官员', '1');
INSERT INTO `jc_content_tag` VALUES ('195', '艳照', '1');
INSERT INTO `jc_content_tag` VALUES ('196', '敲诈信', '1');
INSERT INTO `jc_content_tag` VALUES ('197', '快递员', '1');
INSERT INTO `jc_content_tag` VALUES ('198', '识破', '1');
INSERT INTO `jc_content_tag` VALUES ('199', '新娘', '1');
INSERT INTO `jc_content_tag` VALUES ('200', '婚宴', '1');
INSERT INTO `jc_content_tag` VALUES ('201', '逃婚', '1');
INSERT INTO `jc_content_tag` VALUES ('202', '新郎', '1');
INSERT INTO `jc_content_tag` VALUES ('203', '起诉', '1');
INSERT INTO `jc_content_tag` VALUES ('204', '索要', '1');
INSERT INTO `jc_content_tag` VALUES ('205', '精神损失费', '1');
INSERT INTO `jc_content_tag` VALUES ('206', '醉酒', '1');
INSERT INTO `jc_content_tag` VALUES ('207', '强奸', '1');
INSERT INTO `jc_content_tag` VALUES ('208', '孕妇', '1');
INSERT INTO `jc_content_tag` VALUES ('209', '未遂', '1');
INSERT INTO `jc_content_tag` VALUES ('210', '持刀', '1');
INSERT INTO `jc_content_tag` VALUES ('211', '返回', '1');
INSERT INTO `jc_content_tag` VALUES ('212', '手机', '1');
INSERT INTO `jc_content_tag` VALUES ('213', '伤人', '1');
INSERT INTO `jc_content_tag` VALUES ('214', '欠债', '1');
INSERT INTO `jc_content_tag` VALUES ('215', '万元', '2');
INSERT INTO `jc_content_tag` VALUES ('216', '没钱', '1');
INSERT INTO `jc_content_tag` VALUES ('217', '自导', '1');
INSERT INTO `jc_content_tag` VALUES ('218', '绑架案', '1');
INSERT INTO `jc_content_tag` VALUES ('219', '老婆', '1');
INSERT INTO `jc_content_tag` VALUES ('220', '赎金', '1');
INSERT INTO `jc_content_tag` VALUES ('221', '乘客', '1');
INSERT INTO `jc_content_tag` VALUES ('222', '航班', '2');
INSERT INTO `jc_content_tag` VALUES ('223', '延误', '2');
INSERT INTO `jc_content_tag` VALUES ('224', '殴打', '2');
INSERT INTO `jc_content_tag` VALUES ('225', '地勤人员', '1');
INSERT INTO `jc_content_tag` VALUES ('226', '拘留', '1');
INSERT INTO `jc_content_tag` VALUES ('227', '城管局', '1');
INSERT INTO `jc_content_tag` VALUES ('228', '司机', '2');
INSERT INTO `jc_content_tag` VALUES ('229', '袒护', '1');
INSERT INTO `jc_content_tag` VALUES ('230', '无证', '1');
INSERT INTO `jc_content_tag` VALUES ('231', '幼儿园', '1');
INSERT INTO `jc_content_tag` VALUES ('232', '踢飞', '1');
INSERT INTO `jc_content_tag` VALUES ('233', '凳子', '1');
INSERT INTO `jc_content_tag` VALUES ('234', '阻挠', '1');
INSERT INTO `jc_content_tag` VALUES ('235', '采访', '1');
INSERT INTO `jc_content_tag` VALUES ('236', '伪造', '1');
INSERT INTO `jc_content_tag` VALUES ('237', '国字头', '1');
INSERT INTO `jc_content_tag` VALUES ('238', '研究院', '1');
INSERT INTO `jc_content_tag` VALUES ('239', '承接', '1');
INSERT INTO `jc_content_tag` VALUES ('240', '水电', '1');
INSERT INTO `jc_content_tag` VALUES ('241', '通过', '1');
INSERT INTO `jc_content_tag` VALUES ('242', '政府', '1');
INSERT INTO `jc_content_tag` VALUES ('243', '审查', '1');
INSERT INTO `jc_content_tag` VALUES ('244', '非法拘禁', '1');
INSERT INTO `jc_content_tag` VALUES ('245', '妻子', '1');
INSERT INTO `jc_content_tag` VALUES ('246', '情夫', '1');
INSERT INTO `jc_content_tag` VALUES ('247', '侮辱', '1');
INSERT INTO `jc_content_tag` VALUES ('248', '穿拖鞋', '1');
INSERT INTO `jc_content_tag` VALUES ('249', '开车', '1');
INSERT INTO `jc_content_tag` VALUES ('250', '低头', '1');
INSERT INTO `jc_content_tag` VALUES ('251', '点烟', '1');
INSERT INTO `jc_content_tag` VALUES ('252', '追尾', '1');
INSERT INTO `jc_content_tag` VALUES ('253', '造成', '1');
INSERT INTO `jc_content_tag` VALUES ('254', '死亡', '1');
INSERT INTO `jc_content_tag` VALUES ('255', '骗子', '1');
INSERT INTO `jc_content_tag` VALUES ('256', '假冒', '1');
INSERT INTO `jc_content_tag` VALUES ('257', '导演', '2');
INSERT INTO `jc_content_tag` VALUES ('258', '谎称', '1');
INSERT INTO `jc_content_tag` VALUES ('259', '安排', '1');
INSERT INTO `jc_content_tag` VALUES ('260', '演员', '1');
INSERT INTO `jc_content_tag` VALUES ('261', '诈骗', '1');
INSERT INTO `jc_content_tag` VALUES ('262', '缓刑', '1');
INSERT INTO `jc_content_tag` VALUES ('263', '嫖娼', '1');
INSERT INTO `jc_content_tag` VALUES ('264', '后用', '1');
INSERT INTO `jc_content_tag` VALUES ('265', '迷药', '1');
INSERT INTO `jc_content_tag` VALUES ('266', '卖淫女', '1');
INSERT INTO `jc_content_tag` VALUES ('267', '内地', '1');
INSERT INTO `jc_content_tag` VALUES ('268', '夫妇', '1');
INSERT INTO `jc_content_tag` VALUES ('269', '香港机场', '1');
INSERT INTO `jc_content_tag` VALUES ('270', '袭警', '1');
INSERT INTO `jc_content_tag` VALUES ('271', '拘捕', '1');
INSERT INTO `jc_content_tag` VALUES ('272', '报社', '1');
INSERT INTO `jc_content_tag` VALUES ('273', '副主任', '1');
INSERT INTO `jc_content_tag` VALUES ('274', '被判', '1');
INSERT INTO `jc_content_tag` VALUES ('275', '辩称', '1');
INSERT INTO `jc_content_tag` VALUES ('276', '公款', '1');
INSERT INTO `jc_content_tag` VALUES ('277', '上诉', '1');
INSERT INTO `jc_content_tag` VALUES ('278', '僧人', '1');
INSERT INTO `jc_content_tag` VALUES ('279', '仇家', '1');
INSERT INTO `jc_content_tag` VALUES ('280', '路遇', '1');
INSERT INTO `jc_content_tag` VALUES ('281', '好友', '1');
INSERT INTO `jc_content_tag` VALUES ('282', '以为', '1');
INSERT INTO `jc_content_tag` VALUES ('283', '报信', '1');
INSERT INTO `jc_content_tag` VALUES ('284', '砍死', '1');
INSERT INTO `jc_content_tag` VALUES ('285', '小爸爸', '4');
INSERT INTO `jc_content_tag` VALUES ('286', '云南', '1');
INSERT INTO `jc_content_tag` VALUES ('287', '山洪', '1');
INSERT INTO `jc_content_tag` VALUES ('288', '冲断路桥', '1');
INSERT INTO `jc_content_tag` VALUES ('289', '遇难', '1');
INSERT INTO `jc_content_tag` VALUES ('290', '失踪', '1');
INSERT INTO `jc_content_tag` VALUES ('291', '广州', '1');
INSERT INTO `jc_content_tag` VALUES ('292', '楼歪歪', '1');
INSERT INTO `jc_content_tag` VALUES ('293', '居民楼', '1');
INSERT INTO `jc_content_tag` VALUES ('294', '倾斜', '1');
INSERT INTO `jc_content_tag` VALUES ('295', '海口', '1');
INSERT INTO `jc_content_tag` VALUES ('296', '酒行', '1');
INSERT INTO `jc_content_tag` VALUES ('297', '发生爆炸', '1');
INSERT INTO `jc_content_tag` VALUES ('298', '受伤', '1');
INSERT INTO `jc_content_tag` VALUES ('299', '玻璃', '1');
INSERT INTO `jc_content_tag` VALUES ('300', '炸飞', '1');
INSERT INTO `jc_content_tag` VALUES ('301', '朱镕基', '1');
INSERT INTO `jc_content_tag` VALUES ('302', '新书', '1');
INSERT INTO `jc_content_tag` VALUES ('303', '中央领导', '1');
INSERT INTO `jc_content_tag` VALUES ('304', '征求意见', '1');
INSERT INTO `jc_content_tag` VALUES ('305', '官方', '1');
INSERT INTO `jc_content_tag` VALUES ('306', '翻越', '1');
INSERT INTO `jc_content_tag` VALUES ('307', '澳门大学', '1');
INSERT INTO `jc_content_tag` VALUES ('308', '新校区', '1');
INSERT INTO `jc_content_tag` VALUES ('309', '围墙', '1');
INSERT INTO `jc_content_tag` VALUES ('310', '偷渡', '1');
INSERT INTO `jc_content_tag` VALUES ('311', '毛泽东', '1');
INSERT INTO `jc_content_tag` VALUES ('312', '逝世', '1');
INSERT INTO `jc_content_tag` VALUES ('313', '37周年', '1');
INSERT INTO `jc_content_tag` VALUES ('314', '亲属', '1');
INSERT INTO `jc_content_tag` VALUES ('315', '工作人员', '1');
INSERT INTO `jc_content_tag` VALUES ('316', '现身', '2');
INSERT INTO `jc_content_tag` VALUES ('317', '纪念', '1');
INSERT INTO `jc_content_tag` VALUES ('318', '房价', '1');
INSERT INTO `jc_content_tag` VALUES ('319', '上涨', '1');
INSERT INTO `jc_content_tag` VALUES ('320', '最快', '1');
INSERT INTO `jc_content_tag` VALUES ('321', '城市', '1');
INSERT INTO `jc_content_tag` VALUES ('322', '排行', '1');
INSERT INTO `jc_content_tag` VALUES ('323', '去', '1');
INSERT INTO `jc_content_tag` VALUES ('324', '那', '1');
INSERT INTO `jc_content_tag` VALUES ('325', '买房', '1');
INSERT INTO `jc_content_tag` VALUES ('326', '你', '1');
INSERT INTO `jc_content_tag` VALUES ('327', '赚', '1');
INSERT INTO `jc_content_tag` VALUES ('328', '翻', '1');
INSERT INTO `jc_content_tag` VALUES ('329', '何润东李沁谱写璀璨人生', '1');
INSERT INTO `jc_content_tag` VALUES ('330', '错', '1');
INSERT INTO `jc_content_tag` VALUES ('331', '伏', '1');
INSERT INTO `jc_content_tag` VALUES ('332', '今晚', '1');
INSERT INTO `jc_content_tag` VALUES ('333', '大结局', '1');
INSERT INTO `jc_content_tag` VALUES ('334', '春天', '1');
INSERT INTO `jc_content_tag` VALUES ('335', '播出', '1');
INSERT INTO `jc_content_tag` VALUES ('336', '过半', '1');
INSERT INTO `jc_content_tag` VALUES ('337', '抹布', '1');
INSERT INTO `jc_content_tag` VALUES ('338', '收视', '1');
INSERT INTO `jc_content_tag` VALUES ('339', '攀升', '1');
INSERT INTO `jc_content_tag` VALUES ('340', '海', '1');
INSERT INTO `jc_content_tag` VALUES ('341', '清', '1');
INSERT INTO `jc_content_tag` VALUES ('342', '张', '1');
INSERT INTO `jc_content_tag` VALUES ('343', '译', '1');
INSERT INTO `jc_content_tag` VALUES ('344', '激情', '1');
INSERT INTO `jc_content_tag` VALUES ('345', '床', '1');
INSERT INTO `jc_content_tag` VALUES ('346', '照曝光', '1');
INSERT INTO `jc_content_tag` VALUES ('347', '大明劫', '1');
INSERT INTO `jc_content_tag` VALUES ('348', '冯远征', '1');
INSERT INTO `jc_content_tag` VALUES ('349', '戴立', '1');
INSERT INTO `jc_content_tag` VALUES ('350', '末日战', '1');
INSERT INTO `jc_content_tag` VALUES ('351', '金刚王', '1');
INSERT INTO `jc_content_tag` VALUES ('352', '解密', '2');
INSERT INTO `jc_content_tag` VALUES ('353', '功夫', '1');
INSERT INTO `jc_content_tag` VALUES ('354', '秘籍', '1');
INSERT INTO `jc_content_tag` VALUES ('355', '蛊术', '1');
INSERT INTO `jc_content_tag` VALUES ('356', '狄仁杰', '1');
INSERT INTO `jc_content_tag` VALUES ('357', '登陆', '1');
INSERT INTO `jc_content_tag` VALUES ('358', '大银幕', '1');
INSERT INTO `jc_content_tag` VALUES ('359', '金马', '1');
INSERT INTO `jc_content_tag` VALUES ('360', '影展', '1');
INSERT INTO `jc_content_tag` VALUES ('361', '公布', '1');
INSERT INTO `jc_content_tag` VALUES ('362', '焦点', '1');
INSERT INTO `jc_content_tag` VALUES ('363', '北影', '2');
INSERT INTO `jc_content_tag` VALUES ('364', '开学典礼', '2');
INSERT INTO `jc_content_tag` VALUES ('365', '新生', '4');
INSERT INTO `jc_content_tag` VALUES ('366', '红衣', '1');
INSERT INTO `jc_content_tag` VALUES ('367', '抢镜', '1');
INSERT INTO `jc_content_tag` VALUES ('368', '表演系', '1');
INSERT INTO `jc_content_tag` VALUES ('369', '中戏', '1');
INSERT INTO `jc_content_tag` VALUES ('370', '曝光', '2');
INSERT INTO `jc_content_tag` VALUES ('371', '高考', '1');
INSERT INTO `jc_content_tag` VALUES ('372', '36年', '1');
INSERT INTO `jc_content_tag` VALUES ('373', '变迁', '1');
INSERT INTO `jc_content_tag` VALUES ('374', '珍贵', '1');
INSERT INTO `jc_content_tag` VALUES ('375', '影像', '1');
INSERT INTO `jc_content_tag` VALUES ('376', '富二代', '1');
INSERT INTO `jc_content_tag` VALUES ('377', '大学生', '1');
INSERT INTO `jc_content_tag` VALUES ('378', '奢侈', '1');
INSERT INTO `jc_content_tag` VALUES ('379', '生活', '1');
INSERT INTO `jc_content_tag` VALUES ('380', '华中师范大学', '1');
INSERT INTO `jc_content_tag` VALUES ('381', '家长', '1');
INSERT INTO `jc_content_tag` VALUES ('382', '打地铺', '1');
INSERT INTO `jc_content_tag` VALUES ('383', '过夜', '1');
INSERT INTO `jc_content_tag` VALUES ('384', '李冰冰', '1');
INSERT INTO `jc_content_tag` VALUES ('385', '时尚', '3');
INSERT INTO `jc_content_tag` VALUES ('386', '大片', '4');
INSERT INTO `jc_content_tag` VALUES ('387', '刘德华', '1');
INSERT INTO `jc_content_tag` VALUES ('388', '上海', '1');
INSERT INTO `jc_content_tag` VALUES ('389', '舞伴', '1');
INSERT INTO `jc_content_tag` VALUES ('390', '全智贤', '1');
INSERT INTO `jc_content_tag` VALUES ('391', '衬衫', '1');
INSERT INTO `jc_content_tag` VALUES ('392', '长腿', '1');
INSERT INTO `jc_content_tag` VALUES ('393', '王智', '1');
INSERT INTO `jc_content_tag` VALUES ('394', '写真', '2');
INSERT INTO `jc_content_tag` VALUES ('395', '演绎', '2');
INSERT INTO `jc_content_tag` VALUES ('396', '复古', '2');
INSERT INTO `jc_content_tag` VALUES ('397', '风情', '1');
INSERT INTO `jc_content_tag` VALUES ('398', '宋祖英', '1');
INSERT INTO `jc_content_tag` VALUES ('399', '团长', '1');
INSERT INTO `jc_content_tag` VALUES ('400', '带队', '1');
INSERT INTO `jc_content_tag` VALUES ('401', '演出', '1');
INSERT INTO `jc_content_tag` VALUES ('402', '杨幂', '1');
INSERT INTO `jc_content_tag` VALUES ('403', '亮相', '3');
INSERT INTO `jc_content_tag` VALUES ('404', '时装周', '2');
INSERT INTO `jc_content_tag` VALUES ('405', '王丽坤', '1');
INSERT INTO `jc_content_tag` VALUES ('406', '纯美', '1');
INSERT INTO `jc_content_tag` VALUES ('407', '张智霖', '1');
INSERT INTO `jc_content_tag` VALUES ('408', '新公司', '1');
INSERT INTO `jc_content_tag` VALUES ('409', '直升机', '1');
INSERT INTO `jc_content_tag` VALUES ('410', '接送', '1');
INSERT INTO `jc_content_tag` VALUES ('411', '空姐', '1');
INSERT INTO `jc_content_tag` VALUES ('412', '护驾', '1');
INSERT INTO `jc_content_tag` VALUES ('413', '何晟铭', '1');
INSERT INTO `jc_content_tag` VALUES ('414', '出席', '1');
INSERT INTO `jc_content_tag` VALUES ('415', '敦煌', '1');
INSERT INTO `jc_content_tag` VALUES ('416', '保护', '1');
INSERT INTO `jc_content_tag` VALUES ('417', '活动', '1');
INSERT INTO `jc_content_tag` VALUES ('418', '盗墓笔记', '1');
INSERT INTO `jc_content_tag` VALUES ('419', '宋佳', '1');
INSERT INTO `jc_content_tag` VALUES ('420', '封面', '1');
INSERT INTO `jc_content_tag` VALUES ('421', '恬淡', '1');
INSERT INTO `jc_content_tag` VALUES ('422', '清新', '2');
INSERT INTO `jc_content_tag` VALUES ('423', '早秋', '1');
INSERT INTO `jc_content_tag` VALUES ('424', '吴秀波', '1');
INSERT INTO `jc_content_tag` VALUES ('425', '尽显', '1');
INSERT INTO `jc_content_tag` VALUES ('426', '迷人', '1');
INSERT INTO `jc_content_tag` VALUES ('427', '绅士', '1');
INSERT INTO `jc_content_tag` VALUES ('428', '曾黎', '1');
INSERT INTO `jc_content_tag` VALUES ('429', '造型', '1');
INSERT INTO `jc_content_tag` VALUES ('430', '红唇', '1');
INSERT INTO `jc_content_tag` VALUES ('431', '亮点', '1');
INSERT INTO `jc_content_tag` VALUES ('432', '关之琳', '1');
INSERT INTO `jc_content_tag` VALUES ('433', '超短裙', '1');
INSERT INTO `jc_content_tag` VALUES ('434', '庆生', '1');
INSERT INTO `jc_content_tag` VALUES ('435', '黄晓明', '1');
INSERT INTO `jc_content_tag` VALUES ('436', '俄罗斯', '1');
INSERT INTO `jc_content_tag` VALUES ('437', '电影节', '1');
INSERT INTO `jc_content_tag` VALUES ('438', '景甜', '1');
INSERT INTO `jc_content_tag` VALUES ('439', '范冰冰', '1');
INSERT INTO `jc_content_tag` VALUES ('440', '相谈甚欢', '1');
INSERT INTO `jc_content_tag` VALUES ('441', '小宋佳', '1');
INSERT INTO `jc_content_tag` VALUES ('442', '熊乃瑾', '1');
INSERT INTO `jc_content_tag` VALUES ('443', '章子怡', '1');
INSERT INTO `jc_content_tag` VALUES ('444', '公益', '1');
INSERT INTO `jc_content_tag` VALUES ('445', '节目', '1');
INSERT INTO `jc_content_tag` VALUES ('446', '讲述', '1');
INSERT INTO `jc_content_tag` VALUES ('447', '梦想', '1');
INSERT INTO `jc_content_tag` VALUES ('448', '合伙人', '1');
INSERT INTO `jc_content_tag` VALUES ('449', '人在囧途', '1');
INSERT INTO `jc_content_tag` VALUES ('450', '泰囧', '1');
INSERT INTO `jc_content_tag` VALUES ('451', '中苏', '1');
INSERT INTO `jc_content_tag` VALUES ('452', '外交', '1');
INSERT INTO `jc_content_tag` VALUES ('453', '档案', '1');
INSERT INTO `jc_content_tag` VALUES ('454', '兵戎相见', '1');
INSERT INTO `jc_content_tag` VALUES ('455', '战后', '1');
INSERT INTO `jc_content_tag` VALUES ('456', '伊拉克', '1');
INSERT INTO `jc_content_tag` VALUES ('457', '现在怎么样', '1');
INSERT INTO `jc_content_tag` VALUES ('458', '女博士', '1');
INSERT INTO `jc_content_tag` VALUES ('459', '于娟', '1');
INSERT INTO `jc_content_tag` VALUES ('460', '抗癌', '1');
INSERT INTO `jc_content_tag` VALUES ('461', '日记', '1');
INSERT INTO `jc_content_tag` VALUES ('462', '十二生肖', '1');
INSERT INTO `jc_content_tag` VALUES ('463', '南京', '1');
INSERT INTO `jc_content_tag` VALUES ('464', '新少林寺', '1');
INSERT INTO `jc_content_tag` VALUES ('465', '画壁', '1');
INSERT INTO `jc_content_tag` VALUES ('466', '宝贝', '1');
INSERT INTO `jc_content_tag` VALUES ('467', '计划', '1');
INSERT INTO `jc_content_tag` VALUES ('468', '非诚勿扰', '1');
INSERT INTO `jc_content_tag` VALUES ('469', '新闻', '1');
INSERT INTO `jc_content_tag` VALUES ('471', '老人', '1');
INSERT INTO `jc_content_tag` VALUES ('472', '公交', '1');
INSERT INTO `jc_content_tag` VALUES ('473', '车上', '1');
INSERT INTO `jc_content_tag` VALUES ('474', '发病', '1');
INSERT INTO `jc_content_tag` VALUES ('475', '抢救无效', '1');
INSERT INTO `jc_content_tag` VALUES ('476', '去世', '1');
INSERT INTO `jc_content_tag` VALUES ('477', '家属', '1');
INSERT INTO `jc_content_tag` VALUES ('478', '索赔', '1');
INSERT INTO `jc_content_tag` VALUES ('479', '31', '1');
INSERT INTO `jc_content_tag` VALUES ('480', '赵', '1');
INSERT INTO `jc_content_tag` VALUES ('481', '廷', '1');
INSERT INTO `jc_content_tag` VALUES ('482', '高圆圆', '1');
INSERT INTO `jc_content_tag` VALUES ('483', '直播', '1');

-- ----------------------------
-- Table structure for `jc_content_topic`
-- ----------------------------
DROP TABLE IF EXISTS `jc_content_topic`;
CREATE TABLE `jc_content_topic` (
  `content_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL,
  PRIMARY KEY (`content_id`,`topic_id`),
  KEY `fk_jc_content_topic` (`topic_id`),
  CONSTRAINT `fk_jc_content_topic` FOREIGN KEY (`topic_id`) REFERENCES `jc_topic` (`topic_id`),
  CONSTRAINT `fk_jc_topic_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS专题内容关联表';

-- ----------------------------
-- Records of jc_content_topic
-- ----------------------------
INSERT INTO `jc_content_topic` VALUES ('550', '6');
INSERT INTO `jc_content_topic` VALUES ('550', '7');
INSERT INTO `jc_content_topic` VALUES ('550', '8');

-- ----------------------------
-- Table structure for `jc_content_txt`
-- ----------------------------
DROP TABLE IF EXISTS `jc_content_txt`;
CREATE TABLE `jc_content_txt` (
  `content_id` int(11) NOT NULL,
  `txt` longtext COMMENT '文章内容',
  `txt1` longtext COMMENT '扩展内容1',
  `txt2` longtext COMMENT '扩展内容2',
  `txt3` longtext COMMENT '扩展内容3',
  PRIMARY KEY (`content_id`),
  CONSTRAINT `fk_jc_txt_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容文本表';

-- ----------------------------
-- Records of jc_content_txt
-- ----------------------------
INSERT INTO `jc_content_txt` VALUES ('256', '<p style=\"text-indent: 2em\">2011年即将结束，每年的这个时候都是购机的旺季。对于用户而言，购买手机最关心的就是价格问题，降到心理价位就要入手？NO！NO！NO！从手机市场来看，价格便宜但仍需稳定，如果今天手机报价3500元，明天就跌至3000元，这样的情况谁也不愿碰到。相反对于官方定价的手机，比如<span class=\"infoDataBank\">小米手机 </span>，1999元的售价未来很长一段时间都不会再降，因为再降价就没有利润空间了，这样来看，消费者买回来会比较踏实，不必担心三天两头价格暴降。好了闲话少说，春节即将到来，笔者也为大家盘点几款最保值智能手机，希望朋友们能够喜欢。</p>\r\n<div class=\"dataSourceCardInfo\" style=\"display: none\">\r\n<div class=\"arrowBox\">\r\n<div calss=\"arrow\">&nbsp;</div>\r\n</div>\r\n<div class=\"dataloading\">&nbsp;</div>\r\n<div class=\"dataCardUserDetail\">&nbsp;</div>\r\n</div>\r\n<p>&nbsp;</p>\r\n<p>&nbsp;</p>', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('257', '<p style=\"text-indent: 2em\">蓬莱19-3油田发生溢油事故已逾半年，在渔民们纷纷对<span>康菲石油</span>公司提起索赔诉讼之时，康菲石油公司16日却表示，基本没有证据显示溢油事故对环境造成影响。</p>\r\n<p style=\"text-indent: 2em\">上述表态是康菲石油公司在接受《华尔街日报》采访时表示的。昨日，《第一财经<!--keyword-->（<span class=\"infoMblog\">微博</span>)<!--/keyword-->日报》在康菲石油公司相关负责人处得到确认，该负责人表示，康菲石油公司确实在与《华尔街日报》等国际媒体交流时发表了上述言论。</p>\r\n<p style=\"text-indent: 2em\">溢油事件发生后，康菲公司分别于7月和8月召开新闻发布会，随后，康菲公司开展了多次与各利益方的小规模交流会。<span>环保</span>组织公众环境研究中心主任马军就受邀与康菲公司负责人在12月8日进行了交流。</p>\r\n<p style=\"text-indent: 2em\">对于康菲对国际媒体表示溢油没有影响环境的行为，马军质疑称：&ldquo;康菲公司与我们会面时还表示，要对受到漏油损害的利益方做出赔偿，并为改善渤海环境作贡献，如今怎么做出前后不一致的表述呢？今年6月发生的两次溢油事故对渤海水质造成了污染是无需辩驳的事实。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">马军对记者表示，渤海是半封闭的内海，平均水深仅为20多米，溢油对渤海环境的影响是比较严重的危害。而且，溢油造成的油基泥浆至今还未完全清理干净，对环境的影响还将长期存在。他说，康菲公司对公众隐瞒不报在先，屡屡误导陈述在后，同时一再试图回避承担其造成的<span>生态</span>损害和养殖等经济利益损失，其公信力已经丧失。</p>\r\n<p style=\"text-indent: 2em\">其实，不难看出康菲公司16日的言论与其此前的公开表态存在较大矛盾。9月6日，康菲表示将设立渤海湾<!--keyword--><!--/keyword-->基金<!--keyword--><!--/keyword-->，将根据中国相关法律承担公司应尽的责任并有益于渤海湾的整体环境。康菲石油公司董事长兼首席执行官穆怀礼当时表示：&ldquo;康菲石油公司对于该事件的发生深表歉意，以及因此对中国的人民和环境产生的影响表示道歉。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">作为河北省乐亭县107名渔民向康菲石油公司提起4.9亿元经济赔偿的代理律师，赵京慰对康菲公司16日的言论评价说：&ldquo;康菲公司的自我辩解没有实质意义，法院的裁决不会以康菲的意志为转移，而是会以权威机构作出的鉴定为准。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">&ldquo;康菲公司一直没有对该基金的具体运作做出解释说明。如今，康菲前后表述自相矛盾，再次证明他们对赔偿并没有诚意，也体现了其不负责任的态度。&rdquo;赵京慰说。</p>\r\n<p style=\"text-indent: 2em\">此前，赵京慰已分别向国家<span>海洋</span>局和农业部申请了政府信息公开。国家海洋局政府信息公开答复书显示，蓬莱19-3油田溢出的油污已经在河北省乐亭县养殖区周边登陆。农业部办公厅的答复函也排除了由已知细菌、寄生虫和病毒等病原微<span>生物</span>所导致的水产品大量死亡，确认了赤潮、石油污染等原因可能导致了水产品的滞长和死亡。</p>', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('258', '<p style=\"text-indent: 2em\">随着在华项目越来越多，包括<span class=\"infoMblog\">大众汽车</span>在内的不少跨国汽车巨头陆续遇到高管人才储备断档的瓶颈。这在竞争日益激烈的中国车市，能否顺利解决这一难题，将成为影响企业在华业绩的重要因素。</p>\r\n<p style=\"text-indent: 2em\"><strong>&ldquo;中国60&rdquo;计划</strong></p>\r\n<p style=\"text-indent: 2em\">Tom（化名）是德国大众汽车总部两年前派到一汽-<!--keyword--><!--/keyword-->大众<!--keyword--><!--/keyword-->的一位主管整车技术的高管，明年年中，工作签证到期的他将回到&ldquo;狼堡&rdquo;（沃尔夫斯堡，大众汽车集团总部所在地），而他所负责的一汽-大众佛山分公司的项目则会交给一位中方经理接手。</p>\r\n<p style=\"text-indent: 2em\">作为一家深耕中国车市二十余年的汽车巨头，很长一段时间以来，大众汽车向中国合资公司派驻了大量Tom这类的外籍专家，但是随着在华启动的新项目越来越多，甚至在其他快速增长的国家同样需要外派专家。&ldquo;外脑&rdquo;数量有限的大众汽车打算改变策略。</p>\r\n<p style=\"text-indent: 2em\">&ldquo;大众汽车集团是一个全球性的公司，不只在中国，在印度、俄罗斯和其他任何地方都有自己的分公司，随着大众汽车的壮大，德国总部一时很难派出大量外籍专家。&rdquo;大众汽车集团（中国）董事会成员、主管人力资源的狄凯思向《第一财经日报》记者坦言。</p>\r\n<p style=\"text-indent: 2em\">时值大众汽车在中国发展的发力期，今年大众汽车在中国产销量将突破200万辆，在华两个合资公司分别上马了多个新工厂，一汽-大众成都工厂刚刚投产，正在施工的包括一汽-大众佛山工厂、<!--keyword--><!--/keyword-->上海大众<!--keyword--><!--/keyword-->仪征工厂，另外，上海大众新疆工厂也已获批，一大批管理人才缺口显现。</p>\r\n<p style=\"text-indent: 2em\">&ldquo;之前这些职位大部分是由外籍专家来担任的，现在我们希望把这些职位本土化。&rdquo;大众汽车外籍员工协调规划总监田海诺告诉本报记者。</p>\r\n<p style=\"text-indent: 2em\">大众汽车将陆续撤回一批外派专家，与此同时，一个由田海诺负责的&ldquo;中国60&rdquo;项目悄然启动。</p>\r\n<p style=\"text-indent: 2em\">到明年年中，大众汽车计划将在中国本土招聘60位高端人才，岗位是管理级别和高级管理级别。不同于以往低端岗位的人才招聘，这次职位之高史无前例，甚至有些仅低于董事会成员。</p>\r\n<p style=\"text-indent: 2em\">撤回的外派专家几乎涉及大众汽车中国公司里的所有领域，从技术到销售再到人力资源，尤其是大众汽车在中国新建的几家新工厂，高级经理级人才有大量空缺。</p>\r\n<p style=\"text-indent: 2em\">这60位通过网络、猎头等方式招聘来的人员将进行18个月的培训，这期间，大众汽车将把他们派到德国总部培训6~12个月，跟相应职位的人进行交流、沟通。培训结束后，他们会重新回到中国。目前，已经招到11位管理人才。</p>\r\n<p style=\"text-indent: 2em\">&ldquo;从费用的角度来说，这不是一个省钱的做法，因为要花费时间和金钱来培训这些人更好地了解大众汽车集团的理念。&rdquo;田海诺告诉记者。</p>\r\n<p style=\"text-indent: 2em\">但显然，大众汽车更加看重本土化高管的优势，&ldquo;如果是外籍专家，根据政策他们在工作一定时期后进行岗位轮换。如果我们选择愿意在中国发展的人，他们会有更好的工作心态，我们也可以保证在合资厂的工作得以高质量地完成。&rdquo;</p>\r\n<p style=\"text-indent: 2em\"><strong>本土人才优势凸显</strong></p>\r\n<p style=\"text-indent: 2em\">不少在华投资的跨国车企与大众汽车有着相似的经历。一直被本土化做得不好而受诟病、采取外派高管方式管理中国市场的<!--keyword--><!--/keyword-->标致<!--keyword--><!--/keyword-->雪铁龙集团，为了更贴近中国市场，从今年起加大了本土化高管人才的&ldquo;替换&rdquo;。人力资源总监、公共事务及企业传讯总监，以及合资公司<!--keyword--><!--/keyword-->东风雪铁龙<!--keyword-->(微博)<!--/keyword-->的市场部总监、<!--keyword--><!--/keyword-->东风标致<!--keyword--><!--/keyword-->市场部总监等职务，均由本土化人才担任。</p>\r\n<p style=\"text-indent: 2em\">有一些企业则采取折中的方式，包括上海大众、<!--keyword--><!--/keyword-->斯柯达<!--keyword--><!--/keyword-->等企业，设立中外双总监制，相互制约的同时，更大程度上做到互为补充。</p>\r\n<p style=\"text-indent: 2em\">其实，除了汽车行业，移动通讯、PC、软件、连锁百货甚至飞机制造业，都已实行&ldquo;本土高层管理本土企业&rdquo;的人才策略。</p>\r\n<p style=\"text-indent: 2em\">很多外企公关、法律、市场和业务等部门的经理都由本土人员担任。&ldquo;他们更熟悉本土的情况，更有利于企业适应本土文化。&rdquo;北京外企人力资源服务有限公司总经理王一谔分析认为。</p>\r\n<p style=\"text-indent: 2em\">资深汽车分析师钟师认为，人才本地化有诸多好处，其一是可以节省成本，让老外赴华工作要承担很大的开销，也会减损中国雇员的积极性；其二是销售、市场等业务&ldquo;是很文化的东西，需要接地气，和中国人打交道处关系，外国人并不擅长&rdquo;。</p>\r\n<p style=\"text-indent: 2em\">外资进入中国这么多年来一直注重员工的培训和各方面技能的提高，这也为人才本土化创造了条件。汽车业内人士认为，合资的模式走了二十多年，积累了一大批优秀的管理人才，本土化的高管战略足以在一定程度上加快跨国公司在中国的业务发展。</p>', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('259', '<p style=\"text-indent: 2em\"><strong>腾讯科技讯</strong>（乐天）12月18日消息，小米手机今日正式开放销售，不过，在宣布开放销售仅仅3个小时后，小米官方腾讯微博发布公告，称今天凌晨开放购买3小时，12月在线销售10万库存已售罄。小米还表示，下一轮开放购买，请关注小米论坛和官方微博的通知。</p>\r\n<p style=\"text-indent: 2em\">据了解，小米手机今日凌晨宣布所有个人用户可直接购买，还开通了企业客户采购通道，并提出了年会送员工，年终答谢送客户小米手机的口号，这也意味着小米手机正式加入了智能手机市场的争夺，1999元的价格也必将搅动智能手机市场。</p>\r\n<p style=\"text-indent: 2em\">针对个人用户，小米手机有一定限制，即每人仅限购两台。企业客户能一次性采购多部小米手机，但必须是企事业团体单位，能提供有效的营业执照等资质证明，并且一次采购10部小米手机，并且填写企业客户采购申请表给小米，小米公司资质审核通过，并且企事业团体单位付款后才能收货。</p>\r\n<p style=\"text-indent: 2em\">宣布小米手机开放购买的同时，针对市场上小米手机配件不足的情况，小米还推出包括耳机、后盖、保护壳、保护套、贴膜、贴纸、挂饰、支架、存储卡等原装配件，可供用户购买。</p>\r\n<p style=\"text-indent: 2em\">据官方介绍，用户需要到小米网生成订单后可获得小米手机，可以选择支付宝、财付通、网银等支付方式，也可以选择货到付款，支持现金支付，但不能直接去小米之家购买商品，必须在小米之家和用户确认后才能去指定的小米之家提货。</p>\r\n<p style=\"text-indent: 2em\">据小米科技副总裁黎万强<!--keyword-->(<span class=\"infoMblog\">微博</span>)<!--/keyword-->透露：&ldquo;凌晨开始，第一小时下单破50000。纪念历史的一刻。应大家要求。发张办公室图片。我们要专心把服务改善得更好。谢谢米粉的支持和厚爱。&rdquo;今天凌晨开放购买3小时，12月在线销售10万库存已售罄。</p>\r\n<p style=\"text-indent: 2em\">对此，众多&ldquo;米粉&rdquo;很愤怒，也很失望，有观点指出，小米手机获得了200万部联通定制，一切经营要给定制机让路，也有观点认为，小米是在学习苹果、魅族搞饥饿营销。不过，小米官方并未就此表态。</p>', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('342', '<p>\r\n	&nbsp;</p>\r\n<p>\r\n	由于市场对石油需求急剧上升,钻井配件的需求量也随之大增。要想成为配件供货商,首先要进入供货名录,随后才能签订合同,最后结算货款,这一整套业务流程需要多个科室审批同意。于是,每个环节的科室负责人都成为供货商&ldquo;打点&rdquo;、&ldquo;公关&rdquo;的对象。</p>\r\n<p>\r\n	&nbsp;</p>\r\n<p>\r\n	《法制日报》记者3日从江苏省镇江市润州区人民检察院获悉,该院查办的中石化华东石油局第六普查大队8名高管贪腐窝案已经宣判。</p>\r\n<p>\r\n	&nbsp;</p>\r\n<p>\r\n	<strong>模糊线索牵出大案</strong></p>\r\n<p>\r\n	&nbsp;</p>\r\n<p>\r\n	2009年2月,镇江市润州区检察院收到一封匿名举报信,称华东石油局第六普查勘探大队以及下属部门相关高管涉嫌职务犯罪。但是,信件没有写明确切的涉案人员和具体事件。检察官初步判断,此线索有侦查价值。</p>\r\n<p>\r\n	经查,六普大队是一家隶属于中石化集团华东石油局的国有企业,旗下不仅管理着六普石油机械厂等多家企业,还按业务范围划分江苏工区、东北工区、西北工区、 海外工区。其主要经营业务就是勘探石油、钻井开发,石油机械厂等辖下企业为各工区提供钻前服务、设备维护修理、采油管道制造等后勤物资保障。</p>\r\n<p>\r\n	检察机关调查发现,由于近年来市场对石油需求急剧上升,六普大队各工区勘探、钻井量也随之猛增,而钻头、循环罐等钻井配件市场也日趋火爆,一些企业都瞄准了利润丰厚的配件供应业务。</p>\r\n<p>\r\n	要想成为供货商,配件生产企业首先要进入六普大队的供货方名录,然后才可以与六普大队下属各基层用货单位签订供货合同。基层单位收到供应的配件后,给六普 大队物资装备科报付款计划,物资装备科长审批同意后交计财科,计财科长审批同意后报分管领导审核,最后由大队长批准付款。在供货商看来,每个环节的科室负 责人都是需要&ldquo;打点&rdquo;和&ldquo;公关&rdquo;的。</p>\r\n<p>\r\n	经过两年侦查,检察机关锁定了几名主要行贿人,其中就有某民营电气公司经理姚成虎。检察官与姚成虎接触不到4个小时,姚成虎就全盘交代了其向机械厂原厂长、现六普大队物资装备科科长杨拥军行贿的事实,此外还供述了对前任装备科长魏国祥行贿的事实。</p>\r\n<p>\r\n	&nbsp;</p>\r\n<div style=\"page-break-after:always\">\r\n	<span style=\"display: none;\">&nbsp;</span></div>\r\n<p>\r\n	&nbsp;</p>\r\n<p>\r\n	<strong>求情不成匆忙退赃</strong></p>\r\n<p>\r\n	&nbsp;</p>\r\n<p>\r\n	看到手下两员大将先后&ldquo;落马&rdquo;,六普大队大队长李锋坐卧不安。</p>\r\n<p>\r\n	2012年3月初,李锋带着六普大队领导班子成员到润州区检察院求情,被润州区检察院检察长鲁宽拒绝。</p>\r\n<p>\r\n	&nbsp;</p>\r\n<p>\r\n	得知李锋斡旋无果,其他有问题的高管惶惶不安,纷纷联系各自的行贿人退赃。六普大队工程技术科原科长宋文勇,为了尽快将山东德州一名供应商送的25000 元钱退还,让妻子和11岁的女儿一起坐火车去德州退钱。由于当时买不到座位票,宋文勇的妻子和女儿从镇江整整站了一夜到德州。</p>\r\n<p>\r\n	&nbsp;</p>\r\n<p>\r\n	&nbsp;</p>\r\n<p>\r\n	李锋更是有如热锅上的蚂蚁,从检察院回来的第二天一大早,他就让妻子用路边公用电话联系行贿人曹敏、徐军夫妇的远房亲戚,通知曹敏、徐军赶紧过来领回赃款。</p>\r\n<p>\r\n	与此同时,检察官在继续侦查中还发现,石油机械厂书记高振红存在重大经济问题嫌疑。</p>\r\n<div style=\"page-break-after:always\">\r\n	<span style=\"display: none;\">&nbsp;</span></div>\r\n<p>\r\n	&nbsp;</p>\r\n<p>\r\n	<strong>收钱办事成&ldquo;原则&rdquo;</strong></p>\r\n<p>\r\n	&nbsp;</p>\r\n<p>\r\n	随着调查的深入,李锋的敛财行径逐一暴露。</p>\r\n<p>\r\n	2004年,李锋从六普大队副队长调任固井工程公司经理,为了解决一些不好入账的费用以及给职工发放福利,他指令固井工程公司计财部经理李江开设&ldquo;小金库&rdquo;,通过多开货款给供货商,供货商领到钱后把虚开部分返还的方式筹措资金。</p>\r\n<p>\r\n	2008年7月,李锋调任六普大队大队长前,李江向其汇报&ldquo;小金库&rdquo;还有37万余元资金,请示该如何处理。李锋提出两人平分。2011年6月,李锋以其姐姐买房缺钱为由,让李江将&ldquo;小金库&rdquo;中的18万元汇入其指定的个人账户。</p>\r\n<p>\r\n	2008年6月,李锋调任六普大队大队长一职后,大开欲望之门,几乎是逢送必收、逢事必收,收礼办事成了他的不二法则。</p>\r\n<p>\r\n	经镇江市检察院侦查查明,李锋还收受六普大队下属的东北工区、西北工区、海外工区、管子站等部门、个人给予的好处,为这些单位、人员在年度综合考评、干部 调动等方面牟取利益。2009年至2012年期间,利用担任大队长的职务便利,李锋共收受他人贿赂共计人民币60万元、美元3000元。</p>\r\n<p>\r\n	此案经审理后,李锋因犯贪污罪、受贿罪,被判处有期徒刑12年并没收财产人民币20万元；杨拥军、魏国祥、董正军、宋文勇等人被判处有期徒刑或缓刑不等的刑罚；李江犯贪污罪,被免于刑事处</p>\r\n<div style=\"page-break-after:always\">\r\n	<span style=\"display: none;\">&nbsp;</span></div>\r\n<p>\r\n	罚。（记者马超 通讯员杨海峰雷文辉）<img alt=\"\" src=\"/u/cms/www/201307/08104002qve3.jpg\" style=\"height: 165px; width: 165px;\" /></p>\r\n<p>\r\n	&nbsp;</p>\r\n<p>\r\n	&nbsp;</p>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('398', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end --><!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12248428\' f_id=\'45\' -->\r\n	<p>　　李玲 深圳商报记者</p>\r\n	<p>　　日前景顺长城公告称，将旗下景顺长城上证180等权重交易型开放式指数证券投资基金(ETF)联接基金的募集日期延长至6月20日，成为今年首只募集期超过50天的新基金。至此，今年以来已有5只新基金延长募集期，除几只创新性产品之外，今年基金平均募集金额仅为9.15亿元，相比去年同期缩水近半。</p>\r\n	<p>　　<strong>基金募集规模创新低</strong></p>\r\n	<p>　　除了景顺长城上证180等权重ETF联接基金之外，今年以来延长募集期的其他4只基金分别是长信可转债、中欧盛世成长分级基金、信诚周期轮动基金和泰达宏利逆向策略基金。</p>\r\n	<p>　　在基金出现延期销售的同时，新基金募集规模也呈现冰火两重天，一边是<span id=\"stock_sz399300\"><a class=\"keyword\" href=\"http://finance.sina.com.cn/realstock/company/sz399300/nc.shtml\" target=\"_blank\">沪深300</a></span>ETF和短期理财产品的热卖、创下募集规模新高，而传统型基金则遭冷遇。wind数据显示，今年以来新成立117只基金(A、B基金分开计算)，若剔除两只沪深300ETF及两只短期理财基金，113只基金募资总额约为1042.84亿元左右，平均每只基金的募集份额在9.15亿元左右，仅为去年单只基金平均募集额17.07亿元的5成左右。</p>\r\n	<p>　　同时，不少基金甚至是勉强撑在成立的生死线上，首募金额在4亿之下的基金多达39只。其中，泰信保本其募集额仅为2.2亿元，距离2亿元的成立及格线仅有一步之遥。中海中证380首募金额仅为2.67亿元，信诚周期轮动及国泰大宗商品首募规模也仅有3.08亿元及3.09亿元。</p>\r\n	<p>　　基金募集需要帮忙资金的赞助已经是业内公认的事实，几只创新型基金募集规模创下新高，燃起了业内应多进行产品创新的呼声。但是记者在采访中了解到，目前这类基金发行形势也开始不容乐观。</p>\r\n	<p>　　某基金公司目前正在发行一只创新型产品，原以为会像上只基金一样在数日内创下募集新高。没想到，几天过去了在国内一些主要地区的销售数据依然为零。&ldquo;被逼无奈，公司只能拿出一部分资金购买销量，只一单数量就达到了5000万元。&rdquo;一位业内人士透露。在创新基金也需帮忙资金支持的背景下，其他基金的销售更是举步维艰。</p>\r\n	<p>　　<strong>市场已到底部区域？</strong></p>\r\n	<p>　　业内人士表示，统计显示基金募集情况和股市的走势有一定的关系，基金募集的低点往往是股市的见底之时。</p>\r\n	<p>　　湘财证券分析师王坦表示，市场的连续走高，往往将募集水平推向惊人的爆发水平；而市场的连续走低，又总是将募集水平推至一个极度低点。尤其是在市场底部即将出现时，股票型基金的募集往往只剩下刚性需求，成立规模一般在5亿左右。而在市场顶部即将出现时，股票型基金的成立规模往往会爆发，但在绝对水平上没有较好的规律可循。</p>\r\n	<!-- news_keyword_pub,stock,sz399300 -->\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12248428\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12248428\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<div class=\"otherContent_01\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('399', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end --><!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12248619\' f_id=\'45\' -->\r\n	<p>　　信息时报讯(记者 叶静) 作为证监会首批批准的独立基金销售机构之一的众禄基金研究中心昨日起试营业，成为国内第一家正式试业的独立基金销售机构。</p>\r\n	<p>　　众禄基金相关负责人向记者透露，本次试业一共有13家基金公司产品进入众禄基金销售超市，6月底前至少会上架22家基金公司的产品。&ldquo;我们将致力于为投资者提供差异化的专业投资顾问服务、更便宜的价格和更安全的交易&rdquo;。</p>\r\n	<p>　　据悉，独立基金销售机构即专业基金销售公司，是指在以基金为主的领域内，从事投资顾问服务、产品销售、资产管理等业务的第三方理财服务机构。为了改变当前中国基金行业有销售无服务的现状，监管层借鉴发达国家经验推出了独立基金销售机构，并于今年2月22日核准了众禄基金等4家机构作为首批独立基金销售机构。</p>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12248619\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12248619\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<div class=\"otherContent_01\">&nbsp;</div>\r\n	<div class=\"otherContent_01\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('400', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end --><!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12248674\' f_id=\'45\' -->\r\n	<p>　　信息时报讯 中登公司最新公布数据显示，上周，沪深两交易所新增A股开户数为10.9550万户，连续4周稳定在9万户以上，较之前一周增长约8%。</p>\r\n	<p>　　数据显示，上周新增股票账户10.9838万户，参与交易的A股账户数为1076.44万户；期末持仓A股账户数为5681.94万户；期末有效账户数13780.59万户。此外，当周两市新增B股开户数288户。</p>\r\n	<p>　　上周新增基金开户数为59462户，较此前一周减少27401户或31.55%；其中新增封闭式基金开户数为2768户，TA系统配号产生的基金开户数为56694户；期末基金账户数合计为3830.06万户。</p>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12248674\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12248674\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<div class=\"otherContent_01\">&nbsp;</div>\r\n	<div class=\"otherContent_01\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('401', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end --><!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12248895\' f_id=\'45\' -->\r\n	<p><span style=\"font-family: KaiTi_GB2312,KaiTi;\">　　把握申购契机获稳定收益</span></p>\r\n	<p>　　经过近5年的发展，由于定位清晰，分级基金已被越来越多的投资者所熟悉并青睐。进入6月以来，市场上多只采取半封闭式的债券分级基金的稳健份额将打开申购赎回，并频频受到资金青睐，获得超额申购，启动比例配售。投资者可寻找其中的稳健性投资机会。</p>\r\n	<p>　　<strong>6月五只稳健份额打开申赎</strong></p>\r\n	<p>　　6月1日开放申赎的万家添利A债券型基金，吸引了超过20亿元新增资金申购，加上存量资金，这一数据已超过了万家添利A按基金合同规定的规模上限，公司公告将启动比例配售，确认比例为65.18%。同日打开申购赎回的天弘添利A也获得超额申购，一天的开放日内申购超过14亿元，并启动比例配售机制。</p>\r\n	<p>　　分级债基稳健份额受到较多资金追捧，一方面得益于资金面宽松，以及股市震荡形势下投资者偏好固定收益产品的市场氛围。另一方面，降息预期渐浓，银行理财产品预期收益率普遍下滑，货币基金收益率整体降低，也显示出部分较高约定收益率的债券基金的相对吸引力。</p>\r\n	<p>　　分级债基的稳健份额类似于固定收益类产品，在满足契约规定的条件下，投资者将可获得约定收益，由于债券型基金整体风险较低，因而其稳健份额获得约定收益的确定性较强，除非极端情况发生。目前稳健份额有上市交易和非上市交易两种模式，非上市交易的稳健类份额采用定期打开申购赎回模式，一般每半年或者三个月定期打开一次申购赎回，投资者可以于指定的申赎交易日申购或者赎回稳健份额。</p>\r\n	<p>　　根据<span id=\"stock_sh600837\"><a class=\"keyword\" href=\"http://finance.sina.com.cn/realstock/company/sh600837/nc.shtml\" target=\"_blank\">海通证券</a></span>基金研究中心的数据整理(见表格)，目前市场上存在的债券型稳健份额的约定收益相比市场上一年期银行定期存款的收益率(当前为3.5%)有较明显的优势，此外每隔半年或三个月可定期打开申购，保证了较高的流动性，加之其风险很低，因而提供了投资者一个流动性及收益率均好于银行定期存款的投资品种。</p>\r\n	<p>　　6月有5只基金打开申购赎回，除文中提及的天弘添利A和万家添利A外，今日将有长信利鑫A打开申购赎回、周五博时裕祥A也将打开申购赎回，6月21日还有长信利鑫A。这些产品的利息每半年或三个月打开申购赎回时才调整一次，因而在打开申购赎回之后如果市场降息，这些产品将不受影响，仍将获得较高利息收入。在当前市场降息预期较高的情况下，在一个运作周期内锁定当前较高的利率无疑增加了吸引力，投资者不妨将闲置资金适当配置这类基金。</p>\r\n	<p>　　<strong>把握申购契机获稳定收益</strong></p>\r\n	<p>　　从6月份打开申赎的分级债基稳健份额的情况看，海通证券基金分析师倪韵婷表示，6月8日打开的博时裕祥A吸引力最大。理由有以下两点：</p>\r\n	<p>　　首先，从约定收益来看，在6月打开申购赎回的品种中，博时裕祥A约定收益最高，达到5%，相比当前市场无风险利率有1.5%的超额收益。</p>\r\n	<p>　　其次，博时裕祥A可以引入较多申购份额。由于流动性以及收益率均优于银行定期存款的产品，债券型稳健份额在多数时间受到追捧，而根据契约规定，稳健份额与激进份额的配比不能超过上限值，因而当申购资金大于实际可申购份额时，投资者将有部分资金申购失败。作为最早一只打开申购赎回的天弘添利在历史上就曾多次遇到申购额远大于赎回额的情况，导致众多申购者无功而返，而月初万家添利也启动了比例配售。但值得注意的是，去年年末打开申购赎回的几只债券稳健份额，由于打开时间恰逢年底银行揽储，因而遭受了大额赎回，使得稳健份额与激进份额的份额配比小于约定的最高份额配比。统计了市场上之前打开申购赎回的稳健份额的情况，可以发现，博时裕祥A份额的占比仅为上限的一半，而长信利鑫A的份额已经超过上限的3/4，天弘丰利A的份额几乎达到了上限。博时裕祥A本次打开申购赎回时将提供投资者更多可申购的份额。</p>\r\n	<p>　　事实上，博时裕祥A打开申购赎回同样也将极大的影响博时裕祥B。当债券稳健份额遭受大额赎回，导致稳健份额与激进份额配比下降，从而使得激进份额有效杠杆下降。目前银行揽储动力不大，因而利率较高的短期理财产品稀缺，使得流动性、收益率俱佳的债券稳健份额再度进入投资者视线，从近期打开的富国天盈不难发现，申购热情再度归来。当稳健份额打开后或也将迎来净申购，从而提升激进份额的有效杠杆。</p>\r\n	<p>　　但投资者需要注意的是，杠杆提升带给博时裕祥B的利好或许已提前反应，因为经过富国天盈B的事件型投资机会，市场对于同类事件的关注度已明显提升，博时裕祥B在去年12月经历大幅净赎回后，折价从5.72%迅速扩大至10.17%，而近期其折价则从9.15%大幅缩减至4.99%，对此次杠杆提升的预期已经有所反应。</p>\r\n	<p>　　黄晓霞&nbsp;&nbsp;</p>\r\n	<!-- news_keyword_pub,stock,sh600837 -->\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12248895\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12248895\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<div class=\"otherContent_01\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('402', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end --><!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12249445\' f_id=\'45\' -->\r\n	<p>　　长信基金<a href=\"http://weibo.com/cxfundofficialsite?zw=finance\" onmouseover=\"WeiboCard.show(1886758463,\'finance\',this)\" target=\"_blank\">(微博)</a>旗下长信利鑫分级债基A份额将于6月下旬打开申购，开放时间仅1天。</p>\r\n	<p>　　据了解，长信利鑫分级债基A份额当期约定年收益4.65%、每六个月打开一次、1000元起售，且母基金的净资产将优先支付利鑫A的本金及约定收益。对于债市未来走势，业内主流观点仍认为将延续慢牛行情，这对分级债基A份额是利好。(吴昊)</p>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12249445\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12249445\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<div class=\"otherContent_01\">&nbsp;</div>\r\n	<div class=\"otherContent_01\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('403', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end --><!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12252418\' f_id=\'45\' -->\r\n	<p>　　中证网讯 中国证监会主席郭树清7日在中国证券投资基金业协会成立大会暨第一届年会上表示，推动更多机构投资者积极参与上市公司的公司治理。</p>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12252418\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12252418\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<div class=\"otherContent_01\">&nbsp;</div>\r\n	<div class=\"otherContent_01\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('404', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end --><!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12252434\' f_id=\'45\' -->\r\n	<p>　　中证网讯 中国证监会主席郭树清7日在中国证券投资基金业协会成立大会暨第一届年会上要求，基金公司要牢固树立以客户为中心的发展理念，设计出一些跑得赢通胀，风险不太大的产品，同时也为高端客户提供高收益高风险的产品。</p>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12252434\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12252434\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<script src=\"http://news.sina.com.cn/iframe/survey/js/4/f_content_69892.js\" type=\"text/javascript\" charset=\"GB2312\"></script>\r\n	<div class=\"otherContent_01\">&nbsp;</div>\r\n	<div class=\"otherContent_01\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div class=\"corrTxt_01\">\r\n			<h3>\r\n				&gt; 相关专题：</h3>\r\n			<ul>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/focus/cnfundAssociation/\">中国基金业协会成立</a></li>\r\n			</ul>\r\n		</div>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('405', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end --><!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12252487\' f_id=\'45\' -->\r\n	<p>　　作者：申屠青南<a href=\"http://weibo.com/u/1692651115?zw=finance\" onmouseover=\"WeiboCard.show(1692651115,\'finance\',this)\" target=\"_blank\">(微博)</a></p>\r\n	<p>　　中证网讯 中国证监会主席郭树清7日在中国证券投资基金业协会成立大会暨第一届年会上发表演讲，演讲题目为《我们需要一个强大的财富管理行业》。郭树清指出，发展财富管理行业，对经济转型具有根本意义，也是改善国民分配的需要，推进资本市场发展的需要。</p>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12252487\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12252487\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<div class=\"otherContent_01\">&nbsp;</div>\r\n	<div class=\"otherContent_01\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('406', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end --><!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12252646\' f_id=\'45\' -->\r\n	<div class=\"img_wrapper\"><img alt=\"郭树清\" src=\"http://i1.sinaimg.cn/cj/2012/0607/U5243P31DT20120607230902.jpg\" title=\"郭树清\" /><span class=\"img_descr\">郭树清</span></div>\r\n	<p>　　中国证券网讯 (记者 赵艳云) 中国基金业协会7日在北京召开成立大会，证监会主席郭树清在16时左右为基金业协会揭牌并发表题为《<a href=\"http://finance.sina.com.cn/money/fund/20120607/180712252954.shtml\" target=\"_blank\">我们需要一个强大的财富管理行业</a>》的重要讲话。郭树清称，个人投资者为中国资本?场作出了巨大贡献。</p>\r\n	<p>　　<strong>相关报道：</strong></p>\r\n	<p>　　<a href=\"http://finance.sina.com.cn/money/fund/20120607/180712252954.shtml\" target=\"_blank\">郭树清：我们需要一个强大的财富管理行业</a></p>\r\n	<p>　　<a href=\"http://finance.sina.com.cn/money/fund/20120607/170912252487.shtml\" target=\"_blank\">郭树清：发展财富管理行业</a></p>\r\n	<p>　　<a href=\"http://finance.sina.com.cn/stock/y/20120607/165212252324.shtml\" target=\"_blank\">郭树清：推动机构投资者参与上市公司治理</a></p>\r\n	<p>　　<a href=\"http://finance.sina.com.cn/money/fund/20120607/170312252434.shtml\" target=\"_blank\">郭树清：基金公司要牢固树立客户为中心理念 </a></p>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div id=\"blk_shareComm_1\" style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12252646\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12252646\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<script src=\"http://news.sina.com.cn/iframe/survey/js/4/f_content_69892.js\" type=\"text/javascript\" charset=\"GB2312\"></script>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_1\">&nbsp;</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_2\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div class=\"corrTxt_01\">\r\n			<h3>\r\n				&gt; 相关专题：</h3>\r\n			<ul>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/focus/cnfundAssociation/\">中国基金业协会成立</a></li>\r\n			</ul>\r\n		</div>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('407', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end --><!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12252954\' f_id=\'45\' -->\r\n	<div class=\"img_wrapper\"><img alt=\"中国证券投资基金业协会在北京召开成立大会暨第一届年会现场，郭树清发表重要讲话。。\" src=\"http://i0.sinaimg.cn/cj/2012/0607/U3365P31DT20120607182225.jpg\" title=\"中国证券投资基金业协会在北京召开成立大会暨第一届年会现场，郭树清发表重要讲话。。\" /><span class=\"img_descr\">中国证券投资基金业协会成立大会暨第一届年会现场，郭树清发表重要讲话。</span></div>\r\n	<p>　　6月7日，中国证券投资基金业协会在北京召开成立大会暨第一届年会，中国证监会主席郭树清为基金业协会揭牌，并发表题为&ldquo;我们需要一个强大的财富管理行业&rdquo;的讲话。</p>\r\n	<p>　　郭树清说，自1998年我国第一只证券投资基金诞生以来，短短14年里，中国的财富管理服务蓬勃发展起来。从封闭式基金到开放式基金，从信托计划到银行理财，从投资连接保险到天使基金和阳光私募，从各种各样的资产管理公司到林林总总的另类投资项目，都显示出勃勃生机，中国的金融业也因之而五彩缤纷、熠熠生辉。然而，我们的财富管理行业总体上还处于幼年时期，不仅远远未能满足市场的需要，而且自身也存在种种的缺点与不足，各个领域都遇到严峻的挑战，同时也蕴藏着巨大的潜力。</p>\r\n	<p>　　郭树清指出，发展财富管理对经济转型具有根本意义。首先，是提高我国经济效率的需要。第二，是改善国民收入分配的需要。第三，是改善金融体系结构的需要。第四，是推进资本市场健康发展的需要。总之，财富管理行业在中国大有前途，正处于重要的发展机遇期。要从我国实际出发，借鉴国际经验，培育各种投资理财机构，让更多的居民和企事业单位等把富裕资金交给机构来实施专业化管理，真正实现居民储蓄、资本市场、企业和社会事业的良性互动。</p>\r\n	<p>　　郭树清表示，基金管理公司应当加快向现代财富管理机构转型。当前，海外成熟市场的财富管理行业已经进入多元投资管理时代，而中国的基金行业则刚刚转型，差距还比较明显。基金管理公司要加快创新发展步伐，切实把握好以下几点：一是坚持从实体经济需要出发，通过设立专项资产管理计划，投资实体资产，扩大专户产品的投资范围，服务实体经济。二是认真学习、消化、吸收国外资产管理公司的业务模式和成功做法，深入分析自身的优势和劣势，明确自身的创新战略。三是重视分析研究工作，下大力气提高研究能力，不能完全依赖外部的研究报告，更不能靠小道消息、盲目跟风。四是坚持价值投资、长期投资理念，建立科学清晰的决策流程和管理制度，形成成熟稳定的投资风格，争取能够发挥对市场的引领作用。</p>\r\n	<p>　　郭树清认为，推进财富管理行业发展，根本在于牢固树立以市场为导向、以客户为中心的经营理念。千千万万的城乡居民、各种各样的社会经济组织，实际上有多种多样的财富管理需求，客观上呼唤多元化的理财服务和产品。关键是根据客户偏好，既能够为大众投资者设计出一些可以跑赢通胀、风险又不太大的产品，也能给少数高端客户提供一些较高风险、较高收益的投资组合。与海外机构相比，中国的财富管理行业除了知识、经验和人才不足之外，还缺乏品牌，缺乏值得市场和公众信赖的投资理财机构，缺乏人们可以将自有财产托付的，或大或小、或进取一点或审慎一些，但确实能够信守承诺，永远把客户放在第一位的金融企业。我们历史上有过的钱庄和票号，就是因为坚持&ldquo;信取天下&rdquo;、&ldquo;以义制利&rdquo;，构建出道德为本、人格为用、伦理约束为保障的信用与品牌体系，曾经创造出令人难以置信的辉煌。</p>\r\n	<p>　　财富管理行业各家机构的基础条件和客观环境各不相同，但有一些必须共同遵守的行为准则。一要讲求诚信，时刻把客户利益放在优先位置。二要强化信息披露，保证投资者能够按照约定及时、准确、完整地获取信息。三要注重创新，切实满足多样化的理财需求。四要合规运作，注意风险控制。从业人员特别是高管人员要坚守职业道德，忠诚于投资者的利益，敬畏于法律的威严，决不能触碰法律底线和道德底线。</p>\r\n	<p>　　郭树清强调，证监会将继续加强基础制度建设，不断优化有利于行业发展的外部环境。配合立法机关加快《证券投资基金法》的修订，争取在拓宽基金公司业务范围，扩大基金投资标的，松绑投资运作限制，优化公司治理，规范行业服务行为等方面取得突破性进展。同时，要按照行为监管和分业监管的原则，重点解决好监管标准的协调适用，避免监管套利和监管真空。</p>\r\n	<p>　　对于基金业协会的成立，郭树清说，我国亟待建设一个强大的财富管理行业，基金业协会的成立正当其时，相信协会一定能够大有作为！希望协会从成立开始，就能跳出基金业的圈子，牢牢把握财富管理行业的发展大趋势，引领行业发展方向，凝聚行业发展共识，共谋行业发展大局。要真正发挥行业协会作用，打破目前财富管理行业存在的条块分割、规模偏小、服务能力不足的局面，大胆创新，引领行业闯出一条符合国情的财富管理业务发展之路。协会要转变观念，回归行业协会的本性，突出服务宗旨；要包容开放，鼓励公平竞争，促进行业创新；加强行业管理，积极探索符合实际的自律方式和手段；勇于担当，切实负起行业和社会责任。协会要发挥作用，引导全行业从业人员牢固树立关心国家、热爱人民、回馈社会、共创和谐的理念，使财富管理行业成为履行社会责任的模范。</p>\r\n	<p>　　大会期间，中国证券投资基金业协会选举产生了第一届理事会、监事会，通过了协会章程、自律公约等相关文件，产生了协会领导班子。</p>\r\n	<p>　　中国证监会副主席刘新华，主席助理吴利军出席会议。民政部、财政部、人社部、住建部、人民银行、税务总局、银监会、保监会、全国社保理事会、外汇局以及北京市金融局、西城区和丰台区政府等单位代表，证监会机关各部门、各派出机构、系统各单位负责人及基金业协会会员代表400余人出席大会。</p>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12252954\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12252954\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<div class=\"otherContent_01\">&nbsp;</div>\r\n	<div class=\"otherContent_01\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div class=\"corrTxt_01\">\r\n			<h3>\r\n				&gt; 相关专题：</h3>\r\n			<ul>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/focus/cnfundAssociation/\">中国基金业协会成立</a></li>\r\n			</ul>\r\n		</div>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('408', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end --><!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12253310\' f_id=\'45\' -->\r\n	<p>　　6月7日，中国证券投资基金业协会在北京召开成立大会暨第一届年会，中国证监会主席郭树清为基金业协会揭牌，并发表题为&ldquo;我们需要一个强大的财富管理行业&rdquo;的讲话。</p>\r\n	<p>　　郭树清指出，基金管理公司要加快创新发展步伐，切实把握好以下几点：一是坚持从实体经济需要出发，通过设立专项资产管理计划，投资实体资产，扩大专户产品的投资范围，服务实体经济。二是认真学习、消化、吸收国外资产管理公司的业务模式和成功做法，深入分析自身的优势和劣势，明确自身的创新战略。三是重视分析研究工作，下大力气提高研究能力，不能完全依赖外部的研究报告，更不能靠小道消息、盲目跟风。四是坚持价值投资、长期投资理念，建立科学清晰的决策流程和管理制度，形成成熟稳定的投资风格，争取能够发挥对市场的引领作用。</p>\r\n	<p>　　郭树清说，自1998年我国第一只证券投资基金诞生以来，短短14年里，中国的财富管理服务蓬勃发展起来。从封闭式基金到开放式基金，从信托计划到银行理财，从投资连接保险到天使基金和阳光私募，从各种各样的资产管理公司到林林总总的另类投资项目，都显示出勃勃生机，中国的金融业也因之而五彩缤纷、熠熠生辉。然而，我们的财富管理行业总体上还处于幼年时期，不仅远远未能满足市场的需要，而且自身也存在种种的缺点与不足，各个领域都遇到严峻的挑战，同时也蕴藏着巨大的潜力。</p>\r\n	<p>　　郭树清指出，发展财富管理对经济转型具有根本意义。首先，是提高我国经济效率的需要。第二，是改善国民收入分配的需要。第三，是改善金融体系结构的需要。第四，是推进资本市场健康发展的需要。总之，财富管理行业在中国大有前途，正处于重要的发展机遇期。要从我国实际出发，借鉴国际经验，培育各种投资理财机构，让更多的居民和企事业单位等把富裕资金交给机构来实施专业化管理，真正实现居民储蓄、资本市场、企业和社会事业的良性互动。</p>\r\n	<p>　　郭树清表示，基金管理公司应当加快向现代财富管理机构转型。当前，海外成熟市场的财富管理行业已经进入多元投资管理时代，而中国的基金行业则刚刚转型，差距还比较明显。基金管理公司要加快创新发展步伐，切实把握好以下几点：一是坚持从实体经济需要出发，通过设立专项资产管理计划，投资实体资产，扩大专户产品的投资范围，服务实体经济。二是认真学习、消化、吸收国外资产管理公司的业务模式和成功做法，深入分析自身的优势和劣势，明确自身的创新战略。三是重视分析研究工作，下大力气提高研究能力，不能完全依赖外部的研究报告，更不能靠小道消息、盲目跟风。四是坚持价值投资、长期投资理念，建立科学清晰的决策流程和管理制度，形成成熟稳定的投资风格，争取能够发挥对市场的引领作用。</p>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div id=\"blk_shareComm_1\" style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12253310\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12253310\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_1\">&nbsp;</div>\r\n	<div class=\"otherContent_01\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div class=\"corrTxt_01\">\r\n			<h3>\r\n				&gt; 相关专题：</h3>\r\n			<ul>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/focus/cnfundAssociation/\">中国基金业协会成立</a></li>\r\n			</ul>\r\n		</div>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('409', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end --><!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12253239\' f_id=\'45\' -->\r\n	<p>　　6月7日，中国证券投资基金业协会在北京召开成立大会暨第一届年会，中国证监会主席郭树清为基金业协会揭牌，并发表题为&ldquo;我们需要一个强大的财富管理行业&rdquo;的讲话。</p>\r\n	<p>　　郭树清表示，基金管理公司应当加快向现代财富管理机构转型。当前，海外成熟市场的财富管理行业已经进入多元投资管理时代，而中国的基金行业则刚刚转型，差距还比较明显。</p>\r\n	<p>　　郭树清指出，基金管理公司要加快创新发展步伐，切实把握好以下几点：一是坚持从实体经济需要出发，通过设立专项资产管理计划，投资实体资产，扩大专户产品的投资范围，服务实体经济。二是认真学习、消化、吸收国外资产管理公司的业务模式和成功做法，深入分析自身的优势和劣势，明确自身的创新战略。三是重视分析研究工作，下大力气提高研究能力，不能完全依赖外部的研究报告，更不能靠小道消息、盲目跟风。四是坚持价值投资、长期投资理念，建立科学清晰的决策流程和管理制度，形成成熟稳定的投资风格，争取能够发挥对市场的引领作用。</p>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div id=\"blk_shareComm_1\" style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12253239\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12253239\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_1\">&nbsp;</div>\r\n	<div class=\"otherContent_01\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div class=\"corrTxt_01\">\r\n			<h3>\r\n				&gt; 相关专题：</h3>\r\n			<ul>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/focus/cnfundAssociation/\">中国基金业协会成立</a></li>\r\n			</ul>\r\n		</div>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('410', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end --><!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12253730\' f_id=\'45\' -->\r\n	<p>　　法制网北京6月7日讯 记者周芬棉 酝酿许久的中国证券投资基金业协会今日在京召开成立大会暨第一届年会。证监会主席郭树清在主旨发言中，要求基金管理公司重视分析研究工作，下大力气提高研究能力，不能完全依赖外部的研究报告，更不能靠小道消息、盲目跟风。</p>\r\n	<p>　　郭树清在题为&ldquo;我们需要一个强大的财富管理行业&rdquo;讲话中指出，与海外机构相比，中国的基金管理公司作为财富管理行业，除了知识、经验和人才不足之外，还缺乏品牌，缺乏值得市场和公众信赖的投资理财机构，缺乏人们可以将自有财产托付的，或大或小、或进取一点或审慎一些，但确实能够信守承诺，永远把客户放在第一位的金融企业。</p>\r\n	<p>　　他表示，虽然财富管理行业各家机构的基础条件和客观环境各不相同，但有一些必须共同遵守的行为准则。一要讲求诚信，时刻把客户利益放在优先位置。二要强化信息披露，保证投资者能够按照约定及时、准确、完整地获取信息。三要注重创新，切实满足多样化的理财需求。四要合规运作，注意风险控制。从业人员特别是高管人员要坚守职业道德，忠诚于投资者的利益，敬畏于法律的威严，决不能触碰法律底线和道德底线。</p>\r\n	<p>　　郭树清强调，证监会将继续加强基础制度建设，不断优化有利于行业发展的外部环境。配合立法机关加快《证券投资基金法》的修订，争取在拓宽基金公司业务范围，扩大基金投资标的，松绑投资运作限制，优化公司治理，规范行业服务行为等方面取得突破性进展。同时，要按照行为监管和分业监管的原则，重点解决好监管标准的协调适用，避免监管套利和监管真空。</p>\r\n	<p>&nbsp;</p>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div id=\"blk_shareComm_1\" style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12253730\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12253730\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_1\">&nbsp;</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_2\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div class=\"corrTxt_01\">\r\n			<h3>\r\n				&gt; 相关专题：</h3>\r\n			<ul>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/focus/cnfundAssociation/\">中国基金业协会成立</a></li>\r\n			</ul>\r\n		</div>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('411', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end --><!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12253941\' f_id=\'45\' -->\r\n	<p>　　中国证券投资基金业协会第一次会员大会于2012年6月6日在北京召开，表决通过了中国证券投资基金业协会第一次会员大会工作报告、《中国证券投资基金业协会章程》、《中国证券投资基金业协会会员管理办法》、《中国证券投资基金业协会会费收缴办法》和《中国证券投资基金业协会会员自律公约》，投票选举产生了协会第一届理事会、第一届监事会。</p>\r\n	<p>　　<strong>第一届理事会成员包括</strong>(以姓氏笔画为序)：于华、王立新、邓召明、田仁灿、刘建平、刘晓艳、孙杰、江先周、许小松、余晓晨、吴晓辉、李?、李爱华、杨东、杨新丰、陈敏、周月秋、尚健、林传辉、林利军、范勇宏、金旭、胡家夫、赵学军、郭特华、崔伟、曹殿义、韩勇、韩康。</p>\r\n	<p>　<strong>　会长：</strong>孙杰</p>\r\n	<p>　　<strong>专职副会长：</strong>韩康、曹殿义</p>\r\n	<p>　　<strong>兼职副会长</strong>(以姓氏笔画为序)：于华、刘晓艳、江先周、陈敏、周月秋、林利军、范勇宏、金旭、赵学军。</p>\r\n	<p>　　<strong>秘书长：</strong>韩康(兼)</p>\r\n	<p>　　<strong>副秘书长</strong>：钟蓉萨、汤进喜</p>\r\n	<p>　　<strong>第一届监事会成员包括</strong>(以姓氏笔画为序)：刘树军、何宝、陈昌宏、周一烽、高良玉<a href=\"http://weibo.com/nfjjgaoly?zw=finance\" onmouseover=\"WeiboCard.show(1885257803,\'finance\',this)\" target=\"_blank\">(微博)</a>、裴长江。</p>\r\n	<p>　　<strong>监事长：</strong>高良玉</p>\r\n	<p>　　<strong>副监事长</strong>：刘树军</p>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div id=\"blk_shareComm_1\" style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12253941\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12253941\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_1\">&nbsp;</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_2\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div class=\"corrTxt_01\">\r\n			<h3>\r\n				&gt; 相关专题：</h3>\r\n			<ul>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/focus/cnfundAssociation/\">中国基金业协会成立</a></li>\r\n			</ul>\r\n		</div>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('412', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end --><!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12257982\' f_id=\'45\' -->\r\n	<p>　　本报讯6月7日，中国证券投资基金业协会在北京召开成立大会暨第一届年会，中国证监会主席郭树清为基金业协会揭牌，并在会上作了题为&ldquo;我们需要一个强大的财富管理行业&rdquo;的讲话。他强调，基金公司应重视分析研究工作，下大力气提高研究能力，不能完全依赖外部的研究报告，更不能靠小道消息、盲目跟风。</p>\r\n	<p>　　郭树清指出，财富管理行业要根据客户偏好，既能够为大众投资者设计出一些可以跑赢通胀、风险又不太大的产品，也能给少数高端客户提供一些较高风险、较高收益的投资组合。与海外机构相比，中国的基金公司除了知识、经验和人才不足之外，还缺乏品牌，缺乏值得市场和公众信赖的投资理财机构，缺乏人们可以将自有财产托付的，或大或小、或进取一点或审慎一些，但确实能够信守承诺，永远把客户放在第一位的金融企业。</p>\r\n	<p>　　据悉，证监会原基金部主任孙杰出任基金业协会会长，华夏基金<a href=\"http://weibo.com/chinaamc1998?zw=finance\" onmouseover=\"WeiboCard.show(1402596635,\'finance\',this)\" target=\"_blank\">(微博)</a>现任副董事长、前任总经理范勇宏为协会普通副会长，南方基金<a href=\"http://weibo.com/nffund?zw=finance\" onmouseover=\"WeiboCard.show(1726551832,\'finance\',this)\" target=\"_blank\">(微博)</a>总经理高良玉<a href=\"http://weibo.com/nfjjgaoly?zw=finance\" onmouseover=\"WeiboCard.show(1885257803,\'finance\',this)\" target=\"_blank\">(微博)</a>为监事长。</p>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div id=\"blk_shareComm_1\" style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12257982\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12257982\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_1\">&nbsp;</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_2\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div class=\"corrTxt_01\">\r\n			<h3>\r\n				&gt; 相关专题：</h3>\r\n			<ul>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/focus/cnfundAssociation/\">中国基金业协会成立</a></li>\r\n			</ul>\r\n		</div>\r\n		<div class=\"corrTxt_01\">\r\n			<h3>\r\n				&gt; 相关报道：</h3>\r\n			<ul>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/stock/yjdt/20120608/090512259289.shtml\" target=\"_blank\">郭树清：散户为中国资本?场作出了巨大贡献</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120608/074012258249.shtml\" target=\"_blank\">郭树清：基金投资别依赖研报</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120608/063912257666.shtml\" target=\"_blank\">郭树清：基金投资标的将扩大</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120608/025912255954.shtml\" target=\"_blank\">郭树清：我们需要强大的财富管理行业</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120608/005912254692.shtml\" target=\"_blank\">郭树清:基金公司应加快向现代财富管理转型</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/stock/y/20120607/172812252646.shtml\" target=\"_blank\">郭树清：个人投资者为资本?场作出巨大贡献</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120607/170312252434.shtml\" target=\"_blank\">郭树清：基金公司要牢固树立客户为中心理念</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120607/170012252418.shtml\" target=\"_blank\">郭树清：推动机构投资者参与上市公司治理</a></li>\r\n			</ul>\r\n		</div>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('413', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end -->\r\n	<div class=\"img_wrapper\"><img alt=\"中国证监会主席郭树清在出席中国证券投资基金业协会成立大会后，接受本报记者申屠青南专访。本报记者 车亮 摄\" src=\"http://i0.sinaimg.cn/cj/money/fund/20120608/U5695P31T1D12254692F46DT20120608064206.jpg\" style=\"border:1px #000 solid;\" /> <span class=\"img_descr\">　　中国证监会主席郭树清在出席中国证券投资基金业协会成立大会后，接受本报记者申屠青南专访。本报记者 车亮 摄</span></div>\r\n	<!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12254692\' f_id=\'45\' -->\r\n	<p>　　<span style=\"font-family: KaiTi_GB2312,KaiTi;\">　　证监会主席郭树清在中国证券投资基金业协会成立大会上表示</span></p>\r\n	<p>　　<span style=\"font-family: KaiTi_GB2312,KaiTi;\">　　基金公司应加快向现代财富管理机构转型</span></p>\r\n	<p>　　<span style=\"font-family: KaiTi_GB2312,KaiTi;\">　　基金业协会第一次会员大会6日召开，孙杰当选为会长</span></p>\r\n	<p>　　◆证监会配合立法机关加快《证券投资基金法》修订，争取在拓宽基金公司业务范围、扩大基金投资标的、松绑投资运作限制等方面取得突破性进展</p>\r\n	<p>　　◆坚持从实体经济需要出发，通过设立专项资产管理计划，投资实体资产，扩大专户产品的投资范围，服务实体经济</p>\r\n	<p>　　◆要按照行为监管和分业监管的原则，重点解决好监管标准的协调适用，避免监管套利和监管真空</p>\r\n	<p>　　◆真正发挥行业协会作用，打破财富管理行业存在的条块分割、规模偏小、服务能力不足局面，大胆创新，引领行业闯出一条符合国情的财富管理业务发展之路</p>\r\n	<p>　　□本报记者 申屠青南<a href=\"http://weibo.com/u/1692651115?zw=finance\" onmouseover=\"WeiboCard.show(1692651115,\'finance\',this)\" target=\"_blank\">(微博)</a></p>\r\n	<p>　　中国证券投资基金业协会7日在北京召开成立大会暨第一届年会。中国证监会主席郭树清为基金业协会揭牌，并发表题为&ldquo;我们需要一个强大的财富管理行业&rdquo;的讲话。他表示，基金管理公司应当加快向现代财富管理机构转型。牢固树立以市场为导向、以客户为中心的经营理念。关键是根据客户偏好，既能够为大众投资者设计出一些可以跑赢通胀、风险又不太大的产品，也能给少数高端客户提供一些较高风险、较高收益的投资组合。证监会配合立法机关加快《证券投资基金法》的修订，争取在拓宽基金公司业务范围、扩大基金投资标的、松绑投资运作限制、优化公司治理、规范行业服务行为等方面取得突破性进展。</p>\r\n	<p>　　郭树清说，自1998年我国第一只证券投资基金诞生以来，短短14年里，中国的财富管理服务蓬勃发展起来。从封闭式基金到开放式基金，从信托计划到银行理财，从投资连接保险到天使基金和阳光私募，从各种各样的资产管理公司到林林总总的另类投资项目，都显示出勃勃生机，中国的金融业也因之而五彩缤纷、熠熠生辉。</p>\r\n	<p>　　然而，我们的财富管理行业总体上还处于幼年时期，不仅远远未能满足市场的需要，而且自身也存在种种的缺点与不足，各个领域都遇到严峻的挑战，同时也蕴藏着巨大的潜力。</p>\r\n	<p>　　郭树清指出，发展财富管理对经济转型具有根本意义。首先，是提高我国经济效率的需要。第二，是改善国民收入分配的需要。第三，是改善金融体系结构的需要。第四，是推进资本市场健康发展的需要。总之，财富管理行业在中国大有前途，正处于重要的发展机遇期。要从我国实际出发，借鉴国际经验，培育各种投资理财机构，让更多的居民和企事业单位等把富余资金交给机构来实施专业化管理，真正实现居民储蓄、资本市场、企业和社会事业的良性互动。</p>\r\n	<p>　　郭树清表示，基金管理公司应当加快向现代财富管理机构转型。当前，海外成熟市场的财富管理行业已经进入多元投资管理时代，而中国的基金行业则刚刚转型，差距还比较明显。基金管理公司要加快创新发展步伐，切实把握好以下几点：一是坚持从实体经济需要出发，通过设立专项资产管理计划，投资实体资产，扩大专户产品的投资范围，服务实体经济。二是认真学习、消化、吸收国外资产管理公司的业务模式和成功做法，深入分析自身的优势和劣势，明确自身的创新战略。三是重视分析研究工作，下大力气提高研究能力，不能完全依赖外部的研究报告，更不能靠小道消息、盲目跟风。四是坚持价值投资、长期投资理念，建立科学清晰的决策流程和管理制度，形成成熟稳定的投资风格，争取能够发挥对市场的引领作用。</p>\r\n	<p>　　郭树清认为，推进财富管理行业发展，根本在于牢固树立以市场为导向、以客户为中心的经营理念。千千万万的城乡居民、各种各样的社会经济组织，实际上有多种多样的财富管理需求，客观上呼唤多元化的理财服务和产品。关键是根据客户偏好，既能够为大众投资者设计出一些可以跑赢通胀、风险又不太大的产品，也能给少数高端客户提供一些较高风险、较高收益的投资组合。与海外机构相比，中国的财富管理行业除了知识、经验和人才不足之外，还缺乏品牌，缺乏值得市场和公众信赖的投资理财机构，缺乏人们可以将自有财产托付的，或大或小、或进取一点或审慎一些，但确实能够信守承诺，永远把客户放在第一位的金融企业。我们历史上有过的钱庄和票号，就是因为坚持&ldquo;信取天下&rdquo;、&ldquo;以义制利&rdquo;，构建出道德为本、人格为用、伦理约束为保障的信用与品牌体系，曾经创造出令人难以置信的辉煌。</p>\r\n	<p>　　他表示，财富管理行业各家机构的基础条件和客观环境各不相同，但有一些必须共同遵守的行为准则。一要讲求诚信，时刻把客户利益放在优先位置。二要强化信息披露，保证投资者能够按照约定及时、准确、完整地获取信息。三要注重创新，切实满足多样化的理财需求。四要合规运作，注意风险控制。从业人员特别是高管人员要坚守职业道德，忠诚于投资者的利益，敬畏于法律的威严，决不能触碰法律底线和道德底线。</p>\r\n	<p>　　郭树清强调，证监会将继续加强基础制度建设，不断优化有利于行业发展的外部环境。配合立法机关加快《证券投资基金法》的修订，争取在拓宽基金公司业务范围、扩大基金投资标的、松绑投资运作限制、优化公司治理、规范行业服务行为等方面取得突破性进展。同时，要按照行为监管和分业监管的原则，重点解决好监管标准的协调适用，避免监管套利和监管真空。</p>\r\n	<p>　　对于基金业协会的成立，郭树清说，我国亟待建设一个强大的财富管理行业，基金业协会的成立正当其时，相信协会一定能够大有作为。希望协会从成立开始，就能跳出基金业的圈子，牢牢把握财富管理行业的发展大趋势，引领行业发展方向，凝聚行业发展共识，共谋行业发展大局。要真正发挥行业协会作用，打破目前财富管理行业存在的条块分割、规模偏小、服务能力不足的局面，大胆创新，引领行业闯出一条符合国情的财富管理业务发展之路。协会要转变观念，回归行业协会的本性，突出服务宗旨；要包容开放，鼓励公平竞争，促进行业创新；加强行业管理，积极探索符合实际的自律方式和手段；勇于担当，切实负起行业和社会责任。协会要发挥作用，引导全行业从业人员牢固树立关心国家、热爱人民、回馈社会、共创和谐的理念，使财富管理行业成为履行社会责任的模范。</p>\r\n	<p>　　大会期间，中国证券投资基金业协会选举产生了第一届理事会、监事会，通过了协会章程、自律公约等相关文件，产生了协会领导班子。</p>\r\n	<p>　　中国证监会副主席刘新华，主席助理吴利军出席会议。民政部、财政部、人社部、住建部、人民银行、税务总局、银监会、保监会、全国社保理事会、外汇局以及北京市金融局、西城区和丰台区政府等单位代表，证监会机关各部门、各派出机构、系统各单位负责人及基金业协会会员代表400余人出席大会。</p>\r\n	<p>　　中国证券投资基金业协会第一次会员大会6日在北京召开，表决通过中国证券投资基金业协会第一次会员大会工作报告、《中国证券投资基金业协会章程》、《中国证券投资基金业协会会员管理办法》、《中国证券投资基金业协会会费收缴办法》和《中国证券投资基金业协会会员自律公约》，投票选举产生了协会第一届理事会、第一届监事会。</p>\r\n	<p>　　第一届理事会成员包括(以姓氏笔画为序)：于华、王立新、邓召明、田仁灿、刘建平、刘晓艳、孙杰、江先周、许小松、余晓晨、吴晓辉、李?、李爱华、杨东、杨新丰、陈敏、周月秋、尚健、林传辉、林利军、范勇宏、金旭、胡家夫、赵学军、郭特华、崔伟、曹殿义、韩勇、韩康。</p>\r\n	<p>　　会长：孙杰；专职副会长：韩康、曹殿义；兼职副会长(以姓氏笔画为序)：于华、刘晓艳、江先周、陈敏、周月秋、林利军、范勇宏、金旭、赵学军。</p>\r\n	<p>　　秘书长：韩康(兼)；副秘书长：钟蓉萨、汤进喜。</p>\r\n	<p>　　第一届监事会成员包括(以姓氏笔画为序)：刘树军、何宝、陈昌宏、周一烽、高良玉<a href=\"http://weibo.com/nfjjgaoly?zw=finance\" onmouseover=\"WeiboCard.show(1885257803,\'finance\',this)\" target=\"_blank\">(微博)</a>、裴长江。</p>\r\n	<p>　　监事长：高良玉；副监事长：刘树军。</p>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div id=\"blk_shareComm_1\" style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12254692\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12254692\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_1\">&nbsp;</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_2\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div class=\"corrTxt_01\">\r\n			<h3>\r\n				&gt; 相关专题：</h3>\r\n			<ul>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/focus/cnfundAssociation/\">中国基金业协会成立</a></li>\r\n			</ul>\r\n		</div>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('414', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end -->\r\n	<div class=\"img_wrapper\"><img alt=\"数据来源：WIND\" src=\"http://i3.sinaimg.cn/cj/money/fund/20120608/U5695P31T1D12254723F46DT20120608100621.jpg\" style=\"border:1px #000 solid;\" /> <span class=\"img_descr\">数据来源：WIND</span></div>\r\n	<!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12254723\' f_id=\'45\' -->\r\n	<p>　　<span style=\"font-family: KaiTi_GB2312,KaiTi;\">数量激增成常态 呼唤退出机制</span></p>\r\n	<p>　　□本报记者 曹淑彦</p>\r\n	<p>　　经过14年发展，国内基金数量即将迈过&ldquo;千只&rdquo;门槛。天相数据显示，截至6月7日，公募基金产品数量已经达到998只(A/B/C级、分级基金均合并计算，联接基金分开统计)，加上今日结束募集的两只基金，国内基金业即将迈入&ldquo;千基&rdquo;时代。</p>\r\n	<p>　　基金产品数量的不断增加，为投资者选择增添了难度，但也为专业化投资顾问提供了广阔的市场空间。对于行业来说，在基金同质化严重的情况下，基金&ldquo;破千&rdquo;预示着行业竞争日趋白热化，规模微小、无人问津的&ldquo;僵尸基金&rdquo;可能会越来越多。这意味着基金公司新基金发行&ldquo;重量不重质&rdquo;将难以为继，行业只有不断创新、差异化定位并完善退出机制，才能走得更远。</p>\r\n	<p>　　<strong>数量激增将成常态</strong></p>\r\n	<p>　　我国的基金产品从1998年底的5只增长至100只，用了5年的时间。从2007年开始，基金业进入爆发式增长，破百时间越来越短。基金数量从900只到突破1000只，只用了6个月时间。</p>\r\n	<p>　　有投资者担忧，基金产品数量的迅速增长是符合市场规律还是在盲目扩张？对此，有业内人士认为，从国外经验来看，快速增长是很正常的必经阶段。我国基金行业仍处于发展的初期，未来进入成熟期后可能会达到稳定增长速度。</p>\r\n	<p>　　&ldquo;从美国共同基金发展历史来看，我们确实步伐走得比较快&rdquo;，上海证券基金研究评价中心首席分析师代宏坤<a href=\"http://weibo.com/daihongkun?zw=finance\" onmouseover=\"WeiboCard.show(1635618475,\'finance\',this)\" target=\"_blank\">(微博)</a>说，&ldquo;从1只到800只共同基金，美国大约用了40年的时间，而我们只用了十几年。基金产品数量增长速度快也带来了一些问题，如产品结构仍以股票方向为主，产品同质化等&rdquo;。</p>\r\n	<p>　　广州越秀金融集团金融研究所研究员钟恒则指出，其实从不同发展阶段来看，美国共同基金在初期发展速度也很快，并且最开始产品结构也是以股票型基金为主，在经历了&ldquo;股灾&rdquo;后才逐渐推出货币基金等固定收益类产品。此外，近年来由于&ldquo;401K&rdquo;计划的支持作用，已经进入持续增长期的美国共同基金仍能实现在2008年依然获得资金净流入。&ldquo;我国的公募基金可能也会经历由初期到快速增长阶段，然后进入平稳增长的过程。&rdquo;钟恒说。</p>\r\n	<p>　　代宏坤表示，我国公募基金产品在短期内可能仍然会比较快速地增长。&ldquo;美国基金数量已经有8000多只了，而我国也就1000来只，未来基金数量还会扩张。&rdquo;</p>\r\n	<p>　　<strong>亟需专业化投顾</strong></p>\r\n	<p>　　无论对于投资者还是对于基金公司而言，基金数量的增加或许都是把双刃剑，尤其是达到上千这个数量级时。</p>\r\n	<p>　　上海证券基金研究评价中心首席分析师代宏坤分析，基金数量&ldquo;破千&rdquo;，对于投资者而言，一方面可选择的范围更大、类型更多，另一方面则是更难以抉择。他认为，对于具有投资经验的投资者，可以自己进行配置，例如国外有基金超市，并且不收取手续费，国内近日开始试营业的众禄基金正类似于基金超市；对于没有基金投资经验的投资者而言，就需要借助投资顾问的帮助。&ldquo;基金行业应重视发展专业投顾，目前这部分功能可能更多由基金公司承担，但是未来第三方可能会是这支队伍的主力。&rdquo;</p>\r\n	<p>　　在同质化严重的背景下，基金公司又怎样才能在茫茫基金中惹人注目呢？钟恒指出，要走出同质化困局，基金需要创新和明确市场定位。&ldquo;美国知名的基金都有着明确的定位，例如富达在创新方面较为擅长，拥有明星基金经理，先锋基金擅长指数化投资。而国内基金当前无论是产品还是营销模式都存在同质化现象。&rdquo;</p>\r\n	<p>　　钟恒表示，随着未来竞争压力的不断增加，基金公司会主动寻找突破和差异化。&ldquo;目前一些基金公司已经形成了一定的风格，例如富国基金<a href=\"http://weibo.com/fullgoal1999?zw=finance\" onmouseover=\"WeiboCard.show(1692038921,\'finance\',this)\" target=\"_blank\">(微博)</a>在量化指数增强方面做得很好，新华基金<a href=\"http://weibo.com/ncfund?zw=finance\" onmouseover=\"WeiboCard.show(1644059533,\'finance\',this)\" target=\"_blank\">(微博)</a>作为小基金公司专攻具有优势的权益类投资。&rdquo;</p>\r\n	<p>　　<strong>退出机制呼之欲出</strong></p>\r\n	<p>　　在基金数量激增的背后，一些隐藏在角落里的&ldquo;僵尸基金&rdquo;可能将越来越多，并成为相关基金公司难以承担的包袱。根据天相投顾数据，截至今年一季度末，规模在5千万到6千万的基金约16只，这些基金大多长期徘徊在清盘线附近，有的基金甚至已经低于5000万&ldquo;红线&rdquo;。业内人士表示，对于这些业绩长期不佳，规模持续缩水或没有增长的&ldquo;僵尸基金&rdquo;，相关基金公司顾及声誉，大多会通过自购、帮忙资金等方式维系其生存，但成本很高。在基金数量快速增加，同质化严重，行业竞争日趋激烈的情况下，要解决这些&ldquo;僵尸基金&rdquo;，就需要明确基金退出机制。</p>\r\n	<p>　　代宏坤表示，应该让这些&ldquo;僵尸基金&rdquo;被市场淘汰，这样基金行业才能实现健康的发展。钟恒指出，从美欧等发达市场经验来看，每一轮市场泡沫破裂或金融危机后，都会有很多基金被清盘或整合，基金数量也会下降。</p>\r\n	<p>　　华泰联合证券基金研究中心总经理兼基金研究总监王群航<a href=\"http://weibo.com/u/1413158753?zw=finance\" onmouseover=\"WeiboCard.show(1413158753,\'finance\',this)\" target=\"_blank\">(微博)</a>建议，对于那些规模、业绩均长期表现不佳的基金，基金公司可以考虑合并。尤其是由同一基金经理管理的基金，更加便于合并，从而提升持有人数量和净资产规模，避免踩线。某基金公司人士则表示，&ldquo;谁都不愿意做第一个清盘或合并的人，会损害公司的形象和声誉。&rdquo;也有基金公司表示，如果市场有先例的话，可能也会考虑对濒临清盘的基金进行合并。</p>\r\n	<p>　　目前已有基金采取了应对备案，汇添富理财60天的招募说明书中就出现了&ldquo;如果基金份额持有人数量连续60个工作日达不到200人，或连续60个工作日基金资产净值低于5000万元，本基金份额将自动转换为汇添富货币市场证券投资基金份额。&rdquo;业内人士认为，短期理财产品很难触发转换机制，但是确实是一个创新。</p>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div id=\"blk_shareComm_1\" style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12254723\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12254723\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_1\">&nbsp;</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_2\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div class=\"corrTxt_01\">\r\n			<h3>\r\n				&gt; 相关报道：</h3>\r\n			<ul>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120608/093912259751.shtml\" target=\"_blank\">美女基金经理受质疑 华泰柏瑞ETF月缩水52亿</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120608/044112256816.shtml\" target=\"_blank\">基金：股市下半年有望震荡向上</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120608/033912256454.shtml\" target=\"_blank\">人民日报：百姓理财呼唤基金创新</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120608/013112255389.shtml\" target=\"_blank\">新基金发行屡现延期 最长首募时间达80天</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120608/005912254741.shtml\" target=\"_blank\">基金经理扩军跟不上基金增长</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120608/005912254739.shtml\" target=\"_blank\">僵尸基金日趋增多 基金产品退出机制呼之欲出</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120608/005912254736.shtml\" target=\"_blank\">告别股基独大 基金产品走向多元</a></li>\r\n			</ul>\r\n		</div>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('415', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end --><!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12254731\' f_id=\'45\' -->\r\n	<p><span style=\"font-family: KaiTi_GB2312,KaiTi;\">　　谁造就了千只基金</span></p>\r\n	<p>　　□本报记者 田露</p>\r\n	<p>　　自1998年首批证券投资基金出现之后，基金数量于2008年开始快速提升，尤其是2011年年初来，每半年就会增加百只基金。在这样的速度背后，基金审批流程简化，基金发行的市场化起到了功不可没的作用。</p>\r\n	<p>　　不过，基金数量快速扩张在给基金行业带来机遇，为持有人带来更多投资选择的同时，能否推出吸引客户的创新品种以及与销售渠道间建立起良好合作关系，成为各基金公司面临的一大挑战。</p>\r\n	<p>　　基金数量加速扩容</p>\r\n	<p>　　根据WIND资讯统计，2003年10月末，公募基金的数量首次突破100只，此时距第一批基金涌现时的间隔约5年多。2005年9月时，基金数量达到了200只，与上一个&ldquo;100&rdquo;的间隔缩短为2年；此后在不到一年半的时间之内，基金数量又增加了100只。但受2007年股市狂飙和投资者认购基金狂热的影响，2007年监管部门对于新基金的审批节奏明显放慢，直到2008年4月时，基金数量才攀升到400只。</p>\r\n	<p>　　自此之后，直至2010年5月，几乎每隔一年，基金数量就再度增加百只。其后又再度加速，2010年5月直至2010年12月，7个月之内，基金数量从600只增加至700只，6个月之后，基金数量扩大到800只。接下来几乎以同样的频率，基金总数量分别于2011年底达到900只，并预计于今年6月达到1000只。</p>\r\n	<p>　　从6年、2年、1.5年、1年直至6个月，每百只基金成立的间隔时间日益缩短。对此，业内人士表示，基金数量迅速扩容有多方面的原因，近年来基金公司队伍日益扩大，各公司产品线日趋完善，以及老产品营销难度较大等均是其中因素，但基金审批效率日益提高、基金发行的市场化进程不断推进等是关键因素。</p>\r\n	<p><strong>　　未来市场竞争加剧</strong></p>\r\n	<p>　　某基金公司人士指出，自2008年开始，基金产品从申报到获得批复的流程明显缩短，原因是监管层针对偏股类产品、债券型基金以及QDII、创新产品等开始分条线审批，以往基金公司&ldquo;久旱逢甘霖&rdquo;的景象开始被&ldquo;紧锣密鼓发行战&rdquo;的节奏所代替。</p>\r\n	<p>　　&ldquo;以前整个行业都在呼唤市场化，但当市场化越来越贴近我们时，许多公司可能才发现还没有做好全部的准备。&rdquo;另一位基金公司人士如是说道。他指出，由于基金审批加快和同档期发行的基金产品数量增多，对于渠道、营销、客户乃至投研人员的资源争夺也日趋激烈，不少银行甚至形成了鼓励客户&ldquo;赎旧买新&rdquo;的怪圈，使许多基金公司感到压力沉重。</p>\r\n	<p>　　不过，该位人士也认为，基金审批加快，基金发行更趋市场化的趋势是无可阻挡的，而且对于一个产业的发展来说，过度保护和管制也难以促成产业真正强大。他指出，今年5月证监会开始向社会公开征求意见，拟修订《证券投资基金运作管理办法》，修改内容中就涉及到允许基金公司同时进行多只基金的募集申请。总体来说，基金公司面对这样的大趋势，更应从产品创新和渠道开拓等多方面来迎接未来的竞争局面。</p>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div id=\"blk_shareComm_1\" style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12254731\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12254731\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_1\">&nbsp;</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_2\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('416', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end --><!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12254736\' f_id=\'45\' -->\r\n	<p><span style=\"font-family: KaiTi_GB2312,KaiTi;\">　　谁构成了千只基金</span></p>\r\n	<p>　　□本报记者 黄淑慧</p>\r\n	<p>　　天相投顾统计显示，截至6月7日，国内公募基金产品总计998只，扣除36只联接基金之外的962只基金中，有900只为开放式基金，其中股票型基金447只，混合型基金175只，占比合计达七成，债券型基金、货币基金以及保本型基金分别为146只、51只、24只，共占24.56%；QDII基金57只，占6.33%。</p>\r\n	<p>　　长期以来，国内公募基金以股票型产品居多。随着近一两年来债券市场的快速发展，公募基金中固定收益类产品比重逐步提高，公募基金产品结构正逐步走向更为均衡和多元的格局。但值得注意的是，近几年基金平均规模不断下降，已由2007年顶峰时的94.67亿元，降至2011年底的24.55亿元。</p>\r\n	<p><strong>　　固定收益产品增多</strong></p>\r\n	<p>　　2001年开放式基金的出现，推动了基金数量爆发式增长，打开了中国基金业的成长空间，各类型基金大量出现。到2006年，开放式基金已初步形成了股票型、混合型、债券型、货币市场基金、保本基金以及QDII基金的总体框架。</p>\r\n	<p>　　一直以来，以权益类投资为主的股混型基金占据了绝对的主导地位。自2001年至2010年的十年间，股混型基金数目占所有开放式基金的比例均超过70%，2007年年末已经发展到123只，占比高达76.5%，直到2011年开始这一比例才下降到70%以下。</p>\r\n	<p>　　相比之下，债券型开放式基金自2002年起诞生，到2007年底仅有25只，在2008年A股熊市中才开始迅猛增长，当年年末达到61只。2011年以来，在大力发展直接融资和债券市场的大背景下，加之A股市场出现深幅调整，债券型基金再度受到了各家基金公司的重视。至2011年年末，债券型基金突破百只，达到了132只，今年以来又进一步增长到了146只。诞生于2003年的货币市场基金，发展也一直不温不火，在2007年、2008年两年中完全没有新发基金，到目前为止仅有51只。保本基金则在2011年快速发展，当年数目从5只直接跃升到了22只。</p>\r\n	<p>　　从资产管理规模来看，&ldquo;一股独大&rdquo;的局面也正在改变。天相投顾统计显示，在2007年A股牛市盛宴之后，股票型、混合型基金管理资产规模高达九成，但到今年一季度末管理资产规模已经下降到了73.27%。与此同时，债券型、货币型以及保本型基金的份额则从2007年的5.75%上升到了今年一季度末的23.88%。</p>\r\n	<p><strong>　　平均规模先扬后抑</strong></p>\r\n	<p>　　从公募基金产品发展历程来看，2001年到2007年的6年时间，基金产品发展的计划性比较强。在这种环境下，基金产品数量呈现稳步发展趋势。</p>\r\n	<p>　　在2007年股市暴涨中，由于监管层控制了新基金的发行，当年基金数目仅增加38只。当年346只基金共管理3.28万亿资产，单只基金管理规模94.67亿元，这也创下了单只基金管理规模的历史最高水平。</p>\r\n	<p>　　自2008年之后，新基金发行数目逐步上扬。不过，这段时间基金业一直处于缓慢发展甚至停滞的状态，基金平均管理规模日益萎缩，到2011年末单只基金仅管理24.55亿元。</p>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div id=\"blk_shareComm_1\" style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12254736\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12254736\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_1\">&nbsp;</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_2\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('417', '<!-- google_ad_section_start -->\r\n<div class=\"blkContainerSblkCon\" id=\"artibody\">\r\n	<div align=\"center\">&nbsp;</div>\r\n	<!-- 内容模块：单图 begin -->\r\n	<div class=\"moduleSingleImg01\"><!-- { 20080717股票图片显示 } --></div>\r\n	<!-- 内容模块：单图 end --><!-- publish_helper name=\'原始正文\' p_id=\'31\' t_id=\'1\' d_id=\'12254741\' f_id=\'45\' -->\r\n	<p><span style=\"font-family: KaiTi_GB2312,KaiTi;\">　　谁在管理千只基金</span></p>\r\n	<p>　　□本报记者 黄莹颖</p>\r\n	<p>　　基金业已走过十几年的时间，资产规模从几亿到今天的数万亿。虽然基金数量和基金经理人数比翼齐飞，但近几年基金经理的人数增长却明显落后于基金数量的增长。事实上，由于基金业人才培养的乏力，以及人才流失程度的逐步加剧，基金经理&ldquo;一拖多&rdquo;现象已在逐步增加。</p>\r\n	<p><strong>　　基金经理&ldquo;一拖多&rdquo;增加</strong></p>\r\n	<p>　　WIND数据显示，截至6月7日，基金总数已由1998年的5只增加到目前的998只。从2008年开始，基金数量增速平稳，保持在每年增长26%到30%之间。</p>\r\n	<p>　　与此同时，基金经理的人数由1998年的8人，增加到目前的733人。从增长率看，从2008年开始，基金经理的人数增长大致保持在18%左右。</p>\r\n	<p>　　从近四年的情况看，基金经理人数的增长幅度明显跟不上基金的增长。2008年，基金数量增长26.88%，而基金经理人数增长仅为16.58%，落后近10个百分点。此后的三年，2009到2011年，落后幅度仍然在9个百分点左右。</p>\r\n	<p>　　从&ldquo;基金数量/基金经理人数&rdquo;这一比值变化看，2008到2011年该比值分别为1.04、1.12、1.19、1.30，呈现逐年递增的情况。这意味着，由于人才跟不上，平均起来单个基金经理管理基金的数量在逐步增加，基金经理的工作压力呈现加大的趋势。</p>\r\n	<p>　　从具体基金公司看，华夏基金<a href=\"http://weibo.com/chinaamc1998?zw=finance\" onmouseover=\"WeiboCard.show(1402596635,\'finance\',this)\" target=\"_blank\">(微博)</a>、财通基金<a href=\"http://weibo.com/ctfund?zw=finance\" onmouseover=\"WeiboCard.show(2098033732,\'finance\',this)\" target=\"_blank\">(微博)</a>在&ldquo;基金数量/基金经理人数&rdquo;比值最小，分别为0.87、0.67，这也意味着这两家公司在基金经理上占据了相对优势。而这一比值超过2的基金公司也有三家，数据显示，这三家公司平均每个基金经理管理基金数量在2只以上。从管理基金产品的种类看，基金经理&ldquo;一拖多&rdquo;现象主要集中在指数型基金和债券基金。</p>\r\n	<p><strong>　　人才流动进一步加剧</strong></p>\r\n	<p>　　一方面是基金经理培养的滞后，另一方面则是基金业的人才流动进一步加剧，基金公司的投研人员转投私募、保险、信托，或回流券商资管部门比较普遍。</p>\r\n	<p>　　根据晨星统计，截至2012年2月底，最近一年公募基金行业共计发生基金经理变动200次，与去年同期的149次和2010年同期的94次相比，公募基金经理换将的频次进一步放大。其中，股票型基金的基金经理变动达97次，混合型55次，包含货币基金的固定收益型41次，其余为保本型和分级基金。更换基金经理时的人均任职年限中位数1.47年，平均值为一年半；对应的去年的人均任职年限中位数为1.67年，均值为1.77年，基金经理离职前的平均任职时间也明显缩短。晨星(中国)车小婵指出，除一线投资人才的流失外，公募基金还面临着管理层与研究团队的人才荒，经验丰富、创造价值高的研究员尤其稀缺。</p>\r\n	<p>　　值得注意的是，最近四年，虽然基金整体业绩表现不佳，但是在基金总数和总份额数上却又较大幅度的增长。WIND统计数据显示，2011年基金资产净值总规模同比上年减少13.2%，而基金总数和基金资产份额总规模却分别增长30%、9.42%。人才培养的相对滞后，对基金业绩的长期影响未来将进一步显现。</p>\r\n	<div style=\"clear:both;height:0;visibility:hiddden;overflow:hidden;\">&nbsp;</div>\r\n	<div class=\"blkComment otherContent_01\" id=\"sinashareto\" style=\"margin-right:0px;margin-left:0;padding-right:0px;\">\r\n		<div id=\"blk_shareComm_1\" style=\"font-size: 14px;line-height: 23px;margin: 15px 0;\"><span style=\"font-size:12px;\">分享到:</span> <script type=\"text/javascript\">\r\n//<![CDATA[\r\n var sendT = {\r\n	getHeader : function(){\r\n		var g_title=document.getElementById(\"artibodyTitle\").innerHTML;\r\n		var re = /<[^<>]*?font[^<>]*?>/gi;\r\n		g_title = g_title.replace(re,\"\");\r\n\r\n		return g_title;\r\n	},\r\n	getFirstImgSrc : function(){\r\n		if (document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0] && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].width > 100 && document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].height > 100)\r\n		{\r\n			return document.getElementById(\"artibody\").getElementsByTagName(\"img\")[0].src;\r\n		}\r\n		else\r\n		{\r\n			return null;\r\n		}\r\n\r\n	},\r\n	getIntro : function(){\r\n		var intro = \'\',t=\'\';\r\n		var para = document.getElementById(\"artibody\").getElementsByTagName(\'p\');\r\n		for(var i=0;i<para.length;i++){\r\n			t = para[i].innerHTML.replace(/(\\<.*?\\>)|\\ |[\\t\\n\\r ]/ig,\'\');\r\n			if(t.length>30){\r\n				intro = t.length>180?t.substr(0,180)+\'...\':t;\r\n				break;\r\n			};\r\n		};\r\n		return intro;\r\n	}\r\n}\r\n document.getElementById(\"fxwb\").onclick=function(){\r\n (function(s,d,e,r,l,p,t,z,c){\r\n	 var f=\'http://v.t.sina.com.cn/share/share.php?\',u=z||d.location,p=[\'url=\',e(u),\'&title=\',e(sendT.getHeader()),\'&ralateUid=1638782947\',\'&source=\',e(r),\'&sourceUrl=\',e(l),\'&content=\',c||\'gb2312\',\'&pic=\',e(p||\'\')].join(\'\');\r\n	 function a(){if(!window.open([f,p].join(\'\'),\'mb\',[\'toolbar=0,status=0,resizable=1,width=440,height=430,left=\',(s.width-440)/2,\',top=\',(s.height-430)/2].join(\'\')))u.href=[f,p].join(\'\');\r\n	 };\r\n	 if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();\r\n})(screen,document,encodeURIComponent,\'新浪财经\',\'http://finance.sina.com.cn/\',sendT.getFirstImgSrc(),null,null,null);\r\n	GB_SUDA._S_uaTrack(\'31\',\'c_share_tblog\');\r\n};\r\n(function(){\r\n	document.getElementById(\"fxMSN\").onmousedown = function(){\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=msn\';\r\n		this.href = \"http://profile.live.com/badge?url=\" + e(url) + \"&title=\" + e(t) + (p?\"&screenshot=\" + e(p):\"\") + (it?\"&description=\" + e(it):\"\");\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_MSN\');\r\n	};\r\n\r\n//2011/1/27\r\n	document.getElementById(\'fxfx\').onclick=function () {\r\n		var t = sendT.getHeader(),p = sendT.getFirstImgSrc(),it = sendT.getIntro(),e = encodeURIComponent;\r\n		var url = window.location.href.replace(/\\?.*/,\'\') + \'?from=fetion\';\r\n		var source=\'新浪财经\';\r\n		this.href = \"http://space.feixin.10086.cn/api/sharenews?url=\" + e(url) + \"&title=\" + e(t) +\"&source=\"+e(source);\r\n		GB_SUDA._S_uaTrack(\'31\',\'c_share_fx\');\r\n	}\r\n\r\n})();\r\n//]]>\r\n</script><span id=\"cmnt_count\"><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12254741\" style=\"font-size:12px;background:url(http://i1.sinaimg.cn/dy/news/2008/0708/con_bg_standard_001.gif) no-repeat 0 -99px;padding-left:17px;\">欢迎发表评论</a>&nbsp;&nbsp;</span><a href=\"http://comment5.news.sina.com.cn/comment/comment5.html?channel=cj&amp;newsid=31-1-12254741\" style=\"font-size:12px\">我要评论</a></div>\r\n	</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_1\">&nbsp;</div>\r\n	<div class=\"otherContent_01\" id=\"blk_otherContent_2\" style=\"overflow:hidden;zoom:1;\">\r\n		<style type=\"text/css\">\r\n.corrTxt_01{border-top:1px dashed #C8D8F2;margin-top:-1px;}\r\n	.corrTxt_01 h3{font-weight:bold;padding:5px 0 0 3px;line-height:25px;margin:0;}\r\n	.corrTxt_01 ul{padding:0 0 20px 18px;}\r\n	.corrTxt_01 ul li{font-size:14px;line-height:164.28%;}		</style>\r\n		<div class=\"corrTxt_01\">\r\n			<h3>\r\n				&gt; 相关报道：</h3>\r\n			<ul>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120608/093912259751.shtml\" target=\"_blank\">美女基金经理受质疑 华泰柏瑞ETF月缩水52亿</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120608/044112256816.shtml\" target=\"_blank\">基金：股市下半年有望震荡向上</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120608/033912256454.shtml\" target=\"_blank\">人民日报：百姓理财呼唤基金创新</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120608/013112255389.shtml\" target=\"_blank\">新基金发行屡现延期 最长首募时间达80天</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120608/005912254741.shtml\" target=\"_blank\">基金经理扩军跟不上基金增长</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120608/005912254739.shtml\" target=\"_blank\">僵尸基金日趋增多 基金产品退出机制呼之欲出</a></li>\r\n				<li>\r\n					<a href=\"http://finance.sina.com.cn/money/fund/20120608/005912254736.shtml\" target=\"_blank\">告别股基独大 基金产品走向多元</a></li>\r\n			</ul>\r\n		</div>\r\n		<div id=\"script_lodaer\">&nbsp;</div>\r\n		<div class=\"corrTxt_01\" id=\"interrelatedBlog\">&nbsp;</div>\r\n		<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/cj/finance_js/zhengwen/blog_other_3.js\"></script></div>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('418', '\r\n\n		<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\">\n\n    <div class=\"img_wrapper\">\n<img  src=\"/u/cms/www/201307/08153350iin5.jpg\" alt=\"苏越的女友安雯接受记者采访。 程宁摄\" title=\"苏越的女友安雯接受记者采访。 程宁摄\"/>\n<span class=\"img_descr\">苏越的女友安雯接受记者采访。 程宁摄</span>\n</div>\n\n<!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27587682\' f_id=\'3\' -->\n\n<p>　　本报讯(记者邱伟)历时3年零3个月的司法程序，著名音乐人苏越今天等到了自己的最终刑罚：北京市高级法院终审驳回了检方要求顶格判处苏越无期徒刑的抗诉意见，维持了北京市第二中级法院的一审判决：以合同诈骗罪判处苏越15年有期徒刑。&nbsp;</p>\n\n<p>　　苏越合同诈骗5000多万案可谓一波三折，他先是被判无期徒刑，后不服上诉，高院裁定发回重审；重审改判15年后，检方又提起了抗诉。今天上午宣判前，记者见到了苏越的辩护律师陈旭和李维强，他们告诉记者，苏越最近都在焦急地等待宣判，心中一直非常忐忑。</p>\n\n<p>　　安雯今天到达法院有些晚，她看上去略显憔悴。安雯说，她昨天还在外地紧张工作，为了赶今天的宣判，她连夜回京，凌晨3点才到家，只睡了一个小时就赶来法院。“最后的时刻，一定会和苏越在一起。”辩护律师告诉记者，苏越在看守所里最牵挂的就是安雯，“以前都是苏越安排安雯的生活，现在没人照顾安雯，苏越非常挂念，律师会见时，苏越经常会提及。”</p>\n\n<p>　　据了解，苏越与安雯多年来一直以夫妻相待，但两人并未在中国正式办理结婚手续。今天宣判，安雯的大姐、二姐以及姐夫等六七位家人都来到了法院。安雯的大姐告诉记者，他们一家子有的从美国赶回来，有的从日本赶回来，就是想表示对苏越和安雯的支持。“我们一家人都很认可苏越，他是犯了罪，但毕竟他不是一个坏人。”</p>\n\n<p>　　苏越的辩护律师陈旭对记者评价说，苏越是“书生做生意，做赔了，还死要面子，结果落到今天这个地步。此案确有特殊之处，不能等同于普通的刑事诈骗案件。”</p>\n\n<p>　　上午9时许，宣判结束，陈旭律师向等候在外的媒体宣布，法院驳回了检方抗诉，维持了一审15年有期徒刑的判决。</p>\n\n<p>　　记者看到，北京市高级法院的裁定书采纳了律师的辩护意见，认为本案的发生系事出有因。“苏越作为单位直接负责的主管人员，在因经营不善，资金短缺而出现巨额亏损的情况下，为弥补单位亏空、归还他人欠款而实施了合同诈骗行为，故本案案发确属事出有因。”</p>\n\n<p>　　法院认为，苏越实施合同诈骗犯罪的主观恶性不是很大。尽管苏越合同诈骗的数额确属特别巨大，但其将所骗取的款项均用于偿还公司的借款和日常经营，没有进行挥霍；在被害单位向公安机关举报苏越涉嫌合同诈骗前后，苏越均在偿还被害单位的欠款；在苏越被羁押后，部分被害单位和个人还通过民事诉讼以及执行程序挽回了财产损失，虽然法院执行的对象是其女友安雯的房产，但由于该民事诉讼所涉及的财产与刑事诉讼中指控苏越诈骗的事实重合，故可认定为诈骗款项已被追缴。在此过程中，苏越始终未逃避法律责任，其与亲友亦未隐匿财产，说明其主观恶性不是很大。</p>\n\n<p>　　法院认定，苏越在归案后能够如实供述犯罪事实，真诚认罪。在法院审理期间，苏越的女友安雯主动代其退赔人民币100万元，此外苏越还具有坦白情节，他在被羁押后，主动交代了公安机关尚未掌握的其诈骗于某的犯罪事实。</p>\n\n<p>　　法院综合认为，尽管苏越实施合同诈骗犯罪数额特别巨大，给部分被害单位和个人造成重大经济损失，但由于苏越同时具有如实供述并主动坦白部分犯罪事实、积极退赔赃款、认罪悔罪态度好等法定和酌定等从轻处罚情节，对苏越从轻处罚符合法律规定，一审法院对其判处有期徒刑15年，量刑并无不当，检方的抗诉意见不能成立，法院终审裁定驳回检方的抗诉，维持原判。</p>\n\n<p>　　宣判后，安雯走出法院时对记者表示，希望苏越能好好感恩，好好保重身体。“对于这样一个结果，我无法用语言表达，就是感恩和感谢，我一直相信司法是公正的。”&nbsp;&nbsp;陈旭律师告诉记者，在法庭上听到结果后，苏越很高兴。此次法院的二审裁定直接生效，苏越应该很快会转往监狱开始正式服刑。从被刑拘到现在，苏越已被羁押了3年多，他的15年刑期还有11年多，希望苏越能在狱中获得减刑的机会。</p>\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('419', '\r\n\n		<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\">\n\n<!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27588689\' f_id=\'3\' -->\n\n<p>　　人民网宜州7月5日电 (记者林婉仪 通讯员吴镇全、吴文)“太不值了，扒20元判7个月，还罚1千，出去后再也不能干这个了”。7月5日上午，因扒窃20元而被一审判处有期徒刑7个月、并处罚金1千元的两名男子银某华和刘某忠在宜州市看守所里懊悔不已。</p>\n\n<p>　　据检察机关指控，2013年5月26日下午，被告人银某华伙同刘某忠在宜州市区龙江路一桥头路段到处游荡，寻找合适下手的人群。这里他们发现一名未成年男生路过，决定先从“小朋友”下手。过程中，刘某忠负责望风，银某华用事先准备好的一把大号镊子将受害人衣服左边口袋内的钱扒走，结果发现仅有20元人民币，二人随后逃离现场。不料没过多久即被民警抓获被追回，并将扒来的20元返还失主。</p>\n\n<p>　　法院审理认为，被告人银某华、刘某忠以非法占有为目的，在公共场所扒窃他人财物，其行为已触犯《中华人民共和国刑法》第264条之规定，构成盗窃罪。两被告在共同犯罪中，银某华起主要作用，是主犯，应当按照其所参与的全部犯罪处罚。刘某忠起次要作用，是从犯，应当从轻处罚，刘某忠曾因犯盗窃罪被判有期徒刑，刑罚执行完毕以后五年内再犯应当判处有期徒刑以上的刑罚之罪，是累犯，应当从重处罚。根据两被告犯罪的事实、性质、情节和对社会的危害程度，依照《刑法》的有关规定，宜州市人民法院于2013年7月1日作出上述判决。</p>\n\n<p>　　据了解，2011年5月1日《刑法修正案(八)》实施后，规定了“扒窃入刑”，即只要实施了扒窃行为，无论数额多少，都要追究刑事责任。据悉，“扒窃入刑”大大增加了扒手的犯罪成本。</p>\n\n<p align=\"right\">(原标题：广西宜州2男子扒窃20元被判7个月罚1千怒改行)</p>\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('420', '\r\n\n		<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\">\n\n<!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27590670\' f_id=\'3\' -->\n\n<p>　　河北新闻网讯(通讯员韩文江、张宏立)因为缺钱花，几名无业青年结伙抢劫超市，得手后还将超市老板杀害。6月27日，白沟新城公安分局连续奋战六昼夜，成功侦破“621”抢劫杀人案，抓获犯罪嫌疑人崔某、郝某。</p>\n\n<p>　　6月21日下午5时30分，白沟新城公安分局指挥中心接刘某报案称：其老乡何某在白沟镇富强路一超市内被杀。接警后，民警立即赶赴现场。经勘查：死者何某死亡时头部、嘴部均有血棘头部有明显钝器伤，初步确定为他杀。</p>\n\n<p>　　白沟新城公安分局抽调刑警大队、高桥派出所精干警力组成“621”专案组，全力开展案件侦破工作。专案组对现场进一步勘查，提取痕迹物证，并对尸体进行检验；以案发现场为中心，对目击者、案发附近旅馆、网吧、饭店等门店逐一走访；调取录像信息、顺线追踪，并对死者何某的社会背景关系、近期活动情况展开摸排。</p>\n\n<p>　　专案组民警经大量走访、辨认工作，摸排出崔某、郝某有重大作案嫌疑，但此二人已负案在逃。6月26日，专案组民警最终锁定两名犯罪嫌疑人藏匿在甘肃省天水市北道区一旅馆内。</p>\n\n<p>　　6月27日上午，在甘肃天水市警方的大力协助下，专案组民警成功将崔某、郝某两名犯罪嫌疑人抓获。通过突审，犯罪嫌疑人崔某、郝某对6月21日凌晨1时许，伙同刘某在白沟镇一超市内抢劫现金2000余元并用砖头将超市老板何某打死的犯罪事实供认不讳。</p>\n\n<p>　　目前，犯罪嫌疑人崔某、郝某已被白沟新城公安分局刑事拘留，此案另一名罪嫌疑人刘某的抓捕工作正在进行中。</p>\n\n<p align=\"right\">(原标题：无业青年抢劫超市杀害老板 白沟破获“621”抢劫杀人案)</p>\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('421', '\r\n\n		<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\">\n\n<!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27590671\' f_id=\'3\' -->\n\n<p>　　河北新闻网讯(通讯员李红宇、刘焕江)外出游玩时从地摊买来仿真手枪，却用来袭击他人车辆从中取乐，到头来还是搬起石头砸自己的脚。7月3日，昌黎县检察院对一起持仿真手枪袭击车辆寻衅滋事案件的犯罪嫌疑人赵兵依法批准逮捕。</p>\n\n<p>　　赵兵，男，19岁，昌黎人，中专毕业后一直待业在家，整日无所事事的他不思找一份正式工作，却跟社会上的一群“朋友”厮混在一起。5月28日，赵兵与几个朋友驾车去滦县游玩，期间购买了一支仿真手枪及子弹若干。当晚，几人驾车返回昌黎。行车途中，赵兵感觉前方的一辆红色面包车车速过慢影响了自己超车，心中大为不快，在几个“好朋友”的撺掇下，他决定教训一下面包车司机。在逐渐近面包车后，赵兵与两个朋友拿出仿真手枪，轮番对面包车进行射击，致面包车右、后侧车窗玻璃被击碎。惊慌失措的面包车司机冯某迅速逃离，却没想到还是没能摆脱赵兵等人的纠缠与挑衅——赵兵一伙驾车继续狂追冯某十余公里。</p>\n\n<p>　　第二天晚上，赵兵独自驾车外出，途中又以对方车灯太亮晃了自己眼睛为由，再次持“枪”对受害人魏某驾驶的白色轿车进行射击，其后又对慌忙逃离的魏某展开追逐，一直追到某路口交警执勤处方才罢手。</p>\n\n<p>　　接到报案后，昌黎警方迅速出动，很快将赵兵抓获归案。受理此案的昌黎县检察院经审查认为，赵兵夜间在公共道路上持仿真手枪袭击并肆意追逐车辆，严重破坏社会秩序，情节恶劣，其行为已涉嫌寻衅滋事罪，遂依法将其批准逮捕〃文中人物为化名)</p>\n\n<p align=\"right\">(原标题：昌黎一男子持仿真手枪袭击车辆寻衅滋事被批捕)</p>\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('422', '\r\n\n		<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\">\n\n<!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27592236\' f_id=\'3\' -->\n\n<p>　　<strong>温州一男子猥亵工友幼女，孩子反抗时竟被捂死</strong></p>\n\n<p><strong> </strong></p>\n\n<p><strong>　　9岁“小候鸟”</strong></p>\n\n<p><strong> </strong></p>\n\n<p><strong>　　惨遭禽兽厨师毒手</strong></p>\n\n<p>　　□通讯员 龙公宣 本报记者 解亮</p>\n\n<p>　　早报讯 昨天，网上一直在传：温州市中心某知名酒店的厨师，“强奸一个10来岁的小女孩致死，已潜逃。她的妻子在家中发现女孩尸体后报了案。”</p>\n\n<p>　　这件事传得沸沸扬扬，网友也在不断补充。有的说，遇害的小女孩原本在老家读书，放暑假后刚来温州，是典型的“小候鸟”，不料惨遭毒手；也有人说，嫌犯虽外逃，但已被警方抓获，等等。</p>\n\n<p>　　除了求证，更多网友都在问，倘若这是真的，人性怎会如此泯灭，嫌犯怎会如此残忍？</p>\n\n<p>　　昨晚，龙湾警方证实，当地确实有一名外地来温的小女孩遇害，她叫小毛（化名），只有9岁。</p>\n\n<p>　　嫌犯施某（四川峨眉山市人，47岁）承认对她进行猥亵，为了阻止她反抗喊叫，下了杀手。目前，施某已被抓获，具体案情还在进一步调查中。</p>\n\n<p>　　遇害的小毛原本在老家读书，因父母在温州打工，放假后，来到父母身边。“才过来两天，没想到就出事了。”一名知情人士说。</p>\n\n<p>　　小毛的父母和施某同在一家酒店工作，互相有些交往。正是这层关系，让施某有机会接触到小毛。</p>\n\n<p>　　事情发生在今年7月3日。上午，施某还带小毛去图书馆玩了一会。</p>\n\n<p>　　上述知情人士说，中午，上述酒店一帮人被某单位叫去做饭，小毛的父母也被派过去，很忙，施某又借机把小毛骗了出来。</p>\n\n<p>　　施某把小毛带到蒲州街道上庄一幢民房4楼，这里是他的妻子雷某的暂住处。</p>\n\n<p>　　施某和妻子感情不好，两人多数时间不住一起，不过，他偶尔也会去妻子那里，所以手里有钥匙。</p>\n\n<p>　　在出租房里，丧心病狂的施某对小毛实施猥亵。小毛反抗并喊叫，“施某就用手将她嘴巴捂住，致使其窒息死亡。”警方相关人士说。</p>\n\n<p>　　案发后，施某立即开始潜逃。他还打电话给其妻子，说他杀了人，尸体就放在出租房的储物柜中。他希望妻子能给他点路费，好让他跑路。7月3日下午3点，施某妻子向警方报案。</p>\n\n<p>　　龙湾警方通报，接报后，他们立即赶往现场，很快在储物箱内发现了小女孩的尸体。</p>\n\n<p>　　前天上午10点，警方发现施某在丽水火车站，购买了第二天从丽水至贵阳的火车票。很明显，他正打算再次潜逃。警方派人赶到丽水，当天下午1点多，施某在莲都区一招待所被警方抓获。</p>\n\n<p>　　经审讯，施某对杀害小毛的犯罪事实供认不讳。目前，他已被依法刑拘。</p>\n\n<p align=\"right\">（原标题：9岁“小候鸟”惨遭禽兽厨师毒手）</p>\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('423', '\r\n\n		<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\">\n\n<!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27593240\' f_id=\'3\' -->\n\n<p><link rel=\"stylesheet\" href=\"http://news.sina.com.cn/css/87/20121024/style_videolist2.css\" />\n\n                  <!--[if !IE]> 正文视频小点大 <![endif]-->\n                    <div class=\"article-player-item-wrap blk_ntchack1\">\n                        <div class=\"a-p-i-small J_Play_Item\" play-data=\"108986728-1-1\" url-data=\'http://video.sina.com.cn/p/news/c/v/2013-07-06/130962638467.html\' title-data=\'视频：男子抢公交方向盘 涉危害公共安全被批捕\' source-data=\"CCTV新闻频道\">\n                            <a href=\"javascript:\" class=\"a-p-i-img\" hidefocus=\"true\" title=\"视频：男子抢公交方向盘 涉危害公共安全被批捕\">\n                                <img alt=山东抢夺公交方向盘嫌犯被批捕警方重新定性 width=\"290\" height=\"218\" action-data=\"showbtn=0\" src=\"/u/cms/www/201307/08153355c316.jpg\" alt=\"视频：男子抢公交方向盘 涉危害公共安全被批捕\" /> <i class=\"a-p-i-play\">play</i>\n                                <p class=\"a-p-i-txt\">视频：男子抢公交方向盘 涉危害公共安全被批捕</p>\n                            </a>\n                        </div>\n                    </div>\n                    <!--[if !IE]> /正文视频小点大 <![endif]-->\n\n      <script type=\"text/javascript\" src=\"http://news.sina.com.cn/js/87/20121024/videolist2.js\"></script>\n      <script type=\"text/javascript\">\n        var sinaBokePlayerConfig_o = {\n            container: \"J_Article_Player\",  //Div容器的id\n			width:540,\n			height:441,\n            playerWidth: 540,   //宽\n            playerHeight: 441,    //高\n            autoLoad: 1,  //自动加载\n            autoPlay: 1,  //自动播放\n            as: 1,  //广告\n            pid: 1,\n            tjAD: 0,  //显示擎天柱广告\n            tj: 0,  //片尾推荐\n            continuePlayer : 1, //连续播放\n            casualPlay: 1,  //任意拖动视频\n            head: 0,  //播放片头动画\n            logo: 0,  //显示logo\n            share :0,\n            thumbUrl: \"\"\n          };\n      </script>\n      <script src=\"http://video.sina.com.cn/js/sinaFlashLoad.js\" charset=\"utf-8\" type=\"text/javascript\"></script>\n      <script type=\"text/javascript\">\n      var VideoList1 = new ArticalVideoList2(\'artibody\');\n      VideoList1.init();\n      function playCompleted(){\n        VideoList1.play();\n      }\n      </script>　　【坐过站就抢公交车方向盘？被定性为涉嫌危害公共安全！】6月24日，山东淄博89路公交上，乘客王某因坐过站，突然抢夺司机的方向盘，载着乘客的公交车差点撞上栏杆！原本王某被处以行政拘留10天的处罚，后经公安机关调查，最终抢方向盘者被按\"涉嫌危害公共安全罪\"提请批捕。</p>\n\n<p align=\"right\">(原标题：坐过站就抢公交车方向盘？被定性为涉嫌危害公共安全！)</p>\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('424', '\r\n\n		<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\">\n\n    <div class=\"img_wrapper\">\n<img  src=\"/u/cms/www/201307/08153356s1ct.jpg\" alt=\"图为：徐大远的亲属悲痛欲绝 记者姚品摄\" title=\"图为：徐大远的亲属悲痛欲绝 记者姚品摄\"/>\n<span class=\"img_descr\">图为：徐大远的亲属悲痛欲绝 记者姚品摄</span>\n</div>\n\n<!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27595365\' f_id=\'3\' -->\n\n<p>　　荆楚网消息(记者高东起 伍迪 特约记者王继斌) 昨日上午8时许，两名陌生男子骑摩托车窜至随州一个村庄，用安装有毒针的弓弩将狗射死。狗主人发现后出门追赶，盗狗贼竟将毒针射向主人，并用喷雾器喷洒有毒气雾，致一人当场死亡一人中毒。作案后，两盗狗贼骑车逃跑，当地警方正在全力捕凶。</p>\n\n<p>　　昨日上午7时55分左右，随州市曾都区南郊洪山头村2组，村民徐军正在屋里收拾东西，忽然听到外面有人吵架，于是赶紧出去。只见院子里他家的小狗躺在地上不能动弹，其身体上插着一个像针管一样的东西。两名陌生男子则和父亲徐大远吵架，随后跨上一辆摩托车准备逃跑。徐军和父亲一边追赶一边开骂，一直追到一片树林中。不料，对方恼羞成怒，突然转身对徐家父子进行人身攻击。其中一名男子，手持弓弩，扣动扳机，一支毒针飞速射到徐大远的胳膊上，不到一分钟徐大远便倒在地上。</p>\n\n<p>　　徐军见状追赶对方，该男子竟然又用弓弩吓唬徐军，另一男子则拿出一个像消防灭火器一样的装置，对着他的脸部喷出一股浓烈的气雾。徐军转身躲闪，气雾喷到了他的后脑勺和背上，他顿时感到像烧伤一样疼痛难忍，整个人差点晕倒，等他缓过神来，对方已骑着摩托车扬长而去。</p>\n\n<p>　　邻居闻讯赶来，紧急拨打120和110。约10分钟后，民警和医生赶到。此时，徐大远脸色惨白，胳膊上显出异样的颜色，医生当场确认其已经死亡。神情恍惚的徐军被送到随州市中心医院抢救。</p>\n\n<p>　　医生介绍，徐军所中的这种毒，随时可能会发作，严重的会引起呼吸衰竭甚至死亡，目前尚不能判断他是否已度过危险期。</p>\n\n<p>　　昨日下午记者现场见到，徐大远的遗体已安放进灵柩中，亲人和乡亲抽泣着为他送行。该村58岁的徐光庆介绍，事发时他骑着自行车准备去买酒，忽然看见两个陌生人在树林里被徐家父子追赶，徐大远被弓弩射中，疼痛难忍抱着一棵大树，一分钟不到就倒在地上。</p>\n\n<p>　　多位村民介绍，夜里经常有陌生人潜入村庄盗狗，像这样在光天化日之下作案还是第一次。记者调查发现，在316国道随州段，经常可以看见有人在路边销售弓弩。</p>\n\n<p>　　徐大远的大儿子，昨日从深圳飞回随州。他抽泣着说：“希望警方尽快抓到凶手，查清弓弩、剧毒的来源，还父亲一个公道！”</p>\n\n<p>　　据了解，两名盗狗贼年龄均在30岁左右，身高约1.7米，随州口音，骑一辆红色摩托车。</p>\n\n<p>&nbsp;</p>\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('425', '\r\n\n		<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\">\n\n    <div class=\"img_wrapper\">\n<img  src=\"/u/cms/www/201307/081533578uib.jpg\" alt=\"现场遗留的一双血迹斑斑的男式拖鞋 本报记者田东海摄\" title=\"现场遗留的一双血迹斑斑的男式拖鞋 本报记者田东海摄\"/>\n<span class=\"img_descr\">现场遗留的一双血迹斑斑的男式拖鞋 本报记者田东海摄</span>\n</div>\n\n<!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27597074\' f_id=\'3\' -->\n\n<p>　　本报咸阳讯(记者张林赵方) 29岁的牛某无意间发现，丈夫与32岁的女网友史某有暧昧关系，她先是打电话责骂，气愤不过又打车从兴平赶到咸阳史某住处。7月5日，两人在小区楼下争吵，推搡中，牛某持刀刺向史某脖子，瞬间鲜血喷出。</p>\n\n<p>　　<strong>男子赶到抱起受伤女子</strong></p>\n\n<p>　　5日晚7时许，在事发的咸阳市人民西路彩虹新区家属区，仍留有大量被扯掉的头发，血迹从事发现场一直到小区门外，长达数十米。</p>\n\n<p>　　一名目击者说，当晚6时40分许，两名女子均30岁左右，穿黑色衣服，当时听见两名女子相互叫骂，说着很难听的话，间或听到“勾引”、“破坏家庭”等词语。</p>\n\n<p>　　“把我能吓死，刚转过头去看，就见年龄稍小点的那个女的拿出一把刀向另一个女的脖子刺去。”该目击者说，鲜血顿时从被刺女子的脖子处向外喷出，衣服瞬间被染红，地上到处是血。</p>\n\n<p>　　一名在现场约百米处打乒乓球的老人也目击了整个过程。这名老人说，起初，两名女子相互厮打，约两分钟后，一人突然拿出一把刀刺向另一人，接着一男子赶到抱着受伤女子向门外跑去。小区居民报警，民警赶到将持刀伤人的女子带走。</p>\n\n<p>　　<strong>夫妻俩结婚近10年有3娃</strong></p>\n\n<p>　　昨日，记者从咸阳市公安局彩虹分局了解到，受伤女子姓史，32岁，兴平人，被刺后抢救无效死亡，而刺伤她的牛某也是兴平人，29岁。“据调查，史某与牛某的丈夫张某一年前通过网络相识，后来发展成情人关系，7月5日，牛某无意间发现了丈夫与史某的情人关系，气愤之下打电话质问，气愤不过又打车从兴平赶到咸阳史某所住的小区，将其叫下楼，两人相互谩骂，继而动手。厮打中牛某掏出随身携带的刀子，在史某脖子上划了一刀。”民警介绍，事发时，牛某的丈夫张某也刚刚从兴平赶到该小区，发现史某被刺伤，张某立即将人送往医院。</p>\n\n<p>　　牛某与张某结婚近十年，育有两女一男。夫妻俩在兴平做运输生意，以前有两辆大货车，目前经济条件不如以前了。而死者史某目前还未婚，是武功县一银行职工。</p>\n\n<p>　　目前，牛某因涉嫌故意伤害他人致死已被刑拘。</p>\n\n<p align=\"right\">(原标题：老婆刺死了老公的女网友(图))</p>\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('426', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27597070\' f_id=\'3\' -->\r\n	<p>　　本报宝鸡讯(记者宋秉琴实习记者鱼茜瑶) 7月5日上午10时许，宝鸡市有老两口带着孙女等红灯时，被突然出现的喷药车喷了一身药水，老人说喷药车到来时应该响个音乐，提醒路人及时躲避。目前孙女正住院观察治疗。</p>\r\n	<p>　　<strong>喷药车静悄悄 也不响音乐</strong></p>\r\n	<p>　　王女士介绍，5日上午，她和老伴带着8岁的孙女出去，在宝鸡市金台区东方路和跃进路十字等红灯时，一辆给树喷洒农药的车突然开过，路人还没反应过来，已被喷得全身尽湿，老两口立刻护住孙女，但孩子的右脸颊、右胳膊还是被喷上了药。</p>\r\n	<p>　　&ldquo;孩子脸上和胳膊都往下流水，我和老伴的衣服后背都湿了。喷药时应该响音乐提醒路人，这可是农药呀，大人无所谓，关键还有孩子。&rdquo;王女士说，身上一股刺鼻的农药味，回家后赶快冲了澡，还不放心，便带孩子去医院做检查。</p>\r\n	<p>　　当天上午，他们和金台区环卫局取得联系，并一同在宝鸡市妇幼儿童医院为孩子做检查。医生表示尚无大碍，但建议住院观察。金台区环卫局交付了1000元住院费。昨日，被喷的小女孩还在医院接受观察治疗，目前没有明显不适。</p>\r\n	<p>　　<strong>农药腐蚀线路 音乐不响了</strong></p>\r\n	<p>　　金台区环卫局绿化科科长杨新选表示，正常洒水和喷药时都会响音乐，但由于农药的腐蚀性强，喷药车常被腐蚀出现线路短路等情况，有时车开出去时还是好好的，在工作中突然就出现故障了。他们将立即对问题车进行修理。</p>\r\n	<p>　　那么音乐不响了为啥还喷药，杨新选表示，因为药还没有喷完，不喷就浪费了。</p>\r\n	<p>　　&ldquo;温度高时喷农药效果是最好的，但为了避免给行人造成不便，我们将考虑晚上工作。&rdquo;杨新选对给王女士等行人带来的不便表示歉意，他们会积极配合家属治疗。</p>\r\n	<p>　　据介绍，当时所喷农药名为灭多威。网上查询显示：灭多威是一种广谱性氨基甲酸酯类杀虫剂。人员过量接触或饮用会出现流涎、流泪、视力模糊、震颤、惊厥、恶心、呕吐等症状。</p>\r\n	<p align=\"right\">(原标题：正等红灯 祖孙3人被喷农药)</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('427', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27596976\' f_id=\'3\' -->\r\n	<p>　　本报讯(首席记者张鹏翔) 7月2日凌晨，景泰县城发生一起血案，一小伙在街头被捅身亡。案发后，景泰县公安局刑侦部门仅用3小时速破这起故意伤害致死案。</p>\r\n	<p>　　经审理查明，今年6月30日，小亮给朋友说日前在喝酒过程中，李某给自己&ldquo;摆了口&rdquo;，心里很不爽，要找机会教训李某。7月2日零时许，当小亮得知朋友张某与李某约好见面时，便与张某一同前去。当小亮看见李某时，便冲上前互相扭打，在此过程中在李某身上捅了几刀，致李某当场死亡。随后，犯罪嫌疑人小亮投案自首。到案后，犯罪嫌疑人小亮对其犯罪事实供认不讳。目前，该案正在进一步侦办中。</p>\r\n	<p align=\"right\">(原标题：不满他人&ldquo;摆架子&rdquo;景泰男子持刀酿命案)</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('428', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27596984\' f_id=\'3\' -->\r\n	<p>　　本报讯(通讯员杨永平 首席记者邢剑扬) 5月18日晚9时许，武都区一卫校女学生小菁和小娜一起与网友见面，谁知遭到强奸抢劫。日前，涉嫌强奸罪、抢劫罪的杨某某，涉嫌强奸罪的庞某某、李某某被陇南市武都区人民检察院批准逮捕。</p>\r\n	<p>　　今年年初，杨某某通过QQ聊天结识了卫校女生小菁。5月18日晚9时许，杨某某打电话要求与小菁见面，小菁与同学小娜一起去见杨某某。见面后，杨某某谎称要带她们去KTV唱歌，结果却将二人带至一处偏僻的民房，还叫来两个朋友庞某某和李某某。小菁发现情况不对，找借口脱了身。杨某某等二人随后将小娜带至郊区的一处河滩上，分别对其实施了强奸。杨某某随后还抢走小娜手提包内的100多元现金、一张银行卡。在李某某打算将小娜带至宾馆的途中，小娜趁机逃脱并向公安机关报了案。</p>\r\n	<p>　　(文中受害人为化名)</p>\r\n	<p align=\"right\">(原标题：夜晚约见网友卫校女生被带河滩强奸)</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('429', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27602287\' f_id=\'3\' -->\r\n	<p>　　中新网宜昌7月7日电 (记者 郭晓莹)记者7日从湖北当阳市宣传部门获悉，7日上午11时许，当阳市木店村发生一起凶杀案，一家三口被害，嫌犯跳入堰塘自杀身亡。</p>\r\n	<p>　　经初步调查，犯罪嫌疑人周某与村民望某因纠纷产生矛盾，当日上午11时许，周窜至该村四组望某家，将望某及其子、其妻杀死，随后周某跳入附近堰塘畏罪自杀身亡。</p>\r\n	<p>　　目前，此案正在进一步侦办之中。(完)</p>\r\n	<p align=\"right\">(原标题：湖北当阳一家三口被杀 嫌犯跳入堰塘溺亡)</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('430', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27603488\' f_id=\'3\' -->\r\n	<p>　　本报讯 前天上午，在首都儿科研究所附属儿童医院里，一位男性病人家属在要求加号遭到医生拒绝后，突然从背后用脚踹女医生，造成女医生脚部受轻伤。目前，打人者已经被警方带走。</p>\r\n	<p>　　昨天上午，记者来到位于雅宝路的儿研所2层的急诊室门前，对于前日上午发生的女医生被打事件，儿研所的医生并不愿意多谈，也不愿意告知到底是哪位医生被打伤了。一位医生告诉记者，他上午就听说了这件事，大概9点半左右，一位男性病人家属要求给病人加号，但是由于周末来看病的人很多，医生并未同意，不知道是什么原因，这名男性病人家属突然从背后用脚踹女医生，被踹的女医生当时就倒在了地上。</p>\r\n	<p>　　一名护士则告诉记者，踹人的是一名30多岁的男的，中等身材，带着一个一两岁的小男孩来看急诊。而被踹的医生则是刚刚下夜班，紧接着要上白班，家属让她给加个号，但是因为医院的规定就没有给那名男子加。&ldquo;医生转过身要去病房的时候，却被这名男子一脚踹在小腿肚上，当场就扑倒在了地上，半天没有起来，脚踝上青了一块。&rdquo;</p>\r\n	<p>　　在儿研所急诊室，一位事发时在现场的病人家属告诉记者，她当时看到一名男子突然从背后用脚踹一名女医生，那名女医生当时就被踹倒了，那名踹人的男子踹完人后还一直骂骂咧咧的，不知道双方发生了什么冲突，但打医生确实感觉不太妥当。</p>\r\n	<p>　　记者了解到，被患者家属踹伤的女医生随后被送到朝阳医院去做伤情鉴定，&ldquo;医院一直有人全程陪同并出具书面文件要求警方严惩凶手&rdquo;。</p>\r\n	<p>　　警方称，当日，患儿家属与医院医生因看病问题确实发生纠纷，目前此案正在侦办中。</p>\r\n	<p>　　线索提供/马先生</p>\r\n	<p align=\"right\">(原标题：加号遭拒 病人家属脚踹女医生)</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('431', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27603491\' f_id=\'3\' -->\r\n	<p>　　本报讯 自称被同屋姐妹&ldquo;欺负得狠了&rdquo;，河南来京打工的女保洁员朱红青趁同屋李某熟睡之机，用菜刀猛砍李某头面部几十刀，将其砍死。朱红青还将前来劝架的同宿舍另一名女保洁员面部砍了三刀，造成对方轻伤。日前，市一中院以故意杀人罪一审判处朱红青死缓。现年57岁的朱红青从河南固始老家来北京打工后，在中关村软件园社区当保洁员。按照朱红青的说法，她们七八个女保洁员住在一个宿舍里，她和来自重庆的保洁员李某有矛盾。案发当天晚上，朱红青睡到半夜起来预谋杀人。她先拿菜刀砍了正在熟睡的李某肩膀一刀，李某爬起身边喊&ldquo;救命&rdquo;边跑。她去追，李某被她砍倒在地。保洁员郝女士作证说，她被喊叫声惊醒后看到李某满脸是血地叫，她就去抢朱红青手里的菜刀，结果自己也被砍伤。</p>\r\n	<p align=\"right\">(原标题：自称被欺负砍死同事 女保洁员一审获死缓)</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('432', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\">\r\n	<div class=\"img_wrapper\"><img alt=\"执勤交警飞身夺刀 \" src=\"/u/cms/www/201307/0815340486w7.jpg\" title=\"执勤交警飞身夺刀 \" /> <span class=\"img_descr\">执勤交警飞身夺刀 </span></div>\r\n	<!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27602631\' f_id=\'3\' -->\r\n	<p>　　城市晚报讯&nbsp;&nbsp;</p>\r\n	<p>&nbsp;&nbsp;&nbsp; 7月7日10时30分，长春市解放大路西民主大街交会处，只因为一场争执，一对年近五旬的夫妻当街持刀刺伤4人，而被刺伤的4人是一家四口。</p>\r\n	<p>　　凶案发生时，正在此执勤的长春市交警支队朝阳大队51岁的民警魏军发现后立即大声喝止，魏军不顾生死冲上前去与行凶男子厮打的一幕也感动了路人，在魏军与众人共同的努力下，两名行凶者被制服了。</p>\r\n	<p>　<strong>　危险&gt;&gt;</strong></p>\r\n	<p>&nbsp;</p>\r\n	<p><strong>　　街头争吵酿惨剧</strong></p>\r\n	<p>　　7月7日10时30分左右，长春市朝阳交警大队五中队民警魏军正在解放大路西民主大街执勤。突然，他听到了一阵激烈的争吵声，只见西民主大街上容大旅店门前，一名中年妇女正在破口大骂一名摆摊卖水果的中年男子，从女子骂人的内容不难听出他们是亲属关系。(后来证实该男子系该女子的哥哥)。其间骂人女子还两次掀翻该男子的水果摊。可这名男子并没有做出任何反击，而男子身边还有一位卖水果的中年妇女，这位妇女也一直在劝骂人女子(而该女子则是后来的伤人者之一)。</p>\r\n	<p>　　正当很多围观群众以为这件事情基本接近尾声的时候，突然间，一名中年男子冲了出来，他手中拿着刀，拼命地跑向骂人的妇女，当场将她刺倒在地。那一瞬间是所有人始料未及的。见到这一幕，远远地魏军大声喝斥着，同时向这边飞速跑来。而那名持刀男子似乎什么也听不进去。</p>\r\n	<p>　　在魏军飞奔过来时，男子手中的刀始终没有停下来，不停地刺向骂人女子，而这时一名年轻女孩跑来劝架，女孩呼喊着：&ldquo;别伤害我妈&hellip;&hellip;&rdquo;而男子已经红了眼，他又一刀刺向那名女孩。</p>\r\n	<p>　　魏军扑了上去，将其按倒，他拼死抓住行凶者持刀的手腕，因为行凶者的力气很大，魏军又用膝盖狠狠跪压在其手臂上，用力夺下带血的尖刀，成功控制住不断挣扎的行凶者。这看似只有几分钟的瞬间，但魏军绝对是用自己的命去赌。而就在这时，旁边刚刚还在劝架的那名中年妇女竟然也持刀刺向一名男子。</p>\r\n	<p>　<strong>　感动&gt;&gt;</strong></p>\r\n	<p>&nbsp;</p>\r\n	<p><strong>　　群众自发制服行凶者</strong></p>\r\n	<p>　　昨晚6点，魏军依然在解放大路与西民主大街交会处的岗位上巡逻。尽管膝盖在与行凶者厮打中受了伤，尽管直到现在他依然难以平静，但他仍然坚守在岗位上。见到记者后，51岁的魏军的一句话让记者感慨万分，&ldquo;我是警察，无论多危险我必须上&hellip;&hellip;&rdquo;</p>\r\n	<p>　　魏军说，当时那种情况确实很危险，他在拼命跑上来与行凶男子搏斗时，他感觉到那个人已经杀红了眼。而那一刻魏军什么都不能想，他唯一的想法就是希望男子刀下那条鲜活的生命可以被救下。谁都有子女，魏军的儿子24岁了，当他看到行凶者将女子的女儿也刺倒时，他几乎是飞奔扑过去的。作为父亲他不希望任何孩子受到伤害。</p>\r\n	<p>　　&ldquo;当时的场面很混乱，满地都是血，我很感谢周围的人民群众，因为在我与男子厮打时，男子的妻子，也就是之前还在劝架的那个卖水果的中年妇女也持刀将骂人女子的儿子和丈夫刺倒&hellip;&hellip;&rdquo;</p>\r\n	<p>　　魏军回忆说，幸好过往群众将行凶的卖水果妇女按倒在路边。后来魏军才知道该女子连伤了两人，除了骂人女子的丈夫外，还有她的儿子。</p>\r\n	<p>　　两人被控制了，围观群众又送来行凶女子的尖刀交给魏军。魏军在现场又拾到一把弹簧刀和一个带血的手机，在成功控制住两名行凶者稳定住局面后，拨打了&ldquo;110和120&rdquo;并保护好现场，直到派出所民警到达现场后，将行凶者和凶器移交，三台120急救车拉走了4名伤者。</p>\r\n	<p>　<strong>　敬佩&gt;&gt;</strong></p>\r\n	<p>&nbsp;</p>\r\n	<p><strong>　　51岁交警舍命救人</strong></p>\r\n	<p>　　记者来到案发现场时已经是傍晚，这里已经恢复了平静，但地面上的血迹仍然清晰可见。</p>\r\n	<p>　　据附近的目击者说，事发时陈某在街头与自己的哥哥发生争吵，具体内容都是家庭生活的问题，谁也未曾想到这样一件小事最终会变成一桩惨案。而正在路面执勤的交警魏军看到这一幕后拼死与行凶者搏斗的情景让大家记忆犹新。&ldquo;那位交警平日总在这里执勤，人很好，很认真。他年纪看上去也不小了，这是让我们敬佩的地方，说实话当时那阵势，谁都不敢上前，交警上去的时候他还拿刀上去刺呢！&rdquo;</p>\r\n	<p>　　据了解伤者陈某(骂人者)一家四口都身中数刀，情况很严重，目前正在医院进行抢救。而两名伤人者，51岁的宁某(男)与46岁的史某是夫妻俩，已经被警方控制。目前，此案件正在建设广场派出所进行调查。</p>\r\n	<p>　　(记者 陶彬/报道 董竞琦/摄)</p>\r\n	<p>&nbsp;</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('433', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\">\r\n	<div class=\"img_wrapper\"><img alt=\"漫画/王启峰 \" src=\"/u/cms/www/201307/08153405c406.jpg\" title=\"漫画/王启峰 \" /> <span class=\"img_descr\">漫画/王启峰 </span></div>\r\n	<!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27603386\' f_id=\'3\' -->\r\n	<p>　　41岁&ldquo;的哥&rdquo;刘杰算是和交警杠上了。</p>\r\n	<p>　　今年有多少次起诉西安交警，他记不清了，&ldquo;总有几十个了吧，去年更多&rdquo;。</p>\r\n	<p>　　虽然每次的维权成本至少千元，还&ldquo;赔着本&rdquo;，但他仍打算继续维权，&ldquo;这是个意识问题，现在看着赔了，长远看，如果能让交警部门规范执法，值得！&rdquo;</p>\r\n	<p>　　这一个多月，刘杰自己或者托朋友数次找到记者，每次都会送来几份判决书，还总不忘说一句，&ldquo;赢了！&rdquo;有的判决书上，刘杰是原告，更多的，他是代理人。</p>\r\n	<p>　　作为一名出租车司机，频频和交警部门&ldquo;作对&rdquo;，究竟是为什么？难道不担心营运时被找麻烦吗？维权的效果、成本又如何呢？7月5日，记者和刘杰聊了起来。</p>\r\n	<p>　　<strong>讨说法 跟了交警整整一周</strong></p>\r\n	<p>　　华商报：还记得第一次被处罚的情形吗？刘杰：记得，怎么不记得！1997年我刚开出租车不久。掉头的时候，速度很慢，正好有个乘客拉开门上来了，因为这个，交警扣了驾照，要罚50元。</p>\r\n	<p>　　我不服啊，掉头时是乘客硬要上来的。为了讨个说法，我放弃了营运，骑着自行车跟着处罚我的交警整整一周。最后，交警罚了30元，算各让一步。</p>\r\n	<p>　　华商报：其实你当时还是违规了。你从那时起就有了维权念头吗？</p>\r\n	<p>　　刘杰：那时还没有。我真正有维权念头是在2005年。</p>\r\n	<p>　　2004年冬天，我买了一辆出租车，2005年审车时，发现有30多个违法记录，其中在西安交大北门口就有4个，都是&ldquo;不按规定道路行驶&rdquo;，视频显示车从慢行道出来后在兴庆路十字右拐。我跑到交大北门口发现，路边有个出租车停靠点，按道理应该是从就近出口进快车道，可出口都被石墩封死了，只能走到十字右拐。我找到交警部门，对方认为我说得有道理，原本罚400元，后来给我开了4张50元的罚单。但我又没错，为啥要罚我？我找了交警支队领导，最终免予处罚。</p>\r\n	<p>　　这4个有问题，难道其他的没有问题？从那以后，我就对违法信息留意起来。</p>\r\n	<p>　　<strong>为维权 买书学习交安法</strong></p>\r\n	<p>　　华商报：起诉了那么多次，你对交通法规应该很熟了吧？</p>\r\n	<p>　　刘杰：我是在维权中，买来书学习的。2006年底，交警部门在西京医院附近拍到我在同一地点掉头3次。我要看证据，人家不让看，说应该罚300元，让我给200元算了。罚单上除了车号，就写着依据《交通安全法》第90条。我又不清楚第90条怎么规定的，就去买了《交通安全法》和相关政策的书，发现这条是个概念性的法律责任：机动车驾驶人违反道路交通安全法律、法规关于道路通行规定的，处警告或者二十元以上二百元以下罚款。</p>\r\n	<p>　　按照规定，处罚要有车辆类型、违法时间、地点、违法性质、处罚依据，可罚单上都没有，我就提出行政复议。2007年正月初八刚一上班，我就被民警叫了去，把钱退了，说算了。</p>\r\n	<p>　　<strong>行政诉讼 打赢的只有20%</strong></p>\r\n	<p>　　慢慢的，刘杰在西安&ldquo;的哥&rdquo;里有了一定的名气。</p>\r\n	<p>　　另一位出租车司机刘先生说，他也多次和刘杰一起参与立案、诉讼，当共同代理人。他说，去年5月到8月，刘杰当代理人起诉交警部门的案子就有上百个，复议的数量也不少。</p>\r\n	<p>　　据记者了解，的确有很多起针对交警部门的行政诉讼在西安市雁塔区人民法院立案。</p>\r\n	<p>　　华商报：你维权的途径主要是什么？刘杰：行政复议、行政诉讼。</p>\r\n	<p>　　华商报：维权效果怎么样？</p>\r\n	<p>　　刘杰(叹了口气)：行政复议效果不理想。说句不好听的，这就是到他爸那儿告他娃呢&hellip;&hellip;</p>\r\n	<p>　　华商报：行政诉讼呢，胜率高吗？</p>\r\n	<p>　　刘杰(笑了笑)：和解退钱的算不算？我们估算过，大概40%和解、40%败诉，胜诉的只有20%。败诉主要是因为自己的法律知识不到位以及案件本身模棱两可、把握不大。总的说来，维权效果一般。</p>\r\n	<p>　　<strong>维权成本 一次至少1000元</strong></p>\r\n	<p>　　华商报：算没算过维权的成本，和维权结果比，划得来吗？</p>\r\n	<p>　　刘杰(苦笑)：赔得不像啥了！诉讼费不高，50元，但立案、交费、领传票、开庭、和解、撤诉&hellip;&hellip;还得随时准备配合调查、提交证据，反正一趟官司，至少跑5回，如果这些时间用来营运呢？我得出的结论是，为一个可能错误的处罚，司机维权的成本至少在1000元以上。</p>\r\n	<p>　　华商报：还得做好败诉的准备。</p>\r\n	<p>　　刘杰：是啊，败诉也很正常，就当交学费了。</p>\r\n	<p>　　不过，虽然维权&ldquo;赔本&rdquo;，但我仍打算继续维权，这是个意识问题，现在看着赔点，长远看，如果能让交警部门规范执法，值得！</p>\r\n	<p>　　<strong>弄清楚为啥被罚，是维护自己的权利</strong></p>\r\n	<p>　　目前，刘杰的工作是顶班司机，哪个&ldquo;的哥&rdquo;有事，他就临时替换跑车。</p>\r\n	<p>　　华商报：在一般司机眼里交警&ldquo;躲都躲不及&rdquo;，你总挑毛病，难道不担心被找麻烦吗？</p>\r\n	<p>　　刘杰(想了想)：不存在，没人找过麻烦。我认识了很多交警，他们代表的是行政机关，和我又不存在私人恩怨。</p>\r\n	<p>　　华商报：你不仅自己起诉，还帮别人当代理人，目的是什么？</p>\r\n	<p>　　刘杰：一是成就感，别人都忍气吞声的事，我都能赢，成就感特别强。更重要的是，我希望通过自己的起诉，能让交警部门有所警示，能规范执法、加强透明度，促进处罚的信息公开，执法更加人性化。</p>\r\n	<p>　　刘杰还专门强调，司机除了加强交通安全、遵守法律法规，还要弄清楚自己是不是真的违法了，&ldquo;如果都能站出来，弄清楚为啥被罚了，不仅是维护自己的权利，对交警部门规范执法也是一个很大的促进！&rdquo;</p>\r\n	<p>　　(应当事人要求，文中人物为化名) 本报记者宁军</p>\r\n	<p>　　<strong>看看这些案例学学怎样维权</strong></p>\r\n	<p>　　<strong>■案例一：照片仅显示车牌不能认定车辆超速</strong></p>\r\n	<p>　　事由：2012年4月21日中午，卢先生驾驶车辆&ldquo;在全市其他路段实施机动车行驶超过规定时速50%以下&rdquo;，要被罚款100元、记3分。今年2月，卢先生把交警支队起诉到西安市雁塔区人民法院。</p>\r\n	<p>　　卢先生辩称：当时经过东长安街，车发生故障停着，没有超速。交警处罚的依据仅仅是一张静态的照片，画面中仅显示了车牌，没有车辆具体特征，没有任何有效参照物，无法证明违法地点为&ldquo;其他路段&rdquo;。</p>\r\n	<p>　　法院认为：交警称东长安街限速为40公里每小时，但没有提供证据证明东长安街设置了限速标志、标线。不能仅凭交通技术监控设备收集的记录资料认定卢先生超速。交警部门作出超速的认定缺乏依据，属事实不清、主要证据不足。</p>\r\n	<p>　　法院判决：撤销西安市公安局交警支队作出的处罚决定书，退还罚款、清除所扣3分的记录。</p>\r\n	<p>　　<strong>■案例二：女协警问路交警称电动车拉客处罚</strong></p>\r\n	<p>　　事由：2012年6月21日晚，西安某印务公司员工丁某驾电动自行车在大雁塔北广场由东向西行驶时，路边有两个女孩向他问路，随后被交警曲江大队民警以拉客为由将车扣留。</p>\r\n	<p>　　同年7月6日，丁某提交了电动自行车行驶登记证、保证书等，交警大队要求他缴纳500元罚款。几天后，丁某向西安市雁塔区人民法院提起诉讼，要求确认交警曲江大队罚款500元的行政处罚行为违法、返还500元罚款及停车费等。</p>\r\n	<p>　　交警曲江大队称：大队的两名女协警在大雁塔北广场问原告去不去小寨，原告说去。在商讨乘车价格的过程中，被告大队民警将原告的车辆扣留。</p>\r\n	<p>　　法院认为：交警曲江大队按照相关技术条件自行认定丁某的车辆属机动车缺乏依据，适用机动车有关规定对丁某驾驶非机动车行为进行行政处罚&ldquo;与法相悖&rdquo;。庭审中的陈述和证据，也不能有效证明载客行为。此外，行政处罚程序存在违法。</p>\r\n	<p>　　法院判决：交警曲江大队对丁某作出的行政处罚行为违法，返还丁某所缴纳的罚款500元，赔偿丁某给停车场支付的保管费140元。</p>\r\n	<p>　　<strong>■案例三：出租车39秒跑了5.6公里？</strong></p>\r\n	<p>　　事由：的哥刘杰审验车辆时，查询到自己有两个2012年8月3日的超速：在长鸣公路由北向南、在东二环理工大由南向北实施超速50%以下违法行为，时间是当日0时31分、0时32分。两次违法行为各被罚款100元，各记3分。当天，刘杰缴纳了罚款，随后将交警支队起诉到法院，请求撤销处罚，返还所扣分值。</p>\r\n	<p>　　的哥刘杰称：两次违法行为的时间和距离不符合事实。&ldquo;长鸣公路&rdquo;与&ldquo;东二环理工大学&rdquo;的曲线距离是5.6公里。而交警部门的两张照片上，32分03秒与31分24秒之间仅有39秒，这也就是说，他用了39秒就跑了5.6公里！</p>\r\n	<p>　　交警支队辩称：测速设备的计时钟有误差，导致刘杰对两次测速间隔时间产生质疑。当然，也不可能在39秒内从长鸣公路行驶到东二环理工大学。</p>\r\n	<p>　　法院认为：被告不能仅凭交通技术监控设备收集的记录资料认定原告实施了超速违法行为。另外，从监控设备记录的资料显示，原告两处违法地点间隔时间明显存在矛盾。</p>\r\n	<p>　　法院判决：撤销两份处罚决定书并消除所扣分值的记录，案件受理费50元由交警支队负担。</p>\r\n	<p>　　本报记者宁军</p>\r\n	<p align=\"right\">(原标题：&ldquo;赔本&rdquo;打官司 就希望交警规范执法(图))</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('434', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27603070\' f_id=\'3\' -->\r\n	<p>　　本报宝鸡讯(记者 张莉 实习记者陈喆) 6月27日，宝鸡市陈仓区贾村镇贾村十组的张宝林被人用斧头砍伤，经医院抢救不治身亡，此前，该村一位71岁的白姓老人也被杀害，而行凶者竟是一名出家人。</p>\r\n	<p>　　<strong>出事前两人关系不错</strong></p>\r\n	<p>　　50岁的张宝林平时在镇上修鞋，在邻居们的眼里，张宝林是个好人。和张宝林的摊位紧挨的小卖部老板介绍，出事当天下午，张宝林和俗名张某的僧人发生了激烈争吵。张宝林的妻子来丈夫的摊上拿中午吃过饭的碗，也看见两人在争吵，因为张宝林与僧人关系平时就比较好，他的妻子并未在意。</p>\r\n	<p>　　据悉，村民口中的僧人张某是2010年村里的蛟龙寺刚建好时过来的。记者从陈仓区民族宗教事务局了解到，张某有登记注册。张某平时经常在镇子上转悠，穿的也是僧人服饰，有时候在镇子上卖点香烛。&ldquo;不太自己做饭，常买着吃。&rdquo;&ldquo;之前给别人家做法事时骂人骂得比较粗鲁。&rdquo;镇上居民对该僧人的评价比较多，但有一点大家说法一致，那就是张宝林与僧人关系很好。</p>\r\n	<p>　　据张宝林的妻子回忆，前几天张某来家里和丈夫聊天，说到要把两个人砍了，当时大家都以为他说的是气话。张某所说的两个人是蛟龙寺的管事。事发后，村民们也表示，张某曾多次表示过对寺中两个管事的不满。</p>\r\n	<p>　　<strong>以为通风报信斧头砍向好友</strong></p>\r\n	<p>　　6月27日下午5时10分左右，张某和张宝林争执后，直接奔向了寺庙管事白某的家，张宝林怕出娄子，赶紧骑了摩托车跟了出去。知情者介绍，张某到白某家后用斧头将71岁的白某砍倒在地，然后向另一管事家走去，路上恰巧碰上了张宝林。张某以为张宝林向这名管事通风报信，用斧头砍伤其胳膊、头和脖子。张宝林被送医院经抢救无效死亡，白某也不幸身亡。接警后，宝鸡市公安局陈仓分局贾村派出所民警将张某抓获。</p>\r\n	<p>　　记者从宝鸡市公安局了解到，僧人张某与寺中两名管事发生过争执，后矛盾激化。目前，张某已被刑拘。</p>\r\n	<p align=\"right\">(原标题：出家人斧夺两命 其中一人是朋友)</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('435', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27603160\' f_id=\'3\' -->\r\n	<p>　　新京报讯 (记者张媛)因将单位500万元转入女友账户用于购买理财，某报社实业中心副主任岂某及女友马某，被西城法院以挪用公款罪一审判刑6年。二人以涉案款非公款等原因，提出上诉。</p>\r\n	<p>　　<strong>500万理财获利8000</strong></p>\r\n	<p>　　案发前，48岁的岂某为某报社实业中心副主任兼呼伦贝尔中电草原生态开发有限公司(简称草原公司)总经理，其女友马某曾是呼伦贝尔移动通信有限公司客服部客户经理。</p>\r\n	<p>　　检方指控，2010年12月23日，岂某伙同马某私自将公司500万元转入马某个人理财账户，用于购买理财产品，时间为14天，获利8000余元。2011年1月7日，马某提出现金500万余元存于草原公司账户内。二人行为，涉嫌挪用公款罪。</p>\r\n	<p>　　岂某此前曾供述，2010年12月中旬，草原公司刚成立，马某说她的老同学王某提出来买理财产品能赚些钱。</p>\r\n	<p>　　马某供述中提到，王某当时是某支行领导，2010年12月23日，王某按照事先讲好的、将草原公司的500万元转入她账户买了理财产品。但在整个转账过程中，她都没有参加，由于双方关系好，&ldquo;单据的签字是王某代签的&rdquo;，王某有马的身份证复印件。</p>\r\n	<p>　　王某证言显示，曾跟马某在闲聊间提出了理财产品，&ldquo;马某个人要购买理财产品&rdquo;，之后让手下办理了相关手续。</p>\r\n	<p>　　庭审时，岂、马二人均对指控提出异议，500万元不是公款，且挪钱到个人账户系银行王某等的个人行为。</p>\r\n	<p>　　一审法院没有采信他们的当庭辩解，在判决书中引述了他们之前的有罪供述节录，认为被告人挪用公款用于个人营利活动，构成挪用公款罪，判处二人有期徒刑6年。</p>\r\n	<p>　<strong>　律师称案款与报社无关</strong></p>\r\n	<p>　　宣判后，二人提出了上诉。</p>\r\n	<p>　　辩护律师认为，此案在侦查阶段，银行王某均用&ldquo;如果岂某和马某不知道，我不可能同意&rdquo;等猜测性言词，推论岂某事先知晓购买理财的事情，但&ldquo;猜测&rdquo;无法证明岂某事先知晓，更不能证明岂某授意挪用公司的钱购买理财。另外，报社系国家事业单位，其下属全资子公司与岂某自己的公司合作成立了草原公司，因此认为涉案的500万元属于草原公司财产，与报社已经无关，不属于一审认为的公款。</p>\r\n	<p>　　目前，该案已进入二审审理中。</p>\r\n	<p align=\"right\">(原标题：挪500万理财 报社副主任不服判)</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('436', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- 功能注释标记：插入视频列表模块begin --></div>\r\n<p>&nbsp;</p>\r\n<p>&nbsp;</p>\r\n<p>&nbsp;</p>\r\n<p>&nbsp;</p>\r\n<p>&nbsp;</p>\r\n<p>\r\n	<link href=\"http://ent.sina.com.cn/css/470/20120928/style_videolist.css\" rel=\"stylesheet\" />\r\n</p>\r\n<div class=\"artical-player-wrap\" style=\"display: block\">\r\n	<div class=\"a-p-hd\">\r\n		<div id=\"J_Article_Player\">视频加载中，请稍候...</div>\r\n		<div class=\"a-p-info\"><label class=\"fl\" style=\"display: none\" suda-uatrack=\"key=videoq&amp;value=autoplay\"><input checked=\"checked\" id=\"J_Video_Autoplay\" type=\"checkbox\" /> 自动播放</label><span id=\"J_Video_Source\">&nbsp;</span></div>\r\n	</div>\r\n	<div class=\"a-p-bd a-p-bd-b\" id=\"J_Play_List_Wrap\" style=\"display: none\">\r\n		<div class=\"a-p-slide\">\r\n			<div class=\"a-p-s-list clearfix\" id=\"J_Play_List\">\r\n				<div class=\"a-p-s-item J_Play_Item\" play-data=\"109132441-1-0\" source-data=\"广东卫视《广东早晨》\" title-data=\"内地夫妇香港机场袭警\" url-data=\"http://video.sina.com.cn/p/news/c/v/2013-07-08/081462644129.html\"><a class=\"a-p-s-img\" href=\"javascript:\" title=\"内地夫妇香港机场袭警\"><img alt=\"内地夫妇香港机场袭警\" height=\"90\" src=\"/u/cms/www/201307/08153408gpvd.jpg\" width=\"120\" /> <i class=\"a-p-s-play\">play</i> <span class=\"a-p-s-txt\">内地夫妇香港机场袭警</span> </a></div>\r\n			</div>\r\n			<a class=\"a-p-s-prev\" href=\"javascript:;\" id=\"J_Player_Prev\">向前</a> <a class=\"a-p-s-next\" href=\"javascript:;\" id=\"J_Player_Next\">向后</a></div>\r\n	</div>\r\n	<script type=\"text/javascript\" src=\"http://i3.sinaimg.cn/ty/sinaui/scrollpic/scrollpic2012070701.min.js\"></script><script type=\"text/javascript\" src=\"http://ent.sina.com.cn/js/470/20120928/videolist.js\"></script><script type=\"text/javascript\">\r\n                                        /*自动播放1*/\r\n                                        var AUTOPLAY = 1;\r\n                                        /*连播1*/\r\n                                        var CONTIPLAY = 1;\r\n                                       /*处理自动播放选项和cookie*/\r\n                                        (function(){\r\n                                            var Tool = CommonTool;\r\n                                            var chk = Tool.byId(\'J_Video_Autoplay\');\r\n                                            var ua = navigator.userAgent.toLowerCase();\r\n                                            var isIOS = /\\((iPhone|iPad|iPod)/i.test(ua);\r\n                                            if(isIOS){\r\n                                              console.log(chk.parentNode.style.display);\r\n                                              chk.parentNode.style.display = \'none\';\r\n                                              return;\r\n                                            }\r\n                                            chk.parentNode.style.display = \'\';\r\n                                            var clickCookie=function(){\r\n                                                Tool.bindEvent(chk,\'change\',function(){\r\n                                                    var chked = chk.checked;\r\n                                                    Tool.writeCookie(\'ArtiVAuto\',(chked?1:0),24*365*10,\'/\',\'.sina.com.cn\');\r\n                                                });\r\n                                            }\r\n                                            var byCookie=function(){\r\n                                                var coo = Tool.readCookie(\'ArtiVAuto\');\r\n                                                if(coo){\r\n                                                    if(parseInt(coo)==0){\r\n                                                        chk.checked = false;\r\n                                                        AUTOPLAY = 0;\r\n                                                    }\r\n                                                }\r\n                                            };\r\n                                            clickCookie();\r\n                                            byCookie();\r\n                                        })();\r\n\r\n                                        /*获取第一个视频vid*/\r\n                                        var firstItem = CommonTool.byClass(\'J_Play_Item\',\'J_Play_List\')[0];\r\n                                        var fInfo = firstItem.getAttribute(\'play-data\').split(\'-\');\r\n                                        var fVid = fInfo[0];\r\n                                        var fPid = fInfo[1];\r\n\r\n                                        var sinaBokePlayerConfig_o = {\r\n                                          container: \"J_Article_Player\",  //Div容器的id\r\n										  width:525,\r\n										  height:430,\r\n                                          playerWidth: 525,   //宽\r\n                                          playerHeight: 430,    //高\r\n                                          autoLoad: 1,  //自动加载\r\n                                          autoPlay: AUTOPLAY, //自动播放\r\n                                          as: 1,  //广告\r\n                                          pid: fPid,\r\n                                          tjAD: 0,  //显示擎天柱广告\r\n                                          tj: 1,  //片尾推荐\r\n                                          continuePlayer : 1, //连续播放\r\n                                          casualPlay: 1,  //任意拖动视频\r\n                                          head: 0,  //播放片头动画\r\n                                          logo: 0,  //显示logo\r\n                                          share :0,\r\n                                          thumbUrl: \"http://p.v.iask.com/95/343/109132441_2.jpg\"\r\n                                        };\r\n                                        window.__onloadFun__ = function(){\r\n                                          SinaBokePlayer_o.addVars(\'HTML5Player_controlBar\', true);\r\n                                          SinaBokePlayer_o.addVars(\'HTML5Player_autoChangeBGColor\', false);\r\n                                          //SinaBokePlayer_o.addVars(\"vid\", fVid);\r\n                                          //SinaBokePlayer_o.addVars(\"pid\", fPid);\r\n                                          SinaBokePlayer_o.showFlashPlayer();\r\n\r\n                                        };\r\n                                      </script><script src=\"http://video.sina.com.cn/js/sinaFlashLoad.js\" charset=\"utf-8\" type=\"text/javascript\"></script><script type=\"text/javascript\">\r\n                                        (function(){\r\n                                          var toggle = function(id,hide){\r\n                                            var e = CommonTool.byId(id);\r\n                                            var par = e.parentNode;\r\n                                            if(hide){\r\n                                              CommonTool.addClass(par,e.className+\'_disabled\');\r\n                                            }else{\r\n                                              CommonTool.removeClass(par,e.className+\'_disabled\');\r\n                                            }\r\n                                          }\r\n                                          var scroll = new ScrollPic();\r\n                                          scroll.scrollContId   = \"J_Play_List\"; //内容容器ID\r\n                                          scroll.arrLeftId      = \"J_Player_Prev\";//左箭头ID\r\n                                          scroll.arrRightId     = \"J_Player_Next\"; //右箭头ID\r\n                                          scroll.listEvent      = \"onclick\"; //切换事件\r\n                                          scroll.frameWidth     = 532;//显示框宽度 **显示框宽度必须是翻页宽度的倍数\r\n\r\n                                          scroll.pageWidth      = 133*3; //翻页宽度\r\n                                          scroll.upright        = false; //垂直滚动\r\n                                          scroll.speed          = 10; //移动速度(单位毫秒，越小越快)\r\n                                          scroll.space          = 15; //每次移动像素(单位px，越大越快)\r\n                                          scroll.autoPlay       = false; //自动播放\r\n                                          scroll.autoPlayTime   = 5; //自动播放间隔时间(秒)\r\n                                          scroll.circularly     = false;\r\n                                          scroll._move = scroll.move;\r\n                                          scroll.move = function(num,quick){\r\n                                                scroll._move(num,quick);\r\n                                                toggle(scroll.arrRightId,scroll.eof);\r\n                                                toggle(scroll.arrLeftId,scroll.bof);\r\n                                          };\r\n                                          scroll.initialize(); //初始化\r\n                                          toggle(scroll.arrLeftId,scroll.bof);\r\n                                        })();\r\n                                      </script><script type=\"text/javascript\">\r\n                                      var VideoList1 = new ArticalVideoList(\'J_Play_List\',{\r\n                                        index : 0,\r\n                                        autoPlay : AUTOPLAY,\r\n                                        contiPlay : CONTIPLAY,\r\n                                        itemClass : \'J_Play_Item\'\r\n                                      });\r\n                                      VideoList1.init();\r\n                                      function playCompleted(tag){\r\n                                        VideoList1.playNext();                                          \r\n                                      };\r\n                                      </script></div>\r\n<!-- 功能注释标记：插入视频列表模块end --><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27603391\' f_id=\'3\' --><!-- video_play_list_html <table id=\"video_play_list\" border=\"1\" cellspacing=\"0px\" cellpadding=\"1px\">\r\n<tbody>\r\n	<tr>\r\n	<td>标题</td>\r\n	<td>vid</td>\r\n	<td>是否打开广告</td>\r\n	<td>媒体来源</td>\r\n	<td>视频截图</td>\r\n	<td>是否联播</td>\r\n	<td>地址</td>\r\n</tr>\r\n	<tr class=\"video_tr\">\r\n	<td>内地夫妇香港机场袭警</td>\r\n	<td>109132441</td>\r\n	<td>0</td>\r\n	<td>广东卫视《广东早晨》</td>\r\n	<td title=\"/u/cms/www/201307/08153408gpvd.jpg\"></td>\r\n	<td>1</td>\r\n	<td>http://video.sina.com.cn/p/news/c/v/2013-07-08/081462644129.html</td>\r\n</tr>\r\n</tbody>\r\n</table> video_play_list_html_end --><!-- video_play_list_data 内地夫妇香港机场袭警|109132441|0|广东卫视《广东早晨》||1|http://video.sina.com.cn/p/news/c/v/2013-07-08/081462644129.html<br> video_play_list_data_end -->\r\n<p>　　南都讯 记者石秋菊 发自香港 航班延误，乘客鼓噪，闹事打砸，这一幕发生在7月5日的香港机场。一对内地夫妇的男事主砸国泰航空的柜台更泼汽水，被香港警方拘捕时，妻子更涉嫌推倒一名警员，最后夫妇两人都被警方带走调查。</p>\r\n<p>　　香港警方表示，涉案夫妇中丈夫姓徐，称任职图书管理员，35岁，妻子姓万，34岁，两人来自江苏，在香港旅行后，原定于乘搭7月5日下午5点35分，香港国泰的CX 364航班，从香港返回上海浦东。</p>\r\n<p>　　据了解，当日下午3点多，夫妻两人到达机场国泰柜台办理好登记手续。随后由于上海浦东发出航空交通流量管制，航班延误，这个航班未能如期出发，航班上一共238名旅客之后滞留在候机大堂。</p>\r\n<p>　　航班延误至当天晚上9点多，徐在向国泰柜位职员查询航班最新情况时，与职员发生了争执，随后情绪激动，用手扫翻柜台上的电脑键盘，一名24岁的郑姓女地勤人员上前制止时，再被他用汽水淋泼，弄污衣服，旁边的32岁周姓男性地勤职员也被汽水泼中，之后两人报警。</p>\r\n<p>　　警员接到报案到场调查后，以涉嫌刑事毁坏及普通伤人将徐拘捕。徐的妻子见到丈夫被捕，也激动地意欲阻止警员，混乱间一名警员被人推倒在地上，之后万妇被其他警员制服，也被以涉嫌袭警罪拘捕。</p>\r\n<p>　　之后国泰发言人回应媒体，有关航班一共延误了16个小时，在7月6日早上9点起飞，航班延误期间，已经安排受影响乘客入住酒店及提供膳食，有关伤人事件也已经交由警方处理。</p>\r\n<p align=\"right\">（稿件来源于南方都市报 原标题：航班延误闹香港机场内地夫妇袭警被拘捕）</p>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('437', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27603466\' f_id=\'3\' -->\r\n	<p>　　晨报讯(记者 颜斐) 王某嫖娼后骗卖淫女喝下他放入安眠药的饮料，然后待对方熟睡后实施抢劫。近日朝阳区检察院以涉嫌抢劫罪对抢劫卖淫女的王某批准逮捕。</p>\r\n	<p>&nbsp;&nbsp;&nbsp; 今年5月，王某通过微信与张某取得联系，并在微信上谈妥了卖淫嫖娼的价钱。6月2日晚，王某在支付了嫖资后与张某发生了性关系。之后，王某将随身携带的强效安眠药放入饮料中并让张某喝下，趁对方熟睡之机抢走她5000元。张某后报警，王某即被警方抓获。王某还交代，他在今年2月10日还以同样的方法抢走一名卖淫女6000元。</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('438', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27603695\' f_id=\'3\' -->\r\n	<p>　　晨报讯(记者 李庭煊) 尹某谎称自己是中影数字基地的执行导演，以安排彭某做特邀演员为诱饵，骗取其1万元。近日怀柔法院以诈骗罪判处尹某拘役4个月，缓刑6个月。</p>\r\n	<p>　　去年3月，尹某在怀柔区杨宋镇安乐庄村租了一个群众演员大院，招募群众演员。9月彭某前来应聘，尹某谎称自己是影视基地的执行导演，在收取彭某1万元后称可帮彭某做特邀演员。随后尹某安排了10场群众演员的戏给彭某。因自己总没台词，发现自己不是特邀演员后，彭某要求尹某退还她1万元遭绝。为此彭某报警，面对警方的询问，尹某承认了自己的诈骗行为。</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('439', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27603778\' f_id=\'3\' -->\r\n	<p>　　扬子晚报讯 (记者 郭一鹏 通讯员 张晓冬) 穿着拖鞋开车，还在行驶中低头点烟，结果抬头时突然发现前面有辆电动车，司机赶紧踩了一脚刹车，哪知拖鞋打滑，车辆没有控制住直接撞了上去。电动车上两名老人倒地后，其中一人因伤势过重不幸死亡。前不久，南京大厂发生了这样一幕惨剧。</p>\r\n	<p>　　6月底一天清晨5点半，交警十大队接到报警称，有辆轿车追尾电动自行车，撞死了一名老人。司机会不会是酒后驾车？交警对肇事车司机汤某进行酒精测试，结果发现他并未饮酒。随后，交警又对事故原因进行询问，汤某指着自己脚上的拖鞋和一根掉在驾驶座附近的香烟，懊恼地说：&ldquo;都怪我点烟时没在意车子跑偏方向，发现情况后又没踩住刹车。&rdquo;原来，汤某行车至毕洼路时，有些犯困，便想抽烟提神，他一手扶方向盘，另一只手拿着打火机，低头点烟。就在刚点上火时，汤某觉得车子有点跑偏，他抬头一看，发现车子竟然驶进了非机动车道，右前方还有一辆电动自行车。情急之下，汤某赶紧用力踩下刹车，谁知拖鞋突然打滑，竟然没踩住刹车，他再次踩下刹车时，自己的车已经把电动自行车撞翻了。据了解，该事故汤某将承担全部事故责任。</p>\r\n	<p align=\"right\">(原标题：穿拖鞋开车还低头点烟 轿车失控追尾致人死亡)</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('440', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27604006\' f_id=\'3\' -->\r\n	<p>　　晨报讯&nbsp;(记者 颜斐) 发现妻子有了情人郭某，徐某以讨债为名，伙同两个朋友将郭某拘禁3天，其间不仅实施殴打和侮辱行为，还向郭某索要3000万元补偿费。近日，徐某因涉嫌非法拘禁罪和敲诈勒索罪被公诉到朝阳法院，两同伙也因非法拘禁罪被公诉。</p>\r\n	<p>　　据指控，徐某发现妻子与郭某存在不正当关系，遂谎称与郭某存在债务纠纷，找来禹某和孙某帮忙讨债。徐某与禹某事先在朝阳区租了一房屋。今年2月16日晚，徐某三人在一酒店房间内将郭某装入箱子，并带至承租房间内，郭某被拘禁直至2月19日下午6点。在此期间，徐某殴打、侮辱郭某，并录音录像，后向他索要补偿费。郭某在承诺给付3000万元后被送回酒店。后徐某、禹某、孙某被抓获归案。公安机关随案查处钳子、擀面杖、剪刀等工具。</p>\r\n	<p>　　公诉机关认为，徐某非法拘禁他人，且有殴打、侮辱情节，同时在此过程中向被害人勒索钱财，数额特别巨大，其行为应当以非法拘禁罪、敲诈勒索罪追究刑事责任。考虑到敲诈勒索罪系未遂，建议可以比照既遂犯从轻或减轻处罚。禹某、孙某非法限制他人人身自由，应当以非法拘禁罪追究刑事责任。</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('441', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27604829\' f_id=\'3\' -->\r\n	<p>　　本报记者赵志锋</p>\r\n	<p>　　中国区域特色经济研究院，听起来多么唬人的机构。如果不是法院在审理民事案件中查清它的真实面目，不知道这个国字头机构还要存在多久。</p>\r\n	<p>　　在法院司法建议的推动下，今年5月，这个活动在甘肃省的中国区域特色经济研究院西部中心(以下简称西部中心)被注销。此时，它已存在长达8年之久了。在这8年中，不仅兰州市招商局为它办理了外地驻兰办事机构登记证，陇南市招商局还让其承接了水电项目。</p>\r\n	<p>　　而在事过境迁之后，人们发现，西部中心得以注册并从事商业活动的手段一点儿都不高明。</p>\r\n	<p>　　那么，是什么让一个虚构的机构得以从容地通过招商局、发改委两家政府部门的审查呢？</p>\r\n	<p>　<strong>　号称经党中央国务院批准</strong></p>\r\n	<p>　　兰州商人丁某与中国区域特色经济研究院西部中心签订了水电项目&ldquo;内部股权转让合同&rdquo;和&ldquo;联合开发协议&rdquo;，并依约支付了部分定金和转让款。</p>\r\n	<p>　　后来，丁某发现，西部中心并未取得水电项目的立项及批复，遂提出退还定金及转让款的请求，未果。</p>\r\n	<p>　　于是，丁某任总经理的甘肃某商贸公司将中国区域特色经济研究院西部中心、中心主任王某、中国区域特色经济研究院起诉到法院，要求判令三被告双倍返还定金及转让款。</p>\r\n	<p>　　这起诉讼无意间揭开了一个惊人的秘密：法院发现，在甘肃陇南承接水电项目的中国区域特色经济研究院西部中心上级单位&mdash;&mdash;中国区域特色经济研究院根本不存在。</p>\r\n	<p>　　这时，丁某才意识到自己被骗，开始在网上发帖反映情况，并希望有关部门能够查实情况，挽回他公司的经济损失。</p>\r\n	<p>　　兰州城关区法院2012年6月和7月作出的一份民事裁定书及两份司法建议，揭开了兰州市民王某利用伪造的营业执照及文件材料，申请成立西部中心的事实。</p>\r\n	<p>　　2004年8月14日，兰州招商局(现兰州市经济合作服务局)同意了中国区域特色经济研究院在兰州市设立西部中心。</p>\r\n	<p>　　申请设西部中心提交的材料中有一份营业执照和申请报告。这份中国区域特色经济研究院的营业执照显示，登记机关为北京市工商局，住所为北京市圆明园西路2号，企业类型为国有经济。</p>\r\n	<p>　　申请报告这样描述中国区域特色经济研究院：&ldquo;是经党中央、国务院批准成立的专门从事区域特色经济的研究机构，该院已获得&lsquo;国家重大委托课题&rsquo;地位，这是国家最高级课题地位，关系到国家的重要决策。&rdquo;</p>\r\n	<p>　　西部中心设立以后，2009年12月4日，兰州市招商局为其办理了外地驻兰办事机构登记证，负责人为王某，类别为事业，业务范围为经济研究、资料收集、会议组织、新闻发布。</p>\r\n	<p>　　有了登记证后，王某开始在甘肃陇南洽谈开发水电项目，得到了陇南市发改委一位副主任的信任。陇南市发改委将一些水电项目交给了西部中心。</p>\r\n	<p><strong>　　西部中心印章居然有国徽</strong></p>\r\n	<p>　　丁某所在的甘肃某商贸公司是在2010年初与西部中心及其负责人王某产生交集的。当丁某发现西部中心并未取得水电项目的立项及批复时，一纸诉状将西部中心及其负责人王某起诉至兰州市城关区法院。</p>\r\n	<p>　　2012年6月4日，城关区法院作出了民事裁定书。该裁定书称，根据中国区域特色经济研究院提供的营业执照到北京市工商局、国家工商局调取工商档案材料时，发现该研究院没有注册，其营业执照不真实。</p>\r\n	<p>　　法院在向兰州市招商局了解中国区域特色经济研究院西部中心办理驻兰办登记材料时，发现其向兰州市招商局所提供的驻兰办证文件、材料、印章均不真实。</p>\r\n	<p>　　&ldquo;被告中国区域特色经济研究院不存在，西部中心所提供的办证材料不真实，所谓的西部中心形同虚设。&rdquo;民事裁定书称，被告王某是以中国区域特色经济研究院西部中心名称签订涉案合同，故应确认该案无民事案件具备的平等主体。</p>\r\n	<p>　　据此，城关区法院驳回了原告甘肃某商贸公司的起诉。</p>\r\n	<p>　　民事裁定书还载明：&ldquo;该案涉嫌公文、国徽印章等情形。&rdquo;记者在西部中心所签的合同中发现，该中心使用的印章中确实带有国徽。</p>\r\n	<p>　　记者了解到，最高人民法院关于在审理经济纠纷案件中涉及经济犯罪嫌疑若干问题的规定中明确要求，法院作为经济纠纷受理的案件，经审理认为不属经济纠纷案件而有经济犯罪嫌疑的，应当裁定驳回起诉，将有关材料移送公安机关或检察机关。</p>\r\n	<p>　　记者查看该民事裁定书，没发现有将案件材料移送到公安机关或检察院的字样。但兰州市城关区法院于2012年7月3日，分别向兰州市经济合作服务局(原兰州市招商局)和陇南市发改委发出了司法建议书。</p>\r\n	<p>　<strong>　司法建议书直指政府部门失职</strong></p>\r\n	<p>　　在向兰州市经济合作服务局发出的司法建议书中，城关区法院明确指出，&ldquo;中国区域特色经济研究院&rdquo;营业执照、文件等材料均属伪造，其法人主体虚构。</p>\r\n	<p>　　上述司法建议称，&ldquo;因你局办理的驻兰机构证照，使中国区域特色经济研究院西部中心在甘肃陇南承接开发水电项目，并随意签订转让合同，收取转让费等违法行为，在当事人发生纠纷时，又无法找到合法的法人主体进行追偿。&rdquo;</p>\r\n	<p>　　该司法建议中所称的&ldquo;随意签订转让合同&rdquo;，是指西部中心于2010年7月与甘肃某商贸公司签订了&ldquo;马家坝水电站联合开发协议&rdquo;后，又于同年11月和陕西某矿业公司签订了&ldquo;马家坝水电站联合开发协议&rdquo;，将马家坝水电项目控股权再次转让。</p>\r\n	<p>　　城关区法院建议，兰州市经济合作服务局在办理外地驻兰机构登记证时，应该认真审查外地机构的合法性，不能只是形式审查。</p>\r\n	<p>　　在向陇南市发改委发出的司法建议书中，城关区法院也建议，在立项审核水电站工程时，应该认真审查机构的合法性及经济实力，并公开招标，不能只是形式审查。</p>\r\n	<p><strong>　　陇南发改委一副主任涉嫌受贿</strong></p>\r\n	<p>　　记者从检察机关了解到，陇南市发改委负责上述水电项目的那位副主任，因涉嫌受贿，去年底已被甘肃检察机关立案调查，目前案件还在进一步侦查之中。</p>\r\n	<p>　　甘肃某商贸公司的起诉被驳回后，总经理丁某遂向兰州警方报案，称王某涉嫌构成伪造国家公文、证件、印章及合同诈骗。据了解，截至目前，警方还未立案。</p>\r\n	<p>　　记者还获悉，王某因与湖南一家公司签订项目开发合作合同而被兰州警方立案侦查，并将案件移送至兰州市检察机关，检察机关因事实不清等原因退回警方补充侦查。记者从兰州公安部门了解到，该案目前仍在侦查之中。</p>\r\n	<p>　　据了解，兰州市经济合作服务局已经注销了&ldquo;中国区域特色经济研究院西部中心&rdquo;，收回了《外地驻兰办事机构登记证》。今年5月21日，在该局向陇南市发改委发的一份函中称，西部中心在陇南从事了超业务范围经营。</p>\r\n	<p>　　兰州城关区法院的两份司法建议直指兰州市招商局和陇南市发改委有&ldquo;管理漏洞&rdquo;。这一事件发人深省：兰州市招商局缘何让一个伪造材料申请的西部中心披上合法的外衣？陇南市发改委缘何能让一个利用虚假材料申请注册的&ldquo;外地驻兰办事机构&rdquo;轻易拿到水电项目的开发权？</p>\r\n	<p>　　本报兰州7月7日电</p>\r\n	<p align=\"right\">(原标题：伪造国字头研究院承接水电项目)</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('442', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27605794\' f_id=\'3\' -->\r\n	<p>　　法制网记者张冲 法制网通讯员郭佳音</p>\r\n	<p>　　一段摄录于6月25日的视频，清晰地记录了黑龙江省哈尔滨市城管局一名职工的粗暴行为。</p>\r\n	<p>　　画面显示：城管督察车上下来的男子，走到麻辣烫摊前，趁女记者孙晓卓不备一脚将她坐的塑料凳踢飞，孙重重地摔在地上。</p>\r\n	<p>　　目前，这名城管局职工因受到行政拘留10天的处罚，正关押在看守所里。他叫王雷，今年30岁，系哈尔滨市城市管理局职工。</p>\r\n	<p>　　孙晓卓是哈尔滨电视台记者。当天，哈尔滨市道里区卫生监督所工作人员正在对一家无证经营的幼儿园进行检查，孙晓卓和另外一名东北网的记者在场采访。</p>\r\n	<p>　　<strong>无证幼儿园扰民被举报</strong></p>\r\n	<p>　　今年6月8日，有网民在哈尔滨市道里区政府的官方网页&ldquo;民生民意&rdquo;栏目当中留言，&ldquo;我是道里区海富康城小区B高1的居民，我家旁边开了个宝贝幼儿园，每天老师和孩子的吵闹声搞得我退休在家的母亲烦躁难熬，今年已住院两次。我详细了解到：宝贝幼儿园无执照，这样的幼儿园是不是该取谛！老百姓们求您去调查调查这个黑幼儿园吧！谢谢您了！&rdquo;</p>\r\n	<p>　　6月13日，道里区教育局给予回复：&ldquo;教育局相关工作人员于2013年6月7日深入该园，严肃告知办园人，其擅自开办幼儿园的行为已违反了《幼儿园管理条例》第二章第十一条规定，要求其停止违法办园行为，停止招生，对其下发了&lsquo;违法行为限期改正通知书&rsquo;。&rdquo;</p>\r\n	<p>　　幼儿园管理条例》第十一条规定：国家实行幼儿园登记注册制度，未经登记注册，任何单位和个人不得举办幼儿园。</p>\r\n	<p>　　但教育局的《违法行为限期改正通知书》似乎并未起到应有的作用，幼儿园依旧我行我素。</p>\r\n	<p>　　6月17日和6月21日仍然不断有网友在网上反映该家黑幼儿园的情况，道里区教育局的回复分别是&ldquo;关于您反映的情况，教育局相关工作人员多次踏查，积极与办园人沟通，责令其改正非法办园行为。&rdquo;&ldquo;如限期不能改正，将联合相关部门联合执法。&rdquo;</p>\r\n	<p>　　6月25日下午，当地两家媒体东北网民生栏目记者和哈尔滨电视台方圆之间栏目记者实地暗访，发现这家黑幼儿园仍在经营。随后记者分别向道里区卫生监督部门和城管12319热线进行了反映，卫生监督部门派出3名执法人员很快到达幼儿园进行现场检查。</p>\r\n	<p>　　<strong>记者遭质问&ldquo;凭什么采访&rdquo;</strong></p>\r\n	<p>　　6月25日下午4时50分左右，一辆牌照为黑ALP757、内部编号3005的&ldquo;城管督察&rdquo;车停到了幼儿园门前。</p>\r\n	<p>　　现场采访的记者本以为是接到城管指挥热线来查看&ldquo;幼儿园私扒承重墙&rdquo;情况的。岂料车上走下两名身着便装的男子，其中驾驶员进入幼儿园后先是质问卫生监督所执法人员&ldquo;你们凭什么检查？&rdquo;&ldquo;我是孩子家长，你们凭什么采访？&rdquo;随后该男子又将矛头指向了两名记者，&ldquo;你们别管我是幼儿园的员工还是孩子家长，我就问你们凭什么采访，你们把证件拿出来我看看。&rdquo;该男情绪颇为激动，现场一度混乱。</p>\r\n	<p>　　据哈尔滨电视台记者孙晓卓讲，当天的采访其实已经结束了，有卫生监督所的执法检查，所拍摄的画面足够成片了。正准备要离开时这辆城管督察车停到了幼儿园门前，驾驶员下车后有些气急败坏。</p>\r\n	<p>　　<strong>&ldquo;警用数码鹰&rdquo;录下全程</strong></p>\r\n	<p>　　当日17时，道里区康安路派出所接到报警后有三名警员赶到现场维持秩序，其中一名民警胸前佩戴的编号为92780000的&ldquo;警用数码鹰&rdquo;清晰记录当天发生的情况。</p>\r\n	<p>　　《法制日报》记者在视频中看到，因现场混乱，哈尔滨电视台记者孙晓卓先是站在距幼儿园门前20米远的麻辣烫摊前，随后坐到了麻辣烫摊前的塑料凳子上。</p>\r\n	<p>　　民警要求双方一同到派出所解决问题，城管督察车上下来的男子仍然火气很大，走到麻辣烫摊前，趁孙晓卓不备一脚将其坐的塑料凳踢飞，孙重重地摔在地上。&ldquo;这麻辣烫是我家开的，你凭什么坐？！&rdquo;男子口中振振有词，民警见状立即将该男子控制带回派出所。</p>\r\n	<p>　　<strong>城管局职工被拘留10天</strong></p>\r\n	<p>　　经过民警调查证实，攻击电视台记者孙晓卓的男子叫王雷，今年30岁，工作单位系哈尔滨市城市管理局。</p>\r\n	<p>　　孙晓卓的诊断书上记载：&ldquo;尾骨裂隙性骨折不除外&rdquo;。哈尔滨市公安局出具的法医鉴定结果为&ldquo;轻微伤&rdquo;。</p>\r\n	<p>　　根据《中华人民共和国治安管理处罚法》第43条之规定，公安机关对王雷作出了行政拘留10天并处罚金500元的处罚决定。</p>\r\n	<p>　　孙晓卓提供的另外一份录像显示，事发当日23时许，被传唤到派出所的宝贝幼儿园园长及老师做完笔录后，仍然是被白天那辆内部编号为3005的城管督察轿车接走，面对孙晓卓家人的质疑，驾驶该车的男子自称&ldquo;正在出夜勤&rdquo;。</p>\r\n	<p>　　孙晓卓对《法制日报》记者说，当天晚上开城管督察车去派出所接幼儿园园长的男子在事发当天下午也出现过，与王雷一同来到幼儿园，当时从副驾驶下车，始终站在一旁观望整个过程。</p>\r\n	<p>　　《法制日报》记者调查了解到，王雷的单位是哈尔滨市城管局下属的城市环境综合整治办公室(简称环卫办)，这是一个参照公务员标准的财政全额拨款事业单位。而他所驾驶编号3005号城管督察车户籍属于环卫办管辖的一家清洁公司，而这样的城管督察车在环卫办还有七八辆。</p>\r\n	<p>　　<strong>伤人男子系城管局司机</strong></p>\r\n	<p>　　7月3日下午，《法制日报》记者来到环卫办，在该单位的院内，记者看到多辆喷涂蓝白相间颜色的城管督察车辆，如果去掉醒目的&ldquo;城管督察&rdquo;四个字，外形和颜色与警用车辆极为相似。且每辆车上都有内部编号，但并未找到3005号车。</p>\r\n	<p>　　此时，环卫办党组书记郭东升正在院内打电话，听闻记者要了解员工王雷被行拘的事件，连忙说&ldquo;这情况我知道，但我马上要去开会，去发改委开会&rdquo;。&ldquo;那能不能找一个熟悉情况领导给介绍一下情况。&rdquo;</p>\r\n	<p>　　郭东升回到办公楼内，不一会儿，有工作人员通知记者去四楼会议室&ldquo;郭书记正在等候&rdquo;。</p>\r\n	<p>　　记者：王雷在环卫办担任什么职务？</p>\r\n	<p>　　郭东升：以前是我们粪便处理场的工人，前不久刚刚调到办里当司机，我们打算下一步给他退回去。</p>\r\n	<p>　　记者：他当天是在执行公务当中吗？</p>\r\n	<p>　　郭东升：不是，据我们了解他是开车拉着其他职工去垃圾场检查工作，返回单位的途中接到什么人的电话，个人临时决定去幼儿园的。</p>\r\n	<p>　　记者：录像当中显示王雷穿着普通的短裤和T恤，驾驶执法车辆不需要统一着装吗？</p>\r\n	<p>　　郭东升：我们环卫办没有统一的制服，即使开城管督察的车也不需要，因为我们的车辆虽然叫城管督察，但实际上根本没有任何执法权，执法权都在行政执法局那边。</p>\r\n	<p>　　记者：这家幼儿园和门前的麻辣烫摊与王雷本人有关系吗？</p>\r\n	<p>　　郭东升：这个还不清楚，王雷被拘留还没出来，出来后我们会好好调查一下。国家有规定，单位职工不允许从事第二职业，那个麻辣烫摊好像是王雷他妈开的。</p>\r\n	<p>　　<strong>幼儿园园长疑为科长妻子</strong></p>\r\n	<p>　　法制日报》记者随后请郭东升书记看了一下录像。问：当天晚上有人驾驶城管督察编号3005号车去派出所接幼儿园园长，还自称&ldquo;出夜勤&rdquo;，这人是谁？是否属于公车私用？</p>\r\n	<p>　　郭东升：谭宏利，我们固废科的职工，等我们把事情查清楚了都会作出处理。</p>\r\n	<p>　　离开环卫办后，有知情人向《法制日报》记者透露，谭宏利是环卫办固废科科长，幼儿园是其妻子所开，而且谭宏利的妻子也是环卫办职工。王雷是谭宏利提拔到固废科给自己开车的司机，当天接到幼儿园被检查的电话的人不是王雷，是谭宏利。这一说法尚未得到环卫办的证实，本报将持续关注此事进展。</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('443', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27606256\' f_id=\'3\' -->\r\n	<p>　　温都讯 昨晚，市公安局机场分局对&ldquo;<a href=\"http://news.sina.com.cn/s/p/2013-07-01/031927539435.shtml\" target=\"_blank\">6&middot;28瑞安籍乘客殴打国航地勤人员</a>&rdquo;一事作出处理：对殴打国航地勤人员朱某某(女)的陈某某(女)、刘某某(女)均给予行政拘留5日并处200元罚款的处罚。</p>\r\n	<p>　　记者了解到，昨天这两名女乘客从北京返回温州后，到候机楼派出所接受处理。据候机楼派出所相关负责人介绍，此前殴打一事发生后，他们调取现场的监控录像，并向现场目击者了解情况，最终作出了这一处理决定。</p>\r\n	<p>　　机场分局认定，两名女乘客存在殴打朱某某的事实，但殴打结果不构成轻伤，因此对两人给予行政拘留及罚款的处罚。</p>\r\n	<p>　　6月28日晚，瑞安一批旅客搭乘的国航CA1812，原计划于当晚9时飞往北京。因北京天气的原因，到了晚上11时30分，国航决定取消该航班，次日不补班。旅客陈某某、刘某某对航空公司的安排不满，在5号登机口与前来安排航班后续工作的国航工作人员朱某某发生争执，随后两人殴打朱某某。</p>\r\n	<p>　　最近几天，国内多个地方因雷雨天气，出现大面积航班延误或取消。7月6日凌晨，上海虹桥个别旅客因航班取消情绪激动，殴打航空公司地勤人员，还有人哄抢民警工作证件。机场警方将相关当事人带走调查，随后对4名肇事者分别处以拘留及罚款。温都记者 吕进科</p>\r\n	<p>&nbsp;</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('444', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\">\r\n	<div class=\"img_wrapper\" style=\"text-align: center\"><img alt=\"\" src=\"/u/cms/www/201308/281533525eqt.jpg\" style=\"width: 450px; height: 600px\" /></div>\r\n	<div class=\"img_wrapper\" style=\"text-align: center\"><span class=\"img_descr\">男子包裹里全是此类艳照敲诈信</span></div>\r\n	<div class=\"img_wrapper\" style=\"text-align: center\"><img alt=\"\" src=\"/u/cms/www/201308/28153415wq4t.jpg\" style=\"width: 450px; height: 600px\" /></div>\r\n	<div class=\"img_wrapper\" style=\"text-align: center\"><span class=\"img_descr\">艳照敲诈信</span></div>\r\n	<!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27606407\' f_id=\'3\' -->\r\n	<p>　　荆楚网讯 (记者翟方)&ldquo;你好！我是xxx新闻网的记者，我已查到你生活中不为人知的另一面，只要我在网上一传，你将前途尽毁甚至面临牢狱之灾&hellip;&hellip;三天内汇给我25万元人民币，我就毁灭证据。&rdquo;</p>\r\n	<p>　　7月3日下午2时54分，一中年男子走进襄阳市樊城区一家快递公司，准备向多个地区快递100多封有这样内容的信件，收件人全部是一些单位的领导干部，信件内还附有这些官员的&ldquo;艳照&rdquo;。</p>\r\n	<p>　　监控录像显示，该男子戴着眼镜，身着灰色休闲短袖上衣和短裤，脚穿拖鞋，背着一个黑色包。该男子自称是襄阳某网站记者，要寄邀请函。随后，他从包中拿出了一个塑料袋，袋内装了100多封已封好的信件。快递员发现，信件收件人全是各单位的主要领导，而男子神情有异，于是进行试探，要求他自行打开信封接受检查，但男子称会弄坏邀请函，拒绝打开。</p>\r\n	<p>　　快递员掏出电话欲报警，让人意想不到的是，该男子撒腿就逃，工作人员死死抓住门把手不让他出门。僵持几分钟后，该男子丢下物品，跳窗逃走。</p>\r\n	<p>　　民警赶到后检查发现，该男子要邮寄的信件全是对各地官员的敲诈信，信中称有人对收件官员恨之入骨，于是私下花重金雇请该男子对其调查取证，目的是拿到证据后整垮收件官员。敲诈信中称，&ldquo;已查到你生活中不为人知的另一面&rdquo;，并威胁将会把资料传到网上。</p>\r\n	<p>　　同样在信中，该男子又变脸对收件官员&ldquo;大发慈悲&rdquo;，说双方无冤无仇，只要收件官员三天内汇款25万元，便会销毁所有证据或转交给对方，绝不会有第三人知晓。举报信中还留下了一个银行账号，户主名为&ldquo;刘细勇&rdquo;。信件中附有&ldquo;涉事官员&rdquo;裸体怀抱美女的艳照、记者证的封面照片、U盘照片等。快递员称，这些照片都很相似，疑为合成的。</p>\r\n	<p>　　警方当场扣下了这些信件。至昨日，寄信男子尚未落网，相关案情正在进一步调查中。</p>\r\n</div>\r\n<p>&nbsp;</p>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('445', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27606839\' f_id=\'3\' -->\r\n	<p>　　据江淮晨报报道，铜陵一男子在外花天酒地，借债一万元无钱归还，竟与朋友合伙骗自己老婆。7月3日，该男子声称自己被人&ldquo;绑架&rdquo;，要求妻子拿&ldquo;赎金&rdquo;一万元救自己。最后老婆没骗到，自己的朋友却因吸毒被公安机关依法行政拘留。</p>\r\n	<p>　　7月3日，铜陵市长江路派出所在日常接处警过程中，接到李女士报警，称自己的老公被人绑架了，对方要求她拿一万元钱去赎人。接警后，民警及时与李女士取得了联系。李女士称&ldquo;绑匪&rdquo;打电话给她，声称她老公吴某在他们手上，要求李女士马上拿一万元到某派出所门口，会有人跟她联系，和她接头之后，找她拿钱放人。</p>\r\n	<p>　　民警根据李女士提供的信息，在某派出所门口将前来拿赎金的&ldquo;绑匪&rdquo;王某抓获。通过对王某审讯，民警知道了&ldquo;绑匪&rdquo;的具体藏身地点。民警带着王某来到了某小区某栋某室，民警让王某敲开门，进入室内后，警方立即将房间里的三人控制住。在民警寻找被&ldquo;绑架&rdquo;的吴某时，有一人声称自己就是吴某，民警遂将三人带到了派出所。</p>\r\n	<p>　　经讯问，事情的结果让办案民警瞠目结舌。原来让人担心的&ldquo;绑架&rdquo;情节，竟然是吴某自导自演的一场闹剧，那几个所谓的&ldquo;绑匪&rdquo;都是他的朋友，目的就是骗老婆的钱，想不到老婆会报警。得知真相后，李女士告诉警方，她对老公非常失望。最后，吴某的朋友因吸毒被公安机关依法行政拘留。(记者 方佳伟 通讯员 汪江 吴彬)</p>\r\n	<p align=\"right\">(原标题：男子自导自演绑架为骗老婆一万)</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('446', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27606943\' f_id=\'3\' -->\r\n	<p>　　本报讯 李展辉 钟玲珑 首席记者程呈报道：新娘婚宴上逃婚，新郎痛苦不已索精神损害赔偿。7月4日，泰和县人民法院一审审理了一起婚约纠纷。</p>\r\n	<p>　　2012年9月，吴子华与女子黄晓菊谈起了恋爱，期间，黄晓菊不断向吴子华索要钱财，吴子华先后10次通过银行汇款近3万元给黄晓菊。双方商定2013年4月举行婚礼。结婚当日近百人到现场祝贺，黄晓菊却不同意结婚，并驾车迅速逃离。吴子华将黄晓菊起诉至法院，要求黄晓菊返还其财产2.95万元，并赔偿其精神损失2万元。</p>\r\n	<p>　　法院一审认为吴子华提出要求被告黄晓菊赔偿精神损害赔偿符合法律规定，但其诉请过高，应予核减，故判令黄晓菊返还吴子华财产2.95万元，支付精神损害赔偿5000元。(文中人物均属化名)</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('447', '<div class=\"blkContainerSblkCon BSHARE_POP\" id=\"artibody\"><!-- publish_helper name=\'原始正文\' p_id=\'1\' t_id=\'1\' d_id=\'27606968\' f_id=\'3\' -->\r\n	<p>　　东南网7月8日讯(海峡导报记者 蔡晶晶 通讯员 杨媛 林曼娜)酒后翻墙进别人家，不顾女主人怀有9个月身孕，竟想实施强奸，没能得逞的他还用刀划伤孕妇的婆婆。昨日，漳州市漳浦县检察院以涉嫌强奸罪对黄某龙依法批准逮捕。</p>\r\n	<p>　　4月19日晚上，家住漳浦县深土镇的犯罪嫌疑人黄某龙酒后经过邻居王某家，见王某房间的灯还亮着，于是翻墙而入径直从楼梯上二楼王某的房间。见到王某在屋里看电视，黄某龙关灯将王某按倒，打算强奸她，全然不顾王某已怀有身孕9个月，在王某奋力挣扎并苦苦求饶时，黄某龙仍揪住王某的头发把她拖到床边。此时，睡在床上的王某女儿惊醒，见母亲嘴角流血，便大声哭喊，黄某龙害怕孩子的哭声引来他人，才翻墙离开。</p>\r\n	<p>　　离开王家不一会儿，黄某龙发现自己的手机不见了，怀疑掉落在王某家中，竟在路边捡了把菜刀又折回王某家，打算和王某家人&ldquo;理论&rdquo;，其间挥刀划伤王某的婆婆。后来在邻居的劝架下黄某龙才离开，王某一家随即报警。</p>\r\n</div>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('448', '<p>本报深度记者 朱洪蕾</p>\r\n<p>&ldquo;短时间内杀了七条命，那么多警察，竟然抓不到我，老天助我也，看来我还要继续作案杀人!&rdquo;湖南武冈少年刘洋(化名)安然地坐在网吧里的电脑前敲出这句话。</p>\r\n<p>两个星期内，他和同伴付云强(化名)连续在湖南、广东、云南等地下手，就像是与时间在赛跑。</p>\r\n<p>这场&ldquo;杀人游戏&rdquo;的唯一赌注仅仅是为了获取更多的钱，在他俩看来，只要比打工赚得多。</p>\r\n<p>[NextPage][/NextPage]</p>\r\n<p>而在游戏开始前的半年，两个少年都还坐在学校里，身上并没有显露丝毫邪恶的印迹。</p>\r\n<p>刘洋和付云强曾经是同班同学，同住一个宿舍的上下铺让他俩有了更多的共同话题，相似的家庭背景让两人有了共同的语言。</p>\r\n<p>刘洋出生于1994年，付云强比他还小一岁。父母常年在外打工，刘洋从小就跟着外公外婆长大，只在暑假的时候，才会去爷爷奶奶家住一段时间，与父母相处的时间就更少了。</p>\r\n<p>付云强的家里还要困难，母亲患有眼疾，经常看不清东西，村里人背后就叫她&ldquo;瞎子&rdquo;，没法出去打工，挣钱的任务就交给了付云强的父亲和姐姐。</p>\r\n<p>即便如此，两个家庭都希望孩子能考上大学，用知识来改变生活。</p>\r\n<p>刘洋的成绩曾经在班里名列前茅，还是老师眼里的乖孩子和小才子，作文写得好，画也画得好。</p>\r\n<p>眼看着初中要读完了，一切却都变了。他的一个叔叔和疼爱他的奶奶接连去世，刘洋深受打击，他常想&ldquo;为什么好人不长寿&rdquo;，他的心思也跟着变了，上大学不再是追求的目标。初三下学期，刘洋开始迷恋于上网，经常课也不上就泡在网吧里。幸亏之前的学习底子还比较牢固，顺利帮助他升入高中。</p>\r\n<p>在高中，刘洋遇到了付云强。付云强有些孤僻，不怎么喜欢跟别人交流，老是心事重重，即便老师问他，他也不愿意多说。</p>\r\n<div style=\"page-break-after: always;\"><span style=\"display: none;\">&nbsp;</span></div>\r\n<p>经常上网的刘洋高中成绩一路下滑，甚至受到了老师的劝退。父母觉得是自己没照顾好他，就把他接到身边，希望他能好好读书，但刘洋依然我行我素。</p>\r\n<p>付云强的成绩一直垫底，眼见考大学无望，家里又不富裕，跟父亲商量后，2012年过完春节后不久，他就辍学到外地打工去了。</p>\r\n<p>2012年8月的一天，两个少年又在网上相遇。彼时的刘洋正拿着从家里偷来的几百元钱，泡在县城的网吧里，而不是按学校的要求乖乖呆在学校里补课。</p>\r\n<p>半年未见，付云强聊起了自己的打工生活。他认为，外面的世界很精彩，但是通过打工赚大钱实在太难了。两人在网上一拍即合，决定结伴儿出走，跟电影里演的黑社会一样，通过不正当的手段弄钱花。</p>\r\n<p>不几天，付云强怀揣打工挣来的几百块钱回到了武冈，一来就先给拮据的刘洋买了一堆吃的，让他改善生活。</p>\r\n<p>两人坐下来商量，打工赚钱不好赚，还经常有人拖欠工资，想要赚钱的话，抢劫才是最快捷的方式。考虑到两人都不高，而且身形偏瘦，在城里抢劫肯定不易下手，就把视线转向了农村。</p>\r\n<p>说干就干，付云强买来两把刀，8月21日，两人冒冒失失地到武冈市湾头镇的张某家里打劫。张某夫妇声称没钱，只给了两人90多块钱。一怒之下，他们对着夫妇俩刺了数刀，夫妇俩当场殒命。其实付云强一开始还不想杀人，但怕刘洋说他不够义气，就合力制造了这场血案。</p>\r\n<p>之后，两人赶紧动身来到东莞，继续寻找作案目标。8月25日，以热水器坏了为由，两人将女房东骗至出租屋，持刀威胁并绑住女房东，搜走了现金3000余元和银行卡，在女房东拒绝说出银行卡密码后，两人将她当场刺死。一不做二不休，等到男房东晚上回家，他们又以换房为由将他骗出，逼他说出银行卡密码后将他捅死。</p>\r\n<p>刘洋和付云强像是着了魔。8月29日，两人跑到云南钻进曲靖市富源县大河镇唐某家，将唐某捆绑后抢到90元钱，怕他报警，就将他勒死，然后扬长而去。</p>\r\n<p>是夜，两人住到富源县营上镇吴某家的旅馆。第二天上午，两人让吴某帮忙换牙刷，吴某就背着两岁大的女儿来到两人住的房间。在房间里，他们将怀孕五个月的吴某杀死，看到吴某两岁的女儿在一旁啼哭不止，担心引人注意，又用被子将小女孩捂死，然后来到吴某的卧室，将所有值钱的东西都拿走，再辗转逃到深圳。</p>\r\n<p>其间，两人不忘在网上跟网友分享他们的杀人经历，还收到了&ldquo;真酷!&rdquo;的一致评论。</p>\r\n<p>来到深圳后，两人打算消停一段时间，准备安心打工。刘洋戴着眼镜看着还比较斯文，就被一家饭店留下了，而付云强一时找不到工作，随后决定到上海去投靠一个朋友。</p>\r\n<p>9月6日傍晚，火车还在江西境内，付云强就被铁路公安抓获。随后，刘洋也在深圳落网。</p>\r\n<p>面对警方，两人非常坦然：&ldquo;我杀了人，我在湖南、广东、云南都杀了人。&rdquo;两人面无惧色地将作案情节从头到尾做了完整的复述。</p>\r\n<p>[NextPage][/NextPage]</p>\r\n<p>这一切让人感到无法理解。在后来的庭审中，休庭后，法庭特意邀请心理咨询师对二人分别进行心理评估。</p>\r\n<p>在与心理咨询师沟通后，两人才开始意识到自己的错误。社会调查员通过跟两人接触，认为两人都是留守儿童，父母关爱不够，最后走错路，很大程度上应归结于整个社会的大环境。</p>\r\n<p>在被看押期间，刘洋对自己之前的行为写了一些感受，在感受中，他提到，&ldquo;过去不会思考别人，只会想到自己&rdquo;，&ldquo;自己的冲动做法，害了这么多家庭&rdquo;，&ldquo;不管如何，都无法弥补了&rdquo;。</p>\r\n<p>确实无法弥补，在一审被判死刑后，刘洋会很快走到生命的尽头;付云强被判无期，以后的日子可能都得在监狱里度过了。</p>\r\n<p align=\"center\"><img align=\"\" alt=\"\" border=\"0\" src=\"http://demo7.11077.net/u/cms/www/201307/101732351isd.jpg\" /></p>\r\n', '<p>\r\n	<img alt=\"\" src=\"/u/cms/www/201307/101732073o7k.jpg\" style=\"width: 600px; height: 900px;\" /></p>\r\n', null, null);
INSERT INTO `jc_content_txt` VALUES ('495', '<p style=\"text-align: center\"><span><font color=\"#000000\"><img _fcksavedurl=\"http://pnewsapp.tc.qq.com/newsapp_bt/0/8498180/640\" alt=\"刘晓庆今日大婚 第四任老公系将门之后政协委员\" src=\"http://pnewsapp.tc.qq.com/newsapp_bt/0/8498180/640\" /></font></span></p>\r\n<p style=\"text-align: center\"><span><a class=\"a-tips-Article-QQ\" href=\"http://datalib.ent.qq.com/star/182/index.shtml\" target=\"_blank\"><!--/keyword--><font color=\"#000000\">刘晓庆<!--keyword--></font></a><!--/keyword-->晒穿花裙子照。</span></p>\r\n<p align=\"center\" style=\"text-transform: none; font: 14px Arial, Verdana, sans-serif; white-space: normal; letter-spacing: normal; color: rgb(0,0,0); word-spacing: 0px; -webkit-text-stroke-width: 0px\">&nbsp;</p>\r\n<p style=\"text-align: center\"><img _fcksavedurl=\"http://pnewsapp.tc.qq.com/newsapp_bt/0/8500820/640\" alt=\"刘晓庆今日大婚 第四任老公系将门之后政协委员\" src=\"http://pnewsapp.tc.qq.com/newsapp_bt/0/8500820/640\" /></p>\r\n<p align=\"center\" style=\"text-transform: none; font: 10pt 宋体; white-space: normal; letter-spacing: normal; color: rgb(0,0,0); word-spacing: 0px; -webkit-text-stroke-width: 0px\">刘晓庆团队公布的William先生照片</p>\r\n<p align=\"center\" style=\"text-transform: none; font: 14px Arial, Verdana, sans-serif; white-space: normal; letter-spacing: normal; color: rgb(0,0,0); word-spacing: 0px; -webkit-text-stroke-width: 0px\">&nbsp;</p>\r\n<p style=\"text-align: center\"><img _fcksavedurl=\"http://pnewsapp.tc.qq.com/newsapp_bt/0/8500821/640\" alt=\"刘晓庆今日大婚 第四任老公系将门之后政协委员\" src=\"http://pnewsapp.tc.qq.com/newsapp_bt/0/8500821/640\" /></p>\r\n<p align=\"center\" style=\"text-transform: none; font: 10pt 宋体; white-space: normal; letter-spacing: normal; color: rgb(0,0,0); word-spacing: 0px; -webkit-text-stroke-width: 0px\">2010年，王晓玉以全国政协委员身份接受新华网采访</p>\r\n<p style=\"text-indent: 2em\">中新网8月21日报道，近日，关于刘晓庆秘密赴美完婚的新闻传得沸沸扬扬，占据各大娱乐版块。8月20日早上，刘晓庆团队发表声明，透露期第四任丈夫王晓玉的情况。声明中透露刘晓庆与第四任丈夫已与去年注册结婚，声明中还称其丈夫比刘晓庆大，是将门之后，事业成功。晚些时分，记者致电刘晓庆身边工作人员，得知刘晓庆将于美国时间8月20日在美国举行婚礼。</p>\r\n<p style=\"text-indent: 2em\"><strong>老公揭秘：政协委员王晓玉</strong></p>\r\n<p style=\"text-indent: 2em\">据知情人士爆料，刘晓庆现任丈夫为商人王晓玉，2010年还担任过全国政协委员。</p>\r\n<p style=\"text-indent: 2em\">爆料称，刘晓庆现任丈夫为香港商人王晓玉，资料显示，1942年出生于安徽的王晓玉拥有众多头衔，包括中华海外联谊会理事、中华全国侨联委员、安徽省政协常委、香港安徽联谊总会会长、顺德锡山家具有限公司董事长、华美贸易公司总经理、第十届全国政协委员。据悉，王晓玉是黄埔后代。其父王剑秋是黄埔武汉分校七期毕业生。王晓玉投资家具厂多年，如今厂已经遍布广东、浙江、上海、山东、天津等地。资料显示，锡山家具专注于高端家具生产，多半出口海外，刘晓庆也曾入股家具连锁店。当时还打起了广告噱头，&ldquo;交一万元订金就可免费乘包机到成都，买2万元家具就可在本月19日与明星刘晓庆共进鸡尾酒会和看烟花。&rdquo;</p>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('496', '<p style=\"text-align: center\"><span class=\"infoMblog\"><img alt=\"赵本山女儿正式进军娱乐圈 将与潘长江女儿比拼\" src=\"http://img1.gtimg.com/ent/pics/hv1/150/212/1397/90894135.jpg\" /></span></p>\r\n<p style=\"text-align: center\"><span class=\"infoMblog\">潘阳</span></p>\r\n<p align=\"center\">&nbsp;</p>\r\n<p style=\"text-align: center\"><img alt=\"赵本山女儿正式进军娱乐圈 将与潘长江女儿比拼\" src=\"http://img1.gtimg.com/ent/pics/hv1/151/212/1397/90894136.jpg\" /></p>\r\n<p align=\"center\" style=\"font-family: 宋体; font-size: 10.5pt\">赵本山与女儿赵一涵</p>\r\n<p style=\"text-indent: 2em\">新快报8月21日讯 日前，浙江卫视宣布推出全国首档青年励志节目《我不是明星》，<!--keyword--><!--/keyword-->陈宝国<!--keyword-->儿子陈月末、潘长江女儿潘阳等30多位明星子女均已确定加盟。据透露，赵本山女儿赵一涵也有望加盟，藉此正式进军娱乐圈，并将与潘长江女儿潘阳同台对决。</p>\r\n<div __curdisplay=\"block\" class=\"mbSourceCardInfo\" style=\"display: none\">\r\n	<div class=\"arrowBox\">\r\n		<div calss=\"arrow\">&nbsp;</div>\r\n	</div>\r\n	<div __curdisplay=\"block\" class=\"mbloading\" style=\"display: none\">&nbsp;</div>\r\n	<div class=\"mbCardUserDetail\">\r\n		<div class=\"userPic\"><span class=\"infoMblog \"><a bosszone=\"followalltx2\" href=\"http://t.qq.com/chenbaoguo?pref=qqcom.keywordtx2\" rel=\"陈宝国(@chenbaoguo)\" target=\"_blank\" title=\"陈宝国(@chenbaoguo)\"><img src=\"http://t2.qlogo.cn/mbloghead/ec4910a28b805eb55ee4/50\" /></a></span></div>\r\n		<div class=\"userInfo\">\r\n			<div class=\"nick\"><span class=\"infoMblog \"><a bosszone=\"followallname\" href=\"http://t.qq.com/chenbaoguo?pref=qqcom.keywordname\" target=\"_blank\" title=\"陈宝国(@chenbaoguo)\"><span><font size=\"3\">陈宝国</font></span></a></span></div>\r\n			<div class=\"follower\"><span class=\"infoMblog \"><a bosszone=\"followalltz\" href=\"http://t.qq.com/chenbaoguo/follower?pref=qqcom.keywordtz\" target=\"_blank\" title=\"听众：896459人\"><span>听众：</span><span>896459人</span></a></span></div>\r\n			<div class=\"attentBoxWrap\" follow=\"0\" uid=\"chenbaoguo\"><span class=\"infoMblog \"><a bosszone=\"followallst\" class=\"addAttention\" href=\"javascript:;\" title=\"立即收听\"><span>+收听</span></a><a class=\"delAttention\" href=\"http://ent.qq.com/a/20130821/002333.htm#\" style=\"display: none\" title=\"已收听\"><span><font color=\"#666666\">已收听</font></span></a></span></div>\r\n		</div>\r\n		<div class=\"userNew\">\r\n			<div class=\"titleBox\"><span class=\"infoMblog \"><span>最新消息</span> <span class=\"timer\" rel=\"1322634633\" title=\"2011年11月30日 14:30\"><font color=\"#999999\">2011年11月30日 14:30</font></span></span></div>\r\n			<div class=\"news\"><span class=\"infoMblog \">感谢诸位朋友的关心，最近在家调整休息。近来天冷了，大家注意身体。<a bosszone=\"followallmore\" href=\"http://t.qq.com/chenbaoguo?pref=qqcom.keywordmore\" target=\"_blank\"><font color=\"#0b3b8c\">更多</font></a></span></div>\r\n		</div>\r\n	</div>\r\n</div>\r\n<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 《我不是明星》将邀请娱乐明星、社会名人子女来参赛，有哪些&ldquo;星二代&rdquo;来参赛？据节目组官方微博透露，参赛的选手父母都是众人皆知的一线大牌明星，目前起码有两个&ldquo;影帝&rdquo;、三个一线歌星以及三个上过央视<!--keyword--><!--/keyword-->春晚<!--keyword--><!--/keyword-->小品演员的子女都有意向参加。当中，既有小有名气的，也有尚不出名准备进军演艺圈的。其中&ldquo;影帝&rdquo;陈宝国的儿子陈月末、&ldquo;小品王&rdquo;潘长江的女儿潘阳等已确定加盟，并参与首期节目录制。另据知情人透露，成龙、<!--keyword--><!--/keyword-->曾志伟<!--keyword--><!--/keyword-->、<!--keyword--><!--/keyword-->王菲<!--keyword--><!--/keyword-->、赵本山、谢贤、赵雅芝、<!--keyword--><!--/keyword-->郭德纲<!--keyword--><!--/keyword-->等大腕明星子女也都在节目组的邀请之列，有传赵本山女儿赵一涵也有意借该节目正式进军娱乐圈。</p>\r\n<p style=\"text-indent: 2em\">据悉，赵本山女儿赵一涵多才多艺，几年前远赴新加坡一家知名演艺学校求学，这次加盟也是其国内综艺节目的首秀。值得一提的是，同为东北两大&ldquo;小品王&rdquo;后代，赵一涵和潘阳两人同台对决也备受外界期待，网友笑言，这回两人不仅拼才艺还拼爹。对于赵一涵加盟的消息，节目组表示正积极接洽中。</p>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('498', '<p>&nbsp;</p>\r\n<p style=\"text-align: center\"><!--/keyword--><img alt=\"冯小刚定春晚相声小品类基调：唱赞歌一律不要\" src=\"http://img1.gtimg.com/ent/pics/hv1/10/183/1398/90951625.jpg\" /></p>\r\n<p style=\"text-align: center\">冯小刚<!--keyword--></p>\r\n<p style=\"text-indent: 2em\">成都商报8月21日报道，前日，刚从国外归来的马年<!--keyword--><!--/keyword-->春晚<!--keyword--><!--/keyword-->总导演冯小刚与副总导演赵本山、艺术顾问张国立、刘恒和策划张和平等春晚智囊团首次召开了语言类节目碰头会。此次碰头会从早上开到晚上7点半，&ldquo;贺岁王&rdquo;与&ldquo;小品王&rdquo;在走马上任后的首次碰面为春晚小品、相声等语言类节目的总基调&ldquo;一锤定音&rdquo;。记者获悉本次开会气氛非常轻松，而总导演冯小刚则把敢说敢言的个性展现得淋漓尽致。前日他明确表示，相声小品不能束手束脚，要解放思想，颂赞歌的作品一律不要出现，最喜欢有尖有刺的东西。</p>\r\n<p style=\"text-indent: 2em\">作为电视节目导演界的&ldquo;新人&rdquo;，冯小刚不改其在春晚发布会上的态度，坚决要把针砭时弊的东西带入到语言类节目创作中去，冯小刚曾对春晚语言类节目提出了一个四字方针&mdash;&mdash;&ldquo;干预生活&rdquo;，希望作品能回归讽刺辛辣幽默。前日的碰头会上，冯小刚大胆地提出要摒弃那些煽情、唱赞歌的东西，所有创作者要放开手脚，敢写敢说，呈现出带尖带刺、有棱有角的相声和小品来。冯小刚举例子说，他特别推崇上个世纪八十年代马季和姜昆的讽刺相声，他更举了马季的《宇宙牌香烟》、姜昆的《虎口遐想》和《电梯风波》等作品，认为这才是真正针砭时弊，贴近生活的好作品。</p>\r\n<p style=\"text-indent: 2em\">据悉，赵本山在会上并未发表过多见解，相反多是倾听冯小刚的意见。而尹琪在接受记者采访时则表示，往年春晚作品的创作时间大概在11月份，自己参加本次碰头会也意味着&ldquo;赵氏&rdquo;春晚作品创作时间提前到八月下旬，整整提前了三个多月。</p>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('499', '<p style=\"text-indent: 2em\">我观察到一个现象，就是目前在很多领域，大家对于终极价值的关怀是不够的。人们往往会较多关注法律规定对现实活动的具体影响，而容易忽略法律规定背后所应遵循的基本准则，特别是容易忽略法律的终极价值或伦理价值，这样就缺乏一个最终端的评价标准。</p>\r\n<p style=\"text-indent: 2em\"><strong>社会要公正，就必须透明</strong></p>\r\n<p style=\"text-indent: 2em\">首先是判断终极价值的现实体现，即正义是社会制度的首要价值。正义是非常抽象的，我们要把它落到现实当中去，需要通过具体的原则和方法。在现实社会当中很多发达国家不大讲公开，他们讲&ldquo;透明&rdquo;。透明这个词更加鲜活、精准。我认为一个社会要公正，就必须透明。</p>\r\n<p style=\"text-indent: 2em\">我们交了税以后，要获得的是一种公共产品或者公共服务。如果我们交了税以后，政府不能提供给我们最起码的公共服务，这个财政的正义就缺乏了，这是最基本的要求。比如说农村公共设施是非常落后的，怎么来保障财政正义的实现呢？首先在制度层面必须要明确不同种类的公共产品，在不同政府的分工。我们并没有看到这么一种明确的法律规定，这样的规定应该列入《宪法》。在操作层面、技术层面，我们很希望看到有一个清单，能够告诉民众哪样的公共服务由哪一级政府来提供。</p>\r\n<p style=\"text-indent: 2em\">比如义务教育的问题在于供应不足。在2000年以前，我们义务教育的经费是乡镇政府来保障的，乡镇政府来为义务教育提供服务。后来改为县政府来提供，义务教育到底应该由哪一级政府提供呢？我认为这是中央政府的责任，这个钱可以由中央来出，事情由地方来做。</p>\r\n<p style=\"text-indent: 2em\">政府除了收费、收税之外，它还掌握了一个公共资源。它的收费、租金，包括转让的价格都应该基于市场原则，也就是说政府应该把那些资源给出价最高的人。如果是低价给人家，或者说免费提供给经营者就侵害了老百姓的利益。</p>\r\n<p style=\"text-indent: 2em\">公共罚没收入应以促进法律遵从为原则，如果变成了以收入为目的的话，就完全背离了正义。开车的人都特别担心被贴罚单。什么叫&ldquo;违章停车&rdquo;？有两种原则来定义：一种是交管部门划出一个特殊的区域，说这里是可以停车的，其他地方不能停车。还有一种是划出一个区域，说这个地方不能停，其他地方都可以停车。我们现在采用的是第一种原则，如果采用第一种原则，我们被罚的概率就会增加很多。交管部门很容易就会抓到你，这个执法的弹性就很大。</p>\r\n<p style=\"text-indent: 2em\">&ldquo;罚款发工资&rdquo;是什么意思呢？有一些工作的人手不够，就要聘一些临时工，他们就是靠罚款来养这些人。如果是这样的话，我认为就违背了正义，如果这些临时工确实是工作需要的，就应该让他进编制，你不应该用罚款来保障他。当公共处罚以谋求收入为目的的时候，就与正义背道而驰了。量能课税应合乎比例原则，分担税负的两个原则是受益原则与能力原则：首先采用受益原则将各种特别公共基金与一般公共基金相区别；然后，对于一般公共基金采用能力原则。量能负担原则的衡量指标选择，是收入、消费还是财产？</p>\r\n<p style=\"text-indent: 2em\">量能负担原则的基本含义是没有能力的不交税，能力强的要比能力弱的多交税。所以收入多的人应该比收入少的人多交税，消费多的人应该比消费少的人多交税，财产多的人应该比财产少的人多交税。问题是怎么来确定具体的数额？人头税是否公平呢？当人人都差不多的情况下是公平的，但是现实中人和人的差别很大，所以人头税是不公平的。比例税是不管收入、财产、消费数额多大，都是统一比例的。还有一种标准是累进税，就是说你的收入超过的部分越高，就逐步增加。有的人说累进好，有的人说比例好。比例和累进都能够体现能力原则，当然程度不一样。累进的程度可能更多一点，但是累进不能解释一个能力强的人和一个能力弱的人相比，在多大程度上应该多交多少税。我们人类的理性无法回答这个问题，就只有在实践当中，大家通过互相博弈来解决这个问题。也就是说写在法律当中的，看起来很美，很有逻辑的累进税制，可以说暗含着一些不公平，有一定的武断性。比例税制由于不歧视，对所有人都一样，某种程度上又是公正的。而且比例税制还有一个好处，就是可以消除累进税制规避税收的机会。我的倾向性意见是认为比例税制比较好。</p>\r\n<p style=\"text-indent: 2em\">以上是一些财税正义的具体制度，可是我们发现财和税是不可分割的。你征税是为了某种支出，我们前面讲的是收入取得过程当中的正义。你在收入的时候很正当，但是你拿了钱以后做不正当的事情，我们会发现这都是虚假的。所以财政的收入应该从整体上考虑，也就是说必须要有收支相连的思路来考虑。如果把收和支割裂开，只考虑收入的公正不考虑支出的公正，或者反过来说，我认为在逻辑上会有混乱，而且在实践当中会产生问题。收支相连才能全面落实纳税人的权利，充分体现财税制度的正当性。</p>\r\n<p style=\"text-indent: 2em\"><strong>财税正义应该贯穿终生</strong></p>\r\n<p style=\"text-indent: 2em\">现在提出了一个&ldquo;环保税&rdquo;，有多少人是赞成&ldquo;环保税&rdquo;的？一般认为需要征的逻辑是觉得中国的环境很差，需要征税来保护环境。中国现在环境问题如此的严重，是不是因为征税不足造成的？好像不是，如果不是的话，你这个征税到底能在多大程度上缓解，或者说解决这个环保问题呢？这就让我们思考一个问题，就是为什么要征税？我认为政府向老百姓征税还有其他的原因，它没有说出来的原因就是为了拿钱。钱不够向大家要税，这是不是该有一个正当理由？如果政府告诉大家现在我提供这么多公共服务，确实是钱不够了，老百姓是同意的。你已经收了很多的钱，你又不能说缺钱，你又不能说清楚已经收的钱都已经得到了充分的使用，没有浪费、贪污，你为什么还要向我征税？调控是不是一个正当的理由？在我看来调控论有一定的道理，但不是所有的事实都能支撑它。</p>\r\n<p style=\"text-indent: 2em\">&ldquo;公款不得私用&rdquo;好像是一个尝试，而不是一个原则。什么时候这种公款私用是正当的？第一，个人生活极端的贫困，处在我们贫困线以下，所以我们拿一部分公款来救助他。我认为这就是公正的，因为每个人可能都有这种活不下去的时候。第二，如果个人遭受了公共权力机构的侵害，他应该得到一个补偿，这个给他也是正当的。第三，人人有份，就像澳门一样发一个年终红包。超出这些范围的，我觉得就不太正当了。</p>\r\n<p>[NextPage][/NextPage]</p>\r\n<p style=\"text-indent: 2em\"><strong>下面我举例讨论：</strong></p>\r\n<p style=\"text-indent: 2em\">1.经济适用房由政府买单是否涉嫌公款不当私用？谁在买经济适用房？能买得起经济适用房的人最起码不是最穷的人，在大城市里面这个补贴是很大一部分资金。</p>\r\n<p style=\"text-indent: 2em\">2.公共奖励金及其免税是否具有正当性？奥运会得奖了，我们要不要拿钱来奖励他？中科院的院士每年有1万元的津贴，我们给他免税，你认为正当吗？</p>\r\n<p style=\"text-indent: 2em\">3.政府官员公车补贴是否正当？这就变成了一种隐性的福利，为什么他们不坐公交车，不骑自行车。如果公车公用的话，我认为一个城市里面可以有一个集中的统一调动中心，为什么每个干部都配公车？</p>\r\n<p style=\"text-indent: 2em\">4.政府招商引资时实行的&ldquo;财政返还&rdquo;政策是否具有正当性？这些都是公款，很多政府在花钱的时候，到底有没有正当性的支持？现在我们都默认了，随便怎么用。</p>\r\n<p style=\"text-indent: 2em\">不同财政收支系统的自我平衡原则。财政有不同的收支系统，比如说社保是一个系统，政府经费是另外一个系统，政府有一些基金项目也是独立运行的系统。社保基金的钱不可以跟其他的基金划转，如果划转了就破坏了公正，这也是非常重要的整体原则。</p>\r\n<p style=\"text-indent: 2em\">真正的财税正义，不仅仅是在某个时点或特定时期内的公平正义，应该是终生的。如果有一个办法可以把不同的人，一生当中所得到的财政利益和财政负担计算出来，这个终生正义就有一个基础了。税收优惠是非常普遍的一个现象，这个税收优惠的正当性在哪里？到底该还是不该？有些行业经常会说我这个行业需要发展，当然，特殊的阶段性，短期考虑是有理由的。所以从这个意义上讲，优惠是可以，但是必须要有期限。你不能永远给它优惠，必须要有期限的优惠，没有期限的优惠我认为都是有问题的。</p>\r\n<p style=\"text-indent: 2em\">代际间的公平公正，也是社会制度需要考量的重要方面。社会要发展，人类要进步，所以当代人为后代人多做点努力是正当的。如果反过来说，当代人去侵害后代人的利益就有问题了。当代人忽视后代人的利益，因为后代人还没有出现，你就把他们本该拥有的东西给侵占了，这就违反了代际正义。这种情况一定要通过制度约束，约束当代人过度侵害后代人的情况。这些在财政方面也有一些考虑，但是还不够。比方说资本预算制度就是这样一种考量，如果借钱的话就要通过资本运算来解决。</p>\r\n<p style=\"text-indent: 2em\"><strong>技术手段支持构建&ldquo;税制梦&rdquo;</strong></p>\r\n<p style=\"text-indent: 2em\">最后我想谈一谈我的&ldquo;税制梦&rdquo;，就是基于我前面对正义的考虑。我觉得在现代网络化大数据的时代，可以构想出一个简单透明的制度。就是在国家统一构建一个集中信息处理系统，任何一个单位、个人都在这个系统里面联起来，都有唯一的账号，我觉得在技术上是可以实现的。</p>\r\n<p style=\"text-indent: 2em\">有了这个东西之后，这个账户跟所有的金融机构联网，还要考虑和所有的物流系统联网。联起来以后，征税就会变得非常简单。我们每个人在银行里面都开户，只要有一笔钱进到任何一家银行，那个统一系统的账户里面就可以反映出来。征税方式就是系统自动扣缴，这个时候就不需要税务局了，因为系统可以自动实现把你的税划转到国库。这样就不需要企业交税了，所有的税都可以让个人去交。只要个人有收入，银行系统就自动给你划转。只要你有消费，消费的时候划卡，自动跟你的收款机联起来了，系统里面就自动显示出你买了东西。这样就不用带任何的现金，都是通过网络来实现。我觉得技术上应该是可以实现的，在我的构想当中，这个事情好像很简单。如果这样做的话，你连贪污都不可以了，因为都透明了。</p>\r\n<p style=\"text-indent: 2em\">如果这个构想可以实现，第一，不需要税务局；第二，不需要申报；第三，中介机构不需要有了，逃避税的空间狭窄了。这都是一种构想，不过我认为它不是一个空想，不是一个乌托邦的空想，因为它有技术的可行性支撑。以往很多的构想实现不了，因为它没有技术的条件。我这个设想只是基于现有的基础条件，说不定将来全球一统，没有国家的差别也有可能。我认为这些挑战主要问题还不是技术方面，主要问题是制度、法律、文化、观念、利益阻挠等。这种变革一定是有一个很长的过程，但是不管怎么说，有了这种大方向和大目标的引导，我们就可以通过代代相传的努力来实现它。有了方向的引导，我们才知道那才是我们应该去的地方。所以希望我的税制梦不是梦，希望它成为一个现实。</p>\r\n<p style=\"text-indent: 2em\"><strong>嘉宾简介：</strong></p>\r\n<p style=\"text-indent: 2em\">朱为群，上海财经大学公共经济与管理学院教授、税收系主任、税收学博士生导师，兼任中国税务学会理事、中国国际税收研究会理事、中国财税法研究会理事、全国财政学教学研究会常务理事等职。</p>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('500', '<p style=\"text-align: center\"><img src=\"http://t2.qpic.cn/mblogpic/26276de504f16f64b6f0/2000\" /></p>\r\n<p>&nbsp;</p>\r\n<p>几千年来，谣言不绝如缕。</p>\r\n<p>在古代，因为交通、通讯、传媒、人口等限制，谣言传播的速度与规模不能与近世相提并论。1768年（清乾隆三十三年），一则关于妖术的谣言竟迅速演化成一场全国性的大恐慌，影响波及数千万人，孔飞力在其名著《叫魂&mdash;&mdash;1768年中国妖术大恐慌》中对之作出了精彩叙述与分析。</p>\r\n<p>乾隆帝残酷查处了&ldquo;叫魂案&rdquo;，一颗颗人头像韭菜一样被割掉，并且不可能再长出来。乾隆帝恐惧的并非谣言本身，而是谣言导致的社会集体心理波动与行为变异，更重要的是，通过对谣言案的深入观察，乾隆帝发现自己很可能已经在常规领域失去对官僚的有效控制。上述种种，都对这个异族政权的合法性和稳定性形成威胁，统治者必须严肃应对。</p>\r\n<p>乾隆朝是所谓清代最后一个盛世，嘉道之后，乱世来临，大规模谣言泉涌而出。</p>\r\n<p>在太平天国与清军的对战中，双方都毫不客气地使用了谣言这种非常规武器。洪秀全散播谣言说：&ldquo;予细查满鞑子之始末，其祖宗乃一白狐、一赤狗，交媾成精，遂产妖人，种类日滋，自相配合，并无人伦风化&rdquo;&ldquo;前伪妖康熙暗令鞑子一人管十家，淫乱中国之女子，是欲中国之人尽为胡种也。&rdquo;</p>\r\n<p>清军也立即展开谣言反击战，散播说：&ldquo;（太平军）所过之境，船只无论大小，人民无论贫富，一概抢掠罄尽，寸草不留&rdquo;、&ldquo;其虏入贼中者，剥取衣服，搜括银钱，银满五两而不献贼者，即行斩首。&rdquo;据说曾国藩还炮制了民谣，派人四处传说：&ldquo;天父杀天兄，江山打不通。长毛非正主，依旧让咸丰。&rdquo;</p>\r\n<p>我们当然不能将曾国藩的胜利归于造谣术，但如果他未能有效化解对方的谣言战术，并在己方谣言之上制造并传播新的意识形态（其根基就在《讨粤匪檄》一文），可能胜利会来得更晚。</p>\r\n<p>谣言本身或无法改变历史，但很可能成为历史巨变中的一个关键因素。社会运动有时不需要真相，一个谣传引发的骚动，也可能改变历史。很多人内心追求的未必是真相，而是一场巨变。</p>\r\n<p>1911年10月9日下午，革命党人在汉口不慎引爆炸药。大约同时，&ldquo;清政府正在捕杀革命党人&rdquo;的谣言，于新军中广传。到10月10日，这个谣言更加具体了&mdash;&mdash;&ldquo;清政府正在捉拿没有辫子的革命党人&rdquo;&ldquo;官员已经掌握革命党人的花名册&rdquo;。</p>\r\n<p>当时的新军士兵，不少人都没有辫子，传说中的花名册又都没见过，谁知道自己在不在其中呢？恐惧在新军中蔓延，恐惧滋生新的谣言，新的谣言反过来又加深恐惧。这时候，参加兵变就成了多数士兵自保的最优选择。10日薄暮，一个排长查哨时的普通纠纷，竟激成哗变，最终引发连锁反应，导致辛亥鼎革。</p>\r\n<p>谣言为何能轻易地使人接受并参与传播？1942年美国两位学者做了一个谣言传播与接受的研究，计算出一个&ldquo;信谣指数&rdquo;，结果发现：穷人比富人更易信谣，45岁以上的人比年轻人更易信谣，犹太人比非犹太人更易信谣。</p>\r\n<p>穷人更易信谣是因为他们渴望改变现状；45以上的人更易信谣是因为他们的信息渠道与信息分析能力相对落后；犹太人更易信谣则是因为在战时，犹太人比一般人更缺乏安全感，而当时的谣言往往又是让人恐惧的那种。</p>\r\n<p>法国学者让-诺埃尔《谣言&mdash;&mdash;世界最古老的传媒》一书，对谣言有新颖而独到的见解。他认为，谣言经常是&ldquo;真实的&rdquo;，它之所以令人不舒服，是因为权力无法控制这种信息。</p>\r\n<p>在任何一个地区，当人们希望了解某事而得不到官方答复时，谣言便会甚嚣尘上。谣言是信息的黑市。辟谣往往制止不了谣言，因为谣言不是福尔摩斯，对真相充满感情，谣言是聚集着仇恨的女巫，它只说出人们认为应该如此的&ldquo;事实&rdquo;。人们看上去是在传播新的谣言，实际上是在清算旧账。辟谣注定是无力的，因为辟谣会破除人们的幻想，给狂热者当头浇一盆雪水，唤他们回到平庸的现实中来，爱做白日梦的人们当然不肯买账！</p>\r\n<p>谣言既是社会现象，也是政治现象，它是一种反权力，揭露秘密，提出假设，迫使当局开口说话。谣言还是社会群体心理结构的镜子，因此不论真假，谣言都是有价值的。</p>\r\n<p>在我看来，谣言不但是社会的、政治的，也是历史的。谣言不但可能成为历史事变的导火线，并且可能成为历史事变的解说者。历史中充斥着太多谣言，有些被当场击毙，有些则轻松逃脱，在漫长的时间河流中演变成都市传奇或历史神话。</p>\r\n<p>都市传奇是谣言的连续剧。譬如针刺狂的谣言，1922年在法国巴黎一度盛行，80多年后在中国大陆又化身&ldquo;艾滋针刺狂&rdquo;的传说不胫而走。</p>\r\n<p>神话则是谣言的终极形式。譬如义和团运动，在1901-1920期间曾被认为是愚昧、迷信、野蛮的神话；在1924-1937却被认为是饱含民族自尊与抗击热情的反帝国主义的正义神话；在文革期间更被指认为反封建、反帝国主义的伟大群众运动的神话，且间接为红卫兵哺乳；上世纪80年代以降，它又重返愚昧、野蛮、疯狂的神话，只是不时仍蒙着一层爱国主义的遮羞布。</p>\r\n<p>那么，历史真相究竟如何？然而，&ldquo;绝对客观的历史真相&rdquo;，本身也许就是一个最大的历史神话。</p>\r\n<p>【笔者按：据京华时报报道，昨天全国公安机关集中打击网络有组织制造传播谣言等犯罪专项行动拉开序幕。秦火火、立二拆四等推手被北京警方刑拘，其网络推手公司被查。】</p>\r\n<p>附，《叫魂&mdash;&mdash;1768年中国妖术大恐慌》《谣言&mdash;&mdash;世界最古老的传媒》相关信息：</p>\r\n<p>（1）《叫魂&mdash;&mdash;1768年中国妖术大恐慌》</p>\r\n<p>作者：（美）孔飞力；译者：陈兼/刘昶；出版社：生活&middot;读书&middot;新知三联书店；出版年：2012-5；ISBN：9787108037909</p>\r\n<p>（2）《谣言&mdash;&mdash;世界最古老的传媒》</p>\r\n<p>作者：（法）让-诺埃尔&middot;卡普费雷；译者：郑若麟；出版社：上海人民出版社；出版年：2008-12；ISBN：9787208076495</p>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('501', '<p style=\"text-indent: 2em\"><strong>【编者按】</strong>二战战败，日本在屈辱和不情愿中，被迫接受了麦克阿瑟的改革。麦克阿瑟通过修改宪法、财阀解体、土地改革等一连串的政策，使满目疮痍的日本迅速崛起。麦克阿瑟在日本到底做了什么？8月14日下午北京涵芬楼书店，旅日作家俞天任先生做客燕山大讲堂发表题为&ldquo;谁在统治日本&rdquo;的演讲，以下为文字实录：</p>\r\n<p style=\"text-indent: 2em\">1945年8月30日道格拉斯&middot;麦克阿瑟五星上将乘坐的&ldquo;巴丹号&rdquo;降落在神奈川县厚木海军飞机场，那时候日本并没有解除武装，他心里没有底，而且在太平洋战争中见识过喊着&ldquo;天皇陛下万岁&rdquo;，冒着枪林弹雨不知道&ldquo;死&rdquo;是什么往上冲的日本兵，虽然美国胜利了，但日本军队那种疯狂对麦克阿瑟来讲是恐怖的。所以于麦克阿瑟而言最重要的是不再让一个疯狂的日本出现。</p>\r\n<p style=\"text-indent: 2em\">他通过审判战犯、修改宪法、财阀解体、土地改革一连串的政策，使日本成为一个和平的国家。现在的日本，不管是言论还是举动，作为一个国家来讲，在这六十几年没干过什么坏事，一个军人把一个国家弄成这样，这是一个奇迹。</p>\r\n<p style=\"text-indent: 2em\">麦克阿瑟让占领军中的一些战前职业为律师的年轻军人们起草了一部&ldquo;和平宪法&rdquo;，这部宪法所反映的，实际上就是年轻人对于国家存在方式的政治理想。最后在一定程度上，麦克阿瑟还使用了刺刀，逼着日本人接受了这部宪法，来代替俗称为&ldquo;明治宪法&rdquo;的那部《大日本帝国宪法》。宪法只是描述了麦克阿瑟脑海里日本社会应有的形象，要达到这个目的，还得进行具体的工作，麦克阿瑟改造日本的过程，几乎就是分别对这六个权力集团采取不同的行动以瓦解或消除他们力量的过程。</p>\r\n<p style=\"text-indent: 2em\">麦克阿瑟采取的第一个行动就是解除日本的军事武装，这是在《波茨坦公告》中已经明言了的条件，因此麦克阿瑟没有遇到任何军事抵抗。根据联合国占领军总部的命令，日本帝国大本营于1945年9月13日废止，其中枢部分的参谋本部和军令部于10月30日正式消失，陆军省和海军省也于12月1日被撤销。日本军部，这半个世纪以来，亚洲和太平洋地区人的噩梦，终于退出了历史舞台。</p>\r\n<p style=\"text-indent: 2em\">麦克阿瑟对于宫廷集团所采取的行动是让日本天皇自己发布所谓&ldquo;人间宣言&rdquo;，从&ldquo;现人神&rdquo;的位置上走下来，这样宫廷集团就失去了赖以存在的理由，而且日后也不能再有人来使用&ldquo;效忠天皇&rdquo;的口号蛊惑人心，煽动骚乱。</p>\r\n<p style=\"text-indent: 2em\">麦克阿瑟当然知道以三菱、三井、住友、安田这四大财阀为首的日本财阀集团，在日本军阀对外扩张的行动中所起的重要作用，也知道他们实际上控制着很大一部分国家权力。麦克阿瑟采取的对策是以占领军的强大武力为后盾，进行&ldquo;财阀解体&rdquo;的行动，把这些庞大复杂的康采恩和辛迪加，分割成了一个个再也无力影响国策的普通企业。</p>\r\n<p style=\"text-indent: 2em\">战前日本农村的土地兼并现象非常严重，由此而产生的无地农民问题，成了日本向外扩张的借口。为了解决这个问题，麦克阿瑟毫无顾忌地从日本的地主那里把土地抢了过来，分给无地的农民们以安定农村局势。</p>\r\n<p style=\"text-indent: 2em\">通过释放政治犯，举行民主选举，鼓励成立工会，推进妇女参政等手段，麦克阿瑟在日本一边推广美国式民主自由的思想，建立新的政党政治，同时也采取将与原政权有关系的人开除公职的方法，摧毁了原来的政治结构。虽然这些做法中，有很多因为朝鲜半岛战争而中断或者发生了变化，但整个方向没有变。在日本一直有一个很有趣的猜想，就是如果没有朝鲜半岛战争的爆发，持坚决反共意识形态的麦克阿瑟，最终会把日本鼓捣成一个什么样的国家？</p>\r\n<p style=\"text-indent: 2em\">在军部、财阀、地主、政党、宫廷这些昔日的权力集团全部瓦解之后，麦克阿瑟准备如何处理日本帝国的官僚队伍呢？现在这个国家已经不再是&ldquo;天皇的神国&rdquo;了，麦克阿瑟教给了日本人一句&ldquo;主权在民&rdquo;的口号。但是和过去的天皇不可能自己去执掌权力一样，民众也不可能自身去直接使用权力，还得靠官僚来代表他们行使国家权力，但原来那支被称为&ldquo;天皇的官吏&rdquo;，为天皇服务的旧文官队伍现在能不能作为为国民服务的&ldquo;公仆&rdquo;（public servant，这也是个麦克阿瑟带来的新名词）而被继续使用呢？如果不能继续使用，已经搭起了&ldquo;民主日本&rdquo;框架的麦克阿瑟，又准备上哪儿去寻找实际运作这个国家的行政官员呢？</p>\r\n<p style=\"text-indent: 2em\">来看看占领军总司令部GHQ对 &ldquo;帝国高等文官&rdquo;们的评价。GHQ是这样评价他们的：&ldquo;这些官僚，也就是说高等文官的大部分出身于东京帝国大学法学部。他们在大学里接受了完整的法律训练，他们被教育为忠诚天皇和同僚的人，他们甚至受过如何谈判和讨价还价的教育。但是在东京帝国大学法学部的课程中行政学却是选修课目，而且几乎没有人选修这门课目，因为高等文官的考试中从来没有出现过行政学的试题。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">这就是麦克阿瑟对他们的评价，麦克阿瑟知道他们忠君的政治倾向，但麦克阿瑟并不认为这是很大的问题。麦克阿瑟直截了当地说：&ldquo;世界上任何国家的官僚集团的本能都是替付工资的人工作。&rdquo;可能对某个官僚来说存在着意识形态的问题，但对于整个官僚集团来说，&ldquo;有奶便是娘&rdquo;才是本能。何况麦克阿瑟除了依靠这支官僚队伍之外也没有别的选择，他在日本不可能找出来能够从事行政管理工作的其他人了，精英化的后果就是除了精英之外就没有了有资格的人。</p>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('507', '<p style=\"text-align: center\"><img alt=\"广州又现“楼歪歪” 三栋居民楼倾斜(图)\" src=\"http://img1.gtimg.com/news/pics/hv1/45/129/1413/91913265.jpg\" /></p>\r\n<p>&nbsp;</p>\r\n<p align=\"center\" class=\"pictext\">居民楼倾斜现场图。 肖雄 摄</p>\r\n<p style=\"text-indent: 2em\">上月初，广州番禺区厦滘村一居民楼因地基下陷，房屋倾斜被拆除，5日晚至今，当地又连续有三栋居民楼发生倾斜。目前，周边十余栋受影响的房屋村民已经全部疏散，当地街道办开放临时安置点接纳受影响群众，而疑似事故元凶的附近工地已停止施工。</p>\r\n<p style=\"text-indent: 2em\">昨日（7日）上午，记者在厦滘村沙滘中路看到，14巷、16巷、18巷都已经被封锁，拉起了警戒线。在现场可以清楚地看到，16巷10号楼高六层，已经向东倾斜约15度，地面下沉约50厘米并裂开，14巷发生沉降的楼房分别有五六层高，楼底地面有多处开裂，其中14巷7号整体下沉明显，房子的大门被突起的地面抵住，已经无法完全打开，隔壁的14巷9号楼房墙角处的地面开裂非常明显，裂缝蜿蜒盘旋了很长，整栋房屋有被推高的趋势。</p>\r\n<p style=\"text-indent: 2em\">昨日下午，番禺洛浦街通报称，将继续全力做好厦滘村房屋下沉倾斜应急处置工作，重点做好排危、警戒及安置工作，继续开放临时安置点接纳受影响群众，并做好临时安置款的发放工作。</p>\r\n<p style=\"text-indent: 2em\">接连出现的沉降事故，让村民们惶恐不安，他们将造成这一切的矛头指向了旁边的&ldquo;厦滘商业大厦&rdquo;工地，怀疑是因为工地挖地基太深，造成了水土流失。&ldquo;厦滘商业大厦&rdquo;项目部的负责人在接受媒体采访时表示，这个项目是经过相关职能部门的审批，证件齐全，并不存在违法施工的行为，而且他们已经采取了相应的措施确保村民的安全。如果项目施工完成后，还会对受损的房屋作施工后的安全鉴定，届时如确定是开发商的责任他们将主动承担。（记者 胥柏波）</p>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('508', '<p style=\"text-align: center\"><img alt=\"海口酒行发生爆炸两人受伤 玻璃碎片炸飞50米外\" src=\"http://img1.gtimg.com/news/pics/hv1/240/127/1413/91912950.jpg\" /></p>\r\n<p>&nbsp;</p>\r\n<p style=\"text-indent: 2em\">【海口一酒行发生爆炸两人受伤】今天早上8点40分左右，海口市蓝天路一家酒行发生爆炸，两人受伤被送往医院。现场破坏严重，50米外能看到被炸飞的玻璃碎片，停在路边的多部车辆受损。目前警方已介入调查。（央视记者毛鑫陈龙）</p>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('509', '<p style=\"text-indent: 2em\">京华时报讯 由<span class=\"infoMblog\">人民出版社</span>出版的《朱镕基上海讲话实录》于8月12日向全国发行。昨天，人民出版社常务副社长任超做客中新网时介绍，该书首印110万册全部发出，并已取得65万册的销售佳绩。任超表示，该书出版过程中，朱镕基同志要求实事求是，如实呈现当时的一些情况。</p>\r\n<p style=\"text-indent: 2em\"><strong>首印110万册全发出</strong></p>\r\n<p style=\"text-indent: 2em\">任超透露，《朱镕基上海讲话实录》首印110万册全部批发出去。截至前天，实际销售已达65万册。</p>\r\n<p style=\"text-indent: 2em\">任超介绍，朱镕基退休后共出版三本书，均由人民出版社出版。其中，《朱镕基答记者问》2009年出版，首印25万册，发行当日再次加印，半个月后，印数达百万。而《朱镕基讲话实录》2011年出版，首印50万套，一个多月内售罄。</p>\r\n<p style=\"text-indent: 2em\">任超说，由于前两本书出版社对销量的估计不足，首印偏少，造成了一段时间市场脱销，此次为充分地满足读者的需求，所以做出了首印110万册的决定。</p>\r\n<p style=\"text-indent: 2em\"><strong>朱镕基要求尊重原貌</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>任超介绍，该书出版过程中，朱镕基非常注意听取大家意见，1000余本样书多送给中央领导同志、有关专家及当时和他在上海共事过的同事征求意见。而朱镕基同志本人则要求实事求是，如实呈现当时的一些情况。</strong></p>\r\n<p style=\"text-indent: 2em\"><strong>任超透露，朱镕基同志对自己所写的文章都是认真地思考，每篇文章他都要过。他要求尊重原貌，如实呈现当时的一些情况，是怎么回事就怎么回事，实事求是。任超说，朱镕基曾说过，&ldquo;如果人家拿着我的录音和我书里面的内容对不上，这不好&rdquo;。</strong></p>\r\n<p style=\"text-indent: 2em\">任超介绍，他跟朱镕基身边的一个工作人员了解到一个小故事。这本书里面，最后一篇文章是关于街道工作的，有专家提出来，这篇文章可不可以不说了。但是朱镕基认为街道工作是社会管理工作的一个非常重要的基础性工作，这篇要发表，就把这一篇报道出来了。</p>\r\n<p style=\"text-indent: 2em\"><strong>领导人出书将增多</strong></p>\r\n<p style=\"text-indent: 2em\">近年来，我国多位退休领导人都不止一次出版了自己的专著。对此，任超表示，这是一个好事，表明更加开放，更加透明。同时，党和国家领导人处理问题的方法带有普遍性意义，读者可以从中悟到一些东西。&ldquo;过去我们对党中央的一些决策部署和过程不一定很了解，现在通过一些著述，我们可以比较清晰地看到一些决策的脉络。&rdquo;</p>\r\n<p style=\"text-indent: 2em\">任超认为，今后领导人出书可能会越来越多，大家以后也会习以为常。</p>\r\n<p style=\"text-indent: 2em\">京华时报记者张然 综合中新社</p>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('512', '<p>&nbsp;</p>\r\n<p style=\"margin: 0px 0px 20px; padding: 0px; color: rgb(51, 51, 51); font-family: \'microsoft yahei\'; font-size: 16px; line-height: 30px; text-align: justify; \">9月4日下午，融创集团以21亿元地价款、22.24亿元配建异地医院的出价拿下备受瞩目的北京朝阳区农展馆北路8号0304-622地块，折算楼面地价高达7.3万元/平方米，再度刷新了北京单价地王记录。</p>\r\n<p style=\"margin: 0px 0px 20px; padding: 0px; color: rgb(51, 51, 51); font-family: \'microsoft yahei\'; font-size: 16px; line-height: 30px; text-align: justify; \">　　以此同时，在9月2日，中国指数研究院发布了《2013年8月中国房地产(行情专区)指数系统百城价格指数报告》，报告显示：2013年8月，全国100个城市(新建)住宅平均价格为10442元/平方米，环比7月上涨0.92%。自2012年6月以来连续第15个月环比上涨，涨幅比上月扩大0.05个百分点，其中71个城市环比上涨，29个城市环比下跌。在上涨的城市中，涨幅居前三位的是：福州（楼盘）、徐州（楼盘）、日照（楼盘），而在上涨前十名的城市中，北京是唯一一个一线城市入选的。由此看出，上涨幅度最大的是二、三线城市。</p>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('548', '<p>测试内容</p>\r\n', null, null, null);
INSERT INTO `jc_content_txt` VALUES ('550', '<p style=\"TEXT-INDENT: 2em\">66岁老人乘坐66路公交车从灵昆到市区的医院看病时，在车上突发疾病，经抢救无效死亡。老人的家属将公交所属的温州交运集团城东公交有限公司告上法庭，要求赔偿损失31万多元。</p>\r\n<p style=\"TEXT-INDENT: 2em\">前天下午2时30分许，鹿城法院东郊法庭开庭审理这起纠纷。是否因为公交车乘务人员未尽到安全保障的义务，导致老人抢救不及时而死亡，成为法庭上争论的焦点。</p>\r\n<p style=\"TEXT-INDENT: 2em\"><strong>公交车上,黄老伯突然后仰，呼吸急促</strong></p>\r\n<p style=\"TEXT-INDENT: 2em\">老人姓黄，事发时66岁，此前患有肾衰竭等疾病。据原告方起诉称，2013年7月1日上午6时许，黄老伯乘坐66路公交车，从灵昆始发站前往温州市区。</p>\r\n', null, null, null);

-- ----------------------------
-- Table structure for `jc_content_type`
-- ----------------------------
DROP TABLE IF EXISTS `jc_content_type`;
CREATE TABLE `jc_content_type` (
  `type_id` int(11) NOT NULL,
  `type_name` varchar(20) NOT NULL COMMENT '名称',
  `img_width` int(11) DEFAULT NULL COMMENT '图片宽',
  `img_height` int(11) DEFAULT NULL COMMENT '图片高',
  `has_image` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有图片',
  `is_disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容类型表';

-- ----------------------------
-- Records of jc_content_type
-- ----------------------------
INSERT INTO `jc_content_type` VALUES ('1', '普通', '100', '100', '0', '0');
INSERT INTO `jc_content_type` VALUES ('2', '图文', '143', '98', '1', '0');
INSERT INTO `jc_content_type` VALUES ('3', '焦点', '280', '200', '1', '0');
INSERT INTO `jc_content_type` VALUES ('4', '头条', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for `jc_contenttag`
-- ----------------------------
DROP TABLE IF EXISTS `jc_contenttag`;
CREATE TABLE `jc_contenttag` (
  `content_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  `priority` int(11) NOT NULL,
  KEY `fk_jc_content_tag` (`tag_id`),
  KEY `fk_jc_tag_content` (`content_id`),
  CONSTRAINT `fk_jc_content_tag` FOREIGN KEY (`tag_id`) REFERENCES `jc_content_tag` (`tag_id`),
  CONSTRAINT `fk_jc_tag_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS内容标签关联表';

-- ----------------------------
-- Records of jc_contenttag
-- ----------------------------
INSERT INTO `jc_contenttag` VALUES ('342', '54', '0');
INSERT INTO `jc_contenttag` VALUES ('448', '102', '0');
INSERT INTO `jc_contenttag` VALUES ('448', '103', '1');
INSERT INTO `jc_contenttag` VALUES ('448', '104', '2');
INSERT INTO `jc_contenttag` VALUES ('448', '105', '3');
INSERT INTO `jc_contenttag` VALUES ('448', '106', '4');
INSERT INTO `jc_contenttag` VALUES ('448', '107', '5');
INSERT INTO `jc_contenttag` VALUES ('448', '108', '6');
INSERT INTO `jc_contenttag` VALUES ('448', '109', '7');
INSERT INTO `jc_contenttag` VALUES ('448', '110', '8');
INSERT INTO `jc_contenttag` VALUES ('487', '359', '0');
INSERT INTO `jc_contenttag` VALUES ('487', '360', '1');
INSERT INTO `jc_contenttag` VALUES ('487', '361', '2');
INSERT INTO `jc_contenttag` VALUES ('487', '362', '3');
INSERT INTO `jc_contenttag` VALUES ('487', '257', '4');
INSERT INTO `jc_contenttag` VALUES ('488', '355', '0');
INSERT INTO `jc_contenttag` VALUES ('488', '356', '1');
INSERT INTO `jc_contenttag` VALUES ('488', '357', '2');
INSERT INTO `jc_contenttag` VALUES ('488', '358', '3');
INSERT INTO `jc_contenttag` VALUES ('489', '351', '0');
INSERT INTO `jc_contenttag` VALUES ('489', '352', '1');
INSERT INTO `jc_contenttag` VALUES ('489', '353', '2');
INSERT INTO `jc_contenttag` VALUES ('489', '354', '3');
INSERT INTO `jc_contenttag` VALUES ('490', '347', '0');
INSERT INTO `jc_contenttag` VALUES ('490', '134', '1');
INSERT INTO `jc_contenttag` VALUES ('490', '348', '2');
INSERT INTO `jc_contenttag` VALUES ('490', '349', '3');
INSERT INTO `jc_contenttag` VALUES ('490', '350', '4');
INSERT INTO `jc_contenttag` VALUES ('491', '337', '0');
INSERT INTO `jc_contenttag` VALUES ('491', '122', '1');
INSERT INTO `jc_contenttag` VALUES ('491', '338', '2');
INSERT INTO `jc_contenttag` VALUES ('491', '339', '3');
INSERT INTO `jc_contenttag` VALUES ('491', '340', '4');
INSERT INTO `jc_contenttag` VALUES ('491', '341', '5');
INSERT INTO `jc_contenttag` VALUES ('491', '342', '6');
INSERT INTO `jc_contenttag` VALUES ('491', '343', '7');
INSERT INTO `jc_contenttag` VALUES ('491', '344', '8');
INSERT INTO `jc_contenttag` VALUES ('492', '159', '0');
INSERT INTO `jc_contenttag` VALUES ('492', '334', '1');
INSERT INTO `jc_contenttag` VALUES ('492', '335', '2');
INSERT INTO `jc_contenttag` VALUES ('492', '336', '3');
INSERT INTO `jc_contenttag` VALUES ('493', '330', '0');
INSERT INTO `jc_contenttag` VALUES ('493', '331', '1');
INSERT INTO `jc_contenttag` VALUES ('493', '332', '2');
INSERT INTO `jc_contenttag` VALUES ('493', '333', '3');
INSERT INTO `jc_contenttag` VALUES ('494', '329', '0');
INSERT INTO `jc_contenttag` VALUES ('495', '157', '0');
INSERT INTO `jc_contenttag` VALUES ('495', '158', '1');
INSERT INTO `jc_contenttag` VALUES ('495', '159', '2');
INSERT INTO `jc_contenttag` VALUES ('495', '160', '3');
INSERT INTO `jc_contenttag` VALUES ('495', '161', '4');
INSERT INTO `jc_contenttag` VALUES ('496', '162', '0');
INSERT INTO `jc_contenttag` VALUES ('496', '163', '1');
INSERT INTO `jc_contenttag` VALUES ('496', '164', '2');
INSERT INTO `jc_contenttag` VALUES ('496', '165', '3');
INSERT INTO `jc_contenttag` VALUES ('498', '172', '0');
INSERT INTO `jc_contenttag` VALUES ('498', '173', '1');
INSERT INTO `jc_contenttag` VALUES ('498', '174', '2');
INSERT INTO `jc_contenttag` VALUES ('498', '175', '3');
INSERT INTO `jc_contenttag` VALUES ('499', '183', '0');
INSERT INTO `jc_contenttag` VALUES ('499', '184', '1');
INSERT INTO `jc_contenttag` VALUES ('499', '179', '2');
INSERT INTO `jc_contenttag` VALUES ('499', '180', '3');
INSERT INTO `jc_contenttag` VALUES ('499', '181', '4');
INSERT INTO `jc_contenttag` VALUES ('499', '182', '5');
INSERT INTO `jc_contenttag` VALUES ('500', '185', '0');
INSERT INTO `jc_contenttag` VALUES ('500', '186', '1');
INSERT INTO `jc_contenttag` VALUES ('500', '187', '2');
INSERT INTO `jc_contenttag` VALUES ('500', '188', '3');
INSERT INTO `jc_contenttag` VALUES ('500', '189', '4');
INSERT INTO `jc_contenttag` VALUES ('501', '190', '0');
INSERT INTO `jc_contenttag` VALUES ('501', '191', '1');
INSERT INTO `jc_contenttag` VALUES ('501', '192', '2');
INSERT INTO `jc_contenttag` VALUES ('444', '193', '0');
INSERT INTO `jc_contenttag` VALUES ('444', '194', '1');
INSERT INTO `jc_contenttag` VALUES ('444', '195', '2');
INSERT INTO `jc_contenttag` VALUES ('444', '196', '3');
INSERT INTO `jc_contenttag` VALUES ('444', '197', '4');
INSERT INTO `jc_contenttag` VALUES ('444', '198', '5');
INSERT INTO `jc_contenttag` VALUES ('446', '199', '0');
INSERT INTO `jc_contenttag` VALUES ('446', '200', '1');
INSERT INTO `jc_contenttag` VALUES ('446', '201', '2');
INSERT INTO `jc_contenttag` VALUES ('446', '202', '3');
INSERT INTO `jc_contenttag` VALUES ('446', '203', '4');
INSERT INTO `jc_contenttag` VALUES ('446', '204', '5');
INSERT INTO `jc_contenttag` VALUES ('446', '205', '6');
INSERT INTO `jc_contenttag` VALUES ('447', '206', '0');
INSERT INTO `jc_contenttag` VALUES ('447', '94', '1');
INSERT INTO `jc_contenttag` VALUES ('447', '207', '2');
INSERT INTO `jc_contenttag` VALUES ('447', '208', '3');
INSERT INTO `jc_contenttag` VALUES ('447', '209', '4');
INSERT INTO `jc_contenttag` VALUES ('447', '210', '5');
INSERT INTO `jc_contenttag` VALUES ('447', '211', '6');
INSERT INTO `jc_contenttag` VALUES ('447', '212', '7');
INSERT INTO `jc_contenttag` VALUES ('447', '213', '8');
INSERT INTO `jc_contenttag` VALUES ('445', '94', '0');
INSERT INTO `jc_contenttag` VALUES ('445', '214', '1');
INSERT INTO `jc_contenttag` VALUES ('445', '215', '2');
INSERT INTO `jc_contenttag` VALUES ('445', '216', '3');
INSERT INTO `jc_contenttag` VALUES ('445', '217', '4');
INSERT INTO `jc_contenttag` VALUES ('445', '218', '5');
INSERT INTO `jc_contenttag` VALUES ('445', '219', '6');
INSERT INTO `jc_contenttag` VALUES ('445', '220', '7');
INSERT INTO `jc_contenttag` VALUES ('443', '221', '0');
INSERT INTO `jc_contenttag` VALUES ('443', '222', '1');
INSERT INTO `jc_contenttag` VALUES ('443', '223', '2');
INSERT INTO `jc_contenttag` VALUES ('443', '224', '3');
INSERT INTO `jc_contenttag` VALUES ('443', '225', '4');
INSERT INTO `jc_contenttag` VALUES ('443', '226', '5');
INSERT INTO `jc_contenttag` VALUES ('442', '227', '0');
INSERT INTO `jc_contenttag` VALUES ('442', '228', '1');
INSERT INTO `jc_contenttag` VALUES ('442', '229', '2');
INSERT INTO `jc_contenttag` VALUES ('442', '230', '3');
INSERT INTO `jc_contenttag` VALUES ('442', '231', '4');
INSERT INTO `jc_contenttag` VALUES ('442', '232', '5');
INSERT INTO `jc_contenttag` VALUES ('442', '233', '6');
INSERT INTO `jc_contenttag` VALUES ('442', '234', '7');
INSERT INTO `jc_contenttag` VALUES ('442', '235', '8');
INSERT INTO `jc_contenttag` VALUES ('441', '236', '0');
INSERT INTO `jc_contenttag` VALUES ('441', '237', '1');
INSERT INTO `jc_contenttag` VALUES ('441', '238', '2');
INSERT INTO `jc_contenttag` VALUES ('441', '239', '3');
INSERT INTO `jc_contenttag` VALUES ('441', '240', '4');
INSERT INTO `jc_contenttag` VALUES ('441', '68', '5');
INSERT INTO `jc_contenttag` VALUES ('441', '241', '6');
INSERT INTO `jc_contenttag` VALUES ('441', '242', '7');
INSERT INTO `jc_contenttag` VALUES ('441', '243', '8');
INSERT INTO `jc_contenttag` VALUES ('440', '94', '0');
INSERT INTO `jc_contenttag` VALUES ('440', '244', '1');
INSERT INTO `jc_contenttag` VALUES ('440', '245', '2');
INSERT INTO `jc_contenttag` VALUES ('440', '246', '3');
INSERT INTO `jc_contenttag` VALUES ('440', '224', '4');
INSERT INTO `jc_contenttag` VALUES ('440', '247', '5');
INSERT INTO `jc_contenttag` VALUES ('439', '228', '0');
INSERT INTO `jc_contenttag` VALUES ('439', '248', '1');
INSERT INTO `jc_contenttag` VALUES ('439', '249', '2');
INSERT INTO `jc_contenttag` VALUES ('439', '250', '3');
INSERT INTO `jc_contenttag` VALUES ('439', '251', '4');
INSERT INTO `jc_contenttag` VALUES ('439', '252', '5');
INSERT INTO `jc_contenttag` VALUES ('439', '253', '6');
INSERT INTO `jc_contenttag` VALUES ('439', '254', '7');
INSERT INTO `jc_contenttag` VALUES ('438', '255', '0');
INSERT INTO `jc_contenttag` VALUES ('438', '256', '1');
INSERT INTO `jc_contenttag` VALUES ('438', '257', '2');
INSERT INTO `jc_contenttag` VALUES ('438', '258', '3');
INSERT INTO `jc_contenttag` VALUES ('438', '259', '4');
INSERT INTO `jc_contenttag` VALUES ('438', '260', '5');
INSERT INTO `jc_contenttag` VALUES ('438', '261', '6');
INSERT INTO `jc_contenttag` VALUES ('438', '262', '7');
INSERT INTO `jc_contenttag` VALUES ('437', '94', '0');
INSERT INTO `jc_contenttag` VALUES ('437', '263', '1');
INSERT INTO `jc_contenttag` VALUES ('437', '264', '2');
INSERT INTO `jc_contenttag` VALUES ('437', '265', '3');
INSERT INTO `jc_contenttag` VALUES ('437', '108', '4');
INSERT INTO `jc_contenttag` VALUES ('437', '266', '5');
INSERT INTO `jc_contenttag` VALUES ('436', '267', '0');
INSERT INTO `jc_contenttag` VALUES ('436', '268', '1');
INSERT INTO `jc_contenttag` VALUES ('436', '222', '2');
INSERT INTO `jc_contenttag` VALUES ('436', '223', '3');
INSERT INTO `jc_contenttag` VALUES ('436', '269', '4');
INSERT INTO `jc_contenttag` VALUES ('436', '270', '5');
INSERT INTO `jc_contenttag` VALUES ('436', '271', '6');
INSERT INTO `jc_contenttag` VALUES ('435', '272', '0');
INSERT INTO `jc_contenttag` VALUES ('435', '273', '1');
INSERT INTO `jc_contenttag` VALUES ('435', '274', '2');
INSERT INTO `jc_contenttag` VALUES ('435', '275', '3');
INSERT INTO `jc_contenttag` VALUES ('435', '276', '4');
INSERT INTO `jc_contenttag` VALUES ('435', '277', '5');
INSERT INTO `jc_contenttag` VALUES ('434', '278', '0');
INSERT INTO `jc_contenttag` VALUES ('434', '279', '1');
INSERT INTO `jc_contenttag` VALUES ('434', '280', '2');
INSERT INTO `jc_contenttag` VALUES ('434', '281', '3');
INSERT INTO `jc_contenttag` VALUES ('434', '282', '4');
INSERT INTO `jc_contenttag` VALUES ('434', '283', '5');
INSERT INTO `jc_contenttag` VALUES ('434', '284', '6');
INSERT INTO `jc_contenttag` VALUES ('506', '286', '0');
INSERT INTO `jc_contenttag` VALUES ('506', '287', '1');
INSERT INTO `jc_contenttag` VALUES ('506', '288', '2');
INSERT INTO `jc_contenttag` VALUES ('506', '289', '3');
INSERT INTO `jc_contenttag` VALUES ('506', '290', '4');
INSERT INTO `jc_contenttag` VALUES ('507', '291', '0');
INSERT INTO `jc_contenttag` VALUES ('507', '292', '1');
INSERT INTO `jc_contenttag` VALUES ('507', '293', '2');
INSERT INTO `jc_contenttag` VALUES ('507', '294', '3');
INSERT INTO `jc_contenttag` VALUES ('508', '295', '0');
INSERT INTO `jc_contenttag` VALUES ('508', '296', '1');
INSERT INTO `jc_contenttag` VALUES ('508', '297', '2');
INSERT INTO `jc_contenttag` VALUES ('508', '298', '3');
INSERT INTO `jc_contenttag` VALUES ('508', '299', '4');
INSERT INTO `jc_contenttag` VALUES ('508', '300', '5');
INSERT INTO `jc_contenttag` VALUES ('509', '301', '0');
INSERT INTO `jc_contenttag` VALUES ('509', '302', '1');
INSERT INTO `jc_contenttag` VALUES ('509', '303', '2');
INSERT INTO `jc_contenttag` VALUES ('509', '304', '3');
INSERT INTO `jc_contenttag` VALUES ('510', '305', '0');
INSERT INTO `jc_contenttag` VALUES ('510', '306', '1');
INSERT INTO `jc_contenttag` VALUES ('510', '307', '2');
INSERT INTO `jc_contenttag` VALUES ('510', '308', '3');
INSERT INTO `jc_contenttag` VALUES ('510', '309', '4');
INSERT INTO `jc_contenttag` VALUES ('510', '310', '5');
INSERT INTO `jc_contenttag` VALUES ('511', '311', '0');
INSERT INTO `jc_contenttag` VALUES ('511', '312', '1');
INSERT INTO `jc_contenttag` VALUES ('511', '313', '2');
INSERT INTO `jc_contenttag` VALUES ('511', '314', '3');
INSERT INTO `jc_contenttag` VALUES ('511', '315', '4');
INSERT INTO `jc_contenttag` VALUES ('511', '316', '5');
INSERT INTO `jc_contenttag` VALUES ('511', '317', '6');
INSERT INTO `jc_contenttag` VALUES ('512', '318', '0');
INSERT INTO `jc_contenttag` VALUES ('512', '319', '1');
INSERT INTO `jc_contenttag` VALUES ('512', '320', '2');
INSERT INTO `jc_contenttag` VALUES ('512', '321', '3');
INSERT INTO `jc_contenttag` VALUES ('512', '322', '4');
INSERT INTO `jc_contenttag` VALUES ('512', '323', '5');
INSERT INTO `jc_contenttag` VALUES ('512', '324', '6');
INSERT INTO `jc_contenttag` VALUES ('512', '325', '7');
INSERT INTO `jc_contenttag` VALUES ('512', '326', '8');
INSERT INTO `jc_contenttag` VALUES ('512', '327', '9');
INSERT INTO `jc_contenttag` VALUES ('512', '328', '10');
INSERT INTO `jc_contenttag` VALUES ('491', '345', '9');
INSERT INTO `jc_contenttag` VALUES ('491', '346', '10');
INSERT INTO `jc_contenttag` VALUES ('513', '363', '0');
INSERT INTO `jc_contenttag` VALUES ('513', '364', '1');
INSERT INTO `jc_contenttag` VALUES ('513', '365', '2');
INSERT INTO `jc_contenttag` VALUES ('513', '366', '3');
INSERT INTO `jc_contenttag` VALUES ('513', '367', '4');
INSERT INTO `jc_contenttag` VALUES ('514', '363', '0');
INSERT INTO `jc_contenttag` VALUES ('514', '368', '1');
INSERT INTO `jc_contenttag` VALUES ('514', '365', '2');
INSERT INTO `jc_contenttag` VALUES ('515', '369', '0');
INSERT INTO `jc_contenttag` VALUES ('515', '364', '1');
INSERT INTO `jc_contenttag` VALUES ('515', '365', '2');
INSERT INTO `jc_contenttag` VALUES ('515', '370', '3');
INSERT INTO `jc_contenttag` VALUES ('516', '371', '0');
INSERT INTO `jc_contenttag` VALUES ('516', '372', '1');
INSERT INTO `jc_contenttag` VALUES ('516', '373', '2');
INSERT INTO `jc_contenttag` VALUES ('516', '374', '3');
INSERT INTO `jc_contenttag` VALUES ('516', '375', '4');
INSERT INTO `jc_contenttag` VALUES ('517', '83', '0');
INSERT INTO `jc_contenttag` VALUES ('517', '376', '1');
INSERT INTO `jc_contenttag` VALUES ('517', '377', '2');
INSERT INTO `jc_contenttag` VALUES ('517', '378', '3');
INSERT INTO `jc_contenttag` VALUES ('517', '379', '4');
INSERT INTO `jc_contenttag` VALUES ('518', '380', '0');
INSERT INTO `jc_contenttag` VALUES ('518', '365', '1');
INSERT INTO `jc_contenttag` VALUES ('518', '381', '2');
INSERT INTO `jc_contenttag` VALUES ('518', '382', '3');
INSERT INTO `jc_contenttag` VALUES ('518', '383', '4');
INSERT INTO `jc_contenttag` VALUES ('519', '384', '0');
INSERT INTO `jc_contenttag` VALUES ('519', '385', '1');
INSERT INTO `jc_contenttag` VALUES ('519', '386', '2');
INSERT INTO `jc_contenttag` VALUES ('520', '387', '0');
INSERT INTO `jc_contenttag` VALUES ('520', '388', '1');
INSERT INTO `jc_contenttag` VALUES ('520', '389', '2');
INSERT INTO `jc_contenttag` VALUES ('521', '390', '0');
INSERT INTO `jc_contenttag` VALUES ('521', '391', '1');
INSERT INTO `jc_contenttag` VALUES ('521', '392', '2');
INSERT INTO `jc_contenttag` VALUES ('522', '393', '0');
INSERT INTO `jc_contenttag` VALUES ('522', '394', '1');
INSERT INTO `jc_contenttag` VALUES ('522', '395', '2');
INSERT INTO `jc_contenttag` VALUES ('522', '396', '3');
INSERT INTO `jc_contenttag` VALUES ('522', '397', '4');
INSERT INTO `jc_contenttag` VALUES ('523', '398', '0');
INSERT INTO `jc_contenttag` VALUES ('523', '399', '1');
INSERT INTO `jc_contenttag` VALUES ('523', '400', '2');
INSERT INTO `jc_contenttag` VALUES ('523', '401', '3');
INSERT INTO `jc_contenttag` VALUES ('524', '402', '0');
INSERT INTO `jc_contenttag` VALUES ('524', '403', '1');
INSERT INTO `jc_contenttag` VALUES ('524', '93', '2');
INSERT INTO `jc_contenttag` VALUES ('524', '404', '3');
INSERT INTO `jc_contenttag` VALUES ('525', '405', '0');
INSERT INTO `jc_contenttag` VALUES ('525', '406', '1');
INSERT INTO `jc_contenttag` VALUES ('525', '386', '2');
INSERT INTO `jc_contenttag` VALUES ('526', '407', '0');
INSERT INTO `jc_contenttag` VALUES ('526', '408', '1');
INSERT INTO `jc_contenttag` VALUES ('526', '409', '2');
INSERT INTO `jc_contenttag` VALUES ('526', '410', '3');
INSERT INTO `jc_contenttag` VALUES ('526', '411', '4');
INSERT INTO `jc_contenttag` VALUES ('526', '412', '5');
INSERT INTO `jc_contenttag` VALUES ('527', '413', '0');
INSERT INTO `jc_contenttag` VALUES ('527', '414', '1');
INSERT INTO `jc_contenttag` VALUES ('527', '415', '2');
INSERT INTO `jc_contenttag` VALUES ('527', '416', '3');
INSERT INTO `jc_contenttag` VALUES ('527', '417', '4');
INSERT INTO `jc_contenttag` VALUES ('527', '418', '5');
INSERT INTO `jc_contenttag` VALUES ('528', '419', '0');
INSERT INTO `jc_contenttag` VALUES ('528', '420', '1');
INSERT INTO `jc_contenttag` VALUES ('528', '386', '2');
INSERT INTO `jc_contenttag` VALUES ('528', '370', '3');
INSERT INTO `jc_contenttag` VALUES ('528', '421', '4');
INSERT INTO `jc_contenttag` VALUES ('528', '422', '5');
INSERT INTO `jc_contenttag` VALUES ('528', '395', '6');
INSERT INTO `jc_contenttag` VALUES ('528', '423', '7');
INSERT INTO `jc_contenttag` VALUES ('528', '385', '8');
INSERT INTO `jc_contenttag` VALUES ('529', '424', '0');
INSERT INTO `jc_contenttag` VALUES ('529', '403', '1');
INSERT INTO `jc_contenttag` VALUES ('529', '425', '2');
INSERT INTO `jc_contenttag` VALUES ('529', '426', '3');
INSERT INTO `jc_contenttag` VALUES ('529', '427', '4');
INSERT INTO `jc_contenttag` VALUES ('530', '428', '0');
INSERT INTO `jc_contenttag` VALUES ('530', '396', '1');
INSERT INTO `jc_contenttag` VALUES ('530', '429', '2');
INSERT INTO `jc_contenttag` VALUES ('530', '403', '3');
INSERT INTO `jc_contenttag` VALUES ('530', '93', '4');
INSERT INTO `jc_contenttag` VALUES ('530', '404', '5');
INSERT INTO `jc_contenttag` VALUES ('530', '430', '6');
INSERT INTO `jc_contenttag` VALUES ('530', '431', '7');
INSERT INTO `jc_contenttag` VALUES ('531', '432', '0');
INSERT INTO `jc_contenttag` VALUES ('531', '433', '1');
INSERT INTO `jc_contenttag` VALUES ('531', '434', '2');
INSERT INTO `jc_contenttag` VALUES ('532', '435', '0');
INSERT INTO `jc_contenttag` VALUES ('532', '316', '1');
INSERT INTO `jc_contenttag` VALUES ('532', '436', '2');
INSERT INTO `jc_contenttag` VALUES ('532', '2', '3');
INSERT INTO `jc_contenttag` VALUES ('532', '437', '4');
INSERT INTO `jc_contenttag` VALUES ('533', '438', '0');
INSERT INTO `jc_contenttag` VALUES ('533', '439', '1');
INSERT INTO `jc_contenttag` VALUES ('533', '440', '2');
INSERT INTO `jc_contenttag` VALUES ('534', '441', '0');
INSERT INTO `jc_contenttag` VALUES ('534', '385', '1');
INSERT INTO `jc_contenttag` VALUES ('534', '386', '2');
INSERT INTO `jc_contenttag` VALUES ('535', '442', '0');
INSERT INTO `jc_contenttag` VALUES ('535', '422', '1');
INSERT INTO `jc_contenttag` VALUES ('535', '394', '2');
INSERT INTO `jc_contenttag` VALUES ('536', '443', '0');
INSERT INTO `jc_contenttag` VALUES ('536', '444', '1');
INSERT INTO `jc_contenttag` VALUES ('536', '445', '2');
INSERT INTO `jc_contenttag` VALUES ('536', '446', '3');
INSERT INTO `jc_contenttag` VALUES ('536', '447', '4');
INSERT INTO `jc_contenttag` VALUES ('548', '55', '0');
INSERT INTO `jc_contenttag` VALUES ('548', '469', '1');
INSERT INTO `jc_contenttag` VALUES ('550', '471', '0');
INSERT INTO `jc_contenttag` VALUES ('550', '472', '1');
INSERT INTO `jc_contenttag` VALUES ('550', '473', '2');
INSERT INTO `jc_contenttag` VALUES ('550', '474', '3');
INSERT INTO `jc_contenttag` VALUES ('550', '475', '4');
INSERT INTO `jc_contenttag` VALUES ('550', '476', '5');
INSERT INTO `jc_contenttag` VALUES ('550', '477', '6');
INSERT INTO `jc_contenttag` VALUES ('550', '478', '7');
INSERT INTO `jc_contenttag` VALUES ('550', '479', '8');
INSERT INTO `jc_contenttag` VALUES ('550', '215', '9');
INSERT INTO `jc_contenttag` VALUES ('551', '480', '0');
INSERT INTO `jc_contenttag` VALUES ('551', '481', '1');
INSERT INTO `jc_contenttag` VALUES ('551', '482', '2');
INSERT INTO `jc_contenttag` VALUES ('552', '55', '0');
INSERT INTO `jc_contenttag` VALUES ('552', '483', '1');

-- ----------------------------
-- Table structure for `jc_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `jc_dictionary`;
CREATE TABLE `jc_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT 'name',
  `value` varchar(255) NOT NULL COMMENT 'value',
  `type` varchar(255) NOT NULL COMMENT 'type',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of jc_dictionary
-- ----------------------------
INSERT INTO `jc_dictionary` VALUES ('1', '1-20人', '1-20人', 'scale');
INSERT INTO `jc_dictionary` VALUES ('2', '20-50人', '20-50人', 'scale');
INSERT INTO `jc_dictionary` VALUES ('3', '50-10人', '50-10人', 'scale');
INSERT INTO `jc_dictionary` VALUES ('4', '100人以上', '100人以上', 'scale');
INSERT INTO `jc_dictionary` VALUES ('5', '私企', '私企', 'nature');
INSERT INTO `jc_dictionary` VALUES ('6', '股份制', '股份制', 'nature');
INSERT INTO `jc_dictionary` VALUES ('7', '国企', '国企', 'nature');
INSERT INTO `jc_dictionary` VALUES ('8', '互联网', '互联网', 'industry');
INSERT INTO `jc_dictionary` VALUES ('9', '房地产', '房地产', 'industry');
INSERT INTO `jc_dictionary` VALUES ('10', '加工制造', '加工制造', 'industry');
INSERT INTO `jc_dictionary` VALUES ('11', '服务行业', '服务行业', 'industry');
INSERT INTO `jc_dictionary` VALUES ('12', '政府机构', '政府机构', 'nature');

-- ----------------------------
-- Table structure for `jc_file`
-- ----------------------------
DROP TABLE IF EXISTS `jc_file`;
CREATE TABLE `jc_file` (
  `file_path` varchar(255) NOT NULL DEFAULT '' COMMENT '文件路径',
  `file_name` varchar(255) DEFAULT '' COMMENT '文件名字',
  `file_isvalid` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  `content_id` int(11) DEFAULT NULL COMMENT '内容id',
  PRIMARY KEY (`file_path`),
  KEY `fk_jc_file_content` (`content_id`),
  CONSTRAINT `fk_jc_file_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jc_file
-- ----------------------------
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/171410455sqq.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/17141056zbjl.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/17141320s4xy.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/17141341je98.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/171415447op5.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/17141604lrhh.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/17144805im1p.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/17145028j3bj.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/171545290lr9.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/17161125k5kt.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/171611397af9.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/171612286b6w.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/17161242nbqp.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/171612535rhr.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/17161324z8jn.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/17165616uvy4.', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/17165616uvy4.swf', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/17171653nfp8.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/171717039zq7.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/17171709jdjw.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/17171716ivqv.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/18155751wi1k.gif', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/190837201odl.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19090536qbxp.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19090946s78x.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19092939oy7r.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19093226mevs.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19093508z0je.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19093530anjq.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19093534pbhx.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19093538klmo.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19093544rdou.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/190935502lvi.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19093742dmvl.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/190938494dpy.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19093849begu.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19093854n45u.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19093857hrzl.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19093900wjkf.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19093911o5g2.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19093953zqpx.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19094122mc5z.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19094240v28v.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19094329a5xa.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19094332tbdx.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/190943347cma.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19094339inmu.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/190943477255.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19094508jfde.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/190945185h1l.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/190946273j5i.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19094631wo80.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/190947003jp4.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/190947311x4l.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19094831t9hq.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/1909485814nz.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/190949019o6w.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19094903k37i.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19094907y45o.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/190949132edb.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19094951qk1a.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/190949521eei.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19095026h3ca.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19095103kcpr.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/190952289wxr.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19095231lcq8.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19095231picz.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19095234gz2e.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19095237hsr4.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19095244lvkp.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/190953537bvn.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19095401xwwr.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/190954435nww.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19095533hv75.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19095559cx9e.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19095711di9x.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19095824t0mi.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19095858mdri.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19095940zkzo.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19100045xs61.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19100130dqrl.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191002428q2p.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19100427q41v.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19100730yqkm.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19100732rbr3.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19100735jhtm.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19100738xu07.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19100819q0nh.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19101258cu07.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19101300vgo6.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191013028ees.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19101306te5g.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19101310nlax.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19101405vhee.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191016542zol.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191016566066.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/1910170103if.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19101705pqim.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19101815my5b.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191018173g5w.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19101820duyo.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/1910182468l0.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191019506xu1.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19102057h28j.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/1910215201yl.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191026320f2y.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19102634814r.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19102637g8wu.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191026405ljl.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/1910271036lr.gif', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19102719hx2v.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/1910294695ky.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19102949nry1.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191029525wi9.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191029566nps.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19103035b3ll.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19103318bgac.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191033227v2n.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19103325i7eh.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19103329ra1y.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19103403xlfg.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191035531l49.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19103555yzl9.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19103559ef8r.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19103603lu7z.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191036587tik.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191047224mmx.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191116447msi.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19111647quq9.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191116504sdn.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19111654zbf8.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19111756gll3.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19111932a9xv.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19111935140r.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19111938y8xu.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19111941j2ox.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19112136dax2.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19112139m5p5.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19112142aniu.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191121451voq.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19112223q81f.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191124260dyw.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19112428i5di.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19112431i6yl.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191124347he2.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19112511nfma.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19112627baql.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19112630rnkd.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19112633k9hl.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/1911263840lw.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191127127do2.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19113108a4tf.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19113111l3z3.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19113114xqvt.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19113119mokz.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19113155zjrt.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191133047hli.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19113306vsom.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/1911330905lu.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191133124ekg.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19113349wi0o.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19113455w6da.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19113458qtv5.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19113500lodt.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19113504ak3b.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19113545y1n3.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/1911384005xa.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19113842vbep.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191138453a05.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19113849s58m.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191139257lag.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19114055fyqx.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191140578xaj.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19114100zis1.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19114104hajf.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191141333snd.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191143031z9j.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19114305b0sj.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19114309f164.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19114312te0u.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19114342fmb2.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19115528h893.zip', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19134858biqw.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19135339f6l6.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19135949d13f.zip', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191408344rwj.gif', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19141012lh2q.gif', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19141139noa7.png', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19141255yrfb.gif', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19141401rp2d.gif', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191415078pzs.gif', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/1914272808rj.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19151533k5ey.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19154302hxkg.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19154430myw1.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191545434coc.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19154549gc3w.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19154650hare.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19155032l0ql.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19155034wvyj.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19155038k2do.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191550415nco.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191553100g34.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19155314sxs8.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/19155317zg9k.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201112/191553207vdb.jpg', '', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/13104201642h.jpg', 'mv1.jpg', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/13164348ounp.jpg', 'mv.jpg', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/13181233l0sx.jpg', 'mv.jpg', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/13181255db1i.jpg', 'mv.jpg', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/14152458fy5b.txt', 'linux启动oracle和tomcat.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/14152611izbp.txt', 'bug.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/14152611lngy.txt', 'linux启动oracle和tomcat.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/141530071jwc.txt', 'linux启动oracle和tomcat.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/14153007q6u1.txt', 'bug.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/141543541ogv.txt', 'bug.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/141543547ho5.txt', 'linux启动oracle和tomcat.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/14154515q4ai.txt', 'bug.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/14154625jcco.txt', 'bug.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/1415573813s2.txt', 'bug.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/14155738vhab.txt', 'linux启动oracle和tomcat.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/14160353thjr.txt', 'linux启动oracle和tomcat.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/1416062229ht.txt', 'bug.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/14160723hi9b.txt', 'bug.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/141608146au0.txt', 'bug.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/14160814zzjp.txt', 'linux启动oracle和tomcat.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/14160903ierl.txt', 'bug.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/14161725esyh.txt', 'linux启动oracle和tomcat.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201308/14161727h1du.txt', 'bug.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/09093100544f.jpg', '1.jpg', '0', '494');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/09093304hc6x.jpg', '1.jpg', '0', '493');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/09093632txyy.jpg', '1.jpg', '0', '492');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/090938248x1c.jpg', '1.jpg', '0', '491');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/090940173zaj.jpg', '1.jpg', '0', '490');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/09094140f562.jpg', '1.jpg', '0', '489');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/09094302sldg.jpg', '1.jpg', '0', '488');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/09094430w0xt.jpg', '1.jpg', '0', '487');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/09151507n6i1.jpg', '1.jpg', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/09152518pzoq.jpg', '1.jpg', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/09152931cgps.jpg', '1.jpg', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/091552426die.jpg', '1.jpg', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/0915541140xt.jpg', '1.jpg', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/09160120meel.jpg', '1.jpg', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/091602465aop.jpg', '1.jpg', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/09174523xkvt.jpg', '2.jpg', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/09174527lkok.jpg', '1.jpg', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/10092229195q.jpg', '10.jpg', '1', '506');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/100950366ij3.jpg', '1.jpg', '1', '507');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/100953460bvo.jpg', '1.jpg', '1', '508');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/10102800aa0b.mp4', '/u/cms/www/201309/10102800aa0b.mp4', '1', '509');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/10103806oifr.jpg', '10.jpg', '1', '510');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/10104531ukj5.jpg', '10.jpg', '1', '511');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/10144918u244.jpg', 'newsPic2.jpg', '1', '512');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11165619lkm8.jpg', '6.JPG', '1', '494');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11165624319n.jpg', '5.jpg', '1', '494');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/111656292jx9.jpg', '4.jpg', '1', '494');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11165633po01.jpg', '3.jpg', '1', '494');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/111656366w4b.jpg', '2.jpg', '1', '494');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11165640djk4.jpg', '1.jpg', '1', '494');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11165849ahx1.jpg', '6.JPG', '0', '494');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11170224nw94.jpg', '1.jpg', '1', '494');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11170651qc2f.jpg', '6.JPG', '1', '493');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11170656zz9b.jpg', '5.jpg', '1', '493');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/111707001w2k.jpg', '4.jpg', '1', '493');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11170703j2h2.jpg', '1.jpg', '1', '493');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/111707071tn0.jpg', '2.jpg', '1', '493');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11170712hp37.jpg', '3.jpg', '1', '493');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/111707541g63.jpg', '5.jpg', '1', '493');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11171054ja61.jpg', '1.jpg', '1', '492');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11171058td8f.jpg', '2.jpg', '1', '492');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11171102ae78.jpg', '3.jpg', '1', '492');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11171106qaui.jpg', '4.jpg', '1', '492');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11171109m2sr.jpg', '5.jpg', '1', '492');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11171211kmih.jpg', '1.jpg', '1', '492');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11171546aux9.jpg', '1.jpg', '1', '491');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/111715499z6t.jpg', '2.jpg', '1', '491');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11171553t5kf.jpg', '3.jpg', '1', '491');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11171558kiit.jpg', '4.jpg', '1', '491');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11171603em6o.jpg', '5.jpg', '1', '491');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11171606wnmy.jpg', '6.JPG', '1', '491');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/1117164754x7.jpg', '2.jpg', '1', '491');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11172127ltqx.jpg', '1.jpg', '1', '490');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/111721308eyj.jpg', '2.jpg', '1', '490');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11172134wikw.jpg', '3.jpg', '1', '490');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11172137nwy5.jpg', '4.jpg', '1', '490');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11172219hw2t.jpg', '4.jpg', '1', '490');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11173208w8ji.jpg', '6.JPG', '1', '489');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11173213qlec.jpg', '5.jpg', '1', '489');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/111732181p5u.jpg', '4.jpg', '1', '489');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11173222qknp.jpg', '3.jpg', '1', '489');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/1117323496nx.jpg', '2.jpg', '1', '489');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11173244ggfv.jpg', '1.jpg', '1', '489');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11173334yvdd.jpg', '2.jpg', '1', '489');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/111737053v9i.jpg', '1.jpg', '1', '488');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11173708ffx2.jpg', '2.jpg', '1', '488');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11173711toqx.jpg', '3.jpg', '1', '488');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11173718do4k.jpg', '5.jpg', '1', '488');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11173719ykxk.jpg', '4.jpg', '1', '488');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11173724ul7o.jpg', '6.JPG', '1', '488');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11173804j6hj.jpg', '1.jpg', '1', '488');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11174436pnoi.jpg', '1.jpg', '1', '487');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/11174439eb3l.jpg', '2.jpg', '1', '487');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/1117444317kg.jpg', '3.jpg', '1', '487');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/111744466aux.jpg', '4.jpg', '1', '487');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/111745157ps9.jpg', '2.jpg', '1', '487');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/12105908yuqz.jpg', '1.jpg', '1', '513');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/12110240hrzh.jpg', '5.jpg', '1', '514');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/121104013vts.jpg', '1.jpg', '1', '514');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/12110817ejy1.jpg', '1.jpg', '1', '515');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/1211124392sy.jpg', '1.jpg', '1', '516');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/1211190051if.jpg', '1.jpg', '1', '517');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/12112247lfhe.jpg', '1.jpg', '1', '518');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/121207389dlf.jpg', '1.jpg', '0', '519');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/12134300ap1h.jpg', '1.jpg', '0', '520');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/12134552bk69.jpg', '6.jpg', '1', '521');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/12134933sxsx.jpg', '1.jpg', '1', '522');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/12135243x57l.jpg', '1.jpg', '1', '523');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/12135827xy0y.jpg', '1.jpg', '1', '524');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/12135944xk9f.jpg', '1.jpg', '1', '520');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/12140033b5mx.jpg', '1.jpg', '1', '519');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/12140851yp14.jpg', '1.jpg', '1', '525');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/121414505xah.jpg', '1.jpg', '1', '526');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/12142322wa0z.jpg', '1.jpg', '1', '527');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/12142830poeb.jpg', '1.jpg', '1', '528');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/12143342sfmg.jpg', '1.jpg', '1', '529');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/121456599tnd.jpg', '1.jpg', '1', '530');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/12150701tmkd.jpg', '1.jpg', '1', '531');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/12151139jrv6.jpg', '1.jpg', '1', '532');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/12152257va9g.jpg', '1.jpg', '1', '533');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/121536505s2h.jpg', '1.jpg', '1', '534');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/121543059cct.jpg', '1.jpg', '1', '535');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/121547582f5t.jpg', '1.jpg', '1', '536');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/22093458gynd.jpg', '1.jpg', '1', '511');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/22093502mmft.jpg', '2.jpg', '1', '511');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/22093506l8pv.jpg', '3.jpg', '1', '511');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/22093509qm3l.jpg', '4.jpg', '1', '511');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/22093513srmf.jpg', '5.jpg', '1', '511');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/22094752xoxd.jpg', '1.jpg', '1', '510');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/22094906lrj8.jpg', '2.jpg', '1', '510');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/22094911xe9x.jpg', '3.jpg', '1', '510');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/22094915t8h1.jpg', '4.jpg', '1', '510');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/22094918gnze.jpg', '5.jpg', '1', '510');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/22100555lytj.jpg', '1.jpg', '1', '506');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/22100558gfsb.jpg', '2.jpg', '1', '506');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/22100601l1us.jpg', '3.jpg', '1', '506');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/22100606t8mw.jpg', '4.jpg', '1', '506');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/22100611o2gl.jpg', '5.jpg', '1', '506');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/24102446b7al.jpg', '18152708v6mc.jpg', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/24102503z9wj.jpg', '23095102fzo0.jpg', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/26150136kryi.txt', 'dfd.txt', '0', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/29093548tinl.jpg', '10144918u244.jpg', '1', '433');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/29094130ex66.jpg', '1.jpg', '1', '439');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/2909462878ng.jpg', '2.jpg', '1', '438');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/2909470165q8.jpg', '3.jpg', '1', '437');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201309/29094750vq4w.jpg', '4.jpg', '1', '436');
INSERT INTO `jc_file` VALUES ('/u/cms/www/201404/2713005554hy.jpg', 'QQ图片20140325090900.jpg', '1', null);
INSERT INTO `jc_file` VALUES ('/u/cms/www/201406/08070401nz46.avi', '/u/cms/www/201406/08070401nz46.avi', '1', '551');

-- ----------------------------
-- Table structure for `jc_friendlink`
-- ----------------------------
DROP TABLE IF EXISTS `jc_friendlink`;
CREATE TABLE `jc_friendlink` (
  `friendlink_id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL,
  `friendlinkctg_id` int(11) NOT NULL,
  `site_name` varchar(150) NOT NULL COMMENT '网站名称',
  `domain` varchar(255) NOT NULL COMMENT '网站地址',
  `logo` varchar(150) DEFAULT NULL COMMENT '图标',
  `email` varchar(100) DEFAULT NULL COMMENT '站长邮箱',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `views` int(11) NOT NULL DEFAULT '0' COMMENT '点击次数',
  `is_enabled` char(1) NOT NULL DEFAULT '1' COMMENT '是否显示',
  `priority` int(11) NOT NULL DEFAULT '10' COMMENT '排列顺序',
  PRIMARY KEY (`friendlink_id`),
  KEY `fk_jc_ctg_friendlink` (`friendlinkctg_id`),
  KEY `fk_jc_friendlink_site` (`site_id`),
  CONSTRAINT `fk_jc_ctg_friendlink` FOREIGN KEY (`friendlinkctg_id`) REFERENCES `jc_friendlink_ctg` (`friendlinkctg_id`),
  CONSTRAINT `fk_jc_friendlink_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='CMS友情链接';

-- ----------------------------
-- Records of jc_friendlink
-- ----------------------------
INSERT INTO `jc_friendlink` VALUES ('3', '1', '2', '京东商城', 'http://www.360buy.com/', '/u/cms/www/201112/1910271036lr.gif', '', '', '4', '1', '10');
INSERT INTO `jc_friendlink` VALUES ('4', '1', '2', '当当网', 'http://www.dangdang.com/', '/u/cms/www/201112/191408344rwj.gif', '', '', '0', '1', '10');
INSERT INTO `jc_friendlink` VALUES ('5', '1', '2', '亚马逊', 'http://www.amazon.cn/', '/u/cms/www/201112/19141012lh2q.gif', '', '', '0', '1', '10');
INSERT INTO `jc_friendlink` VALUES ('6', '1', '2', 'ihush', 'http://www.ihush.com/', '/u/cms/www/201112/19141255yrfb.gif', '', '', '1', '1', '10');
INSERT INTO `jc_friendlink` VALUES ('7', '1', '2', '名品打折', 'http://temai.dazhe.cn', '/u/cms/www/201112/19141401rp2d.gif', '', '', '2', '1', '10');
INSERT INTO `jc_friendlink` VALUES ('8', '1', '2', '优品', 'http://temai.dazhe.cn', '/u/cms/www/201112/191415078pzs.gif', '', '', '4', '1', '10');

-- ----------------------------
-- Table structure for `jc_friendlink_ctg`
-- ----------------------------
DROP TABLE IF EXISTS `jc_friendlink_ctg`;
CREATE TABLE `jc_friendlink_ctg` (
  `friendlinkctg_id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL,
  `friendlinkctg_name` varchar(50) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL DEFAULT '10' COMMENT '排列顺序',
  PRIMARY KEY (`friendlinkctg_id`),
  KEY `fk_jc_friendlinkctg_site` (`site_id`),
  CONSTRAINT `fk_jc_friendlinkctg_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='CMS友情链接类别';

-- ----------------------------
-- Records of jc_friendlink_ctg
-- ----------------------------
INSERT INTO `jc_friendlink_ctg` VALUES ('1', '1', '文字链接', '1');
INSERT INTO `jc_friendlink_ctg` VALUES ('2', '1', '品牌专区（图片链接）', '2');

-- ----------------------------
-- Table structure for `jc_group`
-- ----------------------------
DROP TABLE IF EXISTS `jc_group`;
CREATE TABLE `jc_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(100) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL DEFAULT '10' COMMENT '排列顺序',
  `need_captcha` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否需要验证码',
  `need_check` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否需要审核',
  `allow_per_day` int(11) NOT NULL DEFAULT '4096' COMMENT '每日允许上传KB',
  `allow_max_file` int(11) NOT NULL DEFAULT '1024' COMMENT '每个文件最大KB',
  `allow_suffix` varchar(255) DEFAULT 'jpg,jpeg,gif,png,bmp' COMMENT '允许上传的后缀',
  `is_reg_def` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否默认会员组',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='CMS会员组表';

-- ----------------------------
-- Records of jc_group
-- ----------------------------
INSERT INTO `jc_group` VALUES ('1', '普通会员', '10', '1', '1', '4096', '1024', 'jpg,jpeg,gif,png,bmp', '1');
INSERT INTO `jc_group` VALUES ('2', '高级组', '10', '1', '1', '0', '0', '', '0');

-- ----------------------------
-- Table structure for `jc_guestbook`
-- ----------------------------
DROP TABLE IF EXISTS `jc_guestbook`;
CREATE TABLE `jc_guestbook` (
  `guestbook_id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL,
  `guestbookctg_id` int(11) NOT NULL,
  `member_id` int(11) DEFAULT NULL COMMENT '留言会员',
  `admin_id` int(11) DEFAULT NULL COMMENT '回复管理员',
  `ip` varchar(50) NOT NULL COMMENT '留言IP',
  `create_time` datetime NOT NULL COMMENT '留言时间',
  `replay_time` datetime DEFAULT NULL COMMENT '回复时间',
  `is_checked` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否审核',
  `is_recommend` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否推荐',
  PRIMARY KEY (`guestbook_id`),
  KEY `fk_jc_ctg_guestbook` (`guestbookctg_id`),
  KEY `fk_jc_guestbook_admin` (`admin_id`),
  KEY `fk_jc_guestbook_member` (`member_id`),
  KEY `fk_jc_guestbook_site` (`site_id`),
  CONSTRAINT `fk_jc_ctg_guestbook` FOREIGN KEY (`guestbookctg_id`) REFERENCES `jc_guestbook_ctg` (`guestbookctg_id`),
  CONSTRAINT `fk_jc_guestbook_admin` FOREIGN KEY (`admin_id`) REFERENCES `jc_user` (`user_id`),
  CONSTRAINT `fk_jc_guestbook_member` FOREIGN KEY (`member_id`) REFERENCES `jc_user` (`user_id`),
  CONSTRAINT `fk_jc_guestbook_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS留言';

-- ----------------------------
-- Records of jc_guestbook
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_guestbook_ctg`
-- ----------------------------
DROP TABLE IF EXISTS `jc_guestbook_ctg`;
CREATE TABLE `jc_guestbook_ctg` (
  `guestbookctg_id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL,
  `ctg_name` varchar(100) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL DEFAULT '10' COMMENT '排列顺序',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`guestbookctg_id`),
  KEY `fk_jc_guestbookctg_site` (`site_id`),
  CONSTRAINT `fk_jc_guestbookctg_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS留言类别';

-- ----------------------------
-- Records of jc_guestbook_ctg
-- ----------------------------
INSERT INTO `jc_guestbook_ctg` VALUES ('1', '1', '普通', '1', '普通留言');

-- ----------------------------
-- Table structure for `jc_guestbook_ext`
-- ----------------------------
DROP TABLE IF EXISTS `jc_guestbook_ext`;
CREATE TABLE `jc_guestbook_ext` (
  `guestbook_id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL COMMENT '留言标题',
  `content` longtext COMMENT '留言内容',
  `reply` longtext COMMENT '回复内容',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(100) DEFAULT NULL COMMENT '电话',
  `qq` varchar(50) DEFAULT NULL COMMENT 'QQ',
  KEY `fk_jc_ext_guestbook` (`guestbook_id`),
  CONSTRAINT `fk_jc_ext_guestbook` FOREIGN KEY (`guestbook_id`) REFERENCES `jc_guestbook` (`guestbook_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS留言内容';

-- ----------------------------
-- Records of jc_guestbook_ext
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_job_apply`
-- ----------------------------
DROP TABLE IF EXISTS `jc_job_apply`;
CREATE TABLE `jc_job_apply` (
  `job_apply_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `content_id` int(11) NOT NULL COMMENT '职位id',
  `apply_time` datetime NOT NULL COMMENT '申请时间',
  PRIMARY KEY (`job_apply_id`),
  KEY `fk_jc_job_apply_user` (`user_id`),
  KEY `fk_jc_job_apply_content` (`content_id`),
  CONSTRAINT `fk_jc_job_apply_content` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`),
  CONSTRAINT `fk_jc_job_apply_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='职位申请表';

-- ----------------------------
-- Records of jc_job_apply
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_keyword`
-- ----------------------------
DROP TABLE IF EXISTS `jc_keyword`;
CREATE TABLE `jc_keyword` (
  `keyword_id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT NULL COMMENT '站点ID',
  `keyword_name` varchar(100) NOT NULL COMMENT '名称',
  `url` varchar(255) NOT NULL COMMENT '链接',
  `is_disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  PRIMARY KEY (`keyword_id`),
  KEY `fk_jc_keyword_site` (`site_id`),
  CONSTRAINT `fk_jc_keyword_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS内容关键词表';

-- ----------------------------
-- Records of jc_keyword
-- ----------------------------
INSERT INTO `jc_keyword` VALUES ('1', null, '内容管理系统', '<a href=\"http://www.jeecms.com/\" target=\"_blank\">内容管理系统</a>', '0');

-- ----------------------------
-- Table structure for `jc_log`
-- ----------------------------
DROP TABLE IF EXISTS `jc_log`;
CREATE TABLE `jc_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `site_id` int(11) DEFAULT NULL,
  `category` int(11) NOT NULL COMMENT '日志类型',
  `log_time` datetime NOT NULL COMMENT '日志时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `url` varchar(255) DEFAULT NULL COMMENT 'URL地址',
  `title` varchar(255) DEFAULT NULL COMMENT '日志标题',
  `content` varchar(255) DEFAULT NULL COMMENT '日志内容',
  PRIMARY KEY (`log_id`),
  KEY `fk_jc_log_site` (`site_id`),
  KEY `fk_jc_log_user` (`user_id`),
  CONSTRAINT `fk_jc_log_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
  CONSTRAINT `fk_jc_log_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8 COMMENT='CMS日志表';

-- ----------------------------
-- Records of jc_log
-- ----------------------------
INSERT INTO `jc_log` VALUES ('60', null, null, '2', '2014-05-31 12:52:57', '127.0.0.1', '/admin/s/login.do', '登录失败', 'username=admin');
INSERT INTO `jc_log` VALUES ('61', null, null, '2', '2014-05-31 12:53:26', '127.0.0.1', '/admin/s/login.do', '登录失败', 'username=admin');
INSERT INTO `jc_log` VALUES ('62', '1', null, '1', '2014-05-31 12:54:06', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('63', null, null, '2', '2014-05-31 20:42:11', '127.0.0.1', '/admin/s/login.do', '登录失败', 'username=admin');
INSERT INTO `jc_log` VALUES ('64', '1', null, '1', '2014-05-31 20:42:19', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('65', '1', null, '1', '2014-05-31 21:58:47', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('66', '1', null, '1', '2014-06-01 21:43:23', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('67', '1', null, '1', '2014-06-01 21:46:59', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('68', '1', '1', '3', '2014-06-01 22:12:42', '127.0.0.1', '/admin/s/channel/o_save.do', '增加栏目', 'id=66;title=直播');
INSERT INTO `jc_log` VALUES ('69', '1', '1', '3', '2014-06-01 22:15:23', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=66;name=直播');
INSERT INTO `jc_log` VALUES ('70', '1', null, '1', '2014-06-07 16:03:51', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('71', '1', null, '1', '2014-06-07 16:07:54', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('72', '1', '1', '3', '2014-06-07 16:08:37', '127.0.0.1', '/admin/s/model/o_update.do', '修改模型', 'id=1;name=新闻');
INSERT INTO `jc_log` VALUES ('73', '1', '1', '3', '2014-06-07 16:08:49', '127.0.0.1', '/admin/s/model/o_update.do', '修改模型', 'id=2;name=单页');
INSERT INTO `jc_log` VALUES ('74', '1', '1', '3', '2014-06-07 16:09:23', '127.0.0.1', '/admin/s/model/o_update.do', '修改模型', 'id=11;name=直播');
INSERT INTO `jc_log` VALUES ('75', '1', '1', '3', '2014-06-07 16:09:53', '127.0.0.1', '/admin/s/model/o_update.do', '修改模型', 'id=6;name=视频');
INSERT INTO `jc_log` VALUES ('76', '1', '1', '3', '2014-06-07 16:10:09', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=10;name=关于我们');
INSERT INTO `jc_log` VALUES ('77', '1', '1', '3', '2014-06-07 16:10:36', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=66;name=直播');
INSERT INTO `jc_log` VALUES ('78', '1', '1', '3', '2014-06-07 16:10:58', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=58;name=网络调查');
INSERT INTO `jc_log` VALUES ('79', '1', '1', '3', '2014-06-07 16:11:11', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=49;name=电视剧');
INSERT INTO `jc_log` VALUES ('80', '1', '1', '3', '2014-06-07 16:11:18', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=50;name=纪录片');
INSERT INTO `jc_log` VALUES ('81', '1', '1', '3', '2014-06-07 16:11:25', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=61;name=电影');
INSERT INTO `jc_log` VALUES ('82', '1', '1', '3', '2014-06-07 16:11:56', '127.0.0.1', '/admin/s/model/o_update.do', '修改模型', 'id=5;name=图库');
INSERT INTO `jc_log` VALUES ('83', '1', '1', '3', '2014-06-07 16:12:13', '127.0.0.1', '/admin/s/model/o_update.do', '修改模型', 'id=9;name=专题栏目');
INSERT INTO `jc_log` VALUES ('84', '1', '1', '3', '2014-06-07 16:12:32', '127.0.0.1', '/admin/s/model/o_update.do', '修改模型', 'id=4;name=下载');
INSERT INTO `jc_log` VALUES ('85', '1', '1', '3', '2014-06-07 16:12:50', '127.0.0.1', '/admin/s/model/o_update.do', '修改模型', 'id=7;name=产品');
INSERT INTO `jc_log` VALUES ('86', '1', '1', '3', '2014-06-07 16:13:04', '127.0.0.1', '/admin/s/model/o_update.do', '修改模型', 'id=8;name=招聘');
INSERT INTO `jc_log` VALUES ('87', '1', '1', '3', '2014-06-07 16:13:59', '127.0.0.1', '/admin/s/model/o_update.do', '修改模型', 'id=3;name=作品');
INSERT INTO `jc_log` VALUES ('88', '1', '1', '3', '2014-06-07 16:14:10', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=43;name=娱乐图集');
INSERT INTO `jc_log` VALUES ('89', '1', '1', '3', '2014-06-07 16:14:26', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=44;name=娱乐新闻');
INSERT INTO `jc_log` VALUES ('90', '1', '1', '3', '2014-06-07 16:14:34', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=45;name=文化校园');
INSERT INTO `jc_log` VALUES ('91', '1', '1', '3', '2014-06-07 16:14:53', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=63;name=产品');
INSERT INTO `jc_log` VALUES ('92', '1', '1', '3', '2014-06-07 16:15:16', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=11;name=国内新闻');
INSERT INTO `jc_log` VALUES ('93', '1', '1', '3', '2014-06-07 16:15:23', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=12;name=国际新闻');
INSERT INTO `jc_log` VALUES ('94', '1', '1', '3', '2014-06-07 16:15:28', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=13;name=社会热点');
INSERT INTO `jc_log` VALUES ('95', '1', '1', '3', '2014-06-07 16:15:36', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=14;name=时事评论');
INSERT INTO `jc_log` VALUES ('96', '1', '1', '3', '2014-06-07 16:15:41', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=15;name=图片新闻');
INSERT INTO `jc_log` VALUES ('97', '1', '1', '3', '2014-06-07 16:15:50', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=40;name=基金视点');
INSERT INTO `jc_log` VALUES ('98', '1', '1', '3', '2014-06-07 16:15:55', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=41;name=财经报道');
INSERT INTO `jc_log` VALUES ('99', '1', null, '1', '2014-06-07 19:26:34', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('100', '1', '1', '3', '2014-06-07 19:27:50', '127.0.0.1', '/admin/s/model/o_save.do', '增加模型', 'id=12;name=访谈');
INSERT INTO `jc_log` VALUES ('101', '1', '1', '3', '2014-06-07 19:28:41', '127.0.0.1', '/admin/s/channel/o_save.do', '增加栏目', 'id=67;title=null');
INSERT INTO `jc_log` VALUES ('102', '1', '1', '3', '2014-06-07 19:29:09', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=67;name=访谈');
INSERT INTO `jc_log` VALUES ('103', '1', '1', '3', '2014-06-07 20:13:51', '127.0.0.1', '/admin/s/channel/o_delete.do', '删除栏目', 'id=63;title=产品');
INSERT INTO `jc_log` VALUES ('104', '1', '1', '3', '2014-06-07 20:23:17', '127.0.0.1', '/admin/s/template/o_save.do', '增加模板', 'filename=interview');
INSERT INTO `jc_log` VALUES ('105', '1', '1', '3', '2014-06-07 20:23:36', '127.0.0.1', '/admin/s/template/o_delete.do', '删除模板', 'filename=/WEB-INF/t/cms/www/def/channel/download.html');
INSERT INTO `jc_log` VALUES ('106', '1', '1', '3', '2014-06-07 20:23:36', '127.0.0.1', '/admin/s/template/o_delete.do', '删除模板', 'filename=/WEB-INF/t/cms/www/def/channel/download_sub.html');
INSERT INTO `jc_log` VALUES ('107', '1', '1', '3', '2014-06-07 20:23:36', '127.0.0.1', '/admin/s/template/o_delete.do', '删除模板', 'filename=/WEB-INF/t/cms/www/def/channel/job.html');
INSERT INTO `jc_log` VALUES ('108', '1', '1', '3', '2014-06-07 20:23:36', '127.0.0.1', '/admin/s/template/o_delete.do', '删除模板', 'filename=/WEB-INF/t/cms/www/def/channel/prod.html');
INSERT INTO `jc_log` VALUES ('109', '1', '1', '3', '2014-06-07 20:23:36', '127.0.0.1', '/admin/s/template/o_delete.do', '删除模板', 'filename=/WEB-INF/t/cms/www/def/channel/works.html');
INSERT INTO `jc_log` VALUES ('110', '1', '1', '3', '2014-06-07 20:23:36', '127.0.0.1', '/admin/s/template/o_delete.do', '删除模板', 'filename=/WEB-INF/t/cms/www/def/channel/works_sub.html');
INSERT INTO `jc_log` VALUES ('111', '1', '1', '3', '2014-06-07 20:24:03', '127.0.0.1', '/admin/s/template/o_save.do', '增加模板', 'filename=live');
INSERT INTO `jc_log` VALUES ('112', '1', '1', '3', '2014-06-07 20:24:07', '127.0.0.1', '/admin/s/template/o_delete.do', '删除模板', 'filename=/WEB-INF/t/cms/www/def/channel/zhibo.html');
INSERT INTO `jc_log` VALUES ('113', '1', '1', '3', '2014-06-07 20:25:27', '127.0.0.1', '/admin/s/template/o_ajaxUpdate.do', '修改模板', 'filename=/WEB-INF/t/cms/www/def/channel/interview.html');
INSERT INTO `jc_log` VALUES ('114', '1', '1', '3', '2014-06-07 20:25:44', '127.0.0.1', '/admin/s/template/o_ajaxUpdate.do', '修改模板', 'filename=/WEB-INF/t/cms/www/def/channel/live.html');
INSERT INTO `jc_log` VALUES ('115', '1', '1', '3', '2014-06-07 20:26:07', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=66;name=直播');
INSERT INTO `jc_log` VALUES ('116', '1', null, '1', '2014-06-07 20:38:48', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('117', '1', null, '1', '2014-06-07 23:15:21', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('118', '1', '1', '3', '2014-06-07 23:17:25', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=46;name=视频');
INSERT INTO `jc_log` VALUES ('119', '1', '1', '3', '2014-06-07 23:23:56', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=66;name=直播');
INSERT INTO `jc_log` VALUES ('120', '1', '1', '3', '2014-06-07 23:24:09', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=66;name=直播');
INSERT INTO `jc_log` VALUES ('121', '1', '1', '3', '2014-06-07 23:24:37', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=67;name=访谈');
INSERT INTO `jc_log` VALUES ('122', '1', null, '1', '2014-06-08 06:28:29', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('123', '1', '1', '3', '2014-06-08 07:04:10', '127.0.0.1', '/admin/s/content/o_save.do', '增加文章', 'id=551;title=赵又廷高圆圆');
INSERT INTO `jc_log` VALUES ('124', '1', '1', '3', '2014-06-08 07:05:35', '127.0.0.1', '/admin/s/template/o_save.do', '增加模板', 'filename=interview_content');
INSERT INTO `jc_log` VALUES ('125', '1', null, '1', '2014-06-08 15:54:40', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('126', '1', null, '1', '2014-06-08 16:26:11', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('127', '1', null, '1', '2014-06-08 16:59:34', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('128', '1', '1', '3', '2014-06-08 17:00:48', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=67;name=访谈');
INSERT INTO `jc_log` VALUES ('129', '1', null, '1', '2014-06-09 07:14:34', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('130', '1', null, '1', '2014-06-09 07:31:17', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('131', '1', null, '1', '2014-06-09 07:35:51', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('132', '1', '1', '3', '2014-06-09 07:39:54', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=11;name=国内新闻');
INSERT INTO `jc_log` VALUES ('133', '1', '1', '3', '2014-06-09 07:40:12', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=12;name=国际新闻');
INSERT INTO `jc_log` VALUES ('134', '1', '1', '3', '2014-06-09 07:40:23', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=13;name=社会热点');
INSERT INTO `jc_log` VALUES ('135', '1', '1', '3', '2014-06-09 07:40:34', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=14;name=时事评论');
INSERT INTO `jc_log` VALUES ('136', '1', '1', '3', '2014-06-09 07:40:45', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=15;name=图片新闻');
INSERT INTO `jc_log` VALUES ('137', '1', '1', '3', '2014-06-09 07:40:53', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=40;name=基金视点');
INSERT INTO `jc_log` VALUES ('138', '1', '1', '3', '2014-06-09 07:41:01', '127.0.0.1', '/admin/s/channel/o_update.do', '修改栏目', 'id=41;name=财经报道');
INSERT INTO `jc_log` VALUES ('139', '1', null, '1', '2014-06-09 10:21:32', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('140', '1', null, '1', '2014-06-09 17:39:16', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('141', '1', null, '1', '2014-06-09 23:18:22', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('142', '1', '1', '3', '2014-06-09 23:22:02', '127.0.0.1', '/admin/s/content/o_save.do', '增加文章', 'id=552;title=测试直播');
INSERT INTO `jc_log` VALUES ('143', '1', '1', '3', '2014-06-09 23:23:47', '127.0.0.1', '/admin/s/template/o_ajaxUpdate.do', '修改模板', 'filename=/WEB-INF/t/cms/www/def/content/live_content.html');
INSERT INTO `jc_log` VALUES ('144', '1', '1', '3', '2014-06-09 23:24:29', '127.0.0.1', '/admin/s/template/o_ajaxUpdate.do', '修改模板', 'filename=/WEB-INF/t/cms/www/def/content/live_content.html');
INSERT INTO `jc_log` VALUES ('145', '1', '1', '3', '2014-06-09 23:25:56', '127.0.0.1', '/admin/s/template/o_ajaxUpdate.do', '修改模板', 'filename=/WEB-INF/t/cms/www/def/content/live_content.html');
INSERT INTO `jc_log` VALUES ('146', '1', '1', '3', '2014-06-09 23:28:11', '127.0.0.1', '/admin/s/template/o_ajaxUpdate.do', '修改模板', 'filename=/WEB-INF/t/cms/www/def/content/live_content.html');
INSERT INTO `jc_log` VALUES ('147', '1', '1', '3', '2014-06-09 23:29:01', '127.0.0.1', '/admin/s/template/o_ajaxUpdate.do', '修改模板', 'filename=/WEB-INF/t/cms/www/def/content/live_content.html');
INSERT INTO `jc_log` VALUES ('148', '1', '1', '3', '2014-06-09 23:30:58', '127.0.0.1', '/admin/s/template/o_ajaxUpdate.do', '修改模板', 'filename=/WEB-INF/t/cms/www/def/content/live_content.html');
INSERT INTO `jc_log` VALUES ('149', '1', '1', '3', '2014-06-09 23:31:20', '127.0.0.1', '/admin/s/template/o_ajaxUpdate.do', '修改模板', 'filename=/WEB-INF/t/cms/www/def/content/live_content.html');
INSERT INTO `jc_log` VALUES ('150', '1', '1', '3', '2014-06-09 23:31:32', '127.0.0.1', '/admin/s/template/o_ajaxUpdate.do', '修改模板', 'filename=/WEB-INF/t/cms/www/def/content/live_content.html');
INSERT INTO `jc_log` VALUES ('151', '1', '1', '3', '2014-06-09 23:32:22', '127.0.0.1', '/admin/s/content/o_update.do', '修改文章', 'id=552;title=测试直播');
INSERT INTO `jc_log` VALUES ('152', '1', '1', '3', '2014-06-09 23:34:05', '127.0.0.1', '/admin/s/template/o_ajaxUpdate.do', '修改模板', 'filename=/WEB-INF/t/cms/www/def/content/live_content.html');
INSERT INTO `jc_log` VALUES ('153', '1', '1', '3', '2014-06-09 23:35:07', '127.0.0.1', '/admin/s/template/o_ajaxUpdate.do', '修改模板', 'filename=/WEB-INF/t/cms/www/def/content/live_content.html');
INSERT INTO `jc_log` VALUES ('154', '1', '1', '3', '2014-06-09 23:36:03', '127.0.0.1', '/admin/s/template/o_ajaxUpdate.do', '修改模板', 'filename=/WEB-INF/t/cms/www/def/content/live_content.html');
INSERT INTO `jc_log` VALUES ('155', '1', '1', '3', '2014-06-09 23:47:12', '127.0.0.1', '/admin/s/template/o_ajaxUpdate.do', '修改模板', 'filename=/WEB-INF/t/cms/www/def/content/live_content.html');
INSERT INTO `jc_log` VALUES ('156', '1', '1', '3', '2014-06-09 23:47:59', '127.0.0.1', '/admin/s/template/o_ajaxUpdate.do', '修改模板', 'filename=/WEB-INF/t/cms/www/def/content/live_content.html');
INSERT INTO `jc_log` VALUES ('157', '1', null, '1', '2014-06-10 09:22:58', '127.0.0.1', '/admin/s/login.do', '登录成功', null);
INSERT INTO `jc_log` VALUES ('158', '1', '1', '3', '2014-06-10 09:24:27', '127.0.0.1', '/admin/s/template/o_ajaxUpdate.do', '修改模板', 'filename=/WEB-INF/t/cms/www/def/content/live_content.html');

-- ----------------------------
-- Table structure for `jc_message`
-- ----------------------------
DROP TABLE IF EXISTS `jc_message`;
CREATE TABLE `jc_message` (
  `msg_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `msg_title` varchar(255) NOT NULL DEFAULT '' COMMENT '标题',
  `msg_content` longtext COMMENT '站内信息内容',
  `send_time` timestamp NULL DEFAULT NULL COMMENT '发送时间',
  `msg_send_user` int(11) NOT NULL DEFAULT '1' COMMENT '发信息人',
  `msg_receiver_user` int(11) NOT NULL DEFAULT '0' COMMENT '接收人',
  `site_id` int(11) NOT NULL DEFAULT '1' COMMENT '站点',
  `msg_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '消息状态0未读，1已读',
  `msg_box` int(2) NOT NULL DEFAULT '1' COMMENT '消息信箱 0收件箱 1发件箱 2草稿箱 3垃圾箱',
  PRIMARY KEY (`msg_id`),
  KEY `fk_jc_message_user_send` (`msg_send_user`),
  KEY `fk_jc_message_user_receiver` (`msg_receiver_user`),
  KEY `fk_jc_message_site` (`site_id`),
  CONSTRAINT `fk_jc_message_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
  CONSTRAINT `fk_jc_message_user_receiver` FOREIGN KEY (`msg_receiver_user`) REFERENCES `jc_user` (`user_id`),
  CONSTRAINT `fk_jc_message_user_send` FOREIGN KEY (`msg_send_user`) REFERENCES `jc_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='站内信';

-- ----------------------------
-- Records of jc_message
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_model`
-- ----------------------------
DROP TABLE IF EXISTS `jc_model`;
CREATE TABLE `jc_model` (
  `model_id` int(11) NOT NULL,
  `model_name` varchar(100) NOT NULL COMMENT '名称',
  `model_path` varchar(100) NOT NULL COMMENT '路径',
  `tpl_channel_prefix` varchar(20) DEFAULT NULL COMMENT '栏目模板前缀',
  `tpl_content_prefix` varchar(20) DEFAULT NULL COMMENT '内容模板前缀',
  `title_img_width` int(11) NOT NULL DEFAULT '139' COMMENT '栏目标题图宽度',
  `title_img_height` int(11) NOT NULL DEFAULT '139' COMMENT '栏目标题图高度',
  `content_img_width` int(11) NOT NULL DEFAULT '310' COMMENT '栏目内容图宽度',
  `content_img_height` int(11) NOT NULL DEFAULT '310' COMMENT '栏目内容图高度',
  `priority` int(11) NOT NULL DEFAULT '10' COMMENT '排列顺序',
  `has_content` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有内容',
  `is_disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  `is_def` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否默认模型',
  PRIMARY KEY (`model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS模型表';

-- ----------------------------
-- Records of jc_model
-- ----------------------------
INSERT INTO `jc_model` VALUES ('1', '新闻', '1', 'news', 'news_content', '139', '139', '310', '310', '1', '1', '0', '1');
INSERT INTO `jc_model` VALUES ('2', '单页', '2', 'alone', '', '139', '139', '310', '310', '2', '0', '0', '0');
INSERT INTO `jc_model` VALUES ('5', '图库', '5', 'pic', 'pic_content', '139', '139', '310', '310', '5', '1', '0', '0');
INSERT INTO `jc_model` VALUES ('6', '视频', '6', 'video', 'video_content', '139', '139', '310', '310', '10', '1', '0', '0');
INSERT INTO `jc_model` VALUES ('9', '专题栏目', '9', 'topic', 'topic_content', '139', '139', '310', '310', '9', '1', '0', '0');
INSERT INTO `jc_model` VALUES ('11', '直播', '11', 'live', 'live_content', '139', '139', '310', '310', '10', '1', '0', '0');
INSERT INTO `jc_model` VALUES ('12', '访谈', '12', 'interview', 'interview_content', '139', '139', '310', '310', '10', '1', '0', '0');

-- ----------------------------
-- Table structure for `jc_model_item`
-- ----------------------------
DROP TABLE IF EXISTS `jc_model_item`;
CREATE TABLE `jc_model_item` (
  `modelitem_id` int(11) NOT NULL AUTO_INCREMENT,
  `model_id` int(11) NOT NULL,
  `field` varchar(50) NOT NULL COMMENT '字段',
  `item_label` varchar(100) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL DEFAULT '70' COMMENT '排列顺序',
  `def_value` varchar(255) DEFAULT NULL COMMENT '默认值',
  `opt_value` varchar(255) DEFAULT NULL COMMENT '可选项',
  `text_size` varchar(20) DEFAULT NULL COMMENT '长度',
  `area_rows` varchar(3) DEFAULT NULL COMMENT '文本行数',
  `area_cols` varchar(3) DEFAULT NULL COMMENT '文本列数',
  `help` varchar(255) DEFAULT NULL COMMENT '帮助信息',
  `help_position` varchar(1) DEFAULT NULL COMMENT '帮助位置',
  `data_type` int(11) NOT NULL DEFAULT '1' COMMENT '数据类型',
  `is_single` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否独占一行',
  `is_channel` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否栏目模型项',
  `is_custom` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否自定义',
  `is_display` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否显示',
  PRIMARY KEY (`modelitem_id`),
  KEY `fk_jc_item_model` (`model_id`),
  CONSTRAINT `fk_jc_item_model` FOREIGN KEY (`model_id`) REFERENCES `jc_model` (`model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=414 DEFAULT CHARSET=utf8 COMMENT='CMS模型项表';

-- ----------------------------
-- Records of jc_model_item
-- ----------------------------
INSERT INTO `jc_model_item` VALUES ('1', '1', 'name', '栏目名称', '10', null, null, null, null, null, null, null, '1', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('2', '1', 'path', '访问路径', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('3', '1', 'title', 'meta标题', '10', null, null, null, null, null, null, null, '1', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('4', '1', 'keywords', 'meta关键字', '10', null, null, null, null, null, null, null, '1', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('5', '1', 'description', 'meta描述', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('6', '1', 'tplChannel', '栏目模板', '10', null, null, null, null, null, null, null, '6', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('7', '1', 'tplContent', '选择模型模板', '10', null, null, null, null, null, null, null, '6', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('8', '1', 'channelStatic', '栏目静态化', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('9', '1', 'contentStatic', '内容静态化', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('10', '1', 'priority', '排列顺序', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('11', '1', 'display', '显示', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('12', '1', 'docImg', '文档图片', '10', null, null, null, null, null, null, null, '8', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('13', '1', 'finalStep', '终审级别', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('14', '1', 'afterCheck', '审核后', '10', null, null, null, null, null, null, null, '6', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('15', '1', 'commentControl', '评论', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('16', '1', 'allowUpdown', '顶踩', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('17', '1', 'viewGroupIds', '浏览权限', '10', null, null, null, null, null, null, null, '7', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('18', '1', 'contriGroupIds', '投稿权限', '10', null, null, null, null, null, null, null, '7', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('19', '1', 'userIds', '管理权限', '10', null, null, null, null, null, null, null, '7', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('20', '1', 'link', '外部链接', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('21', '1', 'titleImg', '标题图', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('22', '1', 'channelId', '栏目', '10', null, null, null, null, null, null, null, '6', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('23', '1', 'title', '标题', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('24', '1', 'shortTitle', '简短标题', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('25', '1', 'titleColor', '标题颜色', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('26', '1', 'tagStr', 'Tag标签', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('27', '1', 'description', '摘要', '10', null, null, null, null, null, null, null, '4', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('28', '1', 'author', '作者', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('29', '1', 'origin', '来源', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('30', '1', 'viewGroupIds', '浏览权限', '10', null, null, null, null, null, null, null, '7', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('31', '1', 'topLevel', '固顶级别', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('32', '1', 'releaseDate', '发布时间', '10', null, null, null, null, null, null, null, '5', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('33', '1', 'typeId', '内容类型', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('34', '1', 'tplContent', '指定模板', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('35', '1', 'typeImg', '类型图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('36', '1', 'titleImg', '标题图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('37', '1', 'contentImg', '内容图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('38', '1', 'attachments', '附件', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('39', '1', 'media', '多媒体', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('40', '1', 'txt', '内容', '10', null, null, null, null, null, null, null, '4', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('42', '2', 'name', '栏目名称', '10', null, null, null, null, null, null, null, '1', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('43', '2', 'path', '访问路径', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('44', '2', 'title', 'meta标题', '10', null, null, null, null, null, null, null, '1', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('45', '2', 'keywords', 'meta关键字', '10', null, null, null, null, null, null, null, '1', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('46', '2', 'description', 'meta描述', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('47', '2', 'tplChannel', '栏目模板', '10', null, null, null, null, null, null, null, '6', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('48', '2', 'priority', '排列顺序', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('49', '2', 'display', '显示', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('50', '2', 'viewGroupIds', '浏览权限', '10', null, null, null, null, null, null, null, '7', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('51', '2', 'link', '外部链接', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('52', '2', 'contentImg', '内容图', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('53', '2', 'txt', '内容', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('125', '5', 'name', '栏目名称', '10', null, null, null, null, null, null, null, '1', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('126', '5', 'path', '访问路径', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('127', '5', 'title', 'meta标题', '10', null, null, null, null, null, null, null, '1', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('128', '5', 'keywords', 'meta关键字', '10', null, null, null, null, null, null, null, '1', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('129', '5', 'description', 'meta描述', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('130', '5', 'tplChannel', '栏目模板', '10', null, null, null, null, null, null, null, '6', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('131', '5', 'tplContent', '选择模型模板', '10', null, null, null, null, null, null, null, '6', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('132', '5', 'channelStatic', '栏目静态化', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('133', '5', 'contentStatic', '内容静态化', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('134', '5', 'priority', '排列顺序', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('135', '5', 'display', '显示', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('136', '5', 'docImg', '文档图片', '10', null, null, null, null, null, null, null, '8', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('137', '5', 'finalStep', '终审级别', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('138', '5', 'afterCheck', '审核后', '10', null, null, null, null, null, null, null, '6', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('139', '5', 'commentControl', '评论', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('140', '5', 'allowUpdown', '顶踩', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('141', '5', 'viewGroupIds', '浏览权限', '10', null, null, null, null, null, null, null, '7', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('142', '5', 'contriGroupIds', '投稿权限', '10', null, null, null, null, null, null, null, '7', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('143', '5', 'userIds', '管理权限', '10', null, null, null, null, null, null, null, '7', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('144', '5', 'link', '外部链接', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('145', '5', 'titleImg', '标题图', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('146', '5', 'contentImg', '内容图', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('147', '5', 'channelId', '栏目', '10', null, null, null, null, null, null, null, '6', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('148', '5', 'title', '标题', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('149', '5', 'shortTitle', '简短标题', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('150', '5', 'titleColor', '标题颜色', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('151', '5', 'tagStr', 'Tag标签', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('152', '5', 'description', '摘要', '10', null, null, null, null, null, null, null, '4', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('153', '5', 'author', '作者', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('154', '5', 'origin', '来源', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('155', '5', 'viewGroupIds', '浏览权限', '10', null, null, null, null, null, null, null, '7', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('156', '5', 'topLevel', '固顶级别', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('157', '5', 'releaseDate', '发布时间', '10', null, null, null, null, null, null, null, '5', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('158', '5', 'typeId', '内容类型', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('159', '5', 'tplContent', '指定模板', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('160', '5', 'typeImg', '类型图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('161', '5', 'titleImg', '标题图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('162', '5', 'contentImg', '内容图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('163', '5', 'pictures', '图片集', '10', null, null, null, null, null, null, null, '4', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('164', '6', 'name', '栏目名称', '10', null, null, null, null, null, null, null, '1', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('165', '6', 'path', '访问路径', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('166', '6', 'title', 'meta标题', '10', null, null, null, null, null, null, null, '1', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('167', '6', 'keywords', 'meta关键字', '10', null, null, null, null, null, null, null, '1', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('168', '6', 'description', 'meta描述', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('169', '6', 'tplChannel', '栏目模板', '10', null, null, null, null, null, null, null, '6', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('170', '6', 'tplContent', '选择模型模板', '10', null, null, null, null, null, null, null, '6', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('171', '6', 'channelStatic', '栏目静态化', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('172', '6', 'contentStatic', '内容静态化', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('173', '6', 'priority', '排列顺序', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('174', '6', 'display', '显示', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('175', '6', 'docImg', '文档图片', '10', null, null, null, null, null, null, null, '8', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('176', '6', 'finalStep', '终审级别', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('177', '6', 'afterCheck', '审核后', '10', null, null, null, null, null, null, null, '6', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('178', '6', 'commentControl', '评论', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('179', '6', 'allowUpdown', '顶踩', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('180', '6', 'viewGroupIds', '浏览权限', '10', null, null, null, null, null, null, null, '7', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('181', '6', 'contriGroupIds', '投稿权限', '10', null, null, null, null, null, null, null, '7', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('182', '6', 'userIds', '管理权限', '10', null, null, null, null, null, null, null, '7', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('183', '6', 'link', '外部链接', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('184', '6', 'titleImg', '标题图', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('185', '6', 'contentImg', '内容图', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('186', '6', 'channelId', '栏目', '10', null, null, null, null, null, null, null, '6', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('187', '6', 'title', '标题', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('188', '6', 'shortTitle', '简短标题', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('189', '6', 'titleColor', '标题颜色', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('190', '6', 'tagStr', 'Tag标签', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('191', '6', 'description', '内容简介', '10', null, null, null, null, null, null, null, '4', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('192', '6', 'author', '作者', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '0');
INSERT INTO `jc_model_item` VALUES ('193', '6', 'origin', '来源', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '0');
INSERT INTO `jc_model_item` VALUES ('194', '6', 'viewGroupIds', '浏览权限', '10', null, null, null, null, null, null, null, '7', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('195', '6', 'topLevel', '固顶级别', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('196', '6', 'releaseDate', '发布时间', '10', null, null, null, null, null, null, null, '5', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('197', '6', 'typeId', '内容类型', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('198', '6', 'tplContent', '指定模板', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('199', '6', 'typeImg', '类型图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('200', '6', 'titleImg', '标题图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('201', '6', 'contentImg', '内容图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('202', '6', 'attachments', '附件', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '0');
INSERT INTO `jc_model_item` VALUES ('203', '6', 'media', '多媒体', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('299', '6', 'Director', '导演', '10', null, null, null, '1', '2', null, null, '1', '0', '0', '1', '1');
INSERT INTO `jc_model_item` VALUES ('300', '6', 'Starring', '主演', '10', null, null, null, '1', '30', null, '2', '1', '0', '0', '1', '1');
INSERT INTO `jc_model_item` VALUES ('301', '6', 'VideoType', '视频类型', '70', null, '历史,古装,都市,喜剧,悬疑,动作,谍战,伦理,战争,惊悚', null, '3', '30', null, null, '7', '0', '0', '1', '1');
INSERT INTO `jc_model_item` VALUES ('302', '6', 'Video', '影片信息', '70', null, '正片,预告片', null, '3', '30', null, null, '6', '0', '0', '1', '1');
INSERT INTO `jc_model_item` VALUES ('303', '11', 'name', '栏目名称', '10', null, null, null, null, null, null, null, '1', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('304', '11', 'path', '访问路径', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('305', '11', 'title', 'meta标题', '10', null, null, null, null, null, null, null, '1', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('306', '11', 'keywords', 'meta关键字', '10', null, null, null, null, null, null, null, '1', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('307', '11', 'description', 'meta描述', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('308', '11', 'tplChannel', '栏目模板', '10', null, null, null, null, null, null, null, '6', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('309', '11', 'tplContent', '内容模板', '10', null, null, null, null, null, null, null, '6', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('310', '11', 'channelStatic', '栏目静态化', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('311', '11', 'contentStatic', '内容静态化', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('312', '11', 'priority', '排列顺序', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('313', '11', 'display', '显示', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('314', '11', 'docImg', '文档图片', '10', null, null, null, null, null, null, null, '8', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('315', '11', 'finalStep', '终审级别', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('316', '11', 'afterCheck', '审核后', '10', null, null, null, null, null, null, null, '6', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('317', '11', 'commentControl', '评论', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('318', '11', 'allowUpdown', '顶踩', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('319', '11', 'viewGroupIds', '浏览权限', '10', null, null, null, null, null, null, null, '7', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('320', '11', 'contriGroupIds', '投稿权限', '10', null, null, null, null, null, null, null, '7', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('321', '11', 'userIds', '管理权限', '10', null, null, null, null, null, null, null, '7', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('322', '11', 'link', '外部链接', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('323', '11', 'titleImg', '标题图', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('324', '11', 'contentImg', '内容图', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('325', '11', 'txt', '内容', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('326', '11', 'txt1', '内容1', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('327', '11', 'txt2', '内容2', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('328', '11', 'txt3', '内容3', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('329', '12', 'name', '栏目名称', '10', null, null, null, null, null, null, null, '1', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('330', '12', 'path', '访问路径', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('331', '12', 'title', 'meta标题', '10', null, null, null, null, null, null, null, '1', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('332', '12', 'keywords', 'meta关键字', '10', null, null, null, null, null, null, null, '1', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('333', '12', 'description', 'meta描述', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('334', '12', 'tplChannel', '栏目模板', '10', null, null, null, null, null, null, null, '6', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('335', '12', 'tplContent', '内容模板', '10', null, null, null, null, null, null, null, '6', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('336', '12', 'channelStatic', '栏目静态化', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('337', '12', 'contentStatic', '内容静态化', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('338', '12', 'priority', '排列顺序', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('339', '12', 'display', '显示', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('340', '12', 'docImg', '文档图片', '10', null, null, null, null, null, null, null, '8', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('341', '12', 'finalStep', '终审级别', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('342', '12', 'afterCheck', '审核后', '10', null, null, null, null, null, null, null, '6', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('343', '12', 'commentControl', '评论', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('344', '12', 'allowUpdown', '顶踩', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('345', '12', 'viewGroupIds', '浏览权限', '10', null, null, null, null, null, null, null, '7', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('346', '12', 'contriGroupIds', '投稿权限', '10', null, null, null, null, null, null, null, '7', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('347', '12', 'userIds', '管理权限', '10', null, null, null, null, null, null, null, '7', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('348', '12', 'link', '外部链接', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('349', '12', 'titleImg', '标题图', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('350', '12', 'contentImg', '内容图', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('351', '12', 'txt', '内容', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('352', '12', 'txt1', '内容1', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('353', '12', 'txt2', '内容2', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('354', '12', 'txt3', '内容3', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('355', '11', 'channelId', '栏目', '10', null, null, null, null, null, null, null, '6', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('356', '11', 'title', '标题', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('357', '11', 'shortTitle', '简短标题', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('358', '11', 'titleColor', '标题颜色', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('359', '11', 'tagStr', 'Tag标签', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('360', '11', 'description', '摘要', '10', null, null, null, null, null, null, null, '4', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('361', '11', 'author', '作者', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('362', '11', 'origin', '来源', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('363', '11', 'viewGroupIds', '浏览权限', '10', null, null, null, null, null, null, null, '7', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('364', '11', 'topLevel', '固顶级别', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('365', '11', 'releaseDate', '发布时间', '10', null, null, null, null, null, null, null, '5', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('366', '11', 'typeId', '内容类型', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('367', '11', 'tplContent', '指定模板', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('368', '11', 'typeImg', '类型图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('369', '11', 'titleImg', '标题图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('370', '11', 'contentImg', '内容图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('378', '12', 'channelId', '栏目', '10', null, null, null, null, null, null, null, '6', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('379', '12', 'title', '标题', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('380', '12', 'shortTitle', '简短标题', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('381', '12', 'titleColor', '标题颜色', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('382', '12', 'tagStr', 'Tag标签', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('383', '12', 'description', '摘要', '10', null, null, null, null, null, null, null, '4', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('384', '12', 'author', '作者', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('385', '12', 'origin', '来源', '10', null, null, null, null, null, null, null, '1', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('386', '12', 'viewGroupIds', '浏览权限', '10', null, null, null, null, null, null, null, '7', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('387', '12', 'topLevel', '固顶级别', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('388', '12', 'releaseDate', '发布时间', '10', null, null, null, null, null, null, null, '5', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('389', '12', 'typeId', '内容类型', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('390', '12', 'tplContent', '指定模板', '10', null, null, null, null, null, null, null, '6', '0', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('391', '12', 'typeImg', '类型图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('392', '12', 'titleImg', '标题图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('393', '12', 'contentImg', '内容图', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('394', '12', 'attachments', '附件', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('395', '12', 'media', '多媒体', '10', null, null, null, null, null, null, null, '1', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('396', '12', 'txt', '内容', '10', null, null, null, null, null, null, null, '4', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('397', '12', 'txt1', '内容1', '10', null, null, null, null, null, null, null, '4', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('398', '12', 'txt2', '内容2', '10', null, null, null, null, null, null, null, '4', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('399', '12', 'txt3', '内容3', '10', null, null, null, null, null, null, null, '4', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('400', '12', 'pictures', '图片集', '10', null, null, null, null, null, null, null, '4', '1', '0', '0', '1');
INSERT INTO `jc_model_item` VALUES ('401', '2', 'tplContent', '内容模板', '10', null, null, null, null, null, null, null, '6', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('402', '2', 'channelStatic', '栏目静态化', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('403', '2', 'contentStatic', '内容静态化', '10', null, null, null, null, null, null, null, '4', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('404', '2', 'docImg', '文档图片', '10', null, null, null, null, null, null, null, '8', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('405', '2', 'finalStep', '终审级别', '10', null, null, null, null, null, null, null, '2', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('406', '2', 'afterCheck', '审核后', '10', null, null, null, null, null, null, null, '6', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('407', '2', 'commentControl', '评论', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('408', '2', 'allowUpdown', '顶踩', '10', null, null, null, null, null, null, null, '8', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('409', '2', 'contriGroupIds', '投稿权限', '10', null, null, null, null, null, null, null, '7', '0', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('410', '2', 'userIds', '管理权限', '10', null, null, null, null, null, null, null, '7', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('411', '2', 'titleImg', '标题图', '10', null, null, null, null, null, null, null, '1', '1', '1', '0', '1');
INSERT INTO `jc_model_item` VALUES ('412', '11', 'serverUrl', '直播地址', '70', null, null, null, '3', '30', null, null, '1', '1', '0', '1', '1');
INSERT INTO `jc_model_item` VALUES ('413', '11', 'serverPath', '直播路径', '70', '', '', '', '3', '30', '', '', '1', '1', '0', '1', '1');

-- ----------------------------
-- Table structure for `jc_receiver_message`
-- ----------------------------
DROP TABLE IF EXISTS `jc_receiver_message`;
CREATE TABLE `jc_receiver_message` (
  `msg_re_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `msg_title` varchar(255) NOT NULL DEFAULT '' COMMENT '标题',
  `msg_content` longtext COMMENT '站内信息内容',
  `send_time` timestamp NULL DEFAULT NULL COMMENT '发送时间',
  `msg_send_user` int(11) NOT NULL DEFAULT '1' COMMENT '发信息人',
  `msg_receiver_user` int(11) NOT NULL DEFAULT '0' COMMENT '接收人',
  `site_id` int(11) NOT NULL DEFAULT '1' COMMENT '站点',
  `msg_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '消息状态0未读，1已读',
  `msg_box` int(2) NOT NULL DEFAULT '1' COMMENT '消息信箱 0收件箱 1发件箱 2草稿箱 3垃圾箱',
  `msg_id` int(11) DEFAULT NULL COMMENT '发信的信件id',
  PRIMARY KEY (`msg_re_id`),
  KEY `jc_receiver_message_user_send` (`msg_send_user`),
  KEY `jc_receiver_message_user_receiver` (`msg_receiver_user`),
  KEY `jc_receiver_message_site` (`site_id`),
  KEY `jc_receiver_message_message` (`msg_re_id`),
  KEY `fk_jc_receiver_message_message` (`msg_id`),
  CONSTRAINT `fk_jc_receiver_message_message` FOREIGN KEY (`msg_id`) REFERENCES `jc_message` (`msg_id`),
  CONSTRAINT `fk_jc_receiver_message_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
  CONSTRAINT `fk_jc_receiver_message_user_receiver` FOREIGN KEY (`msg_receiver_user`) REFERENCES `jc_user` (`user_id`),
  CONSTRAINT `fk_jc_receiver_message_user_send` FOREIGN KEY (`msg_send_user`) REFERENCES `jc_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='站内信收信表';

-- ----------------------------
-- Records of jc_receiver_message
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_role`
-- ----------------------------
DROP TABLE IF EXISTS `jc_role`;
CREATE TABLE `jc_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT NULL,
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `priority` int(11) NOT NULL DEFAULT '10' COMMENT '排列顺序',
  `is_super` char(1) NOT NULL DEFAULT '0' COMMENT '拥有所有权限',
  PRIMARY KEY (`role_id`),
  KEY `fk_jc_role_site` (`site_id`),
  CONSTRAINT `fk_jc_role_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS角色表';

-- ----------------------------
-- Records of jc_role
-- ----------------------------
INSERT INTO `jc_role` VALUES ('1', null, '管理员', '10', '1');

-- ----------------------------
-- Table structure for `jc_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `jc_role_permission`;
CREATE TABLE `jc_role_permission` (
  `role_id` int(11) NOT NULL,
  `uri` varchar(100) NOT NULL,
  KEY `fk_jc_permission_role` (`role_id`),
  CONSTRAINT `fk_jc_permission_role` FOREIGN KEY (`role_id`) REFERENCES `jc_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS角色授权表';

-- ----------------------------
-- Records of jc_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_sensitivity`
-- ----------------------------
DROP TABLE IF EXISTS `jc_sensitivity`;
CREATE TABLE `jc_sensitivity` (
  `sensitivity_id` int(11) NOT NULL AUTO_INCREMENT,
  `search` varchar(255) NOT NULL COMMENT '敏感词',
  `replacement` varchar(255) NOT NULL COMMENT '替换词',
  PRIMARY KEY (`sensitivity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS敏感词表';

-- ----------------------------
-- Records of jc_sensitivity
-- ----------------------------
INSERT INTO `jc_sensitivity` VALUES ('1', '法论功', '***');

-- ----------------------------
-- Table structure for `jc_site`
-- ----------------------------
DROP TABLE IF EXISTS `jc_site`;
CREATE TABLE `jc_site` (
  `site_id` int(11) NOT NULL AUTO_INCREMENT,
  `config_id` int(11) NOT NULL COMMENT '配置ID',
  `ftp_upload_id` int(11) DEFAULT NULL COMMENT '上传ftp',
  `domain` varchar(50) NOT NULL COMMENT '域名',
  `site_path` varchar(20) NOT NULL COMMENT '路径',
  `site_name` varchar(100) NOT NULL COMMENT '网站名称',
  `short_name` varchar(100) NOT NULL COMMENT '简短名称',
  `protocol` varchar(20) NOT NULL DEFAULT 'http://' COMMENT '协议',
  `dynamic_suffix` varchar(10) NOT NULL DEFAULT '.jhtml' COMMENT '动态页后缀',
  `static_suffix` varchar(10) NOT NULL DEFAULT '.html' COMMENT '静态页后缀',
  `static_dir` varchar(50) DEFAULT NULL COMMENT '静态页存放目录',
  `is_index_to_root` char(1) NOT NULL DEFAULT '0' COMMENT '是否使用将首页放在根目录下',
  `is_static_index` char(1) NOT NULL DEFAULT '0' COMMENT '是否静态化首页',
  `locale_admin` varchar(10) NOT NULL DEFAULT 'zh_CN' COMMENT '后台本地化',
  `locale_front` varchar(10) NOT NULL DEFAULT 'zh_CN' COMMENT '前台本地化',
  `tpl_solution` varchar(50) NOT NULL DEFAULT 'default' COMMENT '模板方案',
  `final_step` tinyint(4) NOT NULL DEFAULT '2' COMMENT '终审级别',
  `after_check` tinyint(4) NOT NULL DEFAULT '2' COMMENT '审核后(1:不能修改删除;2:修改后退回;3:修改后不变)',
  `is_relative_path` char(1) NOT NULL DEFAULT '1' COMMENT '是否使用相对路径',
  `is_recycle_on` char(1) NOT NULL DEFAULT '1' COMMENT '是否开启回收站',
  `domain_alias` varchar(255) DEFAULT NULL COMMENT '域名别名',
  `domain_redirect` varchar(255) DEFAULT NULL COMMENT '域名重定向',
  `is_master` tinyint(1) DEFAULT '0' COMMENT '是否主站',
  PRIMARY KEY (`site_id`),
  UNIQUE KEY `ak_domain` (`domain`),
  KEY `fk_jc_site_config` (`config_id`),
  KEY `fk_jc_site_upload_ftp` (`ftp_upload_id`),
  CONSTRAINT `fk_jc_site_config` FOREIGN KEY (`config_id`) REFERENCES `jc_config` (`config_id`),
  CONSTRAINT `fk_jc_site_upload_ftp` FOREIGN KEY (`ftp_upload_id`) REFERENCES `jo_ftp` (`ftp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS站点表';

-- ----------------------------
-- Records of jc_site
-- ----------------------------
INSERT INTO `jc_site` VALUES ('1', '1', null, 'localhost', 'www', '为民网', '湖南经视', 'http://', '.htm', '.html', '/zhuzhan', '0', '0', 'zh_CN', 'zh_CN', 'def', '2', '1', '1', '1', '', '', '0');

-- ----------------------------
-- Table structure for `jc_site_attr`
-- ----------------------------
DROP TABLE IF EXISTS `jc_site_attr`;
CREATE TABLE `jc_site_attr` (
  `site_id` int(11) NOT NULL,
  `attr_name` varchar(30) NOT NULL COMMENT '名称',
  `attr_value` varchar(255) DEFAULT NULL COMMENT '值',
  KEY `fk_jc_attr_site` (`site_id`),
  CONSTRAINT `fk_jc_attr_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS站点属性表';

-- ----------------------------
-- Records of jc_site_attr
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_site_cfg`
-- ----------------------------
DROP TABLE IF EXISTS `jc_site_cfg`;
CREATE TABLE `jc_site_cfg` (
  `site_id` int(11) NOT NULL,
  `cfg_name` varchar(30) NOT NULL COMMENT '名称',
  `cfg_value` varchar(255) DEFAULT NULL COMMENT '值',
  KEY `fk_jc_cfg_site` (`site_id`),
  CONSTRAINT `fk_jc_cfg_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS站点配置表';

-- ----------------------------
-- Records of jc_site_cfg
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_site_company`
-- ----------------------------
DROP TABLE IF EXISTS `jc_site_company`;
CREATE TABLE `jc_site_company` (
  `site_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL COMMENT '公司名称',
  `scale` varchar(255) DEFAULT NULL COMMENT '公司规模',
  `nature` varchar(255) DEFAULT NULL COMMENT '公司性质',
  `industry` varchar(1000) DEFAULT NULL COMMENT '公司行业',
  `contact` varchar(500) DEFAULT NULL COMMENT '联系方式',
  `description` text COMMENT '公司简介',
  `address` varchar(500) DEFAULT NULL COMMENT '公司地址',
  `longitude` float(5,2) DEFAULT NULL COMMENT '经度',
  `latitude` float(4,2) DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`site_id`),
  CONSTRAINT `fk_jc_company_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司信息';

-- ----------------------------
-- Records of jc_site_company
-- ----------------------------
INSERT INTO `jc_site_company` VALUES ('1', '电子商务', '20-50人', '私企', '互联网', '', '', '', null, null);

-- ----------------------------
-- Table structure for `jc_site_flow`
-- ----------------------------
DROP TABLE IF EXISTS `jc_site_flow`;
CREATE TABLE `jc_site_flow` (
  `flow_id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) DEFAULT NULL,
  `access_ip` varchar(50) NOT NULL DEFAULT '127.0.0.1' COMMENT '访问者ip',
  `access_date` varchar(50) DEFAULT NULL COMMENT '访问日期',
  `access_time` datetime DEFAULT NULL COMMENT '访问时间',
  `access_page` varchar(255) NOT NULL DEFAULT '' COMMENT '访问页面',
  `referer_website` varchar(255) DEFAULT NULL COMMENT '来访网站',
  `referer_page` varchar(255) DEFAULT NULL COMMENT '来访页面',
  `referer_keyword` varchar(255) DEFAULT NULL COMMENT '来访关键字',
  `area` varchar(50) DEFAULT NULL COMMENT '地区',
  `session_id` varchar(50) NOT NULL DEFAULT '' COMMENT 'cookie信息',
  PRIMARY KEY (`flow_id`),
  KEY `fk_jc_flow_site` (`site_id`),
  CONSTRAINT `fk_jc_flow_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2119 DEFAULT CHARSET=utf8 COMMENT='站点流量统计表';

-- ----------------------------
-- Records of jc_site_flow
-- ----------------------------
INSERT INTO `jc_site_flow` VALUES ('1669', '1', '127.0.0.1', '2014-3-9', '2014-03-09 10:00:21', 'http：//localhost：8080/m/547.htm', 'http：//localhost：8080', 'http：//localhost：8080/jeeadmin/jeecms/content/v_view.do?id=547&cid=&pageNo=&queryTitle=&queryInputUsername=&queryTopLevel=false&queryRecommend=false&queryTypeId=&queryStatus=&queryOrderBy=0', '', '本机地址', 'FB00D4DCA88038221EBC51D96D287761');
INSERT INTO `jc_site_flow` VALUES ('1670', '1', '127.0.0.1', '2014-3-9', '2014-03-09 10:01:49', 'http：//localhost：8080/news/index.htm', '', '', '', '本机地址', 'FB00D4DCA88038221EBC51D96D287761');
INSERT INTO `jc_site_flow` VALUES ('1671', '1', '0:0:0:0:0:0:0:1', '2014-3-9', '2014-03-09 20:44:49', 'http：//localhost：8080/picture/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/news/index.htm', '', '中国', '1cnm064x8h52qfi88gqsonutv');
INSERT INTO `jc_site_flow` VALUES ('1672', '1', '0:0:0:0:0:0:0:1', '2014-3-9', '2014-03-09 20:43:43', 'http：//localhost：8080/news/index.htm', '', '', '', '中国', 'E961C8950D5B33DE735F00BE75C986D2');
INSERT INTO `jc_site_flow` VALUES ('1673', '1', '0:0:0:0:0:0:0:1', '2014-3-9', '2014-03-09 22:53:54', 'http：//localhost：8080/', '', '', '', '中国', '17xpsst54du15hfcuaxfpo6fy');
INSERT INTO `jc_site_flow` VALUES ('1674', '1', '0:0:0:0:0:0:0:1', '2014-3-9', '2014-03-09 22:54:22', 'http：//localhost：8080/picture/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/news/index.htm', '', '中国', '17xpsst54du15hfcuaxfpo6fy');
INSERT INTO `jc_site_flow` VALUES ('1675', '1', '0:0:0:0:0:0:0:1', '2014-3-9', '2014-03-09 22:54:07', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '17xpsst54du15hfcuaxfpo6fy');
INSERT INTO `jc_site_flow` VALUES ('1676', '1', '0:0:0:0:0:0:0:1', '2014-3-9', '2014-03-09 22:55:45', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/picture/index.htm', '', '中国', '17xpsst54du15hfcuaxfpo6fy');
INSERT INTO `jc_site_flow` VALUES ('1677', '1', '0:0:0:0:0:0:0:1', '2014-3-9', '2014-03-09 22:57:01', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/picture/index.htm', '', '中国', '17xpsst54du15hfcuaxfpo6fy');
INSERT INTO `jc_site_flow` VALUES ('1678', '1', '0:0:0:0:0:0:0:1', '2014-3-9', '2014-03-09 23:02:30', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '17xpsst54du15hfcuaxfpo6fy');
INSERT INTO `jc_site_flow` VALUES ('1679', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 07:33:51', 'http：//localhost：8080/', '', '', '', '中国', '17xpsst54du15hfcuaxfpo6fy');
INSERT INTO `jc_site_flow` VALUES ('1680', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 07:34:15', 'http：//localhost：8080/', '', '', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1681', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 07:35:54', 'http：//localhost：8080/', '', '', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1682', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 07:49:38', 'http：//localhost：8080/', '', '', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1683', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 07:49:55', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1684', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 07:51:10', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1685', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 07:49:52', 'http：//localhost：8080/', '', '', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1686', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 07:55:05', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1687', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 07:55:01', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1688', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 07:55:03', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1689', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 07:56:01', 'http：//localhost：8080/gnxw/510.htm', 'http：//localhost：8080', 'http：//localhost：8080/jeeadmin/jeecms/content/v_view.do?id=510&cid=11&pageNo=&queryTitle=&queryInputUsername=&queryTopLevel=false&queryRecommend=false&queryTypeId=&queryStatus=&queryOrderBy=0', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1690', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 07:54:46', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1691', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 07:54:44', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1692', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 07:55:08', 'http：//localhost：8080/', '', '', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1693', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 07:56:57', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/gnxw/510.htm', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1694', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 08:14:53', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/gnxw/510.htm', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1695', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 08:17:12', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/gnxw/510.htm', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1696', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 08:20:57', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/gnxw/510.htm', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1697', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 08:21:00', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/gnxw/510.htm', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1698', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 08:22:47', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/gnxw/510.htm', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1699', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 08:23:32', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/gnxw/510.htm', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1700', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 08:22:50', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/gnxw/510.htm', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1701', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 08:25:49', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/gnxw/510.htm', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1702', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 08:29:42', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/gnxw/510.htm', '', '中国', '1oj79hgc8mkx01al9pxsaqckxp');
INSERT INTO `jc_site_flow` VALUES ('1703', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 09:02:50', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1n547fas6ku5b1bm5he9j6y6sr');
INSERT INTO `jc_site_flow` VALUES ('1704', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 09:10:31', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1n547fas6ku5b1bm5he9j6y6sr');
INSERT INTO `jc_site_flow` VALUES ('1705', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 09:17:35', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1n547fas6ku5b1bm5he9j6y6sr');
INSERT INTO `jc_site_flow` VALUES ('1706', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 09:20:26', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/news/index.htm', '', '中国', '1n547fas6ku5b1bm5he9j6y6sr');
INSERT INTO `jc_site_flow` VALUES ('1707', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 09:20:45', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/news/index.htm', '', '中国', '1n547fas6ku5b1bm5he9j6y6sr');
INSERT INTO `jc_site_flow` VALUES ('1708', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 09:21:12', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/news/index.htm', '', '中国', '1n547fas6ku5b1bm5he9j6y6sr');
INSERT INTO `jc_site_flow` VALUES ('1709', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 09:35:48', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1n547fas6ku5b1bm5he9j6y6sr');
INSERT INTO `jc_site_flow` VALUES ('1710', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 11:56:53', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1n547fas6ku5b1bm5he9j6y6sr');
INSERT INTO `jc_site_flow` VALUES ('1711', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 12:00:37', 'http：//localhost：8080/world/512.htm', '', '', '', '中国', '1n547fas6ku5b1bm5he9j6y6sr');
INSERT INTO `jc_site_flow` VALUES ('1712', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 17:30:59', 'http：//localhost：8080/gnxw/548.htm', 'http：//localhost：8080', 'http：//localhost：8080/jeeadmin/jeecms/content/v_list.do?cid=11', '', '中国', '18cv85fzqc0gf73ah88szf6p8');
INSERT INTO `jc_site_flow` VALUES ('1713', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 17:31:21', 'http：//localhost：8080/gnxw/548.htm', 'http：//localhost：8080', 'http：//localhost：8080/jeeadmin/jeecms/content/v_list.do?cid=11', '', '中国', '18cv85fzqc0gf73ah88szf6p8');
INSERT INTO `jc_site_flow` VALUES ('1714', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 17:31:19', 'http：//localhost：8080/gnxw/548.htm', 'http：//localhost：8080', 'http：//localhost：8080/jeeadmin/jeecms/content/v_list.do?cid=11', '', '中国', '18cv85fzqc0gf73ah88szf6p8');
INSERT INTO `jc_site_flow` VALUES ('1715', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 17:33:17', 'http：//localhost：8080/gnxw/548.htm', 'http：//localhost：8080', 'http：//localhost：8080/jeeadmin/jeecms/content/v_list.do?cid=11', '', '中国', '18cv85fzqc0gf73ah88szf6p8');
INSERT INTO `jc_site_flow` VALUES ('1716', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 17:33:23', 'http：//localhost：8080/news/index.htm', '', '', '', '中国', '18cv85fzqc0gf73ah88szf6p8');
INSERT INTO `jc_site_flow` VALUES ('1717', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 17:34:30', 'http：//localhost：8080/news/index.htm', '', '', '', '中国', '18cv85fzqc0gf73ah88szf6p8');
INSERT INTO `jc_site_flow` VALUES ('1718', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 17:33:19', 'http：//localhost：8080/gnxw/548.htm', 'http：//localhost：8080', 'http：//localhost：8080/jeeadmin/jeecms/content/v_list.do?cid=11', '', '中国', '18cv85fzqc0gf73ah88szf6p8');
INSERT INTO `jc_site_flow` VALUES ('1719', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 17:36:46', 'http：//localhost：8080/gnxw/548.htm', 'http：//localhost：8080', 'http：//localhost：8080/jeeadmin/jeecms/content/v_list.do?cid=11', '', '中国', '18cv85fzqc0gf73ah88szf6p8');
INSERT INTO `jc_site_flow` VALUES ('1720', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 17:36:47', 'http：//localhost：8080/gnxw/548.htm', 'http：//localhost：8080', 'http：//localhost：8080/jeeadmin/jeecms/content/v_list.do?cid=11', '', '中国', '18cv85fzqc0gf73ah88szf6p8');
INSERT INTO `jc_site_flow` VALUES ('1721', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 17:36:53', 'http：//localhost：8080/news/index.htm', '', '', '', '中国', '18cv85fzqc0gf73ah88szf6p8');
INSERT INTO `jc_site_flow` VALUES ('1722', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 17:37:41', 'http：//localhost：8080/news/index.htm', '', '', '', '中国', '18cv85fzqc0gf73ah88szf6p8');
INSERT INTO `jc_site_flow` VALUES ('1723', '1', '127.0.0.1', '2014-3-10', '2014-03-10 17:43:39', 'http：//localhost：8080/news/index.htm', '', '', '', '中国', 'jsw23pp46vtgrwlmb9wy7yl6');
INSERT INTO `jc_site_flow` VALUES ('1724', '1', '0:0:0:0:0:0:0:1', '2014-3-10', '2014-03-10 19:38:16', 'http：//localhost：8080/news/index.htm', '', '', '', '中国', '8x66s72e4e0hwaeemt9hp84u');
INSERT INTO `jc_site_flow` VALUES ('1725', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:20:04', 'http：//localhost：8080/wldc/index.jhtml', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', 'd9iaruha1e9d1eampp6b2n4a5');
INSERT INTO `jc_site_flow` VALUES ('1726', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:19:48', 'http：//localhost：8080/wldc/index.jhtml', 'http：//localhost：8080', 'http：//localhost：8080/job/index.jhtml', '', '中国', 'd9iaruha1e9d1eampp6b2n4a5');
INSERT INTO `jc_site_flow` VALUES ('1727', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:20:00', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', 'd9iaruha1e9d1eampp6b2n4a5');
INSERT INTO `jc_site_flow` VALUES ('1728', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:21:37', 'http：//localhost：8080/wldc/index.jhtml', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', 'd9iaruha1e9d1eampp6b2n4a5');
INSERT INTO `jc_site_flow` VALUES ('1729', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:25:28', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1730', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:26:36', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', 'd9iaruha1e9d1eampp6b2n4a5');
INSERT INTO `jc_site_flow` VALUES ('1731', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:27:24', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1732', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:27:26', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1733', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:27:55', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1734', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:28:47', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1735', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:27:59', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1736', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:27:57', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1737', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:28:53', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1738', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:29:16', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1739', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:29:45', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', 'd9iaruha1e9d1eampp6b2n4a5');
INSERT INTO `jc_site_flow` VALUES ('1740', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:28:53', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', 'd9iaruha1e9d1eampp6b2n4a5');
INSERT INTO `jc_site_flow` VALUES ('1741', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:28:52', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', 'd9iaruha1e9d1eampp6b2n4a5');
INSERT INTO `jc_site_flow` VALUES ('1742', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:30:00', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', 'd9iaruha1e9d1eampp6b2n4a5');
INSERT INTO `jc_site_flow` VALUES ('1743', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:29:48', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', 'd9iaruha1e9d1eampp6b2n4a5');
INSERT INTO `jc_site_flow` VALUES ('1744', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:30:08', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1745', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:31:05', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1746', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:32:37', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1747', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:34:22', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', 'd9iaruha1e9d1eampp6b2n4a5');
INSERT INTO `jc_site_flow` VALUES ('1748', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:37:41', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1749', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:37:56', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1750', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:39:07', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1751', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:37:44', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1752', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:38:01', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1753', '1', '0:0:0:0:0:0:0:1', '2014-3-12', '2014-03-12 08:37:58', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1754', '1', '0:0:0:0:0:0:0:1', '2014-3-14', '2014-03-14 10:47:15', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1755', '1', '0:0:0:0:0:0:0:1', '2014-3-14', '2014-03-14 10:48:50', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', '19u4gx4pmcoxmp2btrh7tym20');
INSERT INTO `jc_site_flow` VALUES ('1756', '1', '0:0:0:0:0:0:0:1', '2014-3-14', '2014-03-14 11:38:04', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.jhtml', '', '中国', 'qvl2mibjjoa11x2vefla4udtm');
INSERT INTO `jc_site_flow` VALUES ('1757', '1', '0:0:0:0:0:0:0:1', '2014-3-20', '2014-03-20 21:02:50', 'http：//localhost：8080/dianshiju/504.htm', 'http：//localhost：8080', 'http：//localhost：8080/veido/index.htm', '', '中国', '1xcv1dpqnjs2sf2qiwcbh48sk');
INSERT INTO `jc_site_flow` VALUES ('1758', '1', '0:0:0:0:0:0:0:1', '2014-3-20', '2014-03-20 21:02:06', 'http：//localhost：8080/m/545.htm', 'http：//localhost：8080', 'http：//localhost：8080/veido/index.htm', '', '中国', '1xcv1dpqnjs2sf2qiwcbh48sk');
INSERT INTO `jc_site_flow` VALUES ('1759', '1', '0:0:0:0:0:0:0:1', '2014-3-20', '2014-03-20 21:02:01', 'http：//localhost：8080/m/545.htm', 'http：//localhost：8080', 'http：//localhost：8080/veido/index.htm', '', '中国', '1xcv1dpqnjs2sf2qiwcbh48sk');
INSERT INTO `jc_site_flow` VALUES ('1760', '1', '0:0:0:0:0:0:0:1', '2014-3-20', '2014-03-20 21:07:39', 'http：//localhost：8080/dianshiju/504.htm', 'http：//localhost：8080', 'http：//localhost：8080/veido/index.htm', '', '中国', '1xcv1dpqnjs2sf2qiwcbh48sk');
INSERT INTO `jc_site_flow` VALUES ('1761', '1', '0:0:0:0:0:0:0:1', '2014-3-20', '2014-03-20 21:08:55', 'http：//localhost：8080/dianshiju/504.htm', 'http：//localhost：8080', 'http：//localhost：8080/veido/index.htm', '', '中国', '1xcv1dpqnjs2sf2qiwcbh48sk');
INSERT INTO `jc_site_flow` VALUES ('1762', '1', '0:0:0:0:0:0:0:1', '2014-3-20', '2014-03-20 21:07:40', 'http：//localhost：8080/dianshiju/504.htm', 'http：//localhost：8080', 'http：//localhost：8080/veido/index.htm', '', '中国', '1xcv1dpqnjs2sf2qiwcbh48sk');
INSERT INTO `jc_site_flow` VALUES ('1763', '1', '0:0:0:0:0:0:0:1', '2014-3-20', '2014-03-20 21:07:45', 'http：//localhost：8080/dianshiju/504.htm', 'http：//localhost：8080', 'http：//localhost：8080/veido/index.htm', '', '中国', '1xcv1dpqnjs2sf2qiwcbh48sk');
INSERT INTO `jc_site_flow` VALUES ('1764', '1', '0:0:0:0:0:0:0:1', '2014-3-20', '2014-03-20 21:13:46', 'http：//localhost：8080/dianshiju/504.htm', 'http：//localhost：8080', 'http：//localhost：8080/veido/index.htm', '', '中国', '1xcv1dpqnjs2sf2qiwcbh48sk');
INSERT INTO `jc_site_flow` VALUES ('1765', '1', '0:0:0:0:0:0:0:1', '2014-3-20', '2014-03-20 21:13:51', 'http：//localhost：8080/dianshiju/504.htm', 'http：//localhost：8080', 'http：//localhost：8080/veido/index.htm', '', '中国', '1xcv1dpqnjs2sf2qiwcbh48sk');
INSERT INTO `jc_site_flow` VALUES ('1766', '1', '0:0:0:0:0:0:0:1', '2014-3-20', '2014-03-20 21:13:54', 'http：//localhost：8080/dianshiju/504.htm', 'http：//localhost：8080', 'http：//localhost：8080/veido/index.htm', '', '中国', '1xcv1dpqnjs2sf2qiwcbh48sk');
INSERT INTO `jc_site_flow` VALUES ('1767', '1', '0:0:0:0:0:0:0:1', '2014-3-20', '2014-03-20 21:13:57', 'http：//localhost：8080/dianshiju/473.htm', 'http：//localhost：8080', 'http：//localhost：8080/veido/index.htm', '', '中国', '1xcv1dpqnjs2sf2qiwcbh48sk');
INSERT INTO `jc_site_flow` VALUES ('1768', '1', '0:0:0:0:0:0:0:1', '2014-3-20', '2014-03-20 21:14:24', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bqhqemtockv6p5uofgxa8qog');
INSERT INTO `jc_site_flow` VALUES ('1769', '1', '0:0:0:0:0:0:0:1', '2014-3-22', '2014-03-22 17:25:12', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', '195skwekuebbe1ry65mje3nx50');
INSERT INTO `jc_site_flow` VALUES ('1770', '1', '0:0:0:0:0:0:0:1', '2014-3-22', '2014-03-22 17:26:58', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', '195skwekuebbe1ry65mje3nx50');
INSERT INTO `jc_site_flow` VALUES ('1771', '1', '0:0:0:0:0:0:0:1', '2014-3-22', '2014-03-22 17:27:55', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/dianshiju/473.htm', '', '中国', '195skwekuebbe1ry65mje3nx50');
INSERT INTO `jc_site_flow` VALUES ('1772', '1', '0:0:0:0:0:0:0:1', '2014-3-22', '2014-03-22 17:48:32', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/dianshiju/473.htm', '', '中国', '195skwekuebbe1ry65mje3nx50');
INSERT INTO `jc_site_flow` VALUES ('1773', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:08:28', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1774', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:09:11', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1775', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:09:21', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1776', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:09:53', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1777', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:11:00', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1778', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:11:31', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1779', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:11:44', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', '1fvx11uw8ac5f53ecfrehwruf');
INSERT INTO `jc_site_flow` VALUES ('1780', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:13:02', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1781', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:14:08', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1782', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:13:31', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1783', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:20:11', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1784', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:22:49', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1785', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:26:43', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1786', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:27:39', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1787', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:28:27', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1788', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:33:13', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1789', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:39:01', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1790', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:40:34', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1791', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:42:45', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1792', '1', '0:0:0:0:0:0:0:1', '2014-3-27', '2014-03-27 20:43:48', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'bg9xkunfu6habjgoyxu6u2n0');
INSERT INTO `jc_site_flow` VALUES ('1793', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:13:41', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1794', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:14:57', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1795', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:16:46', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1796', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:17:18', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1797', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:19:36', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1798', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:20:35', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1799', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:21:15', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1800', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:24:24', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1801', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:24:37', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1802', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:25:08', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1803', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:25:44', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1804', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:25:25', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1805', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:28:10', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1806', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:29:05', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1807', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:30:01', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1808', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:30:21', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1809', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:30:36', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1810', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:30:14', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1811', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:31:25', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1812', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:31:05', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1813', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:32:00', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1814', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:31:39', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1815', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:33:34', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1816', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 09:58:48', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1817', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 10:27:26', 'http：//localhost：8080/dianshiju/473.htm', '', '', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1818', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 11:24:11', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/dianshiju/473.htm', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1819', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 11:30:10', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1820', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 11:30:12', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1821', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 11:30:43', 'http：//localhost：8080/download/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/veido/index.htm', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1822', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 11:30:39', 'http：//localhost：8080/veido/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/picture/index.htm', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1823', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 11:30:31', 'http：//localhost：8080/picture/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/news/index.htm', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1824', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 11:30:45', 'http：//localhost：8080/wldc/index.jhtml', 'http：//localhost：8080', 'http：//localhost：8080/download/index.htm', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1825', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 11:30:49', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/download/index.htm', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1826', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 11:32:02', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/download/index.htm', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1827', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 11:32:46', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/download/index.htm', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1828', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 11:34:37', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/download/index.htm', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1829', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 11:34:57', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/download/index.htm', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1830', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 11:35:46', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/download/index.htm', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1831', '1', '0:0:0:0:0:0:0:1', '2014-3-30', '2014-03-30 11:38:24', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/download/index.htm', '', '中国', 'u47f0c9mca67kgbt4btnpio4');
INSERT INTO `jc_site_flow` VALUES ('1832', '1', '0:0:0:0:0:0:0:1', '2014-4-15', '2014-04-15 21:52:05', 'http：//localhost：8080/', '', '', '', '中国', '15z8ykd7l4ujzjzpzcwmgmjen');
INSERT INTO `jc_site_flow` VALUES ('1833', '1', '0:0:0:0:0:0:0:1', '2014-4-15', '2014-04-15 21:54:42', 'http：//localhost：8080/', '', '', '', '中国', '15z8ykd7l4ujzjzpzcwmgmjen');
INSERT INTO `jc_site_flow` VALUES ('1834', '1', '0:0:0:0:0:0:0:1', '2014-4-15', '2014-04-15 21:56:25', 'http：//localhost：8080/', '', '', '', '中国', '15z8ykd7l4ujzjzpzcwmgmjen');
INSERT INTO `jc_site_flow` VALUES ('1835', '1', '0:0:0:0:0:0:0:1', '2014-4-15', '2014-04-15 21:56:26', 'http：//localhost：8080/', '', '', '', '中国', '15z8ykd7l4ujzjzpzcwmgmjen');
INSERT INTO `jc_site_flow` VALUES ('1836', '1', '0:0:0:0:0:0:0:1', '2014-4-15', '2014-04-15 21:57:44', 'http：//localhost：8080/', '', '', '', '中国', '15z8ykd7l4ujzjzpzcwmgmjen');
INSERT INTO `jc_site_flow` VALUES ('1837', '1', '0:0:0:0:0:0:0:1', '2014-4-15', '2014-04-15 22:00:09', 'http：//localhost：8080/', '', '', '', '中国', '15z8ykd7l4ujzjzpzcwmgmjen');
INSERT INTO `jc_site_flow` VALUES ('1838', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:26:53', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1839', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:27:22', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1840', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:27:47', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1841', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:28:38', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1842', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:33:06', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1843', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:36:00', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1844', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:36:40', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1845', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:38:16', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1846', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:36:41', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1847', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:39:51', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1848', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:38:35', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1849', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:38:17', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1850', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:40:24', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1851', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:42:01', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1852', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:42:45', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1853', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:42:03', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1854', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:44:31', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1855', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:42:47', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1856', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:45:49', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1857', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:44:32', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1858', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:44:53', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1859', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:44:52', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1860', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:44:54', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1861', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:45:50', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1862', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:46:40', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1863', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:47:18', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1864', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:47:08', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1865', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:47:00', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1866', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:47:07', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1867', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:49:11', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1868', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:47:41', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1869', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:47:42', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1870', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:53:46', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1871', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:54:42', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1872', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:54:11', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1873', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:55:49', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1874', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:55:52', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1875', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:56:46', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1876', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:58:45', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1877', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 07:59:21', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1878', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:00:01', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1879', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:00:43', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1880', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:00:44', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1881', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:01:00', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1882', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:01:21', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1883', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:02:11', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1884', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:02:41', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1885', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:02:12', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1886', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:02:42', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1887', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:03:46', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1888', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:06:50', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1889', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:10:00', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1890', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:10:23', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1891', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:10:22', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1892', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:11:31', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1893', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:11:32', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1894', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:13:28', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1895', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:14:02', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1896', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:14:17', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1897', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:14:18', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1898', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:14:36', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1899', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:17:41', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1900', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:17:40', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1901', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:18:15', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1902', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:19:05', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1903', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:18:16', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1904', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:20:03', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1905', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:20:05', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1906', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:21:08', 'http：//localhost：8080/', '', '', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1907', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:22:54', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1908', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:21:13', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1909', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:21:11', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1910', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:24:33', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1911', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:24:36', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1912', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:25:33', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1913', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:24:37', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1914', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:24:34', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1915', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:27:14', 'http：//localhost：8080/world/512.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1916', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:30:02', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '9keugcj18wr3xf565gfbpt7e');
INSERT INTO `jc_site_flow` VALUES ('1917', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 08:56:33', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1918', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 09:03:54', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1919', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:14:59', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1920', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:13:12', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1921', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:19:45', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1922', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:20:45', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1923', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:27:33', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1924', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:32:19', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1925', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:33:00', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1926', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:32:38', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1927', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:34:37', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1928', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:33:21', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1929', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:35:31', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1930', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:35:33', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1931', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:35:43', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1932', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:37:13', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1933', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:38:39', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1934', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:38:43', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1935', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:39:26', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1936', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:39:59', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1937', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:39:28', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1938', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:39:27', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1939', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:40:29', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1940', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:40:44', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1941', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:41:10', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1942', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:44:01', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1943', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:45:51', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1944', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:49:45', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1945', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:49:14', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1946', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:49:43', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1947', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:49:16', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1948', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:53:11', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1949', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 22:59:38', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1950', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:03:34', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1951', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:05:46', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1952', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:06:20', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1953', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:05:48', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1954', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:06:05', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1955', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:07:20', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1956', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:08:45', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1957', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:07:23', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1958', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:17:41', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1959', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:18:13', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1960', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:17:43', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1961', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:18:43', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1962', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:18:19', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1963', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:19:45', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1964', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:20:36', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1965', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:22:09', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1966', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:20:38', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1967', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:22:52', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1968', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:22:10', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1969', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:24:22', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1970', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:25:10', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1971', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:26:08', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1972', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:26:46', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1973', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:28:21', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1974', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:28:27', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1975', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:30:19', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1976', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:30:53', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1977', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:31:55', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1978', '1', '0:0:0:0:0:0:0:1', '2014-4-16', '2014-04-16 23:39:29', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1979', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 07:54:34', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1980', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 07:55:08', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1981', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 07:56:10', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1982', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 07:57:29', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1983', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 07:56:36', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1984', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 07:58:49', 'http：//localhost：8080/gnxw/510.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1985', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:00:57', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1986', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:02:33', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1987', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:03:31', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1988', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:03:41', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1989', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:04:35', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1990', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:03:54', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1991', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:04:52', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1992', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:05:09', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1993', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:10:23', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1994', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:11:14', 'http：//localhost：8080/', '', '', '', '中国', '195a9hunhn6thkuo0dd4y4pmv');
INSERT INTO `jc_site_flow` VALUES ('1995', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:19:21', 'http：//localhost：8080/', '', '', '', '中国', 'b1a624v2hm541pnm8iv75k9ew');
INSERT INTO `jc_site_flow` VALUES ('1996', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:43:23', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('1997', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:43:35', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('1998', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:43:43', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('1999', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:45:06', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('2000', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:45:07', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('2001', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:45:25', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('2002', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:45:42', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('2003', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:46:58', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('2004', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:47:09', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('2005', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:47:31', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('2006', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:48:45', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('2007', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:50:07', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('2008', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:51:48', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('2009', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:50:11', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('2010', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:56:17', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('2011', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:55:56', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('2012', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:56:19', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('2013', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 08:57:16', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('2014', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 09:01:51', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('2015', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 09:09:28', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('2016', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 09:09:53', 'http：//localhost：8080/job/index.jhtml', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', 'oftc18ur2fa1lmh0h46yyej0');
INSERT INTO `jc_site_flow` VALUES ('2017', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 09:09:55', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', 'oftc18ur2fa1lmh0h46yyej0');
INSERT INTO `jc_site_flow` VALUES ('2018', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 09:09:29', 'http：//localhost：8080/', '', '', '', '中国', '6eqxdt87a2fi1rsgrgzeastbg');
INSERT INTO `jc_site_flow` VALUES ('2019', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 09:09:47', 'http：//localhost：8080/wldc/index.jhtml', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', 'oftc18ur2fa1lmh0h46yyej0');
INSERT INTO `jc_site_flow` VALUES ('2020', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 09:09:57', 'http：//localhost：8080/wldc/index.jhtml', 'http：//localhost：8080', 'http：//localhost：8080/news/index.htm', '', '中国', 'oftc18ur2fa1lmh0h46yyej0');
INSERT INTO `jc_site_flow` VALUES ('2021', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 09:10:01', 'http：//localhost：8080/download/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/news/index.htm', '', '中国', 'oftc18ur2fa1lmh0h46yyej0');
INSERT INTO `jc_site_flow` VALUES ('2022', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 09:09:44', 'http：//localhost：8080/job/index.jhtml', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', 'oftc18ur2fa1lmh0h46yyej0');
INSERT INTO `jc_site_flow` VALUES ('2023', '1', '0:0:0:0:0:0:0:1', '2014-4-17', '2014-04-17 09:27:56', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/news/index.htm', '', '中国', 'oftc18ur2fa1lmh0h46yyej0');
INSERT INTO `jc_site_flow` VALUES ('2024', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 07:47:02', 'http：//localhost：8080/', '', '', '', '中国', 'nhrhg7bbx6tq120oeo2a8jjcv');
INSERT INTO `jc_site_flow` VALUES ('2025', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:37:27', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/news/index.htm', '', '中国', '11eanujnjx4bristfc11mzz0l');
INSERT INTO `jc_site_flow` VALUES ('2026', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:38:16', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/news/index.htm', '', '中国', '11eanujnjx4bristfc11mzz0l');
INSERT INTO `jc_site_flow` VALUES ('2027', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:50:20', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2028', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:52:30', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2029', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:52:35', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2030', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:52:48', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2031', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:52:37', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2032', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:52:47', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2033', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:53:05', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2034', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:53:32', 'http：//localhost：8080/gnxw/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/news/index.htm', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2035', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:53:43', 'http：//localhost：8080/wldc/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/news/index.htm', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2036', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:53:27', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/picture/index.htm', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2037', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:53:21', 'http：//localhost：8080/picture/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/veido/index.htm', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2038', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:53:19', 'http：//localhost：8080/veido/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.htm', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2039', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:53:17', 'http：//localhost：8080/wldc/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2040', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:55:48', 'http：//localhost：8080/wldc/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.htm', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2041', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:55:51', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.htm', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2042', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:55:52', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2043', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 12:56:20', 'http：//localhost：8080/shehui/428.htm', 'http：//localhost：8080', 'http：//localhost：8080/news/index.htm', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2044', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 13:09:26', 'http：//localhost：8080/shehui/427.htm', 'http：//localhost：8080', 'http：//localhost：8080/shehui/428.htm', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2045', '1', '0:0:0:0:0:0:0:1', '2014-4-26', '2014-04-26 13:25:49', 'http：//localhost：8080/', '', '', '', '中国', '15xsm6ylq9icg5px02sotppa4');
INSERT INTO `jc_site_flow` VALUES ('2046', '1', '0:0:0:0:0:0:0:1', '2014-4-30', '2014-04-30 08:44:26', 'http：//localhost：8080/', '', '', '', '中国', 'jvqvvjwd3pai1rbwlc7snekpl');
INSERT INTO `jc_site_flow` VALUES ('2047', '1', '0:0:0:0:0:0:0:1', '2014-4-30', '2014-04-30 08:45:41', 'http：//localhost：8080/', '', '', '', '中国', 'jvqvvjwd3pai1rbwlc7snekpl');
INSERT INTO `jc_site_flow` VALUES ('2048', '1', '0:0:0:0:0:0:0:1', '2014-4-30', '2014-04-30 08:47:31', 'http：//localhost：8080/', '', '', '', '中国', 'jvqvvjwd3pai1rbwlc7snekpl');
INSERT INTO `jc_site_flow` VALUES ('2049', '1', '0:0:0:0:0:0:0:1', '2014-4-30', '2014-04-30 08:48:38', 'http：//localhost：8080/', '', '', '', '中国', 'jvqvvjwd3pai1rbwlc7snekpl');
INSERT INTO `jc_site_flow` VALUES ('2050', '1', '0:0:0:0:0:0:0:1', '2014-4-30', '2014-04-30 08:47:52', 'http：//localhost：8080/', '', '', '', '中国', 'jvqvvjwd3pai1rbwlc7snekpl');
INSERT INTO `jc_site_flow` VALUES ('2051', '1', '0:0:0:0:0:0:0:1', '2014-4-30', '2014-04-30 08:48:51', 'http：//localhost：8080/', '', '', '', '中国', 'jvqvvjwd3pai1rbwlc7snekpl');
INSERT INTO `jc_site_flow` VALUES ('2052', '1', '0:0:0:0:0:0:0:1', '2014-4-30', '2014-04-30 08:49:33', 'http：//localhost：8080/wldc/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', 'jvqvvjwd3pai1rbwlc7snekpl');
INSERT INTO `jc_site_flow` VALUES ('2053', '1', '0:0:0:0:0:0:0:1', '2014-4-30', '2014-04-30 08:50:24', 'http：//localhost：8080/veido/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/picture/index.htm', '', '中国', 'jvqvvjwd3pai1rbwlc7snekpl');
INSERT INTO `jc_site_flow` VALUES ('2054', '1', '0:0:0:0:0:0:0:1', '2014-4-30', '2014-04-30 08:49:44', 'http：//localhost：8080/picture/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/veido/index.htm', '', '中国', 'jvqvvjwd3pai1rbwlc7snekpl');
INSERT INTO `jc_site_flow` VALUES ('2055', '1', '0:0:0:0:0:0:0:1', '2014-4-30', '2014-04-30 08:49:37', 'http：//localhost：8080/veido/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/wldc/index.htm', '', '中国', 'jvqvvjwd3pai1rbwlc7snekpl');
INSERT INTO `jc_site_flow` VALUES ('2056', '1', '127.0.0.1', '2014-5-1', '2014-05-01 09:35:54', 'http：//localhost：8080/news/', '', '', '', '中国', 'dbcd5uqj7rvu1xjkxjdn5uhmf');
INSERT INTO `jc_site_flow` VALUES ('2057', '1', '127.0.0.1', '2014-5-11', '2014-05-11 10:13:51', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/admin/s/top.do', '', '中国', '1unp7urv1e70x10ir5w8m2ea3b');
INSERT INTO `jc_site_flow` VALUES ('2058', '1', '127.0.0.1', '2014-5-11', '2014-05-11 10:14:20', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/admin/s/top.do', '', '中国', '1unp7urv1e70x10ir5w8m2ea3b');
INSERT INTO `jc_site_flow` VALUES ('2059', '1', '127.0.0.1', '2014-5-11', '2014-05-11 10:15:24', 'http：//localhost：8080/world/512.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1unp7urv1e70x10ir5w8m2ea3b');
INSERT INTO `jc_site_flow` VALUES ('2060', '1', '127.0.0.1', '2014-5-11', '2014-05-11 10:22:57', 'http：//localhost：8080/m/546.htm', 'http：//localhost：8080', 'http：//localhost：8080/veido/index.htm', '', '中国', '1unp7urv1e70x10ir5w8m2ea3b');
INSERT INTO `jc_site_flow` VALUES ('2061', '1', '127.0.0.1', '2014-5-11', '2014-05-11 10:23:14', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1unp7urv1e70x10ir5w8m2ea3b');
INSERT INTO `jc_site_flow` VALUES ('2062', '1', '127.0.0.1', '2014-5-11', '2014-05-11 10:22:54', 'http：//localhost：8080/veido/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/photo/index.htm', '', '中国', '1unp7urv1e70x10ir5w8m2ea3b');
INSERT INTO `jc_site_flow` VALUES ('2063', '1', '127.0.0.1', '2014-5-11', '2014-05-11 10:30:23', 'http：//localhost：8080/gnxw/509.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1unp7urv1e70x10ir5w8m2ea3b');
INSERT INTO `jc_site_flow` VALUES ('2064', '1', '127.0.0.1', '2014-5-11', '2014-05-11 10:30:55', 'http：//localhost：8080/review/445.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1unp7urv1e70x10ir5w8m2ea3b');
INSERT INTO `jc_site_flow` VALUES ('2065', '1', '127.0.0.1', '2014-5-11', '2014-05-11 11:06:06', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/admin/s/top.do', '', '中国', '165qt9dvwzp16gb3pj3th2b0n');
INSERT INTO `jc_site_flow` VALUES ('2066', '1', '127.0.0.1', '2014-5-11', '2014-05-11 11:06:24', 'http：//localhost：8080/gnxw/548.htm', 'http：//localhost：8080', 'http：//localhost：8080/admin/s/content/v_list.do', '', '中国', '1xkgpij0nfl8a1n44sha0lj2iq');
INSERT INTO `jc_site_flow` VALUES ('2067', '1', '127.0.0.1', '2014-5-11', '2014-05-11 11:07:52', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/admin/s/top.do', '', '中国', '1xkgpij0nfl8a1n44sha0lj2iq');
INSERT INTO `jc_site_flow` VALUES ('2068', '1', '127.0.0.1', '2014-5-11', '2014-05-11 11:26:52', 'http：//localhost：8080/gnxw/550.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1xkgpij0nfl8a1n44sha0lj2iq');
INSERT INTO `jc_site_flow` VALUES ('2069', '1', '127.0.0.1', '2014-5-31', '2014-05-31 06:51:57', 'http：//127.0.0.1：8080/picture/index.htm', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/veido/index.htm', '', '中国', '1v70rmbt507aqsnsl2b0ax40');
INSERT INTO `jc_site_flow` VALUES ('2070', '1', '127.0.0.1', '2014-6-1', '2014-06-01 22:12:49', 'http：//127.0.0.1：8080/picture/index.htm', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/veido/index.htm', '', '中国', '1ml0z98pt80s02ckvp4zv89do');
INSERT INTO `jc_site_flow` VALUES ('2071', '1', '127.0.0.1', '2014-6-1', '2014-06-01 22:14:23', 'http：//127.0.0.1：8080/picture/index.htm', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/veido/index.htm', '', '中国', '1ml0z98pt80s02ckvp4zv89do');
INSERT INTO `jc_site_flow` VALUES ('2072', '1', '127.0.0.1', '2014-6-1', '2014-06-01 22:12:50', 'http：//127.0.0.1：8080/picture/index.htm', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/veido/index.htm', '', '中国', '1ml0z98pt80s02ckvp4zv89do');
INSERT INTO `jc_site_flow` VALUES ('2073', '1', '127.0.0.1', '2014-6-1', '2014-06-01 22:14:25', 'http：//127.0.0.1：8080/', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/picture/index.htm', '', '中国', '1ml0z98pt80s02ckvp4zv89do');
INSERT INTO `jc_site_flow` VALUES ('2074', '1', '127.0.0.1', '2014-6-1', '2014-06-01 22:15:31', 'http：//127.0.0.1：8080/', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/picture/index.htm', '', '中国', '1ml0z98pt80s02ckvp4zv89do');
INSERT INTO `jc_site_flow` VALUES ('2075', '1', '127.0.0.1', '2014-6-1', '2014-06-01 22:18:06', 'http：//127.0.0.1：8080/news/index.htm', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/', '', '中国', '1ml0z98pt80s02ckvp4zv89do');
INSERT INTO `jc_site_flow` VALUES ('2076', '1', '127.0.0.1', '2014-6-1', '2014-06-01 22:17:37', 'http：//127.0.0.1：8080/news/index.htm', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/', '', '中国', '1ml0z98pt80s02ckvp4zv89do');
INSERT INTO `jc_site_flow` VALUES ('2077', '1', '127.0.0.1', '2014-6-1', '2014-06-01 22:17:38', 'http：//127.0.0.1：8080/news/index.htm', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/', '', '中国', '1ml0z98pt80s02ckvp4zv89do');
INSERT INTO `jc_site_flow` VALUES ('2078', '1', '127.0.0.1', '2014-6-1', '2014-06-01 22:18:29', 'http：//127.0.0.1：8080/prod/index.htm', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/news/index.htm', '', '中国', '1ml0z98pt80s02ckvp4zv89do');
INSERT INTO `jc_site_flow` VALUES ('2079', '1', '127.0.0.1', '2014-6-1', '2014-06-01 22:18:34', 'http：//127.0.0.1：8080/about.htm', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/news/index.htm', '', '中国', '1ml0z98pt80s02ckvp4zv89do');
INSERT INTO `jc_site_flow` VALUES ('2080', '1', '127.0.0.1', '2014-6-1', '2014-06-01 22:18:40', 'http：//127.0.0.1：8080/wldc/index.htm', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/news/index.htm', '', '中国', '1ml0z98pt80s02ckvp4zv89do');
INSERT INTO `jc_site_flow` VALUES ('2081', '1', '127.0.0.1', '2014-6-7', '2014-06-07 20:13:36', 'http：//localhost：8080/', '', '', '', '中国', '1c1b28v82xqmx188tpi1ivolc');
INSERT INTO `jc_site_flow` VALUES ('2082', '1', '127.0.0.1', '2014-6-7', '2014-06-07 20:21:51', 'http：//localhost：8080/', '', '', '', '中国', '1c1b28v82xqmx188tpi1ivolc');
INSERT INTO `jc_site_flow` VALUES ('2083', '1', '127.0.0.1', '2014-6-7', '2014-06-07 20:25:47', 'http：//localhost：8080/interview/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1c1b28v82xqmx188tpi1ivolc');
INSERT INTO `jc_site_flow` VALUES ('2084', '1', '127.0.0.1', '2014-6-7', '2014-06-07 20:26:19', 'http：//localhost：8080/live/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/interview/index.htm', '', '中国', '1c1b28v82xqmx188tpi1ivolc');
INSERT INTO `jc_site_flow` VALUES ('2085', '1', '127.0.0.1', '2014-6-7', '2014-06-07 20:38:59', 'http：//localhost：8080/news/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/veido/index.htm', '', '中国', '1t92izbi9fr06lf8t6q16ggfa');
INSERT INTO `jc_site_flow` VALUES ('2086', '1', '127.0.0.1', '2014-6-7', '2014-06-07 20:38:57', 'http：//localhost：8080/veido/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/picture/index.htm', '', '中国', '1t92izbi9fr06lf8t6q16ggfa');
INSERT INTO `jc_site_flow` VALUES ('2087', '1', '127.0.0.1', '2014-6-7', '2014-06-07 20:38:56', 'http：//localhost：8080/picture/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/live/index.htm', '', '中国', '1t92izbi9fr06lf8t6q16ggfa');
INSERT INTO `jc_site_flow` VALUES ('2088', '1', '127.0.0.1', '2014-6-7', '2014-06-07 20:39:04', 'http：//localhost：8080/', 'http：//localhost：8080', 'http：//localhost：8080/news/index.htm', '', '中国', '1t92izbi9fr06lf8t6q16ggfa');
INSERT INTO `jc_site_flow` VALUES ('2089', '1', '127.0.0.1', '2014-6-7', '2014-06-07 20:38:54', 'http：//localhost：8080/live/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/interview/index.htm', '', '中国', '1t92izbi9fr06lf8t6q16ggfa');
INSERT INTO `jc_site_flow` VALUES ('2090', '1', '127.0.0.1', '2014-6-8', '2014-06-08 06:18:51', 'http：//localhost：8080/wldc/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1vgjds0aekw7yemlhazpwabxu');
INSERT INTO `jc_site_flow` VALUES ('2091', '1', '127.0.0.1', '2014-6-8', '2014-06-08 07:05:39', 'http：//localhost：8080/interview/551.htm', 'http：//localhost：8080', 'http：//localhost：8080/interview/index.htm', '', '中国', '1vgjds0aekw7yemlhazpwabxu');
INSERT INTO `jc_site_flow` VALUES ('2092', '1', '127.0.0.1', '2014-6-8', '2014-06-08 07:04:21', 'http：//localhost：8080/interview/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '1vgjds0aekw7yemlhazpwabxu');
INSERT INTO `jc_site_flow` VALUES ('2093', '1', '127.0.0.1', '2014-6-8', '2014-06-08 07:12:56', 'http：//localhost：8080/live/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/interview/551.htm', '', '中国', '1vgjds0aekw7yemlhazpwabxu');
INSERT INTO `jc_site_flow` VALUES ('2094', '1', '127.0.0.1', '2014-6-8', '2014-06-08 07:13:28', 'http：//localhost：8080/live/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/interview/index.htm', '', '中国', '1vgjds0aekw7yemlhazpwabxu');
INSERT INTO `jc_site_flow` VALUES ('2095', '1', '127.0.0.1', '2014-6-8', '2014-06-08 07:14:32', 'http：//localhost：8080/interview/551.htm', 'http：//localhost：8080', 'http：//localhost：8080/admin/s/content/v_view.do?id=551&cid=&pageNo=&queryTitle=&queryInputUsername=&queryTopLevel=false&queryRecommend=false&queryTypeId=&queryStatus=&queryOrderBy=0', '', '中国', '1vgjds0aekw7yemlhazpwabxu');
INSERT INTO `jc_site_flow` VALUES ('2096', '1', '127.0.0.1', '2014-6-8', '2014-06-08 07:13:31', 'http：//localhost：8080/interview/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/live/index.htm', '', '中国', '1vgjds0aekw7yemlhazpwabxu');
INSERT INTO `jc_site_flow` VALUES ('2097', '1', '127.0.0.1', '2014-6-9', '2014-06-09 07:16:09', 'http：//127.0.0.1：8080/interview/551.htm', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/interview/index.htm', '', '中国', '1kge5hycv7k0x165bbf8j3tsb');
INSERT INTO `jc_site_flow` VALUES ('2098', '1', '127.0.0.1', '2014-6-9', '2014-06-09 07:16:56', 'http：//127.0.0.1：8080/interview/551.htm', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/interview/index.htm', '', '中国', '1kge5hycv7k0x165bbf8j3tsb');
INSERT INTO `jc_site_flow` VALUES ('2099', '1', '127.0.0.1', '2014-6-9', '2014-06-09 07:16:15', 'http：//127.0.0.1：8080/interview/551.htm', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/interview/index.htm', '', '中国', '1kge5hycv7k0x165bbf8j3tsb');
INSERT INTO `jc_site_flow` VALUES ('2100', '1', '127.0.0.1', '2014-6-9', '2014-06-09 07:17:51', 'http：//127.0.0.1：8080/interview/551.htm', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/interview/index.htm', '', '中国', '1kge5hycv7k0x165bbf8j3tsb');
INSERT INTO `jc_site_flow` VALUES ('2101', '1', '127.0.0.1', '2014-6-9', '2014-06-09 07:38:39', 'http：//127.0.0.1：8080/gnxw/506.htm', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/', '', '中国', '1lm277f8qptb21aa1b39cz90rd');
INSERT INTO `jc_site_flow` VALUES ('2102', '1', '127.0.0.1', '2014-6-9', '2014-06-09 07:39:57', 'http：//127.0.0.1：8080/gnxw/550.htm', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/', '', '中国', '1lm277f8qptb21aa1b39cz90rd');
INSERT INTO `jc_site_flow` VALUES ('2103', '1', '127.0.0.1', '2014-6-9', '2014-06-09 07:38:38', 'http：//127.0.0.1：8080/', 'http：//127.0.0.1：8080', 'http：//127.0.0.1：8080/interview/551.htm', '', '中国', '1lm277f8qptb21aa1b39cz90rd');
INSERT INTO `jc_site_flow` VALUES ('2104', '1', '127.0.0.1', '2014-6-9', '2014-06-09 23:22:20', 'http：//localhost：8080/live/index.htm', 'http：//localhost：8080', 'http：//localhost：8080/', '', '中国', '6flmjicw4rf0bbfjix1h8b4i');
INSERT INTO `jc_site_flow` VALUES ('2105', '1', '127.0.0.1', '2014-6-9', '2014-06-09 23:23:53', 'http：//localhost：8080/live/552.htm', 'http：//localhost：8080', 'http：//localhost：8080/live/index.htm', '', '中国', '6flmjicw4rf0bbfjix1h8b4i');
INSERT INTO `jc_site_flow` VALUES ('2106', '1', '127.0.0.1', '2014-6-9', '2014-06-09 23:24:37', 'http：//localhost：8080/live/552.htm', 'http：//localhost：8080', 'http：//localhost：8080/live/index.htm', '', '中国', '6flmjicw4rf0bbfjix1h8b4i');
INSERT INTO `jc_site_flow` VALUES ('2107', '1', '127.0.0.1', '2014-6-9', '2014-06-09 23:26:03', 'http：//localhost：8080/live/552.htm', 'http：//localhost：8080', 'http：//localhost：8080/live/index.htm', '', '中国', '6flmjicw4rf0bbfjix1h8b4i');
INSERT INTO `jc_site_flow` VALUES ('2108', '1', '127.0.0.1', '2014-6-9', '2014-06-09 23:31:02', 'http：//localhost：8080/live/552.htm', 'http：//localhost：8080', 'http：//localhost：8080/live/index.htm', '', '中国', '6flmjicw4rf0bbfjix1h8b4i');
INSERT INTO `jc_site_flow` VALUES ('2109', '1', '127.0.0.1', '2014-6-9', '2014-06-09 23:31:36', 'http：//localhost：8080/live/552.htm', 'http：//localhost：8080', 'http：//localhost：8080/live/index.htm', '', '中国', '6flmjicw4rf0bbfjix1h8b4i');
INSERT INTO `jc_site_flow` VALUES ('2110', '1', '127.0.0.1', '2014-6-9', '2014-06-09 23:31:24', 'http：//localhost：8080/live/552.htm', 'http：//localhost：8080', 'http：//localhost：8080/live/index.htm', '', '中国', '6flmjicw4rf0bbfjix1h8b4i');
INSERT INTO `jc_site_flow` VALUES ('2111', '1', '127.0.0.1', '2014-6-9', '2014-06-09 23:33:15', 'http：//localhost：8080/live/552.htm', 'http：//localhost：8080', 'http：//localhost：8080/admin/s/content/v_view.do?id=552&cid=&pageNo=&queryTitle=&queryInputUsername=&queryTopLevel=false&queryRecommend=false&queryTypeId=&queryStatus=&queryOrderBy=0', '', '中国', '6flmjicw4rf0bbfjix1h8b4i');
INSERT INTO `jc_site_flow` VALUES ('2112', '1', '127.0.0.1', '2014-6-9', '2014-06-09 23:34:09', 'http：//localhost：8080/live/552.htm', 'http：//localhost：8080', 'http：//localhost：8080/live/index.htm', '', '中国', '6flmjicw4rf0bbfjix1h8b4i');
INSERT INTO `jc_site_flow` VALUES ('2113', '1', '127.0.0.1', '2014-6-9', '2014-06-09 23:35:10', 'http：//localhost：8080/live/552.htm', 'http：//localhost：8080', 'http：//localhost：8080/live/index.htm', '', '中国', '6flmjicw4rf0bbfjix1h8b4i');
INSERT INTO `jc_site_flow` VALUES ('2114', '1', '127.0.0.1', '2014-6-9', '2014-06-09 23:34:10', 'http：//localhost：8080/live/552.htm', 'http：//localhost：8080', 'http：//localhost：8080/live/index.htm', '', '中国', '6flmjicw4rf0bbfjix1h8b4i');
INSERT INTO `jc_site_flow` VALUES ('2115', '1', '127.0.0.1', '2014-6-9', '2014-06-09 23:35:11', 'http：//localhost：8080/live/552.htm', 'http：//localhost：8080', 'http：//localhost：8080/live/index.htm', '', '中国', '6flmjicw4rf0bbfjix1h8b4i');
INSERT INTO `jc_site_flow` VALUES ('2116', '1', '127.0.0.1', '2014-6-9', '2014-06-09 23:36:07', 'http：//localhost：8080/live/552.htm', 'http：//localhost：8080', 'http：//localhost：8080/live/index.htm', '', '中国', '6flmjicw4rf0bbfjix1h8b4i');
INSERT INTO `jc_site_flow` VALUES ('2117', '1', '127.0.0.1', '2014-6-9', '2014-06-09 23:48:03', 'http：//localhost：8080/live/552.htm', 'http：//localhost：8080', 'http：//localhost：8080/live/index.htm', '', '中国', '6flmjicw4rf0bbfjix1h8b4i');
INSERT INTO `jc_site_flow` VALUES ('2118', '1', '127.0.0.1', '2014-6-10', '2014-06-10 09:41:58', 'http：//localhost：8080/live/552.htm', 'http：//localhost：8080', 'http：//localhost：8080/live/index.htm', '', '中国', 'f004cnk5iueom5qcx6301q0p');

-- ----------------------------
-- Table structure for `jc_site_model`
-- ----------------------------
DROP TABLE IF EXISTS `jc_site_model`;
CREATE TABLE `jc_site_model` (
  `model_id` int(11) NOT NULL AUTO_INCREMENT,
  `field` varchar(50) NOT NULL COMMENT '字段',
  `model_label` varchar(100) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL DEFAULT '10' COMMENT '排列顺序',
  `upload_path` varchar(100) DEFAULT NULL COMMENT '上传路径',
  `text_size` varchar(20) DEFAULT NULL COMMENT '长度',
  `area_rows` varchar(3) DEFAULT NULL COMMENT '文本行数',
  `area_cols` varchar(3) DEFAULT NULL COMMENT '文本列数',
  `help` varchar(255) DEFAULT NULL COMMENT '帮助信息',
  `help_position` varchar(1) DEFAULT NULL COMMENT '帮助位置',
  `data_type` int(11) DEFAULT '1' COMMENT '0:编辑器;1:文本框;2:文本区;3:图片;4:附件',
  `is_single` tinyint(1) DEFAULT '1' COMMENT '是否独占一行',
  PRIMARY KEY (`model_id`),
  UNIQUE KEY `ak_field` (`field`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS站点信息模型表';

-- ----------------------------
-- Records of jc_site_model
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_site_txt`
-- ----------------------------
DROP TABLE IF EXISTS `jc_site_txt`;
CREATE TABLE `jc_site_txt` (
  `site_id` int(11) NOT NULL,
  `txt_name` varchar(30) NOT NULL COMMENT '名称',
  `txt_value` longtext COMMENT '值',
  KEY `fk_jc_txt_site` (`site_id`),
  CONSTRAINT `fk_jc_txt_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS站点文本表';

-- ----------------------------
-- Records of jc_site_txt
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_task`
-- ----------------------------
DROP TABLE IF EXISTS `jc_task`;
CREATE TABLE `jc_task` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_code` varchar(255) DEFAULT NULL COMMENT '任务执行代码',
  `task_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '任务类型(1首页静态化、2栏目页静态化、3内容页静态化、4采集、5分发)',
  `task_name` varchar(255) NOT NULL COMMENT '任务名称',
  `job_class` varchar(255) NOT NULL COMMENT '任务类',
  `execycle` tinyint(1) NOT NULL DEFAULT '1' COMMENT '执行周期分类(1非表达式 2 cron表达式)',
  `day_of_month` int(11) DEFAULT NULL COMMENT '每月的哪天',
  `day_of_week` tinyint(1) DEFAULT NULL COMMENT '周几',
  `hour` int(11) DEFAULT NULL COMMENT '小时',
  `minute` int(11) DEFAULT NULL COMMENT '分钟',
  `interval_hour` int(11) DEFAULT NULL COMMENT '间隔小时',
  `interval_minute` int(11) DEFAULT NULL COMMENT '间隔分钟',
  `task_interval_unit` tinyint(1) DEFAULT NULL COMMENT '1分钟、2小时、3日、4周、5月',
  `cron_expression` varchar(255) DEFAULT NULL COMMENT '规则表达式',
  `is_enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `task_remark` varchar(255) DEFAULT NULL COMMENT '任务说明',
  `site_id` int(11) NOT NULL COMMENT '站点',
  `user_id` int(11) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`task_id`),
  KEY `fk_jc_task_site` (`site_id`),
  CONSTRAINT `fk_jc_task_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务表';

-- ----------------------------
-- Records of jc_task
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_task_attr`
-- ----------------------------
DROP TABLE IF EXISTS `jc_task_attr`;
CREATE TABLE `jc_task_attr` (
  `task_id` int(11) NOT NULL,
  `param_name` varchar(30) NOT NULL COMMENT '参数名称',
  `param_value` varchar(255) DEFAULT NULL COMMENT '参数值',
  KEY `fk_jc_attr_task` (`task_id`),
  CONSTRAINT `fk_jc_attr_task` FOREIGN KEY (`task_id`) REFERENCES `jc_task` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务参数表';

-- ----------------------------
-- Records of jc_task_attr
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_topic`
-- ----------------------------
DROP TABLE IF EXISTS `jc_topic`;
CREATE TABLE `jc_topic` (
  `topic_id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_id` int(11) DEFAULT NULL,
  `topic_name` varchar(150) NOT NULL COMMENT '名称',
  `short_name` varchar(150) DEFAULT NULL COMMENT '简称',
  `keywords` varchar(255) DEFAULT NULL COMMENT '关键字',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `title_img` varchar(100) DEFAULT NULL COMMENT '标题图',
  `content_img` varchar(100) DEFAULT NULL COMMENT '内容图',
  `tpl_content` varchar(100) DEFAULT NULL COMMENT '专题模板',
  `priority` int(11) NOT NULL DEFAULT '10' COMMENT '排列顺序',
  `is_recommend` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否推??',
  PRIMARY KEY (`topic_id`),
  KEY `fk_jc_topic_channel` (`channel_id`),
  CONSTRAINT `fk_jc_topic_channel` FOREIGN KEY (`channel_id`) REFERENCES `jc_channel` (`channel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='CMS专题表';

-- ----------------------------
-- Records of jc_topic
-- ----------------------------
INSERT INTO `jc_topic` VALUES ('1', null, '2010年南非世界杯', '世界杯', '世界杯', '2010年世界杯将在南非约翰内斯堡拉开帷幕，32路豪强将在一个月的时间里，为大力神杯展开争夺。', 'http://a2.att.hudong.com/08/61/01300000406647124377613651616.jpg', 'http://i0.sinaimg.cn/ty/news/2010/0611/sjbsc.jpg', '', '10', '1');
INSERT INTO `jc_topic` VALUES ('2', null, '上海世博会专题', '世博', '世博', '人类文明的盛会，我们大家的世博，精彩开篇，“满月”有余。随着上海世博会的有序前行，人们从中收获的感悟也由表及里。', 'http://xwcb.eastday.com/c/20061116/images/00033531.jpg', '/u/cms/www/201112/19151533k5ey.jpg', '', '10', '1');
INSERT INTO `jc_topic` VALUES ('3', null, '低碳经济', '低碳', '低碳', '所谓低碳经济，是指在可持续发展理念指导下，通过技术创新、制度创新、产业转型、新能源开发等多种手段，尽可能地减少煤炭石油等高碳能源消耗，减少温室气体排放，达到经济社会发展与生态环境保护双赢的一种经济发展形态。', '/u/cms/www/201309/09151507n6i1.jpg', null, '', '10', '1');
INSERT INTO `jc_topic` VALUES ('4', null, '习近平中亚之行', '习近平中亚之行', '习近平中亚之行', '9月3日—4日：对土库曼斯进行国事访问\r\n·9月5日—6日：出席G20领导人第八次峰会\r\n·9月7日—12日：对哈萨克斯坦、乌兹别克斯坦和吉尔吉斯斯坦进行国事访问\r\n·9月13日：出席上海合作组织成员国元首理事会第十三次会议', '/u/cms/www/201309/09152931cgps.jpg', '/u/cms/www/201309/09152518pzoq.jpg', '', '10', '1');
INSERT INTO `jc_topic` VALUES ('5', null, '广西桂林学校附近发生爆炸', '广西桂林学校附近发生爆炸', '广西桂林学校附近发生爆炸', '·时间：9日8时许\r\n·地点：桂林市灵川县八里街学校大门附近\r\n·事件：9日8时许，广西桂林市八里街发生一起爆炸事故…[详细] \r\n·伤亡：已造成2人死亡，17人不同程度受伤，其中1人受重伤。已确认伤者中包括10名小学生、3名家长和5名路人 \r\n·嫌疑人：警方称嫌疑人驾驶三轮车经过现场，当时是三轮车起火，然后发生爆炸 \r\n', '/u/cms/www/201309/0915541140xt.jpg', '/u/cms/www/201309/091552426die.jpg', '', '10', '1');
INSERT INTO `jc_topic` VALUES ('6', null, '美军准备空袭叙利亚', '美军准备空袭叙利亚', '美军准备空袭叙利亚', '·6月4日：联合国称叙冲突双方均使用化武\r\n·7月9日：俄调查称叙反对派使用化学武器 美国拒绝接受 \r\n·8月21日：反对派称遭化武袭击1300人死 \r\n·26日：联合国小组开始调查叙化武事件\r\n·26日：美国取消俄美有关叙问题会谈 \r\n·28日：美媒称美拟29日导弹打击叙利亚 \r\n·9月3日：以色列和美国试射一枚“麻雀”导弹，这是此种导弹首次进行飞行测试。', '/u/cms/www/201309/09160120meel.jpg', '/u/cms/www/201309/091602465aop.jpg', '', '10', '1');
INSERT INTO `jc_topic` VALUES ('7', null, '“气功大师”王林遭质疑', '“气功大师”王林遭质疑', '“气功大师”王林遭质疑', '“气功大师”王林一直以来的低调被2013年7月初马云的一次拜访打破，网友的质疑让他重回公众视野。上世纪90年代气功潮之后，大师纷纷被拉下神坛。当时已成名的王林却有着自己的生存策略。这十几年，他极少接受媒体采访。他在江西萍乡，他一直延续着自己“大师”的神话。在相对封闭的空间里，经营着名声、财富和权势。据商人邹勇的说法，他当时在铁道部见到了刘志军，王林和刘志军两人看起来很熟悉。当着邹勇的面，王林对刘志军说要帮他办公室弄一块靠山石，“保你一辈子不倒”。', '/u/cms/www/201309/09174523xkvt.jpg', '/u/cms/www/201309/09174527lkok.jpg', '', '10', '1');
INSERT INTO `jc_topic` VALUES ('8', null, '测试专题', '测试专题简称', '测试专题关键字', '测试专题描述', '/u/cms/www/201404/2713005554hy.jpg', null, '', '10', '1');

-- ----------------------------
-- Table structure for `jc_user`
-- ----------------------------
DROP TABLE IF EXISTS `jc_user`;
CREATE TABLE `jc_user` (
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `register_ip` varchar(50) NOT NULL DEFAULT '127.0.0.1' COMMENT '注册IP',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) NOT NULL DEFAULT '127.0.0.1' COMMENT '最后登录IP',
  `login_count` int(11) NOT NULL DEFAULT '0' COMMENT '登录次数',
  `rank` int(11) NOT NULL DEFAULT '0' COMMENT '管理员级别',
  `upload_total` bigint(20) NOT NULL DEFAULT '0' COMMENT '上传总大小',
  `upload_size` int(11) NOT NULL DEFAULT '0' COMMENT '上传大小',
  `upload_date` date DEFAULT NULL COMMENT '上传日期',
  `is_admin` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否管理员',
  `is_viewonly_admin` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否只读管理员',
  `is_self_admin` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否只管理自己的数据',
  `is_disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `ak_username` (`username`),
  KEY `fk_jc_user_group` (`group_id`),
  CONSTRAINT `fk_jc_user_group` FOREIGN KEY (`group_id`) REFERENCES `jc_group` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS用户表';

-- ----------------------------
-- Records of jc_user
-- ----------------------------
INSERT INTO `jc_user` VALUES ('1', '1', 'admin', 'admin@yahoo.com', '2011-01-03 00:00:00', '127.0.0.1', '2014-06-10 09:22:58', '127.0.0.1', '472', '9', '63', '63', '2013-07-10', '1', '0', '0', '0');

-- ----------------------------
-- Table structure for `jc_user_collection`
-- ----------------------------
DROP TABLE IF EXISTS `jc_user_collection`;
CREATE TABLE `jc_user_collection` (
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `content_id` int(11) NOT NULL DEFAULT '0' COMMENT '内容id',
  PRIMARY KEY (`user_id`,`content_id`),
  KEY `fk_jc_user_collection_con` (`content_id`),
  CONSTRAINT `fk_jc_user_collection_con` FOREIGN KEY (`content_id`) REFERENCES `jc_content` (`content_id`),
  CONSTRAINT `fk_jc_user_collection_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户收藏关联表';

-- ----------------------------
-- Records of jc_user_collection
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_user_ext`
-- ----------------------------
DROP TABLE IF EXISTS `jc_user_ext`;
CREATE TABLE `jc_user_ext` (
  `user_id` int(11) NOT NULL,
  `realname` varchar(100) DEFAULT NULL COMMENT '真实姓名',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期',
  `intro` varchar(255) DEFAULT NULL COMMENT '个人介绍',
  `comefrom` varchar(150) DEFAULT NULL COMMENT '来自',
  `qq` varchar(100) DEFAULT NULL COMMENT 'QQ',
  `msn` varchar(100) DEFAULT NULL COMMENT 'MSN',
  `phone` varchar(50) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机',
  `user_img` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `user_signature` varchar(255) DEFAULT NULL COMMENT '用户个性签名',
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_jc_ext_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS用户扩展信息表';

-- ----------------------------
-- Records of jc_user_ext
-- ----------------------------
INSERT INTO `jc_user_ext` VALUES ('1', '管理员', '1', null, null, '南昌', null, null, null, '15011111111', '', '');

-- ----------------------------
-- Table structure for `jc_user_resume`
-- ----------------------------
DROP TABLE IF EXISTS `jc_user_resume`;
CREATE TABLE `jc_user_resume` (
  `user_id` int(11) NOT NULL,
  `resume_name` varchar(255) NOT NULL COMMENT '简历名称',
  `target_worknature` varchar(255) DEFAULT NULL COMMENT '期望工作性质',
  `target_workplace` varchar(255) DEFAULT NULL COMMENT '期望工作地点',
  `target_category` varchar(255) DEFAULT NULL COMMENT '期望职位类别',
  `target_salary` varchar(255) DEFAULT NULL COMMENT '期望月薪',
  `edu_school` varchar(255) DEFAULT NULL COMMENT '毕业学校',
  `edu_graduation` datetime DEFAULT NULL COMMENT '毕业时间',
  `edu_back` varchar(255) DEFAULT NULL COMMENT '学历',
  `edu_discipline` varchar(255) DEFAULT NULL COMMENT '专业',
  `recent_company` varchar(500) DEFAULT NULL COMMENT '最近工作公司名称',
  `company_industry` varchar(255) DEFAULT NULL COMMENT '最近公司所属行业',
  `company_scale` varchar(255) DEFAULT NULL COMMENT '公司规模',
  `job_name` varchar(255) DEFAULT NULL COMMENT '职位名称',
  `job_category` varchar(255) DEFAULT NULL COMMENT '职位类别',
  `job_start` datetime DEFAULT NULL COMMENT '工作起始时间',
  `subordinates` varchar(255) DEFAULT NULL COMMENT '下属人数',
  `job_description` text COMMENT '工作描述',
  `self_evaluation` varchar(2000) DEFAULT NULL COMMENT '自我评价',
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_jc_resume_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户简历';

-- ----------------------------
-- Records of jc_user_resume
-- ----------------------------
INSERT INTO `jc_user_resume` VALUES ('1', '程序员', '全职', '上海', '项目主管', '', '南昌大学', '2009-07-01 00:00:00', '本科', '计算机', '江西金磊科技发展有限公司', null, null, '程序员', null, '2009-10-01 00:00:00', null, '程序开发', '小伙不错');

-- ----------------------------
-- Table structure for `jc_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `jc_user_role`;
CREATE TABLE `jc_user_role` (
  `role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `fk_jc_role_user` (`user_id`),
  CONSTRAINT `fk_jc_role_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`),
  CONSTRAINT `fk_jc_user_role` FOREIGN KEY (`role_id`) REFERENCES `jc_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS用户角色关联表';

-- ----------------------------
-- Records of jc_user_role
-- ----------------------------
INSERT INTO `jc_user_role` VALUES ('1', '1');

-- ----------------------------
-- Table structure for `jc_user_site`
-- ----------------------------
DROP TABLE IF EXISTS `jc_user_site`;
CREATE TABLE `jc_user_site` (
  `usersite_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `site_id` int(11) NOT NULL,
  `check_step` tinyint(4) NOT NULL DEFAULT '1' COMMENT '审核级别',
  `is_all_channel` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否拥有所有栏目的权限',
  PRIMARY KEY (`usersite_id`),
  KEY `fk_jc_site_user` (`user_id`),
  KEY `fk_jc_user_site` (`site_id`),
  CONSTRAINT `fk_jc_site_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`),
  CONSTRAINT `fk_jc_user_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='CMS管理员站点表';

-- ----------------------------
-- Records of jc_user_site
-- ----------------------------
INSERT INTO `jc_user_site` VALUES ('1', '1', '1', '2', '1');

-- ----------------------------
-- Table structure for `jc_vote_item`
-- ----------------------------
DROP TABLE IF EXISTS `jc_vote_item`;
CREATE TABLE `jc_vote_item` (
  `voteitem_id` int(11) NOT NULL AUTO_INCREMENT,
  `votetopic_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL COMMENT '标题',
  `vote_count` int(11) NOT NULL DEFAULT '0' COMMENT '投票数量',
  `priority` int(11) NOT NULL DEFAULT '10' COMMENT '排列顺序',
  `subtopic_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`voteitem_id`),
  KEY `fk_jc_vote_item_topic` (`votetopic_id`),
  KEY `FK_jc_vote_item_subtopic` (`subtopic_id`),
  CONSTRAINT `FK_jc_vote_item_subtopic` FOREIGN KEY (`subtopic_id`) REFERENCES `jc_vote_subtopic` (`subtopic_id`),
  CONSTRAINT `fk_jc_vote_item_topic` FOREIGN KEY (`votetopic_id`) REFERENCES `jc_vote_topic` (`votetopic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='CMS投票项';

-- ----------------------------
-- Records of jc_vote_item
-- ----------------------------
INSERT INTO `jc_vote_item` VALUES ('13', '3', '1111111111111111111', '0', '1', '1');
INSERT INTO `jc_vote_item` VALUES ('14', '3', '1111111111111122222222', '0', '4', '1');
INSERT INTO `jc_vote_item` VALUES ('15', '3', '111111111133333333333', '0', '5', '1');
INSERT INTO `jc_vote_item` VALUES ('16', '3', '22222222222222', '0', '2', '2');
INSERT INTO `jc_vote_item` VALUES ('17', '3', '222222222222222222', '0', '6', '2');
INSERT INTO `jc_vote_item` VALUES ('18', '3', '2222222222222222222222', '0', '7', '2');
INSERT INTO `jc_vote_item` VALUES ('19', '3', '33333333333333333333', '0', '3', '3');
INSERT INTO `jc_vote_item` VALUES ('20', '3', '3333333333333333333333', '0', '8', '3');
INSERT INTO `jc_vote_item` VALUES ('21', '3', '3333333333333333333333333333333333333333333333333', '0', '9', '3');

-- ----------------------------
-- Table structure for `jc_vote_record`
-- ----------------------------
DROP TABLE IF EXISTS `jc_vote_record`;
CREATE TABLE `jc_vote_record` (
  `voterecored_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `votetopic_id` int(11) NOT NULL,
  `vote_time` datetime NOT NULL COMMENT '投票时间',
  `vote_ip` varchar(50) NOT NULL COMMENT '投票IP',
  `vote_cookie` varchar(32) NOT NULL COMMENT '投票COOKIE',
  PRIMARY KEY (`voterecored_id`),
  KEY `fk_jc_vote_record_topic` (`votetopic_id`),
  KEY `fk_jc_voterecord_user` (`user_id`),
  CONSTRAINT `fk_jc_voterecord_user` FOREIGN KEY (`user_id`) REFERENCES `jc_user` (`user_id`),
  CONSTRAINT `fk_jc_vote_record_topic` FOREIGN KEY (`votetopic_id`) REFERENCES `jc_vote_topic` (`votetopic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS投票记录';

-- ----------------------------
-- Records of jc_vote_record
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_vote_reply`
-- ----------------------------
DROP TABLE IF EXISTS `jc_vote_reply`;
CREATE TABLE `jc_vote_reply` (
  `votereply_id` int(11) NOT NULL AUTO_INCREMENT,
  `reply` text COMMENT '回复内容',
  `subtopic_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`votereply_id`),
  KEY `FK_jc_vote_reply_sub` (`subtopic_id`),
  CONSTRAINT `FK_jc_vote_reply_sub` FOREIGN KEY (`subtopic_id`) REFERENCES `jc_vote_subtopic` (`subtopic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投票文本题目回复表';

-- ----------------------------
-- Records of jc_vote_reply
-- ----------------------------

-- ----------------------------
-- Table structure for `jc_vote_subtopic`
-- ----------------------------
DROP TABLE IF EXISTS `jc_vote_subtopic`;
CREATE TABLE `jc_vote_subtopic` (
  `subtopic_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '标题',
  `votetopic_id` int(11) NOT NULL DEFAULT '0' COMMENT '投票（调查）',
  `subtopic_type` int(2) NOT NULL DEFAULT '1' COMMENT '类型（1单选，2多选，3文本）',
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`subtopic_id`),
  KEY `FK_jc_vote_subtopic_vote` (`votetopic_id`),
  CONSTRAINT `FK_jc_vote_subtopic_vote` FOREIGN KEY (`votetopic_id`) REFERENCES `jc_vote_topic` (`votetopic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='投票子题目';

-- ----------------------------
-- Records of jc_vote_subtopic
-- ----------------------------
INSERT INTO `jc_vote_subtopic` VALUES ('1', '444444444444', '3', '3', '1');
INSERT INTO `jc_vote_subtopic` VALUES ('2', '333333333333', '3', '2', '2');
INSERT INTO `jc_vote_subtopic` VALUES ('3', '222222222222222', '3', '2', '3');
INSERT INTO `jc_vote_subtopic` VALUES ('4', '1111111111', '3', '1', '4');

-- ----------------------------
-- Table structure for `jc_vote_topic`
-- ----------------------------
DROP TABLE IF EXISTS `jc_vote_topic`;
CREATE TABLE `jc_vote_topic` (
  `votetopic_id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL COMMENT '标题',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `repeate_hour` int(11) DEFAULT NULL COMMENT '重复投票限制时间，单位小时，为空不允许重复投票',
  `total_count` int(11) NOT NULL DEFAULT '0' COMMENT '总投票数',
  `multi_select` int(11) NOT NULL DEFAULT '1' COMMENT '最多可以选择几项',
  `is_restrict_member` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否限制会员',
  `is_restrict_ip` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否限制IP',
  `is_restrict_cookie` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否限制COOKIE',
  `is_disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用',
  `is_def` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否默认主题',
  PRIMARY KEY (`votetopic_id`),
  KEY `fk_jc_votetopic_site` (`site_id`),
  CONSTRAINT `fk_jc_votetopic_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='CMS投票主题';

-- ----------------------------
-- Records of jc_vote_topic
-- ----------------------------
INSERT INTO `jc_vote_topic` VALUES ('3', '1', '腾讯新闻频道首页用户调查问卷', '1212', null, null, null, '0', '1', '0', '0', '1', '0', '1');

-- ----------------------------
-- Table structure for `jo_authentication`
-- ----------------------------
DROP TABLE IF EXISTS `jo_authentication`;
CREATE TABLE `jo_authentication` (
  `authentication_id` char(32) NOT NULL COMMENT '认证ID',
  `userid` int(11) NOT NULL COMMENT '用户ID',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `login_ip` varchar(50) NOT NULL COMMENT '登录ip',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='认证信息表';

-- ----------------------------
-- Records of jo_authentication
-- ----------------------------
INSERT INTO `jo_authentication` VALUES ('aec1f1ca1263405fa1b9c63a47d883ae', '1', 'admin', 'admin@yahoo.com', '2014-06-10 09:22:58', '127.0.0.1', '2014-06-10 09:42:04');

-- ----------------------------
-- Table structure for `jo_config`
-- ----------------------------
DROP TABLE IF EXISTS `jo_config`;
CREATE TABLE `jo_config` (
  `cfg_key` varchar(50) NOT NULL COMMENT '配置KEY',
  `cfg_value` varchar(255) DEFAULT NULL COMMENT '配置VALUE',
  PRIMARY KEY (`cfg_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置表';

-- ----------------------------
-- Records of jo_config
-- ----------------------------
INSERT INTO `jo_config` VALUES ('email_encoding', '');
INSERT INTO `jo_config` VALUES ('email_host', 'smtp.qq.com');
INSERT INTO `jo_config` VALUES ('email_password', 'jeecms');
INSERT INTO `jo_config` VALUES ('email_personal', '');
INSERT INTO `jo_config` VALUES ('email_port', null);
INSERT INTO `jo_config` VALUES ('email_username', '2283752600@qq.com');
INSERT INTO `jo_config` VALUES ('login_error_interval', '30');
INSERT INTO `jo_config` VALUES ('login_error_times', '3');
INSERT INTO `jo_config` VALUES ('message_forgotpassword_subject', '找回密码');
INSERT INTO `jo_config` VALUES ('message_forgotpassword_text', '感谢您使用JEECMS系统会员密码找回功能，请记住以下找回信息：\r\n用户ID：${uid}\r\n用户名：${username}\r\n您的新密码为：${resetPwd}\r\n请访问如下链接新密码才能生效：\r\nhttp://localhost/member/password_reset.jspx?uid=${uid}&key=${resetKey}');
INSERT INTO `jo_config` VALUES ('message_register_subject', '欢迎您注册JEECMS用户');
INSERT INTO `jo_config` VALUES ('message_register_text', '${username}您好：\r\n欢迎您注册JEECMS系统会员\r\n请点击以下链接进行激活\r\nhttp://localhost/active.jspx?username=${username}&key=${activationCode}');
INSERT INTO `jo_config` VALUES ('message_subject', 'JEECMS会员密码找回信息');
INSERT INTO `jo_config` VALUES ('message_text', '感谢您使用JEECMS系统会员密码找回功能，请记住以下找回信息：\r\n用户ID：${uid}\r\n用户名：${username}\r\n您的新密码为：${resetPwd}\r\n请访问如下链接新密码才能生效：\r\nhttp://localhost/member/password_reset.jspx?uid=${uid}&key=${resetKey}\r\n');

-- ----------------------------
-- Table structure for `jo_ftp`
-- ----------------------------
DROP TABLE IF EXISTS `jo_ftp`;
CREATE TABLE `jo_ftp` (
  `ftp_id` int(11) NOT NULL AUTO_INCREMENT,
  `ftp_name` varchar(100) NOT NULL COMMENT '名称',
  `ip` varchar(50) NOT NULL COMMENT 'IP',
  `port` int(11) NOT NULL DEFAULT '21' COMMENT '端口号',
  `username` varchar(100) DEFAULT NULL COMMENT '登录名',
  `password` varchar(100) DEFAULT NULL COMMENT '登陆密码',
  `encoding` varchar(20) NOT NULL DEFAULT 'UTF-8' COMMENT '编码',
  `timeout` int(11) DEFAULT NULL COMMENT '超时时间',
  `ftp_path` varchar(255) DEFAULT NULL COMMENT '路径',
  `url` varchar(255) NOT NULL COMMENT '访问URL',
  PRIMARY KEY (`ftp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='FTP表';

-- ----------------------------
-- Records of jo_ftp
-- ----------------------------

-- ----------------------------
-- Table structure for `jo_template`
-- ----------------------------
DROP TABLE IF EXISTS `jo_template`;
CREATE TABLE `jo_template` (
  `tpl_name` varchar(150) NOT NULL COMMENT '模板名称',
  `tpl_source` longtext COMMENT '模板内容',
  `last_modified` bigint(20) NOT NULL COMMENT '最后修改时间',
  `is_directory` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否目录',
  PRIMARY KEY (`tpl_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模板表';

-- ----------------------------
-- Records of jo_template
-- ----------------------------

-- ----------------------------
-- Table structure for `jo_upload`
-- ----------------------------
DROP TABLE IF EXISTS `jo_upload`;
CREATE TABLE `jo_upload` (
  `filename` varchar(150) NOT NULL COMMENT '文件名',
  `length` int(11) NOT NULL COMMENT '文件大小(字节)',
  `last_modified` bigint(20) NOT NULL COMMENT '最后修改时间',
  `content` longblob NOT NULL COMMENT '内容',
  PRIMARY KEY (`filename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='上传附件表';

-- ----------------------------
-- Records of jo_upload
-- ----------------------------

-- ----------------------------
-- Table structure for `jo_user`
-- ----------------------------
DROP TABLE IF EXISTS `jo_user`;
CREATE TABLE `jo_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `password` char(32) NOT NULL COMMENT '密码',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `register_ip` varchar(50) NOT NULL DEFAULT '127.0.0.1' COMMENT '注册IP',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) NOT NULL DEFAULT '127.0.0.1' COMMENT '最后登录IP',
  `login_count` int(11) NOT NULL DEFAULT '0' COMMENT '登录次数',
  `reset_key` char(32) DEFAULT NULL COMMENT '重置密码KEY',
  `reset_pwd` varchar(10) DEFAULT NULL COMMENT '重置密码VALUE',
  `error_time` datetime DEFAULT NULL COMMENT '登录错误时间',
  `error_count` int(11) NOT NULL DEFAULT '0' COMMENT '登录错误次数',
  `error_ip` varchar(50) DEFAULT NULL COMMENT '登录错误IP',
  `activation` tinyint(1) NOT NULL DEFAULT '1' COMMENT '激活状态',
  `activation_code` char(32) DEFAULT NULL COMMENT '激活码',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `ak_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of jo_user
-- ----------------------------
INSERT INTO `jo_user` VALUES ('1', 'admin', 'admin@yahoo.com', '5f4dcc3b5aa765d61d8327deb882cf99', '2011-01-03 00:00:00', '127.0.0.1', '2014-06-10 09:22:58', '127.0.0.1', '473', null, null, null, '0', null, '1', null);
