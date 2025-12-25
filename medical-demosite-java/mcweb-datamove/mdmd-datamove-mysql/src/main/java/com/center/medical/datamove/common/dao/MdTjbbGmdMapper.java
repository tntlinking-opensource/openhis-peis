package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdTjbbGmd;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS骨密度体检报表(MdTjbbGmd)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:00
 */
public interface MdTjbbGmdMapper extends BaseMapper<MdTjbbGmd> {

    /**
     * 分页查询[KS骨密度体检报表]列表
     *
     * @param page  分页参数
     * @param param MdTjbbGmd查询参数
     * @return 分页数据
     */
    IPage<MdTjbbGmd> getPage(PageParam<MdTjbbGmd> page, @Param("param") MdTjbbGmd param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTjbbGmd getInfoById(@Param("id") String id);

}
