package com.center.medical.appadmin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.appadmin.bean.dto.GetLastTimeDto;
import com.center.medical.appadmin.bean.model.MdUser;
import com.center.medical.appadmin.bean.param.AppUserParam;
import com.center.medical.appadmin.bean.vo.AppUserVo;
import com.center.medical.appadmin.dao.MdUserMapper;
import com.center.medical.appadmin.service.MdUserService;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(MdUser)服务实现类
 *
 * @author ay
 * @since 2024-07-23 17:03:25
 */
@Slf4j
@Service("mdUserService")
@RequiredArgsConstructor
public class MdUserServiceImpl extends ServiceImpl<MdUserMapper, MdUser> implements MdUserService {

    private final MdUserMapper mdUserMapper;


    /**
     * 分页查询[用户表]列表
     *
     * @param page  分页参数
     * @param param MdUser查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppUserVo> getPage(PageParam<AppUserVo> page, AppUserParam param) {
        IPage<AppUserVo> iPage = mdUserMapper.getPage(page, param);
        List<AppUserVo> list = iPage.getRecords();
        //一次查出来，手机号相同视为同一条
        List<String> phones = list.stream()
                .map(AppUserVo::getUserMobile)
                .collect(Collectors.toList());
        List<GetLastTimeDto> dtos = mdUserMapper.getLastTime(phones);
        for (AppUserVo vo : list) {
            for (GetLastTimeDto dto : dtos) {
                if (dto.getUserMobile().equals(vo.getUserMobile())){
                    vo.setNumber(dto.getNumber());
                    vo.setLastTime(dto.getLastTime());
                    break;
                }
            }
        }
        return iPage;
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    @Override
    public MdUser getInfoById(String id) {
        return mdUserMapper.getInfoById(id);
    }

}

