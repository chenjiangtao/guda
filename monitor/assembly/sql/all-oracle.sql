
DROP TABLE  m_agent;

CREATE TABLE m_agent(
  id varchar2(36) NOT NULL,
  ip varchar2(16) NOT NULL,
  k varchar2(200) NOT NULL,
  order_num number(5) DEFAULT NULL,
  host varchar2(100) DEFAULT NULL,
  value_type number(1) DEFAULT NULL,
  gmt_created timestamp  DEFAULT NULL,
  comment_info varchar2(50) DEFAULT NULL);
alter table m_agent add constraint m_agent_pk primary key(id);


DROP TABLE   m_sys;
CREATE TABLE m_sys (
  id varchar2(36) NOT NULL,
  ip varchar2(20) NOT NULL,
  k varchar2(300) NOT NULL,
  val float NOT NULL,
  val_text clob ,
  value_type number(11) NOT NULL,
  is_warn number NOT NULL,
  gmt_created timestamp DEFAULT to_timestamp('1900-01-01 00:00:00','YYYY-MM-DD HH24:MI:SS') NOT NULL 
);
alter table m_sys add constraint m_sys_pk primary key(id);


DROP TABLE  m_user;
CREATE TABLE m_user (
  id varchar2(36) DEFAULT '' NOT NULL ,
  user_id varchar2(36) DEFAULT NULL,
  password varchar2(36) DEFAULT NULL,
  status varchar2(1) DEFAULT NULL
) ;
alter table m_user add constraint m_user_pk primary key(id);


-- ----------------------------
-- Records of m_user
-- ----------------------------
INSERT INTO m_user VALUES ('1', 'test', 'afa9c01e4457dd59', '1');
INSERT INTO m_user VALUES ('2', 'admin', 'afa9c01e4457dd59', '1');

-- ----------------------------
-- Table structure for m_warn
-- ----------------------------
DROP TABLE m_warn;
CREATE TABLE m_warn (
  id varchar2(36) NOT NULL,
  ip varchar2(20) NOT NULL,
  k varchar2(200) NOT NULL,
  cond varchar2(5) NOT NULL,
  val float DEFAULT NULL,
  email varchar2(500) DEFAULT NULL,
  phone varchar2(500) DEFAULT NULL,
  warn varchar2(30) DEFAULT NULL,
  gmt_created timestamp  DEFAULT NULL
);
alter table m_warn add constraint m_warn_pk primary key(id);
