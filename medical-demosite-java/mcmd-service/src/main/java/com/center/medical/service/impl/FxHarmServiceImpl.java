package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.FxHarmMapper;
import com.center.medical.bean.model.FxHarm;
import com.center.medical.service.FxHarmService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 职业团检分析-危害因素(FxHarm)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:04
 */
@Slf4j
@Service("fxHarmService")
@RequiredArgsConstructor
public class FxHarmServiceImpl extends ServiceImpl<FxHarmMapper, FxHarm> implements FxHarmService {

    private final FxHarmMapper fxHarmMapper;

    /**
     * 分页查询[职业团检分析-危害因素]列表
     *
     * @param page  分页参数
     * @param param FxHarm查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FxHarm> getList(PageParam<FxHarm> page, FxHarm param) {
        return fxHarmMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public FxHarm getInfoById(String id) {
        return fxHarmMapper.getInfoById(id);
    }

}

