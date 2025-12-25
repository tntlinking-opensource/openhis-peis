package com.center.medical.member.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.Memberintegral;
import com.center.medical.member.bean.param.MemberintegralParam;
import com.center.medical.member.dao.MemberintegralMapper;
import com.center.medical.member.service.MemberintegralService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员卡积分明细表(Memberintegral)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-24 09:59:57
 */
@Slf4j
@Service("memberintegralService")
@RequiredArgsConstructor
public class MemberintegralServiceImpl extends ServiceImpl<MemberintegralMapper, Memberintegral> implements MemberintegralService {

    private final MemberintegralMapper memberintegralMapper;
    private final ISysConfigService iSysConfigService;

    /**
     * 分页查询[会员卡积分明细表]列表
     *
     * @param page  分页参数
     * @param param Memberintegral查询参数
     * @return 分页数据
     */
    @Override

    public IPage<Memberintegral> getPage(PageParam<Memberintegral> page, MemberintegralParam param) {
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

    /**
     * 根据条件筛选用户积分数据
     *
     * @param param
     * @return
     */
    @Override
    public List<Memberintegral> getList(MemberintegralParam param) {
        return memberintegralMapper.getList(param);
    }

}

