package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.Peispatient2Mapper;
import com.center.medical.datamove.oracle.bean.model.Peispatient2;
import com.center.medical.datamove.oracle.service.Peispatient2Service;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * QT体检者表(Peispatient2)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:11
 */
@Slf4j
@Service("peispatient2Service")
@RequiredArgsConstructor
public class Peispatient2ServiceImpl extends ServiceImpl<Peispatient2Mapper, Peispatient2> implements Peispatient2Service {

    private final Peispatient2Mapper peispatient2Mapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient2查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatient2> getPage(PageParam<Peispatient2> page, Peispatient2 param) {
        return peispatient2Mapper.getPage(page, param);
    }


}


