package cn.ozawa.layuiproject.mapper;

import cn.ozawa.layuiproject.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ozawa
 * @since 2022-02-25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
