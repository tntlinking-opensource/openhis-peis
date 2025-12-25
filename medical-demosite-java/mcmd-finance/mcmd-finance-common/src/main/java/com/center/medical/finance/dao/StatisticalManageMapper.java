package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Createorder;
import org.apache.ibatis.annotations.Param;

/**
 * 财务报表-统计报表(Createorder)表数据库访问层
 *
 * @author ay
 * @since 2023-05-15 09:37:34
 */
public interface StatisticalManageMapper extends BaseMapper<Createorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param Createorder查询参数
     * @return 分页数据
     */
    IPage<Createorder> getList(PageParam<Createorder> page, @Param("param") Createorder param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createorder getInfoById(@Param("id") String id);

}
