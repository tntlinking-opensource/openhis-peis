package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxUserBranchMapper;
import com.center.medical.datamove.oracle.bean.model.QxUserBranch;
import com.center.medical.datamove.oracle.service.QxUserBranchService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (QxUserBranch)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:54
 */
@Slf4j
@Service("qxUserBranchService")
@RequiredArgsConstructor
public class QxUserBranchServiceImpl extends ServiceImpl<QxUserBranchMapper, QxUserBranch> implements QxUserBranchService {

    private final QxUserBranchMapper qxUserBranchMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxUserBranch查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxUserBranch> getPage(PageParam<QxUserBranch> page, QxUserBranch param) {
        return qxUserBranchMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public QxUserBranch getInfoById(String id) {
        return qxUserBranchMapper.getInfoById(id);
    }

}


