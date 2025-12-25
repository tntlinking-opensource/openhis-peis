package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.ZyFinalSummary;
import com.center.medical.report.bean.param.ZySaveParam;
import com.center.medical.report.bean.param.ZyVsSummaryListParam;
import com.center.medical.report.bean.vo.ZyChooseVo;
import com.center.medical.report.bean.vo.ZyGridDataVo;
import com.center.medical.report.bean.vo.ZyVsSummaryListVo;

import java.util.List;


/**
 * 职业最终结论(ZyFinalSummary)服务接口
 *
 * @author makejava
 * @since 2024-12-18 09:28:44
 */
public interface ZyFinalSummaryService extends IService<ZyFinalSummary> {

    /**
     * 分页查询[职业最终结论]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ZyFinalSummary> getPage(PageParam<ZyFinalSummary> page, ZyFinalSummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ZyFinalSummary getInfoById(String id);

    /**
     * 获取职业最终结论
     * @param patientcode
     * @return
     */
    List<ZyGridDataVo> getGridData(String patientcode);

    /**
     * 选择危害因素
     * @param patientcode
     * @return
     */
    List<ZyChooseVo> getPendingHarmsList(String patientcode);

    /**
     * 获取职业最终结论
     * @param param
     * @return
     */
    List<ZyVsSummaryListVo> getZyVsSummaryList(ZyVsSummaryListParam param);

    /**
     * 添加
     * @param param
     * @return
     */
    String saOrUp(ZySaveParam param);

    /**
     * 删除
     * @param param
     * @return
     */
    Boolean delete(List<String> ids);
}

