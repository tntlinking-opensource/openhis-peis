package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdCardMemberMedicalMapper;
import com.center.medical.datamove.common.bean.model.MdCardMemberMedical;
import com.center.medical.datamove.common.service.MdCardMemberMedicalService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 会员卡体检卡关联表(MdCardMemberMedical)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:14
 */
@Slf4j
@Service("mdCardMemberMedicalService")
@RequiredArgsConstructor
public class MdCardMemberMedicalServiceImpl extends ServiceImpl<MdCardMemberMedicalMapper, MdCardMemberMedical> implements MdCardMemberMedicalService {

    private final MdCardMemberMedicalMapper mdCardMemberMedicalMapper;

    /**
     * 分页查询[会员卡体检卡关联表]列表
     *
     * @param page  分页参数
     * @param param MdCardMemberMedical查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdCardMemberMedical> getPage(PageParam<MdCardMemberMedical> page, MdCardMemberMedical param) {
        return mdCardMemberMedicalMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdCardMemberMedical getInfoById(String id) {
        return mdCardMemberMedicalMapper.getInfoById(id);
    }

}


