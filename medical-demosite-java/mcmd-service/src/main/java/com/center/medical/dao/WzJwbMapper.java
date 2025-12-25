package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.WzJwb;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——既往病(WzJwb)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:52
 */
public interface WzJwbMapper extends BaseMapper<WzJwb> {

    /**
     * 分页查询[KS问诊——既往病]列表
     *
     * @param page  分页参数
     * @param param WzJwb查询参数
     * @return 分页数据
     */
    IPage<WzJwb> getList(PageParam<WzJwb> page, @Param("param") WzJwb param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzJwb getInfoById(@Param("id") String id);

}
