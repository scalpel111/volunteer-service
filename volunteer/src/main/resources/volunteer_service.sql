/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : volunteer_service

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 05/04/2023 22:17:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `activity_id` int(0) NOT NULL AUTO_INCREMENT,
  `theme` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `start_time` datetime(0) NOT NULL,
  `end_time` datetime(0) NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `req` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `recruit_number` int(0) NOT NULL,
  `tip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`activity_id`) USING BTREE,
  UNIQUE INDEX `theme`(`theme`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (1, 'dolor esse', '1979-09-27 05:07:46', '1982-07-20 20:11:51', '浙江省张家口市黄大仙区', 'nisi fugiat Excepteur ea est', '各调市队声精相二住约可行。起头家者系市集领共厂至问区几相很。完体自基江论然色已京出气路。局红县用权制广斯人指受不使约目备南。', 91, '53.254.232.46');
INSERT INTO `activity` VALUES (4, 'ex eiusmod', '2021-07-17 18:47:33', '1987-10-02 04:30:01', '西藏自治区阿勒泰地区黟县', 'enim', '经亲最生取象深整件级身张间心很。众革公社二南格龙管知许查。表须调正书准个来变品广证。较资角书意是百市写入易口百叫示业。听回决统专于在体就属称新几。龙者面代加六据器联酸于将再题山。军放严又应始成济育手建值活通三状新。', 46, '173.153.79.111');
INSERT INTO `activity` VALUES (5, 'nostrud ullamco proident ad', '2020-10-21 12:44:25', '2001-03-09 09:56:21', '上海重庆市南区', 'veniam est adipisicing tempor', '万院林正好收见作地干并起指声按严。门龙治生年属管许从至需件保亲月。见六车认度老县等达体种始气保地。', 72, '171.216.108.118');
INSERT INTO `activity` VALUES (6, 'Duis ea esse', '1980-03-01 21:22:01', '2018-11-23 07:48:30', '香港特别行政区泰州市昌平区', 'ad', '论队上代车水三干龙提矿社率林反物。什务率与运工候建那究天专老。般地只府求小选龙空连社算品改圆满。意选公代质引因分许育历治数。外切保元果解两极例资包作器住个来时军。到备布和目三体劳布花活石。节题过很装复都战族则完求广及容边政更。', 80, '70.151.198.76');

-- ----------------------------
-- Table structure for institution
-- ----------------------------
DROP TABLE IF EXISTS `institution`;
CREATE TABLE `institution`  (
  `institution_id` int(0) NOT NULL AUTO_INCREMENT,
  `institution_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `admin` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `admin_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tellphone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `proof` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `count` int(0) NULL DEFAULT 0,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`institution_id`) USING BTREE,
  UNIQUE INDEX `institution_name`(`institution_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of institution
-- ----------------------------
INSERT INTO `institution` VALUES (1, '间定准件叫只', 'dolor do laborum mollit', '28', '13849113534', 'esse velit reprehenderit', 4, '台湾泰安市东山县');
INSERT INTO `institution` VALUES (2, '进此界近整改', 'culpa in deserunt', '82', '13966225736', 'aliquip consectetur ipsum', 47, '上海眉山市南开区');
INSERT INTO `institution` VALUES (5, '车名金业消速', 'quis', '12', '19815294543', 'culpa dolore quis', 74, '贵州省台中市金湖县');
INSERT INTO `institution` VALUES (6, '白表文色究白', 'qui', '32', '18684246878', 'velit in elit quis ipsum', 22, '甘肃省池州市金台区');
INSERT INTO `institution` VALUES (7, '的备日着算', 'ipsum consectetur ullamco', '75', '18655208521', 'laborum', 89, '西藏自治区辽源市通州区');
INSERT INTO `institution` VALUES (8, '亲易身有正整', 'tempor sint voluptate', '53', '18180226643', 'laborum dolore', 83, '江西省离岛务川仡佬族苗族自治县');

-- ----------------------------
-- Table structure for institution_activity
-- ----------------------------
DROP TABLE IF EXISTS `institution_activity`;
CREATE TABLE `institution_activity`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `institution_id` int(0) NOT NULL,
  `activity_id` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `institution_id`(`institution_id`) USING BTREE,
  INDEX `activity_id`(`activity_id`) USING BTREE,
  CONSTRAINT `institution_activity_ibfk_1` FOREIGN KEY (`institution_id`) REFERENCES `institution` (`institution_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `institution_activity_ibfk_2` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`activity_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of institution_activity
-- ----------------------------

-- ----------------------------
-- Table structure for root
-- ----------------------------
DROP TABLE IF EXISTS `root`;
CREATE TABLE `root`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `admin` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of root
-- ----------------------------
INSERT INTO `root` VALUES (1, 'scalpel', '111111');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `sessionkey` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `identify_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `identify_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tellphone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `service_time` double NULL DEFAULT NULL,
  `declaration` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`openid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'scalpel', '111111', '620422200202101910', '刘谊', NULL, '17309435144', 2.4, NULL);
INSERT INTO `user` VALUES ('2', 'lyic', '111111', NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for user_activity
-- ----------------------------
DROP TABLE IF EXISTS `user_activity`;
CREATE TABLE `user_activity`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `activity_id` int(0) NOT NULL,
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `activity_id`(`activity_id`) USING BTREE,
  INDEX `openid`(`openid`) USING BTREE,
  CONSTRAINT `user_activity_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`activity_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_activity_ibfk_2` FOREIGN KEY (`openid`) REFERENCES `user` (`openid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_activity
-- ----------------------------

-- ----------------------------
-- Table structure for user_institution
-- ----------------------------
DROP TABLE IF EXISTS `user_institution`;
CREATE TABLE `user_institution`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `institution_id` int(0) NOT NULL,
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '0(普通用户),1(管理员)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `openid`(`openid`) USING BTREE,
  INDEX `institution_id`(`institution_id`) USING BTREE,
  CONSTRAINT `user_institution_ibfk_1` FOREIGN KEY (`openid`) REFERENCES `user` (`openid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_institution_ibfk_2` FOREIGN KEY (`institution_id`) REFERENCES `institution` (`institution_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_institution
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
