package cn.ozawa.layuiproject.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author : zheng.xiong
 * @version : 1.0
 * @date : Created at 2022/03/03
 * @description : cn.ozawa.layuiproject.entity.vo
 */
@Data
public class AwxRegionVo {

    /**
     * 当前节点区域、店铺编号
     */
    private Integer id;

    /**
     * 父节点reg_no
     */
    private Integer parentId;

    /**
     * 区域、店铺名称
     */
    private String title;

    private List<AwxRegionVo> children;
}
