package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.bean.param.SDPageParam;
import com.center.medical.finance.bean.vo.SDPageVo;

import java.util.List;

/**
 * 体检卡管理-卡消费明细(Card)表服务接口
 *
 * @author ay
 * @since 2023-03-30 18:47:31
 */
public interface SpendDetailService extends IService<Card> {

    /**
     * 分页查询[体检卡]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SDPageVo> getList(PageParam<SDPageVo> page, SDPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Card getInfoById(String id);

    /**
     * 导出体检卡消费明细
     *
     * @param param
     * @return
     */
    List<SDPageVo> getExportData(SDPageParam param);
}

