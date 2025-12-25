package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdWzLatestRummager;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——最近检查人(MdWzLatestRummager)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:24
 */
public interface MdWzLatestRummagerMapper extends BaseMapper<MdWzLatestRummager> {

    /**
     * 分页查询[KS问诊——最近检查人]列表
     *
     * @param page  分页参数
     * @param param MdWzLatestRummager查询参数
     * @return 分页数据
     */
    IPage<MdWzLatestRummager> getPage(PageParam<MdWzLatestRummager> page, @Param("param") MdWzLatestRummager param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWzLatestRummager getInfoById(@Param("id") String id);

}
