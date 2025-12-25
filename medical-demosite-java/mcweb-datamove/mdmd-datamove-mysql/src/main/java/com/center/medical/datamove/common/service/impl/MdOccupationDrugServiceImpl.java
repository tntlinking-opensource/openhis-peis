package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdOccupationDrugMapper;
import com.center.medical.datamove.common.bean.model.MdOccupationDrug;
import com.center.medical.datamove.common.service.MdOccupationDrugService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC职业病/禁忌症(MdOccupationDrug)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:28
 */
@Slf4j
@Service("mdOccupationDrugService")
@RequiredArgsConstructor
public class MdOccupationDrugServiceImpl extends ServiceImpl<MdOccupationDrugMapper, MdOccupationDrug> implements MdOccupationDrugService {

    private final MdOccupationDrugMapper mdOccupationDrugMapper;

    /**
     * 分页查询[JC职业病/禁忌症]列表
     *
     * @param page  分页参数
     * @param param MdOccupationDrug查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdOccupationDrug> getPage(PageParam<MdOccupationDrug> page, MdOccupationDrug param) {
        return mdOccupationDrugMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdOccupationDrug getInfoById(String id) {
        return mdOccupationDrugMapper.getInfoById(id);
    }

}


