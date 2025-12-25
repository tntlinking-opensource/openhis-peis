package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysJobMapper;
import com.center.medical.datamove.common.bean.model.SysJob;
import com.center.medical.datamove.common.service.SysJobService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务调度表(SysJob)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:35
 */
@Slf4j
@Service("sysJobService")
@RequiredArgsConstructor
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService {

    private final SysJobMapper sysJobMapper;

    /**
     * 分页查询[定时任务调度表]列表
     *
     * @param page  分页参数
     * @param param SysJob查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysJob> getPage(PageParam<SysJob> page, SysJob param) {
        return sysJobMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键jobId
     * @return 详情信息
     */
    @Override
    public SysJob getInfoById(Long id) {
        return sysJobMapper.getInfoById(id);
    }

}


