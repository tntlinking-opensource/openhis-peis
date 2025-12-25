package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdSectionAndRemindMapper;
import com.center.medical.datamove.common.bean.model.MdSectionAndRemind;
import com.center.medical.datamove.common.service.MdSectionAndRemindService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 科室提醒和科室关联表(MdSectionAndRemind)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:30
 */
@Slf4j
@Service("mdSectionAndRemindService")
@RequiredArgsConstructor
public class MdSectionAndRemindServiceImpl extends ServiceImpl<MdSectionAndRemindMapper, MdSectionAndRemind> implements MdSectionAndRemindService {

    private final MdSectionAndRemindMapper mdSectionAndRemindMapper;

    /**
     * 分页查询[科室提醒和科室关联表]列表
     *
     * @param page  分页参数
     * @param param MdSectionAndRemind查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSectionAndRemind> getPage(PageParam<MdSectionAndRemind> page, MdSectionAndRemind param) {
        return mdSectionAndRemindMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdSectionAndRemind getInfoById(String id) {
        return mdSectionAndRemindMapper.getInfoById(id);
    }

}


