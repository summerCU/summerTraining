/*
Navicat MySQL Data Transfer

Source Server         : CU
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : b508test

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2019-07-13 13:28:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `appointment`
-- ----------------------------
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment` (
  `book_id` bigint(20) NOT NULL COMMENT '图书ID',
  `student_id` bigint(20) NOT NULL COMMENT '学号',
  `appoint_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '预约时间',
  PRIMARY KEY (`book_id`,`student_id`),
  KEY `idx_appoint_time` (`appoint_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预约图书表';

-- ----------------------------
-- Records of appointment
-- ----------------------------
INSERT INTO `appointment` VALUES ('1003', '9', '2019-07-09 16:25:28');
INSERT INTO `appointment` VALUES ('1004', '9', '2019-07-09 16:27:05');
INSERT INTO `appointment` VALUES ('1002', '8', '2019-07-09 16:39:05');
INSERT INTO `appointment` VALUES ('1003', '4', '2019-07-10 17:12:55');
INSERT INTO `appointment` VALUES ('1000', '4', '2019-07-10 17:13:37');

-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `book_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '图书ID',
  `name` varchar(100) NOT NULL COMMENT '图书名称',
  `number` int(11) NOT NULL COMMENT '馆藏数量',
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1005 DEFAULT CHARSET=utf8 COMMENT='图书表';

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1000', 'Java程序设计', '9');
INSERT INTO `book` VALUES ('1002', 'JAVA编程思想', '12');
INSERT INTO `book` VALUES ('1003', '机器学习', '13');
INSERT INTO `book` VALUES ('1004', 'spring', '10');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) DEFAULT NULL,
  `passWord` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('4', '张三老铁', '1');
INSERT INTO `user` VALUES ('5', 'admin', '1');
INSERT INTO `user` VALUES ('7', 'admin2', 'admin2');
INSERT INTO `user` VALUES ('8', '123', '123');
INSERT INTO `user` VALUES ('9', '12', '12');
INSERT INTO `user` VALUES ('11', 'qw', 'qw');
INSERT INTO `user` VALUES ('14', '1', '1');
