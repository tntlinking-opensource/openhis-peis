package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BasconclusiontypeMapper;
import com.center.medical.datamove.oracle.bean.model.Basconclusiontype;
import com.center.medical.datamove.oracle.service.BasconclusiontypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Basconclusiontype)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:24
 */
@Slf4j
@Service("basconclusiontypeService")
@RequiredArgsConstructor
public class BasconclusiontypeServiceImpl extends ServiceImpl<BasconclusiontypeMapper, Basconclusiontype> implements BasconclusiontypeService {

    private final BasconclusiontypeMapper basconclusiontypeMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Basconclusiontype查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Basconclusiontype> getPage(PageParam<Basconclusiontype> page, Basconclusiontype param) {
        return basconclusiontypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Basconclusiontype getInfoById(String id) {
        return basconclusiontypeMapper.getInfoById(id);
    }

}


