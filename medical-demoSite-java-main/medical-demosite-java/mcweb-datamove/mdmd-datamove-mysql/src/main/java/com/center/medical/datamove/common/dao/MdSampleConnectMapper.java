package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdSampleConnect;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS样本交接(MdSampleConnect)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:22
 */
public interface MdSampleConnectMapper extends BaseMapper<MdSampleConnect> {

    /**
     * 分页查询[KS样本交接]列表
     *
     * @param page  分页参数
     * @param param MdSampleConnect查询参数
     * @return 分页数据
     */
    IPage<MdSampleConnect> getPage(PageParam<MdSampleConnect> page, @Param("param") MdSampleConnect param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSampleConnect getInfoById(@Param("id") String id);

}
