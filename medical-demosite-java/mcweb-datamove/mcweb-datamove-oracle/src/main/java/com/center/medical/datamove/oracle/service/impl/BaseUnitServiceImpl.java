package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BaseUnitMapper;
import com.center.medical.datamove.oracle.bean.model.BaseUnit;
import com.center.medical.datamove.oracle.service.BaseUnitService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 济南-计量单位(BaseUnit)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:38
 */
@Slf4j
@Service("baseUnitService")
@RequiredArgsConstructor
public class BaseUnitServiceImpl extends ServiceImpl<BaseUnitMapper, BaseUnit> implements BaseUnitService {

    private final BaseUnitMapper baseUnitMapper;

    /**
     * 分页查询[济南-计量单位]列表
     *
     * @param page  分页参数
     * @param param BaseUnit查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BaseUnit> getPage(PageParam<BaseUnit> page, BaseUnit param) {
        return baseUnitMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BaseUnit getInfoById(String id) {
        return baseUnitMapper.getInfoById(id);
    }

}


