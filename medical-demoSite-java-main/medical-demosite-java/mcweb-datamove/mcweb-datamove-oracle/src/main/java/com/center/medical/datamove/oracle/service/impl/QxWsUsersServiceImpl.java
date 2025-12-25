package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxWsUsersMapper;
import com.center.medical.datamove.oracle.bean.model.QxWsUsers;
import com.center.medical.datamove.oracle.service.QxWsUsersService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (QxWsUsers)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:10
 */
@Slf4j
@Service("qxWsUsersService")
@RequiredArgsConstructor
public class QxWsUsersServiceImpl extends ServiceImpl<QxWsUsersMapper, QxWsUsers> implements QxWsUsersService {

    private final QxWsUsersMapper qxWsUsersMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxWsUsers查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxWsUsers> getPage(PageParam<QxWsUsers> page, QxWsUsers param) {
        return qxWsUsersMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public QxWsUsers getInfoById(String id) {
        return qxWsUsersMapper.getInfoById(id);
    }

}


