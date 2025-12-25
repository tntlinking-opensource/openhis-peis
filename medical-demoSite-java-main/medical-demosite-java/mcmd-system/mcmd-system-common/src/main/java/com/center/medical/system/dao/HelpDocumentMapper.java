package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.HelpDocument;
import org.apache.ibatis.annotations.Param;

/**
 * 帮助文档(HelpDocument)数据库访问层
 *
 * @author ay
 * @since 2024-04-24 13:56:58
 */
public interface HelpDocumentMapper extends BaseMapper<HelpDocument> {

    /**
     * 分页查询[帮助文档]列表
     *
     * @param page  分页参数
     * @param param HelpDocument查询参数
     * @return 分页数据
     */
    IPage<HelpDocument> getPage(PageParam<HelpDocument> page, @Param("param") HelpDocument param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    HelpDocument getInfoById(@Param("id") String id);

}
