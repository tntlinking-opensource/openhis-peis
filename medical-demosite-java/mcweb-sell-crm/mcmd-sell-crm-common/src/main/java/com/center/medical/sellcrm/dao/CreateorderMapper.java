package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.CustomerOrderVo;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.dto.CreateorderDto;
import com.center.medical.sellcrm.bean.dto.ItemDataDto;
import com.center.medical.sellcrm.bean.dto.VationAndGroupErrorDataDto;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.param.CheckDateParam;
import com.center.medical.sellcrm.bean.param.CreateorderParam;
import com.center.medical.sellcrm.bean.param.OrderParam;
import com.center.medical.sellcrm.bean.vo.*;
import com.center.medical.workflow.bean.model.WorkflowItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单表(Createorder)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:41
 */
public interface CreateorderMapper extends BaseMapper<Createorder> {
    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Createorder> getPage(PageParam<Createorder> page, @Param("param") CreateorderParam param);

    /**
     * 分页查询[订单表]列表
     *
     * @param page             分页参数
     * @param createorderParam Createorder查询参数
     * @return 分页数据
     */
    IPage<CreateorderVo> getList(PageParam<CreateorderVo> page, @Param("param") CreateorderParam createorderParam);

    /**
     * 根据订单代码查询简单列表数据
     *
     * @param param 查询参数
     */
    List<CreateorderVo> getListByKey(@Param("param") CreateorderParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Createorder getInfoById(@Param("id") String id);

    /**
     * 有决策管理权限的分页查询
     *
     * @param page
     * @param param
     * @return
     */
    IPage<Createorder> greatLeaderPage(PageParam<Createorder> page, @Param("param") CreateorderParam param);

    /**
     * 是否领导的的分页查询
     *
     * @param page
     * @param param
     * @return
     */
    IPage<Createorder> leaderPage(PageParam<Createorder> page, @Param("param") CreateorderParam param);

    /**
     * 有材料审核权限的分页查询
     *
     * @param page
     * @param param
     * @return
     */
    IPage<Createorder> clshPage(PageParam<Createorder> page, @Param("param") CreateorderParam param);

    /**
     * 销售自己创建的  和下级的订单
     *
     * @param page
     * @param param
     * @return
     */
    IPage<Createorder> getMyPage(PageParam<Createorder> page, @Param("param") CreateorderParam param);

    /**
     * 根据订单号订单名称获取订单下拉
     *
     * @param page
     * @param ddh
     * @param ddmc
     * @return
     */
    IPage<Createorder> getDdhData(PageParam<Createorder> page, @Param("ddh") String ddh, @Param("ddmc") String ddmc);

    /**
     * 根据客户单位名称ID获取订单
     *
     * @param groupId
     * @return
     */
    List<Createorder> getDateByKhdwmcid(@Param("groupId") String groupId);

    /**
     * 根据key模糊查询获取所有订单数据
     *
     * @param key
     * @return
     */
    List<AllOrderDataVo> getAllOrderData(@Param("key") String key);

    /**
     * 获取检查数据
     *
     * @param param
     * @return
     */
    List<CheckDateVo> checkDate(@Param("param") CheckDateParam param);

    /**
     * 通过sql获取相关的收费项目
     *
     * @param id
     * @return
     */
    List<ItemDataDto> getItemData(@Param("id") String id);

    /**
     * 获取客户单位类型
     *
     * @param id
     * @return
     */
    List<Integer> getGroupLevel(@Param("id") String id);


    /**
     * 根据订单号获取记录详情
     *
     * @param ddh 订单号
     */
    Createorder getInfoByDdh(@Param("ddh") String ddh);

    /**
     * 获取订单号下拉
     *
     * @param key
     * @param customerId
     * @return
     */
    List<DdhDataVo> getDdhDatas(@Param("key") String key, @Param("customerId") String customerId);

    /**
     * 根据订单id获取撞单订单信息
     *
     * @param ddIds
     * @return
     */
    List<ConflictOrderVo> getOrderConflictInfo(@Param("ddIds") List<String> ddIds);

    /**
     * 列表数据  显示订单下所有人员不含复查,判断重复时不判断复查
     *
     * @param ddh
     * @return
     */
    List<COListDataVo> getListData(@Param("ddh") String ddh);

    /**
     * 通过参数获取订单信息
     *
     * @param param 查询参数
     * @return 单条数据
     */
    IPage<CreateorderDto> getOrderInfo(PageParam<CreateorderDto> page, @Param("param") OrderParam param);

    /**
     * 查询平安订单
     * @param id orderandcombo的id
     * @return
     */
    List<Createorder> getPingAnById(@Param("id") String id);

    /**
     * 获取变动成本率
     * @param id
     * @return
     */
    Double getVarCostRate(@Param("id") String id);

    /**
     * 获取订单的分中心
     * @param numorgresv
     * @return
     */
    List<String> getFzxList(@Param("numorgresv") String numorgresv);

    /**
     * 查询订单和任务错误的分组
     * @param ddh
     * @return
     */
    VationAndGroupErrorDataDto getVationAndGroupErrorData(@Param("ddh") String ddh);

    /**
     * 根据客户ID查询订单列表
     * @param customerIds
     * @return
     */
    List<CustomerOrderVo> getOrderByKHIds(@Param("customerIds") List<String> customerIds);

    /**
     * 获取需要选择的数据
     * @param fzxid
     * @param typeFlag
     * @return
     */
    List<WorkflowItem> needChoose(@Param("fzxid") String fzxid , @Param("typeFlag") String typeFlag);
}
