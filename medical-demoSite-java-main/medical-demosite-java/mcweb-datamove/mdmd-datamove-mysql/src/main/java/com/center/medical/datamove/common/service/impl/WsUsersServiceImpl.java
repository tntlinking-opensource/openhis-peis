package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.WsUsersMapper;
import com.center.medical.datamove.common.bean.model.WsUsers;
import com.center.medical.datamove.common.service.WsUsersService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 网站用户(WsUsers)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:45
 */
@Slf4j
@Service("wsUsersService")
@RequiredArgsConstructor
public class WsUsersServiceImpl extends ServiceImpl<WsUsersMapper, WsUsers> implements WsUsersService {

    private final WsUsersMapper wsUsersMapper;

    /**
     * 分页查询[网站用户]列表
     *
     * @param page  分页参数
     * @param param WsUsers查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WsUsers> getPage(PageParam<WsUsers> page, WsUsers param) {
        return wsUsersMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WsUsers getInfoById(String id) {
        return wsUsersMapper.getInfoById(id);
    }

}


