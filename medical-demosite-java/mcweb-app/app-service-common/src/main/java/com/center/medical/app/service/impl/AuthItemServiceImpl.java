package com.center.medical.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.app.bean.model.AuthItem;
import com.center.medical.app.dao.AuthItemMapper;
import com.center.medical.app.service.AuthItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 外部授权记录(AuthItem)服务实现类
 *
 * @author makejava
 * @since 2023-09-19 11:55:00
 */
@Slf4j
@Service("authItemService")
@RequiredArgsConstructor
public class AuthItemServiceImpl extends ServiceImpl<AuthItemMapper, AuthItem> implements AuthItemService {

    private final AuthItemMapper authItemMapper;

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AuthItem getInfoById(Integer id) {
        return authItemMapper.getInfoById(id);
    }

}

