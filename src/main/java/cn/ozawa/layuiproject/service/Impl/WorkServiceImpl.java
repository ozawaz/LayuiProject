package cn.ozawa.layuiproject.service.Impl;

import cn.ozawa.layuiproject.common.result.ResponseEnum;
import cn.ozawa.layuiproject.common.result.Result;
import cn.ozawa.layuiproject.entity.dto.AwxRedirectUrlDTO;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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
@Slf4j
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
        // ????????????
        User userInfo = userService
                .lambdaQuery()
                .eq(User::getUserName, user.getUserName())
                .one();
        // ??????encoder
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // ??????
        return userInfo != null && passwordEncoder.matches(user.getPassword(), userInfo.getPassword());
    }

    @Override
    public List<AwxRegionVo> listByParentId(int parentId) {
        // ??????????????????
        List<AwxRegion> awxRegionList = awxRegionService.lambdaQuery().eq(AwxRegion::getParentRegNo, parentId).list();
        if (awxRegionList == null) {
            return null;
        }
        // ???????????????????????????
        regionSearchChildren(awxRegionList);
        // ????????????
        return regionConvert(awxRegionList);
    }

    @Override
    public IPage<AwxAccountQrcodeVO> getQrcodeList(Page<AwxAccountQrcode> pageParam, Integer type, String qrcodeName, String createId) {
        // ????????????????????????????????????
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
        // ????????????
        List<AwxAccountQrcode> list = awxAccountQrcodeService
                .lambdaQuery()
                .list();
        // ????????????
        return effectiveName(list);
    }

    @Override
    public Result<Object> saveQrCodeRedirect(AwxRedirectUrl awxRedirectUrl) {
        // ??????????????????????????????
        if (redirectUrlCanSave(awxRedirectUrl, getEffectiveDate(awxRedirectUrl.getQrcodeName()))) {
            awxRedirectUrl.setBindMemo("");
            awxRedirectUrlService.save(awxRedirectUrl);
            return Result.ok().message("??????????????????");
        } else{
            return Result.build(null, ResponseEnum.TIME_OVERLAPPING_ERROR);
        }
    }

    @Override
    public boolean deleteQrcodeRedirectUrl(AwxRedirectUrlVO awxRedirectUrlVO) {
        return awxRedirectUrlService
                .lambdaUpdate()
                .eq(AwxRedirectUrl::getQrcodeName, awxRedirectUrlVO.getQrcodeName())
                .eq(AwxRedirectUrl::getActivityName, awxRedirectUrlVO.getActivityName())
                .eq(AwxRedirectUrl::getUrl, awxRedirectUrlVO.getUrl())
                .remove();
    }

    /**
     * ???????????????wrapper
     */
    private LambdaQueryWrapper<AwxAccountQrcode> qrcodeWrapper(Integer type, String qrcodeName, String createId) {
        // ??????????????????wrapper
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
     * ???????????????????????????????????????
     */
    private IPage<AwxAccountQrcodeVO> qrcodeListConvert(Page<AwxAccountQrcode> pageParam,
                                                        LambdaQueryWrapper<AwxAccountQrcode> queryWrapper) {
        // ????????????
        IPage<AwxAccountQrcode> iPage = awxAccountQrcodeService.page(pageParam, queryWrapper);
        // ????????????
        return iPage.convert(qrcode -> {
            AwxAccountQrcodeVO qrcodeVO = new AwxAccountQrcodeVO();
            BeanUtils.copyProperties(qrcode, qrcodeVO);
            qrcodeVO.setType(decideQrcodeType(qrcode.getType()));
            return  qrcodeVO;
        });
    }

    /**
     * ??????????????????????????????????????????????????????
     * @return 1 ??????????????????2 ??????????????????3 ?????????????????????
     */
    private String decideQrcodeType(int type) {
        if (type == 1) {
            return "???????????????";
        } else if (type == 2) {
            return "???????????????";
        } else {
            return "?????????????????????";
        }
    }

    /**
     * ???????????????wrapper
     */
    private LambdaQueryWrapper<AwxRedirectUrl> qrcodeRedirectWrapper(String qrcodeName, Integer activityType, Integer urlType, String createdUser) {
        // ??????????????????wrapper
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
     * ???????????????????????????????????????????????????
     */
    private IPage<AwxRedirectUrlVO> qrCodeRedirectList(Page<AwxRedirectUrl> pageParam,
                                                        LambdaQueryWrapper<AwxRedirectUrl> queryWrapper) {
        // ????????????
        IPage<AwxRedirectUrl> iPage = awxRedirectUrlService.page(pageParam, queryWrapper);
        // ????????????
        return iPage.convert(qrcode -> {
            AwxRedirectUrlVO qrcodeVO = new AwxRedirectUrlVO();
            BeanUtils.copyProperties(qrcode, qrcodeVO);
            qrcodeVO.setActivityType(decideQrcodeRedirectType(1, qrcode.getActivityType()));
            qrcodeVO.setUrlType(decideQrcodeRedirectType(2, qrcode.getUrlType()));
            return  qrcodeVO;
        });
    }

    /**
     * ???????????????????????????????????????????????????????????????
     * flag
     *  1 ????????????
     *      1 ????????????????????? 2 ????????????????????????
     *  2 ????????????
     *      1 h5?????? 2 ???????????????
     * @return ??????
     */
    private String decideQrcodeRedirectType(int flag, int type) {
        if (flag == 1) {
            if (type == 1) {
                return "?????????????????????";
            } else{
                return "????????????????????????";
            }
        } else {
            if (type == 1) {
                return "h5??????";
            } else{
                return "???????????????";
            }
        }
    }

    /**
     * ???????????????????????????????????????????????????????????????
     */
    private List<String> effectiveName(List<AwxAccountQrcode> list) {
        List<String> effectiveNameList = new LinkedList<>();
        // ????????????????????????
        for (AwxAccountQrcode qrcode : list) {
            if (qrcode.getType() == 2) {
                // ??????????????????
                long expireTime = qrcode.getExpireTime() + qrcode.getCreateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
                if (System.currentTimeMillis() < expireTime) {
                    effectiveNameList.add(qrcode.getQrcodeName());
                }
            } else {
                effectiveNameList.add(qrcode.getQrcodeName());
            }
        }
        return effectiveNameList;
    }

    /**
     * ??????????????????????????????????????????
     */
    private List<AwxRedirectUrlDTO> getEffectiveDate(String qrcodeName) {
        // ???????????????????????????????????????????????????
        List<AwxRedirectUrl> effectiveRedirect = getEffectiveRedirect(qrcodeName);
        // ????????????
        List<AwxRedirectUrlDTO> dtoList = new LinkedList<>();
        for (AwxRedirectUrl awxRedirectUrl : effectiveRedirect) {
            AwxRedirectUrlDTO dto = new AwxRedirectUrlDTO();
            BeanUtils.copyProperties(awxRedirectUrl, dto);
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * ??????????????????????????????????????????????????????????????????
     */
    private List<AwxRedirectUrl> getEffectiveRedirect(String qrcodeName) {
        // ???????????????
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // ??????????????????
        LocalDateTime nowTime = LocalDateTime.now();
        // ???????????????????????????????????????????????????
        return awxRedirectUrlService
                .lambdaQuery()
                .eq(AwxRedirectUrl::getQrcodeName, qrcodeName)
                .gt(AwxRedirectUrl::getEndDate, format.format(nowTime))
                .orderByAsc(AwxRedirectUrl::getBeginDate)
                .list();
    }

    /**
     * ?????????????????????????????????????????????????????????
     */
    private boolean redirectUrlCanSave(AwxRedirectUrl awxRedirectUrl, List<AwxRedirectUrlDTO> effectiveDate) {
        // ?????????????????????
        LocalDateTime beginDate = awxRedirectUrl.getBeginDate();
        LocalDateTime endDate = awxRedirectUrl.getEndDate();
        // ????????????
        for (AwxRedirectUrlDTO dto : effectiveDate) {
            if (beginDate.isAfter(dto.getEndDate())) {
                continue;
            }
            if (endDate.isBefore(dto.getBeginDate())) {
                return true;
            }
            if (beginDate.isBefore(dto.getEndDate()) && endDate.isAfter(dto.getEndDate())) {
                return false;
            } else if (beginDate.isAfter(dto.getBeginDate()) && endDate.isBefore(dto.getEndDate())) {
                return false;
            } else if (beginDate.isBefore(dto.getBeginDate())) {
                return false;
            }
        }
        return true;
    }

    /**
     * ??????????????????????????????
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
     * ???????????????????????????
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
