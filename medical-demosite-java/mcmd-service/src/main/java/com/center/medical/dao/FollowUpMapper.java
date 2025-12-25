package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.FollowUp;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC随访目的维护(FollowUp)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:25
 */
public interface FollowUpMapper extends BaseMapper<FollowUp> {

    /**
     * 分页查询[JC随访目的维护]列表
     *
     * @param page  分页参数
     * @param param FollowUp查询参数
     * @return 分页数据
     */
    IPage<FollowUp> getList(PageParam<FollowUp> page, @Param("param") FollowUp param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FollowUp getInfoById(@Param("id") String id);

}
