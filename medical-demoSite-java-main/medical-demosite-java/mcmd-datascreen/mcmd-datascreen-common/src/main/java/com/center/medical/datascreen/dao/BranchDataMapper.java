package com.center.medical.datascreen.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Branch;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datascreen.bean.param.BranchDataSelectAddItemPageMapperParam;
import com.center.medical.datascreen.bean.param.DatascreenBaseTimeAndBranchParam;
import com.center.medical.datascreen.bean.vo.BranchDataSelectAddItemPageVo;
import com.center.medical.datascreen.bean.vo.BranchDataSelectDeptListVo;
import com.center.medical.datascreen.bean.vo.BranchDataSelectDeptVo;
import com.center.medical.datascreen.bean.vo.DatascreenBaseListVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xhp
 * @since 2023-06-06 9:07
 */
@Repository
public interface BranchDataMapper extends BaseMapper<Branch> {
    /**
     * 查询分中心功能科室数量
     * @param branchId
     * @return
     * deprecated by: 应该查所选时间段内有人来检的科室数量
     */
    @Deprecated
    int selectDeptCount(@Param("branchId")String branchId);

    /**
     * 查询分中心开检的功能科室数量
     * @param param
     * @return
     */
    BranchDataSelectDeptVo selectOpenedDeptCount(@Param("param")DatascreenBaseTimeAndBranchParam param);

    /**
     * 项目推广统计
     * @param param
     * @return
     */
    List<DatascreenBaseListVo> selectItemList(@Param("param") DatascreenBaseTimeAndBranchParam param);

    /**
     * 查询预约人数
     * @param param
     * @param fUsecodehiden
     * @param levelId
     * @return
     */
    int selectReservationCount(@Param("param") DatascreenBaseTimeAndBranchParam param,@Param("fUsecodehiden") Integer fUsecodehiden,@Param("levelId") Integer levelId);

    /**
     * 按职业体检处理意见查询人数
     * @param param
     * @param serialNo
     * @return
     */
    int selectCountByHandlingOpinion(@Param("param") DatascreenBaseTimeAndBranchParam param,@Param("serialNo") int serialNo);

    /**
     * 查询高危人数
     * @param param
     * @return
     */
    int selectHighRiskCount(@Param("param") DatascreenBaseTimeAndBranchParam param);

    /**
     * 查询危急值回访率
     * @param param
     * @return
     */
    Double selectCriticalValueRate(@Param("param") DatascreenBaseTimeAndBranchParam param);

    /**
     * 查询回访率
     * @param param
     * @return
     */
    Double selectFollowUpRate(@Param("param") DatascreenBaseTimeAndBranchParam param,@Param("fUsecodehiden") Integer fUsecodehiden);

    /**
     * 查询现场满意度
     * @param param
     * @return
     */
    Double selectSatisfactionRate(@Param("param") DatascreenBaseTimeAndBranchParam param);

    /**
     * 查询加项人数
     * @param param
     * @return
     */
    int selectAddItemNumber(@Param("param") DatascreenBaseTimeAndBranchParam param);

    /**
     * 查询加项费用
     * @param param
     * @return
     */
    double selectAddItemAmount(@Param("param") DatascreenBaseTimeAndBranchParam param);

    /**
     * 加项数据统计列表
     * @param page
     * @param param
     * @return
     */
    IPage<BranchDataSelectAddItemPageVo> selectAddItemPage(PageParam page,@Param("param") BranchDataSelectAddItemPageMapperParam param);

    /**
     * 科室工作量列表
     * @param param
     * @return
     */
    List<BranchDataSelectDeptListVo> selectDeptList(@Param("param") DatascreenBaseTimeAndBranchParam param);
}
