package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdSyncData;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 同步数据操作(MdSyncData)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:49
 */
public interface MdSyncDataMapper extends BaseMapper<MdSyncData> {

    /**
     * 分页查询[同步数据操作]列表
     *
     * @param page  分页参数
     * @param param MdSyncData查询参数
     * @return 分页数据
     */
    IPage<MdSyncData> getPage(PageParam<MdSyncData> page, @Param("param") MdSyncData param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSyncData getInfoById(@Param("id") Long id);

}
