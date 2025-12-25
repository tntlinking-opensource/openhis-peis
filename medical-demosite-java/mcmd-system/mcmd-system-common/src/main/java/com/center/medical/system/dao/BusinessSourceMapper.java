package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.BusinessSource;
import com.center.medical.system.bean.param.BusinessSourceParam;
import org.apache.ibatis.annotations.Param;

/**
 * 合作第三方(BusinessSource)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-03-24 10:10:24
 */
public interface BusinessSourceMapper extends BaseMapper<BusinessSource> {

    /**
     * 分页查询[合作第三方]列表
     *
     * @param page  分页参数
     * @param param BusinessSource查询参数
     * @return 分页数据
     */
    IPage<BusinessSource> getPage(PageParam<BusinessSource> page, @Param("param") BusinessSourceParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BusinessSource getInfoById(@Param("id") Long id);

}
