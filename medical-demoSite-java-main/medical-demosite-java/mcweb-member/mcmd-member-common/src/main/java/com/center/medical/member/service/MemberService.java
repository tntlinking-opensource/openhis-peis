package com.center.medical.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.bean.param.ArchiveMergeParam;
import com.center.medical.bean.param.ArchiveParam;
import com.center.medical.bean.param.MeSaOrUpParam;
import com.center.medical.bean.param.MemberParam;
import com.center.medical.bean.vo.ArchiveVo;
import com.center.medical.bean.vo.MemberVo;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.param.AddJfParam;
import com.center.medical.member.bean.param.IFPageParam;
import com.center.medical.member.bean.param.SaveReportParam;
import com.center.medical.member.bean.vo.InterflowVo;
import com.center.medical.member.bean.vo.MemberListVo;
import com.center.medical.member.bean.vo.MerExportVo;

import java.util.List;
import java.util.Map;

/**
 * 体检者档案表(Peispatientarchive)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-24 10:08:13
 */
public interface MemberService extends IService<Peispatientarchive> {

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MemberVo> getPage(PageParam<MemberVo> page, MemberParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatientarchive getInfoById(String id);

    /**
     * 导出会员列表数据
     *
     * @param param
     */
    List<MerExportVo> getList(MemberParam param);

    /**
     * 分页查询档案列表
     *
     * @param page  分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    IPage<ArchiveVo> getArchivePage(PageParam<Peispatientarchive> page, ArchiveParam param);

    /**
     * 合并
     *
     * @param mergeParam
     * @return
     */
    Boolean merge(ArchiveMergeParam mergeParam);

    /**
     * 会员管理中心会员添加
     *
     * @param param
     * @return
     */
    Boolean SaOrUp(MeSaOrUpParam param);

    /**
     * 挂失保存数据
     *
     * @param param
     * @return
     */
    Boolean saveReport(SaveReportParam param);

    /**
     * 会员管理沟通记录分页查询
     *
     * @param page
     * @param param
     * @return
     */
    IPage<InterflowVo> getIFPage(PageParam<InterflowVo> page, IFPageParam param);

    /**
     * 沟通记录导出
     *
     * @param param
     * @return
     */
    List<InterflowVo> getIFExportData(IFPageParam param);

    /**
     * 获取卡数据
     *
     * @param patientcardno
     * @return
     */
    Map<String, Object> getCardData(String patientcardno);


    /**
     * 保存
     *
     * @param param
     * @return
     */
    Boolean saveOl(MeSaOrUpParam param);

    /**
     * 积分充值-搜索会员卡号
     *
     * @param patientcardno
     * @param phone
     * @return
     */
    Map<String, Object> getMemberData(String patientcardno, String phone);

    /**
     * 积分充值
     *
     * @param param
     * @return
     */
    Boolean addJf(AddJfParam param);

    /**
     * 分页查询平台会员列表数据
     *
     * @param page
     * @param param
     * @return
     */
    IPage<MemberListVo> getMemberListData(PageParam<MemberListVo> page, MemberParam param);

    /**
     * 导出会员列表数据
     *
     * @param param
     * @return
     */
    List<MemberListVo> getExportData(MemberParam param);

    /**
     * 中心会员导出数据
     *
     * @param param
     * @return
     */
    List<MerExportVo> getExport(MemberParam param);
}

