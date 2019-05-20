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
 * 微信
 * </p>
 *
 * @author 徐晓伟
 * @since 2019-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WeChat implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "we_caht_id", type = IdType.AUTO)
    private Integer weCahtId;

    /**
     * 用户的标识，对当前公众号唯一
     */
    @TableField("openid")
    private String openid;

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    @TableField("unionid")
    private String unionid;

    /**
     * 用户的昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 用户所在省份
     */
    @TableField("province")
    private String province;

    /**
     * 用户所在城市
     */
    @TableField("city")
    private String city;

    /**
     * 用户所在国家
     */
    @TableField("country")
    private String country;

    /**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
     */
    @TableField("headimgurl")
    private String headimgurl;

    /**
     * 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
     */
    @TableField("privilege")
    private String privilege;

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


    public static final String WE_CAHT_ID = "we_caht_id";

    public static final String OPENID = "openid";

    public static final String UNIONID = "unionid";

    public static final String NICKNAME = "nickname";

    public static final String SEX = "sex";

    public static final String PROVINCE = "province";

    public static final String CITY = "city";

    public static final String COUNTRY = "country";

    public static final String HEADIMGURL = "headimgurl";

    public static final String PRIVILEGE = "privilege";

    public static final String CREATE_DATE = "create_date";

    public static final String UPDATE_DATE = "update_date";

    public static final String DELETED = "deleted";

    public static final String REMARKS = "remarks";

}
