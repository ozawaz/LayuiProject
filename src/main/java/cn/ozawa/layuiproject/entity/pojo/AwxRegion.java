package cn.ozawa.layuiproject.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxh
 * @since 2022-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AwxRegion implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 当前节点区域、店铺编号
     */
    private Integer regNo;

    /**
     * 父节点reg_no
     */
    private Integer parentRegNo;

    /**
     * 区域、店铺名称
     */
    private String name;

    /**
     * 创建人
     */
    private String createId;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String updateId;

    /**
     * 修改日期
     */
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private boolean hasChildren;
    @TableField(exist = false)
    private List<AwxRegion> children;
}
