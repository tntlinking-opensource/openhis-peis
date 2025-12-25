package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SectionTotalMapper;
import com.center.medical.datamove.oracle.bean.model.SectionTotal;
import com.center.medical.datamove.oracle.service.SectionTotalService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ZJ总检主表(SectionTotal)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:47
 */
@Slf4j
@Service("sectionTotalService")
@RequiredArgsConstructor
public class SectionTotalServiceImpl extends ServiceImpl<SectionTotalMapper, SectionTotal> implements SectionTotalService {

    private final SectionTotalMapper sectionTotalMapper;

    /**
     * 分页查询[ZJ总检主表]列表
     *
     * @param page  分页参数
     * @param param SectionTotal查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SectionTotal> getPage(PageParam<SectionTotal> page, SectionTotal param) {
        return sectionTotalMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SectionTotal getInfoById(String id) {
        return sectionTotalMapper.getInfoById(id);
    }

}


