package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SelltargetMapper;
import com.center.medical.datamove.oracle.bean.model.Selltarget;
import com.center.medical.datamove.oracle.service.SelltargetService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * XS销售目标(Selltarget)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:57
 */
@Slf4j
@Service("selltargetService")
@RequiredArgsConstructor
public class SelltargetServiceImpl extends ServiceImpl<SelltargetMapper, Selltarget> implements SelltargetService {

    private final SelltargetMapper selltargetMapper;

    /**
     * 分页查询[XS销售目标]列表
     *
     * @param page  分页参数
     * @param param Selltarget查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Selltarget> getPage(PageParam<Selltarget> page, Selltarget param) {
        return selltargetMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Selltarget getInfoById(String id) {
        return selltargetMapper.getInfoById(id);
    }

}


