package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Riskclient;
import com.center.medical.sellcrm.bean.param.RiskclientParam;
import com.center.medical.sellcrm.dao.RiskclientMapper;
import com.center.medical.sellcrm.service.RiskclientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 高危人员管理表(Riskclient)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-22 19:29:10
 */
@Slf4j
@Service("riskclientService")
@RequiredArgsConstructor
public class RiskclientServiceImpl extends ServiceImpl<RiskclientMapper, Riskclient> implements RiskclientService {

    private final RiskclientMapper riskclientMapper;

    /**
     * 分页查询[高危人员管理表]列表
     *
     * @param page  分页参数
     * @param param Riskclient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Riskclient> getPage(PageParam<Riskclient> page, RiskclientParam param) {
        if (!SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE)) {
            //不是决策者,获取本分中心下的高危高危人员数据
            param.setBranchIds(Arrays.asList(SecurityUtils.getCId()));
            if (!SecurityUtils.isLeader()) {
                //不为领导,获取当前登录者的数据
                //获取高危人员数据
                param.setIdOpendoctor(SecurityUtils.getUserNo());
            }
        }
        return riskclientMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Riskclient getInfoById(String id) {
        return riskclientMapper.getInfoById(id);
    }

}

