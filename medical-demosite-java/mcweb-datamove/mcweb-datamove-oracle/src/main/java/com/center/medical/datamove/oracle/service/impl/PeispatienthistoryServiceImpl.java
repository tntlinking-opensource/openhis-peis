package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeispatienthistoryMapper;
import com.center.medical.datamove.oracle.bean.model.Peispatienthistory;
import com.center.medical.datamove.oracle.service.PeispatienthistoryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Peispatienthistory)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:31
 */
@Slf4j
@Service("peispatienthistoryService")
@RequiredArgsConstructor
public class PeispatienthistoryServiceImpl extends ServiceImpl<PeispatienthistoryMapper, Peispatienthistory> implements PeispatienthistoryService {

    private final PeispatienthistoryMapper peispatienthistoryMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Peispatienthistory查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatienthistory> getPage(PageParam<Peispatienthistory> page, Peispatienthistory param) {
        return peispatienthistoryMapper.getPage(page, param);
    }


}


