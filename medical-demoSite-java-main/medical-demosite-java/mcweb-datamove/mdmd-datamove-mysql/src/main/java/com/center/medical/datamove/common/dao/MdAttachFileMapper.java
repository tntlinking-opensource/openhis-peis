package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdAttachFile;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 上传文件记录表(MdAttachFile)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:06
 */
public interface MdAttachFileMapper extends BaseMapper<MdAttachFile> {

    /**
     * 分页查询[上传文件记录表]列表
     *
     * @param page  分页参数
     * @param param MdAttachFile查询参数
     * @return 分页数据
     */
    IPage<MdAttachFile> getPage(PageParam<MdAttachFile> page, @Param("param") MdAttachFile param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键fileId
     * @return 详情信息
     */
    MdAttachFile getInfoById(@Param("id") String id);

}
