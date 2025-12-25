package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.CrmSelltargetMapper;
import com.center.medical.datamove.common.bean.model.CrmSelltarget;
import com.center.medical.datamove.common.service.CrmSelltargetService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * XS销售目标(CrmSelltarget)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:02
 */
@Slf4j
@Service("crmSelltargetService")
@RequiredArgsConstructor
public class CrmSelltargetServiceImpl extends ServiceImpl<CrmSelltargetMapper, CrmSelltarget> implements CrmSelltargetService {

    private final CrmSelltargetMapper crmSelltargetMapper;

    /**
     * 分页查询[XS销售目标]列表
     *
     * @param page  分页参数
     * @param param CrmSelltarget查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CrmSelltarget> getPage(PageParam<CrmSelltarget> page, CrmSelltarget param) {
        return crmSelltargetMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CrmSelltarget getInfoById(String id) {
        return crmSelltargetMapper.getInfoById(id);
    }

}


