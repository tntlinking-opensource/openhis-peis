/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.app.common.bean.SysConfig;
import org.apache.ibatis.annotations.Param;

/**
 * 系统配置信息
 *
 * @author lgh
 */
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    /**
     * 根据key，查询系统配置信息
     *
     * @param key key
     * @return SysConfig
     */
    SysConfig queryByKey(String key);

    /**
     * 根据key，更新value
     *
     * @param key
     * @param value
     * @return 更新成功条数
     */
    int updateValueByKey(@Param("key") String key, @Param("value") String value);

    /**
     * 批量删除系统配置
     *
     * @param ids 系统配置信息数组
     */
    void deleteBatch(@Param("ids") Long[] ids);

}
