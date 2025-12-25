package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.FylxMapper;
import com.center.medical.bean.model.Fylx;
import com.center.medical.service.FylxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC费用类型(Fylx)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:03
 */
@Slf4j
@Service("fylxService")
@RequiredArgsConstructor
public class FylxServiceImpl extends ServiceImpl<FylxMapper, Fylx> implements FylxService {

    private final FylxMapper fylxMapper;

    /**
     * 分页查询[JC费用类型]列表
     *
     * @param page  分页参数
     * @param param Fylx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Fylx> getList(PageParam<Fylx> page, Fylx param) {
        return fylxMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Fylx getInfoById(String id) {
        return fylxMapper.getInfoById(id);
    }

}

