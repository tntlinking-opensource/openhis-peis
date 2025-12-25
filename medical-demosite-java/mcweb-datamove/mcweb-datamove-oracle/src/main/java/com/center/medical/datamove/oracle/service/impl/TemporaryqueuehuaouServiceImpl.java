package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.TemporaryqueuehuaouMapper;
import com.center.medical.datamove.oracle.bean.model.Temporaryqueuehuaou;
import com.center.medical.datamove.oracle.service.TemporaryqueuehuaouService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Temporaryqueuehuaou)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:19
 */
@Slf4j
@Service("temporaryqueuehuaouService")
@RequiredArgsConstructor
public class TemporaryqueuehuaouServiceImpl extends ServiceImpl<TemporaryqueuehuaouMapper, Temporaryqueuehuaou> implements TemporaryqueuehuaouService {

    private final TemporaryqueuehuaouMapper temporaryqueuehuaouMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Temporaryqueuehuaou查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Temporaryqueuehuaou> getPage(PageParam<Temporaryqueuehuaou> page, Temporaryqueuehuaou param) {
        return temporaryqueuehuaouMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Temporaryqueuehuaou getInfoById(Object id) {
        return temporaryqueuehuaouMapper.getInfoById(id);
    }

    ;

}


