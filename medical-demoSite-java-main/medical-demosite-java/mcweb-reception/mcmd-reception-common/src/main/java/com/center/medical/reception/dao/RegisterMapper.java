package com.center.medical.reception.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.dto.AnswerDto;
import com.center.medical.reception.bean.dto.CheckPaNoDto;
import com.center.medical.reception.bean.param.*;
import com.center.medical.reception.bean.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author: 路飞
 * @date: 2023-03-01 16:47
 * @description: 前台-登记管理DAO接口
 */
public interface RegisterMapper extends BaseMapper<Peispatient> {
    /**
     * 分页查询登记信息列表
     *
     * @param page  分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    IPage<RegisterVo> getPage(PageParam<Peispatient> page, @Param("param") RegisterParam param);

    /**
     * 取得已预约客户
     *
     * @param page
     * @param param
     * @return
     */
    IPage<ReservationUserVo> getReservationUser(PageParam<ReservationUserVo> page, @Param("param") ReservationUserParam param);

    /**
     * 获取档案记录
     *
     * @param page
     * @param param
     * @return
     */
    IPage<RecordListVo> getRecordListData(PageParam<RecordListVo> page, @Param("param") RecordListParam param);

    /**
     * 通过sql查询答案
     *
     * @param itemIds
     * @return
     */
    List<AnswerDto> getAnswer(@Param("itemIds") Set<String> itemIds);

    /**
     * 最近体检人员列表
     *
     * @param page
     * @param param
     * @return
     */
    IPage<PaForReVo> getPatientForRegister(PageParam<PaForReVo> page, @Param("param") PaForReParam param);

    /**
     * 批量登记查询
     *
     * @param patientcode
     * @return
     */
    PatientForOrderIdVo getPatientForOrderIdSql(@Param("patientcode") String patientcode);

    /**
     * 导出excel数据
     *
     * @param param
     * @return
     */
    List<RCExportVo> getExportData(@Param("param") RegisterParam param);

    /**
     * 导出登记信息列表
     *
     * @param param
     * @return
     */
    List<PaForReVo> getPaExportData(@Param("param") PaForReParam param);

    /**
     * 通过身份证号获取体检号
     *
     * @param idCard
     * @return
     */
    List<IdcarPatientVo> getPatientcodeByIdcard(@Param("idCard") String idCard);

    /**
     * 退款管理
     * @param page
     * @param param
     * @return
     */
    IPage<RefundManagementVo> refundManagement(PageParam<RefundManagementVo> page,@Param("param") RefundManagementParam param);

    /**
     * 导出退款管理
     * @param param
     * @return
     */
    List<RefundManagementVo> exportData(@Param("param") RefundManagementParam param);

    /**
     * 获取最近人员列表
     * @param page
     * @param param
     * @return
     */
    IPage<RecentPersonnelListVo> getRecentPersonnelList(PageParam<RecentPersonnelListVo> page,@Param("param") RecentPersonnelListParam param);

    /**
     * 校正档案号
     * @return
     */
    List<CheckPaNoDto> checkPatientarchiveno();
}
