package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.model.SampleConnect;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.param.SampleSaOrUpParam;
import com.center.medical.abteilung.bean.vo.SampleConnectVo;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.page.PageParam;

import java.util.HashMap;

/**
 * KS样本交接(SampleConnect)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:33
 */
public interface SampleConnectService extends IService<SampleConnect> {

    /**
     * 分页查询[KS样本交接]列表
     *
     * @param page  分页参数
     * @param param SampleConnect查询参数
     * @return 分页数据
     */
    IPage<SampleConnectVo> getList(PageParam<SampleConnectVo> page, BaseParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    SampleConnect getInfoById(String id);

    /**
     * 样本交接展现数据
     * @param page
     * @param patientCode
     * @return
     */
    HashMap<String, Object> getItemData(PageParam<SampleConnectVo> page, String patientCode);

    /**
     * 保存
     * @param param
     * @return
     */
    Boolean saOrUp(SampleSaOrUpParam param);
}

