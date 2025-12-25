package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Selltarget;
import com.center.medical.sellcrm.bean.param.SellDateParam;
import com.center.medical.sellcrm.bean.param.SelltargetParam;
import com.center.medical.sellcrm.bean.vo.*;

import java.util.List;

/**
 * XS销售目标(Selltarget)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:23
 */
public interface SelltargetService extends IService<Selltarget> {

    /**
     * 分页查询[XS销售目标]列表
     *
     * @param page  分页参数
     * @param selltargetParam 查询参数
     * @return 分页数据
     */
    IPage<SelltargetVo> getPage(PageParam<SelltargetVo> page, SelltargetParam selltargetParam);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Selltarget getInfoById(String id);

    /**
     * 获取总结数据
     * @param selltargetParam
     * @return
     */
    List<SelltargetVo> getSummaryData(SelltargetParam selltargetParam);

    /**
     * 数据保存或编辑
     * @param selltarget
     * @return
     */
    Boolean saOrUp(Selltarget selltarget);

    /**
     * 获取销售人员关联的数据
     * @param selldatayear
     * @param selldatauserid
     * @return
     */
    GetXsAndDataVo getXsAndData(String selldatayear, String selldatauserid);

    /**
     * 不分页查全部
     * @param selltargetParam
     * @return
     */
    List<SelltargetVo> getAllList(SelltargetParam selltargetParam);

    /**
     * 搜索查询
     * @param sellDateParam
     * @return
     */
    IPage<SellDateVo> getSellDatePage(PageParam<SellDateVo> page,SellDateParam sellDateParam);

    /**
     * 根据用户id、年份、月份获取关联的数据
     * @param sellDateParam
     * @return
     */
    SellDateVo getXsAndSellDate(SellDateParam sellDateParam);

    /**
     * 销售同期对比年
     * @param page
     * @param sellDateParam
     * @return
     */
    IPage<SellDateVo> getSellDateYear(PageParam<SellDateVo> page, SellDateParam sellDateParam);

    /**
     * 销售同期对比季度
     * @param page
     * @param sellDateParam
     * @return
     */
    IPage<SellDateQuarterVo> getSellDateQuarter(PageParam<SellDateQuarterVo> page, SellDateParam sellDateParam);

    /**
     * 销售同期对比月
     * @param page
     * @param sellDateParam
     * @return
     */
    IPage<SellDateMonthVo> getSellDateMonth(PageParam<SellDateMonthVo> page, SellDateParam sellDateParam);
}

