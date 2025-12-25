package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.FxPositiveMapper;
import com.center.medical.bean.model.FxPositive;
import com.center.medical.service.FxPositiveService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 综合分析-阳性小结(FxPositive)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:18
 */
@Slf4j
@Service("fxPositiveService")
@RequiredArgsConstructor
public class FxPositiveServiceImpl extends ServiceImpl<FxPositiveMapper, FxPositive> implements FxPositiveService {

    private final FxPositiveMapper fxPositiveMapper;

    /**
     * 分页查询[综合分析-阳性小结]列表
     *
     * @param page  分页参数
     * @param param FxPositive查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FxPositive> getList(PageParam<FxPositive> page, FxPositive param) {
        return fxPositiveMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public FxPositive getInfoById(String id) {
        return fxPositiveMapper.getInfoById(id);
    }

    ;

}

