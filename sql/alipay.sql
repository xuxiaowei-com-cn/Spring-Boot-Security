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

 Date: 21/05/2019 17:49:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alipay
-- ----------------------------
DROP TABLE IF EXISTS `alipay`;
CREATE TABLE `alipay`  (
  `alipay_id` int(11) NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '详细地址。',
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '区县名称。',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户头像地址',
  `business_scope` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '经营/业务范围（用户类型是公司类型时才有此字段）。',
  `cert_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '【注意】只有is_certified为T的时候才有意义，否则不保证准确性.\r\n证件号码，结合证件类型使用.',
  `cert_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '【注意】只有is_certified为T的时候才有意义，否则不保证准确性.\r\n0:身份证\r\n1:护照\r\n2:军官证\r\n3:士兵证\r\n4:回乡证\r\n5:临时身份证\r\n6:户口簿\r\n7:警官证\r\n8:台胞证\r\n9:营业执照\r\n10:其它证件\r\n11:港澳居民来往内地通行证\r\n12:台湾居民来往大陆通行证',
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '市名称。',
  `college_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '学信网返回的学校名称，有可能为空。',
  `country_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '国家码',
  `degree` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '学信网返回的学历/学位信息，数据质量一般，请谨慎使用，取值包括：博士研究生、硕士研究生、高升本、专科、博士、硕士、本科、夜大电大函大普通班、专科(高职)、第二学士学位。',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户支付宝邮箱登录名',
  `enrollment_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '入学时间，yyyy-mm-dd格式',
  `firm_agent_person_cert_expiry_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '企业代理人证件有效期（用户类型是公司类型时才有此字段）。',
  `firm_agent_person_cert_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '企业代理人证件号码（用户类型是公司类型时才有此字段）。',
  `firm_agent_person_cert_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '企业代理人证件类型, 返回值参考cert_type字段（用户类型是公司类型时才有此字段）。',
  `firm_agent_person_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '企业代理人姓名（用户类型是公司类型时才有此字段）。',
  `firm_legal_person_cert_expiry_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '企业法人证件有效期（用户类型是公司类型时才有此字段）。',
  `firm_legal_person_cert_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '法人证件号码（用户类型是公司类型时才有此字段）。',
  `firm_legal_person_cert_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '企业法人证件类型, 返回值参考cert_type字段（用户类型是公司类型时才有此字段）。',
  `firm_legal_person_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '企业法人名称（用户类型是公司类型时才有此字段）。',
  `firm_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '公司类型，包括（用户类型是公司类型时才有此字段）：\r\nCO(公司)\r\nINST(事业单位),\r\nCOMM(社会团体),\r\nNGO(民办非企业组织),\r\nSTATEORGAN(党政国家机关)',
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '【注意】只有is_certified为T的时候才有意义，否则不保证准确性.\r\n性别（F：女性；M：男性）。',
  `graduation_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '预期毕业时间，不保证准确性，yyyy-mm-dd格式。',
  `is_balance_frozen` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '余额账户是否被冻结。\r\nT--被冻结；F--未冻结',
  `is_certified` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否通过实名认证。T是通过 F是没有实名认证。',
  `is_student_certified` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否是学生',
  `license_expiry_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '营业执照有效期，yyyyMMdd或长期，（用户类型是公司类型时才有此字段）。',
  `license_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '企业执照号码（用户类型是公司类型时才有此字段）。',
  `member_grade` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '支付宝会员等级',
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号码。',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户昵称',
  `organization_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组织机构代码（用户类型是公司类型时才有此字段）。',
  `person_birthday` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '个人用户生日。',
  `person_cert_expiry_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '证件有效期（用户类型是个人的时候才有此字段）。',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '电话号码。',
  `profession` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '职业',
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '省份名称',
  `taobao_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '淘宝id',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '支付宝用户的userId',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '【注意】只有is_certified为T的时候才有意义，否则不保证准确性.\r\n若用户是个人用户，则是用户的真实姓名；若是企业用户，则是企业名称。',
  `user_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户状态（Q/T/B/W）。\r\nQ代表快速注册用户\r\nT代表已认证用户\r\nB代表被冻结账户\r\nW代表已注册，未激活的账户',
  `user_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户类型（1/2）\r\n1代表公司账户2代表个人账户',
  `zip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮政编码。',
  `access_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '访问令牌。通过该令牌调用需要授权类接口',
  `access_token_expired_date` datetime(0) DEFAULT NULL COMMENT '访问令牌过期时间',
  `refresh_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '刷新令牌。通过该令牌可以刷新access_token',
  `refresh_token_expired_date` datetime(0) DEFAULT NULL COMMENT '刷新令牌过期时间',
  `create_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，自动生成，无需填写并禁止修改。',
  `update_date` datetime(0) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间，自动生成，无需填写并禁止修改。',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除，0 未删除，1 删除，MySQL 默认值 0，不为 NULL，注解@TableLogic。',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '数据备注。',
  PRIMARY KEY (`alipay_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '支付宝' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
