package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.dto.AccountsTotalDto;
import com.center.medical.finance.bean.param.AccountsInfoParam;
import com.center.medical.finance.bean.param.AnalyseParam;
import com.center.medical.finance.bean.vo.AccountsInfoVo;
import com.center.medical.finance.bean.vo.AnalyseVo;
import com.center.medical.finance.bean.vo.ExportItemsVo;
import com.center.medical.sellcrm.bean.model.Createorder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单表(Createorder)表服务接口
 *
 * @author ay
 * @since 2023-03-11 18:01:03
 */
public interface PhysicalCheckoutService extends IService<Createorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AnalyseVo> getList(PageParam<AnalyseVo> page, AnalyseParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createorder getInfoById(String id);

    /**
     * 查看左中体检人数据
     *
     * @param param
     * @return
     */
    IPage<AccountsInfoVo> getAccountsInfoData(PageParam<AccountsInfoVo> page, AccountsInfoParam param);

    /**
     * 右上禁检或反禁检
     *
     * @param type
     * @param ids
     * @return
     */
    Boolean updateGroupLimit(Integer type, List<String> ids);

    /**
     * 左中-禁检或反禁检
     *
     * @param paused
     * @param ids
     * @return
     */
    Boolean savePausedStatus(Integer paused, List<String> ids);

    /**
     * 左中-已结账
     *
     * @param ids
     * @return
     */
    Boolean finishStatus(List<String> ids);

    /**
     * 左中-反结账
     *
     * @param ids
     * @return
     */
    Boolean unfinishStatus(List<String> ids);

    /**
     * 左中-未检禁检
     *
     * @param paused
     * @param ids
     * @return
     */
    Boolean unSavePausedStatus(Integer paused, List<String> ids);

    /**
     * 右中-项目列表数据
     *
     * @param patientcode
     * @return
     */
    List<Map<String, Object>> getItemListData(String patientcode);

    /**
     * 右下-收费信息
     *
     * @param patientcode
     * @return
     */
    List<Map<String, Object>> getChargeData(String patientcode);

    /**
     * 导出收费项目
     *
     * @param patientCode
     * @return
     */
    List<ExportItemsVo> exportItems(String patientCode);

    /**
     * 获取右侧收费项目
     *
     * @param patientCode
     * @param type
     * @param idtjtc
     * @return
     */
    List<Map<String, Object>> getExamfeeByPatientCode(String patientCode, String type, String idtjtc);


    /**
     * 获取右侧收费项目
     *
     * @param tcid
     * @param idOrder
     * @param idGroup
     * @return
     */
    List getExamfeeitemData(String tcid, String idOrder, String idGroup);

    /**
     * 导出体检人员上方数据
     * @param param
     * @return
     */
    void exportAccountsInfoData(AccountsInfoParam param);

    /**
     * 导出体检人员下方合计数据
     * @param param
     * @return
     */
    List<AccountsTotalDto> exportAccountsTotalDto(AccountsInfoParam param);

    /**
     * 获取登记页面最小套餐收费项目明细
     * @param tcid
     * @return
     */
    List<HashMap<String, String>> getExamfeeitem(String tcid);

    /**
     * 把老系统登记的体检号，在新系统变成禁检
     * @param ddh
     * @return
     */
    Boolean synchronizedChecked(String ddh);
}

