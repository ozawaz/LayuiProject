package cn.ozawa.layuiproject.serviceImpl;

import cn.ozawa.layuiproject.entity.User;
import cn.ozawa.layuiproject.mapper.UserMapper;
import cn.ozawa.layuiproject.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ozawa
 * @since 2022-02-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
