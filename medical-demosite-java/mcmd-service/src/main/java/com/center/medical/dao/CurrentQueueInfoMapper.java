package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.CurrentQueueInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 当前排队绑定信息(CurrentQueueInfo)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:02
 */
public interface CurrentQueueInfoMapper extends BaseMapper<CurrentQueueInfo> {

    /**
     * 分页查询[当前排队绑定信息]列表
     *
     * @param page  分页参数
     * @param param CurrentQueueInfo查询参数
     * @return 分页数据
     */
    IPage<CurrentQueueInfo> getList(PageParam<CurrentQueueInfo> page, @Param("param") CurrentQueueInfo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    CurrentQueueInfo getInfoById(@Param("id") String id);

}
