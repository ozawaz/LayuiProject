package cn.ozawa.layuiproject.controller;

import cn.ozawa.layuiproject.common.result.Result;
import cn.ozawa.layuiproject.entity.pojo.AwxRegion;
import cn.ozawa.layuiproject.entity.vo.AwxRegionVo;
import cn.ozawa.layuiproject.service.WorkService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : zheng.xiong
 * @version : 1.0
 * @date : Created at 2022/03/03
 * @description : cn.ozawa.layuiproject.controller
 */
@CrossOrigin
@RestController
@RequestMapping("/awx-region")
public class AwxRegionController {

    private WorkService workService;

    @Autowired
    public void setUserService(WorkService workService) {
        this.workService = workService;
    }

    @ApiOperation("根据上级id获取子节点数据列表")
    @GetMapping("/listByParentId/{parentId}")
    public Result<List<AwxRegionVo>> listByParentId(
            @ApiParam(value = "上级节点id", required = true)
            @PathVariable Long parentId) {
        List<AwxRegionVo> dictList = workService.listByParentId(Math.toIntExact(parentId));
        return Result.ok(dictList);
    }
}
