package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppUserCardMapper;
import com.center.medical.datamove.oracle.bean.model.AppUserCard;
import com.center.medical.datamove.oracle.service.AppUserCardService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (AppUserCard)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:05
 */
@Slf4j
@Service("appUserCardService")
@RequiredArgsConstructor
public class AppUserCardServiceImpl extends ServiceImpl<AppUserCardMapper, AppUserCard> implements AppUserCardService {

    private final AppUserCardMapper appUserCardMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param AppUserCard查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppUserCard> getPage(PageParam<AppUserCard> page, AppUserCard param) {
        return appUserCardMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppUserCard getInfoById(String id) {
        return appUserCardMapper.getInfoById(id);
    }

}


