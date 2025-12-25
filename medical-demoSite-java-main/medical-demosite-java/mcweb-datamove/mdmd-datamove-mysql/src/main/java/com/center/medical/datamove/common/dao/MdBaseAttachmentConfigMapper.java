package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdBaseAttachmentConfig;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 基础附件配置(MdBaseAttachmentConfig)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:09
 */
public interface MdBaseAttachmentConfigMapper extends BaseMapper<MdBaseAttachmentConfig> {

    /**
     * 分页查询[基础附件配置]列表
     *
     * @param page  分页参数
     * @param param MdBaseAttachmentConfig查询参数
     * @return 分页数据
     */
    IPage<MdBaseAttachmentConfig> getPage(PageParam<MdBaseAttachmentConfig> page, @Param("param") MdBaseAttachmentConfig param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBaseAttachmentConfig getInfoById(@Param("id") String id);

}
