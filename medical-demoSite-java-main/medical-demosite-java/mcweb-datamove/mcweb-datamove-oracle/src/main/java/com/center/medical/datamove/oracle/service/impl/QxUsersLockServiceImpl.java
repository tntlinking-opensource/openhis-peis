package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxUsersLockMapper;
import com.center.medical.datamove.oracle.bean.model.QxUsersLock;
import com.center.medical.datamove.oracle.service.QxUsersLockService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (QxUsersLock)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:58
 */
@Slf4j
@Service("qxUsersLockService")
@RequiredArgsConstructor
public class QxUsersLockServiceImpl extends ServiceImpl<QxUsersLockMapper, QxUsersLock> implements QxUsersLockService {

    private final QxUsersLockMapper qxUsersLockMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxUsersLock查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxUsersLock> getPage(PageParam<QxUsersLock> page, QxUsersLock param) {
        return qxUsersLockMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public QxUsersLock getInfoById(String id) {
        return qxUsersLockMapper.getInfoById(id);
    }

}


