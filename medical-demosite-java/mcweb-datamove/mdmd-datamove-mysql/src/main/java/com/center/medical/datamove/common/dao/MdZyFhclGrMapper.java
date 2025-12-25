package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdZyFhclGr;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC个人防护用品(MdZyFhclGr)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:27
 */
public interface MdZyFhclGrMapper extends BaseMapper<MdZyFhclGr> {

    /**
     * 分页查询[JC个人防护用品]列表
     *
     * @param page  分页参数
     * @param param MdZyFhclGr查询参数
     * @return 分页数据
     */
    IPage<MdZyFhclGr> getPage(PageParam<MdZyFhclGr> page, @Param("param") MdZyFhclGr param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdZyFhclGr getInfoById(@Param("id") String id);

}
