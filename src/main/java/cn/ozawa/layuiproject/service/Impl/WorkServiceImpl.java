package cn.ozawa.layuiproject.service.Impl;

import cn.ozawa.layuiproject.entity.pojo.User;
import cn.ozawa.layuiproject.entity.vo.UserVo;
import cn.ozawa.layuiproject.service.UserService;
import cn.ozawa.layuiproject.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author : zheng.xiong
 * @version : 1.0
 * @date : Created at 2022/02/28
 * @description : cn.ozawa.layuiproject.service.Impl
 */
@Service
public class WorkServiceImpl implements WorkService {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean login(UserVo user) {
        // 获取信息
        User userInfo = userService
                .lambdaQuery()
                .eq(User::getUserName, user.getUserName())
                .one();
        // 加密encoder
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 判断
        return userInfo != null && passwordEncoder.matches(user.getPassword(), userInfo.getPassword());
    }
}
