package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.TTjZyzlqkbMapper;
import com.center.medical.datamove.oracle.bean.model.TTjZyzlqkb;
import com.center.medical.datamove.oracle.service.TTjZyzlqkbService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (TTjZyzlqkb)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:11
 */
@Slf4j
@Service("tTjZyzlqkbService")
@RequiredArgsConstructor
public class TTjZyzlqkbServiceImpl extends ServiceImpl<TTjZyzlqkbMapper, TTjZyzlqkb> implements TTjZyzlqkbService {

    private final TTjZyzlqkbMapper tTjZyzlqkbMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param TTjZyzlqkb查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TTjZyzlqkb> getPage(PageParam<TTjZyzlqkb> page, TTjZyzlqkb param) {
        return tTjZyzlqkbMapper.getPage(page, param);
    }


}


