package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.CorruptLobsMapper;
import com.center.medical.datamove.oracle.bean.model.CorruptLobs;
import com.center.medical.datamove.oracle.service.CorruptLobsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (CorruptLobs)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:52
 */
@Slf4j
@Service("corruptLobsService")
@RequiredArgsConstructor
public class CorruptLobsServiceImpl extends ServiceImpl<CorruptLobsMapper, CorruptLobs> implements CorruptLobsService {

    private final CorruptLobsMapper corruptLobsMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param CorruptLobs查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CorruptLobs> getPage(PageParam<CorruptLobs> page, CorruptLobs param) {
        return corruptLobsMapper.getPage(page, param);
    }


}


