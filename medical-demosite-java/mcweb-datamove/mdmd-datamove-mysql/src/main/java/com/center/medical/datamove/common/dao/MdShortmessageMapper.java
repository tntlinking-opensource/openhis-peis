package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdShortmessage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC短信信息表(MdShortmessage)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:42
 */
public interface MdShortmessageMapper extends BaseMapper<MdShortmessage> {

    /**
     * 分页查询[JC短信信息表]列表
     *
     * @param page  分页参数
     * @param param MdShortmessage查询参数
     * @return 分页数据
     */
    IPage<MdShortmessage> getPage(PageParam<MdShortmessage> page, @Param("param") MdShortmessage param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdShortmessage getInfoById(@Param("id") String id);

}
