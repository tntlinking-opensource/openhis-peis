package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.WzTjdwxxMxMapper;
import com.center.medical.bean.model.WzTjdwxxMx;
import com.center.medical.service.WzTjdwxxMxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——单位明细信息(WzTjdwxxMx)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:41
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
    public IPage<WzTjdwxxMx> getList(PageParam<WzTjdwxxMx> page, WzTjdwxxMx param) {
        return wzTjdwxxMxMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public WzTjdwxxMx getInfoById(String id) {
        return wzTjdwxxMxMapper.getInfoById(id);
    }

    ;

}

