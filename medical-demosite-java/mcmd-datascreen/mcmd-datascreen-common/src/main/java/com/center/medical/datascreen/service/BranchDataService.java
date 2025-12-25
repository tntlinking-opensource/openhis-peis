package com.center.medical.datascreen.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datascreen.bean.param.BranchDataSelectAddItemPageParam;
import com.center.medical.datascreen.bean.param.BranchDataSelectAddItemParam;
import com.center.medical.datascreen.bean.param.DatascreenBaseTimeAndBranchParam;
import com.center.medical.datascreen.bean.vo.*;
import com.center.medical.datascreen.bean.vo.*;

import java.util.List;

/**
 * @author xhp
 * @since 2023-06-06 9:07
 */
public interface BranchDataService {
    /**
     * 中心分区
     * @return
     */
    List<BranchDataBranchVo> selectBranchList();

    /**
     * 个检体检概况
     * @param page
     * @param param
     * @return
     */
    IPage<BranchDataPersonPatientPageVo> selectPersonPatientPage(PageParam page, DatascreenBaseTimeAndBranchParam param);

    /**
     * 总人数 总共费用等信息
     * @param param
     * @return
     */
    BranchDataTotalNumberVo selectTotalNumber(DatascreenBaseTimeAndBranchParam param, Integer fUsecodehiden);

    /**
     * 科室工作量
     * @param param
     * @return
     */
    BranchDataSelectDeptVo selectDept(DatascreenBaseTimeAndBranchParam param);

    /**
     * 项目推广统计
     * @param param
     * @return
     */
    List<DatascreenBaseListVo> selectItemList(DatascreenBaseTimeAndBranchParam param);

    /**
     *销售部预约
     * @param param
     * @return
     */
    BranchDataSelectReservationVo selectReservation(DatascreenBaseTimeAndBranchParam param);

    /**
     * 危急值
     * @param param
     * @return
     */
    BranchDataSelectCriticalValueVo selectCriticalValue(DatascreenBaseTimeAndBranchParam param);

    /**
     * 回访率
     * @param param
     * @return
     */
    BranchDataSelectFollowUpVo selectFollowUp(DatascreenBaseTimeAndBranchParam param);

    /**
     * 加项数据统计
     * @param param
     * @return
     */
    BranchDataSelectAddItemVo selectAddItem(BranchDataSelectAddItemParam param);

    /**
     * 加项数据统计列表
     * @param page
     * @param param
     * @return
     */
    IPage<BranchDataSelectAddItemPageVo> selectAddItemPage(PageParam page, BranchDataSelectAddItemPageParam param);

    /**
     * 科室工作量列表
     * @param param
     * @return
     */
    List<BranchDataSelectDeptListVo> selectDeptList(DatascreenBaseTimeAndBranchParam param);
}
