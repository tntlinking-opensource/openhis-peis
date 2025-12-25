package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.QxBkDictionary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (QxBkDictionary)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:42
 */
public interface QxBkDictionaryMapper extends BaseMapper<QxBkDictionary> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxBkDictionary查询参数
     * @return 分页数据
     */
    IPage<QxBkDictionary> getPage(PageParam<QxBkDictionary> page, @Param("param") QxBkDictionary param);


}
