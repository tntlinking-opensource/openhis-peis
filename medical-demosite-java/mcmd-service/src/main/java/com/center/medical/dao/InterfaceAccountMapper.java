package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.InterfaceAccount;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 各种接口加密信息(InterfaceAccount)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:02
 */
public interface InterfaceAccountMapper extends BaseMapper<InterfaceAccount> {

    /**
     * 分页查询[各种接口加密信息]列表
     *
     * @param page  分页参数
     * @param param InterfaceAccount查询参数
     * @return 分页数据
     */
    IPage<InterfaceAccount> getList(PageParam<InterfaceAccount> page, @Param("param") InterfaceAccount param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    InterfaceAccount getInfoById(@Param("id") String id);

}
