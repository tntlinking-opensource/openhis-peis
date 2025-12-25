package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.CrmReceiveandsellMapper;
import com.center.medical.datamove.common.bean.model.CrmReceiveandsell;
import com.center.medical.datamove.common.service.CrmReceiveandsellService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户公共池领取与领取人员关联表(CrmReceiveandsell)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:00
 */
@Slf4j
@Service("crmReceiveandsellService")
@RequiredArgsConstructor
public class CrmReceiveandsellServiceImpl extends ServiceImpl<CrmReceiveandsellMapper, CrmReceiveandsell> implements CrmReceiveandsellService {

    private final CrmReceiveandsellMapper crmReceiveandsellMapper;

    /**
     * 分页查询[客户公共池领取与领取人员关联表]列表
     *
     * @param page  分页参数
     * @param param CrmReceiveandsell查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CrmReceiveandsell> getPage(PageParam<CrmReceiveandsell> page, CrmReceiveandsell param) {
        return crmReceiveandsellMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CrmReceiveandsell getInfoById(String id) {
        return crmReceiveandsellMapper.getInfoById(id);
    }

}


