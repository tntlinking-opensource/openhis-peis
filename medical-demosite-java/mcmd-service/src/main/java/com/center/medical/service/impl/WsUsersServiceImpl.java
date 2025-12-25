package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.WsUsers;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.WsUsersMapper;
import com.center.medical.service.WsUsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 网站用户(WsUsers)服务实现类
 *
 * @author ay
 * @since 2024-05-29 16:31:10
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

