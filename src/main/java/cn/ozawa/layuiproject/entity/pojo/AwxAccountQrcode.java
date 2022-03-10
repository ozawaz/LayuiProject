package cn.ozawa.layuiproject.entity.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 微信账号二维码
 * </p>
 *
 * @author ozawa
 * @since 2022-03-03
 */
@Getter
@Setter
@TableName("awx_account_qrcode")
@ApiModel(value = "AwxAccountQrcode对象", description = "微信账号二维码")
public class AwxAccountQrcode implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("公众号ID")
    @TableField("account_id")
    private Integer accountId;

    @ApiModelProperty("二维码类型 1永久二维码，2临时二维码，3关注门店二维码")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("状态 0 未生成，1已生成")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("场景值字符串")
    @TableField("qrcode_name")
    private String qrcodeName;

    @ApiModelProperty("场景ID")
    @TableField("qrcode_id")
    private Integer qrcodeId;

    @ApiModelProperty("ticket")
    @TableField("ticket")
    private String ticket;

    @ApiModelProperty("URL")
    @TableField("url")
    private String url;

    @ApiModelProperty("有效时间（秒）")
    @TableField("expire_time")
    private Integer expireTime;

    @TableField("create_id")
    private String createId;

    @TableField("create_time")
    @ApiModelProperty(value = "开始时间")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime createTime;

    @TableField("update_id")
    private String updateId;

    @TableField("update_time")
    private LocalDateTime updateTime;


}
