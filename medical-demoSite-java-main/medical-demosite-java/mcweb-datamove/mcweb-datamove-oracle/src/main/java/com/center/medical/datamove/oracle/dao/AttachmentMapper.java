package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Attachment;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC附件(Attachment)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:13
 */
public interface AttachmentMapper extends BaseMapper<Attachment> {

    /**
     * 分页查询[JC附件]列表
     *
     * @param page  分页参数
     * @param param Attachment查询参数
     * @return 分页数据
     */
    IPage<Attachment> getPage(PageParam<Attachment> page, @Param("param") Attachment param);


}
