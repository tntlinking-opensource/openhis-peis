package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdCreateorder;
import org.apache.ibatis.annotations.Param;

/**
 * 订单表(MdCreateorder)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 18:18:42
 */
@DataSource(value = DataSourceType.MASTER)
public interface MdCreateorderMapper extends BaseMapper<MdCreateorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param MdCreateorder查询参数
     * @return 分页数据
     */
    IPage<MdCreateorder> getPage(PageParam<MdCreateorder> page, @Param("param") MdCreateorder param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCreateorder getInfoById(@Param("id") String id);

}
