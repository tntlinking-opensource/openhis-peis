package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SectionAndRemindMapper;
import com.center.medical.datamove.oracle.bean.model.SectionAndRemind;
import com.center.medical.datamove.oracle.service.SectionAndRemindService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (SectionAndRemind)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:40
 */
@Slf4j
@Service("sectionAndRemindService")
@RequiredArgsConstructor
public class SectionAndRemindServiceImpl extends ServiceImpl<SectionAndRemindMapper, SectionAndRemind> implements SectionAndRemindService {

    private final SectionAndRemindMapper sectionAndRemindMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param SectionAndRemind查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SectionAndRemind> getPage(PageParam<SectionAndRemind> page, SectionAndRemind param) {
        return sectionAndRemindMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SectionAndRemind getInfoById(String id) {
        return sectionAndRemindMapper.getInfoById(id);
    }

}


