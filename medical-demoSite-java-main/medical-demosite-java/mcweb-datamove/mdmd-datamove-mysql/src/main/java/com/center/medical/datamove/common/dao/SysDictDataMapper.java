package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.SysDictData;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 字典数据表(SysDictData)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:34
 */
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    /**
     * 分页查询[字典数据表]列表
     *
     * @param page  分页参数
     * @param param SysDictData查询参数
     * @return 分页数据
     */
    IPage<SysDictData> getPage(PageParam<SysDictData> page, @Param("param") SysDictData param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键dictCode
     * @return 详情信息
     */
    SysDictData getInfoById(@Param("id") Long id);

}
