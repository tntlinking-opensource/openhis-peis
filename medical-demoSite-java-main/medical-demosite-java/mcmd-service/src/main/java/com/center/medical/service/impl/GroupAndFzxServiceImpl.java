package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.GroupAndFzxMapper;
import com.center.medical.bean.model.GroupAndFzx;
import com.center.medical.service.GroupAndFzxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 分组分中心(GroupAndFzx)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:14
 */
@Slf4j
@Service("groupAndFzxService")
@RequiredArgsConstructor
public class GroupAndFzxServiceImpl extends ServiceImpl<GroupAndFzxMapper, GroupAndFzx> implements GroupAndFzxService {

    private final GroupAndFzxMapper groupAndFzxMapper;

    /**
     * 分页查询[分组分中心]列表
     *
     * @param page  分页参数
     * @param param GroupAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<GroupAndFzx> getList(PageParam<GroupAndFzx> page, GroupAndFzx param) {
        return groupAndFzxMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public GroupAndFzx getInfoById(String id) {
        return groupAndFzxMapper.getInfoById(id);
    }

}

