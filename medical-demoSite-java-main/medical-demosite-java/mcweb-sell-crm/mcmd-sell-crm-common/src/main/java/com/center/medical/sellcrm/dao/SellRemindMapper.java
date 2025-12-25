package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.SellRemind;
import com.center.medical.sellcrm.bean.vo.SellRemindVo;
import org.apache.ibatis.annotations.Param;

/**
 * 销售提醒(SellRemind)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:26
 */
public interface SellRemindMapper extends BaseMapper<SellRemind> {

    /**
     * 分页查询[销售提醒]列表
     *
     * @param page     分页参数
     * @param username SellRemind查询参数
     * @return 分页数据
     */
    IPage<SellRemindVo> getPage(PageParam<SellRemindVo> page, @Param("username") String username);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SellRemind getInfoById(@Param("id") String id);

}
