package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.WsUser;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.dao.WsUserMapper;
import com.center.medical.sellcrm.service.WsUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户信息表(WsUser)服务实现类
 *
 * @author makejava
 * @since 2024-09-12 09:29:50
 */
@Slf4j
@Service("wsUserService")
@RequiredArgsConstructor
public class WsUserServiceImpl extends ServiceImpl<WsUserMapper, WsUser> implements WsUserService {

    private final WsUserMapper wsUserMapper;

    /**
     * 分页查询[用户信息表]列表
     *
     * @param page  分页参数
     * @param param WsUser查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WsUser> getPage(PageParam<WsUser> page, WsUser param) {
        return wsUserMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    @Override
    public WsUser getInfoById(Long id) {
        return wsUserMapper.getInfoById(id);
    }

}

