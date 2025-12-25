package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.WzTjdwxxMxMapper;
import com.center.medical.datamove.oracle.bean.model.WzTjdwxxMx;
import com.center.medical.datamove.oracle.service.WzTjdwxxMxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——单位明细信息(WzTjdwxxMx)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:30:52
 */
@Slf4j
@Service("wzTjdwxxMxService")
@RequiredArgsConstructor
public class WzTjdwxxMxServiceImpl extends ServiceImpl<WzTjdwxxMxMapper, WzTjdwxxMx> implements WzTjdwxxMxService {

    private final WzTjdwxxMxMapper wzTjdwxxMxMapper;

    /**
     * 分页查询[KS问诊——单位明细信息]列表
     *
     * @param page  分页参数
     * @param param WzTjdwxxMx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WzTjdwxxMx> getPage(PageParam<WzTjdwxxMx> page, WzTjdwxxMx param) {
        return wzTjdwxxMxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WzTjdwxxMx getInfoById(String id) {
        return wzTjdwxxMxMapper.getInfoById(id);
    }

}


