package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.vo.CustomerOrderVo;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.dto.CreateorderDto;
import com.center.medical.sellcrm.bean.dto.VationAndGroupErrorDataDto;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.model.Peisorgreservationgroup;
import com.center.medical.sellcrm.bean.param.*;
import com.center.medical.sellcrm.bean.vo.*;
import com.center.medical.system.bean.vo.KdzlVo;
import com.center.medical.workflow.bean.model.WorkflowItem;

import java.util.List;
import java.util.Map;

/**
 * 订单表(Createorder)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:41
 */
public interface CreateorderService extends IService<Createorder> {


    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Createorder> getPage(PageParam<Createorder> page, CreateorderParam param);

    /**
     * 分页查询[订单表]列表
     *
     * @param page             分页参数
     * @param createorderParam Createorder查询参数
     * @return 分页数据
     */
    IPage<CreateorderVo> getList(PageParam<CreateorderVo> page, CreateorderParam createorderParam);

    /**
     * 根据订单代码查询简单列表数据
     *
     * @param key 订单代码
     */
    List<CreateorderVo> getListByKey(String key);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Createorder getInfoById(String id);

    /**
     * 新增/编辑操作
     *
     * @param createorder
     * @return
     */
    Boolean saOrUp(Createorder createorder);

    /**
     * 订单里面为套餐增加收费项目
     *
     * @param addItemsParam
     * @return
     */
    Boolean addItems(AddItemsParam addItemsParam);

    /**
     * 根据套餐id删除关联的收费项目
     *
     * @param tcrowId
     * @param sfxmId
     * @return
     */
    Boolean removeItemsData(String tcrowId, List<String> sfxmId);

    /**
     * 提交订单
     *
     * @param ddcomId
     * @param flag    是否需要进行撞单排查：true需要、false不需要
     * @return
     */
    R commit(List<String> ddcomId, Boolean flag , List<String> approverIds);


    /**
     * 变更审核
     *
     * @param checkOrderParam
     * @return
     */
    Boolean checkChange(CheckOrderParam checkOrderParam);

    /**
     * 普通审核
     *
     * @param checkOrderParam
     * @return
     */
    Boolean checkOrder(CheckOrderParam checkOrderParam);

    /**
     * 判断要编辑的订单是否为“提交”或“审核通过”状态
     *
     * @param isTjOrShtgId
     * @return
     */
    Boolean isTjOrShtg(String isTjOrShtgId);

    /**
     * 判断对于已提交与审核通过的订单不能删除
     *
     * @param isRemoveId
     * @return
     */
    String isRemove(List<String> isRemoveId);

    /**
     * 判断选择的记录是否是【提交】和【审核通过】的,这两种状态不能再提交
     *
     * @param isCommitAndSptgId
     * @return
     */
    String isCommitAndSptg(List<String> isCommitAndSptgId);


    /**
     * 判断对于订单状态为：草稿、已撤回、审核通过、审核未通过--状态的订单不能再进行撤回
     *
     * @param isChId
     * @return
     */
    String isCh(List<String> isChId);

    /**
     * 根据订单号订单名称获取订单下拉
     *
     * @param page
     * @param ddh
     * @param ddmc
     * @return
     */
    IPage<Createorder> getDdhData(PageParam<Createorder> page, String ddh, String ddmc);

    /**
     * 根据客户单位名称ID获取订单
     *
     * @param groupId
     * @return
     */
    List<Createorder> getDateByKhdwmcid(String groupId);

    /**
     * 根据key模糊查询获取所有订单数据
     *
     * @param key
     * @return
     */
    List<AllOrderDataVo> getAllOrderData(String key);

    /**
     * 获取开单助理
     *
     * @param page
     * @param key
     * @return
     */
    IPage<KdzlVo> getKdzl(PageParam<KdzlVo> page, String key);

    /**
     * 检查数据
     *
     * @param param
     * @return
     */
    List<CheckDateVo> checkDate(CheckDateParam param);

    /**
     * 隐藏展示操作
     *
     * @param paused
     * @param ids
     * @param ddId
     * @return
     */
    Boolean showOrHide(Integer paused, List<String> ids, String ddId);

    /**
     * 获取当前季度最低折扣
     *
     * @return
     */
    Double getSeasonZk();

    /**
     * 订单撤回
     *
     * @param ddid 订单ID列表
     * @return
     */
    Boolean undo(List<String> ddid);

    /**
     * 订单反审核
     *
     * @param id
     * @return
     */
    Boolean undoOrder(String id);

    /**
     * 总结-保存
     *
     * @param param
     * @return
     */
    Boolean saOrUpSummary(SaOrUpSumParam param);

    /**
     * 变更提交
     *
     * @param ids
     * @return
     */
    Boolean commitChange(List<String> ids , List<String> approverIds);

    /**
     * 变更撤回
     *
     * @param ids
     * @return
     */
    Boolean undoChange(List<String> ids);

    /**
     * 变更反审
     *
     * @param id
     * @return
     */
    Boolean unauditChange(String id);

    /**
     * 材料通过
     *
     * @param ids
     * @param clspzt
     * @param clspyj
     * @return
     */
    Boolean clpassOrUmpass(List<String> ids, int clspzt, String clspyj);

    /**
     * 修改发放方式-保存
     *
     * @param ids
     * @param idInformway
     * @return
     */
    Boolean saveInfo(List<String> ids, String idInformway);

    /**
     * 变更前台须知-保存
     *
     * @param orderId
     * @param qtxz
     * @return
     */
    Boolean saveQtxz(String orderId, String qtxz);

    /**
     * 编辑开单助理保存
     *
     * @param id
     * @param kdzlName
     * @return
     */
    Boolean saveKdzl(String id, String kdzlName);

    /**
     * 查看套餐
     *
     * @param createorder
     * @return
     */
    Map getDataForRequest2(Createorder createorder);

    /**
     * 获取客户单位类型
     *
     * @param id
     * @return
     */
    Integer getGroupLevel(String id);

    /**
     * 获取订单号下拉
     *
     * @param key
     * @param customerId
     * @return
     */
    List<DdhDataVo> getDdhDatas(String key, String customerId);

    /**
     * 材料路径保存
     *
     * @param id   订单号
     * @param urls 材料路径：多个以英文
     * @return
     */
    Boolean saveClUrl(String id, List<String> urls);

    /**
     * 上传名单-保存
     *
     * @param param
     * @return
     */
    Boolean saveUpload(SaveUploadParam param);

    /**
     * 列表数据  显示订单下所有人员不含复查,判断重复时不判断复查
     *
     * @param ddh
     * @return
     */
    List<COListDataVo> getListData(String ddh);

    /**
     * 导入名单-保存
     *
     * @param param
     * @return
     */
    Boolean saOrUpNameList(SaOrUpNameListParam param);


    /**
     * 导入名单-删除
     *
     * @param param
     * @return
     */
    Boolean removeAll(RemoveAllParam param);

    /**
     * 获取体检者任务分组
     *
     * @param id
     * @return
     */
    List<Peisorgreservationgroup> getGroup(String id);

    /**
     * 删除订单
     *
     * @param ids 订单id集合
     * @return
     */
    Boolean removeOrders(List<String> ids);

    /**
     * 通过参数获取订单信息
     *
     * @param param 查询参数
     * @return 单条数据
     */
    IPage<CreateorderDto> getOrderInfo(PageParam<CreateorderDto> page, OrderParam param);


    /**
     * 绑定档案
     *
     * @param patient
     * @return
     */
    String bindArchive(Peispatient patient);

    /**
     * 获取套餐
     * @param createorder
     * @return
     */
    List<GetPackageVo> getPackage(Createorder createorder);

    /**
     * 获取变动成本率
     * @param id
     * @return
     */
    Double getVarCostRate(String id);

    /**
     * 获取订单的分中心
     * @param numorgresv
     * @return
     */
    List<String> getFzxList(String numorgresv);

    /**
     * 查询订单和任务错误的分组
     * @param ddh
     * @return
     */
    VationAndGroupErrorDataDto getVationAndGroupErrorData(String ddh);

    /**
     * 根据客户ID查询订单列表
     * @param customerIds
     * @return
     */
    List<CustomerOrderVo>  getOrderByKHIds(List<String> customerIds);

    /**
     * 是否需要选择
     * @param param
     * @return
     */
    List<WorkflowItem> needChoose(NeedChooseParam param);
}

