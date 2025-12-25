package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdAttachmentGroupMapper;
import com.center.medical.datamove.common.bean.model.MdAttachmentGroup;
import com.center.medical.datamove.common.service.MdAttachmentGroupService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件分组表(MdAttachmentGroup)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:07
 */
@Slf4j
@Service("mdAttachmentGroupService")
@RequiredArgsConstructor
public class MdAttachmentGroupServiceImpl extends ServiceImpl<MdAttachmentGroupMapper, MdAttachmentGroup> implements MdAttachmentGroupService {

    private final MdAttachmentGroupMapper mdAttachmentGroupMapper;

    /**
     * 分页查询[文件分组表]列表
     *
     * @param page  分页参数
     * @param param MdAttachmentGroup查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdAttachmentGroup> getPage(PageParam<MdAttachmentGroup> page, MdAttachmentGroup param) {
        return mdAttachmentGroupMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键fileGroupId
     * @return 详情信息
     */
    @Override
    public MdAttachmentGroup getInfoById(String id) {
        return mdAttachmentGroupMapper.getInfoById(id);
    }

}


