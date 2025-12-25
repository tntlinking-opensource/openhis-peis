package com.center.medical.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.app.bean.model.AuthCode;

/**
 * 加密密钥及授权码表(AuthCode)服务接口
 *
 * @author makejava
 * @since 2023-09-19 18:35:48
 */
public interface AuthCodeService extends IService<AuthCode> {

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AuthCode getInfoById(Long id);

}

