package cn.ozawa.layuiproject.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author : zheng.xiong
 * @version : 1.0
 * @date : Created at 2022/03/03
 * @description : cn.ozawa.layuiproject.entity.vo
 */
@Data
public class AwxAccountQrcodeVO {

    @ApiModelProperty("二维码类型 永久二维码，临时二维码，关注门店二维码")
    private String type;

    @ApiModelProperty("二维码名称")
    private String qrcodeName;

    @ApiModelProperty("有效时间（秒）")
    private Integer expireTime;

    @ApiModelProperty("生成时间")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime createTime;

    @ApiModelProperty("解析URL")
    private String url;

    @ApiModelProperty("创建人")
    private String createId;
}
