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

 Date: 16/12/2019 12:40:45
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
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES (10022, 2);

-- ----------------------------
-- Table structure for college_leave_list
-- ----------------------------
DROP TABLE IF EXISTS `college_leave_list`;
CREATE TABLE `college_leave_list`  (
  `id` int(11) NOT NULL COMMENT '请假单ID',
  `looked` int(11) NOT NULL COMMENT '院领导是否查看 未看0',
  `allow` int(1) NULL DEFAULT NULL COMMENT '院领导是否同意 拒绝0 同意1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of college_leave_list
-- ----------------------------
INSERT INTO `college_leave_list` VALUES (1, 1, NULL);
INSERT INTO `college_leave_list` VALUES (2, 1, 0);
INSERT INTO `college_leave_list` VALUES (19, 1, 1);
INSERT INTO `college_leave_list` VALUES (23, 1, 0);
INSERT INTO `college_leave_list` VALUES (25, 1, 1);
INSERT INTO `college_leave_list` VALUES (26, 1, 1);
INSERT INTO `college_leave_list` VALUES (27, 1, 1);
INSERT INTO `college_leave_list` VALUES (28, 1, 1);
INSERT INTO `college_leave_list` VALUES (29, 1, NULL);
INSERT INTO `college_leave_list` VALUES (30, 1, NULL);
INSERT INTO `college_leave_list` VALUES (32, 1, 1);
INSERT INTO `college_leave_list` VALUES (34, 1, NULL);
INSERT INTO `college_leave_list` VALUES (36, 1, NULL);
INSERT INTO `college_leave_list` VALUES (39, 1, NULL);

-- ----------------------------
-- Table structure for counselor_leave_list
-- ----------------------------
DROP TABLE IF EXISTS `counselor_leave_list`;
CREATE TABLE `counselor_leave_list`  (
  `id` int(11) NOT NULL COMMENT '请假单ID',
  `looked` int(1) NOT NULL DEFAULT 0 COMMENT '辅导员是否查看 未看0',
  `allow` int(1) NULL DEFAULT NULL COMMENT '辅导员是否同意 拒绝0 同意1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of counselor_leave_list
-- ----------------------------
INSERT INTO `counselor_leave_list` VALUES (1, 1, 1);
INSERT INTO `counselor_leave_list` VALUES (2, 1, 1);
INSERT INTO `counselor_leave_list` VALUES (18, 1, 0);
INSERT INTO `counselor_leave_list` VALUES (19, 1, 1);
INSERT INTO `counselor_leave_list` VALUES (20, 1, 0);
INSERT INTO `counselor_leave_list` VALUES (21, 1, 0);
INSERT INTO `counselor_leave_list` VALUES (22, 1, NULL);
INSERT INTO `counselor_leave_list` VALUES (23, 1, 1);
INSERT INTO `counselor_leave_list` VALUES (24, 1, 0);
INSERT INTO `counselor_leave_list` VALUES (25, 1, 1);
INSERT INTO `counselor_leave_list` VALUES (26, 1, 1);
INSERT INTO `counselor_leave_list` VALUES (27, 1, 1);
INSERT INTO `counselor_leave_list` VALUES (28, 1, 1);
INSERT INTO `counselor_leave_list` VALUES (29, 1, 1);
INSERT INTO `counselor_leave_list` VALUES (30, 1, 1);
INSERT INTO `counselor_leave_list` VALUES (32, 1, 1);
INSERT INTO `counselor_leave_list` VALUES (34, 1, 1);
INSERT INTO `counselor_leave_list` VALUES (36, 1, 1);
INSERT INTO `counselor_leave_list` VALUES (37, 1, NULL);
INSERT INTO `counselor_leave_list` VALUES (38, 1, NULL);
INSERT INTO `counselor_leave_list` VALUES (39, 1, 1);

-- ----------------------------
-- Table structure for leave_list
-- ----------------------------
DROP TABLE IF EXISTS `leave_list`;
CREATE TABLE `leave_list`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '请假单ID',
  `send_time` bigint(20) NULL DEFAULT NULL COMMENT '发送时间,未发送为null',
  `start_time` bigint(20) NOT NULL COMMENT '请假开始时间',
  `end_time` bigint(20) NOT NULL COMMENT '请假结束时间',
  `stu_id` int(11) NOT NULL COMMENT '学生ID',
  `del_status` int(1) NOT NULL COMMENT '是否已经放入回收站 回收站1 ',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '请假类型',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '请假具体原因',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '0未发送 1等待 2同意 3拒绝',
  `team` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '学期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of leave_list
-- ----------------------------
INSERT INTO `leave_list` VALUES (1, 1576146497756, 1576425600000, 1576598400000, 1, 0, '公假', 'new', 1, '2019-2020-1');
INSERT INTO `leave_list` VALUES (2, 1576146192568, 1575993600000, 1576166400000, 1, 0, '事假', '我哈偶来', 3, '2019-2020-1');
INSERT INTO `leave_list` VALUES (18, 1575960537901, 1575993600000, 1576080000000, 1, 0, '事假', '你后', 3, '2019-2020-1');
INSERT INTO `leave_list` VALUES (19, 1575991370547, 1575993600000, 1576080000000, 1, 0, '事假', '发农行', 2, '2019-2020-1');
INSERT INTO `leave_list` VALUES (20, 1575991372511, 1576080000000, 1576166400000, 1, 0, '事假', '发送', 3, '2019-2020-1');
INSERT INTO `leave_list` VALUES (21, 1575991374017, 1576080000000, 1576166400000, 1, 0, '事假', '发送', 3, '2019-2020-1');
INSERT INTO `leave_list` VALUES (22, 1575991375386, 1577376000000, 1577462400000, 1, 0, '公假', '发送s', 1, '2018-2019-1');
INSERT INTO `leave_list` VALUES (23, 1576146057928, 1576080000000, 1576166400000, 1, 0, '病假', '123', 3, '2019-2020-1');
INSERT INTO `leave_list` VALUES (24, 1576145906040, 1576252800000, 1576857600000, 1, 0, '公假', '123', 3, '2019-2020-1');
INSERT INTO `leave_list` VALUES (25, 1576152442342, 1576650960000, 1577253780000, 1, 0, '事假', '测试_星期三上课时间到星期四', 2, '2019-2020-1');
INSERT INTO `leave_list` VALUES (26, 1576152474907, 1576857600000, 1576944000000, 1, 0, '事假', '测试_没有课', 2, '2019-2020-1');
INSERT INTO `leave_list` VALUES (27, 1576152533509, 1576512000000, 1576648800000, 1, 0, '病假', '测试_无课到优课', 2, '2019-2020-1');
INSERT INTO `leave_list` VALUES (28, 1576204160886, 1576252800000, 1577462400000, 1, 0, '事假', '重构后测试11', 2, '2019-2020-1');
INSERT INTO `leave_list` VALUES (29, 1576224409226, 1576252800000, 1576771200000, 1, 0, '事假', '测试_进度', 1, '2019-2020-1');
INSERT INTO `leave_list` VALUES (30, 1576239273908, 1576684800000, 1576771200000, 1, 0, '事假', '未发送', 1, '2019-2020-1');
INSERT INTO `leave_list` VALUES (32, 1576296530834, 1604160000000, 1604419200000, 1, 0, '公假', '测试_微信2_20201101-20201104', 2, '2019-2020-1');
INSERT INTO `leave_list` VALUES (33, NULL, 1576339200000, 1576425600000, 1, 0, '事假', '测试_不发送', 0, '2019-2020-1');
INSERT INTO `leave_list` VALUES (34, 1576383741958, 1576425600000, 1576512000000, 55, 0, '事假', '测试_第二个学生', 1, '2019-2020-1');
INSERT INTO `leave_list` VALUES (35, NULL, 1577808000000, 1577894400000, 55, 0, '事假', '测试_第二个学生', 0, '2019-2020-1');
INSERT INTO `leave_list` VALUES (36, 1576414064743, 1576425600000, 1576598400000, 1, 0, '事假', '测试_邮箱', 1, '2019-2020-1');
INSERT INTO `leave_list` VALUES (37, 1576414813293, 1576425600000, 1576512000000, 1, 0, '事假', '测试_邮件异常处理', 1, '2019-2020-1');
INSERT INTO `leave_list` VALUES (38, 1576414896957, 1577030400000, 1577203200000, 55, 0, '事假', '测试_邮件', 1, '2019-2020-1');
INSERT INTO `leave_list` VALUES (39, 1576415122693, 1578240000000, 1578412800000, 55, 0, '事假', '测试_邮件异常处理', 1, '2019-2020-1');

-- ----------------------------
-- Table structure for lesson
-- ----------------------------
DROP TABLE IF EXISTS `lesson`;
CREATE TABLE `lesson`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '课程编号',
  `team` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '开设课程的学期',
  `teacher_id` int(11) NULL DEFAULT NULL COMMENT '教该课程老师的ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '课程对外显示的名称',
  `week` int(2) NOT NULL COMMENT '星期几上课',
  `time_id` int(2) NOT NULL COMMENT 'lesson_time中上课时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lesson
-- ----------------------------
INSERT INTO `lesson` VALUES ('NSA-1000', '2019-2020-1', 4, '高等数学A', 1, 1);
INSERT INTO `lesson` VALUES ('NSA-1001', '2019-2020-1', 4, '高等数学B', 3, 4);

-- ----------------------------
-- Table structure for lesson_time
-- ----------------------------
DROP TABLE IF EXISTS `lesson_time`;
CREATE TABLE `lesson_time`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `start_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '课程开始时间',
  `end_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '课程结束时间',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '显示名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lesson_time
-- ----------------------------
INSERT INTO `lesson_time` VALUES (1, '8:20', '9:55', '上午 1,2');
INSERT INTO `lesson_time` VALUES (2, '10:05', '11:40', '上午 3,4');
INSERT INTO `lesson_time` VALUES (3, '10:05', '12:30', '上午 3,4,5');
INSERT INTO `lesson_time` VALUES (4, '13:30', '15:00', '下午 1,2');
INSERT INTO `lesson_time` VALUES (5, '15:10', '16:45', '下午 3,4');
INSERT INTO `lesson_time` VALUES (6, '18:15', '21:00', '晚上 1,2,3');

-- ----------------------------
-- Table structure for student_info
-- ----------------------------
DROP TABLE IF EXISTS `student_info`;
CREATE TABLE `student_info`  (
  `id` int(11) NOT NULL COMMENT '学生ID',
  `class_id` int(11) NOT NULL COMMENT '学生所在班级ID',
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '学生所在的地区',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_info
-- ----------------------------
INSERT INTO `student_info` VALUES (1, 10022, '浙江');
INSERT INTO `student_info` VALUES (55, 10022, '厦门');

-- ----------------------------
-- Table structure for student_lesson
-- ----------------------------
DROP TABLE IF EXISTS `student_lesson`;
CREATE TABLE `student_lesson`  (
  `student_id` int(11) NOT NULL COMMENT '学生ID',
  `lesson_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '课程ID',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_lesson
-- ----------------------------
INSERT INTO `student_lesson` VALUES (1, 'NSA-1000', 1);
INSERT INTO `student_lesson` VALUES (1, 'NSA-1001', 2);
INSERT INTO `student_lesson` VALUES (55, 'NSA-1000', 3);

-- ----------------------------
-- Table structure for teacher_leave_list
-- ----------------------------
DROP TABLE IF EXISTS `teacher_leave_list`;
CREATE TABLE `teacher_leave_list`  (
  `apply_id` int(11) NOT NULL COMMENT '请假单id',
  `teacher_id` int(11) NOT NULL COMMENT '教师id',
  `looked` int(1) NOT NULL DEFAULT 0 COMMENT '是否已读 0未读',
  `lesson_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '课程id',
  `week_num` int(11) NOT NULL COMMENT '一年中的第几周',
  `year` int(11) NOT NULL COMMENT '年份',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher_leave_list
-- ----------------------------
INSERT INTO `teacher_leave_list` VALUES (19, 4, 1, 'NSA-1001', 49, 2019, 1);
INSERT INTO `teacher_leave_list` VALUES (25, 4, 1, 'NSA-1000', 51, 2019, 6);
INSERT INTO `teacher_leave_list` VALUES (25, 4, 1, 'NSA-1001', 50, 2019, 7);
INSERT INTO `teacher_leave_list` VALUES (25, 4, 0, 'NSA-1001', 51, 2019, 8);
INSERT INTO `teacher_leave_list` VALUES (27, 4, 1, 'NSA-1001', 50, 2019, 9);
INSERT INTO `teacher_leave_list` VALUES (28, 4, 1, 'NSA-1000', 50, 2019, 10);
INSERT INTO `teacher_leave_list` VALUES (28, 4, 1, 'NSA-1001', 50, 2019, 11);
INSERT INTO `teacher_leave_list` VALUES (28, 4, 0, 'NSA-1000', 51, 2019, 12);
INSERT INTO `teacher_leave_list` VALUES (28, 4, 0, 'NSA-1001', 51, 2019, 13);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL COMMENT '用户唯一ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户密码',
  `type` int(1) NOT NULL COMMENT '用户的类型',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'default.jpg' COMMENT '用户头像',
  `e_mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '金凯凯', '1', 0, '7d5c7f115ef84273b177107defd39763', '465640448@qq.com');
INSERT INTO `user` VALUES (2, '辅导员', '2', 1, 'default.jpg', '465640448@qq.com');
INSERT INTO `user` VALUES (3, '院领导', '3', 2, 'default.jpg', '465640448@qq.com');
INSERT INTO `user` VALUES (4, '教师', '4', 3, 'default.jpg', '465640448@qq.com');
INSERT INTO `user` VALUES (55, '第二个学生', '55', 0, 'default.jpg', '465640448@qq.com');

SET FOREIGN_KEY_CHECKS = 1;
