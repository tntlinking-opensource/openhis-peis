package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.WzJwb;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——既往病(WzJwb)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:30:49
 */
public interface WzJwbMapper extends BaseMapper<WzJwb> {

    /**
     * 分页查询[KS问诊——既往病]列表
     *
     * @param page  分页参数
     * @param param WzJwb查询参数
     * @return 分页数据
     */
    IPage<WzJwb> getPage(PageParam<WzJwb> page, @Param("param") WzJwb param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WzJwb getInfoById(@Param("id") String id);

}
