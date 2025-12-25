package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseUnit;
import com.center.medical.data.dao.BaseUnitMapper;
import com.center.medical.data.service.BaseUnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 济南-计量单位(BaseUnit)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:46
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
    public IPage<BaseUnit> getList(PageParam<BaseUnit> page, BaseUnit param) {
        return baseUnitMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public BaseUnit getInfoById(String id) {
        return baseUnitMapper.getInfoById(id);
    }

    ;

}

