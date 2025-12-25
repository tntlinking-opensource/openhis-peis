package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.AttachmentConfig;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (AttachmentConfig)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:22
 */
public interface AttachmentConfigMapper extends BaseMapper<AttachmentConfig> {

    /**
     * 分页查询[]列表
     *
     * @param page 分页参数
     * @return 分页数据
     */
    IPage<AttachmentConfig> getPage(PageParam<AttachmentConfig> page);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    AttachmentConfig getInfoById(@Param("id") String id);

}
