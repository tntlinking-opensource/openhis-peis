package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.CrmOrderConflictMapper;
import com.center.medical.datamove.common.bean.model.CrmOrderConflict;
import com.center.medical.datamove.common.service.CrmOrderConflictService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 撞单记录(CrmOrderConflict)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:44:59
 */
@Slf4j
@Service("crmOrderConflictService")
@RequiredArgsConstructor
public class CrmOrderConflictServiceImpl extends ServiceImpl<CrmOrderConflictMapper, CrmOrderConflict> implements CrmOrderConflictService {

    private final CrmOrderConflictMapper crmOrderConflictMapper;

    /**
     * 分页查询[撞单记录]列表
     *
     * @param page  分页参数
     * @param param CrmOrderConflict查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CrmOrderConflict> getPage(PageParam<CrmOrderConflict> page, CrmOrderConflict param) {
        return crmOrderConflictMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CrmOrderConflict getInfoById(String id) {
        return crmOrderConflictMapper.getInfoById(id);
    }

}


