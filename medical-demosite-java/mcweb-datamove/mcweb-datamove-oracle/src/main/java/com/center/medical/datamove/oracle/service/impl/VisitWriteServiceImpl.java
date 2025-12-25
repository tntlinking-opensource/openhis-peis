package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.VisitWriteMapper;
import com.center.medical.datamove.oracle.bean.model.VisitWrite;
import com.center.medical.datamove.oracle.service.VisitWriteService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KF回访记录表(VisitWrite)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:54
 */
@Slf4j
@Service("visitWriteService")
@RequiredArgsConstructor
public class VisitWriteServiceImpl extends ServiceImpl<VisitWriteMapper, VisitWrite> implements VisitWriteService {

    private final VisitWriteMapper visitWriteMapper;

    /**
     * 分页查询[KF回访记录表]列表
     *
     * @param page  分页参数
     * @param param VisitWrite查询参数
     * @return 分页数据
     */
    @Override
    public IPage<VisitWrite> getPage(PageParam<VisitWrite> page, VisitWrite param) {
        return visitWriteMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public VisitWrite getInfoById(String id) {
        return visitWriteMapper.getInfoById(id);
    }

}


