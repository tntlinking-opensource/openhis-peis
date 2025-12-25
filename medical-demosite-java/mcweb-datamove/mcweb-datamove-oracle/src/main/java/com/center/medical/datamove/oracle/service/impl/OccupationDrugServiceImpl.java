package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.OccupationDrugMapper;
import com.center.medical.datamove.oracle.bean.model.OccupationDrug;
import com.center.medical.datamove.oracle.service.OccupationDrugService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC职业病/禁忌症(OccupationDrug)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:20
 */
@Slf4j
@Service("occupationDrugService")
@RequiredArgsConstructor
public class OccupationDrugServiceImpl extends ServiceImpl<OccupationDrugMapper, OccupationDrug> implements OccupationDrugService {

    private final OccupationDrugMapper occupationDrugMapper;

    /**
     * 分页查询[JC职业病/禁忌症]列表
     *
     * @param page  分页参数
     * @param param OccupationDrug查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OccupationDrug> getPage(PageParam<OccupationDrug> page, OccupationDrug param) {
        return occupationDrugMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OccupationDrug getInfoById(String id) {
        return occupationDrugMapper.getInfoById(id);
    }

}


