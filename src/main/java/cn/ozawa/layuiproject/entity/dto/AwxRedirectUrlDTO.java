package cn.ozawa.layuiproject.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : zheng.xiong
 * @version : 1.0
 * @date : Created at 2022/03/20
 * @description : cn.ozawa.layuiproject.entity.dto
 */
@Data
public class AwxRedirectUrlDTO {

    @ApiModelProperty("开始时间")
    @TableField("begin_date")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginDate;

    @ApiModelProperty("结束时间")
    @TableField("end_date")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
}
