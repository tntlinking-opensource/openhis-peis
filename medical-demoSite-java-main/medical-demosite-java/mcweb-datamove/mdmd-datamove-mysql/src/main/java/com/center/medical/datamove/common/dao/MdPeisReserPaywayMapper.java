package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPeisReserPayway;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者结算方式表(MdPeisReserPayway)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:08
 */
public interface MdPeisReserPaywayMapper extends BaseMapper<MdPeisReserPayway> {

    /**
     * 分页查询[体检者结算方式表]列表
     *
     * @param page  分页参数
     * @param param MdPeisReserPayway查询参数
     * @return 分页数据
     */
    IPage<MdPeisReserPayway> getPage(PageParam<MdPeisReserPayway> page, @Param("param") MdPeisReserPayway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeisReserPayway getInfoById(@Param("id") String id);

}
