package com.center.medical.reception.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.dto.ItemsDto;
import com.center.medical.reception.bean.dto.RefundFeeItemDto;
import com.center.medical.reception.bean.dto.RegisterDto;
import com.center.medical.reception.bean.param.*;
import com.center.medical.reception.bean.vo.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 路飞
 * @date: 2023-03-01 16:37
 * @description: 前台-登记管理业务接口
 */
public interface RegisterService extends IService<Peispatient> {
    /**
     * 分页查询登记信息列表
     *
     * @param page  分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    IPage<RegisterVo> getPage(PageParam<Peispatient> page, RegisterParam param);

    /**
     * 前台-登记管理-新增登记
     *
     * @param intReservation 提交类型：0 预约 1登记 2 保存
     * @return 登记操作结果
     */
    RegisterResultVo saOrUp(RegisterDto registerDto, Integer intReservation);

    /**
     * 添加检查收费项目
     *
     * @param pei            体检者信息
     * @param itemList       收费项目信息
     * @param intReservation 提交类型：0 预约 1登记 2 保存
     * @param registerDto    登记提交内容
     * @return
     */
    R<String> saveOrUpdateItem(Peispatient pei, List<ItemsDto> itemList, Integer intReservation, RegisterDto registerDto);

    Peispatient getInfoById(String id);

    /**
     * 得到备单人员的信息
     *
     * @param id
     * @param patientCode
     * @return
     */
    HashMap getPatientData(String id, String patientCode);

    /**
     * 获取体检者与收费项目信息
     *
     * @param patientCode
     * @param type
     * @param autoFill
     * @return
     */
    CustomerDataVo getCustomerData(String patientCode, Integer type, Integer autoFill);

    /**
     * 获取右侧收费项目
     *
     * @param patientCode
     * @param type
     * @return
     */
    List<PeispatientfeeitemVo> getExamfeeByPatientCode(String patientCode, Integer type);

    /**
     * 反登记
     *
     * @param patientCode
     * @return
     */
    Boolean setUnRegister(String patientCode);

    /**
     * 前台-登记管理-退项
     *
     * @param refundFeeItemDto
     * @return
     */
    R<Integer> updateTui(RefundFeeItemDto refundFeeItemDto);


    /**
     * 前台-登记-退项-退项恢复
     *
     * @param id 体检者收费项目ID
     * @return
     */
    Boolean returnItem(String id);

    /**
     * 取得已预约客户
     *
     * @param page
     * @param param
     * @return
     */
    IPage<ReservationUserVo> getReservationUser(PageParam<ReservationUserVo> page, ReservationUserParam param);

    /**
     * 获取档案记录
     *
     * @param page
     * @param param
     * @return
     */
    IPage<RecordListVo> getRecordListData(PageParam<RecordListVo> page, RecordListParam param);

    /**
     * 问卷-保存
     *
     * @param params
     * @return
     */
    Map getAnswer(String params);

    /**
     * 问卷-数据
     *
     * @param id
     * @return
     */
    List<QuestionsVo> addQuestions(String id);

    /**
     * 最近体检人员列表
     *
     * @param page
     * @param param
     * @return
     */
    IPage<PaForReVo> getPatientForRegister(PageParam<PaForReVo> page, PaForReParam param);

    /**
     * 根据体检号查询体检者信息
     *
     * @param patientCode
     * @param autoFill
     * @return
     */
    GetPeispatientVo getPeispatient(String patientCode, String autoFill);

    /**
     * 根据体检号查询不同状态的收费项目
     *
     * @param patientCode
     * @param type
     * @param inpatientno
     * @return
     */
    Map getKindItems(String patientCode, String type, String inpatientno);

    /**
     * 批量登记
     *
     * @param ids
     * @return
     */
    String saveBatchRegister(List<String> ids);

    /**
     * 批量设置统收限额
     *
     * @param param
     * @return
     */
    Boolean saveTsLimitEdit(TsLimitEditParam param);

    /**
     * 修改体检者开单医师和备注
     *
     * @param param
     * @return
     */
    Boolean saveEdit(RCSaveEditParam param);

    /**
     * 批量登记查询
     *
     * @param patientcode
     * @return
     */
    PatientForOrderIdVo getPatientForCode(String patientcode);

    /**
     * 导出Excel
     *
     * @param param
     * @return
     */
    List<RCExportVo> getExportData(RegisterParam param);

    /**
     * 导出登记信息列表
     *
     * @param param
     * @return
     */
    List<PaForReVo> getPaExportData(PaForReParam param);


    /**
     * 缴费单打印数据
     *
     * @param id
     * @return
     */
    List<Map<String, Object>> chargeDataPrint(String id);

    /**
     * 通过身份证号获取体检号
     *
     * @param idCard
     * @return
     */
    List<IdcarPatientVo> getPatientcodeByIdcard(String idCard);

    /**
     * 保存发送短信
     *
     * @param param
     * @return
     */
    String saveOrUpdateMsg(SaveOrUpdateMsgParam param);

    /**
     * 取消发送短信
     *
     * @param patientcodes
     * @return
     */
    Boolean cancelSmsPredetection(List<String> patientcodes);

    /**
     * 判断支付方式的金额是否可以退
     *
     * @param param
     * @return
     */
    Boolean checkRefund(CheckRefundParam param);

    /**
     * 体检卡退费
     *
     * @param param
     * @return
     */
    Map receiveTjkCard(ReceiveTjkCardParam param);

    /**
     * 体检卡误操作
     *
     * @param param
     * @return
     */
    Map removeCard(RemoveCardParam param);

    /**
     * 会员卡退费
     *
     * @param param
     * @return
     */
    Map receiveMemberCard(ReceiveMemberParam param);

    /**
     * 第三方支付退款
     *
     * @param param
     * @return
     */
    Map refundThirdPayment(ReceiveTongLianParam param);

    /**
     * 退款管理
     *
     * @param page
     * @param param
     * @return
     */
    IPage<RefundManagementVo> refundManagement(PageParam<RefundManagementVo> page, RefundManagementParam param);

    /**
     * 导出退款管理
     *
     * @param param
     * @return
     */
    List<RefundManagementVo> exportData(RefundManagementParam param);

    /**
     * 疫苗自动交单
     *
     * @param patientcode
     */
    void autoCompare(String patientcode);

    /**
     * 设置没有登记的体检号
     *
     * @param patientcodes
     * @return
     */
    Boolean setDateregister(List<String> patientcodes);

    /**
     * 重新设置错误的档案
     *
     * @return
     */
    Boolean setUpProfile(List<String> patientcodes);

    /**
     * 获取最近人员列表
     *
     * @param page
     * @param param
     * @return
     */
    IPage<RecentPersonnelListVo> getRecentPersonnelList(PageParam<RecentPersonnelListVo> page, RecentPersonnelListParam param);

    /**
     * 校正档案号
     *
     * @return
     */
    String checkPatientarchiveno();

    /**
     * 家庭卡退费
     *
     * @param param
     * @return
     */
    Map receivesFamilyCard(ReFamilyCardParam param);

    /**
     * 重新生成体检号
     *
     * @param codes
     * @return
     */
    Boolean againRegenCode(List<String> codes);

    /**
     * 插入分中心的危害因素
     *
     * @param fzxId
     * @return
     */
    Boolean addHarmAndFzx(List<String> fzxId);


    /**
     * 多中心备单的体检者登记后，删除其他非到检的中心的体检数据
     * @param patientCode 删除对象
     * @param branchList 删除的分中心
     */
    void delOtherBranchPatient(String patientCode, List<String> branchList);
}
