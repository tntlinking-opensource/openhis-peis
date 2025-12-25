package com.center.medical.abteilung.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.model.SampleDelivery;
import com.center.medical.abteilung.bean.param.SampleDeliveryParam;
import com.center.medical.abteilung.bean.vo.SampleDeVo;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * KS样本录入(SampleDelivery)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:06
 */
public interface SampleDeliveryMapper extends BaseMapper<SampleDelivery> {

    /**
     * 分页查询[KS样本录入]列表
     *
     * @param page  分页参数
     * @param param SampleDelivery查询参数
     * @return 分页数据
     */
    IPage<SampleDeVo> getList(PageParam<SampleDelivery> page, @Param("param") SampleDeliveryParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    SampleDelivery getInfoById(@Param("id") String id);

    /**
     * 查询导出数据
     * @param param
     * @return
     */
    List<SampleDeVo> export(@Param("param")SampleDeliveryParam param);
}
