package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdItemRelatedInformation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 第三方接口关联记录(MdItemRelatedInformation)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:23
 */
public interface MdItemRelatedInformationMapper extends BaseMapper<MdItemRelatedInformation> {

    /**
     * 分页查询[第三方接口关联记录]列表
     *
     * @param page  分页参数
     * @param param MdItemRelatedInformation查询参数
     * @return 分页数据
     */
    IPage<MdItemRelatedInformation> getPage(PageParam<MdItemRelatedInformation> page, @Param("param") MdItemRelatedInformation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdItemRelatedInformation getInfoById(@Param("id") String id);

}
