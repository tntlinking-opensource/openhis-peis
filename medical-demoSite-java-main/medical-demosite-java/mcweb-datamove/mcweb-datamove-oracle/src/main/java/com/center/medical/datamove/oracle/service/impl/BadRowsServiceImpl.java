package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BadRowsMapper;
import com.center.medical.datamove.oracle.bean.model.BadRows;
import com.center.medical.datamove.oracle.service.BadRowsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (BadRows)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:17
 */
@Slf4j
@Service("badRowsService")
@RequiredArgsConstructor
public class BadRowsServiceImpl extends ServiceImpl<BadRowsMapper, BadRows> implements BadRowsService {

    private final BadRowsMapper badRowsMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param BadRows查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BadRows> getPage(PageParam<BadRows> page, BadRows param) {
        return badRowsMapper.getPage(page, param);
    }


}


