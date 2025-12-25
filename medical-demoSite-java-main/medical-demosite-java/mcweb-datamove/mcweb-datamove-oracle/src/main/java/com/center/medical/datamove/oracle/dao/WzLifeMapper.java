package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.WzLife;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——个人生活史(WzLife)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:30:50
 */
public interface WzLifeMapper extends BaseMapper<WzLife> {

    /**
     * 分页查询[KS问诊——个人生活史]列表
     *
     * @param page  分页参数
     * @param param WzLife查询参数
     * @return 分页数据
     */
    IPage<WzLife> getPage(PageParam<WzLife> page, @Param("param") WzLife param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WzLife getInfoById(@Param("id") String id);

}
