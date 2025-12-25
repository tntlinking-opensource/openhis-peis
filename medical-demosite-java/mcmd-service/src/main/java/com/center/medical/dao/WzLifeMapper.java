package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.WzLife;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——个人生活史(WzLife)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:29
 */
public interface WzLifeMapper extends BaseMapper<WzLife> {

    /**
     * 分页查询[KS问诊——个人生活史]列表
     *
     * @param page  分页参数
     * @param param WzLife查询参数
     * @return 分页数据
     */
    IPage<WzLife> getList(PageParam<WzLife> page, @Param("param") WzLife param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzLife getInfoById(@Param("id") String id);

}
