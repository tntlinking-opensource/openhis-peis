package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.CrmSellRemind;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 销售提醒(CrmSellRemind)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:00
 */
public interface CrmSellRemindMapper extends BaseMapper<CrmSellRemind> {

    /**
     * 分页查询[销售提醒]列表
     *
     * @param page  分页参数
     * @param param CrmSellRemind查询参数
     * @return 分页数据
     */
    IPage<CrmSellRemind> getPage(PageParam<CrmSellRemind> page, @Param("param") CrmSellRemind param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmSellRemind getInfoById(@Param("id") String id);

}
