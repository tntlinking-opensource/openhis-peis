package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SortexamLimitMapper;
import com.center.medical.datamove.oracle.bean.model.SortexamLimit;
import com.center.medical.datamove.oracle.service.SortexamLimitService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 预约管理(SortexamLimit)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:03
 */
@Slf4j
@Service("sortexamLimitService")
@RequiredArgsConstructor
public class SortexamLimitServiceImpl extends ServiceImpl<SortexamLimitMapper, SortexamLimit> implements SortexamLimitService {

    private final SortexamLimitMapper sortexamLimitMapper;

    /**
     * 分页查询[预约管理]列表
     *
     * @param page  分页参数
     * @param param SortexamLimit查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SortexamLimit> getPage(PageParam<SortexamLimit> page, SortexamLimit param) {
        return sortexamLimitMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SortexamLimit getInfoById(String id) {
        return sortexamLimitMapper.getInfoById(id);
    }

}


