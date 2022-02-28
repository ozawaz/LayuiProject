package cn.ozawa.layuiproject.service.Impl;

import cn.ozawa.layuiproject.entity.pojo.Emp;
import cn.ozawa.layuiproject.mapper.EmpMapper;
import cn.ozawa.layuiproject.service.EmpService;
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
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements EmpService {

}
