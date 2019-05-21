package cn.com.xuxiaowei.security.entity;

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
 * 微博
 * </p>
 *
 * @author 徐晓伟
 * @since 2019-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WeiBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 微博主键
     */
    @TableId(value = "wei_bo_id", type = IdType.AUTO)
    private Integer weiBoId;

    /**
     * 用户UID
     */
    @TableField("id")
    private String id;

    /**
     * 微博昵称
     */
    @TableField("screen_name")
    private String screenName;

    /**
     * 友好显示名称，如Bill Gates,名称中间的空格正常显示(此特性暂不支持)
     */
    @TableField("name")
    private String name;

    /**
     * 省份编码（参考省份编码表）
     */
    @TableField("province")
    private Integer province;

    /**
     * 城市编码（参考城市编码表）
     */
    @TableField("city")
    private Integer city;

    /**
     * 地址
     */
    @TableField("location")
    private String location;

    /**
     * 个人描述
     */
    @TableField("description")
    private String description;

    /**
     * 用户博客地址
     */
    @TableField("url")
    private String url;

    /**
     * 自定义图像
     */
    @TableField("profile_image_url")
    private String profileImageUrl;

    /**
     * 用户个性化URL
     */
    @TableField("user_domain")
    private String userDomain;

    /**
     * 性别,m--男，f--女,n--未知
     */
    @TableField("gender")
    private String gender;

    /**
     * 粉丝数
     */
    @TableField("followers_count")
    private Integer followersCount;

    /**
     * 关注数
     */
    @TableField("friends_count")
    private Integer friendsCount;

    /**
     * 微博数
     */
    @TableField("statuses_count")
    private Integer statusesCount;

    /**
     * 收藏数
     */
    @TableField("favourites_count")
    private Integer favouritesCount;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 保留字段,是否已关注(此特性暂不支持)
     */
    @TableField("following")
    private Boolean following;

    /**
     * 加V标示，是否微博认证用户
     */
    @TableField("verified")
    private Boolean verified;

    /**
     * 认证类型
     */
    @TableField("verified_type")
    private Integer verifiedType;

    /**
     * 是否允许所有人给我发私信
     */
    @TableField("allow_all_act_msg")
    private Integer allowAllActMsg;

    /**
     * 是否允许所有人对我的微博进行评论
     */
    @TableField("allow_all_comment")
    private Boolean allowAllComment;

    /**
     * 此用户是否关注我
     */
    @TableField("follow_me")
    private Boolean followMe;

    /**
     * 大头像地址
     */
    @TableField("avatarLarge")
    private String avatarLarge;

    /**
     * 用户在线状态
     */
    @TableField("online_status")
    private Integer onlineStatus;

    /**
     * 用户最新一条微博
     */
    @TableField("status")
    private String status;

    /**
     * 互粉数
     */
    @TableField("bi_followers_count")
    private Integer biFollowersCount;

    /**
     * 备注信息，在查询用户关系时提供此字段。
     */
    @TableField("remark")
    private String remark;

    /**
     * 用户语言版本
     */
    @TableField("lang")
    private String lang;

    /**
     * 认证原因
     */
    @TableField("verified_reason")
    private String verifiedReason;

    /**
     * 微號
     */
    @TableField("weihao")
    private String weihao;

    @TableField("statusId")
    private String statusId;

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


    public static final String WEI_BO_ID = "wei_bo_id";

    public static final String ID = "id";

    public static final String SCREEN_NAME = "screen_name";

    public static final String NAME = "name";

    public static final String PROVINCE = "province";

    public static final String CITY = "city";

    public static final String LOCATION = "location";

    public static final String DESCRIPTION = "description";

    public static final String URL = "url";

    public static final String PROFILE_IMAGE_URL = "profile_image_url";

    public static final String USER_DOMAIN = "user_domain";

    public static final String GENDER = "gender";

    public static final String FOLLOWERS_COUNT = "followers_count";

    public static final String FRIENDS_COUNT = "friends_count";

    public static final String STATUSES_COUNT = "statuses_count";

    public static final String FAVOURITES_COUNT = "favourites_count";

    public static final String CREATED_AT = "created_at";

    public static final String FOLLOWING = "following";

    public static final String VERIFIED = "verified";

    public static final String VERIFIED_TYPE = "verified_type";

    public static final String ALLOW_ALL_ACT_MSG = "allow_all_act_msg";

    public static final String ALLOW_ALL_COMMENT = "allow_all_comment";

    public static final String FOLLOW_ME = "follow_me";

    public static final String AVATARLARGE = "avatarLarge";

    public static final String ONLINE_STATUS = "online_status";

    public static final String STATUS = "status";

    public static final String BI_FOLLOWERS_COUNT = "bi_followers_count";

    public static final String REMARK = "remark";

    public static final String LANG = "lang";

    public static final String VERIFIED_REASON = "verified_reason";

    public static final String WEIHAO = "weihao";

    public static final String STATUSID = "statusId";

    public static final String ACCESS_TOKEN = "access_token";

    public static final String ACCESS_TOKEN_EXPIRED_DATE = "access_token_expired_date";

    public static final String REFRESH_TOKEN = "refresh_token";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    public static final String DELETED = "deleted";

    public static final String REMARKS = "remarks";

}
