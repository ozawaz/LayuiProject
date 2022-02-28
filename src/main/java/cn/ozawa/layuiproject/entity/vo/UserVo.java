package cn.ozawa.layuiproject.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author : zheng.xiong
 * @version : 1.0
 * @date : Created at 2022/02/28
 * @description : cn.ozawa.layuiproject.entity.vo
 */
@Data
@TableName("UerVo")
@ApiModel(value = "UerVo对象")
public class UserVo implements Serializable {

    @ApiModelProperty("登录用户名")
    private String userName;

    @ApiModelProperty("登录密码")
    private String password;
}
