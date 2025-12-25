package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Branch;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.param.*;
import com.center.medical.sellcrm.bean.vo.AllTcOrderVo;
import com.center.medical.sellcrm.bean.vo.ItemOnlineVo;
import com.center.medical.sellcrm.bean.vo.NeedNoticeVo;
import com.center.medical.sellcrm.bean.vo.PatientDataVo;

import java.util.List;
import java.util.Map;

/**
 * 订单表(Createorder)表服务接口
 *
 * @author ay
 * @since 2023-03-11 18:01:03
 */
public interface ItemListOnlineService extends IService<Createorder> {

    /**
    * 分页查询[订单表]列表
    *
    * @param page 分页参数
    * @param param 查询参数
    * @return 分页数据
    */
    IPage<ItemOnlineVo> getList(PageParam<ItemOnlineVo> page, ItemOnlineParam param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    Map getInfoById(String id);

    /**
     * 导出Excel
     * @param param
     * @return
     */
    List<ItemOnlineVo> getExportData(ItemOnlineParam param);

    /**
     * 获取体检团体分组信息
     * @param idOrgRw
     * @return
     */
    Map getGroupData(String idOrgRw);

    /**
     * 获取订单下的所有套餐
     * @param page
     * @param param
     * @return
     */
    IPage<AllTcOrderVo> getAllTcForOrder(PageParam<AllTcOrderVo> page, AllTcOrderParam param);

    /**
     * 获取套餐的分中心
     * @param id
     * @return
     */
    List<Branch> getBranchData(String id);

    /**
     * 体检团体分组保存
     * @param param
     * @return
     */
    String saveOrUpdateGroup(SaOrUpGroupParam param);

    /**
     * 备单确认
     * @param ids
     * @return
     */
    Boolean groupConfirm(List<String> ids);

    /**
     * 获取分组下相应的人员信息
     * @param page
     * @param param
     * @return
     */
    IPage<PatientDataVo> getPatientData(PageParam<PatientDataVo> page, PatientDataParam param);

    /**
     * 体检者基本信息保存（预登记）
     * @param param
     * @return
     */
    Boolean saveOrUpdatePatient(SaOrUpPatientParam param);

    /**
     * 清除
     * @param id
     * @param ids
     * @return
     */
    Boolean removeAll(String id, List<String> ids);

    /**
     * 已备单
     * @param ids
     * @return
     */
    Boolean markTbzt(List<String> ids);

    /**
     * 订单结束/反结束
     * @param ids
     * @param type
     * @return
     */
    Boolean finishOrder(List<String> ids, int type);

    /**
     * 线上备单-绑定档案
     * @param patient
     * @return
     */
    String bindArchive(Peispatient patient);

    /**
     * 线上备单发送短信 查询数据
     * @return
     */
    List<NeedNoticeVo> selectNeedNoticeWechatCodeList();
}

