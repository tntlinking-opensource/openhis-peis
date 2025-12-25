package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SectionRemindMapper;
import com.center.medical.datamove.oracle.bean.model.SectionRemind;
import com.center.medical.datamove.oracle.service.SectionRemindService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (SectionRemind)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:41
 */
@Slf4j
@Service("sectionRemindService")
@RequiredArgsConstructor
public class SectionRemindServiceImpl extends ServiceImpl<SectionRemindMapper, SectionRemind> implements SectionRemindService {

    private final SectionRemindMapper sectionRemindMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param SectionRemind查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SectionRemind> getPage(PageParam<SectionRemind> page, SectionRemind param) {
        return sectionRemindMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SectionRemind getInfoById(String id) {
        return sectionRemindMapper.getInfoById(id);
    }

    ;

}


