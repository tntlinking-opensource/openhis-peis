package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.FxItemscheckMapper;
import com.center.medical.datamove.oracle.bean.model.FxItemscheck;
import com.center.medical.datamove.oracle.service.FxItemscheckService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (FxItemscheck)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:53
 */
@Slf4j
@Service("fxItemscheckService")
@RequiredArgsConstructor
public class FxItemscheckServiceImpl extends ServiceImpl<FxItemscheckMapper, FxItemscheck> implements FxItemscheckService {

    private final FxItemscheckMapper fxItemscheckMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param FxItemscheck查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FxItemscheck> getPage(PageParam<FxItemscheck> page, FxItemscheck param) {
        return fxItemscheckMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public FxItemscheck getInfoById(String id) {
        return fxItemscheckMapper.getInfoById(id);
    }

    ;

}


