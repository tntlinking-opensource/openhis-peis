package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdLeadertargetMapper;
import com.center.medical.datamove.common.bean.model.MdLeadertarget;
import com.center.medical.datamove.common.service.MdLeadertargetService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 领导目标表(MdLeadertarget)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:25
 */
@Slf4j
@Service("mdLeadertargetService")
@RequiredArgsConstructor
public class MdLeadertargetServiceImpl extends ServiceImpl<MdLeadertargetMapper, MdLeadertarget> implements MdLeadertargetService {

    private final MdLeadertargetMapper mdLeadertargetMapper;

    /**
     * 分页查询[领导目标表]列表
     *
     * @param page  分页参数
     * @param param MdLeadertarget查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdLeadertarget> getPage(PageParam<MdLeadertarget> page, MdLeadertarget param) {
        return mdLeadertargetMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdLeadertarget getInfoById(String id) {
        return mdLeadertargetMapper.getInfoById(id);
    }

}


