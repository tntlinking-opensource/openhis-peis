package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.DongruanPacsStateMapper;
import com.center.medical.datamove.oracle.bean.model.DongruanPacsState;
import com.center.medical.datamove.oracle.service.DongruanPacsStateService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (DongruanPacsState)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:21
 */
@Slf4j
@Service("dongruanPacsStateService")
@RequiredArgsConstructor
public class DongruanPacsStateServiceImpl extends ServiceImpl<DongruanPacsStateMapper, DongruanPacsState> implements DongruanPacsStateService {

    private final DongruanPacsStateMapper dongruanPacsStateMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param DongruanPacsState查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DongruanPacsState> getPage(PageParam<DongruanPacsState> page, DongruanPacsState param) {
        return dongruanPacsStateMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public DongruanPacsState getInfoById(String id) {
        return dongruanPacsStateMapper.getInfoById(id);
    }

    ;

}


