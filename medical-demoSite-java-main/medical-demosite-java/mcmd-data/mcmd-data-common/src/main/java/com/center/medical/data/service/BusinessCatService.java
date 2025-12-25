package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BusinessCat;
import com.center.medical.data.bean.param.BusinessCatParam;

import java.util.List;

/**
 * 业务类型(BusinessCat)表服务接口
 *
 * @author 路飞船长
 * @since 2023-02-28 15:40:49
 */
public interface BusinessCatService extends IService<BusinessCat> {

    /**
     * 分页查询[业务类型]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<BusinessCat> getPage(PageParam<BusinessCat> page, BusinessCatParam param);

    /**
     * 获取列表
     *
     * @param param 查询参数
     * @return 分页数据
     */
    List<BusinessCat> getList(BusinessCat param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键typeId
     * @return 详情信息
     */
    BusinessCat getInfoById(Long id);

    /**
     * 导出业务类型
     * @param param
     * @return
     */
    List<BusinessCat> getExportData(BusinessCatParam param);
}

