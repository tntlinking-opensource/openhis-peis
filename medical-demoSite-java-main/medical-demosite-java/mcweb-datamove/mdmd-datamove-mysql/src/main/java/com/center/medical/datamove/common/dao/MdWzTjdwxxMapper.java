package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdWzTjdwxx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——体检单位信息(MdWzTjdwxx)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:25
 */
public interface MdWzTjdwxxMapper extends BaseMapper<MdWzTjdwxx> {

    /**
     * 分页查询[KS问诊——体检单位信息]列表
     *
     * @param page  分页参数
     * @param param MdWzTjdwxx查询参数
     * @return 分页数据
     */
    IPage<MdWzTjdwxx> getPage(PageParam<MdWzTjdwxx> page, @Param("param") MdWzTjdwxx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWzTjdwxx getInfoById(@Param("id") String id);

}
