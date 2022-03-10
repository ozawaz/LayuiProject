package cn.ozawa.layuiproject.controller;


import cn.ozawa.layuiproject.common.result.Result;
import cn.ozawa.layuiproject.entity.pojo.AwxRedirectUrl;
import cn.ozawa.layuiproject.entity.vo.AwxRedirectUrlVO;
import cn.ozawa.layuiproject.service.WorkService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ozawa
 * @since 2022-03-09
 */
@CrossOrigin
@RestController
@RequestMapping("/awx-redirect-url")
public class AwxRedirectUrlController {

    private WorkService workService;

    @Autowired
    public void setWorkService(WorkService workService) {
        this.workService = workService;
    }

    @ApiOperation(value = "微信账号二维码跳转活动列表查询")
    @GetMapping("/getQrCodeRedirectList/{page}/{limit}")
    public Result<IPage<AwxRedirectUrlVO>> getQrCodeRedirectList(@ApiParam(value = "当前页码", required = true)
                                                                 @PathVariable Long page,
                                                                 @ApiParam(value = "每页记录数", required = true)
                                                                 @PathVariable Long limit,
                                                                 @RequestParam(required = false) String qrcodeName,
                                                                 @RequestParam(required = false) Integer activityType,
                                                                 @RequestParam(required = false) Integer urlType,
                                                                 @RequestParam(required = false) String createdUser) {
        Page<AwxRedirectUrl> pageParam = new Page<>(page, limit);
        return Result.ok(workService
                .getQrCodeRedirectList(pageParam, qrcodeName, activityType, urlType, createdUser))
                .message("查询成功");
    }

}
