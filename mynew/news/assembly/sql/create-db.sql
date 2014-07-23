DROP DATABASE IF EXISTS `news`;
CREATE DATABASE `news`;
use `news`;


delete from mysql.user where User = 'news_user';
grant select,update,delete,insert on `news`.* to 'news_user'@'%' identified by 'news_pwd';
grant select,update,delete,insert on `news`.* to 'news_user'@'localhost' identified by 'news_pwd';
flush privileges;