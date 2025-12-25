package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.SortexamLimitMapper;
import com.center.medical.bean.model.SortexamLimit;
import com.center.medical.service.SortexamLimitService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 每日排检上限(SortexamLimit)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:19
 */
@Slf4j
@Service("sortexamLimitService")
@RequiredArgsConstructor
public class SortexamLimitServiceImpl extends ServiceImpl<SortexamLimitMapper, SortexamLimit> implements SortexamLimitService {

    private final SortexamLimitMapper sortexamLimitMapper;

    /**
     * 分页查询[每日排检上限]列表
     *
     * @param page  分页参数
     * @param param SortexamLimit查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SortexamLimit> getList(PageParam<SortexamLimit> page, SortexamLimit param) {
        return sortexamLimitMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public SortexamLimit getInfoById(String id) {
        return sortexamLimitMapper.getInfoById(id);
    }

    ;

}

