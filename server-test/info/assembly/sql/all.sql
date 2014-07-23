/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : info

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2013-10-14 22:24:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `info_city`
-- ----------------------------
DROP TABLE IF EXISTS `info_city`;
CREATE TABLE `info_city` (
  `id` int(11) NOT NULL,
  `name` varchar(36) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of info_city
-- ----------------------------
INSERT INTO `info_city` VALUES ('1', '北京市', '0');
INSERT INTO `info_city` VALUES ('2', '天津市', '0');
INSERT INTO `info_city` VALUES ('3', '河北省', '0');
INSERT INTO `info_city` VALUES ('4', '山西省', '0');
INSERT INTO `info_city` VALUES ('5', '内蒙古自治区', '0');
INSERT INTO `info_city` VALUES ('6', '辽宁省', '0');
INSERT INTO `info_city` VALUES ('7', '吉林省', '0');
INSERT INTO `info_city` VALUES ('8', '黑龙江省', '0');
INSERT INTO `info_city` VALUES ('9', '上海市', '0');
INSERT INTO `info_city` VALUES ('10', '江苏省', '0');
INSERT INTO `info_city` VALUES ('11', '浙江省', '0');
INSERT INTO `info_city` VALUES ('12', '安徽省', '0');
INSERT INTO `info_city` VALUES ('13', '福建省', '0');
INSERT INTO `info_city` VALUES ('14', '江西省', '0');
INSERT INTO `info_city` VALUES ('15', '山东省', '0');
INSERT INTO `info_city` VALUES ('16', '河南省', '0');
INSERT INTO `info_city` VALUES ('17', '湖北省', '0');
INSERT INTO `info_city` VALUES ('18', '湖南省', '0');
INSERT INTO `info_city` VALUES ('19', '广东省', '0');
INSERT INTO `info_city` VALUES ('20', '广西壮族自治区', '0');
INSERT INTO `info_city` VALUES ('21', '海南省', '0');
INSERT INTO `info_city` VALUES ('22', '重庆市', '0');
INSERT INTO `info_city` VALUES ('23', '四川省', '0');
INSERT INTO `info_city` VALUES ('24', '贵州省', '0');
INSERT INTO `info_city` VALUES ('25', '云南省', '0');
INSERT INTO `info_city` VALUES ('26', '西藏自治区', '0');
INSERT INTO `info_city` VALUES ('27', '陕西省', '0');
INSERT INTO `info_city` VALUES ('28', '甘肃省', '0');
INSERT INTO `info_city` VALUES ('29', '青海省', '0');
INSERT INTO `info_city` VALUES ('30', '宁夏回族自治区', '0');
INSERT INTO `info_city` VALUES ('31', '新疆维吾尔自治区', '0');
INSERT INTO `info_city` VALUES ('32', '香港特别行政区', '0');
INSERT INTO `info_city` VALUES ('33', '澳门特别行政区', '0');
INSERT INTO `info_city` VALUES ('34', '台湾省', '0');
INSERT INTO `info_city` VALUES ('40', '抚顺市', '6');
INSERT INTO `info_city` VALUES ('41', '本溪市', '6');
INSERT INTO `info_city` VALUES ('42', '丹东市', '6');
INSERT INTO `info_city` VALUES ('43', '锦州市', '6');
INSERT INTO `info_city` VALUES ('44', '营口市', '6');
INSERT INTO `info_city` VALUES ('45', '阜新市', '6');
INSERT INTO `info_city` VALUES ('46', '辽阳市', '6');
INSERT INTO `info_city` VALUES ('47', '盘锦市', '6');
INSERT INTO `info_city` VALUES ('48', '铁岭市', '6');
INSERT INTO `info_city` VALUES ('49', '朝阳市', '6');
INSERT INTO `info_city` VALUES ('50', '葫芦岛市', '6');
INSERT INTO `info_city` VALUES ('51', '长春市', '7');
INSERT INTO `info_city` VALUES ('52', '吉林市', '7');
INSERT INTO `info_city` VALUES ('53', '四平市', '7');
INSERT INTO `info_city` VALUES ('54', '辽源市', '7');
INSERT INTO `info_city` VALUES ('55', '通化市', '7');
INSERT INTO `info_city` VALUES ('56', '白山市', '7');
INSERT INTO `info_city` VALUES ('57', '松原市', '7');
INSERT INTO `info_city` VALUES ('58', '白城市', '7');
INSERT INTO `info_city` VALUES ('59', '延边朝鲜族自治州', '7');
INSERT INTO `info_city` VALUES ('60', '哈尔滨市', '8');
INSERT INTO `info_city` VALUES ('61', '齐齐哈尔市', '8');
INSERT INTO `info_city` VALUES ('62', '鸡西市', '8');
INSERT INTO `info_city` VALUES ('63', '鹤岗市', '8');
INSERT INTO `info_city` VALUES ('64', '双鸭山市', '8');
INSERT INTO `info_city` VALUES ('65', '大庆市', '8');
INSERT INTO `info_city` VALUES ('66', '伊春市', '8');
INSERT INTO `info_city` VALUES ('67', '佳木斯市', '8');
INSERT INTO `info_city` VALUES ('68', '七台河市', '8');
INSERT INTO `info_city` VALUES ('69', '牡丹江市', '8');
INSERT INTO `info_city` VALUES ('70', '黑河市', '8');
INSERT INTO `info_city` VALUES ('71', '绥化市', '8');
INSERT INTO `info_city` VALUES ('72', '大兴安岭地区', '8');
INSERT INTO `info_city` VALUES ('73', '上海市', '9');
INSERT INTO `info_city` VALUES ('74', '南京市', '10');
INSERT INTO `info_city` VALUES ('75', '无锡市', '10');
INSERT INTO `info_city` VALUES ('76', '徐州市', '10');
INSERT INTO `info_city` VALUES ('77', '常州市', '10');
INSERT INTO `info_city` VALUES ('78', '苏州市', '10');
INSERT INTO `info_city` VALUES ('79', '南通市', '10');
INSERT INTO `info_city` VALUES ('80', '连云港市', '10');
INSERT INTO `info_city` VALUES ('81', '淮安市', '10');
INSERT INTO `info_city` VALUES ('82', '盐城市', '10');
INSERT INTO `info_city` VALUES ('83', '扬州市', '10');
INSERT INTO `info_city` VALUES ('84', '镇江市', '10');
INSERT INTO `info_city` VALUES ('85', '泰州市', '10');
INSERT INTO `info_city` VALUES ('86', '宿迁市', '10');
INSERT INTO `info_city` VALUES ('87', '杭州市', '11');
INSERT INTO `info_city` VALUES ('88', '宁波市', '11');
INSERT INTO `info_city` VALUES ('89', '温州市', '11');
INSERT INTO `info_city` VALUES ('90', '嘉兴市', '11');
INSERT INTO `info_city` VALUES ('91', '湖州市', '11');
INSERT INTO `info_city` VALUES ('92', '绍兴市', '11');
INSERT INTO `info_city` VALUES ('93', '金华市', '11');
INSERT INTO `info_city` VALUES ('94', '衢州市', '11');
INSERT INTO `info_city` VALUES ('95', '舟山市', '11');
INSERT INTO `info_city` VALUES ('96', '台州市', '11');
INSERT INTO `info_city` VALUES ('97', '丽水市', '11');
INSERT INTO `info_city` VALUES ('98', '合肥市', '12');
INSERT INTO `info_city` VALUES ('99', '芜湖市', '12');
INSERT INTO `info_city` VALUES ('100', '蚌埠市', '12');
INSERT INTO `info_city` VALUES ('101', '淮南市', '12');
INSERT INTO `info_city` VALUES ('102', '马鞍山市', '12');
INSERT INTO `info_city` VALUES ('103', '淮北市', '12');
INSERT INTO `info_city` VALUES ('104', '铜陵市', '12');
INSERT INTO `info_city` VALUES ('105', '安庆市', '12');
INSERT INTO `info_city` VALUES ('106', '黄山市', '12');
INSERT INTO `info_city` VALUES ('107', '滁州市', '12');
INSERT INTO `info_city` VALUES ('108', '阜阳市', '12');
INSERT INTO `info_city` VALUES ('109', '宿州市', '12');
INSERT INTO `info_city` VALUES ('110', '巢湖市', '12');
INSERT INTO `info_city` VALUES ('111', '六安市', '12');
INSERT INTO `info_city` VALUES ('112', '亳州市', '12');
INSERT INTO `info_city` VALUES ('113', '池州市', '12');
INSERT INTO `info_city` VALUES ('114', '宣城市', '12');
INSERT INTO `info_city` VALUES ('115', '福州市', '13');
INSERT INTO `info_city` VALUES ('116', '厦门市', '13');
INSERT INTO `info_city` VALUES ('117', '莆田市', '13');
INSERT INTO `info_city` VALUES ('118', '三明市', '13');
INSERT INTO `info_city` VALUES ('119', '泉州市', '13');
INSERT INTO `info_city` VALUES ('120', '漳州市', '13');
INSERT INTO `info_city` VALUES ('121', '南平市', '13');
INSERT INTO `info_city` VALUES ('122', '龙岩市', '13');
INSERT INTO `info_city` VALUES ('123', '宁德市', '13');
INSERT INTO `info_city` VALUES ('124', '南昌市', '14');
INSERT INTO `info_city` VALUES ('125', '景德镇市', '14');
INSERT INTO `info_city` VALUES ('126', '萍乡市', '14');
INSERT INTO `info_city` VALUES ('127', '九江市', '14');
INSERT INTO `info_city` VALUES ('128', '新余市', '14');
INSERT INTO `info_city` VALUES ('129', '鹰潭市', '14');
INSERT INTO `info_city` VALUES ('130', '赣州市', '14');
INSERT INTO `info_city` VALUES ('131', '吉安市', '14');
INSERT INTO `info_city` VALUES ('132', '宜春市', '14');
INSERT INTO `info_city` VALUES ('133', '抚州市', '14');
INSERT INTO `info_city` VALUES ('134', '上饶市', '14');
INSERT INTO `info_city` VALUES ('135', '济南市', '15');
INSERT INTO `info_city` VALUES ('136', '青岛市', '15');
INSERT INTO `info_city` VALUES ('137', '淄博市', '15');
INSERT INTO `info_city` VALUES ('138', '枣庄市', '15');
INSERT INTO `info_city` VALUES ('139', '东营市', '15');
INSERT INTO `info_city` VALUES ('140', '烟台市', '15');
INSERT INTO `info_city` VALUES ('141', '潍坊市', '15');
INSERT INTO `info_city` VALUES ('142', '济宁市', '15');
INSERT INTO `info_city` VALUES ('143', '泰安市', '15');
INSERT INTO `info_city` VALUES ('144', '威海市', '15');
INSERT INTO `info_city` VALUES ('145', '日照市', '15');
INSERT INTO `info_city` VALUES ('146', '莱芜市', '15');
INSERT INTO `info_city` VALUES ('147', '临沂市', '15');
INSERT INTO `info_city` VALUES ('148', '德州市', '15');
INSERT INTO `info_city` VALUES ('149', '聊城市', '15');
INSERT INTO `info_city` VALUES ('150', '滨州市', '15');
INSERT INTO `info_city` VALUES ('151', '荷泽市', '15');
INSERT INTO `info_city` VALUES ('152', '郑州市', '16');
INSERT INTO `info_city` VALUES ('153', '开封市', '16');
INSERT INTO `info_city` VALUES ('154', '洛阳市', '16');
INSERT INTO `info_city` VALUES ('155', '平顶山市', '16');
INSERT INTO `info_city` VALUES ('156', '安阳市', '16');
INSERT INTO `info_city` VALUES ('157', '鹤壁市', '16');
INSERT INTO `info_city` VALUES ('158', '新乡市', '16');
INSERT INTO `info_city` VALUES ('159', '焦作市', '16');
INSERT INTO `info_city` VALUES ('160', '濮阳市', '16');
INSERT INTO `info_city` VALUES ('161', '许昌市', '16');
INSERT INTO `info_city` VALUES ('162', '漯河市', '16');
INSERT INTO `info_city` VALUES ('163', '三门峡市', '16');
INSERT INTO `info_city` VALUES ('164', '南阳市', '16');
INSERT INTO `info_city` VALUES ('165', '商丘市', '16');
INSERT INTO `info_city` VALUES ('166', '信阳市', '16');
INSERT INTO `info_city` VALUES ('167', '周口市', '16');
INSERT INTO `info_city` VALUES ('168', '驻马店市', '16');
INSERT INTO `info_city` VALUES ('169', '武汉市', '17');
INSERT INTO `info_city` VALUES ('170', '黄石市', '17');
INSERT INTO `info_city` VALUES ('171', '十堰市', '17');
INSERT INTO `info_city` VALUES ('172', '宜昌市', '17');
INSERT INTO `info_city` VALUES ('173', '襄樊市', '17');
INSERT INTO `info_city` VALUES ('174', '鄂州市', '17');
INSERT INTO `info_city` VALUES ('175', '荆门市', '17');
INSERT INTO `info_city` VALUES ('176', '孝感市', '17');
INSERT INTO `info_city` VALUES ('177', '荆州市', '17');
INSERT INTO `info_city` VALUES ('178', '黄冈市', '17');
INSERT INTO `info_city` VALUES ('179', '咸宁市', '17');
INSERT INTO `info_city` VALUES ('180', '随州市', '17');
INSERT INTO `info_city` VALUES ('181', '恩施土家族苗族自治州', '17');
INSERT INTO `info_city` VALUES ('182', '神农架', '17');
INSERT INTO `info_city` VALUES ('183', '长沙市', '18');
INSERT INTO `info_city` VALUES ('184', '株洲市', '18');
INSERT INTO `info_city` VALUES ('185', '湘潭市', '18');
INSERT INTO `info_city` VALUES ('186', '衡阳市', '18');
INSERT INTO `info_city` VALUES ('187', '邵阳市', '18');
INSERT INTO `info_city` VALUES ('188', '岳阳市', '18');
INSERT INTO `info_city` VALUES ('189', '常德市', '18');
INSERT INTO `info_city` VALUES ('190', '张家界市', '18');
INSERT INTO `info_city` VALUES ('191', '益阳市', '18');
INSERT INTO `info_city` VALUES ('192', '郴州市', '18');
INSERT INTO `info_city` VALUES ('193', '永州市', '18');
INSERT INTO `info_city` VALUES ('194', '怀化市', '18');
INSERT INTO `info_city` VALUES ('195', '娄底市', '18');
INSERT INTO `info_city` VALUES ('196', '湘西土家族苗族自治州', '18');
INSERT INTO `info_city` VALUES ('197', '广州市', '19');
INSERT INTO `info_city` VALUES ('198', '韶关市', '19');
INSERT INTO `info_city` VALUES ('199', '深圳市', '19');
INSERT INTO `info_city` VALUES ('200', '珠海市', '19');
INSERT INTO `info_city` VALUES ('201', '汕头市', '19');
INSERT INTO `info_city` VALUES ('202', '佛山市', '19');
INSERT INTO `info_city` VALUES ('203', '江门市', '19');
INSERT INTO `info_city` VALUES ('204', '湛江市', '19');
INSERT INTO `info_city` VALUES ('205', '茂名市', '19');
INSERT INTO `info_city` VALUES ('206', '肇庆市', '19');
INSERT INTO `info_city` VALUES ('207', '惠州市', '19');
INSERT INTO `info_city` VALUES ('208', '梅州市', '19');
INSERT INTO `info_city` VALUES ('209', '汕尾市', '19');
INSERT INTO `info_city` VALUES ('210', '河源市', '19');
INSERT INTO `info_city` VALUES ('211', '阳江市', '19');
INSERT INTO `info_city` VALUES ('212', '清远市', '19');
INSERT INTO `info_city` VALUES ('213', '东莞市', '19');
INSERT INTO `info_city` VALUES ('214', '中山市', '19');
INSERT INTO `info_city` VALUES ('215', '潮州市', '19');
INSERT INTO `info_city` VALUES ('216', '揭阳市', '19');
INSERT INTO `info_city` VALUES ('217', '云浮市', '19');
INSERT INTO `info_city` VALUES ('218', '南宁市', '20');
INSERT INTO `info_city` VALUES ('219', '柳州市', '20');
INSERT INTO `info_city` VALUES ('220', '桂林市', '20');
INSERT INTO `info_city` VALUES ('221', '梧州市', '20');
INSERT INTO `info_city` VALUES ('222', '北海市', '20');
INSERT INTO `info_city` VALUES ('223', '防城港市', '20');
INSERT INTO `info_city` VALUES ('224', '钦州市', '20');
INSERT INTO `info_city` VALUES ('225', '贵港市', '20');
INSERT INTO `info_city` VALUES ('226', '玉林市', '20');
INSERT INTO `info_city` VALUES ('227', '百色市', '20');
INSERT INTO `info_city` VALUES ('228', '贺州市', '20');
INSERT INTO `info_city` VALUES ('229', '河池市', '20');
INSERT INTO `info_city` VALUES ('230', '来宾市', '20');
INSERT INTO `info_city` VALUES ('231', '崇左市', '20');
INSERT INTO `info_city` VALUES ('232', '海口市', '21');
INSERT INTO `info_city` VALUES ('233', '三亚市', '21');
INSERT INTO `info_city` VALUES ('234', '重庆市', '22');
INSERT INTO `info_city` VALUES ('235', '成都市', '23');
INSERT INTO `info_city` VALUES ('236', '自贡市', '23');
INSERT INTO `info_city` VALUES ('237', '攀枝花市', '23');
INSERT INTO `info_city` VALUES ('238', '泸州市', '23');
INSERT INTO `info_city` VALUES ('239', '德阳市', '23');
INSERT INTO `info_city` VALUES ('240', '绵阳市', '23');
INSERT INTO `info_city` VALUES ('241', '广元市', '23');
INSERT INTO `info_city` VALUES ('242', '遂宁市', '23');
INSERT INTO `info_city` VALUES ('243', '内江市', '23');
INSERT INTO `info_city` VALUES ('244', '乐山市', '23');
INSERT INTO `info_city` VALUES ('245', '南充市', '23');
INSERT INTO `info_city` VALUES ('246', '眉山市', '23');
INSERT INTO `info_city` VALUES ('247', '宜宾市', '23');
INSERT INTO `info_city` VALUES ('248', '广安市', '23');
INSERT INTO `info_city` VALUES ('249', '达州市', '23');
INSERT INTO `info_city` VALUES ('250', '雅安市', '23');
INSERT INTO `info_city` VALUES ('251', '巴中市', '23');
INSERT INTO `info_city` VALUES ('252', '资阳市', '23');
INSERT INTO `info_city` VALUES ('253', '阿坝藏族羌族自治州', '23');
INSERT INTO `info_city` VALUES ('254', '甘孜藏族自治州', '23');
INSERT INTO `info_city` VALUES ('255', '凉山彝族自治州', '23');
INSERT INTO `info_city` VALUES ('256', '贵阳市', '24');
INSERT INTO `info_city` VALUES ('257', '六盘水市', '24');
INSERT INTO `info_city` VALUES ('258', '遵义市', '24');
INSERT INTO `info_city` VALUES ('259', '安顺市', '24');
INSERT INTO `info_city` VALUES ('260', '铜仁地区', '24');
INSERT INTO `info_city` VALUES ('261', '黔西南布依族苗族自治州', '24');
INSERT INTO `info_city` VALUES ('262', '毕节地区', '24');
INSERT INTO `info_city` VALUES ('263', '黔东南苗族侗族自治州', '24');
INSERT INTO `info_city` VALUES ('264', '黔南布依族苗族自治州', '24');
INSERT INTO `info_city` VALUES ('265', '昆明市', '25');
INSERT INTO `info_city` VALUES ('266', '曲靖市', '25');
INSERT INTO `info_city` VALUES ('267', '玉溪市', '25');
INSERT INTO `info_city` VALUES ('268', '保山市', '25');
INSERT INTO `info_city` VALUES ('269', '昭通市', '25');
INSERT INTO `info_city` VALUES ('270', '丽江市', '25');
INSERT INTO `info_city` VALUES ('271', '思茅市', '25');
INSERT INTO `info_city` VALUES ('272', '临沧市', '25');
INSERT INTO `info_city` VALUES ('273', '楚雄彝族自治州', '25');
INSERT INTO `info_city` VALUES ('274', '红河哈尼族彝族自治州', '25');
INSERT INTO `info_city` VALUES ('275', '文山壮族苗族自治州', '25');
INSERT INTO `info_city` VALUES ('276', '西双版纳傣族自治州', '25');
INSERT INTO `info_city` VALUES ('277', '大理白族自治州', '25');
INSERT INTO `info_city` VALUES ('278', '德宏傣族景颇族自治州', '25');
INSERT INTO `info_city` VALUES ('279', '怒江傈僳族自治州', '25');
INSERT INTO `info_city` VALUES ('280', '迪庆藏族自治州', '25');
INSERT INTO `info_city` VALUES ('281', '拉萨市', '26');
INSERT INTO `info_city` VALUES ('282', '昌都地区', '26');
INSERT INTO `info_city` VALUES ('283', '山南地区', '26');
INSERT INTO `info_city` VALUES ('284', '日喀则地区', '26');
INSERT INTO `info_city` VALUES ('285', '那曲地区', '26');
INSERT INTO `info_city` VALUES ('286', '阿里地区', '26');
INSERT INTO `info_city` VALUES ('287', '林芝地区', '26');
INSERT INTO `info_city` VALUES ('288', '西安市', '27');
INSERT INTO `info_city` VALUES ('289', '铜川市', '27');
INSERT INTO `info_city` VALUES ('290', '宝鸡市', '27');
INSERT INTO `info_city` VALUES ('291', '咸阳市', '27');
INSERT INTO `info_city` VALUES ('292', '渭南市', '27');
INSERT INTO `info_city` VALUES ('293', '延安市', '27');
INSERT INTO `info_city` VALUES ('294', '汉中市', '27');
INSERT INTO `info_city` VALUES ('295', '榆林市', '27');
INSERT INTO `info_city` VALUES ('296', '安康市', '27');
INSERT INTO `info_city` VALUES ('297', '商洛市', '27');
INSERT INTO `info_city` VALUES ('298', '兰州市', '28');
INSERT INTO `info_city` VALUES ('299', '嘉峪关市', '28');
INSERT INTO `info_city` VALUES ('300', '金昌市', '28');
INSERT INTO `info_city` VALUES ('301', '白银市', '28');
INSERT INTO `info_city` VALUES ('302', '天水市', '28');
INSERT INTO `info_city` VALUES ('303', '武威市', '28');
INSERT INTO `info_city` VALUES ('304', '张掖市', '28');
INSERT INTO `info_city` VALUES ('305', '平凉市', '28');
INSERT INTO `info_city` VALUES ('306', '酒泉市', '28');
INSERT INTO `info_city` VALUES ('307', '庆阳市', '28');
INSERT INTO `info_city` VALUES ('308', '定西市', '28');
INSERT INTO `info_city` VALUES ('309', '陇南市', '28');
INSERT INTO `info_city` VALUES ('310', '临夏回族自治州', '28');
INSERT INTO `info_city` VALUES ('311', '甘南藏族自治州', '28');
INSERT INTO `info_city` VALUES ('312', '西宁市', '29');
INSERT INTO `info_city` VALUES ('313', '海东地区', '29');
INSERT INTO `info_city` VALUES ('314', '海北藏族自治州', '29');
INSERT INTO `info_city` VALUES ('315', '黄南藏族自治州', '29');
INSERT INTO `info_city` VALUES ('316', '海南藏族自治州', '29');
INSERT INTO `info_city` VALUES ('317', '果洛藏族自治州', '29');
INSERT INTO `info_city` VALUES ('318', '玉树藏族自治州', '29');
INSERT INTO `info_city` VALUES ('319', '海西蒙古族藏族自治州', '29');
INSERT INTO `info_city` VALUES ('320', '银川市', '30');
INSERT INTO `info_city` VALUES ('321', '石嘴山市', '30');
INSERT INTO `info_city` VALUES ('322', '吴忠市', '30');
INSERT INTO `info_city` VALUES ('323', '固原市', '30');
INSERT INTO `info_city` VALUES ('324', '中卫市', '30');
INSERT INTO `info_city` VALUES ('325', '乌鲁木齐市', '31');
INSERT INTO `info_city` VALUES ('326', '克拉玛依市', '31');
INSERT INTO `info_city` VALUES ('327', '吐鲁番地区', '31');
INSERT INTO `info_city` VALUES ('328', '哈密地区', '31');
INSERT INTO `info_city` VALUES ('329', '昌吉回族自治州', '31');
INSERT INTO `info_city` VALUES ('330', '博尔塔拉蒙古自治州', '31');
INSERT INTO `info_city` VALUES ('331', '巴音郭楞蒙古自治州', '31');
INSERT INTO `info_city` VALUES ('332', '阿克苏地区', '31');
INSERT INTO `info_city` VALUES ('333', '克孜勒苏柯尔克孜自治州', '31');
INSERT INTO `info_city` VALUES ('334', '喀什地区', '31');
INSERT INTO `info_city` VALUES ('335', '和田地区', '31');
INSERT INTO `info_city` VALUES ('336', '伊犁哈萨克自治州', '31');
INSERT INTO `info_city` VALUES ('337', '塔城地区', '31');
INSERT INTO `info_city` VALUES ('338', '阿勒泰地区', '31');
INSERT INTO `info_city` VALUES ('339', '石河子市', '31');
INSERT INTO `info_city` VALUES ('340', '阿拉尔市', '31');
INSERT INTO `info_city` VALUES ('341', '图木舒克市', '31');
INSERT INTO `info_city` VALUES ('342', '五家渠市', '31');
INSERT INTO `info_city` VALUES ('343', '香港特别行政区', '32');
INSERT INTO `info_city` VALUES ('344', '澳门特别行政区', '33');
INSERT INTO `info_city` VALUES ('345', '台湾省', '34');
INSERT INTO `info_city` VALUES ('401', '北京市', '1');
INSERT INTO `info_city` VALUES ('402', '天津市', '2');
INSERT INTO `info_city` VALUES ('403', '石家庄市', '3');
INSERT INTO `info_city` VALUES ('404', '唐山市', '3');
INSERT INTO `info_city` VALUES ('405', '秦皇岛市', '3');
INSERT INTO `info_city` VALUES ('406', '邯郸市', '3');
INSERT INTO `info_city` VALUES ('407', '邢台市', '3');
INSERT INTO `info_city` VALUES ('408', '保定市', '3');
INSERT INTO `info_city` VALUES ('409', '张家口市', '3');
INSERT INTO `info_city` VALUES ('410', '承德市', '3');
INSERT INTO `info_city` VALUES ('411', '沧州市', '3');
INSERT INTO `info_city` VALUES ('412', '廊坊市', '3');
INSERT INTO `info_city` VALUES ('413', '衡水市', '3');
INSERT INTO `info_city` VALUES ('414', '太原市', '4');
INSERT INTO `info_city` VALUES ('415', '大同市', '4');
INSERT INTO `info_city` VALUES ('416', '阳泉市', '4');
INSERT INTO `info_city` VALUES ('417', '长治市', '4');
INSERT INTO `info_city` VALUES ('418', '晋城市', '4');
INSERT INTO `info_city` VALUES ('419', '朔州市', '4');
INSERT INTO `info_city` VALUES ('420', '晋中市', '4');
INSERT INTO `info_city` VALUES ('421', '运城市', '4');
INSERT INTO `info_city` VALUES ('422', '忻州市', '4');
INSERT INTO `info_city` VALUES ('423', '临汾市', '4');
INSERT INTO `info_city` VALUES ('424', '吕梁市', '4');
INSERT INTO `info_city` VALUES ('425', '呼和浩特市', '5');
INSERT INTO `info_city` VALUES ('426', '包头市', '5');
INSERT INTO `info_city` VALUES ('427', '乌海市', '5');
INSERT INTO `info_city` VALUES ('428', '赤峰市', '5');
INSERT INTO `info_city` VALUES ('429', '通辽市', '5');
INSERT INTO `info_city` VALUES ('430', '鄂尔多斯市', '5');
INSERT INTO `info_city` VALUES ('431', '呼伦贝尔市', '5');
INSERT INTO `info_city` VALUES ('432', '巴彦淖尔市', '5');
INSERT INTO `info_city` VALUES ('433', '乌兰察布市', '5');
INSERT INTO `info_city` VALUES ('434', '兴安盟', '5');
INSERT INTO `info_city` VALUES ('435', '锡林郭勒盟', '5');
INSERT INTO `info_city` VALUES ('436', '阿拉善盟', '5');
INSERT INTO `info_city` VALUES ('437', '沈阳市', '6');
INSERT INTO `info_city` VALUES ('438', '大连市', '6');
INSERT INTO `info_city` VALUES ('439', '鞍山市', '6');

-- ----------------------------
-- Table structure for `info_classify`
-- ----------------------------
DROP TABLE IF EXISTS `info_classify`;
CREATE TABLE `info_classify` (
  `id` varchar(36) NOT NULL,
  `name` varchar(500) NOT NULL,
  `code` varchar(100) NOT NULL,
  `show_index` int(2) DEFAULT NULL,
  `show_order` int(2) DEFAULT NULL,
  `col_index` int(2) DEFAULT NULL,
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of info_classify
-- ----------------------------
INSERT INTO `info_classify` VALUES ('1', '粮食', 'liangshi', '1', '1', '1', '2013-10-14 09:34:11');
INSERT INTO `info_classify` VALUES ('10', '植物油', 'zhiwuyou', '1', '2', '1', '2013-10-14 09:34:12');
INSERT INTO `info_classify` VALUES ('11', '水产', 'shuichan', '1', '3', '1', '2013-10-14 09:34:13');
INSERT INTO `info_classify` VALUES ('12', '农资', 'nongzi', '1', '4', '2', '2013-10-14 09:34:14');
INSERT INTO `info_classify` VALUES ('13', '畜禽', 'chuqin', '1', '5', '2', '2013-10-14 09:34:17');
INSERT INTO `info_classify` VALUES ('14', '其他', 'qita', '1', '16', '6', '2013-10-14 22:23:07');
INSERT INTO `info_classify` VALUES ('2', '油料', 'youliao', '1', '7', '5', '2013-10-14 14:59:36');
INSERT INTO `info_classify` VALUES ('3', '棉麻丝', 'mianmasi', '1', '8', '3', '2013-10-14 09:34:22');
INSERT INTO `info_classify` VALUES ('4', '糖烟茶', 'tangyancha', '1', '9', '3', '2013-10-14 09:34:23');
INSERT INTO `info_classify` VALUES ('5', '蔬菜', 'shucai', '1', '10', '3', '2013-10-14 09:34:24');
INSERT INTO `info_classify` VALUES ('6', '果品', 'guoping', '1', '6', '5', '2013-10-14 22:22:53');
INSERT INTO `info_classify` VALUES ('7', '园林花草', 'yuanlinhuahui', '1', '12', '4', '2013-10-14 09:34:26');
INSERT INTO `info_classify` VALUES ('8', '林产品', 'linchanping', '1', '13', '4', '2013-10-14 09:34:27');
INSERT INTO `info_classify` VALUES ('9', '药材', 'yaocai', '1', '14', '4', '2013-10-14 09:34:28');

-- ----------------------------
-- Table structure for `info_detail`
-- ----------------------------
DROP TABLE IF EXISTS `info_detail`;
CREATE TABLE `info_detail` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `title` text NOT NULL,
  `content` text NOT NULL,
  `city_id` varchar(36) NOT NULL,
  `price` float NOT NULL,
  `sub_classify_id` varchar(36) NOT NULL,
  `contact_user` varchar(100) NOT NULL,
  `contact_info` varchar(200) NOT NULL,
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `gmt_modify` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of info_detail
-- ----------------------------
INSERT INTO `info_detail` VALUES ('2ebca9d5-0f18-4d3b-8081-0f10d7d79a13', '1', '121efdsafas', 'fdafdasasasasasasasas', '414', '11', '102', '121', '32432', '2013-10-14 19:28:41', '2013-10-14 19:28:41');
INSERT INTO `info_detail` VALUES ('63a71772-0e4d-4db2-883d-36d0510cef67', '1', '????', '????????????????????', '401', '11', '101', 'test', '13588754574', '2013-10-14 19:23:43', '2013-10-14 19:23:43');
INSERT INTO `info_detail` VALUES ('c3a92229-1125-4dff-a3f0-4fdc0e03db7f', '1', 'a港港版全新手机，品质保证您值得拥有，现货发售优惠', '港港版全新手机，品质保证您值得拥有，现货发售优惠', '425', '111', '103', '11', '11', '2013-10-14 19:36:19', '2013-10-14 19:36:19');
INSERT INTO `info_detail` VALUES ('ce25c0fb-074c-4791-8081-cac28e3cfbb1', '1', '121efdsafas', 'fdafdasasasasasasasas', '414', '11', '102', '121', '32432', '2013-10-14 19:29:11', '2013-10-14 19:29:11');
INSERT INTO `info_detail` VALUES ('ef9ccb76-871d-499f-a64e-d5f538eed610', '1', '????', '????????????????????', '401', '11', '101', 'test', '13588754574', '2013-10-14 19:26:25', '2013-10-14 19:26:25');

-- ----------------------------
-- Table structure for `info_msg`
-- ----------------------------
DROP TABLE IF EXISTS `info_msg`;
CREATE TABLE `info_msg` (
  `id` varchar(36) NOT NULL,
  `title` varchar(300) DEFAULT NULL,
  `content` text NOT NULL,
  `contact_name` varchar(20) NOT NULL,
  `contact_info` varchar(100) NOT NULL,
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of info_msg
-- ----------------------------
INSERT INTO `info_msg` VALUES ('1', '关于', '留言内容放大风大是辅导课哇哦i官方特热', '吴先生', '联系方式', '2012-12-23 09:24:12');
INSERT INTO `info_msg` VALUES ('2', '短发放大', ' 短发放大费大幅降低阿婆分哇放到i皮肤打破分哇飞啊饿挖', '女士', '联系方式', '2012-12-23 09:24:14');
INSERT INTO `info_msg` VALUES ('a12d0a8e-9912-4a5e-a02c-e7ecb12f3b01', null, '我的问题我的问题', '好先生', '爱爱爱', '2012-12-23 09:27:25');

-- ----------------------------
-- Table structure for `info_msg_reply`
-- ----------------------------
DROP TABLE IF EXISTS `info_msg_reply`;
CREATE TABLE `info_msg_reply` (
  `id` varchar(36) NOT NULL,
  `content` text NOT NULL,
  `msg_id` varchar(36) NOT NULL,
  `gmt_created` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of info_msg_reply
-- ----------------------------
INSERT INTO `info_msg_reply` VALUES ('1', '回复吴先生', '1', '2012-12-22 22:59:16');
INSERT INTO `info_msg_reply` VALUES ('2', '回复吴女士', '2', '2012-12-22 22:59:35');

-- ----------------------------
-- Table structure for `info_sub_classify`
-- ----------------------------
DROP TABLE IF EXISTS `info_sub_classify`;
CREATE TABLE `info_sub_classify` (
  `id` varchar(36) NOT NULL,
  `name` varchar(500) NOT NULL,
  `code` varchar(100) NOT NULL,
  `classify_id` varchar(36) NOT NULL,
  `show_index` int(11) DEFAULT NULL,
  `show_order` int(11) DEFAULT NULL,
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of info_sub_classify
-- ----------------------------
INSERT INTO `info_sub_classify` VALUES ('1001', '食用植物油', 'shiyong', '10', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('1002', '非食用植物油', 'feishiyong', '10', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('101', '谷物', 'guwu', '1', null, null, '2013-10-11 10:49:49');
INSERT INTO `info_sub_classify` VALUES ('102', '豆类', 'doulei', '1', null, null, '2013-10-11 10:49:53');
INSERT INTO `info_sub_classify` VALUES ('103', '薯类', 'shulei', '1', null, null, '2013-10-11 10:50:07');
INSERT INTO `info_sub_classify` VALUES ('104', '其他粮食', 'qita', '1', null, null, '2013-10-11 11:18:48');
INSERT INTO `info_sub_classify` VALUES ('1101', '淡水鱼', 'danshuiyu', '11', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('1102', '海水鱼', 'haishuiyu', '11', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('1103', '虾蟹螺贝', 'xiaxie', '11', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('1104', '海植物', 'haizhiwu', '11', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('1105', '水产加工', 'shuichanjiagong', '11', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('1106', '其它水产', 'qitashuichan', '11', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('1201', '农药肥料', 'nongyefeiliao', '12', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('1202', '饲料', 'siliao', '12', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('1203', '农机', 'nongji', '12', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('1204', '农业设施', 'nongyeshesi', '12', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('1205', '其它农资', 'qita', '12', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('1301', '家畜家禽', 'jiachujiaqin', '13', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('1302', '动物皮毛', 'dongwupimao', '13', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('1303', '加工副产品', 'jiagongfuchanp', '13', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('1304', '其它畜禽', 'qitachuqin', '13', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('1401', '其它农产品', 'qitanongchanp', '14', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('201', '食用油籽', 'shiyongyouzi', '2', null, null, '2013-10-11 11:18:42');
INSERT INTO `info_sub_classify` VALUES ('202', '非食用油籽', 'feishiyongyouzi', '2', null, null, '2013-10-11 10:51:21');
INSERT INTO `info_sub_classify` VALUES ('301', '棉花及制品', 'mianhuazhipin', '3', null, null, '2013-10-11 11:18:51');
INSERT INTO `info_sub_classify` VALUES ('302', '麻类', 'malei', '3', null, null, '2013-10-11 11:18:54');
INSERT INTO `info_sub_classify` VALUES ('303', '蚕丝及制品', 'cansizhip', '3', null, null, '2013-10-11 11:18:57');
INSERT INTO `info_sub_classify` VALUES ('304', '其他棉麻丝', 'qita', '3', null, null, '2013-10-11 11:19:00');
INSERT INTO `info_sub_classify` VALUES ('401', '糖料', 'tangliao', '4', null, null, '2013-10-11 11:19:04');
INSERT INTO `info_sub_classify` VALUES ('402', '烟叶及制品', 'yanyezhipin', '4', null, null, '2013-10-11 11:19:07');
INSERT INTO `info_sub_classify` VALUES ('403', '茶叶', 'chaye', '4', null, null, '2013-10-11 19:45:22');
INSERT INTO `info_sub_classify` VALUES ('404', '其他糖烟茶', 'qita', '4', null, null, '2013-10-11 19:45:36');
INSERT INTO `info_sub_classify` VALUES ('501', '叶菜类', 'yecailei', '5', null, null, '2013-10-11 19:45:43');
INSERT INTO `info_sub_classify` VALUES ('502', '花类', 'hualei', '5', null, null, '2013-10-11 19:45:46');
INSERT INTO `info_sub_classify` VALUES ('503', '瓜果类', 'guaguo', '5', null, null, '2013-10-11 19:45:51');
INSERT INTO `info_sub_classify` VALUES ('504', '食用菌类', 'qitashucai', '5', null, null, '2013-10-11 19:45:55');
INSERT INTO `info_sub_classify` VALUES ('505', '根茎类', 'qitashucai', '5', null, null, '2013-10-11 19:45:55');
INSERT INTO `info_sub_classify` VALUES ('506', '其他蔬菜', 'qitashucai', '5', null, null, '2013-10-11 19:45:55');
INSERT INTO `info_sub_classify` VALUES ('601', '仁果', 'renguo', '6', null, null, '2013-10-11 19:45:58');
INSERT INTO `info_sub_classify` VALUES ('602', '坚果类', 'jianguo', '6', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('603', '热带及亚热带水果', 'redai', '6', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('604', '浆果类', 'jianguo', '6', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('605', '其它水果', 'qitashuiguo', '6', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('701', '多浆植物', 'duojiang', '7', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('702', '仙人掌类', 'xianren', '7', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('703', '岩生花卉', 'yansheng', '7', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('704', '观赏植物', 'guansang', '7', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('705', '其它植物', 'qitazhiwu', '7', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('801', '原木', 'yuanmu', '8', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('802', '树脂及胶类', 'shuzhi', '8', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('901', '花类', 'hualei', '9', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('902', '茎木类', 'jingmulei', '9', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('903', '果实类', 'guoshilei', '9', null, null, '2013-10-11 19:46:02');
INSERT INTO `info_sub_classify` VALUES ('904', '其它药材', 'qitayaocai', '9', null, null, '2013-10-11 19:46:02');

-- ----------------------------
-- Table structure for `info_user`
-- ----------------------------
DROP TABLE IF EXISTS `info_user`;
CREATE TABLE `info_user` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `user_name` varchar(30) DEFAULT NULL,
  `password` varchar(36) NOT NULL,
  `email` varchar(36) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `qq` varchar(30) DEFAULT NULL,
  `sex` int(2) DEFAULT NULL,
  `city_id` varchar(36) DEFAULT NULL,
  `status` int(2) NOT NULL,
  `code` varchar(36) DEFAULT NULL,
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of info_user
-- ----------------------------
INSERT INTO `info_user` VALUES ('73f49387-166e-435d-a1ce-a57641c766d1', '1234', null, '7b2df4547e2b1769', 'foodoon@qq.com', null, null, null, null, '3', '4848820f-748d-439c-a71f-b3b36414c86f', '2013-10-14 14:35:21');
