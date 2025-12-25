package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.IRPageParam;
import com.center.medical.finance.bean.param.IRSaOrUpParam;
import com.center.medical.finance.bean.vo.IRPageVo;
import com.center.medical.sellcrm.bean.model.Peisorgreservationreceipt;

import java.util.List;

/**
 * 发票管理(Peisorgreservationreceipt)表服务接口
 *
 * @author ay
 * @since 2023-04-04 11:00:04
 */
public interface InvoiceRequestService extends IService<Peisorgreservationreceipt> {

    /**
     * 分页查询[发票]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<IRPageVo> getList(PageParam<IRPageVo> page, IRPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peisorgreservationreceipt getInfoById(String id);

    /**
     * 发票导出
     *
     * @param param
     * @return
     */
    List<IRPageVo> getExportData(IRPageParam param);

    /**
     * 获取图表数据
     *
     * @param param
     * @return
     */
    List<String> getBarData(BaseParam param);

    /**
     * 发票申请保存
     *
     * @param param
     * @return
     */
    Boolean saOrUp(IRSaOrUpParam param);

    /**
     * 删除发票
     *
     * @param ids
     * @return
     */
    Boolean removeIds(List<String> ids);

    /**
     * 审核
     *
     * @param id
     * @return
     */
    Boolean examine(String id);

    /**
     * 反审核
     *
     * @param id
     * @param unauditNote
     * @return
     */
    Boolean unauditSave(String id, String unauditNote);

    /**
     * 审核不通过
     *
     * @param id
     * @return
     */
    Boolean unapprove(String id);

    /**
     * 出票信息保存
     *
     * @param param
     * @return
     */
    Boolean saOrUpPrint(IRSaOrUpParam param);

    /**
     * 换票申请保存
     *
     * @param param
     * @return
     */
    Boolean saveReturnApply(IRSaOrUpParam param);

    /**
     * 换票撤回
     *
     * @param id
     * @return
     */
    Boolean saveReturnCancle(String id);

    /**
     * 换票审核
     *
     * @param param
     * @return
     */
    Boolean saveReturnAudit(IRSaOrUpParam param);

    /**
     * 换票反审核
     *
     * @param param
     * @return
     */
    Boolean saveReturnUnaudit(IRSaOrUpParam param);

    /**
     * 换票
     *
     * @param param
     * @return
     */
    Boolean saveReturnConfirm(IRSaOrUpParam param);
}

