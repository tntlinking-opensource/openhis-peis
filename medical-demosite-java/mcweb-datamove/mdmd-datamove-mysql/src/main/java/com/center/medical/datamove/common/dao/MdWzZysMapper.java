package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdWzZys;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——职业史(MdWzZys)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:26
 */
public interface MdWzZysMapper extends BaseMapper<MdWzZys> {

    /**
     * 分页查询[KS问诊——职业史]列表
     *
     * @param page  分页参数
     * @param param MdWzZys查询参数
     * @return 分页数据
     */
    IPage<MdWzZys> getPage(PageParam<MdWzZys> page, @Param("param") MdWzZys param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWzZys getInfoById(@Param("id") String id);

}
