package com.center.medical.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.app.bean.model.AuthCode;
import com.center.medical.app.dao.AuthCodeMapper;
import com.center.medical.app.service.AuthCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 加密密钥及授权码表(AuthCode)服务实现类
 *
 * @author makejava
 * @since 2023-09-19 18:35:48
 */
@Slf4j
@Service("authCodeService")
@RequiredArgsConstructor
public class AuthCodeServiceImpl extends ServiceImpl<AuthCodeMapper, AuthCode> implements AuthCodeService {

    private final AuthCodeMapper authCodeMapper;

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AuthCode getInfoById(Long id) {
        return authCodeMapper.getInfoById(id);
    }

}

