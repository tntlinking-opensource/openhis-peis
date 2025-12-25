package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.DwHarmMapper;
import com.center.medical.datamove.oracle.bean.model.DwHarm;
import com.center.medical.datamove.oracle.service.DwHarmService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC单位危害因素(DwHarm)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:32
 */
@Slf4j
@Service("dwHarmService")
@RequiredArgsConstructor
public class DwHarmServiceImpl extends ServiceImpl<DwHarmMapper, DwHarm> implements DwHarmService {

    private final DwHarmMapper dwHarmMapper;

    /**
     * 分页查询[JC单位危害因素]列表
     *
     * @param page  分页参数
     * @param param DwHarm查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DwHarm> getPage(PageParam<DwHarm> page, DwHarm param) {
        return dwHarmMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public DwHarm getInfoById(String id) {
        return dwHarmMapper.getInfoById(id);
    }

}


