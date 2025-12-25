package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.FxPositiveMapper;
import com.center.medical.datamove.oracle.bean.model.FxPositive;
import com.center.medical.datamove.oracle.service.FxPositiveService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (FxPositive)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:57
 */
@Slf4j
@Service("fxPositiveService")
@RequiredArgsConstructor
public class FxPositiveServiceImpl extends ServiceImpl<FxPositiveMapper, FxPositive> implements FxPositiveService {

    private final FxPositiveMapper fxPositiveMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param FxPositive查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FxPositive> getPage(PageParam<FxPositive> page, FxPositive param) {
        return fxPositiveMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public FxPositive getInfoById(String id) {
        return fxPositiveMapper.getInfoById(id);
    }

}


