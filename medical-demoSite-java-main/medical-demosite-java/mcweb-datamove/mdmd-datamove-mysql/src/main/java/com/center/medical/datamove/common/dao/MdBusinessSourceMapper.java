package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdBusinessSource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 合作第三方(MdBusinessSource)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:13
 */
public interface MdBusinessSourceMapper extends BaseMapper<MdBusinessSource> {

    /**
     * 分页查询[合作第三方]列表
     *
     * @param page  分页参数
     * @param param MdBusinessSource查询参数
     * @return 分页数据
     */
    IPage<MdBusinessSource> getPage(PageParam<MdBusinessSource> page, @Param("param") MdBusinessSource param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBusinessSource getInfoById(@Param("id") Object id);

}
