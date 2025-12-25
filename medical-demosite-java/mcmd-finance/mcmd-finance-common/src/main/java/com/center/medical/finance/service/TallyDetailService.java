package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.TDPageParam;
import com.center.medical.finance.bean.vo.TDPageVo;

import java.util.List;

/**
 * 记账管理-记账结算明细(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-03-31 14:27:31
 */
public interface TallyDetailService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<TDPageVo> getList(PageParam<TDPageVo> page, TDPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);

    /**
     * 导出记帐结算明细
     *
     * @param param
     * @return
     */
    List<TDPageVo> getExportData(TDPageParam param);
}

