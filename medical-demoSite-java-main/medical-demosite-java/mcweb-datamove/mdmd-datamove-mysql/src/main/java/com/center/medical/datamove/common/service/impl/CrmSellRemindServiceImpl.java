package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.CrmSellRemindMapper;
import com.center.medical.datamove.common.bean.model.CrmSellRemind;
import com.center.medical.datamove.common.service.CrmSellRemindService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 销售提醒(CrmSellRemind)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:00
 */
@Slf4j
@Service("crmSellRemindService")
@RequiredArgsConstructor
public class CrmSellRemindServiceImpl extends ServiceImpl<CrmSellRemindMapper, CrmSellRemind> implements CrmSellRemindService {

    private final CrmSellRemindMapper crmSellRemindMapper;

    /**
     * 分页查询[销售提醒]列表
     *
     * @param page  分页参数
     * @param param CrmSellRemind查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CrmSellRemind> getPage(PageParam<CrmSellRemind> page, CrmSellRemind param) {
        return crmSellRemindMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CrmSellRemind getInfoById(String id) {
        return crmSellRemindMapper.getInfoById(id);
    }

}


