package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Shortmessage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC短信信息表(Shortmessage)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:59
 */
public interface ShortmessageMapper extends BaseMapper<Shortmessage> {

    /**
     * 分页查询[JC短信信息表]列表
     *
     * @param page  分页参数
     * @param param Shortmessage查询参数
     * @return 分页数据
     */
    IPage<Shortmessage> getPage(PageParam<Shortmessage> page, @Param("param") Shortmessage param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Shortmessage getInfoById(@Param("id") String id);

}
