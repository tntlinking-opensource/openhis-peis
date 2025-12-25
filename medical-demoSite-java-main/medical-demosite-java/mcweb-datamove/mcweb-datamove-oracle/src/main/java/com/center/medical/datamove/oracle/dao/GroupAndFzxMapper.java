package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.GroupAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (GroupAndFzx)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:03
 */
public interface GroupAndFzxMapper extends BaseMapper<GroupAndFzx> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param GroupAndFzx查询参数
     * @return 分页数据
     */
    IPage<GroupAndFzx> getPage(PageParam<GroupAndFzx> page, @Param("param") GroupAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    GroupAndFzx getInfoById(@Param("id") String id);

}
