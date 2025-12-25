package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxUsersMapper;
import com.center.medical.datamove.oracle.bean.model.QxUsers;
import com.center.medical.datamove.oracle.service.QxUsersService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (QxUsers)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:57
 */
@Slf4j
@Service("qxUsersService")
@RequiredArgsConstructor
public class QxUsersServiceImpl extends ServiceImpl<QxUsersMapper, QxUsers> implements QxUsersService {

    private final QxUsersMapper qxUsersMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxUsers查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxUsers> getPage(PageParam<QxUsers> page, QxUsers param) {
        return qxUsersMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public QxUsers getInfoById(String id) {
        return qxUsersMapper.getInfoById(id);
    }

}


