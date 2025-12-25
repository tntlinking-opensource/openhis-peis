package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdBaseUnitMapper;
import com.center.medical.datamove.common.bean.model.MdBaseUnit;
import com.center.medical.datamove.common.service.MdBaseUnitService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 济南-计量单位(MdBaseUnit)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:10
 */
@Slf4j
@Service("mdBaseUnitService")
@RequiredArgsConstructor
public class MdBaseUnitServiceImpl extends ServiceImpl<MdBaseUnitMapper, MdBaseUnit> implements MdBaseUnitService {

    private final MdBaseUnitMapper mdBaseUnitMapper;

    /**
     * 分页查询[济南-计量单位]列表
     *
     * @param page  分页参数
     * @param param MdBaseUnit查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBaseUnit> getPage(PageParam<MdBaseUnit> page, MdBaseUnit param) {
        return mdBaseUnitMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdBaseUnit getInfoById(String id) {
        return mdBaseUnitMapper.getInfoById(id);
    }

}


