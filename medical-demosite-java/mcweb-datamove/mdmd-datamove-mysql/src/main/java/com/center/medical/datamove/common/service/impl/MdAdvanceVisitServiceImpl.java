package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdAdvanceVisitMapper;
import com.center.medical.datamove.common.bean.model.MdAdvanceVisit;
import com.center.medical.datamove.common.service.MdAdvanceVisitService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KF预检跟踪回访记录表(MdAdvanceVisit)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:05
 */
@Slf4j
@Service("mdAdvanceVisitService")
@RequiredArgsConstructor
public class MdAdvanceVisitServiceImpl extends ServiceImpl<MdAdvanceVisitMapper, MdAdvanceVisit> implements MdAdvanceVisitService {

    private final MdAdvanceVisitMapper mdAdvanceVisitMapper;

    /**
     * 分页查询[KF预检跟踪回访记录表]列表
     *
     * @param page  分页参数
     * @param param MdAdvanceVisit查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdAdvanceVisit> getPage(PageParam<MdAdvanceVisit> page, MdAdvanceVisit param) {
        return mdAdvanceVisitMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdAdvanceVisit getInfoById(String id) {
        return mdAdvanceVisitMapper.getInfoById(id);
    }

}


