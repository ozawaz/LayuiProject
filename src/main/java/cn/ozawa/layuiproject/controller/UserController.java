package cn.ozawa.layuiproject.controller;


import cn.ozawa.layuiproject.common.result.Result;
import cn.ozawa.layuiproject.entity.pojo.User;
import cn.ozawa.layuiproject.entity.vo.UserVo;
import cn.ozawa.layuiproject.service.WorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ozawa
 * @since 2022-02-25
 */
@RestController
@Slf4j
@RequestMapping("/layui")
public class UserController {

    private WorkService workService;

    @Autowired
    public void setUserService(WorkService workService) {
        this.workService = workService;
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<Object> login(@RequestBody UserVo user, HttpSession httpSession) {
        // 打印入参
        log.info("login_param: {}", user);

        // 登录
        if (workService.login(user)) {
            // 保存用户信息
            httpSession.setAttribute("userInfo", user);

            return Result.ok().message("登录成功");
        } else {
            return Result.fail().message("用户名或密码错误");
        }
    }
}
