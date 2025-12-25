package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdTempFeeitem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 科室临时加项表(MdTempFeeitem)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:55
 */
public interface MdTempFeeitemMapper extends BaseMapper<MdTempFeeitem> {

    /**
     * 分页查询[科室临时加项表]列表
     *
     * @param page  分页参数
     * @param param MdTempFeeitem查询参数
     * @return 分页数据
     */
    IPage<MdTempFeeitem> getPage(PageParam<MdTempFeeitem> page, @Param("param") MdTempFeeitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTempFeeitem getInfoById(@Param("id") String id);

}
