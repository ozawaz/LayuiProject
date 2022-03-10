package cn.ozawa.layuiproject.service;

import cn.ozawa.layuiproject.entity.pojo.AwxAccountQrcode;
import cn.ozawa.layuiproject.entity.pojo.AwxRedirectUrl;
import cn.ozawa.layuiproject.entity.vo.AwxAccountQrcodeVO;
import cn.ozawa.layuiproject.entity.vo.AwxRedirectUrlVO;
import cn.ozawa.layuiproject.entity.vo.AwxRegionVo;
import cn.ozawa.layuiproject.entity.vo.UserVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

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
    boolean login(UserVo user);

    /**
     * 根据父节点查找
     * @param parentId 父节点
     * @return 返回查找到的数据
     */
    List<AwxRegionVo> listByParentId(int parentId);

    /**
     * 获取所有二维码的信息
     * @param pageParam 分页
     * @param type 二维码类型
     * @param qrcodeName 二维码名称
     * @param createId 创建人
     * @return 返回查询到的信息
     */
    IPage<AwxAccountQrcodeVO> getQrcodeList(Page<AwxAccountQrcode> pageParam, Integer type, String qrcodeName, String createId);

    /**
     * 删除对应的二维码
     * @param qrcodeName 对应的二维码名称
     * @return 返回是否删除
     */
    boolean deleteQrcode(String qrcodeName);

    /**
     * 获取二维码跳转活动链接
     * @param pageParam 分页
     * @param qrcodeName 二维码名称
     * @param activityType 活动类型
     * @param urlType 链接类型
     * @param createdUser 创建人
     * @return 返回查询到的信息
     */
    IPage<AwxRedirectUrlVO> getQrCodeRedirectList(Page<AwxRedirectUrl> pageParam, String qrcodeName, Integer activityType, Integer urlType, String createdUser);

    /**
     * 获取有效期内的临时二维码和永久二维码
     * @return 返回名称的list集合
     */
    List<String> getEffectiveQrcodeName();
}
