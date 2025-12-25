package com.center.medical.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
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

import java.util.List;

/**
 * KF满意度(Satisfaction)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:51
 */
public interface SatisfactionService extends IService<Satisfaction> {

    /**
     * 分页查询[KF满意度]列表
     *
     * @param page  分页参数
     * @param satisfactionParam Satisfaction查询参数
     * @return 分页数据
     */
    IPage<Satisfaction> getPage(PageParam<Satisfaction> page, SatisfactionParam satisfactionParam);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Satisfaction getInfoById(String id);

    /**
     *获取对科室评价不是非常满意的数据
     * @param page
     * @param satisfactionParam
     * @return
     */
    IPage<Satisfaction> getListData(PageParam<Satisfaction> page, SatisfactionParam satisfactionParam);

    /**
     * 新增或更新
     * @param satisfaction
     * @return
     */
    Boolean saOrUp(Satisfaction satisfaction);

    /**
     * 取消不满意
     * @param ids
     * @return
     */
    Boolean cancelDissat(List<String> ids);

    /**
     * 满意度排名数据
     * @param page
     * @param satisfactionParam
     * @return
     */
    IPage<Satisfaction> getAnalyseData(PageParam<Satisfaction> page, SatisfactionParam satisfactionParam);

    /**
     * 新增或更新Qt
     * @param satisfaction
     * @return
     */
    Boolean saveOrUpdateQt(Satisfaction satisfaction);

    /**
     * 不分页，根据筛选条件查全部
     * @param satisfactionParam
     * @return
     */
    List<Satisfaction> getAllList(SatisfactionParam satisfactionParam);

    /**
     * 分页查询体检满意度所有数据
     * @param page
     * @param satisfactionParam
     * @return
     */
    IPage<AddSatisficingVo> getAddSatisficingPage(PageParam<AddSatisficingVo> page, SatisfactionParam satisfactionParam);

    /**
     *
     * @param satisfaction
     * @return
     */
    Boolean saOrUpAdd(Satisfaction satisfaction);

    /**
     * 不分页，根据筛选条件查全部体检满意度
     * @param satisfactionParam
     * @return
     */
    List<AddSatisficingVo> getAllAddList(SatisfactionParam satisfactionParam);

    /**
     * 分页显示未评价的数据
     * @param page
     * @param wpjDataParam
     * @return
     */
    IPage<Peispatient> getWpjData(PageParam<Peispatient> page, WpjDataParam wpjDataParam);

    /**
     * 不分页显示未评价的数据
     * @param wpjDataParam
     * @return
     */
    List<Peispatient> getAllWpjData(WpjDataParam wpjDataParam);

    /**
     *  分页查询体检满意度
     * @param page
     * @param satisfactionParam
     * @return
     */
    IPage<FirstInterviewVo> getFirstInterviewPage(PageParam<FirstInterviewVo> page, SatisfactionParam satisfactionParam);

    /**
     * 获取回访全部为非常满意的数据
     * @param page
     * @param satisfactionParam
     * @return
     */
    IPage<FirstInterviewVo> getFcmyListData(PageParam<FirstInterviewVo> page, SatisfactionParam satisfactionParam);

    /**
     * 新增或修改满意度回访数据
     * @param satisfaction
     * @return
     */
    Boolean saOrUpFirstInterview(Satisfaction satisfaction);


    /**
     * 不满意客户回访导出excel
     * @param satisfactionParam
     * @return
     */
    List<FirstInterviewVo> exportFirstInterview(SatisfactionParam satisfactionParam);

    /**
     * 导出回访全部为非常满意的数据
     * @param satisfactionParam
     * @return
     */
    List<Satisfaction> exportsFirstInterview(SatisfactionParam satisfactionParam);

    /**
     * 满意度统计分页查询
     * @param page
     * @param totalCountParam
     * @return
     */
    IPage<TotalCountVo> getTotalCountPage(PageParam<TotalCountVo> page, TotalCountParam totalCountParam);

    /**
     * 不满意客户深访分页查询所有数据
     * @param page
     * @param secondInterviewParam
     * @return
     */
    IPage<Satisfaction> getSecondInterviewPage(PageParam<Satisfaction> page, SecondInterviewParam secondInterviewParam);

    /**
     * 更新不满意客户深访数据
     * @param satisfaction
     * @return
     */
    Boolean saOrUpSecondInterview(Satisfaction satisfaction);

    /**
     * 再次不满意数据导出excel
     * @param secondInterviewParam
     * @return
     */
    List<Satisfaction> exportSecondInterview(SecondInterviewParam secondInterviewParam);

    /**
     * 获取科室回访全部为非常满意的数据
     * @param page
     * @param secondInterviewParam
     * @return
     */
    IPage<Satisfaction> getFcmyListSecondData(PageParam<Satisfaction> page, SecondInterviewParam secondInterviewParam);

    /**
     * 导出科室回访全部为非常满意数据
     * @param secondInterviewParam
     * @return
     */
    List<Satisfaction> exportsSecondInterview(SecondInterviewParam secondInterviewParam);
}

