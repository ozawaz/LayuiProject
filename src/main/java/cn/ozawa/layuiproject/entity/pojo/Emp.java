package cn.ozawa.layuiproject.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author ozawa
 * @since 2022-02-25
 */
@Getter
@Setter
@TableName("emp")
@ApiModel(value = "Emp对象")
public class Emp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("员工id")
    @TableId(value = "emp_id", type = IdType.AUTO)
    private Integer empId;

    @ApiModelProperty("员工姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty("员工性别")
    @TableField("sex")
    private String sex;

    @ApiModelProperty("员工年龄")
    @TableField("age")
    private Integer age;

    @ApiModelProperty("员工工资")
    @TableField("sal")
    private BigDecimal sal;

    @ApiModelProperty("员工生日")
    @TableField("birthday")
    private LocalDate birthday;

    @ApiModelProperty("员工住址")
    @TableField("address")
    private String address;

    @ApiModelProperty("部门id")
    @TableField("dept_id")
    private Integer deptId;


}
