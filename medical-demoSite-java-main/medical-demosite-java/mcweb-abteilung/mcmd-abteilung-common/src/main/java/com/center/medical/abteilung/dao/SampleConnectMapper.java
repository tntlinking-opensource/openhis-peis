package com.center.medical.abteilung.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.abteilung.bean.model.SampleConnect;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.vo.SampleConnectVo;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS样本交接(SampleConnect)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:33
 */
public interface SampleConnectMapper extends BaseMapper<SampleConnect> {

    /**
     * 分页查询[KS样本交接]列表
     *
     * @param page  分页参数
     * @param param SampleConnect查询参数
     * @return 分页数据
     */
    IPage<SampleConnectVo> getList(PageParam<SampleConnectVo> page, @Param("param") BaseParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    SampleConnect getInfoById(@Param("id") String id);

}
