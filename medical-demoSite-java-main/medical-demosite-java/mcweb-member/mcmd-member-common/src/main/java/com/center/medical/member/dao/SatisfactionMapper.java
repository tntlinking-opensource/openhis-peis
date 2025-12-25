package com.center.medical.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.Satisfaction;
import com.center.medical.member.bean.param.SatisfactionParam;
import com.center.medical.member.bean.param.SecondInterviewParam;
import com.center.medical.member.bean.param.TotalCountParam;
import com.center.medical.member.bean.param.WpjDataParam;
import com.center.medical.member.bean.vo.AddSatisficingVo;
import com.center.medical.member.bean.vo.FirstInterviewVo;
import com.center.medical.member.bean.vo.TotalCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * KF满意度(Satisfaction)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:51
 */
public interface SatisfactionMapper extends BaseMapper<Satisfaction> {

    /**
     * 分页查询[KF满意度]列表
     *
     * @param page  分页参数
     * @param satisfactionParam Satisfaction查询参数
     * @return 分页数据
     */
    IPage<Satisfaction> getList(PageParam<Satisfaction> page, @Param("param") SatisfactionParam satisfactionParam);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Satisfaction getInfoById(@Param("id") String id);

    /**
     * 获取对科室评价不是非常满意的数据
     * @param page
     * @param satisfactionParam
     * @return
     */
    IPage<Satisfaction> getListData(PageParam<Satisfaction> page, @Param("param")SatisfactionParam satisfactionParam);

    /**
     * 满意度排名数据
     * @param page
     * @param satisfactionParam
     * @return
     */
    IPage<Satisfaction> getAnalyseData(PageParam<Satisfaction> page,@Param("param") SatisfactionParam satisfactionParam);

    /**
     * 不分页，根据筛选条件查全部
     * @param satisfactionParam
     * @return
     */
    List<Satisfaction> getAllList(@Param("param")SatisfactionParam satisfactionParam);

    /**
     * 分页查询体检满意度所有数据
     * @param page
     * @param satisfactionParam
     * @return
     */
    IPage<AddSatisficingVo> getAddSatisficingPage(PageParam<AddSatisficingVo> page, @Param("param")SatisfactionParam satisfactionParam);

    /**
     * 不分页，根据筛选条件查全部体检满意度
     * @param satisfactionParam
     * @return
     */
    List<AddSatisficingVo> getAllAddList(@Param("param")SatisfactionParam satisfactionParam);

    /**
     * 分页显示未评价的数据
     * @param page
     * @param wpjDataParam
     * @return
     */
    IPage<Peispatient> getWpjData(PageParam<Peispatient> page, @Param("param") WpjDataParam wpjDataParam);

    /**
     * 不分页显示未评价的数据
     * @param wpjDataParam
     * @return
     */
    List<Peispatient> getAllWpjData(@Param("param") WpjDataParam wpjDataParam);

    /**
     * 分页查询体检满意度
     * @param page
     * @param satisfactionParam
     * @return
     */
    IPage<FirstInterviewVo> getFirstInterviewPage(PageParam<FirstInterviewVo> page, @Param("param") SatisfactionParam satisfactionParam);

    /**
     * 获取回访全部为非常满意的数据
     * @param page
     * @param satisfactionParam
     * @return
     */
    IPage<FirstInterviewVo> getFcmyListData(PageParam<FirstInterviewVo> page, @Param("param")SatisfactionParam satisfactionParam);

    /**
     * 不满意客户回访导出excel
     * @param satisfactionParam
     * @return
     */
    List<FirstInterviewVo> exportFirstInterview(@Param("param")SatisfactionParam satisfactionParam);

    /**
     * 满意度统计分页查询
     * @param page
     * @param totalCountParam
     * @return
     */
    IPage<TotalCountVo> getTotalCountPage(PageParam<TotalCountVo> page, @Param("param") TotalCountParam totalCountParam);

    /**
     * 不满意客户深访分页查询所有数据
     * @param page
     * @param secondInterviewParam
     * @return
     */
    IPage<Satisfaction> getSecondInterviewPage(PageParam<Satisfaction> page,@Param("param") SecondInterviewParam secondInterviewParam);

    /**
     * 再次不满意数据导出excel
     * @param secondInterviewParam
     * @return
     */
    List<Satisfaction> exportSecondInterview(@Param("param")SecondInterviewParam secondInterviewParam);

    /**
     * 获取科室回访全部为非常满意的数据
     * @param page
     * @param secondInterviewParam
     * @return
     */
    IPage<Satisfaction> getFcmyListSecondData(PageParam<Satisfaction> page, @Param("param")SecondInterviewParam secondInterviewParam);

    /**
     * 导出科室回访全部为非常满意数据
     * @param secondInterviewParam
     * @return
     */
    List<Satisfaction> exportsSecondInterview(@Param("param") SecondInterviewParam secondInterviewParam);
}
