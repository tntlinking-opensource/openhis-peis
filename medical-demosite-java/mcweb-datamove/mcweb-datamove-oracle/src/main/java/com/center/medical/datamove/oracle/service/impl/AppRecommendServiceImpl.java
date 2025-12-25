package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppRecommendMapper;
import com.center.medical.datamove.oracle.bean.model.AppRecommend;
import com.center.medical.datamove.oracle.service.AppRecommendService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (AppRecommend)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:54
 */
@Slf4j
@Service("appRecommendService")
@RequiredArgsConstructor
public class AppRecommendServiceImpl extends ServiceImpl<AppRecommendMapper, AppRecommend> implements AppRecommendService {

    private final AppRecommendMapper appRecommendMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param AppRecommend查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppRecommend> getPage(PageParam<AppRecommend> page, AppRecommend param) {
        return appRecommendMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppRecommend getInfoById(String id) {
        return appRecommendMapper.getInfoById(id);
    }

}


