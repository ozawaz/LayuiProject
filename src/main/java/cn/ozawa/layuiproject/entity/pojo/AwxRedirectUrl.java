package cn.ozawa.layuiproject.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author ozawa
 * @since 2022-03-09
 */
@Getter
@Setter
@TableName("awx_redirect_url")
@ApiModel(value = "AwxRedirectUrl对象")
public class AwxRedirectUrl implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("二维码名称")
    @TableField("qrcode_name")
    private String qrcodeName;

    @ApiModelProperty("活动名称")
    @TableField("activity_name")
    private String activityName;

    @ApiModelProperty("活动类型（1 判断是否关注点 2 不判断是否关注店）")
    @TableField("activity_type")
    private Integer activityType;

    @ApiModelProperty("活动链接")
    @TableField("url")
    private String url;

    @ApiModelProperty("链接类型（1 h5链接 2 小程序链接）")
    @TableField("url_type")
    private Integer urlType;

    @ApiModelProperty("链接说明")
    @TableField("direct_memo")
    private String directMemo;

    @ApiModelProperty("小程序id")
    @TableField("appid")
    private String appid;

    @ApiModelProperty("选择关注店文案")
    @TableField("choose_memo")
    private String chooseMemo;

    @ApiModelProperty("开始时间")
    @TableField("begin_date")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginDate;

    @ApiModelProperty("结束时间")
    @TableField("end_date")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @ApiModelProperty("创建人")
    @TableField("created_user")
    private String createdUser;

    @ApiModelProperty("创建时间")
    @TableField("created_date")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @ApiModelProperty("店号")
    @TableField("store_no")
    private Integer storeNo;

    @TableField("bind_memo")
    private String bindMemo;

    @TableField("brand")
    private Integer brand;


}
