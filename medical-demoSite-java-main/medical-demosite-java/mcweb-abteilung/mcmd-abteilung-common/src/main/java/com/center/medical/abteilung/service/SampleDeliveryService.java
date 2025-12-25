package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.model.SampleDelivery;
import com.center.medical.abteilung.bean.param.SDSaOrUParam;
import com.center.medical.abteilung.bean.param.SampleDeliveryParam;
import com.center.medical.abteilung.bean.vo.SampleDeVo;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * KS样本录入(SampleDelivery)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:06
 */
public interface SampleDeliveryService extends IService<SampleDelivery> {

    /**
     * 分页查询[KS样本录入]列表
     *
     * @param page  分页参数
     * @param param SampleDelivery查询参数
     * @return 分页数据
     */
    IPage<SampleDeVo> getList(PageParam<SampleDelivery> page, SampleDeliveryParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    SampleDelivery getInfoById(String id);

    /**
     * 录入保存
     *
     * @param param
     * @return
     */
    Boolean saOrUp(SDSaOrUParam param);

    /**
     * 查询导出数据
     *
     * @param param
     * @return
     */
    List<SampleDeVo> export(SampleDeliveryParam param);
}

