/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 20/05/2019 02:23:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '昵称',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `create_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，自动生成，无需填写并禁止修改。',
  `update_date` datetime(0) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间，自动生成，无需填写并禁止修改。',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除，0 未删除，1 删除，MySQL 默认值 0，不为 NULL，注解@TableLogic。',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '数据备注。',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户表（测试）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'xuxiaowei', '徐晓伟', NULL, 'a9cd53fdfd1b4a95953cb25ac50bc693', 21, '2019-05-17 20:18:48', '2019-05-17 20:59:46', 0, '测试数据');
INSERT INTO `user` VALUES (2, 'zhangfei', '张飞', NULL, '788b5ccaf9434e458348da2ba6f1402f', 21, '2019-05-17 20:18:48', '2019-05-17 21:19:40', 0, '测试数据');
INSERT INTO `user` VALUES (3, 'yuefei', '岳飞', NULL, '7e2f2f562bbf49538ecc46f5f3ce5334', 47, '2019-05-17 20:18:48', '2019-05-17 21:39:02', 1, '测试数据');
INSERT INTO `user` VALUES (4, 'liuxiang', '刘翔', NULL, '1fb7bb48c188455f8d64a5773f028233', 11, '2019-05-17 20:18:48', '2019-05-17 20:59:46', 0, '测试数据');
INSERT INTO `user` VALUES (5, 'zhangliang', '张良', NULL, 'c33a7f3a513348ee8db519c1ddbde9b0', 46, '2019-05-17 20:18:48', '2019-05-17 21:39:02', 1, '测试数据');
INSERT INTO `user` VALUES (6, 'zhaokuangyin', '赵匡胤', NULL, '5bf22f99b0514c558736f4a5bf07375a', 43, '2019-05-17 20:18:48', '2019-05-17 21:39:02', 1, '测试数据');
INSERT INTO `user` VALUES (7, 'kongming', '孔明', NULL, '277354bac9164e2b8061ef543c50a2a2', 13, '2019-05-17 20:18:48', '2019-05-17 20:59:46', 0, '测试数据');
INSERT INTO `user` VALUES (8, 'liubei', '刘备', NULL, '34ff168a7398404588d989f5ea8798ac', 40, '2019-05-17 20:18:48', '2019-05-17 21:39:02', 1, '测试数据');
INSERT INTO `user` VALUES (9, 'sunbin', '孙斌', NULL, 'b5fa1f35db4f4043be31a99a0e624773', 26, '2019-05-17 20:18:48', '2019-05-17 20:59:46', 0, '测试数据');
INSERT INTO `user` VALUES (10, 'zhangwei', '张伟', NULL, '8315a3784fb54fd080ae5bae5987a06a', 24, '2019-05-17 20:18:48', '2019-05-17 20:59:46', 0, '测试数据');
INSERT INTO `user` VALUES (11, 'wangfei', '王菲', NULL, 'e04347400fe740cd8ded068040929093', 39, '2019-05-17 20:18:48', '2019-05-17 21:39:02', 1, '测试数据');
INSERT INTO `user` VALUES (12, NULL, 'AAAAA', NULL, NULL, NULL, '2019-05-17 21:25:56', NULL, 0, NULL);
INSERT INTO `user` VALUES (13, NULL, 'AAAAA', NULL, NULL, NULL, '2019-05-17 21:26:35', NULL, 0, NULL);
INSERT INTO `user` VALUES (14, NULL, 'AAAAA', NULL, NULL, NULL, '2019-05-17 21:26:35', NULL, 0, NULL);
INSERT INTO `user` VALUES (15, NULL, 'AAAAA', NULL, NULL, NULL, '2019-05-17 21:26:36', NULL, 0, NULL);
INSERT INTO `user` VALUES (16, NULL, 'AAAAA', NULL, NULL, NULL, '2019-05-17 21:26:36', NULL, 0, NULL);
INSERT INTO `user` VALUES (17, NULL, 'AAAAA', NULL, NULL, NULL, '2019-05-17 21:26:36', NULL, 0, NULL);
INSERT INTO `user` VALUES (18, NULL, 'AAAAA', NULL, NULL, NULL, '2019-05-17 21:26:36', '2019-05-17 21:29:47', 1, NULL);

SET FOREIGN_KEY_CHECKS = 1;
