package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdMemberbirthdatMapper;
import com.center.medical.datamove.common.bean.model.MdMemberbirthdat;
import com.center.medical.datamove.common.service.MdMemberbirthdatService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 会员生日提醒回访表(MdMemberbirthdat)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:26
 */
@Slf4j
@Service("mdMemberbirthdatService")
@RequiredArgsConstructor
public class MdMemberbirthdatServiceImpl extends ServiceImpl<MdMemberbirthdatMapper, MdMemberbirthdat> implements MdMemberbirthdatService {

    private final MdMemberbirthdatMapper mdMemberbirthdatMapper;

    /**
     * 分页查询[会员生日提醒回访表]列表
     *
     * @param page  分页参数
     * @param param MdMemberbirthdat查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdMemberbirthdat> getPage(PageParam<MdMemberbirthdat> page, MdMemberbirthdat param) {
        return mdMemberbirthdatMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdMemberbirthdat getInfoById(String id) {
        return mdMemberbirthdatMapper.getInfoById(id);
    }

}


