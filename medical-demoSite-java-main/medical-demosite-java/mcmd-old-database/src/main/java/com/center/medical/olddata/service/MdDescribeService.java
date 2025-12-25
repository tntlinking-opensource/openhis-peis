package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdDescribe;

/**
 * KS科室描述、检查结果表(MdDescribe)服务接口
 *
 * @author ay
 * @since 2024-06-05 16:01:38
 */
public interface MdDescribeService extends IService<MdDescribe> {

    /**
     * 分页查询[KS科室描述、检查结果表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdDescribe> getPage(PageParam<MdDescribe> page, MdDescribe param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdDescribe getInfoById(String id);

}

