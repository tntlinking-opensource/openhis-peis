package com.center.medical.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.VisitMain;
import com.center.medical.member.bean.param.BelowSampleEditParam;
import com.center.medical.member.bean.param.BelowSampleParam;
import com.center.medical.member.bean.vo.BelowSampleEditVo;
import com.center.medical.member.bean.vo.BelowSampleVo;

import java.util.List;

/**
 * 与迟检、阳性、不合格样本回访表一对多关联，(VisitMain)表服务接口
 *
 * @author makejava
 * @since 2023-02-07 08:31:21
 */
public interface BelowSampleService extends IService<VisitMain> {

    /**
     * 分页查询[与迟检、阳性、不合格样本回访表一对多关联，]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<BelowSampleVo> getList(PageParam<BelowSampleVo> page, BelowSampleParam param);

    /**
     * 导出Excel
     *
     * @param param
     * @return
     */
    List<BelowSampleVo> export(BelowSampleParam param);


    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BelowSampleEditVo getInfoById(String id);

    /**
     * 不合格样品回访
     *
     * @param belowSampleEditParam
     * @return
     */
    Boolean saOrUpBelowSample(BelowSampleEditParam belowSampleEditParam);

}

