package com.center.medical.reception.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.appadmin.bean.param.GenerateCodeParam;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.dto.CheckListDto;
import com.center.medical.reception.bean.param.*;
import com.center.medical.reception.bean.vo.BdOrderVo;
import com.center.medical.reception.bean.vo.OrderPaDataVo;
import com.center.medical.reception.bean.vo.StatisticsVo;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.param.DbOrderParam;
import com.center.medical.sellcrm.bean.param.SaOrUpGroupParam;
import com.center.medical.sellcrm.bean.param.SaOrUpPatientParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 路飞
 * @date: 2022-12-05 19:56
 * @description: 前台-备单服务接口
 */
public interface OrderService extends IService<Createorder> {

    /**
     * 分页查询备单订单列表
     *
     * @param page  分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    IPage<BdOrderVo> getPage(PageParam<Createorder> page, DbOrderParam param);

    /**
     * 查看套餐
     *
     * @param idOrgRw
     * @return
     */
    Map getGroupData(String idOrgRw,String tjtcmc);

    /**
     * 备单管理-上方数据
     *
     * @param id
     * @return
     */
    Map edit(String id);

    /**
     * 设置工种
     *
     * @param id
     * @param ids
     * @return
     */
    Boolean setWorktype(String id, List<String> ids);

    /**
     * 体检团体分组保存
     *
     * @param param
     * @return
     */
    String saveOrUpdateGroup(SaOrUpGroupParam param);

    /**
     * 备单状态更改
     *
     * @param orderId
     * @param fzxId
     * @return
     */
    String updateNotifyRemoteOrder(String orderId, String fzxId);

    /**
     * 获取分组下相应的人员信息
     *
     * @param page
     * @param param
     * @return
     */
    IPage<OrderPaDataVo> getPatientData(PageParam<OrderPaDataVo> page, OrderPaDataParam param);

    /**
     * 体检者基本信息保存（预登记）
     *
     * @param param
     * @return
     */
    Boolean saveOrUpdatePatient(SaOrUpPatientParam param);

    /**
     * 导入名单
     *
     * @param param 导入参数
     * @return
     */
    R importPatientBatch(ImportPatientBatchParam param);

    /**
     * 来检短信提醒-保存
     *
     * @param param
     * @return
     */
    Boolean saveSmsToExam(SmsToExamParam param);

    /**
     * 批量设置
     *
     * @param data
     * @param id
     * @return
     */
    Boolean updatehy(List<String> data, String id);

    /**
     * 获取分页统计数据
     *
     * @return
     */
    StatisticsVo getStatistics(DbOrderParam param);

    /**
     * 清除
     *
     * @param id
     * @param ids
     * @return
     */
    Boolean removeAll(String id, List<String> ids);

    /**
     * 获取当前选中的已预约用户信息
     *
     * @param patientCode
     * @param type
     * @param autoFill
     * @return
     */
    HashMap getCustomerData(String patientCode, String type, String autoFill);

    /**
     * 反收费
     *
     * @param id
     * @return
     */
    Boolean returnItem(String id);

    /**
     * 获取订单导出数据
     *
     * @param param
     * @return
     */
    void getExportData(HttpServletResponse response,DbOrderParam param) throws IOException;

    /**
     * 导出应急导引单
     * @param id
     * @return
     */
    void exportGuidanceList(HttpServletResponse response, String id) throws Exception;

    /**
     * 重置所选订单及其套餐在网上的下载状态
     * @param orderIds
     * @param cid
     * @return
     */
    Boolean returnToZero(List<String> orderIds, String cid);

    /**
     * 分组-禁检或反禁检
     * @param type
     * @param ids
     * @return
     */
    Boolean updateGroupLimit(Integer type, List<String> ids);

    /**
     * 计算总工龄和接害工龄
     * @param patientCodes
     * @return
     */
    Boolean calculateZglAndJhgl(List<String> patientCodes);

    /**
     * 发送预约短信
     * @param ddh
     * @return
     */
    Boolean appointmentSMSByDddh(String ddh);

    /**
     * 获取分组下相应的人员信息(不分页)
     * @param param
     * @return
     */
    List<OrderPaDataVo> getPatientDataList(OrderPaDataParam param);

    /**
     * 检查名单
     * @param param
     * @return
     */
    List<CheckListDto> checkList(CheckListParam param);

    /**
     * 校正体检者
     * @param idPayway
     * @return
     */
    Boolean checkPeispatient(String idPayway);

    /**
     * 单位预约提交
     * @param param
     * @return
     */
    String unitReservation(UnitReservationParam param);

    /**
     * 生成体检者主表
     * @param patientCodes
     * @return
     */
    Boolean generatePatientChangeMain(List<String> patientCodes);

    /**
     * 重新计算年龄
     * @param ddhs
     * @return
     */
    Boolean calculateAge(List<String> ddhs);

    /**
     * 重新计算价格
     * @param ddhs
     * @return
     */
    Boolean recalculatePrices(List<String> ddhs);

    /**
     * 团检套餐加项
     * @param param
     * @return
     */
    Boolean addItem(OrderAddItemParam param);

    /**
     * 添加收费主表
     * @return
     */
    Boolean addChangMain(List<String> patientcodes);

    /**
     * 购买套餐生成体检号
     * @param param
     * @return
     */
    String generateCode(GenerateCodeParam param);

    /**
     * 退款删除体检号
     * @param code
     * @return
     */
    Boolean deleteCode(String code);

    /**
     * 校验体检号状态的体检号是
     * @param code
     * @return
     */
    String checkPeiStatus(String code);

    /**
     * 收费项目不对的重新计算价格
     * @param ddhs
     * @return
     */
    String recalculatePrice(List<String> ddhs);

    /**
     * 线上重新绑定体检者任务和分组
     * @return
     */
    String bindingVationAndGroupAgain(List<String> ddhs);

    /**
     * 设为可重复
     * @param ids
     * @return
     */
    Boolean setRepeated(List<String> ids);

    /**
     * 重新计算收费项目的价格
     * @param peispatient
     */
    void recalculatePeiPrice(Peispatient peispatient);

    /**
     * 补全套餐项目
     * @param patientcode
     * @return
     */
    Boolean completeTheProject(String patientcode);

    /**
     * 拉取线上体检者数据
     * @param patientcode 体检号
     * @param type 插入类型 0全部 1收费项目 2收费主表
     * @return
     */
    Boolean pullOnlineData(String patientcode , Integer type);

    /**
     * 清理失效图片
     * @return
     */
    String cleanUpInvalidImages();

    /**
     * 重新计算已登记体检号的价格
     * @param patientCodes
     * @return
     */
    Boolean recalculateRegistrationPrice(List<String> patientCodes);

    /**
     * 批量加项
     * @param param
     * @return
     */
    Boolean batchAddItems(BatchAddItemsParam param);

    /**
     * 重新添加收费项目
     * @param patientCodes
     * @return
     */
    Boolean addAgainFeeItem(List<String> patientCodes);
}
