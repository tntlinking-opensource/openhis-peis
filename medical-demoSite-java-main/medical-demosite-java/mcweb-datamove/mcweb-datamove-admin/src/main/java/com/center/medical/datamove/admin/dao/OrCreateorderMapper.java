package com.center.medical.datamove.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Createorder;
import org.apache.ibatis.annotations.Param;

/**
 * 订单表(Createorder)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 18:20:57
 */
@DataSource(value = DataSourceType.SLAVE)
public interface OrCreateorderMapper extends BaseMapper<Createorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param Createorder查询参数
     * @return 分页数据
     */
    IPage<Createorder> getPage(PageParam<Createorder> page, @Param("param") Createorder param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createorder getInfoById(@Param("id") String id);

    /**
     * 根据订单号获取记录详情
     *
     * @param ddh
     * @return
     */
    Createorder getInfoByDdh(@Param("ddh") String ddh);
}
