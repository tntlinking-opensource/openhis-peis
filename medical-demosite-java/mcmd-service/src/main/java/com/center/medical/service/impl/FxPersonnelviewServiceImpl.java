package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.FxPersonnelviewMapper;
import com.center.medical.bean.model.FxPersonnelview;
import com.center.medical.service.FxPersonnelviewService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 综合分析-人员一览表(FxPersonnelview)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:19
 */
@Slf4j
@Service("fxPersonnelviewService")
@RequiredArgsConstructor
public class FxPersonnelviewServiceImpl extends ServiceImpl<FxPersonnelviewMapper, FxPersonnelview> implements FxPersonnelviewService {

    private final FxPersonnelviewMapper fxPersonnelviewMapper;

    /**
     * 分页查询[综合分析-人员一览表]列表
     *
     * @param page  分页参数
     * @param param FxPersonnelview查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FxPersonnelview> getList(PageParam<FxPersonnelview> page, FxPersonnelview param) {
        return fxPersonnelviewMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public FxPersonnelview getInfoById(String id) {
        return fxPersonnelviewMapper.getInfoById(id);
    }

}

