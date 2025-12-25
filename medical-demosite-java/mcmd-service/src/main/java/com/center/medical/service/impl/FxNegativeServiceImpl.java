package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.FxNegativeMapper;
import com.center.medical.bean.model.FxNegative;
import com.center.medical.service.FxNegativeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 综合分析-阴性小结(FxNegative)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:40
 */
@Slf4j
@Service("fxNegativeService")
@RequiredArgsConstructor
public class FxNegativeServiceImpl extends ServiceImpl<FxNegativeMapper, FxNegative> implements FxNegativeService {

    private final FxNegativeMapper fxNegativeMapper;

    /**
     * 分页查询[综合分析-阴性小结]列表
     *
     * @param page  分页参数
     * @param param FxNegative查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FxNegative> getList(PageParam<FxNegative> page, FxNegative param) {
        return fxNegativeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public FxNegative getInfoById(String id) {
        return fxNegativeMapper.getInfoById(id);
    }

}

