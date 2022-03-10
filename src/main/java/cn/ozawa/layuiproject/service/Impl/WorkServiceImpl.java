package cn.ozawa.layuiproject.service.Impl;

import cn.ozawa.layuiproject.entity.pojo.AwxAccountQrcode;
import cn.ozawa.layuiproject.entity.pojo.AwxRedirectUrl;
import cn.ozawa.layuiproject.entity.pojo.AwxRegion;
import cn.ozawa.layuiproject.entity.pojo.User;
import cn.ozawa.layuiproject.entity.vo.AwxAccountQrcodeVO;
import cn.ozawa.layuiproject.entity.vo.AwxRedirectUrlVO;
import cn.ozawa.layuiproject.entity.vo.AwxRegionVo;
import cn.ozawa.layuiproject.entity.vo.UserVo;
import cn.ozawa.layuiproject.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author : zheng.xiong
 * @version : 1.0
 * @date : Created at 2022/02/28
 * @description : cn.ozawa.layuiproject.service.Impl
 */
@Service
public class WorkServiceImpl implements WorkService {

    private UserService userService;
    private AwxRegionService awxRegionService;
    private AwxAccountQrcodeService awxAccountQrcodeService;
    private AwxRedirectUrlService awxRedirectUrlService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAwxRegionService(AwxRegionService awxRegionService) {
        this.awxRegionService = awxRegionService;
    }

    @Autowired
    public void setAwxAccountQrcodeService(AwxAccountQrcodeService awxAccountQrcodeService) {
        this.awxAccountQrcodeService = awxAccountQrcodeService;
    }
    @Autowired
    public void setAwxRedirectUrlService(AwxRedirectUrlService awxRedirectUrlService) {
        this.awxRedirectUrlService = awxRedirectUrlService;
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

    @Override
    public List<AwxRegionVo> listByParentId(int parentId) {
        // 查询区域信息
        List<AwxRegion> awxRegionList = awxRegionService.lambdaQuery().eq(AwxRegion::getParentRegNo, parentId).list();
        if (awxRegionList == null) {
            return null;
        }
        // 查询区域子节点信息
        regionSearchChildren(awxRegionList);
        // 数据处理
        return regionConvert(awxRegionList);
    }

    @Override
    public IPage<AwxAccountQrcodeVO> getQrcodeList(Page<AwxAccountQrcode> pageParam, Integer type, String qrcodeName, String createId) {
        // 查询信息，并进行数据处理
        return qrcodeListConvert(pageParam, qrcodeWrapper(type, qrcodeName, createId));
    }

    @Override
    public boolean deleteQrcode(String qrcodeName) {
        return awxAccountQrcodeService.lambdaUpdate().eq(AwxAccountQrcode::getQrcodeName, qrcodeName).remove();
    }

    @Override
    public IPage<AwxRedirectUrlVO> getQrCodeRedirectList(Page<AwxRedirectUrl> pageParam, String qrcodeName, Integer activityType, Integer urlType, String createdUser) {
        return qrCodeRedirectList(pageParam, qrcodeRedirectWrapper(qrcodeName, activityType, urlType, createdUser));
    }

    @Override
    public List<String> getEffectiveQrcodeName() {
        // 获取数据
        List<AwxAccountQrcode> list = awxAccountQrcodeService
                .lambdaQuery()
                .ne(AwxAccountQrcode::getType, 3)
                .list();
        // 处理数据
        return effectiveName(list);
    }

    /**
     * 返回对应的wrapper
     */
    private LambdaQueryWrapper<AwxAccountQrcode> qrcodeWrapper(Integer type, String qrcodeName, String createId) {
        // 根据条件封装wrapper
        LambdaQueryWrapper<AwxAccountQrcode> queryWrapper =new LambdaQueryWrapper<>();
        if (type != null) {
            queryWrapper.eq(AwxAccountQrcode::getType, type);
        }
        if (qrcodeName != null && !"".equals(qrcodeName)) {
            queryWrapper.eq(AwxAccountQrcode::getQrcodeName, qrcodeName);
        }
        if (createId != null && !"".equals(createId)) {
            queryWrapper.eq(AwxAccountQrcode::getCreateId, createId);
        }
        queryWrapper.eq(AwxAccountQrcode::getStatus, 1);

        return queryWrapper;
    }

    /**
     * 查询二维码信息，并进行处理
     */
    private IPage<AwxAccountQrcodeVO> qrcodeListConvert(Page<AwxAccountQrcode> pageParam,
                                                        LambdaQueryWrapper<AwxAccountQrcode> queryWrapper) {
        // 查询信息
        IPage<AwxAccountQrcode> iPage = awxAccountQrcodeService.page(pageParam, queryWrapper);
        // 转换类型
        return iPage.convert(qrcode -> {
            AwxAccountQrcodeVO qrcodeVO = new AwxAccountQrcodeVO();
            BeanUtils.copyProperties(qrcode, qrcodeVO);
            qrcodeVO.setType(decideQrcodeType(qrcode.getType()));
            return  qrcodeVO;
        });
    }

    /**
     * 根据类型的不同，返回对应的二维码名称
     * @return 1 永久二维码，2 临时二维码，3 关注门店二维码
     */
    private String decideQrcodeType(int type) {
        if (type == 1) {
            return "永久二维码";
        } else if (type == 2) {
            return "临时二维码";
        } else {
            return "关注门店二维码";
        }
    }

    /**
     * 返回对应的wrapper
     */
    private LambdaQueryWrapper<AwxRedirectUrl> qrcodeRedirectWrapper(String qrcodeName, Integer activityType, Integer urlType, String createdUser) {
        // 根据条件封装wrapper
        LambdaQueryWrapper<AwxRedirectUrl> queryWrapper =new LambdaQueryWrapper<>();
        if (qrcodeName != null && !"".equals(qrcodeName)) {
            queryWrapper.like(AwxRedirectUrl::getQrcodeName, qrcodeName);
        }
        if (activityType != null) {
            queryWrapper.eq(AwxRedirectUrl::getActivityType, activityType);
        }
        if (urlType != null) {
            queryWrapper.eq(AwxRedirectUrl::getUrlType, urlType);
        }
        if (createdUser != null && !"".equals(createdUser)) {
            queryWrapper.like(AwxRedirectUrl::getCreatedUser, createdUser);
        }

        return queryWrapper;
    }

    /**
     * 查询二维码跳转活动信息，并进行处理
     */
    private IPage<AwxRedirectUrlVO> qrCodeRedirectList(Page<AwxRedirectUrl> pageParam,
                                                        LambdaQueryWrapper<AwxRedirectUrl> queryWrapper) {
        // 查询信息
        IPage<AwxRedirectUrl> iPage = awxRedirectUrlService.page(pageParam, queryWrapper);
        // 转换类型
        return iPage.convert(qrcode -> {
            AwxRedirectUrlVO qrcodeVO = new AwxRedirectUrlVO();
            BeanUtils.copyProperties(qrcode, qrcodeVO);
            qrcodeVO.setActivityType(decideQrcodeRedirectType(1, qrcode.getActivityType()));
            qrcodeVO.setUrlType(decideQrcodeRedirectType(2, qrcode.getUrlType()));
            return  qrcodeVO;
        });
    }

    /**
     * 根据标志位和类型的不同，返回对应的类型名称
     * flag
     *  1 活动类型
     *      1 判断是否关注点 2 不判断是否关注店
     *  2 链接类型
     *      1 h5链接 2 小程序链接
     * @return 类型
     */
    private String decideQrcodeRedirectType(int flag, int type) {
        if (flag == 1) {
            if (type == 1) {
                return "判断是否关注店";
            } else{
                return "不判断是否关注店";
            }
        } else {
            if (type == 1) {
                return "h5链接";
            } else{
                return "小程序链接";
            }
        }
    }

    /**
     * 将有效期内的临时二维码和永久二维码名称取出
     */
    private List<String> effectiveName(List<AwxAccountQrcode> list) {
        List<String> effectiveNameList = new LinkedList<>();
        // 循环获取有效名称
        for (AwxAccountQrcode qrcode : list) {
            if (qrcode.getType() == 1) {
                effectiveNameList.add(qrcode.getQrcodeName());
            } else {
                // 计算过期时间
                long expireTime = qrcode.getExpireTime() + qrcode.getCreateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
                if (System.currentTimeMillis() < expireTime) {
                    effectiveNameList.add(qrcode.getQrcodeName());
                }
            }
        }
        return effectiveNameList;
    }

    /**
     * 处理查询到的区域信息
     */
    private List<AwxRegionVo> regionConvert(List<AwxRegion> awxRegionList) {
        List<AwxRegionVo> voList = new LinkedList<>();
        for (AwxRegion region : awxRegionList) {
            AwxRegionVo regionVo = new AwxRegionVo();
            regionVo.setId(region.getRegNo());
            regionVo.setParentId(region.getParentRegNo());
            regionVo.setTitle(region.getName());
            if (region.isHasChildren()) {
                regionVo.setChildren(regionConvert(region.getChildren()));
            }
            voList.add(regionVo);
        }

        return voList;
    }

    /**
     * 查询区域子节点信息
     */
    private void regionSearchChildren(List<AwxRegion> awxRegionList) {
        Queue<List<AwxRegion>> queue = new LinkedList<>();
        queue.add(awxRegionList);
        while (queue.size() > 0) {
            List<AwxRegion> list = queue.poll();
            for (AwxRegion region : list) {
                List<AwxRegion> regionList = awxRegionService
                        .lambdaQuery()
                        .eq(AwxRegion::getParentRegNo, region.getRegNo())
                        .list();
                if (regionList != null) {
                    region.setHasChildren(true);
                    region.setChildren(regionList);
                    queue.add(regionList);
                } else {
                    region.setHasChildren(false);
                }
            }
        }
    }
}
