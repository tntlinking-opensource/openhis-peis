package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.FxHarmMapper;
import com.center.medical.datamove.oracle.bean.model.FxHarm;
import com.center.medical.datamove.oracle.service.FxHarmService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 职业团检分析-危害因素(FxHarm)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:52
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
    public IPage<FxHarm> getPage(PageParam<FxHarm> page, FxHarm param) {
        return fxHarmMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public FxHarm getInfoById(String id) {
        return fxHarmMapper.getInfoById(id);
    }

}


