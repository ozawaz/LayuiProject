package cn.ozawa.layuiproject.controller;


import cn.ozawa.layuiproject.common.result.Result;
import cn.ozawa.layuiproject.entity.pojo.AwxAccountQrcode;
import cn.ozawa.layuiproject.entity.vo.AwxAccountQrcodeVO;
import cn.ozawa.layuiproject.service.WorkService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 微信账号二维码 前端控制器
 * </p>
 *
 * @author ozawa
 * @since 2022-03-03
 */
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/awx-account-qrcode")
public class AwxAccountQrcodeController {

    private WorkService workService;

    @Autowired
    public void setWorkService(WorkService workService) {
        this.workService = workService;
    }

    @ApiOperation(value = "微信账号二维码 列表查询")
    @GetMapping("/getQrCodeList/{page}/{limit}")
    public Result<IPage<AwxAccountQrcodeVO>> getQrCodeList(@ApiParam(value = "当前页码", required = true)
                                                               @PathVariable Long page,
                                                           @ApiParam(value = "每页记录数", required = true)
                                                               @PathVariable Long limit,
                                                           @RequestParam(required = false) Integer type,
                                                           @RequestParam(required = false) String qrcodeName,
                                                           @RequestParam(required = false) String createId) {
        Page<AwxAccountQrcode> pageParam = new Page<>(page, limit);
        return Result.ok(workService.getQrcodeList(pageParam, type, qrcodeName, createId)).message("查询成功");
    }

    @ApiOperation(value = "删除微信账号二维码")
    @DeleteMapping("/deleteQrcode")
    public Result<Object> deleteQrcode(@RequestBody AwxAccountQrcodeVO qrcodeVO) {
        log.info("deleteQrcode_param:{}", qrcodeVO);
        if (workService.deleteQrcode(qrcodeVO.getQrcodeName())) {
            return Result.ok().message("删除成功");
        } else {
            return Result.fail().message("删除失败");
        }
    }

    @ApiOperation(value = "获取有效期内的临时二维码和永久二维码")
    @GetMapping("/getEffectiveQrcodeName")
    public Result<List<String>> getEffectiveQrcodeName() {
        return Result.ok(workService.getEffectiveQrcodeName());
    }
}
