package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.KdDepartment;
import com.center.medical.system.bean.param.KdDepartmentParam;

/**
 * kingdeedepartment(KdDepartment)表服务接口
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:42
 */
public interface KdDepartmentService extends IService<KdDepartment> {

    /**
     * 分页查询[kingdeedepartment]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<KdDepartment> getPage(PageParam<KdDepartment> page, KdDepartmentParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键accountNo
     * @return 详情信息
     */
    KdDepartment getInfoById(String id);

    /**
     * 金蝶部门更新
     */
    void upgradeDepartmentByKingdee();
}

