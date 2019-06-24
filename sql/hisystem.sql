/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50528
 Source Host           : localhost:3306
 Source Schema         : hisystem

 Target Server Type    : MySQL
 Target Server Version : 50528
 File Encoding         : 65001

 Date: 24/06/2019 11:26:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for his_announcement
-- ----------------------------
DROP TABLE IF EXISTS `his_announcement`;
CREATE TABLE `his_announcement`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `contents` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_datetime` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ann_status` int(10) NOT NULL DEFAULT 0,
  `ann_date` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of his_announcement
-- ----------------------------
INSERT INTO `his_announcement` VALUES ('402880e86a1fb6e1016a1fc02da00000', '医院', '  医院（Hospital）一词是来自于拉丁文原意为“客人”，因为一开始设立\n时，是供人避难，还备有休息间，使来者舒适，有招待意图。后来，才\n逐渐成为满足人类医疗需求，提供医疗服务的专业机构，收容和治疗病\n人的服务场所。', '2019-04-15 14:47:29', 1, '2019-05-16');
INSERT INTO `his_announcement` VALUES ('402880e86a24ccfc016a24d2934c0003', 'hello', '  医院（Hospital）一词是来自于拉丁文原意为“客人”，因为一开始设立\n时，是供人避难，还备有休息间，使来者舒适，有招待意图。后来，才\n逐渐成为满足人类医疗需求，提供医疗服务的专业机构，收容和治疗病\n人的服务场所。', '2019-04-16 14:25:41', 1, '2019-05-16');
INSERT INTO `his_announcement` VALUES ('402880e86a251778016a2530a2ca0004', '12', '1、当你在\n2、地方\n3、各个地方晃过上帝很过分\n4、大V收拾\n5、电焊工和规范化的\n6、给第三方还不停的\n人的服务场所。', '2019-04-16 16:08:25', 1, '2019-05-16');

-- ----------------------------
-- Table structure for his_drug
-- ----------------------------
DROP TABLE IF EXISTS `his_drug`;
CREATE TABLE `his_drug`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_datetime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `drug_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `efficacy_classification` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `limit_status` int(50) NOT NULL,
  `manufacturer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `num` double NOT NULL,
  `price` double(50, 0) NOT NULL,
  `specification` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `unit` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `wholesale_price` double(50, 0) NOT NULL,
  `storage_quantity` int(11) NOT NULL,
  `production_date` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `quality_date` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of his_drug
-- ----------------------------
INSERT INTO `his_drug` VALUES ('402880ed6aac6930016aac8b115b0003', '2019-05-12 22:55:56', '片剂', '解热镇痛', 0, '西南药业股份有限公司', '布洛芬缓释片', 1557672956250, 12, '0.3g*20', '盒', 11, 20, '2015-06-13', '2020-09-18');
INSERT INTO `his_drug` VALUES ('402880ed6aac6930016aac8cf9340004', '2019-05-12 22:58:01', '胶囊剂', '解热镇痛', 0, '四川蜀中制药有限公司', '复方氨酚烷胺', 1557673081139, 4, '0.3g*10', '板', 4, 30, '2013-06-13', '2019-09-18');
INSERT INTO `his_drug` VALUES ('402880ed6aac6930016aac8f23c50005', '2019-05-12 23:00:23', '片剂', '镇痛', 0, '成都市潜江制药有限公司', '罗痛定片', 1557673223108, 5, '30mg*20', '瓶', 4, 20, '2015-06-11', '2021-09-16');
INSERT INTO `his_drug` VALUES ('4028b8816b0244fd016b02f8d60e0005', '2019-05-29 17:43:10', '薄膜衣片', '抗病毒药', 0, '葛兰素史克（苏州）有限公司', '拉米夫定', 1559122990587, 200, '100mg*14', '盒', 180, 100, '2019-03-05', '2019-06-07');

-- ----------------------------
-- Table structure for his_drug_type
-- ----------------------------
DROP TABLE IF EXISTS `his_drug_type`;
CREATE TABLE `his_drug_type`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_datetime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `drug_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of his_drug_type
-- ----------------------------
INSERT INTO `his_drug_type` VALUES ('4028b8816aff159b016aff19715c0000', '2019-05-28 23:40:18', '胶囊剂');
INSERT INTO `his_drug_type` VALUES ('4028b8816b0244fd016b02b68ecd0001', '2019-05-29 16:30:46', '注射剂');
INSERT INTO `his_drug_type` VALUES ('4028b8816b0244fd016b02f378820003', '2019-05-29 17:37:18', '片剂');
INSERT INTO `his_drug_type` VALUES ('4028b8816b0244fd016b02f64d1a0004', '2019-05-29 17:40:24', '薄膜衣片');

-- ----------------------------
-- Table structure for his_efficacy_classification
-- ----------------------------
DROP TABLE IF EXISTS `his_efficacy_classification`;
CREATE TABLE `his_efficacy_classification`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_datetime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `efficacy_classification` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of his_efficacy_classification
-- ----------------------------
INSERT INTO `his_efficacy_classification` VALUES ('4028b8816aff159b016aff1a23a20001', '2019-05-28 23:41:04', '风热感冒');
INSERT INTO `his_efficacy_classification` VALUES ('4028b8816b0244fd016b02d101990002', '2019-05-29 16:59:40', '抗病毒药');
INSERT INTO `his_efficacy_classification` VALUES ('4028b8816b26fa19016b2721befb0000', '2019-06-05 18:14:11', '解热镇痛');

-- ----------------------------
-- Table structure for his_idcard
-- ----------------------------
DROP TABLE IF EXISTS `his_idcard`;
CREATE TABLE `his_idcard`  (
  `card_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `birthday` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `id_card` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nationality` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`card_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of his_idcard
-- ----------------------------
INSERT INTO `his_idcard` VALUES ('1A5CA981', '四川省成都市双流区天府大道', '1995-04-13', '510727199504130125', '慕容清', '汉', '男');
INSERT INTO `his_idcard` VALUES ('1A60EDE1', '四川省成都市郫县红光镇', '1993-02-07', '510727199806045201', '张花花', '汉', '女');
INSERT INTO `his_idcard` VALUES ('AA9C887D', '四川省成都市武侯区', '1995-09-04', '510727199509040311', '李明', '羌', '男');

-- ----------------------------
-- Table structure for his_login_infor
-- ----------------------------
DROP TABLE IF EXISTS `his_login_infor`;
CREATE TABLE `his_login_infor`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `login_ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `login_broswer` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `login_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_datetime` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK2rtlewjw7yapj82150k1enrhg`(`user_id`) USING BTREE,
  CONSTRAINT `FK2rtlewjw7yapj82150k1enrhg` FOREIGN KEY (`user_id`) REFERENCES `his_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of his_login_infor
-- ----------------------------
INSERT INTO `his_login_infor` VALUES ('402880e56a11b879016a11bdd1ea0000', '171.216.69.212', 'Chrome', '四川省成都市', '8ad8a0ec6a06851e016a069172fa0002', '1208585122@qq.com', '2019-04-12 21:30:13');
INSERT INTO `his_login_infor` VALUES ('402880e56a163990016a163c4cc80000', '171.216.69.212', 'Edge', '四川省成都市', '8ad8a0ec6a06851e016a069172fa0002', '1208585122@qq.com', '2019-04-13 18:26:50');
INSERT INTO `his_login_infor` VALUES ('402881ea6a0fa5c7016a0fb0aa820000', '117.173.217.156', 'Chrome', '四川省成都市', '8ad8a0ec6a06851e016a069172fa0002', '1208585122@qq.com', '2019-04-12 11:56:35');
INSERT INTO `his_login_infor` VALUES ('4028b8816b2829c9016b282edcee0000', NULL, 'Chrome', NULL, '402880e86a24ccfc016a24cd6b240000', 'register@shospital.com', '2019-06-05 23:08:08');
INSERT INTO `his_login_infor` VALUES ('4028b8816b2829c9016b282f22840001', NULL, 'Chrome', NULL, '402880ea6a7df7d6016a7e5806bf0006', 'm_doctor_2@shospital.com', '2019-06-05 23:08:26');
INSERT INTO `his_login_infor` VALUES ('4028b8816b2829c9016b28306d3a0002', NULL, 'Chrome', NULL, '8ad8a0ec6a0a7e7b016a0a7ed6e50000', 'm_doctor@shospital.com', '2019-06-05 23:09:50');
INSERT INTO `his_login_infor` VALUES ('4028b8816b2829c9016b2830c3490003', NULL, 'Chrome', NULL, '402881ea6abf196a016abf40b8400005', 'm_tollCollector_1@shospital.com', '2019-06-05 23:10:12');
INSERT INTO `his_login_infor` VALUES ('4028b8816b2829c9016b283108f30004', NULL, 'Chrome', NULL, '402881ea6abf196a016abf2fbb5e0003', 'm_druggist_1@shospital.com', '2019-06-05 23:10:30');
INSERT INTO `his_login_infor` VALUES ('4028b8816b2829c9016b2833e5970009', NULL, 'Chrome', NULL, '402881ea6aca4965016aca6a54d0000a', 'm_technologyDoc_1@shospital.com', '2019-06-05 23:13:38');
INSERT INTO `his_login_infor` VALUES ('8ad8a0ec6a06851e016a06921bc70004', '182.138.101.27', 'Chrome', '四川省成都市', '8ad8a0ec6a06851e016a069172fa0002', '1208585122@qq.com', '2019-04-10 17:26:38');
INSERT INTO `his_login_infor` VALUES ('8ad8a0ec6a0a74fd016a0a76156a0000', '182.138.101.27', 'Edge', '四川省成都市', '8ad8a0ec6a06851e016a069172fa0002', '1208585122@qq.com', '2019-04-11 11:34:31');
INSERT INTO `his_login_infor` VALUES ('ff8080816ad0a67a016ad0a737f50000', NULL, 'Chrome', NULL, '8ad8a0ec6a06851e016a069172fa0002', '1208585122@qq.com', '2019-05-19 23:13:00');

-- ----------------------------
-- Table structure for his_medical_examination
-- ----------------------------
DROP TABLE IF EXISTS `his_medical_examination`;
CREATE TABLE `his_medical_examination`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_datetime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `blood_pressure` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `body_temperature` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `heart_rate` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pulse` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `examination_cost` double(50, 0) NULL DEFAULT NULL,
  `prescription_num` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `examination_operator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of his_medical_examination
-- ----------------------------
INSERT INTO `his_medical_examination` VALUES ('402881ea6aca4965016aca61ee6b0009', '2019-05-18 17:59:36', '120', '36', '70', '60', 5, '1558173573484', '402881ea6aca4965016aca6a54d0000a');
INSERT INTO `his_medical_examination` VALUES ('4028b8816b2829c9016b28335e9f0008', '2019-06-05 23:13:03', '11', '11', '11', '11', 11, '1559747520517', '402881ea6aca4965016aca6a54d0000a');
INSERT INTO `his_medical_examination` VALUES ('4028b8816b3a5928016b3a776d190003', '2019-06-09 12:20:33', '120', '35', '70', '60', 10, '1560054030690', '8ad8a0ec6a0a7e7b016a0a7ed6e50000');

-- ----------------------------
-- Table structure for his_medical_record
-- ----------------------------
DROP TABLE IF EXISTS `his_medical_record`;
CREATE TABLE `his_medical_record`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_datetime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `condition_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `diagnosis_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `drug_cost` double(50, 0) NULL DEFAULT NULL,
  `medical_order` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `prescription` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `prescription_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `register_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `taking_drug_date_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `taking_drug_operator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `toll_date_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `toll_operator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `taking_drug_frequency` int(11) NOT NULL,
  `toll_frequency` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKj7lqjwchyktjc2pno6e7f60k8`(`register_id`) USING BTREE,
  CONSTRAINT `FKj7lqjwchyktjc2pno6e7f60k8` FOREIGN KEY (`register_id`) REFERENCES `his_register` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of his_medical_record
-- ----------------------------
INSERT INTO `his_medical_record` VALUES ('402880ed6ab1c82b016ab1ec8ec70006', '2019-05-14 00:00:31', '头痛', '轻微脑震荡', 17, '多吃清淡食物', '\n                                        <ol>\n\n                                        <li>贝洛酯片<span style=\"margin-left:100px\">0.5g*50/瓶</span></li><div style=\"margin: 10px 0 10px 5px;\">用法：<sapn>0.5g</sapn><sapn style=\"margin-left:40px\">口服</sapn><sapn style=\"margin-left:60px\">每日两次</sapn></div><li>布洛芬缓释片<span style=\"margin-left:100px\">0.3g*20/盒</span></li><div style=\"margin: 10px 0 10px 5px;\">用法：<sapn>0.3g</sapn><sapn style=\"margin-left:40px\">口服</sapn><sapn style=\"margin-left:60px\">每日两次</sapn></div></ol>\n                                    ', '1557763119462', '402880ed6ab1c82b016ab1d755ec0002', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `his_medical_record` VALUES ('402880ed6ab4193c016ab470fbbe0004', '2019-05-14 11:44:24', '经常咳嗽，导致声带损伤', '病毒性感冒', 4, '多喝水', '\n                                        <ol>\n\n                                        <li>罗痛定片<span style=\"margin-left:100px\">30mg/瓶</span></li><div style=\"margin: 10px 0 10px 5px;\">用法：<sapn>10mg</sapn><sapn style=\"margin-left:40px\">口服</sapn><sapn style=\"margin-left:60px\">每日两次</sapn></div></ol>\n                                    ', '1557803995526', '402880ed6ab4193c016ab41cbeca0001', '2019-05-15 23:42:11', '8ad8a0ec6a06851e016a069172fa0002', '2019-05-15 17:43:10', '8ad8a0ec6a06851e016a069172fa0002', 1, 1);
INSERT INTO `his_medical_record` VALUES ('402881ea6abf196a016abf2d18900002', '2019-05-16 13:46:04', '工作时间长，间歇性头痛', '高强度脑力工作导致', 9, '服药后多休息', '<ol>\n\n                                        <li>贝洛酯片<span style=\"margin-left:100px\">0.5g*50/瓶</span></li><div style=\"margin: 10px 0 10px 5px;\">用法：<sapn>0.5</sapn><sapn style=\"margin-left:40px\">口服</sapn><sapn style=\"margin-left:60px\">每日三次</sapn></div><li>罗痛定片<span style=\"margin-left:100px\">30mg/瓶</span></li><div style=\"margin: 10px 0 10px 5px;\">用法：<sapn>10mg</sapn><sapn style=\"margin-left:40px\">口服</sapn><sapn style=\"margin-left:60px\">每日三次</sapn></div></ol>', '1557985359952', '402881ea6abf196a016abf29baf00000', '2019-05-16 14:19:30', '402881ea6abf196a016abf2fbb5e0003', '2019-05-16 18:13:48', '402881ea6abf196a016abf40b8400005', 1, 2);
INSERT INTO `his_medical_record` VALUES ('402881ea6ac4ced9016ac4d3b2170002', '2019-05-17 16:06:09', 'mm', 'mmm', 12, 'mm', '<ol>\n\n                                        <li>布洛芬缓释片<span style=\"margin-left:100px\">0.3g*20/盒</span></li><div style=\"margin: 10px 0 10px 5px;\">用法：<sapn>0.5</sapn><sapn style=\"margin-left:40px\">口服</sapn><sapn style=\"margin-left:60px\">每日两次</sapn></div></ol>', '1558080348109', '402881ea6ac4ced9016ac4d30dff0000', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `his_medical_record` VALUES ('402881ea6aca4965016aca61ee5a0008', '2019-05-18 17:59:36', 'sd', 'sda', 12, 'saf', '<ol>\n\n                                        <li>布洛芬缓释片<span style=\"margin-left:100px\">0.3g*20/盒</span></li><div style=\"margin: 10px 0 10px 5px;\">用法：<sapn>ss</sapn><sapn style=\"margin-left:40px\">口服</sapn><sapn style=\"margin-left:60px\">每日一次</sapn></div></ol>', '1558173573484', '402881ea6aca4965016aca619f620006', '2019-05-18 19:14:46', '402881ea6abf196a016abf2fbb5e0003', '2019-05-18 19:14:01', '402881ea6abf196a016abf40b8400005', 1, 2);
INSERT INTO `his_medical_record` VALUES ('4028b8816b2829c9016b28335e870007', '2019-06-05 23:13:03', 'test', 'test', 16, 'test', '<ol><li>布洛芬缓释片<span style=\"margin-left:100px\">0.3g*20/盒</span></li><div style=\"margin: 10px 0 10px 5px;\">用法：<sapn>11</sapn><sapn style=\"margin-left:40px\">口服</sapn><sapn style=\"margin-left:60px\">每日一次</sapn></div><li>复方氨酚烷胺<span style=\"margin-left:100px\">0.3g*10/板</span></li><div style=\"margin: 10px 0 10px 5px;\">用法：<sapn>1111</sapn><sapn style=\"margin-left:40px\">口服</sapn><sapn style=\"margin-left:60px\">每日两次</sapn></div></ol>', '1559747520517', '4028b8816b2829c9016b2831c96e0005', '2019-06-09 12:31:56', '402881ea6abf196a016abf2fbb5e0003', '2019-06-09 12:31:16', '402881ea6abf196a016abf40b8400005', 2, 2);
INSERT INTO `his_medical_record` VALUES ('4028b8816b3a5928016b3a776d010002', '2019-06-09 12:20:33', '', NULL, 0, NULL, NULL, '1560054030690', '4028b8816b3a5928016b3a66e5ab0000', NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `his_medical_record` VALUES ('ff8080816abf9422016ac020b246000e', '2019-05-16 18:12:09', 'ddd', '病毒性感冒', 5, 'ddd', '<ol><li>贝洛酯片<span style=\"margin-left:100px\">0.5g*50/瓶</span></li><div style=\"margin: 10px 0 10px 5px;\">用法：<sapn>0.5</sapn><sapn style=\"margin-left:40px\">口服</sapn><sapn style=\"margin-left:60px\">每日一次</sapn></div></ol>', '1558001525790', 'ff8080816abf9422016ac02071f0000c', '2019-05-16 18:14:54', '402881ea6abf196a016abf2fbb5e0003', '2019-05-16 18:14:10', '402881ea6abf196a016abf40b8400005', 1, 1);

-- ----------------------------
-- Table structure for his_outpatient_queue
-- ----------------------------
DROP TABLE IF EXISTS `his_outpatient_queue`;
CREATE TABLE `his_outpatient_queue`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_datetime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `outpatient_queue_status` int(11) NULL DEFAULT NULL,
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `register_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `patient_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKr4e9u7n5vuhgxvfkiwrvihqp3`(`register_id`) USING BTREE,
  INDEX `FK8crcyewdiqishredqwx9w1cnw`(`user_id`) USING BTREE,
  INDEX `FKanyqf7asaslfiu8qa9trhsq7f`(`patient_id`) USING BTREE,
  CONSTRAINT `FK8crcyewdiqishredqwx9w1cnw` FOREIGN KEY (`user_id`) REFERENCES `his_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKanyqf7asaslfiu8qa9trhsq7f` FOREIGN KEY (`patient_id`) REFERENCES `his_patient` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKr4e9u7n5vuhgxvfkiwrvihqp3` FOREIGN KEY (`register_id`) REFERENCES `his_register` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of his_outpatient_queue
-- ----------------------------
INSERT INTO `his_outpatient_queue` VALUES ('4028b8816b3a5928016b3a66e5c80001', '2019-06-09 12:02:30', -1, '张花花#彭医生', '4028b8816b3a5928016b3a66e5ab0000', '8ad8a0ec6a0a7e7b016a0a7ed6e50000', '402880ea6a77a772016a77a7f63c0000');

-- ----------------------------
-- Table structure for his_patient
-- ----------------------------
DROP TABLE IF EXISTS `his_patient`;
CREATE TABLE `his_patient`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_datetime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `birthday` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `card_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `id_card` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nationality` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `telphone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `career` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `family_history` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `marital_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `past_history` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `personal_history` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of his_patient
-- ----------------------------
INSERT INTO `his_patient` VALUES ('402880ea6a77a772016a77a7f63c0000', '2019-05-02 16:27:37', '四川省成都市郫县红光镇', '1993-02-07', '1A553D21', '510727199806045201', '张花花', '汉', '女', NULL, '自营', '无遗传病史。', '已婚', '无重大疾病', '经常感冒');
INSERT INTO `his_patient` VALUES ('402880ea6a7df7d6016a7e35dec80000', '2019-05-03 23:00:20', '四川省成都市双流区天府大道', '1995-04-13', '4FE24201', '510727199504130125', '慕容清', '汉', '男', NULL, '出纳员', '否认家族遗传病史。', '未婚', '无肝炎、结核等传染病史及密切接触史。无重大外伤及手术史，无药物过敏史。预防接种史不详。', '生于原籍，无外地久居史，无不良嗜好。');

-- ----------------------------
-- Table structure for his_register
-- ----------------------------
DROP TABLE IF EXISTS `his_register`;
CREATE TABLE `his_register`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_datetime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `department` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `doctor` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pay_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `register_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `treatment_price` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `patient_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `operator_email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `operator_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `charge_status` int(11) NOT NULL,
  `register_status` int(11) NOT NULL,
  `treatment_status` int(11) NOT NULL,
  `registered_num` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `doctor_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKro0gncetto77fqmii4bxaprlu`(`patient_id`) USING BTREE,
  CONSTRAINT `FKro0gncetto77fqmii4bxaprlu` FOREIGN KEY (`patient_id`) REFERENCES `his_patient` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of his_register
-- ----------------------------
INSERT INTO `his_register` VALUES ('402880ea6a77f80f016a78015e3d0002', '2019-05-02 18:05:16', '呼吸内科', 'm_doctor', '现金', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', '', '', 0, 0, 0, '', '');
INSERT INTO `his_register` VALUES ('402880ea6a77f80f016a7801a5780003', '2019-05-02 18:05:34', '呼吸内科', 'm_doctor', '现金', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', '', '', 0, 0, 0, '', '');
INSERT INTO `his_register` VALUES ('402880ea6a7c8768016a7c8eea040002', '2019-05-03 15:18:21', '呼吸内科', 'm_doctor', '支付宝', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', '1208585122@qq.com', 'sen', 0, 0, 0, '', '');
INSERT INTO `his_register` VALUES ('402880ea6a7df7d6016a7e669608000a', '2019-05-03 23:53:33', '消化内科', 'm_doctor_2', '支付宝', '普通门诊', '3.0', '402880ea6a7df7d6016a7e35dec80000', '1208585122@qq.com', 'sen', 0, 0, 0, '', '');
INSERT INTO `his_register` VALUES ('402880ea6a7df7d6016a7e6aa6a7000b', '2019-05-04 23:57:59', '急诊科', 'm_doctor_3', '支付宝', '急诊', '5.0', '402880ea6a7df7d6016a7e35dec80000', '1208585122@qq.com', 'sen', 0, 1, 1, '', '');
INSERT INTO `his_register` VALUES ('402880ea6a82e5d9016a82f891c70000', '2019-05-04 21:11:29', '呼吸内科', 'm_doctor', '现金', '普通门诊', '2.0', '402880ea6a7df7d6016a7e35dec80000', '1208585122@qq.com', 'sen', 0, -1, 0, '', '');
INSERT INTO `his_register` VALUES ('402880ea6a8da56e016a8db51f090003', '2019-05-06 23:13:38', '呼吸内科', 'm_doctor', '支付宝', '普通门诊', '2.0', '402880ea6a7df7d6016a7e35dec80000', '1208585122@qq.com', 'sen', 0, -1, 0, '', '');
INSERT INTO `his_register` VALUES ('402880ea6a92e5b7016a92e91e4f0000', '2019-05-07 23:28:32', '消化内科', 'm_doctor_2', '现金', '普通门诊', '3.0', '402880ea6a7df7d6016a7e35dec80000', '1208585122@qq.com', 'sen', 0, -1, 0, '', '');
INSERT INTO `his_register` VALUES ('402880ea6a95d3b9016a95d4f8c40000', '2019-05-08 13:05:23', '消化内科', 'm_doctor_2', '支付宝', '普通门诊', '3.0', '402880ea6a7df7d6016a7e35dec80000', '1208585122@qq.com', 'sen', 0, 1, 1, '', '');
INSERT INTO `his_register` VALUES ('402880ea6a95d3b9016a95daa33b0003', '2019-05-08 13:11:31', '呼吸内科', 'm_doctor', '现金', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', '1208585122@qq.com', 'sen', 0, 1, 1, '', '');
INSERT INTO `his_register` VALUES ('402880ea6a962fa6016a963500540000', '2019-05-08 14:50:17', '消化内科', 'm_doctor_2', '现金', '普通门诊', '3.0', '402880ea6a7df7d6016a7e35dec80000', '1208585122@qq.com', 'sen', 0, 1, 1, '', '');
INSERT INTO `his_register` VALUES ('402880ea6a962fa6016a963c201e0002', '2019-05-08 14:58:03', '消化内科', 'm_doctor_2', '支付宝', '普通门诊', '3.0', '402880ea6a77a772016a77a7f63c0000', 'm_doctor_2@shospital.com', 'm_doctor_2', 0, 1, 1, '', '');
INSERT INTO `his_register` VALUES ('402880ea6a97d937016a97dfbfcf0004', '2019-05-08 22:36:12', '呼吸内科', 'm_doctor', '现金', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', '1208585122@qq.com', 'sen', 0, -1, 0, 'RE1557326172669488', '');
INSERT INTO `his_register` VALUES ('402880ea6a97d937016a97dfff690006', '2019-05-08 22:36:40', '呼吸内科', 'm_doctor', '支付宝', '普通门诊', '2.0', '402880ea6a7df7d6016a7e35dec80000', '1208585122@qq.com', 'sen', 0, -1, 0, 'RE1557326200680137', '');
INSERT INTO `his_register` VALUES ('402880ea6a9b0835016a9b0c768c0000', '2019-05-09 13:24:06', '呼吸内科', 'm_doctor', '现金', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', '1208585122@qq.com', 'sen', 0, 1, 1, 'RE1557379446408893', '');
INSERT INTO `his_register` VALUES ('402880ea6a9b0835016a9b0d56650002', '2019-05-09 13:25:03', '呼吸内科', 'm_doctor', '支付宝', '普通门诊', '2.0', '402880ea6a7df7d6016a7e35dec80000', '1208585122@qq.com', 'sen', 0, 1, 1, 'RE1557379503717574', '');
INSERT INTO `his_register` VALUES ('402880ed6ab1c82b016ab1d6e01c0000', '2019-05-13 23:36:50', '呼吸内科', 'm_doctor', '现金', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', '1208585122@qq.com', 'sen', 0, -1, 0, 'RE1557761810455118', '');
INSERT INTO `his_register` VALUES ('402880ed6ab1c82b016ab1d755ec0002', '2019-05-13 23:37:20', '呼吸内科', 'm_doctor', '支付宝', '普通门诊', '2.0', '402880ea6a7df7d6016a7e35dec80000', '1208585122@qq.com', 'sen', 0, -1, 0, 'RE1557761840620517', '');
INSERT INTO `his_register` VALUES ('402880ed6ab4193c016ab41cbeca0001', '2019-05-14 10:12:23', '呼吸内科', 'm_doctor', '支付宝', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', '1208585122@qq.com', 'sen', 1, 1, 1, 'RE1557799943880390', '');
INSERT INTO `his_register` VALUES ('402880ed6ab68612016ab6972b4b0000', '2019-05-14 21:45:21', '呼吸内科', 'm_doctor', '现金', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', '1208585122@qq.com', 'sen', 0, 1, 1, 'RE1557841521473501', '');
INSERT INTO `his_register` VALUES ('402881836a9bbc3e016a9bbea9090000', '2019-05-09 16:38:44', '呼吸内科', 'm_doctor', '支付宝', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', 'register@shospital.com', 'register', 0, -1, 0, 'RE1557391124733712', '');
INSERT INTO `his_register` VALUES ('402881836a9bc374016a9bc622330000', '2019-05-09 16:46:54', '呼吸内科', 'm_doctor', '现金', '普通门诊', '2.0', '402880ea6a7df7d6016a7e35dec80000', 'register@shospital.com', 'register', 0, -1, 0, 'RE1557391614504606', '');
INSERT INTO `his_register` VALUES ('402881ea6abf196a016abf29baf00000', '2019-05-16 13:42:24', '呼吸内科', 'm_doctor', '现金', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', 'register@shospital.com', 'register', 1, 1, 1, 'RE1557985344225385', '');
INSERT INTO `his_register` VALUES ('402881ea6ac4ced9016ac4d30dff0000', '2019-05-17 16:05:27', '呼吸内科', 'm_doctor', '现金', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', '1208585122@qq.com', 'sen', 0, 1, 1, 'RE1558080327155978', '8ad8a0ec6a06851e016a069172fa0002');
INSERT INTO `his_register` VALUES ('402881ea6aca4965016aca619f620006', '2019-05-18 17:59:16', '呼吸内科', 'm_doctor', '现金', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', '1208585122@qq.com', 'sen', 1, 1, 1, 'RE1558173556569459', '8ad8a0ec6a06851e016a069172fa0002');
INSERT INTO `his_register` VALUES ('402881ea6ad2abb8016ad2bc3c500000', '2019-05-20 08:55:12', '呼吸内科', '彭医生', '现金', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', '1208585122@qq.com', 'sen', 0, -1, 0, 'RE1558313712709314', '8ad8a0ec6a06851e016a069172fa0002');
INSERT INTO `his_register` VALUES ('4028b8816b230d9e016b23310d5d0000', '2019-06-04 23:52:25', '呼吸内科', '彭医生', '支付宝', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', '1208585122@qq.com', 'sen', 0, -1, 0, 'RE1559663545677146', '8ad8a0ec6a06851e016a069172fa0002');
INSERT INTO `his_register` VALUES ('4028b8816b2829c9016b2831c96e0005', '2019-06-05 23:11:19', '呼吸内科', '彭医生', '现金', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', 'm_druggist_1@shospital.com', '黄药剂', 1, 1, 1, 'RE1559747479917749', '402881ea6abf196a016abf2fbb5e0003');
INSERT INTO `his_register` VALUES ('4028b8816b3a5928016b3a66e5ab0000', '2019-06-09 12:02:30', '呼吸内科', '彭医生', '现金', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', '1208585122@qq.com', '薛管理', 0, 1, 1, 'RE1560052950429314', '8ad8a0ec6a06851e016a069172fa0002');
INSERT INTO `his_register` VALUES ('ff8080816abf9422016abf9880500000', '2019-05-16 15:43:23', '呼吸内科', 'm_doctor', '现金', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', '1208585122@qq.com', 'sen', 1, 1, 1, 'RE1557992603716985', '');
INSERT INTO `his_register` VALUES ('ff8080816abf9422016ac02071f0000c', '2019-05-16 18:11:52', '呼吸内科', 'm_doctor', '支付宝', '普通门诊', '2.0', '402880ea6a77a772016a77a7f63c0000', 'register@shospital.com', 'register', 1, 1, 1, 'RE1558001512931274', '');

-- ----------------------------
-- Table structure for his_role
-- ----------------------------
DROP TABLE IF EXISTS `his_role`;
CREATE TABLE `his_role`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_value` tinyint(2) NOT NULL,
  `desrciption` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_datetime` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of his_role
-- ----------------------------
INSERT INTO `his_role` VALUES ('402880ea6a7df7d6016a7e480e360001', 'druggist', 3, '药剂师', '2019-05-03 23:20:12');
INSERT INTO `his_role` VALUES ('402880ea6a7df7d6016a7e49e7b70002', 'nurseAdmin', 4, '护士管理员', '2019-05-03 23:22:13');
INSERT INTO `his_role` VALUES ('402880ea6a7df7d6016a7e4b18130003', 'technologyDoc', 5, '医技师', '2019-05-03 23:23:31');
INSERT INTO `his_role` VALUES ('402880ea6a7df7d6016a7e4d6f480004', 'tollCollector', 6, '划价收费员', '2019-05-03 23:26:05');
INSERT INTO `his_role` VALUES ('402880ea6a7df7d6016a7e4eb6360005', 'drugStoreAdmin', 7, '药房管理员', '2019-05-03 23:27:28');
INSERT INTO `his_role` VALUES ('402881e76a0b39e5016a0b3de7de0002', 'register', 2, '挂号员', '2019-04-11 15:12:47');
INSERT INTO `his_role` VALUES ('8ad8a0ec6a06851e016a068ab7b30000', 'admin', 0, '管理员', '2019-04-10 17:18:35');
INSERT INTO `his_role` VALUES ('8ad8a0ec6a06851e016a068b90480001', 'mdoctor', 1, '门诊医生', '2019-04-10 17:19:30');

-- ----------------------------
-- Table structure for his_user
-- ----------------------------
DROP TABLE IF EXISTS `his_user`;
CREATE TABLE `his_user`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `plain_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ' ',
  `create_datetime` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `political_status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `allow_num` int(10) NULL DEFAULT NULL,
  `department` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `grade` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `treatment_price` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `work_address` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `work_date_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `work_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email_status` int(11) NULL DEFAULT NULL,
  `validate_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `department_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `now_num` int(11) NULL DEFAULT NULL,
  `update_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of his_user
-- ----------------------------
INSERT INTO `his_user` VALUES ('402880e86a24ccfc016a24cd6b240000', 'register@shospital.com', '王挂号', '123', 'b03ecbe66599f4451225edf293ffc8ae', '50db0e033e10810bc1a3c8fc6d2de7f9', '2019-04-16 14:20:03', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `his_user` VALUES ('402880e86a24ccfc016a24e8e12a000b', 'register_2@shospital.com', 'register_2', '123', '7e93221630869a886899755ad3ae9fff', '611b5b68776ee4a8cb297a716ccc0b55', '2019-04-16 14:49:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `his_user` VALUES ('402880ea6a7df7d6016a7e5806bf0006', 'm_doctor_2@shospital.com', '李医生', '123', '97c7a1fc1f3e8c2e0c40d7b13b13a994', 'a45abce549ccbe83c5b229232e12ad5b', '2019-05-03 23:37:39', NULL, NULL, NULL, NULL, NULL, 10, '消化内科', NULL, '3.0', '三楼311', NULL, NULL, 1, 'A45ABCE549CCBE83C5B229232E12AD5B', '普通门诊', 0, '2019-06-04');
INSERT INTO `his_user` VALUES ('402880ea6a7df7d6016a7e5ce3810008', 'm_doctor_3@shospital.com', '张医生', '123', '6669a63f1921092662fa61c7346ef578', '1f9c8b259edc93053702bc1af5ebc9a0', '2019-05-03 23:42:57', NULL, NULL, NULL, NULL, NULL, 3, '急诊科', NULL, '5.0', '一楼101', NULL, NULL, 1, '1F9C8B259EDC93053702BC1AF5EBC9A0', '急诊', 0, '2019-06-04');
INSERT INTO `his_user` VALUES ('402881ea6abf196a016abf2fbb5e0003', 'm_druggist_1@shospital.com', '黄药剂', '123', 'ebc444f9cf5d0362d2b574406bf6a37d', 'bcaf9c29f362cda8b2b9d8031b799078', '2019-05-16 13:48:55', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'BCAF9C29F362CDA8B2B9D8031B799078', NULL, NULL, '2019-06-05');
INSERT INTO `his_user` VALUES ('402881ea6abf196a016abf40b8400005', 'm_tollCollector_1@shospital.com', '赵收费', '123', '732c0809461dd032d9d068dc9a30d93f', 'eb1f263c26e8c1ad016d0a38e9798bf9', '2019-05-16 14:07:30', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'EB1F263C26E8C1AD016D0A38E9798BF9', NULL, NULL, NULL);
INSERT INTO `his_user` VALUES ('402881ea6aca4965016aca6a54d0000a', 'm_technologyDoc_1@shospital.com', '孙检查', '123', 'f907bfea4569408325280fff74600cc3', 'ec1f7da3ecbc4781ebb67a02a7304093', '2019-05-18 18:08:47', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'EC1F7DA3ECBC4781EBB67A02A7304093', NULL, NULL, NULL);
INSERT INTO `his_user` VALUES ('4028b8816b01e30d016b01f2941f0000', 'drugStoreAdmin_1@shospital.com', '邓药管', '123', '1bc6b0bc8a97edf39264438ebd36455c', '35eb9694392fe78cca6cca2578dba9e1', '2019-05-29 12:56:43', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, '35EB9694392FE78CCA6CCA2578DBA9E1', NULL, NULL, NULL);
INSERT INTO `his_user` VALUES ('8ad8a0ec6a06851e016a069172fa0002', '1208585122@qq.com', '薛管理', '123', '3b6859e0f808a88b350b9878cd4e8ff5', '0d97c0c016d4759268b75a25d6eb4eb5', '2019-04-10 17:25:56', 'wwwww', '团员', '15202843353', '1996-09-24', '男', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '2019-05-18');
INSERT INTO `his_user` VALUES ('8ad8a0ec6a0a7e7b016a0a7ed6e50000', 'm_doctor@shospital.com', '彭医生', '123', '53cde41aa04572f1bbbb1184195c492c', '171a609704cecaa4e78ec7d51b71fd8c', '2019-04-11 11:44:05', NULL, NULL, NULL, NULL, NULL, 10, '呼吸内科', NULL, '2.0', '三楼302', NULL, NULL, 1, NULL, '普通门诊', 1, '2019-06-09');

-- ----------------------------
-- Table structure for his_user_role
-- ----------------------------
DROP TABLE IF EXISTS `his_user_role`;
CREATE TABLE `his_user_role`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `uid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `desciption` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱+角色描述',
  `create_datetime` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_status` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKonj60rt9n1rofgopjmongdc0d`(`role_id`) USING BTREE,
  INDEX `FKnq3n6y2wd8002bgn0rcs4f96y`(`uid`) USING BTREE,
  CONSTRAINT `FKnq3n6y2wd8002bgn0rcs4f96y` FOREIGN KEY (`uid`) REFERENCES `his_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKonj60rt9n1rofgopjmongdc0d` FOREIGN KEY (`role_id`) REFERENCES `his_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of his_user_role
-- ----------------------------
INSERT INTO `his_user_role` VALUES ('402880e56a6ee7ff016a6eeb83dc0001', '8ad8a0ec6a06851e016a069172fa0002', '8ad8a0ec6a06851e016a068b90480001', '1208585122@qq.com#mdoctor', '2019-04-30 23:44:49', 1);
INSERT INTO `his_user_role` VALUES ('402880e56a6f2253016a6f22b8ec0000', '8ad8a0ec6a06851e016a069172fa0002', '402881e76a0b39e5016a0b3de7de0002', '1208585122@qq.com#register', '2019-05-01 00:45:07', 1);
INSERT INTO `his_user_role` VALUES ('402880e86a24ccfc016a24cd6b7b0001', '402880e86a24ccfc016a24cd6b240000', '402881e76a0b39e5016a0b3de7de0002', 'register@shospital.com#register', '2019-04-16 14:20:03', 1);
INSERT INTO `his_user_role` VALUES ('402880e86a24ccfc016a24e8e142000c', '402880e86a24ccfc016a24e8e12a000b', '402881e76a0b39e5016a0b3de7de0002', 'register_2@shospital.com#register', '2019-04-16 14:50:02', 0);
INSERT INTO `his_user_role` VALUES ('402880ea6a7df7d6016a7e5806d70007', '402880ea6a7df7d6016a7e5806bf0006', '8ad8a0ec6a06851e016a068b90480001', 'm_doctor_2@shospital.com#mdoctor', '2019-05-03 23:37:39', 1);
INSERT INTO `his_user_role` VALUES ('402880ea6a7df7d6016a7e5ce38a0009', '402880ea6a7df7d6016a7e5ce3810008', '8ad8a0ec6a06851e016a068b90480001', 'm_doctor_3@shospital.com#mdoctor', '2019-05-03 23:42:57', 0);
INSERT INTO `his_user_role` VALUES ('402880ed6aaa5655016aaa77ef260000', '8ad8a0ec6a06851e016a069172fa0002', '402880ea6a7df7d6016a7e480e360001', '1208585122@qq.com#druggist', '2019-05-12 13:15:47', -1);
INSERT INTO `his_user_role` VALUES ('402881ea6abf196a016abf2fbb750004', '402881ea6abf196a016abf2fbb5e0003', '402880ea6a7df7d6016a7e480e360001', 'm_druggist_1@shospital.com#druggist', '2019-05-16 13:48:57', 1);
INSERT INTO `his_user_role` VALUES ('402881ea6abf196a016abf40b8570006', '402881ea6abf196a016abf40b8400005', '402880ea6a7df7d6016a7e4d6f480004', 'm_tollCollector_1@shospital.com#tollCollector', '2019-05-16 14:07:30', 1);
INSERT INTO `his_user_role` VALUES ('402881ea6aca4965016aca6a54e4000b', '402881ea6aca4965016aca6a54d0000a', '402880ea6a7df7d6016a7e4b18130003', 'm_technologyDoc_1@shospital.com#technologyDoc', '2019-05-18 18:08:47', 1);
INSERT INTO `his_user_role` VALUES ('4028b8816b01e30d016b01f294580001', '4028b8816b01e30d016b01f2941f0000', '402880ea6a7df7d6016a7e4eb6360005', 'drugStoreAdmin_1@shospital.com#drugStoreAdmin', '2019-05-29 12:56:43', 1);
INSERT INTO `his_user_role` VALUES ('8ad8a0ec6a06851e016a069173580003', '8ad8a0ec6a06851e016a069172fa0002', '8ad8a0ec6a06851e016a068ab7b30000', '1208585122@qq.com#admin', '2019-04-10 17:25:56', 1);
INSERT INTO `his_user_role` VALUES ('8ad8a0ec6a0a7e7b016a0a7ed7630001', '8ad8a0ec6a0a7e7b016a0a7ed6e50000', '8ad8a0ec6a06851e016a068b90480001', 'm_doctor@shospital.com#mdoctor', '2019-04-11 11:44:05', 1);

SET FOREIGN_KEY_CHECKS = 1;
