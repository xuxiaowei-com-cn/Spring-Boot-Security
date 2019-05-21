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
 * QQ
 * </p>
 *
 * @author 徐晓伟
 * @since 2019-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Qq implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "qq_id", type = IdType.AUTO)
    private Integer qqId;

    @TableField("open_id")
    private String openId;

    @TableField("union_id")
    private String unionId;

    @TableField("nickname")
    private String nickname;

    @TableField("figureurl30")
    private String figureurl30;

    @TableField("figureurl50")
    private String figureurl50;

    @TableField("figureurl100")
    private String figureurl100;

    @TableField("gender")
    private String gender;

    @TableField("vip")
    private Boolean vip;

    @TableField("level")
    private Integer level;

    @TableField("yellow_year_vip")
    private Boolean yellowYearVip;

    /**
     * Access Token
     */
    @TableField("access_token")
    private String accessToken;

    /**
     * Access Token 过期时间
     */
    @TableField("access_token_expired_date")
    private LocalDateTime accessTokenExpiredDate;

    /**
     * 在授权自动续期步骤中，获取新的Access_Token时需要提供的参数。
     */
    @TableField("refres_token")
    private String refresToken;

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


    public static final String QQ_ID = "qq_id";

    public static final String OPEN_ID = "open_id";

    public static final String UNION_ID = "union_id";

    public static final String NICKNAME = "nickname";

    public static final String FIGUREURL30 = "figureurl30";

    public static final String FIGUREURL50 = "figureurl50";

    public static final String FIGUREURL100 = "figureurl100";

    public static final String GENDER = "gender";

    public static final String VIP = "vip";

    public static final String LEVEL = "level";

    public static final String YELLOW_YEAR_VIP = "yellow_year_vip";

    public static final String ACCESS_TOKEN = "access_token";

    public static final String ACCESS_TOKEN_EXPIRED_DATE = "access_token_expired_date";

    public static final String REFRES_TOKEN = "refres_token";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    public static final String DELETED = "deleted";

    public static final String REMARKS = "remarks";

}
