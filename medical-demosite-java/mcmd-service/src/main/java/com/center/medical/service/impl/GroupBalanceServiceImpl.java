package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.GroupBalanceMapper;
import com.center.medical.bean.model.GroupBalance;
import com.center.medical.service.GroupBalanceService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检团体结算表(GroupBalance)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:36
 */
@Slf4j
@Service("groupBalanceService")
@RequiredArgsConstructor
public class GroupBalanceServiceImpl extends ServiceImpl<GroupBalanceMapper, GroupBalance> implements GroupBalanceService {

    private final GroupBalanceMapper groupBalanceMapper;

    /**
     * 分页查询[体检团体结算表]列表
     *
     * @param page  分页参数
     * @param param GroupBalance查询参数
     * @return 分页数据
     */
    @Override
    public IPage<GroupBalance> getList(PageParam<GroupBalance> page, GroupBalance param) {
        return groupBalanceMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public GroupBalance getInfoById(String id) {
        return groupBalanceMapper.getInfoById(id);
    }

}

