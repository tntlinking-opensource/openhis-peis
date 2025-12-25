package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.MemberintegralMapper;
import com.center.medical.datamove.oracle.bean.model.Memberintegral;
import com.center.medical.datamove.oracle.service.MemberintegralService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 会员卡积分明细表(Memberintegral)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:06
 */
@Slf4j
@Service("memberintegralService")
@RequiredArgsConstructor
public class MemberintegralServiceImpl extends ServiceImpl<MemberintegralMapper, Memberintegral> implements MemberintegralService {

    private final MemberintegralMapper memberintegralMapper;

    /**
     * 分页查询[会员卡积分明细表]列表
     *
     * @param page  分页参数
     * @param param Memberintegral查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Memberintegral> getPage(PageParam<Memberintegral> page, Memberintegral param) {
        return memberintegralMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Memberintegral getInfoById(String id) {
        return memberintegralMapper.getInfoById(id);
    }

}


