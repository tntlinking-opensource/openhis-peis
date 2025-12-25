package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.TemporaryqueueMapper;
import com.center.medical.datamove.oracle.bean.model.Temporaryqueue;
import com.center.medical.datamove.oracle.service.TemporaryqueueService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Temporaryqueue)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:18
 */
@Slf4j
@Service("temporaryqueueService")
@RequiredArgsConstructor
public class TemporaryqueueServiceImpl extends ServiceImpl<TemporaryqueueMapper, Temporaryqueue> implements TemporaryqueueService {

    private final TemporaryqueueMapper temporaryqueueMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Temporaryqueue查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Temporaryqueue> getPage(PageParam<Temporaryqueue> page, Temporaryqueue param) {
        return temporaryqueueMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Temporaryqueue getInfoById(Object id) {
        return temporaryqueueMapper.getInfoById(id);
    }

    ;

}


