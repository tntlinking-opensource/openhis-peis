package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Basconclusiontype;
import com.center.medical.data.dao.BasconclusiontypeMapper;
import com.center.medical.data.service.BasconclusiontypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 总检结论词类型(Basconclusiontype)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:03
 */
@Slf4j
@Service("basconclusiontypeService")
@RequiredArgsConstructor
public class BasconclusiontypeServiceImpl extends ServiceImpl<BasconclusiontypeMapper, Basconclusiontype> implements BasconclusiontypeService {

    private final BasconclusiontypeMapper basconclusiontypeMapper;

    /**
     * 分页查询[总检结论词类型]列表
     *
     * @param page  分页参数
     * @param param Basconclusiontype查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Basconclusiontype> getList(PageParam<Basconclusiontype> page, Basconclusiontype param) {
        return basconclusiontypeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Basconclusiontype getInfoById(String id) {
        return basconclusiontypeMapper.getInfoById(id);
    }

}

