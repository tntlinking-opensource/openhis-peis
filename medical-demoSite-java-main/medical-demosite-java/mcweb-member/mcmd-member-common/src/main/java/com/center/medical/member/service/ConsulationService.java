package com.center.medical.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.Consulation;
import com.center.medical.member.bean.param.ConSaOrUpParam;
import com.center.medical.member.bean.param.ConStatisticsParam;
import com.center.medical.member.bean.param.ConsulationParam;
import com.center.medical.member.bean.param.DCSaOrUpParam;
import com.center.medical.member.bean.vo.StatisticsListVo;

/**
 * 科室/总检咨询(Consulation)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:32
 */
public interface ConsulationService extends IService<Consulation> {

    /**
     * 分页查询[科室/总检咨询]列表
     *
     * @param page  分页参数
     * @param param Consulation查询参数
     * @return 分页数据
     */
    IPage<Consulation> getList(PageParam<Consulation> page, ConsulationParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Consulation getInfoById(String id);

    /**
     * 保存
     * @param param
     * @return
     */
    Boolean saOrUp(ConSaOrUpParam param);

    /**
     * 咨询与随访统计分页查询
     * @param page
     * @param param
     * @return
     */
    IPage<StatisticsListVo> getStatisticsListData(PageParam<StatisticsListVo> page, ConStatisticsParam param);

    /**
     * 科室咨询添加或修改
     * @param param
     * @return
     */
    Boolean DCSaOrUp(DCSaOrUpParam param);
}

