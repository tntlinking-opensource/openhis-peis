package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AdvanceVisitWriteMapper;
import com.center.medical.datamove.oracle.bean.model.AdvanceVisitWrite;
import com.center.medical.datamove.oracle.service.AdvanceVisitWriteService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 主表记录体检者是否来检，如果来检，显示来检时间，便于筛选，在预约来检未检列表中显示来检时间且是否已检为未检的做出标记，在预约来检未检回访操作时将该状态、来检时间重置，以此进行无限循环(AdvanceVisitWrite)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:22
 */
@Slf4j
@Service("advanceVisitWriteService")
@RequiredArgsConstructor
public class AdvanceVisitWriteServiceImpl extends ServiceImpl<AdvanceVisitWriteMapper, AdvanceVisitWrite> implements AdvanceVisitWriteService {

    private final AdvanceVisitWriteMapper advanceVisitWriteMapper;

    /**
     * 分页查询[主表记录体检者是否来检，如果来检，显示来检时间，便于筛选，在预约来检未检列表中显示来检时间且是否已检为未检的做出标记，在预约来检未检回访操作时将该状态、来检时间重置，以此进行无限循环]列表
     *
     * @param page  分页参数
     * @param param AdvanceVisitWrite查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AdvanceVisitWrite> getPage(PageParam<AdvanceVisitWrite> page, AdvanceVisitWrite param) {
        return advanceVisitWriteMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AdvanceVisitWrite getInfoById(String id) {
        return advanceVisitWriteMapper.getInfoById(id);
    }

}


