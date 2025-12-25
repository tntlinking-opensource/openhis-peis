package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdUserMapping;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检用户与其他系统用户映射表(MdUserMapping)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:21
 */
public interface MdUserMappingMapper extends BaseMapper<MdUserMapping> {

    /**
     * 分页查询[体检用户与其他系统用户映射表]列表
     *
     * @param page  分页参数
     * @param param MdUserMapping查询参数
     * @return 分页数据
     */
    IPage<MdUserMapping> getPage(PageParam<MdUserMapping> page, @Param("param") MdUserMapping param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdUserMapping getInfoById(@Param("id") Long id);

}
