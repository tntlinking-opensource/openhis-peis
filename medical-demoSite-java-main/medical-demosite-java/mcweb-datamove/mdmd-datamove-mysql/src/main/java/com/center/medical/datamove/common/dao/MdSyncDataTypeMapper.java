package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdSyncDataType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 同步数据类型(MdSyncDataType)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:52
 */
public interface MdSyncDataTypeMapper extends BaseMapper<MdSyncDataType> {

    /**
     * 分页查询[同步数据类型]列表
     *
     * @param page  分页参数
     * @param param MdSyncDataType查询参数
     * @return 分页数据
     */
    IPage<MdSyncDataType> getPage(PageParam<MdSyncDataType> page, @Param("param") MdSyncDataType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键typeId
     * @return 详情信息
     */
    MdSyncDataType getInfoById(@Param("id") Integer id);

}
