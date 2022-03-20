package cn.ozawa.layuiproject.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : zheng.xiong
 * @version : 1.0
 * @date : Created at 2022/03/09
 * @description : cn.ozawa.layuiproject.entity.vo
 */
@Data
public class AwxRedirectUrlVO {

    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("二维码名称")
    private String qrcodeName;

    @ApiModelProperty("活动类型（1 判断是否关注点 2 不判断是否关注店）")
    private String activityType;

    @ApiModelProperty("选择关注店文案")
    @TableField("choose_memo")
    private String chooseMemo;

    @ApiModelProperty("活动链接")
    private String url;

    @ApiModelProperty("链接类型（1 h5链接 2 小程序链接）")
    private String urlType;

    @ApiModelProperty("链接说明")
    private String directMemo;

    @ApiModelProperty("小程序id")
    private String appid;

    @ApiModelProperty("开始时间")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginDate;

    @ApiModelProperty("结束时间")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @ApiModelProperty("创建人")
    private String createdUser;
}
