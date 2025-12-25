package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ZySummary;

/**
 * JC职业病检查结论(ZySummary)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:35
 */
public interface ZySummaryService extends IService<ZySummary> {

    /**
     * 分页查询[JC职业病检查结论]列表
     *
     * @param page  分页参数
     * @param param ZySummary查询参数
     * @return 分页数据
     */
    IPage<ZySummary> getList(PageParam<ZySummary> page, ZySummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    ZySummary getInfoById(String id);

    /**
     * 保存或修改
     *
     * @param zySummary
     * @return
     */
    String saveOrUpdateZySummary(ZySummary zySummary);

    /**
     * 逻辑删除数据
     *
     * @param ids
     * @return
     */
    String removeZySummary(String ids);

    /**
     * 获取检查结论下拉
     *
     * @param page
     * @param inputCode
     * @return
     */
    IPage<ZySummary> getJcjlData(PageParam<ZySummary> page, String inputCode);
}

