package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.enums.MedicalType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ZyVsSummary;

import java.util.List;

/**
 * JC职业病处理意见(ZyVsSummary)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:16
 */
public interface ZyVsSummaryService extends IService<ZyVsSummary> {

    /**
     * 分页查询[JC职业病处理意见]列表
     *
     * @param page  分页参数
     * @param param ZyVsSummary查询参数
     * @return 分页数据
     */
    IPage<ZyVsSummary> getPage(PageParam<ZyVsSummary> page, ZyVsSummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    ZyVsSummary getInfoById(String id);

    /**
     * 添加或修改
     *
     * @param zyVsSummary
     * @return
     */
    String saveOrUpdateZyVsSummary(ZyVsSummary zyVsSummary);

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    String removeZyVsSummary(String ids);

    /**
     * 同步
     *
     * @return
     */
    String synchonize();

    /**
     * 根据条件获取职业病处理意见ID
     *
     * @param medicaltype 职业体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急
     * @param odList      危害因素ID集合
     * @param itemIds     收费项目ID集合
     * @return
     * @see MedicalType
     */
    List<String> getIdList(String medicaltype, List<String> odList, List<String> itemIds);
}

