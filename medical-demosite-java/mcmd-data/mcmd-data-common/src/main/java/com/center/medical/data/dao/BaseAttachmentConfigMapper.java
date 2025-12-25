package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseAttachmentConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 基础附件配置(BaseAttachmentConfig)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:39
 */
public interface BaseAttachmentConfigMapper extends BaseMapper<BaseAttachmentConfig> {

    /**
     * 分页查询[基础附件配置]列表
     *
     * @param page  分页参数
     * @param param BaseAttachmentConfig查询参数
     * @return 分页数据
     */
    IPage<BaseAttachmentConfig> getList(PageParam<BaseAttachmentConfig> page, @Param("param") BaseAttachmentConfig param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BaseAttachmentConfig getInfoById(@Param("id") String id);

    List<BaseAttachmentConfig> getLatestConfig();
}
