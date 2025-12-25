package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.CrmClientcommonMapper;
import com.center.medical.datamove.common.bean.model.CrmClientcommon;
import com.center.medical.datamove.common.service.CrmClientcommonService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户公共池(CrmClientcommon)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:44:57
 */
@Slf4j
@Service("crmClientcommonService")
@RequiredArgsConstructor
public class CrmClientcommonServiceImpl extends ServiceImpl<CrmClientcommonMapper, CrmClientcommon> implements CrmClientcommonService {

    private final CrmClientcommonMapper crmClientcommonMapper;

    /**
     * 分页查询[客户公共池]列表
     *
     * @param page  分页参数
     * @param param CrmClientcommon查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CrmClientcommon> getPage(PageParam<CrmClientcommon> page, CrmClientcommon param) {
        return crmClientcommonMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CrmClientcommon getInfoById(String id) {
        return crmClientcommonMapper.getInfoById(id);
    }

}


