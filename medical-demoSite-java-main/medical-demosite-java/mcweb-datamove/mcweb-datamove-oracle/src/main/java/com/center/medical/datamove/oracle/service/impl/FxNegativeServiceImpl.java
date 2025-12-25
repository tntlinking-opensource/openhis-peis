package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.FxNegativeMapper;
import com.center.medical.datamove.oracle.bean.model.FxNegative;
import com.center.medical.datamove.oracle.service.FxNegativeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (FxNegative)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:54
 */
@Slf4j
@Service("fxNegativeService")
@RequiredArgsConstructor
public class FxNegativeServiceImpl extends ServiceImpl<FxNegativeMapper, FxNegative> implements FxNegativeService {

    private final FxNegativeMapper fxNegativeMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param FxNegative查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FxNegative> getPage(PageParam<FxNegative> page, FxNegative param) {
        return fxNegativeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public FxNegative getInfoById(String id) {
        return fxNegativeMapper.getInfoById(id);
    }

}


