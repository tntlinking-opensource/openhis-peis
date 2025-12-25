package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.LeadertargetMapper;
import com.center.medical.datamove.oracle.bean.model.Leadertarget;
import com.center.medical.datamove.oracle.service.LeadertargetService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 领导目标表(Leadertarget)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:36
 */
@Slf4j
@Service("leadertargetService")
@RequiredArgsConstructor
public class LeadertargetServiceImpl extends ServiceImpl<LeadertargetMapper, Leadertarget> implements LeadertargetService {

    private final LeadertargetMapper leadertargetMapper;

    /**
     * 分页查询[领导目标表]列表
     *
     * @param page  分页参数
     * @param param Leadertarget查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Leadertarget> getPage(PageParam<Leadertarget> page, Leadertarget param) {
        return leadertargetMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Leadertarget getInfoById(String id) {
        return leadertargetMapper.getInfoById(id);
    }

}


