package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.DrugstorePrescribeMapper;
import com.center.medical.datamove.oracle.bean.model.DrugstorePrescribe;
import com.center.medical.datamove.oracle.service.DrugstorePrescribeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (DrugstorePrescribe)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:31
 */
@Slf4j
@Service("drugstorePrescribeService")
@RequiredArgsConstructor
public class DrugstorePrescribeServiceImpl extends ServiceImpl<DrugstorePrescribeMapper, DrugstorePrescribe> implements DrugstorePrescribeService {

    private final DrugstorePrescribeMapper drugstorePrescribeMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param DrugstorePrescribe查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DrugstorePrescribe> getPage(PageParam<DrugstorePrescribe> page, DrugstorePrescribe param) {
        return drugstorePrescribeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public DrugstorePrescribe getInfoById(String id) {
        return drugstorePrescribeMapper.getInfoById(id);
    }

}


