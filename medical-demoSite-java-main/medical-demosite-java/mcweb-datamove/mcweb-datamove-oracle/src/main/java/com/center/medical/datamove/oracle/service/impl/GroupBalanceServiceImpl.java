package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.GroupBalanceMapper;
import com.center.medical.datamove.oracle.bean.model.GroupBalance;
import com.center.medical.datamove.oracle.service.GroupBalanceService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (GroupBalance)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:05
 */
@Slf4j
@Service("groupBalanceService")
@RequiredArgsConstructor
public class GroupBalanceServiceImpl extends ServiceImpl<GroupBalanceMapper, GroupBalance> implements GroupBalanceService {

    private final GroupBalanceMapper groupBalanceMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param GroupBalance查询参数
     * @return 分页数据
     */
    @Override
    public IPage<GroupBalance> getPage(PageParam<GroupBalance> page, GroupBalance param) {
        return groupBalanceMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public GroupBalance getInfoById(String id) {
        return groupBalanceMapper.getInfoById(id);
    }

}


