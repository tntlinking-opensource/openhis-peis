package com.center.medical.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.app.bean.model.AuthCode;
import org.apache.ibatis.annotations.Param;

/**
 * 加密密钥及授权码表(AuthCode)数据库访问层
 *
 * @author makejava
 * @since 2023-09-22 11:35:46
 */
public interface AuthCodeMapper extends BaseMapper<AuthCode> {


    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AuthCode getInfoById(@Param("id") Long id);

}
