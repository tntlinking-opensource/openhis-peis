package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.ReceiptLetterParam;
import com.center.medical.finance.bean.vo.ReceiptLetterVo;
import com.center.medical.sellcrm.bean.model.Peisorgreservation;

import java.util.List;

/**
 * 体检者团体任务(Peisorgreservation)服务接口
 *
 * @author ay
 * @since 2024-02-19 15:50:20
 */
public interface AdvanceReceiptLetterService extends IService<Peisorgreservation> {

    /**
     * 分页查询[体检者团体任务]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReceiptLetterVo> getPage(PageParam<ReceiptLetterVo> page, ReceiptLetterParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peisorgreservation getInfoById(String id);

    /**
     * 导出应收预收函证数据
     * @param param
     * @return
     */
    List<ReceiptLetterVo> getExportData(ReceiptLetterParam param);
}

