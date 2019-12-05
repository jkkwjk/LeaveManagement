/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : leave

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 05/12/2019 22:49:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth
-- ----------------------------
DROP TABLE IF EXISTS `auth`;
CREATE TABLE `auth`  (
  `id` int(1) NOT NULL COMMENT '权限ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '权限对应的名称',
  `key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '键',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '值'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth
-- ----------------------------
INSERT INTO `auth` VALUES (0, '学生', 'router', '个人信息');
INSERT INTO `auth` VALUES (0, '学生', 'router', '回收站');
INSERT INTO `auth` VALUES (0, '学生', 'router', '请假申请');
INSERT INTO `auth` VALUES (1, '辅导员', 'router', '个人信息');
INSERT INTO `auth` VALUES (1, '辅导员', 'router', '我要归档');
INSERT INTO `auth` VALUES (1, '辅导员', 'router', '请假审批');
INSERT INTO `auth` VALUES (0, '学生', 'router', '我要归档');
INSERT INTO `auth` VALUES (2, '院领导', 'router', '请假审批');
INSERT INTO `auth` VALUES (2, '院领导', 'router', '我要归档');
INSERT INTO `auth` VALUES (2, '院领导', 'router', '导入数据');
INSERT INTO `auth` VALUES (2, '院领导', 'router', '教师管理');
INSERT INTO `auth` VALUES (2, '院领导', 'router', '学生管理');
INSERT INTO `auth` VALUES (2, '院领导', 'router', '个人信息');
INSERT INTO `auth` VALUES (3, '教师', 'router', '请假查看');
INSERT INTO `auth` VALUES (3, '教师', 'router', '课程归档');
INSERT INTO `auth` VALUES (3, '教师', 'router', '个人信息');
INSERT INTO `auth` VALUES (1, '辅导员', 'router', '概要查询');
INSERT INTO `auth` VALUES (2, '院领导', 'router', '概要查询');

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `id` int(11) NOT NULL COMMENT '班级号',
  `counselor_id` int(11) NOT NULL COMMENT '辅导员ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for leave_list
-- ----------------------------
DROP TABLE IF EXISTS `leave_list`;
CREATE TABLE `leave_list`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '请假单ID',
  `send_time` int(15) NULL DEFAULT NULL COMMENT '发送时间,未发送为null',
  `start_time` int(15) NOT NULL COMMENT '请假开始时间',
  `end_time` int(15) NOT NULL COMMENT '请假结束时间',
  `stu_id` int(11) NOT NULL COMMENT '学生ID',
  `del_status` int(1) NOT NULL COMMENT '是否已经放入回收站 回收站1 ',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lesson
-- ----------------------------
DROP TABLE IF EXISTS `lesson`;
CREATE TABLE `lesson`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '课程编号',
  `team` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '开设课程的学期',
  `teacher_id` int(11) NULL DEFAULT NULL COMMENT '教该课程老师的ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '课程对外显示的名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for student_info
-- ----------------------------
DROP TABLE IF EXISTS `student_info`;
CREATE TABLE `student_info`  (
  `id` int(11) NOT NULL COMMENT '学生ID',
  `class_id` int(11) NOT NULL COMMENT '学生所在班级ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for student_lesson
-- ----------------------------
DROP TABLE IF EXISTS `student_lesson`;
CREATE TABLE `student_lesson`  (
  `student_id` int(11) NOT NULL COMMENT '学生ID',
  `lesson_id` int(11) NOT NULL COMMENT '课程ID',
  PRIMARY KEY (`student_id`, `lesson_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL COMMENT '用户唯一ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户密码',
  `type` int(1) NOT NULL COMMENT '用户的类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '金凯凯', '1', 0);
INSERT INTO `user` VALUES (2, '辅导员', '2', 1);
INSERT INTO `user` VALUES (3, '院领导', '3', 2);
INSERT INTO `user` VALUES (4, '教师', '4', 3);

SET FOREIGN_KEY_CHECKS = 1;
