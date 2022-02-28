package cn.ozawa.layuiproject.service;

import cn.ozawa.layuiproject.entity.pojo.User;
import cn.ozawa.layuiproject.entity.vo.UserVo;

/**
 * @author : zheng.xiong
 * @version : 1.0
 * @date : Created at 2022/02/28
 * @description : cn.ozawa.layuiproject.service
 */
public interface WorkService {

    /**
     * 登录
     * @param user 入参
     * @return 返回登录结果
     */
    public boolean login(UserVo user);
}
