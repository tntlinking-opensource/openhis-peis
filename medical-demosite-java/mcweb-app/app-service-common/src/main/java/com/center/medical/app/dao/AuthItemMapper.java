package com.center.medical.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.app.bean.model.AuthItem;
import org.apache.ibatis.annotations.Param;

/**
 * 外部接口授权记录(AuthItem)数据库访问层
 *
 * @author makejava
 * @since 2023-09-22 11:35:46
 */
public interface AuthItemMapper extends BaseMapper<AuthItem> {


    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AuthItem getInfoById(@Param("id") Integer id);

}
