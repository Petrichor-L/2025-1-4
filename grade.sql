/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50726 (5.7.26)
 Source Host           : localhost:3306
 Source Schema         : grade

 Target Server Type    : MySQL
 Target Server Version : 50726 (5.7.26)
 File Encoding         : 65001

 Date: 05/01/2025 11:18:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`
(
    `courseId`   int(10) UNSIGNED NOT NULL,
    `courseName` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
    `classroom`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
    `teacherId`  int(10) NULL DEFAULT NULL,
    `data`       varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
    `studentId`  int(10) NULL DEFAULT NULL,
    INDEX        `teacher_id`(`teacherId`) USING BTREE,
    INDEX        `studentId`(`studentId`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course`
VALUES (1101, 'java', '实验楼213', 6, '周一 1-2节', 9);
INSERT INTO `course`
VALUES (1101, 'java', '实验楼213', 6, '周一 1-2节', 8);
INSERT INTO `course`
VALUES (101, '数学', 'A101', 1, '2025-01-05', 2);
INSERT INTO `course`
VALUES (102, '英语', 'A102', 1, '2025-01-06', 3);
INSERT INTO `course`
VALUES (103, '物理', 'A103', 1, '2025-01-07', 5);
INSERT INTO `course`
VALUES (104, '化学', 'A104', 1, '2025-01-08', 2);

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade`
(
    `studentId` int(10) NOT NULL,
    `courseId`  int(10) NULL DEFAULT NULL,
    `grade`     int(10) NULL DEFAULT NULL,
    INDEX       `course_id`(`courseId`) USING BTREE,
    UNIQUE INDEX `studentId`(`studentId`, `courseId`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade`
VALUES (8, 1101, 100);
INSERT INTO `grade`
VALUES (9, 1101, 88);
INSERT INTO `grade`
VALUES (2, 101, 90);
INSERT INTO `grade`
VALUES (3, 102, 85);
INSERT INTO `grade`
VALUES (5, 103, 95);
INSERT INTO `grade`
VALUES (2, 104, 88);
INSERT INTO `grade`
VALUES (8, 101, 99);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `id`       int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
    `name`     varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
    `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
    `role`     enum('student','teacher','admin') CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
    `phone`    varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX      `name`(`name`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users`
VALUES (4, 'admin', 'admin', 'qwerty', 'admin', '11111111111');
INSERT INTO `users`
VALUES (6, '10001', '赵亮', '123456', 'teacher', '17188495598');
INSERT INTO `users`
VALUES (7, '10002', '马千里', '123456', 'teacher', '19188559999');
INSERT INTO `users`
VALUES (8, '2220020305', 'hyy', '123456', 'student', '13133665599');
INSERT INTO `users`
VALUES (9, '2220020308', 'aad', '123456', 'student', '78945641231');
INSERT INTO `users`
VALUES (1, 'teacher1', '张老师', 'password123', 'teacher', '12345678901');
INSERT INTO `users`
VALUES (2, 'student1', '李学生', 'password123', 'student', '12345678902');
INSERT INTO `users`
VALUES (3, 'student2', '王学生', 'password123', 'student', '12345678903');
INSERT INTO `users`
VALUES (5, 'student3', '赵学生', 'password123', 'student', '12345678905');

SET
FOREIGN_KEY_CHECKS = 1;
