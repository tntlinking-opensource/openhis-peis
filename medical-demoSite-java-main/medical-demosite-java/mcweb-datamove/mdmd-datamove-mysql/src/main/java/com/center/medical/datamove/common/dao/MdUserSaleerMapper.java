package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdUserSaleer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 用户-销售关联表 (MdUserSaleer)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:24
 */
public interface MdUserSaleerMapper extends BaseMapper<MdUserSaleer> {

    /**
     * 分页查询[用户-销售关联表 ]列表
     *
     * @param page  分页参数
     * @param param MdUserSaleer查询参数
     * @return 分页数据
     */
    IPage<MdUserSaleer> getPage(PageParam<MdUserSaleer> page, @Param("param") MdUserSaleer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdUserSaleer getInfoById(@Param("id") String id);

}
