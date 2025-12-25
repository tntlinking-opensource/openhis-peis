package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdDrugstoreDrugMapper;
import com.center.medical.datamove.common.bean.model.MdDrugstoreDrug;
import com.center.medical.datamove.common.service.MdDrugstoreDrugService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 药品基础表(MdDrugstoreDrug)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:14
 */
@Slf4j
@Service("mdDrugstoreDrugService")
@RequiredArgsConstructor
public class MdDrugstoreDrugServiceImpl extends ServiceImpl<MdDrugstoreDrugMapper, MdDrugstoreDrug> implements MdDrugstoreDrugService {

    private final MdDrugstoreDrugMapper mdDrugstoreDrugMapper;

    /**
     * 分页查询[药品基础表]列表
     *
     * @param page  分页参数
     * @param param MdDrugstoreDrug查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdDrugstoreDrug> getPage(PageParam<MdDrugstoreDrug> page, MdDrugstoreDrug param) {
        return mdDrugstoreDrugMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdDrugstoreDrug getInfoById(String id) {
        return mdDrugstoreDrugMapper.getInfoById(id);
    }

}


