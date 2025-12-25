package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdWzZysWhys;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊—职业史—危害因素(MdWzZysWhys)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:26
 */
public interface MdWzZysWhysMapper extends BaseMapper<MdWzZysWhys> {

    /**
     * 分页查询[KS问诊—职业史—危害因素]列表
     *
     * @param page  分页参数
     * @param param MdWzZysWhys查询参数
     * @return 分页数据
     */
    IPage<MdWzZysWhys> getPage(PageParam<MdWzZysWhys> page, @Param("param") MdWzZysWhys param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWzZysWhys getInfoById(@Param("id") String id);

}
