package cn.com.xuxiaowei.springbootsecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 支付宝
 * </p>
 *
 * @author 徐晓伟
 * @since 2019-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Alipay implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "alipay_id", type = IdType.AUTO)
    private Integer alipayId;

    /**
     * 详细地址。
     */
    @TableField("address")
    private String address;

    /**
     * 区县名称。
     */
    @TableField("area")
    private String area;

    /**
     * 用户头像地址
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 经营/业务范围（用户类型是公司类型时才有此字段）。
     */
    @TableField("business_scope")
    private String businessScope;

    /**
     * 【注意】只有is_certified为T的时候才有意义，否则不保证准确性.
     * 证件号码，结合证件类型使用.
     */
    @TableField("cert_no")
    private String certNo;

    /**
     * 【注意】只有is_certified为T的时候才有意义，否则不保证准确性.
     * 0:身份证
     * 1:护照
     * 2:军官证
     * 3:士兵证
     * 4:回乡证
     * 5:临时身份证
     * 6:户口簿
     * 7:警官证
     * 8:台胞证
     * 9:营业执照
     * 10:其它证件
     * 11:港澳居民来往内地通行证
     * 12:台湾居民来往大陆通行证
     */
    @TableField("cert_type")
    private String certType;

    /**
     * 市名称。
     */
    @TableField("city")
    private String city;

    /**
     * 学信网返回的学校名称，有可能为空。
     */
    @TableField("college_name")
    private String collegeName;

    /**
     * 国家码
     */
    @TableField("country_code")
    private String countryCode;

    /**
     * 学信网返回的学历/学位信息，数据质量一般，请谨慎使用，取值包括：博士研究生、硕士研究生、高升本、专科、博士、硕士、本科、夜大电大函大普通班、专科(高职)、第二学士学位。
     */
    @TableField("degree")
    private String degree;

    /**
     * 用户支付宝邮箱登录名
     */
    @TableField("email")
    private String email;

    /**
     * 入学时间，yyyy-mm-dd格式
     */
    @TableField("enrollment_time")
    private String enrollmentTime;

    /**
     * 企业代理人证件有效期（用户类型是公司类型时才有此字段）。
     */
    @TableField("firm_agent_person_cert_expiry_date")
    private String firmAgentPersonCertExpiryDate;

    /**
     * 企业代理人证件号码（用户类型是公司类型时才有此字段）。
     */
    @TableField("firm_agent_person_cert_no")
    private String firmAgentPersonCertNo;

    /**
     * 企业代理人证件类型, 返回值参考cert_type字段（用户类型是公司类型时才有此字段）。
     */
    @TableField("firm_agent_person_cert_type")
    private String firmAgentPersonCertType;

    /**
     * 企业代理人姓名（用户类型是公司类型时才有此字段）。
     */
    @TableField("firm_agent_person_name")
    private String firmAgentPersonName;

    /**
     * 企业法人证件有效期（用户类型是公司类型时才有此字段）。
     */
    @TableField("firm_legal_person_cert_expiry_date")
    private String firmLegalPersonCertExpiryDate;

    /**
     * 法人证件号码（用户类型是公司类型时才有此字段）。
     */
    @TableField("firm_legal_person_cert_no")
    private String firmLegalPersonCertNo;

    /**
     * 企业法人证件类型, 返回值参考cert_type字段（用户类型是公司类型时才有此字段）。
     */
    @TableField("firm_legal_person_cert_type")
    private String firmLegalPersonCertType;

    /**
     * 企业法人名称（用户类型是公司类型时才有此字段）。
     */
    @TableField("firm_legal_person_name")
    private String firmLegalPersonName;

    /**
     * 公司类型，包括（用户类型是公司类型时才有此字段）：
     * CO(公司)
     * INST(事业单位),
     * COMM(社会团体),
     * NGO(民办非企业组织),
     * STATEORGAN(党政国家机关)
     */
    @TableField("firm_type")
    private String firmType;

    /**
     * 【注意】只有is_certified为T的时候才有意义，否则不保证准确性.
     * 性别（F：女性；M：男性）。
     */
    @TableField("gender")
    private String gender;

    /**
     * 预期毕业时间，不保证准确性，yyyy-mm-dd格式。
     */
    @TableField("graduation_time")
    private String graduationTime;

    /**
     * 余额账户是否被冻结。
     * T--被冻结；F--未冻结
     */
    @TableField("is_balance_frozen")
    private String isBalanceFrozen;

    /**
     * 是否通过实名认证。T是通过 F是没有实名认证。
     */
    @TableField("is_certified")
    private String isCertified;

    /**
     * 是否是学生
     */
    @TableField("is_student_certified")
    private String isStudentCertified;

    /**
     * 营业执照有效期，yyyyMMdd或长期，（用户类型是公司类型时才有此字段）。
     */
    @TableField("license_expiry_date")
    private String licenseExpiryDate;

    /**
     * 企业执照号码（用户类型是公司类型时才有此字段）。
     */
    @TableField("license_no")
    private String licenseNo;

    /**
     * 支付宝会员等级
     */
    @TableField("member_grade")
    private String memberGrade;

    /**
     * 手机号码。
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 用户昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 组织机构代码（用户类型是公司类型时才有此字段）。
     */
    @TableField("organization_code")
    private String organizationCode;

    /**
     * 个人用户生日。
     */
    @TableField("person_birthday")
    private String personBirthday;

    /**
     * 证件有效期（用户类型是个人的时候才有此字段）。
     */
    @TableField("person_cert_expiry_date")
    private String personCertExpiryDate;

    /**
     * 电话号码。
     */
    @TableField("phone")
    private String phone;

    /**
     * 职业
     */
    @TableField("profession")
    private String profession;

    /**
     * 省份名称
     */
    @TableField("province")
    private String province;

    /**
     * 淘宝id
     */
    @TableField("taobao_id")
    private String taobaoId;

    /**
     * 支付宝用户的userId
     */
    @TableField("user_id")
    private String userId;

    /**
     * 【注意】只有is_certified为T的时候才有意义，否则不保证准确性.
     * 若用户是个人用户，则是用户的真实姓名；若是企业用户，则是企业名称。
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户状态（Q/T/B/W）。
     * Q代表快速注册用户
     * T代表已认证用户
     * B代表被冻结账户
     * W代表已注册，未激活的账户
     */
    @TableField("user_status")
    private String userStatus;

    /**
     * 用户类型（1/2）
     * 1代表公司账户2代表个人账户
     */
    @TableField("user_type")
    private String userType;

    /**
     * 邮政编码。
     */
    @TableField("zip")
    private String zip;

    /**
     * 访问令牌。通过该令牌调用需要授权类接口
     */
    @TableField("access_token")
    private String accessToken;

    /**
     * 访问令牌过期时间
     */
    @TableField("access_token_expired_date")
    private LocalDateTime accessTokenExpiredDate;

    /**
     * 刷新令牌。通过该令牌可以刷新access_token
     */
    @TableField("refresh_token")
    private String refreshToken;

    /**
     * 刷新令牌过期时间
     */
    @TableField("refresh_token_expired_date")
    private LocalDateTime refreshTokenExpiredDate;

    /**
     * 创建时间，自动生成，无需填写并禁止修改。
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    /**
     * 更新时间，自动生成，无需填写并禁止修改。
     */
    @TableField("update_date")
    private LocalDateTime updateDate;

    /**
     * 逻辑删除，0 未删除，1 删除，MySQL 默认值 0，不为 NULL，注解@TableLogic。
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;

    /**
     * 数据备注。
     */
    @TableField("remarks")
    private String remarks;


    public static final String ALIPAY_ID = "alipay_id";

    public static final String ADDRESS = "address";

    public static final String AREA = "area";

    public static final String AVATAR = "avatar";

    public static final String BUSINESS_SCOPE = "business_scope";

    public static final String CERT_NO = "cert_no";

    public static final String CERT_TYPE = "cert_type";

    public static final String CITY = "city";

    public static final String COLLEGE_NAME = "college_name";

    public static final String COUNTRY_CODE = "country_code";

    public static final String DEGREE = "degree";

    public static final String EMAIL = "email";

    public static final String ENROLLMENT_TIME = "enrollment_time";

    public static final String FIRM_AGENT_PERSON_CERT_EXPIRY_DATE = "firm_agent_person_cert_expiry_date";

    public static final String FIRM_AGENT_PERSON_CERT_NO = "firm_agent_person_cert_no";

    public static final String FIRM_AGENT_PERSON_CERT_TYPE = "firm_agent_person_cert_type";

    public static final String FIRM_AGENT_PERSON_NAME = "firm_agent_person_name";

    public static final String FIRM_LEGAL_PERSON_CERT_EXPIRY_DATE = "firm_legal_person_cert_expiry_date";

    public static final String FIRM_LEGAL_PERSON_CERT_NO = "firm_legal_person_cert_no";

    public static final String FIRM_LEGAL_PERSON_CERT_TYPE = "firm_legal_person_cert_type";

    public static final String FIRM_LEGAL_PERSON_NAME = "firm_legal_person_name";

    public static final String FIRM_TYPE = "firm_type";

    public static final String GENDER = "gender";

    public static final String GRADUATION_TIME = "graduation_time";

    public static final String IS_BALANCE_FROZEN = "is_balance_frozen";

    public static final String IS_CERTIFIED = "is_certified";

    public static final String IS_STUDENT_CERTIFIED = "is_student_certified";

    public static final String LICENSE_EXPIRY_DATE = "license_expiry_date";

    public static final String LICENSE_NO = "license_no";

    public static final String MEMBER_GRADE = "member_grade";

    public static final String MOBILE = "mobile";

    public static final String NICK_NAME = "nick_name";

    public static final String ORGANIZATION_CODE = "organization_code";

    public static final String PERSON_BIRTHDAY = "person_birthday";

    public static final String PERSON_CERT_EXPIRY_DATE = "person_cert_expiry_date";

    public static final String PHONE = "phone";

    public static final String PROFESSION = "profession";

    public static final String PROVINCE = "province";

    public static final String TAOBAO_ID = "taobao_id";

    public static final String USER_ID = "user_id";

    public static final String USER_NAME = "user_name";

    public static final String USER_STATUS = "user_status";

    public static final String USER_TYPE = "user_type";

    public static final String ZIP = "zip";

    public static final String ACCESS_TOKEN = "access_token";

    public static final String ACCESS_TOKEN_EXPIRED_DATE = "access_token_expired_date";

    public static final String REFRESH_TOKEN = "refresh_token";

    public static final String REFRESH_TOKEN_EXPIRED_DATE = "refresh_token_expired_date";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    public static final String DELETED = "deleted";

    public static final String REMARKS = "remarks";

}
