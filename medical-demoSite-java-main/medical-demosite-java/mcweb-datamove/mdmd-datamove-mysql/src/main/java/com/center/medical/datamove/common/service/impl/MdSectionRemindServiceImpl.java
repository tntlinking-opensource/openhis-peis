package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdSectionRemindMapper;
import com.center.medical.datamove.common.bean.model.MdSectionRemind;
import com.center.medical.datamove.common.service.MdSectionRemindService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 科室提醒主表(MdSectionRemind)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:31
 */
@Slf4j
@Service("mdSectionRemindService")
@RequiredArgsConstructor
public class MdSectionRemindServiceImpl extends ServiceImpl<MdSectionRemindMapper, MdSectionRemind> implements MdSectionRemindService {

    private final MdSectionRemindMapper mdSectionRemindMapper;

    /**
     * 分页查询[科室提醒主表]列表
     *
     * @param page  分页参数
     * @param param MdSectionRemind查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSectionRemind> getPage(PageParam<MdSectionRemind> page, MdSectionRemind param) {
        return mdSectionRemindMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdSectionRemind getInfoById(String id) {
        return mdSectionRemindMapper.getInfoById(id);
    }

}


