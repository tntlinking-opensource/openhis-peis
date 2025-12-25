package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeispatientHisMapper;
import com.center.medical.datamove.oracle.bean.model.PeispatientHis;
import com.center.medical.datamove.oracle.service.PeispatientHisService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * QT体检者表(PeispatientHis)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:21
 */
@Slf4j
@Service("peispatientHisService")
@RequiredArgsConstructor
public class PeispatientHisServiceImpl extends ServiceImpl<PeispatientHisMapper, PeispatientHis> implements PeispatientHisService {

    private final PeispatientHisMapper peispatientHisMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param PeispatientHis查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeispatientHis> getPage(PageParam<PeispatientHis> page, PeispatientHis param) {
        return peispatientHisMapper.getPage(page, param);
    }


}


