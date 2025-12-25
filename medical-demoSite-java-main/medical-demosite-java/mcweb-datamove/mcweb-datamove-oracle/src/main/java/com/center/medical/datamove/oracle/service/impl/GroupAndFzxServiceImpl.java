package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.GroupAndFzxMapper;
import com.center.medical.datamove.oracle.bean.model.GroupAndFzx;
import com.center.medical.datamove.oracle.service.GroupAndFzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (GroupAndFzx)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:04
 */
@Slf4j
@Service("groupAndFzxService")
@RequiredArgsConstructor
public class GroupAndFzxServiceImpl extends ServiceImpl<GroupAndFzxMapper, GroupAndFzx> implements GroupAndFzxService {

    private final GroupAndFzxMapper groupAndFzxMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param GroupAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<GroupAndFzx> getPage(PageParam<GroupAndFzx> page, GroupAndFzx param) {
        return groupAndFzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public GroupAndFzx getInfoById(String id) {
        return groupAndFzxMapper.getInfoById(id);
    }

}


