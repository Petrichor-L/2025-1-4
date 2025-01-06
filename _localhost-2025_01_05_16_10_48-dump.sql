/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50726 (5.7.26)
 Source Host           : localhost:3306
 Source Schema         : student_performance_management_system

 Target Server Type    : MySQL
 Target Server Version : 50726 (5.7.26)
 File Encoding         : 65001

 Date: 06/01/2025 14:31:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `course_id` int(10) UNSIGNED NOT NULL,
  `course_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `classroom` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `teacher_id` int(10) NULL DEFAULT NULL,
  PRIMARY KEY (`course_id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, 'c1', 'c1', 2);
INSERT INTO `course` VALUES (2, 'c2', 'c2', 2);
INSERT INTO `course` VALUES (3, 'c3', 'c3', 3);
INSERT INTO `course` VALUES (4, 'c4', 'c4', 3);

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade`  (
  `studentId` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `courseId` int(11) NULL DEFAULT NULL,
  `grade` int(11) NULL DEFAULT NULL,
  UNIQUE INDEX `studentId`(`studentId`, `courseId`) USING BTREE,
  INDEX `course_id`(`courseId`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES ('7', 1, NULL);
INSERT INTO `grade` VALUES ('6', 2, 22);
INSERT INTO `grade` VALUES ('5', 1, 121);
INSERT INTO `grade` VALUES ('5', 2, 35);
INSERT INTO `grade` VALUES ('4', 2, 77);
INSERT INTO `grade` VALUES ('4', 1, 60);
INSERT INTO `grade` VALUES ('7', 2, NULL);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `role` enum('admin','teacher','student') CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (4, 's1', '123456', 'student', '12312312', '学生');
INSERT INTO `users` VALUES (2, 't1', '123456', 'teacher', '13800138000', '王大山');
INSERT INTO `users` VALUES (1, 'admin', '123456', 'admin', '12345678910', 'admin');
INSERT INTO `users` VALUES (5, 's2', '123456', 'student', '13356237732', '李四');
INSERT INTO `users` VALUES (6, 's3', '123456', 'student', '13332322121', '王五');
INSERT INTO `users` VALUES (7, 's4', '123456', 'student', '13267323912', '王六');
INSERT INTO `users` VALUES (8, 's5', '123456', 'student', '15632894356', '孙武');
INSERT INTO `users` VALUES (9, 's6', '123456', 'student', '13358849207', '李桐');
INSERT INTO `users` VALUES (10, 's7', '123456', 'student', '13346798520', '王鑫');
INSERT INTO `users` VALUES (11, 's8', '123456', 'student', '13369748512', '孙萨芬hi');
INSERT INTO `users` VALUES (3, 't2', '123456', 'teacher', '12345686214', '大苞米');

SET FOREIGN_KEY_CHECKS = 1;
