package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdWzTjrecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——体检记录(MdWzTjrecord)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:25
 */
public interface MdWzTjrecordMapper extends BaseMapper<MdWzTjrecord> {

    /**
     * 分页查询[KS问诊——体检记录]列表
     *
     * @param page  分页参数
     * @param param MdWzTjrecord查询参数
     * @return 分页数据
     */
    IPage<MdWzTjrecord> getPage(PageParam<MdWzTjrecord> page, @Param("param") MdWzTjrecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWzTjrecord getInfoById(@Param("id") String id);

}
