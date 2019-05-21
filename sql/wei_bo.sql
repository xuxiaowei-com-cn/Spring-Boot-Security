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

 Date: 21/05/2019 23:38:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wei_bo
-- ----------------------------
DROP TABLE IF EXISTS `wei_bo`;
CREATE TABLE `wei_bo`  (
  `wei_bo_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '微博主键',
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户UID',
  `screen_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '微博昵称',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '友好显示名称，如Bill Gates,名称中间的空格正常显示(此特性暂不支持)',
  `province` int(11) DEFAULT NULL COMMENT '省份编码（参考省份编码表）',
  `city` int(11) DEFAULT NULL COMMENT '城市编码（参考城市编码表）',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '地址',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '个人描述',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户博客地址',
  `profile_image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '自定义图像',
  `user_domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户个性化URL',
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '性别,m--男，f--女,n--未知',
  `followers_count` int(11) DEFAULT NULL COMMENT '粉丝数',
  `friends_count` int(11) DEFAULT NULL COMMENT '关注数',
  `statuses_count` int(11) DEFAULT NULL COMMENT '微博数',
  `favourites_count` int(11) DEFAULT NULL COMMENT '收藏数',
  `created_at` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `following` tinyint(1) DEFAULT NULL COMMENT '保留字段,是否已关注(此特性暂不支持)',
  `verified` tinyint(1) DEFAULT NULL COMMENT '加V标示，是否微博认证用户',
  `verified_type` int(11) DEFAULT NULL COMMENT '认证类型',
  `allow_all_act_msg` tinyint(11) DEFAULT NULL COMMENT '是否允许所有人给我发私信',
  `allow_all_comment` tinyint(1) DEFAULT NULL COMMENT '是否允许所有人对我的微博进行评论',
  `follow_me` tinyint(1) DEFAULT NULL COMMENT '此用户是否关注我',
  `avatarLarge` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '大头像地址',
  `online_status` int(11) DEFAULT NULL COMMENT '用户在线状态',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户最新一条微博',
  `bi_followers_count` int(11) DEFAULT NULL COMMENT '互粉数',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注信息，在查询用户关系时提供此字段。',
  `lang` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户语言版本',
  `verified_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '认证原因',
  `weihao` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '微號',
  `statusId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `access_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '访问令牌。通过该令牌调用需要授权类接口',
  `access_token_expired_date` datetime(0) DEFAULT NULL COMMENT '访问令牌过期时间',
  `refresh_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '刷新令牌。通过该令牌可以刷新access_token',
  `create_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，自动生成，无需填写并禁止修改。',
  `update_date` datetime(0) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间，自动生成，无需填写并禁止修改。',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除，0 未删除，1 删除，MySQL 默认值 0，不为 NULL，注解@TableLogic。',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '数据备注。',
  PRIMARY KEY (`wei_bo_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '微博' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
