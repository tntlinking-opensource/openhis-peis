package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Leadertarget;
import com.center.medical.sellcrm.bean.param.ImportTargetParam;
import com.center.medical.sellcrm.bean.param.LTSaOrUpParam;
import com.center.medical.sellcrm.bean.param.LeadertargetParam;
import com.center.medical.sellcrm.bean.vo.GetXsAndDataVo;
import com.center.medical.sellcrm.bean.vo.LTSummaryVo;

import java.util.List;

/**
 * 领导目标表(Leadertarget)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:36
 */
public interface LeadertargetService extends IService<Leadertarget> {

    /**
     * 分页查询[领导目标表]列表
     *
     * @param page  分页参数
     * @param leadertargetParam Leadertarget查询参数
     * @return 分页数据
     */
    IPage<Leadertarget> getList(PageParam<Leadertarget> page, LeadertargetParam leadertargetParam);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Leadertarget getInfoById(String id);

    /**
     * 返回年份
     * @return
     */
    List getAllYear();

    /**
     * 获取总结数据
     * @param leadertargetParam
     * @return
     */
    List<LTSummaryVo> getSummaryData(LeadertargetParam leadertargetParam);

    /**
     * 新增或修改数据
     * @param param
     * @return
     */
    Boolean saOrUp(LTSaOrUpParam param);

    /**
     * 判断是否已经制定了目标
     * @param lyear
     * @param luserId
     * @return
     */
    Boolean isLeaderYearTarget(String lyear, String luserId);

    /**
     * 根据年份获取相应销售关联的数据
     * @param leaderUserId
     * @param leaderYear
     * @return
     */
    GetXsAndDataVo getXsAndData(List<String> leaderUserId, String leaderYear);

    /**
     * 查询导出的销售年度目标数据
     * @param leadertargetParam
     * @return
     */
    List<Leadertarget> getExportData(LeadertargetParam leadertargetParam);

    /**
     * 导入销售年度目标
     * @param param
     * @return
     */
    Boolean importYearTarget(ImportTargetParam param);
}

