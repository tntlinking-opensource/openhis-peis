package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdWsjg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 外送机构(MdWsjg)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:21
 */
public interface MdWsjgMapper extends BaseMapper<MdWsjg> {

    /**
     * 分页查询[外送机构]列表
     *
     * @param page  分页参数
     * @param param MdWsjg查询参数
     * @return 分页数据
     */
    IPage<MdWsjg> getPage(PageParam<MdWsjg> page, @Param("param") MdWsjg param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWsjg getInfoById(@Param("id") String id);

}
