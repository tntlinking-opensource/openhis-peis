package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.DrugstoreDrugMapper;
import com.center.medical.datamove.oracle.bean.model.DrugstoreDrug;
import com.center.medical.datamove.oracle.service.DrugstoreDrugService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (DrugstoreDrug)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:30
 */
@Slf4j
@Service("drugstoreDrugService")
@RequiredArgsConstructor
public class DrugstoreDrugServiceImpl extends ServiceImpl<DrugstoreDrugMapper, DrugstoreDrug> implements DrugstoreDrugService {

    private final DrugstoreDrugMapper drugstoreDrugMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param DrugstoreDrug查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DrugstoreDrug> getPage(PageParam<DrugstoreDrug> page, DrugstoreDrug param) {
        return drugstoreDrugMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public DrugstoreDrug getInfoById(String id) {
        return drugstoreDrugMapper.getInfoById(id);
    }

}


