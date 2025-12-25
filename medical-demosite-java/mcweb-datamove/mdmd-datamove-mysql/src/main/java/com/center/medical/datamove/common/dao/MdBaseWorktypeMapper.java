package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdBaseWorktype;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 工种(MdBaseWorktype)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:10
 */
public interface MdBaseWorktypeMapper extends BaseMapper<MdBaseWorktype> {

    /**
     * 分页查询[工种]列表
     *
     * @param page  分页参数
     * @param param MdBaseWorktype查询参数
     * @return 分页数据
     */
    IPage<MdBaseWorktype> getPage(PageParam<MdBaseWorktype> page, @Param("param") MdBaseWorktype param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBaseWorktype getInfoById(@Param("id") String id);

}
