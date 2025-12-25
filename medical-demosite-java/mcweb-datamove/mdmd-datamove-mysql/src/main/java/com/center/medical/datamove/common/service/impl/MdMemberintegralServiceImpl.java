package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdMemberintegralMapper;
import com.center.medical.datamove.common.bean.model.MdMemberintegral;
import com.center.medical.datamove.common.service.MdMemberintegralService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 会员卡积分明细表(MdMemberintegral)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:26
 */
@Slf4j
@Service("mdMemberintegralService")
@RequiredArgsConstructor
public class MdMemberintegralServiceImpl extends ServiceImpl<MdMemberintegralMapper, MdMemberintegral> implements MdMemberintegralService {

    private final MdMemberintegralMapper mdMemberintegralMapper;

    /**
     * 分页查询[会员卡积分明细表]列表
     *
     * @param page  分页参数
     * @param param MdMemberintegral查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdMemberintegral> getPage(PageParam<MdMemberintegral> page, MdMemberintegral param) {
        return mdMemberintegralMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdMemberintegral getInfoById(String id) {
        return mdMemberintegralMapper.getInfoById(id);
    }

}


